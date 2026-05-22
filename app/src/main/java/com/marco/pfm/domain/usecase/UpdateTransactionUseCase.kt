package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(transaction: Transaction) {
        validateTransaction(
            type = transaction.type,
            amountMinor = transaction.amountMinor,
            sourceAccountId = transaction.sourceAccountId,
            destinationAccountId = transaction.destinationAccountId,
        )
        transactionRepository.updateTransaction(
            transaction.copy(note = transaction.note?.trim()?.ifBlank { null }),
        )
    }
}
