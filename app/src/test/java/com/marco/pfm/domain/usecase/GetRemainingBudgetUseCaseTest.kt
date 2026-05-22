package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.BudgetRepository
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRemainingBudgetUseCaseTest {
    @Test
    fun `remaining budget uses current month expense aggregate`() = runTest {
        val transactionRepository = FakeRemainingBudgetTransactionRepository(expensesMinor = 350)
        val remainingBudget = GetRemainingBudgetUseCase(
            budgetRepository = FakeBudgetRepository(targetBudgetMinor = 1_000),
            transactionRepository = transactionRepository,
        ).invoke(date = LocalDate.of(2026, 5, 22)).first()

        assertEquals(650L, remainingBudget)
        assertEquals(
            LocalDate.of(2026, 5, 1) to LocalDate.of(2026, 5, 22),
            transactionRepository.lastRange,
        )
    }
}

private class FakeBudgetRepository(
    private val targetBudgetMinor: Long,
) : BudgetRepository {
    override fun getBudgetProfile(): Flow<BudgetProfile?> =
        flowOf(
            BudgetProfile(
                monthlyIncomeMinor = 0,
                targetBudgetMinor = targetBudgetMinor,
                updatedAt = Instant.EPOCH,
            ),
        )

    override suspend fun saveBudgetProfile(
        monthlyIncomeMinor: Long,
        targetBudgetMinor: Long,
    ) = Unit
}

private class FakeRemainingBudgetTransactionRepository(
    private val expensesMinor: Long,
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
        return flowOf(if (type == TransactionType.Expense) expensesMinor else 0L)
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
