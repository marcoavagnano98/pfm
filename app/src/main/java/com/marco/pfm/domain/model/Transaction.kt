package com.marco.pfm.domain.model

import java.time.Instant
import java.time.LocalDate

data class Transaction(
    val id: Long = 0,
    val type: TransactionType,
    val amountMinor: Long,
    val date: LocalDate,
    val sourceAccountId: Long?,
    val destinationAccountId: Long?,
    val categoryId: Long?,
    val note: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
