package com.marco.pfm.ui.features.accounts

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marco.pfm.ui.common.formatMinorAmount

@Composable
fun AccountsScreen(
    onCreateAccount: () -> Unit,
    onEditAccount: (Long) -> Unit,
    viewModel: AccountsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateAccount,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add account",
                    )
                },
                text = { Text("Account") },
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
                text = "Accounts",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "Financial positions saved on this device.",
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(16.dp))

            if (uiState.accounts.isEmpty()) {
                EmptyAccounts(onCreateAccount = onCreateAccount)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = uiState.accounts,
                        key = { it.account.id },
                    ) { item ->
                        AccountRow(
                            item = item,
                            onClick = { onEditAccount(item.account.id) },
                            onArchive = { viewModel.archiveAccount(item.account.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyAccounts(onCreateAccount: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "No account yet",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Add cash, bank, savings, or another position to start tracking balances.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(onClick = onCreateAccount) {
            Text("Add account")
        }
    }
}

@Composable
private fun AccountRow(
    item: AccountBalanceItem,
    onClick: () -> Unit,
    onArchive: () -> Unit,
) {
    val account = item.account
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(
                        onClick = onClick,
                        label = { Text(account.type.label) },
                    )
                    if (account.isArchived) {
                        AssistChip(
                            onClick = onClick,
                            label = { Text("Archived") },
                        )
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatMinorAmount(item.currentBalanceMinor),
                    style = MaterialTheme.typography.titleMedium,
                )
                TextButton(onClick = onArchive) {
                    Text("Archive")
                }
            }
        }
    }
}
