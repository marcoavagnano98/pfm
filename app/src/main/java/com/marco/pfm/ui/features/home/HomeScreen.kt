package com.marco.pfm.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marco.pfm.ui.common.formatMinorAmount

@Composable
fun HomeScreen(
    onCreateTransaction: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
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
                    text = "Home",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "Current balance, budget, and recent period summary.",
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            item {
                Button(
                    onClick = onCreateTransaction,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                    )
                    Text(
                        text = "Add transaction",
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SummaryCard(
                        label = "Total balance",
                        value = formatMinorAmount(uiState.summary.totalBalanceMinor),
                    )
                    SummaryCard(
                        label = "Remaining budget",
                        value = formatMinorAmount(uiState.summary.remainingBudgetMinor),
                    )
                    SummaryCard(
                        label = "Current month net",
                        value = formatMinorAmount(uiState.summary.currentMonthNetMinor),
                    )
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SummaryCard(
                        label = "Income",
                        value = formatMinorAmount(uiState.summary.currentMonthIncomeMinor),
                        modifier = Modifier.weight(1f),
                    )
                    SummaryCard(
                        label = "Expenses",
                        value = formatMinorAmount(uiState.summary.currentMonthExpensesMinor),
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            item {
                PeriodSelector(
                    selectedPeriodMonths = uiState.selectedPeriodMonths,
                    onSelected = viewModel::selectPeriod,
                )
            }

            item {
                PeriodSummaryCard(uiState = uiState)
            }

            item {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SummaryCard(
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
private fun PeriodSelector(
    selectedPeriodMonths: Int,
    onSelected: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Period",
            style = MaterialTheme.typography.titleMedium,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(1, 2, 3, 12).forEach { months ->
                FilterChip(
                    selected = selectedPeriodMonths == months,
                    onClick = { onSelected(months) },
                    label = { Text("${months}m") },
                )
            }
        }
    }
}

@Composable
private fun PeriodSummaryCard(uiState: HomeUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "${uiState.periodSummary.months} month summary",
                style = MaterialTheme.typography.titleMedium,
            )
            MetricLine(
                label = "Income",
                value = formatMinorAmount(uiState.periodSummary.incomeMinor),
            )
            MetricLine(
                label = "Expenses",
                value = formatMinorAmount(uiState.periodSummary.expensesMinor),
            )
            MetricLine(
                label = "Net",
                value = formatMinorAmount(uiState.periodSummary.netMinor),
            )
        }
    }
}

@Composable
private fun MetricLine(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
