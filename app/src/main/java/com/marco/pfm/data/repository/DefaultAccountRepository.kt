package com.marco.pfm.data.repository

import com.marco.pfm.data.local.dao.AccountDao
import com.marco.pfm.data.local.entity.AccountEntity
import com.marco.pfm.data.local.mapper.toDomain
import com.marco.pfm.data.local.mapper.toEntity
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import com.marco.pfm.domain.repository.AccountRepository
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAccountRepository @Inject constructor(
    private val accountDao: AccountDao,
) : AccountRepository {
    override fun getAccounts(includeArchived: Boolean): Flow<List<Account>> =
        accountDao.observeAccounts(includeArchived).map { accounts ->
            accounts.map(AccountEntity::toDomain)
        }

    override suspend fun getAccount(accountId: Long): Account? =
        accountDao.getAccount(accountId)?.toDomain()

    override suspend fun createAccount(
        name: String,
        type: AccountType,
        initialBalanceMinor: Long,
    ) {
        val now = Instant.now().toEpochMilli()
        accountDao.insertAccount(
            AccountEntity(
                name = name,
                type = type.name,
                initialBalanceMinor = initialBalanceMinor,
                createdAtEpochMillis = now,
                updatedAtEpochMillis = now,
            ),
        )
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(
            account.copy(updatedAt = Instant.now()).toEntity(),
        )
    }

    override suspend fun archiveAccount(accountId: Long) {
        accountDao.archiveAccount(
            accountId = accountId,
            updatedAtEpochMillis = Instant.now().toEpochMilli(),
        )
    }
}
