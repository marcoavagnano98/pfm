package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        type: TransactionType,
        amountMinor: Long,
        date: LocalDate,
        sourceAccountId: Long?,
        destinationAccountId: Long?,
        categoryId: Long?,
        note: String?,
    ) {
        validateTransaction(
            type = type,
            amountMinor = amountMinor,
            sourceAccountId = sourceAccountId,
            destinationAccountId = destinationAccountId,
        )
        transactionRepository.createTransaction(
            type = type,
            amountMinor = amountMinor,
            date = date,
            sourceAccountId = sourceAccountId,
            destinationAccountId = destinationAccountId,
            categoryId = categoryId,
            note = note?.trim()?.ifBlank { null },
        )
    }
}
