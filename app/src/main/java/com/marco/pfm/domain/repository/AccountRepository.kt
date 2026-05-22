package com.marco.pfm.domain.repository

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(includeArchived: Boolean = false): Flow<List<Account>>

    suspend fun getAccount(accountId: Long): Account?

    suspend fun createAccount(
        name: String,
        type: AccountType,
        initialBalanceMinor: Long,
    )

    suspend fun updateAccount(account: Account)

    suspend fun archiveAccount(accountId: Long)
}
