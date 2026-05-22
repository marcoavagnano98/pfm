package com.marco.pfm.domain.model

data class PeriodSummary(
    val months: Int,
    val incomeMinor: Long,
    val expensesMinor: Long,
    val netMinor: Long,
)
