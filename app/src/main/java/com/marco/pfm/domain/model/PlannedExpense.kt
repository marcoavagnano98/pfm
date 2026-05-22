package com.marco.pfm.domain.model

import java.time.Instant

data class PlannedExpense(
    val id: Long = 0,
    val name: String,
    val amountMinor: Long,
    val isArchived: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
)
