package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.BudgetRepository
import javax.inject.Inject

class SaveBudgetProfileUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
) {
    suspend operator fun invoke(
        monthlyIncomeMinor: Long,
        targetBudgetMinor: Long,
    ) {
        require(monthlyIncomeMinor >= 0L) { "Monthly income cannot be negative." }
        require(targetBudgetMinor >= 0L) { "Target budget cannot be negative." }
        budgetRepository.saveBudgetProfile(
            monthlyIncomeMinor = monthlyIncomeMinor,
            targetBudgetMinor = targetBudgetMinor,
        )
    }
}
