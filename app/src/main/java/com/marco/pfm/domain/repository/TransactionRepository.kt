package com.marco.pfm.domain.repository

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>>

    fun getAmountSumByTypeBetween(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<Long>

    fun getAccountBalanceDeltas(): Flow<Map<Long, Long>>

    suspend fun getTransaction(transactionId: Long): Transaction?

    suspend fun getAccountBalanceDelta(accountId: Long): Long

    suspend fun createTransaction(
        type: TransactionType,
        amountMinor: Long,
        date: LocalDate,
        sourceAccountId: Long?,
        destinationAccountId: Long?,
        categoryId: Long?,
        note: String?,
    )

    suspend fun updateTransaction(transaction: Transaction)
}
