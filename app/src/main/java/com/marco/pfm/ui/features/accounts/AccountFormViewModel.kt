package com.marco.pfm.ui.features.accounts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import com.marco.pfm.domain.usecase.CreateAccountUseCase
import com.marco.pfm.domain.usecase.GetAccountUseCase
import com.marco.pfm.domain.usecase.UpdateAccountUseCase
import com.marco.pfm.ui.common.formatInputMinorAmount
import com.marco.pfm.ui.common.parseMinorAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountFormUiState(
    val account: Account? = null,
    val name: String = "",
    val type: AccountType = AccountType.Bank,
    val initialBalance: String = "0",
    val nameError: String? = null,
    val initialBalanceError: String? = null,
    val isSaved: Boolean = false,
) {
    val isEditing: Boolean = account != null
}

@HiltViewModel
class AccountFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createAccount: CreateAccountUseCase,
    private val updateAccount: UpdateAccountUseCase,
    private val getAccount: GetAccountUseCase,
) : ViewModel() {
    private val accountId: Long = savedStateHandle["accountId"] ?: 0L

    private val _uiState = MutableStateFlow(AccountFormUiState())
    val uiState: StateFlow<AccountFormUiState> = _uiState

    init {
        if (accountId > 0L) {
            viewModelScope.launch {
                getAccount(accountId)?.let { account ->
                    _uiState.value = AccountFormUiState(
                        account = account,
                        name = account.name,
                        type = account.type,
                        initialBalance = formatInputMinorAmount(account.initialBalanceMinor),
                    )
                }
            }
        }
    }

    fun onNameChanged(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                nameError = null,
                isSaved = false,
            )
        }
    }

    fun onTypeChanged(value: AccountType) {
        _uiState.update {
            it.copy(
                type = value,
                isSaved = false,
            )
        }
    }

    fun onInitialBalanceChanged(value: String) {
        if (value.length <= 16 && value.all { it.isDigit() || it == '.' || it == ',' || it == '-' }) {
            _uiState.update {
                it.copy(
                    initialBalance = value,
                    initialBalanceError = null,
                    isSaved = false,
                )
            }
        }
    }

    fun save() {
        val state = _uiState.value
        val amount = parseMinorAmount(state.initialBalance)
        val nameError = if (state.name.isBlank()) "Name is required." else null
        val amountError = if (amount == null) "Enter a valid amount." else null

        if (nameError != null || amountError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    initialBalanceError = amountError,
                )
            }
            return
        }

        viewModelScope.launch {
            val parsedAmount = amount ?: return@launch
            val account = state.account
            if (account == null) {
                createAccount(
                    name = state.name,
                    type = state.type,
                    initialBalanceMinor = parsedAmount,
                )
            } else {
                updateAccount(
                    account.copy(
                        name = state.name,
                        type = state.type,
                        initialBalanceMinor = parsedAmount,
                    ),
                )
            }
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
