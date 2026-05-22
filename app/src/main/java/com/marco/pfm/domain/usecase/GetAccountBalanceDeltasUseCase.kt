package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.repository.TransactionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAccountBalanceDeltasUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(): Flow<Map<Long, Long>> =
        transactionRepository.getAccountBalanceDeltas()
}
