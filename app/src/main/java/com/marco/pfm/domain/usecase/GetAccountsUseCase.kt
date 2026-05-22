package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(includeArchived: Boolean = false): Flow<List<Account>> =
        accountRepository.getAccounts(includeArchived)
}
