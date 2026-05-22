package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.AccountRepository
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

class GetHomeSummaryUseCaseTest {
    @Test
    fun `home summary uses aggregate balances and monthly totals`() = runTest {
        val summary = GetHomeSummaryUseCase(
            accountRepository = FakeAccountRepository(),
            budgetRepository = FakeHomeBudgetRepository(),
            transactionRepository = FakeHomeTransactionRepository(),
        ).invoke(date = LocalDate.of(2026, 5, 22)).first()

        assertEquals(2_650L, summary.totalBalanceMinor)
        assertEquals(700L, summary.remainingBudgetMinor)
        assertEquals(1_200L, summary.currentMonthIncomeMinor)
        assertEquals(300L, summary.currentMonthExpensesMinor)
        assertEquals(900L, summary.currentMonthNetMinor)
    }
}

private class FakeAccountRepository : AccountRepository {
    override fun getAccounts(includeArchived: Boolean): Flow<List<Account>> =
        flowOf(
            listOf(
                account(id = 1, initialBalanceMinor = 1_000),
                account(id = 2, initialBalanceMinor = 2_000),
            ),
        )

    override suspend fun getAccount(accountId: Long): Account? = null

    override suspend fun createAccount(
        name: String,
        type: AccountType,
        initialBalanceMinor: Long,
    ) = Unit

    override suspend fun updateAccount(account: Account) = Unit

    override suspend fun archiveAccount(accountId: Long) = Unit

    private fun account(
        id: Long,
        initialBalanceMinor: Long,
    ): Account = Account(
        id = id,
        name = "Account $id",
        type = AccountType.Bank,
        initialBalanceMinor = initialBalanceMinor,
        isArchived = false,
        createdAt = Instant.EPOCH,
        updatedAt = Instant.EPOCH,
    )
}

private class FakeHomeBudgetRepository : BudgetRepository {
    override fun getBudgetProfile(): Flow<BudgetProfile?> =
        flowOf(
            BudgetProfile(
                monthlyIncomeMinor = 0,
                targetBudgetMinor = 1_000,
                updatedAt = Instant.EPOCH,
            ),
        )

    override suspend fun saveBudgetProfile(
        monthlyIncomeMinor: Long,
        targetBudgetMinor: Long,
    ) = Unit
}

private class FakeHomeTransactionRepository : TransactionRepository {
    override fun getTransactionsBetween(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>> = flowOf(emptyList())

    override fun getAmountSumByTypeBetween(
        type: TransactionType,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<Long> =
        flowOf(
            when (type) {
                TransactionType.Income -> 1_200L
                TransactionType.Expense -> 300L
                TransactionType.Transfer -> 0L
            },
        )

    override fun getAccountBalanceDeltas(): Flow<Map<Long, Long>> =
        flowOf(mapOf(1L to 250L, 2L to -600L))

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
