package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import java.time.LocalDate

fun calculateAccountCurrentBalance(
    account: Account,
    transactions: List<Transaction>,
): Long {
    val transactionDelta = transactions.sumOf { transaction ->
        when (transaction.type) {
            TransactionType.Income ->
                if (transaction.destinationAccountId == account.id) transaction.amountMinor else 0L

            TransactionType.Expense ->
                if (transaction.sourceAccountId == account.id) -transaction.amountMinor else 0L

            TransactionType.Transfer -> when (account.id) {
                transaction.sourceAccountId -> -transaction.amountMinor
                transaction.destinationAccountId -> transaction.amountMinor
                else -> 0L
            }
        }
    }
    return account.initialBalanceMinor + transactionDelta
}

fun calculateAvailableBudget(
    monthlyIncomeMinor: Long,
    plannedExpensesMinor: Long,
): Long = monthlyIncomeMinor - plannedExpensesMinor

fun calculateRemainingBudget(
    targetBudgetMinor: Long,
    monthlyExpenseTransactions: List<Transaction>,
): Long = targetBudgetMinor - monthlyExpenseTransactions
    .filter { it.type == TransactionType.Expense }
    .sumOf { it.amountMinor }

fun Transaction.isInMonth(date: LocalDate): Boolean =
    this.date.year == date.year && this.date.month == date.month
