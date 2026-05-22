# PFM Sprint Deliverables Matrix

Date: 2026-05-21

This file maps `docs/en/05-delivery-plan.md` deliverables to source artifacts. Runtime status is tracked separately in `docs/en/release-readiness-audit.md`.

## Sprint 0 — Project Foundation

| Deliverable | Source evidence |
|---|---|
| Configured Android project | `settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`, `gradlew`, `gradle/wrapper/`, `AndroidManifest.xml` |
| Jetpack Compose enabled | `app/build.gradle.kts`, `MainActivity.kt`, `PfmApp.kt` |
| Material 3 configured | `app/build.gradle.kts`, `ui/theme/Theme.kt` |
| Navigation Compose configured | `app/build.gradle.kts`, `ui/PfmApp.kt` |
| Hilt configured | `PfmApplication.kt`, `MainActivity.kt`, `di/AppModule.kt` |
| Room configured | `data/local/db/AppDatabase.kt`, `di/AppModule.kt` |
| DataStore configured | `data/datastore/UserPreferences.kt`, `di/AppModule.kt` |
| Initial package structure | `data/`, `domain/`, `ui/`, `di/` packages |
| Main screens | `ui/features/home`, `accounts`, `transactions`, `budget`, `settings` |
| Minimal base theme | `ui/theme/Color.kt`, `Theme.kt`, `Type.kt` |

## Sprint 1 — Accounts Foundation

| Deliverable | Source evidence |
|---|---|
| Account entity/domain model | `AccountEntity.kt`, `Account.kt` |
| AccountType enum | `AccountType.kt` |
| Account DAO | `AccountDao.kt` |
| Account repository | `AccountRepository.kt`, `DefaultAccountRepository.kt` |
| Base account use cases | `CreateAccountUseCase.kt`, `UpdateAccountUseCase.kt`, `ArchiveAccountUseCase.kt`, `GetAccountsUseCase.kt`, `GetAccountUseCase.kt` |
| Accounts list | `AccountsScreen.kt`, `AccountsViewModel.kt` |
| Create/edit account screen | `AccountFormScreen.kt`, `AccountFormViewModel.kt` |
| Account archiving | `ArchiveAccountUseCase.kt`, `AccountDao.archiveAccount`, `AccountsScreen.kt` |

## Sprint 2 — Transactions Foundation

| Deliverable | Source evidence |
|---|---|
| Transaction entity/domain model | `TransactionEntity.kt`, `Transaction.kt` |
| TransactionType enum | `TransactionType.kt` |
| Base category | `CategoryEntity.kt`, `Category.kt`, category seeding in `AppModule.kt` |
| Transaction DAO | `TransactionDao.kt` |
| Transaction repository | `TransactionRepository.kt`, `DefaultTransactionRepository.kt` |
| Base transaction use cases | `CreateTransactionUseCase.kt`, `UpdateTransactionUseCase.kt`, `GetTransactionsUseCase.kt`, `GetTransactionUseCase.kt` |
| Transaction list | `TransactionsScreen.kt`, `TransactionsViewModel.kt` |
| Create/edit transaction form | `TransactionFormScreen.kt`, `TransactionFormViewModel.kt` |

## Sprint 3 — Balance and Budget Core

| Deliverable | Source evidence |
|---|---|
| Account current balance calculation | `FinanceCalculations.kt`, `GetAccountCurrentBalanceUseCase.kt`, `AccountsViewModel.kt` |
| Persisted BudgetProfile | `BudgetProfileEntity.kt`, `BudgetDao.kt`, `DefaultBudgetRepository.kt` |
| Persisted PlannedExpense | `PlannedExpenseEntity.kt`, `PlannedExpenseDao.kt`, `DefaultPlannedExpenseRepository.kt` |
| Budget use cases | `GetBudgetProfileUseCase.kt`, `SaveBudgetProfileUseCase.kt`, `GetPlannedExpensesUseCase.kt`, `CreatePlannedExpenseUseCase.kt`, `UpdatePlannedExpenseUseCase.kt`, `GetAvailableBudgetUseCase.kt`, `GetRemainingBudgetUseCase.kt` |
| Working budget screen | `BudgetScreen.kt`, `BudgetViewModel.kt` |

## Sprint 4 — Home Dashboard and Period Summary

| Deliverable | Source evidence |
|---|---|
| Home summary | `HomeSummary.kt`, `GetHomeSummaryUseCase.kt` |
| Period summary | `PeriodSummary.kt`, `GetPeriodSummaryUseCase.kt` |
| Base time-range filter | `HomeViewModel.kt`, `HomeScreen.kt` period selector |
| Main dashboard metrics | `HomeScreen.kt`, `HomeViewModel.kt` |

## Sprint 5 — UX Refinement and Quality Pass

| Deliverable | Source evidence |
|---|---|
| Improved validations | account, transaction, budget, and planned expense ViewModels and use cases |
| Empty states | `AccountsScreen.kt`, `TransactionsScreen.kt`, `BudgetScreen.kt` |
| Minimal loading/error states | form error state fields in feature ViewModels |
| Form UI improvements | account, transaction, and budget form screens |
| Critical-case tests | `FinanceCalculationsTest.kt`, `TransactionValidationTest.kt`, `GetPeriodSummaryUseCaseTest.kt`, `MoneyFormattersTest.kt` |
| Naming and visual consistency refinement | shared `MoneyFormatters.kt`, feature screen title/metric patterns |
| Settings refinement | `SettingsScreen.kt` local storage/sync/status screen |
