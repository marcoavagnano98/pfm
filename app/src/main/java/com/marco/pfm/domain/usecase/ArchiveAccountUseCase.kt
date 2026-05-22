package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.AccountRepository
import javax.inject.Inject

class ArchiveAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(accountId: Long) {
        accountRepository.archiveAccount(accountId)
    }
}
