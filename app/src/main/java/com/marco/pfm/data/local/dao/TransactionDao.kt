package com.marco.pfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marco.pfm.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

data class AccountBalanceDeltaRow(
    val accountId: Long,
    val deltaMinor: Long,
)

@Dao
interface TransactionDao {
    @Query(
        """
        SELECT * FROM transactions
        WHERE dateEpochDay BETWEEN :startEpochDay AND :endEpochDay
        ORDER BY dateEpochDay DESC, createdAtEpochMillis DESC
        """,
    )
    fun observeTransactionsBetween(
        startEpochDay: Long,
        endEpochDay: Long,
    ): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT COALESCE(SUM(amountMinor), 0) FROM transactions
        WHERE type = :type AND dateEpochDay BETWEEN :startEpochDay AND :endEpochDay
        """,
    )
    fun observeAmountSumByTypeBetween(
        type: String,
        startEpochDay: Long,
        endEpochDay: Long,
    ): Flow<Long>

    @Query(
        """
        SELECT accountId, COALESCE(SUM(deltaMinor), 0) AS deltaMinor
        FROM (
            SELECT destinationAccountId AS accountId, amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Income' AND destinationAccountId IS NOT NULL
            UNION ALL
            SELECT sourceAccountId AS accountId, -amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Expense' AND sourceAccountId IS NOT NULL
            UNION ALL
            SELECT sourceAccountId AS accountId, -amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Transfer' AND sourceAccountId IS NOT NULL
            UNION ALL
            SELECT destinationAccountId AS accountId, amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Transfer' AND destinationAccountId IS NOT NULL
        )
        GROUP BY accountId
        """,
    )
    fun observeAccountBalanceDeltas(): Flow<List<AccountBalanceDeltaRow>>

    @Query(
        """
        SELECT COALESCE(SUM(deltaMinor), 0)
        FROM (
            SELECT amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Income' AND destinationAccountId = :accountId
            UNION ALL
            SELECT -amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Expense' AND sourceAccountId = :accountId
            UNION ALL
            SELECT -amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Transfer' AND sourceAccountId = :accountId
            UNION ALL
            SELECT amountMinor AS deltaMinor
            FROM transactions
            WHERE type = 'Transfer' AND destinationAccountId = :accountId
        )
        """,
    )
    suspend fun getAccountBalanceDelta(accountId: Long): Long

    @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
    suspend fun getTransaction(transactionId: Long): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
}
