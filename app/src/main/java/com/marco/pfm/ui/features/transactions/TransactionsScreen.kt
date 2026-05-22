package com.marco.pfm.ui.features.transactions

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.Transaction
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.ui.common.formatMinorAmount

@Composable
fun TransactionsScreen(
    onCreateTransaction: () -> Unit,
    onEditTransaction: (Long) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateTransaction,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add transaction",
                    )
                },
                text = { Text("Transaction") },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "Income, expenses, and transfers in chronological order.",
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(1, 2, 3, 12).forEach { months ->
                    FilterChip(
                        selected = uiState.selectedPeriodMonths == months,
                        onClick = { viewModel.selectPeriod(months) },
                        label = { Text(if (months == 1) "1 month" else "$months months") },
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (uiState.transactions.isEmpty()) {
                EmptyTransactions(onCreateTransaction = onCreateTransaction)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = uiState.transactions,
                        key = { it.id },
                    ) { transaction ->
                        TransactionRow(
                            transaction = transaction,
                            accounts = uiState.accounts,
                            categories = uiState.categories,
                            onClick = { onEditTransaction(transaction.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTransactions(onCreateTransaction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "No transaction yet",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Add an income, expense, or transfer when you are ready.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(onClick = onCreateTransaction) {
            Text("Add transaction")
        }
    }
}

@Composable
private fun TransactionRow(
    transaction: Transaction,
    accounts: List<Account>,
    categories: List<Category>,
    onClick: () -> Unit,
) {
    val sourceName = accounts.firstOrNull { it.id == transaction.sourceAccountId }?.name
    val destinationName = accounts.firstOrNull { it.id == transaction.destinationAccountId }?.name
    val categoryName = categories.firstOrNull { it.id == transaction.categoryId }?.name
    val accountLabel = when (transaction.type) {
        TransactionType.Income -> destinationName ?: "Destination account"
        TransactionType.Expense -> sourceName ?: "Source account"
        TransactionType.Transfer -> "${sourceName ?: "Source"} -> ${destinationName ?: "Destination"}"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = transaction.type.label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = formatMinorAmount(transaction.amountMinor),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Text(
                text = accountLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = onClick,
                    label = { Text(transaction.date.toDisplayDate()) },
                )
                categoryName?.let {
                    AssistChip(
                        onClick = onClick,
                        label = { Text(it) },
                    )
                }
            }
            transaction.note?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
