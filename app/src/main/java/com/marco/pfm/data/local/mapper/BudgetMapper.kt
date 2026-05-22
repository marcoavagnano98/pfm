package com.marco.pfm.data.local.mapper

import com.marco.pfm.data.local.entity.BudgetProfileEntity
import com.marco.pfm.data.local.entity.PlannedExpenseEntity
import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.model.PlannedExpense
import java.time.Instant

fun BudgetProfileEntity.toDomain(): BudgetProfile = BudgetProfile(
    id = id,
    monthlyIncomeMinor = monthlyIncomeMinor,
    targetBudgetMinor = targetBudgetMinor,
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMillis),
)

fun PlannedExpenseEntity.toDomain(): PlannedExpense = PlannedExpense(
    id = id,
    name = name,
    amountMinor = amountMinor,
    isArchived = isArchived,
    createdAt = Instant.ofEpochMilli(createdAtEpochMillis),
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMillis),
)

fun PlannedExpense.toEntity(): PlannedExpenseEntity = PlannedExpenseEntity(
    id = id,
    name = name,
    amountMinor = amountMinor,
    isArchived = isArchived,
    createdAtEpochMillis = createdAt.toEpochMilli(),
    updatedAtEpochMillis = updatedAt.toEpochMilli(),
)
