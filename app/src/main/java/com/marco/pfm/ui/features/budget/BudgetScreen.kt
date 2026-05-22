package com.marco.pfm.ui.features.budget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marco.pfm.domain.model.PlannedExpense
import com.marco.pfm.ui.common.formatMinorAmount

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text(
                    text = "Budget",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "Monthly income, target, planned expenses, and current remaining budget.",
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            item {
                BudgetMetrics(uiState)
            }

            item {
                BudgetProfileForm(
                    uiState = uiState,
                    onMonthlyIncomeChanged = viewModel::onMonthlyIncomeChanged,
                    onTargetBudgetChanged = viewModel::onTargetBudgetChanged,
                    onSave = viewModel::saveProfile,
                )
            }

            item {
                PlannedExpenseForm(
                    uiState = uiState,
                    onNameChanged = viewModel::onPlannedExpenseNameChanged,
                    onAmountChanged = viewModel::onPlannedExpenseAmountChanged,
                    onSave = viewModel::savePlannedExpense,
                    onCancel = viewModel::clearPlannedExpenseForm,
                )
            }

            if (uiState.plannedExpenses.isEmpty()) {
                item {
                    Text(
                        text = "No planned expense yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                items(
                    items = uiState.plannedExpenses,
                    key = { it.id },
                ) { plannedExpense ->
                    PlannedExpenseRow(
                        plannedExpense = plannedExpense,
                        onClick = { viewModel.editPlannedExpense(plannedExpense) },
                    )
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun BudgetMetrics(uiState: BudgetUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        MetricCard(
            label = "Available budget",
            value = formatMinorAmount(uiState.availableBudgetMinor),
        )
        MetricCard(
            label = "Remaining budget",
            value = formatMinorAmount(uiState.remainingBudgetMinor),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                label = "Planned",
                value = formatMinorAmount(uiState.plannedExpensesTotalMinor),
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                label = "Target",
                value = formatMinorAmount(uiState.profile?.targetBudgetMinor ?: 0L),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun BudgetProfileForm(
    uiState: BudgetUiState,
    onMonthlyIncomeChanged: (String) -> Unit,
    onTargetBudgetChanged: (String) -> Unit,
    onSave: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Monthly profile",
                style = MaterialTheme.typography.titleMedium,
            )
            OutlinedTextField(
                value = uiState.monthlyIncome,
                onValueChange = onMonthlyIncomeChanged,
                label = { Text("Monthly income") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = uiState.targetBudget,
                onValueChange = onTargetBudgetChanged,
                label = { Text("Target budget") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
            )
            uiState.profileError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Save profile")
            }
        }
    }
}

@Composable
private fun PlannedExpenseForm(
    uiState: BudgetUiState,
    onNameChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = if (uiState.isEditingPlannedExpense) "Edit planned expense" else "New planned expense",
                style = MaterialTheme.typography.titleMedium,
            )
            OutlinedTextField(
                value = uiState.plannedExpenseName,
                onValueChange = onNameChanged,
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = uiState.plannedExpenseAmount,
                onValueChange = onAmountChanged,
                label = { Text("Amount") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
            )
            uiState.plannedExpenseError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(if (uiState.isEditingPlannedExpense) "Update planned expense" else "Add planned expense")
            }
            if (uiState.isEditingPlannedExpense) {
                TextButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Cancel edit")
                }
            }
        }
    }
}

@Composable
private fun PlannedExpenseRow(
    plannedExpense: PlannedExpense,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = plannedExpense.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = formatMinorAmount(plannedExpense.amountMinor),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
