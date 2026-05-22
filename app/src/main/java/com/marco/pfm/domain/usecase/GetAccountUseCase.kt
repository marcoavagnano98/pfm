package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(accountId: Long): Account? = accountRepository.getAccount(accountId)
}
