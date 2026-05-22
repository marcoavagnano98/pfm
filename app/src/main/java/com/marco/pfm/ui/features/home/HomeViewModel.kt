package com.marco.pfm.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.HomeSummary
import com.marco.pfm.domain.model.PeriodSummary
import com.marco.pfm.domain.usecase.GetHomeSummaryUseCase
import com.marco.pfm.domain.usecase.GetPeriodSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val summary: HomeSummary = HomeSummary(
        totalBalanceMinor = 0,
        remainingBudgetMinor = 0,
        currentMonthIncomeMinor = 0,
        currentMonthExpensesMinor = 0,
        currentMonthNetMinor = 0,
    ),
    val periodSummary: PeriodSummary = PeriodSummary(
        months = 1,
        incomeMinor = 0,
        expensesMinor = 0,
        netMinor = 0,
    ),
    val selectedPeriodMonths: Int = 1,
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeSummary: GetHomeSummaryUseCase,
    getPeriodSummary: GetPeriodSummaryUseCase,
) : ViewModel() {
    private val selectedPeriodMonths = MutableStateFlow(1)

    val uiState: StateFlow<HomeUiState> = combine(
        getHomeSummary(),
        selectedPeriodMonths.flatMapLatest { months -> getPeriodSummary(months) },
        selectedPeriodMonths,
    ) { summary, periodSummary, periodMonths ->
        HomeUiState(
            summary = summary,
            periodSummary = periodSummary,
            selectedPeriodMonths = periodMonths,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(),
    )

    fun selectPeriod(months: Int) {
        if (months in supportedPeriods) {
            selectedPeriodMonths.value = months
        }
    }

    private companion object {
        val supportedPeriods = setOf(1, 2, 3, 12)
    }
}
