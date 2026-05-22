package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import java.time.Instant
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FinanceCalculationsTest {
    @Test
    fun `account balance includes income expense and transfers`() {
        val account = account(id = 1, initialBalanceMinor = 1_000)
        val transactions = listOf(
            transaction(type = TransactionType.Income, amountMinor = 500, destinationAccountId = 1),
            transaction(type = TransactionType.Expense, amountMinor = 200, sourceAccountId = 1),
            transaction(type = TransactionType.Transfer, amountMinor = 100, sourceAccountId = 1, destinationAccountId = 2),
            transaction(type = TransactionType.Transfer, amountMinor = 300, sourceAccountId = 2, destinationAccountId = 1),
        )

        assertEquals(1_500L, calculateAccountCurrentBalance(account, transactions))
    }

    @Test
    fun `available budget subtracts planned expenses from monthly income`() {
        assertEquals(700L, calculateAvailableBudget(monthlyIncomeMinor = 1_000, plannedExpensesMinor = 300))
    }

    @Test
    fun `remaining budget subtracts only expenses from target budget`() {
        val transactions = listOf(
            transaction(type = TransactionType.Income, amountMinor = 1_000, destinationAccountId = 1),
            transaction(type = TransactionType.Expense, amountMinor = 250, sourceAccountId = 1),
            transaction(type = TransactionType.Transfer, amountMinor = 100, sourceAccountId = 1, destinationAccountId = 2),
        )

        assertEquals(750L, calculateRemainingBudget(targetBudgetMinor = 1_000, monthlyExpenseTransactions = transactions))
    }

    @Test
    fun `month filter matches same year and month`() {
        val transaction = transaction(
            type = TransactionType.Expense,
            amountMinor = 100,
            sourceAccountId = 1,
            date = LocalDate.of(2026, 5, 12),
        )

        assertTrue(transaction.isInMonth(LocalDate.of(2026, 5, 21)))
        assertFalse(transaction.isInMonth(LocalDate.of(2026, 6, 1)))
    }

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

    private fun transaction(
        type: TransactionType,
        amountMinor: Long,
        sourceAccountId: Long? = null,
        destinationAccountId: Long? = null,
        date: LocalDate = LocalDate.of(2026, 5, 21),
    ): Transaction = Transaction(
        id = 0,
        type = type,
        amountMinor = amountMinor,
        date = date,
        sourceAccountId = sourceAccountId,
        destinationAccountId = destinationAccountId,
        categoryId = null,
        note = null,
        createdAt = Instant.EPOCH,
        updatedAt = Instant.EPOCH,
    )
}
