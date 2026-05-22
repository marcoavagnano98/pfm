package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.HomeSummary
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.AccountRepository
import com.marco.pfm.domain.repository.BudgetRepository
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetHomeSummaryUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(date: LocalDate = LocalDate.now()): Flow<HomeSummary> {
        val startDate = date.withDayOfMonth(1)
        return combine(
            accountRepository.getAccounts(),
            budgetRepository.getBudgetProfile(),
            transactionRepository.getAccountBalanceDeltas(),
            transactionRepository.getAmountSumByTypeBetween(TransactionType.Income, startDate, date),
            transactionRepository.getAmountSumByTypeBetween(TransactionType.Expense, startDate, date),
        ) { accounts, budgetProfile, balanceDeltas, currentMonthIncome, currentMonthExpenses ->
            HomeSummary(
                totalBalanceMinor = accounts.sumOf { account ->
                    account.initialBalanceMinor + (balanceDeltas[account.id] ?: 0L)
                },
                remainingBudgetMinor = (budgetProfile?.targetBudgetMinor ?: 0L) - currentMonthExpenses,
                currentMonthIncomeMinor = currentMonthIncome,
                currentMonthExpensesMinor = currentMonthExpenses,
                currentMonthNetMinor = currentMonthIncome - currentMonthExpenses,
            )
        }
    }
}
