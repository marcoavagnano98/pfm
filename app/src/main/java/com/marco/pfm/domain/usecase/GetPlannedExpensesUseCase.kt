package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.PlannedExpense
import com.marco.pfm.domain.repository.PlannedExpenseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPlannedExpensesUseCase @Inject constructor(
    private val plannedExpenseRepository: PlannedExpenseRepository,
) {
    operator fun invoke(includeArchived: Boolean = false): Flow<List<PlannedExpense>> =
        plannedExpenseRepository.getPlannedExpenses(includeArchived)
}
