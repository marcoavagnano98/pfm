package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.TransactionType

fun validateTransaction(
    type: TransactionType,
    amountMinor: Long,
    sourceAccountId: Long?,
    destinationAccountId: Long?,
) {
    require(amountMinor > 0L) { "Amount must be greater than zero." }

    when (type) {
        TransactionType.Income -> {
            require(destinationAccountId != null) { "Income requires a destination account." }
            require(sourceAccountId == null) { "Income cannot have a source account." }
        }

        TransactionType.Expense -> {
            require(sourceAccountId != null) { "Expense requires a source account." }
            require(destinationAccountId == null) { "Expense cannot have a destination account." }
        }

        TransactionType.Transfer -> {
            require(sourceAccountId != null) { "Transfer requires a source account." }
            require(destinationAccountId != null) { "Transfer requires a destination account." }
            require(sourceAccountId != destinationAccountId) { "Transfer accounts must be different." }
        }
    }
}
