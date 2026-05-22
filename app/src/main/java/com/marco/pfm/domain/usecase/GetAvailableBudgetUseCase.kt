package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.BudgetRepository
import com.marco.pfm.domain.repository.PlannedExpenseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAvailableBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val plannedExpenseRepository: PlannedExpenseRepository,
) {
    operator fun invoke(): Flow<Long> = combine(
        budgetRepository.getBudgetProfile(),
        plannedExpenseRepository.getPlannedExpenses(),
    ) { profile, plannedExpenses ->
        calculateAvailableBudget(
            monthlyIncomeMinor = profile?.monthlyIncomeMinor ?: 0L,
            plannedExpensesMinor = plannedExpenses.sumOf { it.amountMinor },
        )
    }
}
