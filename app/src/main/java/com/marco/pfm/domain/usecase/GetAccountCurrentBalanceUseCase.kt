package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.AccountRepository
import com.marco.pfm.domain.repository.TransactionRepository
import javax.inject.Inject

class GetAccountCurrentBalanceUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(accountId: Long): Long? {
        val account = accountRepository.getAccount(accountId) ?: return null
        val balanceDeltaMinor = transactionRepository.getAccountBalanceDelta(accountId)
        return account.initialBalanceMinor + balanceDeltaMinor
    }
}
