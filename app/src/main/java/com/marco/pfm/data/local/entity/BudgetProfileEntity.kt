package com.marco.pfm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_profile")
data class BudgetProfileEntity(
    @PrimaryKey
    val id: Long = 1,
    val monthlyIncomeMinor: Long,
    val targetBudgetMinor: Long,
    val updatedAtEpochMillis: Long,
)
