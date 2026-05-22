package com.marco.pfm.ui.features.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.usecase.CreateTransactionUseCase
import com.marco.pfm.domain.usecase.GetAccountsUseCase
import com.marco.pfm.domain.usecase.GetCategoriesUseCase
import com.marco.pfm.domain.usecase.GetTransactionUseCase
import com.marco.pfm.domain.usecase.UpdateTransactionUseCase
import com.marco.pfm.ui.common.formatInputMinorAmount
import com.marco.pfm.ui.common.parseMinorAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TransactionFormUiState(
    val transaction: Transaction? = null,
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val type: TransactionType = TransactionType.Expense,
    val amount: String = "",
    val date: String = LocalDate.now().toDisplayDate(),
    val sourceAccountId: Long? = null,
    val destinationAccountId: Long? = null,
    val categoryId: Long? = null,
    val note: String = "",
    val amountError: String? = null,
    val dateError: String? = null,
    val accountError: String? = null,
    val isSaved: Boolean = false,
) {
    val isEditing: Boolean = transaction != null
    val visibleCategories: List<Category> = categories.filter { it.type == type }
}

@HiltViewModel
class TransactionFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createTransaction: CreateTransactionUseCase,
    private val updateTransaction: UpdateTransactionUseCase,
    private val getTransaction: GetTransactionUseCase,
    getAccounts: GetAccountsUseCase,
    getCategories: GetCategoriesUseCase,
) : ViewModel() {
    private val transactionId: Long = savedStateHandle["transactionId"] ?: 0L

    private val _uiState = MutableStateFlow(TransactionFormUiState())
    val uiState: StateFlow<TransactionFormUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                getAccounts(),
                getCategories(),
            ) { accounts, categories -> accounts to categories }
                .collect { (accounts, categories) ->
                    _uiState.update { state ->
                        val selectedCategoryStillValid = state.categoryId == null ||
                            categories.isEmpty() ||
                            categories.any { it.id == state.categoryId && it.type == state.type }
                        state.copy(
                            accounts = accounts,
                            categories = categories,
                            categoryId = state.categoryId.takeIf { selectedCategoryStillValid },
                        )
                    }
                }
        }

        if (transactionId > 0L) {
            viewModelScope.launch {
                getTransaction(transactionId)?.let { transaction ->
                    _uiState.update {
                        it.copy(
                            transaction = transaction,
                            type = transaction.type,
                            amount = formatInputMinorAmount(transaction.amountMinor),
                            date = transaction.date.toDisplayDate(),
                            sourceAccountId = transaction.sourceAccountId,
                            destinationAccountId = transaction.destinationAccountId,
                            categoryId = transaction.categoryId,
                            note = transaction.note.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    fun onTypeChanged(value: TransactionType) {
        _uiState.update {
            it.copy(
                type = value,
                sourceAccountId = if (value == TransactionType.Income) null else it.sourceAccountId,
                destinationAccountId = if (value == TransactionType.Expense) null else it.destinationAccountId,
                categoryId = null,
                accountError = null,
                isSaved = false,
            )
        }
    }

    fun onAmountChanged(value: String) {
        if (value.length <= 16 && value.all { it.isDigit() || it == '.' || it == ',' }) {
            _uiState.update {
                it.copy(
                    amount = value,
                    amountError = null,
                    isSaved = false,
                )
            }
        }
    }

    fun onDateChanged(value: String) {
        _uiState.update {
            it.copy(
                date = value,
                dateError = null,
                isSaved = false,
            )
        }
    }

    fun onSourceAccountChanged(accountId: Long?) {
        _uiState.update {
            it.copy(
                sourceAccountId = accountId,
                accountError = null,
                isSaved = false,
            )
        }
    }

    fun onDestinationAccountChanged(accountId: Long?) {
        _uiState.update {
            it.copy(
                destinationAccountId = accountId,
                accountError = null,
                isSaved = false,
            )
        }
    }

    fun onCategoryChanged(categoryId: Long?) {
        _uiState.update {
            it.copy(
                categoryId = categoryId,
                isSaved = false,
            )
        }
    }

    fun onNoteChanged(value: String) {
        if (value.length <= 160) {
            _uiState.update {
                it.copy(
                    note = value,
                    isSaved = false,
                )
            }
        }
    }

    fun save() {
        val state = _uiState.value
        val amount = parseMinorAmount(state.amount)
        val date = parseDisplayDate(state.date)
        val amountError = when {
            amount == null -> "Enter a valid amount."
            amount <= 0L -> "Amount must be greater than zero."
            else -> null
        }
        val dateError = if (date == null) "Use YYYY-MM-DD." else null
        val accountError = validateAccounts(state)

        if (amountError != null || dateError != null || accountError != null) {
            _uiState.update {
                it.copy(
                    amountError = amountError,
                    dateError = dateError,
                    accountError = accountError,
                )
            }
            return
        }

        viewModelScope.launch {
            val parsedAmount = amount ?: return@launch
            val parsedDate = date ?: return@launch
            val transaction = state.transaction
            if (transaction == null) {
                createTransaction(
                    type = state.type,
                    amountMinor = parsedAmount,
                    date = parsedDate,
                    sourceAccountId = state.sourceAccountId,
                    destinationAccountId = state.destinationAccountId,
                    categoryId = state.categoryId,
                    note = state.note,
                )
            } else {
                updateTransaction(
                    transaction.copy(
                        type = state.type,
                        amountMinor = parsedAmount,
                        date = parsedDate,
                        sourceAccountId = state.sourceAccountId,
                        destinationAccountId = state.destinationAccountId,
                        categoryId = state.categoryId,
                        note = state.note,
                    ),
                )
            }
            _uiState.update { it.copy(isSaved = true) }
        }
    }

    private fun validateAccounts(state: TransactionFormUiState): String? =
        when (state.type) {
            TransactionType.Income ->
                if (state.destinationAccountId == null) "Choose a destination account." else null

            TransactionType.Expense ->
                if (state.sourceAccountId == null) "Choose a source account." else null

            TransactionType.Transfer -> when {
                state.sourceAccountId == null -> "Choose a source account."
                state.destinationAccountId == null -> "Choose a destination account."
                state.sourceAccountId == state.destinationAccountId -> "Choose two different accounts."
                else -> null
            }
        }
}
