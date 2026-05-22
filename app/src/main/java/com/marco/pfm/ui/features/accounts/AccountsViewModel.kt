package com.marco.pfm.ui.features.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.usecase.ArchiveAccountUseCase
import com.marco.pfm.domain.usecase.GetAccountBalanceDeltasUseCase
import com.marco.pfm.domain.usecase.GetAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AccountsUiState(
    val accounts: List<AccountBalanceItem> = emptyList(),
)

data class AccountBalanceItem(
    val account: Account,
    val currentBalanceMinor: Long,
)

@HiltViewModel
class AccountsViewModel @Inject constructor(
    getAccounts: GetAccountsUseCase,
    getAccountBalanceDeltas: GetAccountBalanceDeltasUseCase,
    private val archiveAccount: ArchiveAccountUseCase,
) : ViewModel() {
    val uiState: StateFlow<AccountsUiState> = combine(
        getAccounts(),
        getAccountBalanceDeltas(),
    ) { accounts: List<Account>, balanceDeltas: Map<Long, Long> ->
        AccountsUiState(
            accounts = accounts.map { account ->
                AccountBalanceItem(
                    account = account,
                    currentBalanceMinor = account.initialBalanceMinor + (balanceDeltas[account.id] ?: 0L),
                )
            },
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AccountsUiState(),
        )

    fun archiveAccount(accountId: Long) {
        viewModelScope.launch {
            archiveAccount.invoke(accountId)
        }
    }
}
