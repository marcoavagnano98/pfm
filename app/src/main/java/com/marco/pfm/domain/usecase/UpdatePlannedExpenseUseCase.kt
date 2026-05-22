package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.PlannedExpense
import com.marco.pfm.domain.repository.PlannedExpenseRepository
import javax.inject.Inject

class UpdatePlannedExpenseUseCase @Inject constructor(
    private val plannedExpenseRepository: PlannedExpenseRepository,
) {
    suspend operator fun invoke(plannedExpense: PlannedExpense) {
        require(plannedExpense.name.isNotBlank()) { "Planned expense name is required." }
        require(plannedExpense.amountMinor >= 0L) { "Planned expense amount cannot be negative." }
        plannedExpenseRepository.updatePlannedExpense(
            plannedExpense.copy(name = plannedExpense.name.trim()),
        )
    }
}
