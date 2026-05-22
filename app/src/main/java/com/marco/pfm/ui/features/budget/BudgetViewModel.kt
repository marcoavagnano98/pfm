package com.marco.pfm.ui.features.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.pfm.domain.model.BudgetProfile
import com.marco.pfm.domain.model.PlannedExpense
import com.marco.pfm.domain.usecase.CreatePlannedExpenseUseCase
import com.marco.pfm.domain.usecase.GetAvailableBudgetUseCase
import com.marco.pfm.domain.usecase.GetBudgetProfileUseCase
import com.marco.pfm.domain.usecase.GetPlannedExpensesUseCase
import com.marco.pfm.domain.usecase.GetRemainingBudgetUseCase
import com.marco.pfm.domain.usecase.SaveBudgetProfileUseCase
import com.marco.pfm.domain.usecase.UpdatePlannedExpenseUseCase
import com.marco.pfm.ui.common.formatInputMinorAmount
import com.marco.pfm.ui.common.parseMinorAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BudgetUiState(
    val profile: BudgetProfile? = null,
    val plannedExpenses: List<PlannedExpense> = emptyList(),
    val availableBudgetMinor: Long = 0,
    val remainingBudgetMinor: Long = 0,
    val monthlyIncome: String = "0",
    val targetBudget: String = "0",
    val profileError: String? = null,
    val plannedExpenseId: Long? = null,
    val plannedExpenseName: String = "",
    val plannedExpenseAmount: String = "",
    val plannedExpenseError: String? = null,
) {
    val isEditingPlannedExpense: Boolean = plannedExpenseId != null
    val plannedExpensesTotalMinor: Long = plannedExpenses.sumOf { it.amountMinor }
}

@HiltViewModel
class BudgetViewModel @Inject constructor(
    getBudgetProfile: GetBudgetProfileUseCase,
    getPlannedExpenses: GetPlannedExpensesUseCase,
    getAvailableBudget: GetAvailableBudgetUseCase,
    getRemainingBudget: GetRemainingBudgetUseCase,
    private val saveBudgetProfile: SaveBudgetProfileUseCase,
    private val createPlannedExpense: CreatePlannedExpenseUseCase,
    private val updatePlannedExpense: UpdatePlannedExpenseUseCase,
) : ViewModel() {
    private val edits = MutableStateFlow(BudgetEdits())

    val uiState: StateFlow<BudgetUiState> = combine(
        getBudgetProfile(),
        getPlannedExpenses(),
        getAvailableBudget(),
        getRemainingBudget(),
        edits,
    ) { profile, plannedExpenses, availableBudget, remainingBudget, edits ->
        BudgetUiState(
            profile = profile,
            plannedExpenses = plannedExpenses,
            availableBudgetMinor = availableBudget,
            remainingBudgetMinor = remainingBudget,
            monthlyIncome = edits.monthlyIncome ?: formatInputMinorAmount(profile?.monthlyIncomeMinor ?: 0L),
            targetBudget = edits.targetBudget ?: formatInputMinorAmount(profile?.targetBudgetMinor ?: 0L),
            profileError = edits.profileError,
            plannedExpenseId = edits.plannedExpenseId,
            plannedExpenseName = edits.plannedExpenseName,
            plannedExpenseAmount = edits.plannedExpenseAmount,
            plannedExpenseError = edits.plannedExpenseError,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BudgetUiState(),
    )

    fun onMonthlyIncomeChanged(value: String) {
        if (isAmountInput(value)) {
            edits.update {
                it.copy(
                    monthlyIncome = value,
                    profileError = null,
                )
            }
        }
    }

    fun onTargetBudgetChanged(value: String) {
        if (isAmountInput(value)) {
            edits.update {
                it.copy(
                    targetBudget = value,
                    profileError = null,
                )
            }
        }
    }

    fun saveProfile() {
        val state = uiState.value
        val monthlyIncome = parseMinorAmount(state.monthlyIncome)
        val targetBudget = parseMinorAmount(state.targetBudget)
        if (monthlyIncome == null || monthlyIncome < 0L || targetBudget == null || targetBudget < 0L) {
            edits.update { it.copy(profileError = "Enter valid non-negative amounts.") }
            return
        }

        viewModelScope.launch {
            saveBudgetProfile(
                monthlyIncomeMinor = monthlyIncome,
                targetBudgetMinor = targetBudget,
            )
            edits.update {
                it.copy(
                    monthlyIncome = null,
                    targetBudget = null,
                    profileError = null,
                )
            }
        }
    }

    fun onPlannedExpenseNameChanged(value: String) {
        if (value.length <= 60) {
            edits.update {
                it.copy(
                    plannedExpenseName = value,
                    plannedExpenseError = null,
                )
            }
        }
    }

    fun onPlannedExpenseAmountChanged(value: String) {
        if (isAmountInput(value)) {
            edits.update {
                it.copy(
                    plannedExpenseAmount = value,
                    plannedExpenseError = null,
                )
            }
        }
    }

    fun editPlannedExpense(plannedExpense: PlannedExpense) {
        edits.update {
            it.copy(
                plannedExpenseId = plannedExpense.id,
                plannedExpenseName = plannedExpense.name,
                plannedExpenseAmount = formatInputMinorAmount(plannedExpense.amountMinor),
                plannedExpenseError = null,
            )
        }
    }

    fun clearPlannedExpenseForm() {
        edits.update {
            it.copy(
                plannedExpenseId = null,
                plannedExpenseName = "",
                plannedExpenseAmount = "",
                plannedExpenseError = null,
            )
        }
    }

    fun savePlannedExpense() {
        val state = uiState.value
        val amount = parseMinorAmount(state.plannedExpenseAmount)
        val name = state.plannedExpenseName.trim()
        if (name.isBlank() || amount == null || amount < 0L) {
            edits.update { it.copy(plannedExpenseError = "Enter a name and valid amount.") }
            return
        }

        viewModelScope.launch {
            val existing = state.plannedExpenses.firstOrNull { it.id == state.plannedExpenseId }
            if (existing == null) {
                createPlannedExpense(
                    name = name,
                    amountMinor = amount,
                )
            } else {
                updatePlannedExpense(
                    existing.copy(
                        name = name,
                        amountMinor = amount,
                    ),
                )
            }
            clearPlannedExpenseForm()
        }
    }

    private fun isAmountInput(value: String): Boolean =
        value.length <= 16 && value.all { it.isDigit() || it == '.' || it == ',' }
}

private data class BudgetEdits(
    val monthlyIncome: String? = null,
    val targetBudget: String? = null,
    val profileError: String? = null,
    val plannedExpenseId: Long? = null,
    val plannedExpenseName: String = "",
    val plannedExpenseAmount: String = "",
    val plannedExpenseError: String? = null,
)
