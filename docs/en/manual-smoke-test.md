# PFM Manual Smoke Test

Use this checklist on an Android emulator or device after `:app:assembleDebug` succeeds.

## Setup

1. Install and open the debug app.
2. Confirm the app opens on Home.
3. Confirm bottom navigation switches between Home, Accounts, Transactions, Budget, and Settings.

## Accounts

1. Open Accounts.
2. Add a Bank account named `Main account` with initial balance `1000`.
3. Confirm the account appears in the list with balance `1000.00`.
4. Tap the account, change the name to `Primary account`, and save.
5. Confirm the updated name appears.

## Transactions

1. Open Transactions.
2. Add an income of `2000` to `Primary account`.
3. Add an expense of `150.25` from `Primary account`.
4. Add a second account named `Cash` with initial balance `50`.
5. Add a transfer of `25` from `Primary account` to `Cash`.
6. Confirm the transaction list shows all three transactions in chronological order.
7. Confirm each transaction row shows type, amount, date, account context, and category when selected.

## Balances

1. Open Accounts.
2. Confirm `Primary account` balance reflects initial balance + income - expense - outgoing transfer.
3. Confirm `Cash` balance reflects initial balance + incoming transfer.

## Budget

1. Open Budget.
2. Set monthly income to `2000`.
3. Set target budget to `1000`.
4. Add planned expense `Rent` for `600`.
5. Confirm available budget is `1400.00`.
6. Confirm remaining budget is target budget minus current-month expenses.
7. Tap `Rent`, change amount to `650`, and confirm planned total and available budget update.

## Home

1. Open Home.
2. Confirm Total balance, Remaining budget, and Current month net are shown.
3. Confirm Income and Expenses reflect the current month.
4. Switch period chips between `1m`, `2m`, `3m`, and `12m`.
5. Confirm the period summary updates and transfers do not change income, expenses, or net.
6. Tap Add transaction and confirm the transaction form opens.

## Persistence

1. Close and reopen the app.
2. Confirm accounts, transactions, budget profile, and planned expenses are still present.
