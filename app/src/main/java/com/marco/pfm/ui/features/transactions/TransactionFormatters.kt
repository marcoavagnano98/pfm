package com.marco.pfm.ui.features.transactions

import com.marco.pfm.domain.model.TransactionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

val TransactionType.label: String
    get() = when (this) {
        TransactionType.Income -> "Income"
        TransactionType.Expense -> "Expense"
        TransactionType.Transfer -> "Transfer"
    }

fun LocalDate.toDisplayDate(): String = format(DateFormatter)

fun parseDisplayDate(value: String): LocalDate? =
    runCatching { LocalDate.parse(value.trim(), DateFormatter) }.getOrNull()
