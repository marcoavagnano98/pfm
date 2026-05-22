package com.marco.pfm.data.local.mapper

import com.marco.pfm.data.local.entity.TransactionEntity
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import java.time.Instant
import java.time.LocalDate

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    type = runCatching { TransactionType.valueOf(type) }.getOrDefault(TransactionType.Expense),
    amountMinor = amountMinor,
    date = LocalDate.ofEpochDay(dateEpochDay),
    sourceAccountId = sourceAccountId,
    destinationAccountId = destinationAccountId,
    categoryId = categoryId,
    note = note,
    createdAt = Instant.ofEpochMilli(createdAtEpochMillis),
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMillis),
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type.name,
    amountMinor = amountMinor,
    dateEpochDay = date.toEpochDay(),
    sourceAccountId = sourceAccountId,
    destinationAccountId = destinationAccountId,
    categoryId = categoryId,
    note = note,
    createdAtEpochMillis = createdAt.toEpochMilli(),
    updatedAtEpochMillis = updatedAt.toEpochMilli(),
)
