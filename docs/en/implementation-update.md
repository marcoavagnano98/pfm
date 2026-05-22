# PFM Implementation Update Log

This file tracks repository changes made during implementation.

## 2026-05-21 — Sprint 0 foundation scaffold

Assumptions:
- Package name: `com.marco.pfm`.
- Single Android app module for V1, as allowed by the technical specification.
- Existing untracked `package.json` and `package-lock.json` were left untouched.

Changes:
- Added Gradle Android project configuration with Kotlin, Jetpack Compose, Material 3, Navigation Compose, Hilt, Room, DataStore, Coroutines/Flow-ready dependencies, and test placeholders.
- Added a minimal Android/Gradle `.gitignore`.
- Added Android manifest, app resources, backup/data extraction rules that exclude the local Room database from backup/transfer by default.
- Added simple local vector launcher icons.
- Added `PfmApplication` with Hilt and `MainActivity` with Compose entry point.
- Added `AppDatabase` as the initial Room database.
- Set the initial Room schema export to false until persistent entities exist.
- Added DataStore preferences wrapper and DI providers for Room/DataStore.
- Added base UI package structure with theme, navigation shell, bottom navigation, common placeholder screen, and placeholder screens for Home, Accounts, Transactions, Budget, and Settings.
- Added tracked placeholders for empty `data/local/dao`, `data/local/entity`, `data/local/mapper`, `data/repository`, `domain/model`, `domain/repository`, and `domain/usecase` packages.

Verification:
- Not yet compiled locally because this environment has no `java` or `gradle` command available.
- Checked that no Gradle wrapper is present in the repository.

## 2026-05-21 — Sprint 1 accounts foundation

Assumptions:
- Account balances are stored as `Long` minor units.
- Account types for V1 are `Cash`, `Bank`, `Savings`, `CreditCard`, `Investment`, and `Other`.
- Archiving keeps accounts persisted and hides archived accounts from the default list.

Changes:
- Added `Account` and `AccountType` domain models.
- Added `AccountEntity`, `AccountDao`, and Room registration in `AppDatabase`.
- Added account entity/domain mappers.
- Added `AccountRepository` and `DefaultAccountRepository`.
- Added account use cases: create, update, archive, get list, get one.
- Removed Sprint 0 `.gitkeep` placeholders from account-related packages after adding real files.
- Added DI providers for `AccountDao` and `AccountRepository`.
- Added lifecycle ViewModel Compose dependency for Hilt-backed screens.
- Enabled KAPT correct error types for generated Hilt references.
- Replaced the Accounts placeholder with a persisted accounts list, empty state, FAB, account rows, edit navigation, and archive action.
- Added an account create/edit form with minimum validation for name and amount, defaulting new account balance to `0`.
- Added exact text-to-minor-unit amount parsing and display formatting without `Float` or `Double`.
- Added account form navigation destination.

Verification:
- Pending local compile; this environment still has no `java`, no `gradle`, and no Gradle wrapper.
- Ran static search for TODO/FIXME, stale launcher references, schema export drift, and direct floating-point amount types; no actionable issues found. Matches for `Float` are Compose `ExtendedFloatingActionButton` and log text.

## 2026-05-21 — Sprint 2 transactions foundation

Assumptions:
- Transaction amounts are stored as positive `Long` minor units.
- Income uses a destination account only; expense uses a source account only; transfer uses distinct source and destination accounts.
- Categories are a small seeded local list for V1 and are optional on transaction forms.
- Transfers do not use categories in the initial UI.

Changes:
- Added `Transaction`, `TransactionType`, and `Category` domain models.
- Added `TransactionEntity` and `CategoryEntity` with Room indexes and account/category foreign keys.
- Added `TransactionDao` and `CategoryDao`.
- Added transaction and category entity/domain mappers.
- Added `TransactionRepository`, `CategoryRepository`, and default data-layer implementations.
- Added transaction use cases for create, update, get list, and get one.
- Added category get-list use case.
- Added shared transaction validation for income, expense, and transfer account rules.
- Registered transaction/category entities and DAOs in `AppDatabase`.
- Bumped `AppDatabase` to version 2 and added a `1 -> 2` migration for categories, transactions, indexes, and seeded base categories.
- Added DI providers for category and transaction DAOs/repositories.
- Moved exact text-to-minor-unit amount parsing/display helpers into `ui/common` and updated account screens to use them.
- Replaced the Transactions placeholder with a chronological persisted list, empty state, FAB, and transaction rows.
- Added transaction create/edit navigation destination.
- Added a transaction create/edit form with type chips, amount/date fields, account pickers, optional category, optional note, and type-specific validation.

Verification:
- Pending local compile; this environment still has no `java`, no `gradle`, and no Gradle wrapper.
- Ran static search for TODO/FIXME, stale launcher references, schema export drift, and direct floating-point amount types; no actionable issues found. Matches for `Float` are Compose `ExtendedFloatingActionButton` and log text; `type: String` / `dateEpochDay` matches are Room persistence fields.

## 2026-05-21 — Sprint 3 balance and budget core

Assumptions:
- Available budget is `monthly income - planned expenses`.
- Remaining budget is `target budget - actual current-month expense transactions`.
- Account current balance is initial balance plus income into the account, minus expenses from the account, minus outgoing transfers, plus incoming transfers.
- Planned expenses are updated by tapping an existing planned expense row and saving the inline form.

Changes:
- Added `BudgetProfile` and `PlannedExpense` domain models.
- Added `BudgetRepository` and `PlannedExpenseRepository` domain contracts.
- Added `BudgetProfileEntity` and `PlannedExpenseEntity`.
- Added `BudgetDao` and `PlannedExpenseDao`.
- Added budget and planned expense entity/domain mappers.
- Added `DefaultBudgetRepository` and `DefaultPlannedExpenseRepository`.
- Registered budget and planned expense entities and DAOs in `AppDatabase`.
- Bumped `AppDatabase` to version 3 and added a `2 -> 3` migration for `budget_profile`, `planned_expenses`, and planned expense indexes.
- Added DI providers for budget and planned expense DAOs/repositories.
- Added budget use cases: get/save budget profile, get/create/update planned expenses, get available budget, and get remaining budget.
- Added shared finance calculations for account current balance, available budget, remaining budget, and current-month filtering.
- Added account current balance use case and transaction snapshot repository method.
- Updated the Accounts list to display calculated current balance instead of initial balance.
- Replaced the Budget placeholder with a working screen showing available budget, remaining budget, planned total, target budget, monthly profile form, planned expense add/edit form, and planned expense list.

Verification:
- Pending local compile; this environment still has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Sprint 4 home dashboard and period summary

Assumptions:
- Supported summary periods are 1, 2, 3, and 12 months.
- Period summaries include income and expenses only; transfers do not alter net.
- Period windows start at the first day of the earliest included month and end today.

Changes:
- Added `HomeSummary` and `PeriodSummary` domain models.
- Added `GetHomeSummaryUseCase` for total balance, remaining budget, current-month income, expenses, and net.
- Added `GetPeriodSummaryUseCase` for supported period summaries.
- Added transaction aggregate DAO/repository method for income/expense sums between dates.
- Replaced the Home placeholder with a dashboard showing total balance, remaining budget, current-month net, current-month income/expenses, period selector, and period summary.
- Added Home ViewModel state using `StateFlow`.
- Wired Home quick action to open the transaction form.

Verification:
- Pending local compile; this environment still has no `java`, no `gradle`, and no Gradle wrapper.
- Ran static search for TODO/FIXME, stale launcher references, schema export drift, and direct floating-point amount types; no actionable issues found. Matches for `Float` are Compose `ExtendedFloatingActionButton` and log text.

## 2026-05-21 — Sprint 5 UX refinement and quality pass

Assumptions:
- With no local Java/Gradle available, tests are added as source files but cannot be executed in this environment.
- The highest-risk test coverage for this pass is core calculations, transaction validation, and amount parsing/formatting.

Changes:
- Added focused unit tests for account current balance, available budget, remaining budget, and current-month filtering.
- Added focused unit tests for income, expense, transfer, distinct-account, and positive-amount transaction validation.
- Added focused unit tests for exact text-to-minor-unit amount parsing and display/input formatting.

Verification:
- Pending local test run; this environment still has no `java`, no `gradle`, and no Gradle wrapper.
- Ran static search for TODO/FIXME, stale launcher references, schema export drift, and direct floating-point amount types; no actionable issues found. Matches for `Float` are Compose `ExtendedFloatingActionButton` and log text.

## 2026-05-21 — Project README update

Changes:
- Replaced the placeholder README with concise project scope, requirements, build/test commands, and a pointer to this implementation log.

Verification:
- README-only change; no compile run available because this environment still has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Decision log

Changes:
- Added `docs/en/decision-log.md` with stable Android foundation, financial representation, transaction rule, and budget formula decisions.

Verification:
- Documentation-only change; no compile run available because this environment still has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Release readiness audit

Changes:
- Added `docs/en/release-readiness-audit.md` mapping each V1 release checklist item to current source evidence and the remaining runtime verification gap.
- Added a README pointer to the readiness audit.

Verification:
- Documentation-only change; no compile run available because this environment still has no `java`, no `gradle`, and no Gradle wrapper.
- Ran static search for TODO/FIXME, stale launcher references, schema export drift, and direct floating-point amount types; no actionable issues found. Matches for `Float` are Compose `ExtendedFloatingActionButton`, docs, and log text.

## 2026-05-21 — Static verification script

Changes:
- Added `scripts/static-check.sh` for repeatable local static checks of stale TODO/FIXME markers, launcher references, schema export drift, direct floating-point amount usage in app code, and required project files.
- Added the static check command to the README.

Verification:
- Initial run caught documentation self-references, so the script was narrowed to app source and README for marker/stale-reference checks.
- `sh scripts/static-check.sh` passes in this environment.
- Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Android CI workflow

Changes:
- Added `.github/workflows/android.yml` to run static checks, unit tests, and debug assembly with JDK 17 and Gradle 8.10.2 on pushes and pull requests.
- Documented the CI workflow in the README.

Verification:
- Pending CI execution outside this environment.
- `sh scripts/static-check.sh` passes locally after the workflow addition.
- Local Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Sprint deliverables matrix

Changes:
- Added `docs/en/sprint-deliverables-matrix.md` mapping delivery-plan sprint deliverables to current source evidence.
- Added a README pointer to the sprint deliverables matrix.

Verification:
- `sh scripts/static-check.sh` passes after this documentation change.
- Local Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Manual smoke-test protocol

Changes:
- Added `docs/en/manual-smoke-test.md` with concrete emulator/device validation steps for navigation, accounts, transactions, balances, budget, home dashboard, and persistence.
- Added README and release readiness audit pointers to the smoke-test protocol.

Verification:
- `sh scripts/static-check.sh` passes after this documentation change.
- Local Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Period summary tests

Changes:
- Added focused unit tests for `GetPeriodSummaryUseCase`, covering supported period range calculation, income-minus-expenses net, and unsupported period rejection.
- Added `kotlinx-coroutines-test` as a test dependency for coroutine Flow use-case tests.

Verification:
- `sh scripts/static-check.sh` passes after this source/test change.
- Local Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-21 — Settings screen refinement

Changes:
- Replaced the Settings placeholder with a simple V1 settings/status screen showing local storage, sync status, and base currency display notes.
- Updated the sprint deliverables matrix to include the refined Settings screen.

Verification:
- `sh scripts/static-check.sh` passes after this source change.
- Local Android compile/test execution is still unavailable because this environment has no `java`, no `gradle`, and no Gradle wrapper.

## 2026-05-22 — Local verification helper

Changes:
- Added `scripts/verify-local.sh` to run the static verification gate and, when local Java plus Gradle or a Gradle wrapper are available, Android unit tests and debug assembly.
- Added the local verification command to the README.

Verification:
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `java` is not installed in this environment.

## 2026-05-22 — Balance summary memory optimization

Assumptions:
- SQL account-balance deltas must match the existing income, expense, and transfer balance rules.
- Home remaining budget can use the current-month expense aggregate directly because transfers are excluded from that aggregate.

Changes:
- Added Room aggregate queries for account balance deltas by account and for a single account.
- Added transaction repository methods for streamed account balance deltas and single-account balance delta lookup.
- Updated account current balance lookup to use the SQL delta instead of loading every transaction.
- Updated the Accounts list to combine accounts with streamed balance deltas instead of collecting the full transaction list.
- Updated the Home summary to calculate total balance from account balance deltas and remaining budget from the monthly expense aggregate.
- Updated remaining-budget calculation to use the monthly expense aggregate instead of collecting the full transaction list.
- Extended the static check to prevent reintroducing full transaction-list reads in Accounts, Home summary, and remaining-budget paths.

Verification:
- `sh scripts/static-check.sh` passes after this source change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `java` is not installed in this environment.
- Static search confirms no full transaction-list reads remain in the guarded Accounts, Home summary, and remaining-budget paths.

## 2026-05-22 — Aggregate summary test coverage

Changes:
- Added a focused `GetRemainingBudgetUseCase` unit test for the aggregate-backed current-month expense path.
- Added a focused `GetHomeSummaryUseCase` unit test for aggregate account balance deltas and monthly income/expense totals.

Verification:
- `sh scripts/static-check.sh` passes after this test-source change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `java` is not installed in this environment.

## 2026-05-22 — Accounts use-case boundary cleanup

Changes:
- Added `GetAccountBalanceDeltasUseCase` so optimized account balance deltas stay behind the domain use-case boundary.
- Updated `AccountsViewModel` to depend on the new use case instead of the transaction repository interface.

Verification:
- `sh scripts/static-check.sh` passes after this source change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `java` is not installed in this environment.
- Static search confirms UI code no longer imports domain repository interfaces.

## 2026-05-22 — UI architecture static guard

Changes:
- Extended `scripts/static-check.sh` to fail when UI code imports domain repository interfaces directly.

Verification:
- `sh scripts/static-check.sh` passes after this script change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `java` is not installed in this environment.
- Static search confirms no UI imports of domain repository interfaces.

## 2026-05-22 — Dead transaction snapshot API removal

Changes:
- Removed the unused transaction snapshot repository method and DAO query that loaded the full transactions table.
- Updated transaction repository test fakes after the interface cleanup.
- Extended the static check to prevent reintroducing the dead full-table snapshot API.

Verification:
- `sh scripts/static-check.sh` passes after this source/script change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `gradle` is not installed and no Gradle wrapper is present.
- Static search confirms the removed snapshot API is not present.

## 2026-05-22 — Release readiness environment update

Changes:
- Updated `docs/en/release-readiness-audit.md` to reflect current local toolchain evidence: Java 21 is available, but Gradle and a Gradle wrapper are still missing.
- Added current local verification command status to the readiness audit.

Verification:
- `sh scripts/static-check.sh` passes after this documentation change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `gradle` is not installed and no Gradle wrapper is present.

## 2026-05-22 — Bounded transactions list

Assumptions:
- The Transactions screen can default to a simple period filter for V1 because the UI guidelines call for a simple period filter and the app should avoid unbounded list loading.
- Supported transaction list periods match dashboard periods: 1, 2, 3, and 12 months.

Changes:
- Replaced the unbounded transaction list DAO/repository/use-case path with a date-bounded `getTransactionsBetween` path.
- Added a Transactions screen period filter with 1, 2, 3, and 12 month options.
- Updated `TransactionsViewModel` to reload transactions only for the selected bounded period.
- Added a focused `GetTransactionsUseCase` unit test for period range calculation and unsupported period rejection.
- Updated transaction repository test fakes for the bounded method.
- Extended the static check to prevent reintroducing unbounded transaction list observers in main source.

Verification:
- `sh scripts/static-check.sh` passes after this source/test/script change.
- `sh scripts/verify-local.sh` passes the static gate and reports Android unit tests/debug build skipped because `gradle` is not installed and no Gradle wrapper is present.
- Static search confirms main source now uses bounded `getTransactionsBetween` and no unbounded transaction observer remains.

## 2026-05-22 — Android environment verification detail

Assumptions:
- Local verification should report the Android SDK state before attempting Gradle work so missing SDK pieces are visible even when Gradle is not installed.
- The project should keep `compileSdk = 35` until a build with a changed SDK target can be verified.

Changes:
- Extended `scripts/verify-local.sh` to detect `$ANDROID_SDK_ROOT`, `$ANDROID_HOME`, or `$HOME/Android/Sdk`, export the detected SDK path for Gradle, and check for the declared compile SDK platform plus `adb`.
- Updated the release readiness audit with current local evidence: Java 21 is available, Android SDK exists under `$HOME/Android/Sdk`, `adb` and build tools are present, platform `android-36.1` is installed, platform `android-35` is missing, and Gradle/Gradle wrapper are still unavailable.
- Corrected the release readiness evidence for the bounded transaction list DAO method.

Verification:
- `sh scripts/static-check.sh` passes after this documentation/script change.
- `sh scripts/verify-local.sh` passes the static gate, detects the Android SDK at `$HOME/Android/Sdk`, and reports Android unit tests/debug build skipped because SDK platform `android-35` is not installed. Installed platform detected: `android-36.1`.

## 2026-05-22 — Local Android build verification enabled

Assumptions:
- The repository should include a standard Gradle wrapper so Android verification does not depend on a separately installed system `gradle` command.
- Test fake classes should use unique top-level names because Kotlin private top-level declarations still cannot redeclare the same JVM class name in the same package.

Changes:
- Installed Android command-line tools in the local SDK and installed SDK platform `android-35` so the declared `compileSdk = 35` can be built.
- Downloaded temporary Gradle 8.10.2 tooling to generate the repository Gradle wrapper.
- Added Gradle wrapper files: `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`, and `gradle/wrapper/gradle-wrapper.properties`.
- Renamed test fakes in `GetPeriodSummaryUseCaseTest` and `GetRemainingBudgetUseCaseTest` to avoid top-level JVM class name collisions during unit-test compilation.
- Updated README commands, CI workflow commands, sprint deliverable evidence, and release readiness audit to use the Gradle wrapper and current local verification evidence.

Verification:
- Initial `sh scripts/verify-local.sh` with temporary Gradle reached real Android compilation and failed in `:app:compileDebugUnitTestKotlin` because two test files declared top-level `FakeTransactionRepository` classes.
- After renaming the test fakes, `GRADLE_USER_HOME=/tmp/pfm-gradle-home PATH=/tmp/pfm-android-tools/gradle-8.10.2/bin:$PATH sh scripts/verify-local.sh` passed `:app:testDebugUnitTest` and `:app:assembleDebug`.
- After generating the Gradle wrapper, `GRADLE_USER_HOME=/tmp/pfm-gradle-home sh scripts/verify-local.sh` passed through `./gradlew`: static checks, `:app:testDebugUnitTest`, and `:app:assembleDebug`.

## 2026-05-22 — Emulator startup smoke test

Assumptions:
- A startup/navigation smoke test is useful runtime evidence, but it does not replace the full CRUD and persistence checklist in `docs/en/manual-smoke-test.md`.

Changes:
- Installed the API 35 Google APIs x86_64 system image in the local Android SDK.
- Created a headless emulator AVD named `pfm_api35`.
- Updated the release readiness audit with runtime startup/navigation smoke evidence and the remaining full manual smoke gap.

Verification:
- `avdmanager list avd` shows `pfm_api35` targeting Android 15.0 Google APIs x86_64.
- The headless emulator booted successfully with `sys.boot_completed=1`.
- `adb install -r app/build/outputs/apk/debug/app-debug.apk` succeeded.
- `adb shell am start -n com.marco.pfm/.MainActivity` succeeded.
- `adb shell pidof com.marco.pfm` returned a running process id.
- Startup log inspection found no `FATAL EXCEPTION` in the checked log window.
- Emulator screenshot `/tmp/pfm-launch.png` shows the Home dashboard rendering.
- Bottom navigation smoke reached Home, Accounts, Transactions, Budget, and Settings by `uiautomator` text inspection.
- Emulator was shut down with `adb emu kill`; `adb devices` then showed no attached devices.


## 2026-05-22 — V1 roadmap refresh

Assumptions:
- The requested account deletion bug fix should be modeled as a safe swipe right-to-left delete flow with explicit confirmation.
- Existing readiness and evidence documents should stay focused on implemented behavior, so this update only changes planning/product/UX/technical docs.

Changes:
- Updated the product scope docs to include safe delete flows, flexible Home period selection, yearly trend analysis, lightweight CSV export, dated planned expenses, and optional automatic budgeting in V1.
- Extended the technical and UX guidance to cover swipe-to-delete confirmations, the yearly trend view, export requirements, planned expense dates, and optional budget automation.
- Expanded the delivery plan with new milestones plus Sprint 6, Sprint 7, and Sprint 8 to schedule the requested fix/features.

Verification:
- Reviewed all Markdown files in `docs/en` to identify which documents describe future scope versus current evidence.
- Left `release-readiness-audit.md`, `manual-smoke-test.md`, and `sprint-deliverables-matrix.md` unchanged because the new items are not implemented yet.

## 2026-05-22 — CRUD and persistence smoke test

Assumptions:
- The smoke run should preserve a useful persisted dataset, so account archiving remains source/build verified but was not executed in this run.
- The manual smoke values use the current emulator date; all created transactions are in the current month.

Changes:
- Updated the release readiness audit with CRUD and persistence smoke evidence.

Verification:
- Reset app data, installed the debug APK, and launched `com.marco.pfm/.MainActivity` on `pfm_api35`.
- Created `Main account` with initial balance `1000.00`, then edited it to `Primary account`.
- Created an income of `2000.00` to `Primary account`.
- Created an expense of `150.25` from `Primary account`.
- Created `Cash` with initial balance `50.00`.
- Created a transfer of `25.00` from `Primary account` to `Cash`.
- Verified account balances: `Primary account` = `2824.75`; `Cash` = `75.00`.
- Saved budget profile values: monthly income `2000.00`, target budget `1000.00`.
- Added planned expense `Rent` for `600.00`, then edited it to `650.00`.
- Verified budget values after edit: available budget `1350.00`, remaining budget `849.75`, planned `650.00`, target `1000.00`.
- Verified Home values: total balance `2899.75`, remaining budget `849.75`, current month net `1849.75`, income `2000.00`, expenses `150.25`.
- Verified Home period chips for `1m`, `2m`, `3m`, and `12m`; each period summary kept income `2000.00`, expenses `150.25`, and net `1849.75`, showing transfers did not affect net.
- Verified Home `Add transaction` opens the transaction form.
- Edited the expense transaction to select the `Food` category and verified the transaction row shows `Food`.
- Force-stopped and relaunched the app; verified persisted accounts, transactions, Home totals, and budget/planned expense values.
- After the persistence proof, archived `Cash` and verified it no longer appeared in the active Accounts list while `Primary account` remained visible.
