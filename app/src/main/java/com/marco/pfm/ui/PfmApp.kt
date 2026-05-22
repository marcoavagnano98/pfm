package com.marco.pfm.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marco.pfm.ui.features.accounts.AccountFormScreen
import com.marco.pfm.ui.features.accounts.AccountsScreen
import com.marco.pfm.ui.features.budget.BudgetScreen
import com.marco.pfm.ui.features.home.HomeScreen
import com.marco.pfm.ui.features.settings.SettingsScreen
import com.marco.pfm.ui.features.transactions.TransactionFormScreen
import com.marco.pfm.ui.features.transactions.TransactionsScreen
import com.marco.pfm.ui.navigation.MainDestination

@Composable
fun PfmApp() {
    val navController = rememberNavController()
    val destinations = listOf(
        MainDestination("home", "Home", Icons.Outlined.Home),
        MainDestination("accounts", "Accounts", Icons.Outlined.AccountBalanceWallet),
        MainDestination("transactions", "Transactions", Icons.Outlined.Payments),
        MainDestination("budget", "Budget", Icons.Outlined.PieChart),
        MainDestination("settings", "Settings", Icons.Outlined.Settings),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label,
                            )
                        },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("home") {
                HomeScreen(
                    onCreateTransaction = { navController.navigate("transaction-form") },
                )
            }
            composable("accounts") {
                AccountsScreen(
                    onCreateAccount = { navController.navigate("account-form") },
                    onEditAccount = { accountId -> navController.navigate("account-form?accountId=$accountId") },
                )
            }
            composable(
                route = "account-form?accountId={accountId}",
                arguments = listOf(
                    navArgument("accountId") {
                        type = NavType.LongType
                        defaultValue = 0L
                    },
                ),
            ) {
                AccountFormScreen(
                    onClose = { navController.popBackStack() },
                )
            }
            composable("transactions") {
                TransactionsScreen(
                    onCreateTransaction = { navController.navigate("transaction-form") },
                    onEditTransaction = { transactionId ->
                        navController.navigate("transaction-form?transactionId=$transactionId")
                    },
                )
            }
            composable(
                route = "transaction-form?transactionId={transactionId}",
                arguments = listOf(
                    navArgument("transactionId") {
                        type = NavType.LongType
                        defaultValue = 0L
                    },
                ),
            ) {
                TransactionFormScreen(
                    onClose = { navController.popBackStack() },
                )
            }
            composable("budget") { BudgetScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}
