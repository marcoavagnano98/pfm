package com.marco.pfm.data.repository

import com.marco.pfm.data.local.dao.PlannedExpenseDao
import com.marco.pfm.data.local.entity.PlannedExpenseEntity
import com.marco.pfm.data.local.mapper.toDomain
import com.marco.pfm.data.local.mapper.toEntity
import com.marco.pfm.domain.model.PlannedExpense
import com.marco.pfm.domain.repository.PlannedExpenseRepository
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultPlannedExpenseRepository @Inject constructor(
    private val plannedExpenseDao: PlannedExpenseDao,
) : PlannedExpenseRepository {
    override fun getPlannedExpenses(includeArchived: Boolean): Flow<List<PlannedExpense>> =
        plannedExpenseDao.observePlannedExpenses(includeArchived).map { plannedExpenses ->
            plannedExpenses.map(PlannedExpenseEntity::toDomain)
        }

    override suspend fun createPlannedExpense(
        name: String,
        amountMinor: Long,
    ) {
        val now = Instant.now().toEpochMilli()
        plannedExpenseDao.insertPlannedExpense(
            PlannedExpenseEntity(
                name = name,
                amountMinor = amountMinor,
                isArchived = false,
                createdAtEpochMillis = now,
                updatedAtEpochMillis = now,
            ),
        )
    }

    override suspend fun updatePlannedExpense(plannedExpense: PlannedExpense) {
        plannedExpenseDao.updatePlannedExpense(
            plannedExpense.copy(updatedAt = Instant.now()).toEntity(),
        )
    }
}
