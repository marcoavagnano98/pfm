package com.marco.pfm.data.repository

import com.marco.pfm.data.local.dao.BudgetDao
import com.marco.pfm.data.local.entity.BudgetProfileEntity
import com.marco.pfm.data.local.mapper.toDomain
import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.repository.BudgetRepository
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao,
) : BudgetRepository {
    override fun getBudgetProfile(): Flow<BudgetProfile?> =
        budgetDao.observeBudgetProfile().map { it?.toDomain() }

    override suspend fun saveBudgetProfile(
        monthlyIncomeMinor: Long,
        targetBudgetMinor: Long,
    ) {
        budgetDao.upsertBudgetProfile(
            BudgetProfileEntity(
                monthlyIncomeMinor = monthlyIncomeMinor,
                targetBudgetMinor = targetBudgetMinor,
                updatedAtEpochMillis = Instant.now().toEpochMilli(),
            ),
        )
    }
}
