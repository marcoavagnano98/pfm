package com.marco.pfm.domain.model

import java.time.Instant

data class Account(
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val initialBalanceMinor: Long,
    val isArchived: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
)
