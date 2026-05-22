package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.AccountType
import com.marco.pfm.domain.repository.AccountRepository
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(
        name: String,
        type: AccountType,
        initialBalanceMinor: Long,
    ) {
        require(name.isNotBlank()) { "Account name is required." }
        accountRepository.createAccount(
            name = name.trim(),
            type = type,
            initialBalanceMinor = initialBalanceMinor,
        )
    }
}
