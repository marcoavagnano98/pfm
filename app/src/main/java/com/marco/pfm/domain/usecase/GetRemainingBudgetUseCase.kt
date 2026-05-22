package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.BudgetRepository
import com.marco.pfm.domain.repository.TransactionRepository
import com.marco.pfm.domain.model.TransactionType
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetRemainingBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(date: LocalDate = LocalDate.now()): Flow<Long> {
        val startDate = date.withDayOfMonth(1)
        return combine(
            budgetRepository.getBudgetProfile(),
            transactionRepository.getAmountSumByTypeBetween(TransactionType.Expense, startDate, date),
        ) { profile, currentMonthExpenses ->
            (profile?.targetBudgetMinor ?: 0L) - currentMonthExpenses
        }
    }
}
