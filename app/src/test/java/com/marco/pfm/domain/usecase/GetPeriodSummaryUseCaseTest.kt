package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPeriodSummaryUseCaseTest {
    @Test
    fun `period summary uses income minus expenses for net`() = runTest {
        val repository = FakePeriodSummaryTransactionRepository(
            incomeMinor = 1_000,
            expensesMinor = 250,
        )
        val summary = GetPeriodSummaryUseCase(repository)
            .invoke(months = 3, today = LocalDate.of(2026, 5, 21))
            .first()

        assertEquals(3, summary.months)
        assertEquals(1_000L, summary.incomeMinor)
        assertEquals(250L, summary.expensesMinor)
        assertEquals(750L, summary.netMinor)
        assertEquals(
            LocalDate.of(2026, 3, 1) to LocalDate.of(2026, 5, 21),
            repository.lastRange,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `period summary rejects unsupported periods`() {
        GetPeriodSummaryUseCase(FakePeriodSummaryTransactionRepository())
            .invoke(months = 6, today = LocalDate.of(2026, 5, 21))
    }
}

private class FakePeriodSummaryTransactionRepository(
    private val incomeMinor: Long = 0,
    private val expensesMinor: Long = 0,
) : TransactionRepository {
    var lastRange: Pair<LocalDate, LocalDate>? = null

    override fun getTransactionsBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>> = flowOf(emptyList())

    override fun getAmountSumByTypeBetween(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<Long> {
        lastRange = startDate to endDate
        return flowOf(
            when (type) {
                TransactionType.Income -> incomeMinor
                TransactionType.Expense -> expensesMinor
                TransactionType.Transfer -> 0L
            },
        )
    }

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
