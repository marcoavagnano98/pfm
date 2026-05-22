package com.marco.pfm.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "planned_expenses",
    indices = [
        Index(value = ["isArchived"]),
        Index(value = ["name"]),
    ],
)
data class PlannedExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amountMinor: Long,
    val isArchived: Boolean,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
)
