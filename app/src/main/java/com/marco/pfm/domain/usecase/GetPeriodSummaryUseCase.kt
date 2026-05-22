package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.PeriodSummary
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.TransactionRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPeriodSummaryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(
        months: Int,
        today: LocalDate = LocalDate.now(),
    ): Flow<PeriodSummary> {
        require(months in supportedMonths) { "Unsupported summary period." }
        val startDate = today.withDayOfMonth(1).minusMonths((months - 1).toLong())
        return combine(
            transactionRepository.getAmountSumByTypeBetween(TransactionType.Income, startDate, today),
            transactionRepository.getAmountSumByTypeBetween(TransactionType.Expense, startDate, today),
        ) { income, expenses ->
            PeriodSummary(
                months = months,
                incomeMinor = income,
                expensesMinor = expenses,
                netMinor = income - expenses,
            )
        }
    }

    private companion object {
        val supportedMonths = setOf(1, 2, 3, 12)
    }
}
