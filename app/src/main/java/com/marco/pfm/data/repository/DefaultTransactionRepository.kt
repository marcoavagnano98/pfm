package com.marco.pfm.data.repository

import com.marco.pfm.data.local.dao.TransactionDao
import com.marco.pfm.data.local.entity.TransactionEntity
import com.marco.pfm.data.local.mapper.toDomain
import com.marco.pfm.data.local.mapper.toEntity
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getTransactionsBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>> =
        transactionDao.observeTransactionsBetween(
            startEpochDay = startDate.toEpochDay(),
            endEpochDay = endDate.toEpochDay(),
        ).map { transactions ->
            transactions.map(TransactionEntity::toDomain)
        }

    override fun getAmountSumByTypeBetween(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<Long> = transactionDao.observeAmountSumByTypeBetween(
        type = type.name,
        startEpochDay = startDate.toEpochDay(),
        endEpochDay = endDate.toEpochDay(),
    )

    override fun getAccountBalanceDeltas(): Flow<Map<Long, Long>> =
        transactionDao.observeAccountBalanceDeltas().map { rows ->
            rows.associate { it.accountId to it.deltaMinor }
        }

    override suspend fun getTransaction(transactionId: Long): Transaction? =
        transactionDao.getTransaction(transactionId)?.toDomain()

    override suspend fun getAccountBalanceDelta(accountId: Long): Long =
        transactionDao.getAccountBalanceDelta(accountId)

    override suspend fun createTransaction(
        type: TransactionType,
        amountMinor: Long,
        date: LocalDate,
        sourceAccountId: Long?,
        destinationAccountId: Long?,
        categoryId: Long?,
        note: String?,
    ) {
        val now = Instant.now().toEpochMilli()
        transactionDao.insertTransaction(
            TransactionEntity(
                type = type.name,
                amountMinor = amountMinor,
                dateEpochDay = date.toEpochDay(),
                sourceAccountId = sourceAccountId,
                destinationAccountId = destinationAccountId,
                categoryId = categoryId,
                note = note,
                createdAtEpochMillis = now,
                updatedAtEpochMillis = now,
            ),
        )
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(
            transaction.copy(updatedAt = Instant.now()).toEntity(),
        )
    }
}
