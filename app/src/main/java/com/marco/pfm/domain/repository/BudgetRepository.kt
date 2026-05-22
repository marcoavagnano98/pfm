package com.marco.pfm.domain.repository

import com.marco.pfm.domain.model.BudgetProfile
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getBudgetProfile(): Flow<BudgetProfile?>

    suspend fun saveBudgetProfile(
        monthlyIncomeMinor: Long,
        targetBudgetMinor: Long,
    )
}
