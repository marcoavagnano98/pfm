package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.repository.BudgetRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBudgetProfileUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
) {
    operator fun invoke(): Flow<BudgetProfile?> = budgetRepository.getBudgetProfile()
}
