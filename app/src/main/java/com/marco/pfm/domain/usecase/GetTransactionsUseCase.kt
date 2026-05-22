package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(
        months: Int,
        today: LocalDate = LocalDate.now(),
    ): Flow<List<Transaction>> {
        require(months in supportedPeriods) { "Unsupported transaction period: $months" }
        val startDate = today.withDayOfMonth(1).minusMonths((months - 1).toLong())
        return transactionRepository.getTransactionsBetween(startDate, today)
    }

    private companion object {
        val supportedPeriods = setOf(1, 2, 3, 12)
    }
}
