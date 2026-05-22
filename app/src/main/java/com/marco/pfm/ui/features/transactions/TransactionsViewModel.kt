package com.marco.pfm.ui.features.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.usecase.GetAccountsUseCase
import com.marco.pfm.domain.usecase.GetCategoriesUseCase
import com.marco.pfm.domain.usecase.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

data class TransactionsUiState(
    val transactions: List<Transaction> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedPeriodMonths: Int = 1,
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TransactionsViewModel @Inject constructor(
    getTransactions: GetTransactionsUseCase,
    getAccounts: GetAccountsUseCase,
    getCategories: GetCategoriesUseCase,
) : ViewModel() {
    private val selectedPeriodMonths = MutableStateFlow(1)

    val uiState: StateFlow<TransactionsUiState> = combine(
        selectedPeriodMonths.flatMapLatest { months -> getTransactions(months) },
        getAccounts(),
        getCategories(),
        selectedPeriodMonths,
    ) { transactions, accounts, categories, periodMonths ->
        TransactionsUiState(
            transactions = transactions,
            accounts = accounts,
            categories = categories,
            selectedPeriodMonths = periodMonths,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TransactionsUiState(),
    )

    fun selectPeriod(months: Int) {
        if (months in supportedPeriods) {
            selectedPeriodMonths.value = months
        }
    }

    private companion object {
        val supportedPeriods = setOf(1, 2, 3, 12)
    }
}
