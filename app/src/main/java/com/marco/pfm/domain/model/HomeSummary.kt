package com.marco.pfm.domain.model

data class HomeSummary(
    val totalBalanceMinor: Long,
    val remainingBudgetMinor: Long,
    val currentMonthIncomeMinor: Long,
    val currentMonthExpensesMinor: Long,
    val currentMonthNetMinor: Long,
)
