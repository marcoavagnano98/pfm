package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(account: Account) {
        require(account.name.isNotBlank()) { "Account name is required." }
        accountRepository.updateAccount(account.copy(name = account.name.trim()))
    }
}
