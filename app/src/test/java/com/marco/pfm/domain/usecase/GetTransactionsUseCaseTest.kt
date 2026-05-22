package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTransactionsUseCaseTest {
    @Test
    fun `transactions use case requests selected period range`() {
        val repository = FakeTransactionsRepository()
        GetTransactionsUseCase(repository)
            .invoke(months = 3, today = LocalDate.of(2026, 5, 22))

        assertEquals(
            LocalDate.of(2026, 3, 1) to LocalDate.of(2026, 5, 22),
            repository.lastRange,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `transactions use case rejects unsupported periods`() {
        GetTransactionsUseCase(FakeTransactionsRepository())
            .invoke(months = 6, today = LocalDate.of(2026, 5, 22))
    }
}

private class FakeTransactionsRepository : TransactionRepository {
    var lastRange: Pair<LocalDate, LocalDate>? = null

    override fun getTransactionsBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>> {
        lastRange = startDate to endDate
        return flowOf(emptyList())
    }

    override fun getAmountSumByTypeBetween(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<Long> = flowOf(0L)

    override fun getAccountBalanceDeltas(): Flow<Map<Long, Long>> = flowOf(emptyMap())

    override suspend fun getTransaction(transactionId: Long): Transaction? = null

    override suspend fun getAccountBalanceDelta(accountId: Long): Long = 0

    override suspend fun createTransaction(
        type: TransactionType,
        amountMinor: Long,
        date: LocalDate,
        sourceAccountId: Long?,
        destinationAccountId: Long?,
        categoryId: Long?,
        note: String?,
    ) = Unit

    override suspend fun updateTransaction(transaction: Transaction) = Unit
}
