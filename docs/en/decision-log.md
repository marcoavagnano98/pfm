# PFM Decision Log

This file records stable implementation decisions for the Android V1 app.

## 2026-05-21 — Android foundation

- Package name: `com.marco.pfm`.
- Architecture: single Android app module using MVVM, repositories, use cases, Room, DataStore, Hilt, Jetpack Compose, Material 3, and Navigation Compose.
- Persistence: all V1 financial data is local-only in Room; lightweight preferences use DataStore.
- Room database name: `pfm.db`.

## 2026-05-21 — Financial representation

- Amounts are persisted and calculated as `Long` minor units.
- UI parses amount text into minor units without `Float` or `Double`.
- Domain/UI display helpers format minor units as decimal text.

## 2026-05-21 — Transaction rules

- Income requires a destination account and no source account.
- Expense requires a source account and no destination account.
- Transfer requires distinct source and destination accounts.
- Transfers do not count as income or expenses for net, budget, or period summaries.

## 2026-05-21 — Budget formulas

- Available budget = monthly income - planned expenses.
- Remaining budget = target budget - actual current-month expenses.
- Account current balance = initial balance + account income - account expenses - outgoing transfers + incoming transfers.
