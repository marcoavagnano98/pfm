package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(transactionId: Long): Transaction? =
        transactionRepository.getTransaction(transactionId)
}
