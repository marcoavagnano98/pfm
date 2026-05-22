# PFM Release Readiness Audit

Date: 2026-05-22

This audit maps the V1 checklist in `docs/en/05-delivery-plan.md` to current repository evidence. This environment has Java 21, an Android SDK at `$HOME/Android/Sdk`, SDK platform `android-35`, an API 35 emulator AVD named `pfm_api35`, and a repository Gradle wrapper using Gradle 8.10.2. Local unit-test, debug APK build, startup/navigation smoke, and CRUD/persistence smoke evidence is available.

## Checklist Status

| Checklist item | Status | Evidence |
|---|---|---|
| Account creation works | Runtime smoke-tested | `AccountFormScreen`, `AccountFormViewModel`, `CreateAccountUseCase`, `AccountDao.insertAccount`; created `Main account` |
| Account editing works | Runtime smoke-tested | `AccountFormViewModel`, `UpdateAccountUseCase`, `AccountDao.updateAccount`; renamed to `Primary account` |
| Account archiving works | Runtime smoke-tested | `AccountsScreen`, `ArchiveAccountUseCase`, `AccountDao.archiveAccount`; archived `Cash` and verified it left the active account list |
| Expense creation works | Runtime smoke-tested and unit-tested | `TransactionFormViewModel`, `CreateTransactionUseCase`, `TransactionValidationTest`; created `150.25` expense from `Primary account` with `Food` category |
| Income creation works | Runtime smoke-tested and unit-tested | `TransactionFormViewModel`, `CreateTransactionUseCase`, `TransactionValidationTest`; created `2000.00` income to `Primary account` |
| Transfer creation works | Runtime smoke-tested and unit-tested | `TransactionFormViewModel`, `CreateTransactionUseCase`, `TransactionValidationTest`; created `25.00` transfer from `Primary account` to `Cash` |
| Transaction list works | Runtime smoke-tested | `TransactionsScreen`, `TransactionsViewModel`, `TransactionDao.observeTransactionsBetween`; list shows income, expense, transfer, account context, and selected category |
| Budget profile persisted | Runtime smoke-tested | `BudgetProfileEntity`, `BudgetDao`, `DefaultBudgetRepository`, `BudgetViewModel`; profile persisted after app force-stop/relaunch |
| Planned expenses persisted | Runtime smoke-tested | `PlannedExpenseEntity`, `PlannedExpenseDao`, `DefaultPlannedExpenseRepository`, `BudgetViewModel`; `Rent` persisted after app force-stop/relaunch |
| Available budget correct | Unit-tested | `calculateAvailableBudget`, `FinanceCalculationsTest` |
| Remaining budget correct | Unit-tested | `calculateRemainingBudget`, `FinanceCalculationsTest`, `GetRemainingBudgetUseCaseTest` |
| Dashboard with key metrics | Runtime smoke-tested and unit-tested | `HomeScreen`, `HomeViewModel`, `GetHomeSummaryUseCaseTest`; Home shows total balance, remaining budget, current net, income, expenses |
| Period summary correct | Unit-tested | `GetPeriodSummaryUseCaseTest`, `TransactionDao.observeAmountSumByTypeBetween` |
| Base validations present | Implemented and unit-tested | account, transaction, budget, planned expense ViewModels and use cases |
| UI usable without major friction | Runtime smoke-tested on emulator | Compose screens for Home, Accounts, Transactions, Budget, Settings |
| No dependency on external services | Implemented by source inspection | No network/API integration; Room/DataStore local persistence |

## Local Verification

Run on a machine with JDK 17 or newer and Android SDK platform `android-35`:

```bash
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

Current local command status:
- `sh scripts/static-check.sh` passes.
- `GRADLE_USER_HOME=/tmp/pfm-gradle-home sh scripts/verify-local.sh` passes through the Gradle wrapper: static checks, `:app:testDebugUnitTest`, and `:app:assembleDebug`.
- During first verification, Android SDK Build-Tools `34.0.0` were installed automatically by AGP.

Runtime smoke status:
- API 35 AVD `pfm_api35` boots headlessly.
- `app/build/outputs/apk/debug/app-debug.apk` installs successfully.
- `com.marco.pfm/.MainActivity` launches successfully.
- App process `com.marco.pfm` stays running after launch, with no `FATAL EXCEPTION` in the checked startup log window.
- Screenshot evidence from the emulator shows the Home dashboard rendering.
- Bottom navigation smoke reached Home, Accounts, Transactions, Budget, and Settings.
- CRUD/persistence smoke covered `docs/en/manual-smoke-test.md` core flow: account create/edit/archive, income/expense/transfer create, transaction list account/category context, account balances, budget profile, planned expense add/edit, Home period chips, Add transaction shortcut, and persistence after force-stop/relaunch.
- Account archiving was checked after the persistence proof by archiving `Cash` and confirming it no longer appeared in the active Accounts list.

Alternatively, use the repository CI workflow:

```text
.github/workflows/android.yml
```

It runs:
- `sh scripts/static-check.sh`
- `./gradlew :app:testDebugUnitTest`
- `./gradlew :app:assembleDebug`

Recommended manual smoke test on an emulator/device:

```text
docs/en/manual-smoke-test.md
```
