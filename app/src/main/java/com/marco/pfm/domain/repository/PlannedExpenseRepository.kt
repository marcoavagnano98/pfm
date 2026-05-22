package com.marco.pfm.domain.repository

import com.marco.pfm.domain.model.PlannedExpense
import kotlinx.coroutines.flow.Flow

interface PlannedExpenseRepository {
    fun getPlannedExpenses(includeArchived: Boolean = false): Flow<List<PlannedExpense>>

    suspend fun createPlannedExpense(
        name: String,
        amountMinor: Long,
    )

    suspend fun updatePlannedExpense(plannedExpense: PlannedExpense)
}
