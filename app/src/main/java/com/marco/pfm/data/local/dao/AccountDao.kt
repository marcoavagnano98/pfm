package com.marco.pfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marco.pfm.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query(
        """
        SELECT * FROM accounts
        WHERE (:includeArchived = 1 OR isArchived = 0)
        ORDER BY isArchived ASC, name COLLATE NOCASE ASC
        """,
    )
    fun observeAccounts(includeArchived: Boolean): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :accountId LIMIT 1")
    suspend fun getAccount(accountId: Long): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Query("UPDATE accounts SET isArchived = 1, updatedAtEpochMillis = :updatedAtEpochMillis WHERE id = :accountId")
    suspend fun archiveAccount(
        accountId: Long,
        updatedAtEpochMillis: Long,
    )
}
