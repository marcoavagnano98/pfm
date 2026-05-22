package com.marco.pfm.domain.model

import java.time.Instant

data class BudgetProfile(
    val id: Long = 1,
    val monthlyIncomeMinor: Long,
    val targetBudgetMinor: Long,
    val updatedAt: Instant,
)
