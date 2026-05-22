package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.PlannedExpenseRepository
import javax.inject.Inject

class CreatePlannedExpenseUseCase @Inject constructor(
    private val plannedExpenseRepository: PlannedExpenseRepository,
) {
    suspend operator fun invoke(
        name: String,
        amountMinor: Long,
    ) {
        require(name.isNotBlank()) { "Planned expense name is required." }
        require(amountMinor >= 0L) { "Planned expense amount cannot be negative." }
        plannedExpenseRepository.createPlannedExpense(
            name = name.trim(),
            amountMinor = amountMinor,
        )
    }
}
