# Personal Finance Manager (PFM) — Delivery Plan v1

## 1. Purpose
This document defines the delivery plan for PFM V1 in small tasks and sprints, suitable for being executed by a coding agent in an orderly and verifiable way with controlled token usage.

## 2. Delivery Strategy
Principles:
- incremental development;
- small and vertical tasks;
- priority to foundations;
- UI and domain built together but progressively;
- each sprint must leave the project in a coherent state.

## 3. Overall Milestones
Main V1 milestones are:

1. Project foundations
2. Account management
3. Transaction management
4. Budgeting
5. Dashboard and summaries
6. Polish, validations, and critical tests

## 4. Sprint Structure
Each sprint must include:
- clear goal;
- verifiable outputs;
- few well-bounded tasks;
- no unnecessary architectural changes.

## 5. Sprint 0 — Project Foundation

### Goal
Set up the technical base of the project.

### Deliverables
- configured Android project
- Jetpack Compose enabled
- Material 3 configured
- Navigation Compose configured
- Hilt configured
- Room configured
- DataStore configured
- initial package structure
- main placeholder screens
- minimal base theme

### Tasks
- create Android project in Kotlin
- configure main dependencies
- create package structure `data/domain/ui/di`
- configure `AppDatabase`
- configure bottom navigation shell
- create placeholder screens:
  - Home
  - Accounts
  - Transactions
  - Budget
  - Settings

### Definition of Done
- app starts correctly
- base navigation works
- database is initialized
- dependency injection works
- project structure is ready for features

## 6. Sprint 1 — Accounts Foundation

### Goal
Implement base management of financial positions.

### Deliverables
- Account entity/domain model
- AccountType enum
- account DAO
- account repository
- base account use cases
- accounts list
- create/edit account screen
- account archiving

### Tasks
- define Room entity `AccountEntity`
- create entity-domain mapper
- create `AccountRepository`
- implement:
  - create account
  - update account
  - archive account
  - get accounts
- create accounts list UI
- create account form
- validate minimum fields

### Definition of Done
- user can create an account
- account is persisted
- list shows accounts
- user can edit an account
- user can archive it

## 7. Sprint 2 — Transactions Foundation

### Goal
Allow manual transaction entry.

### Deliverables
- Transaction entity/domain model
- TransactionType enum
- base category
- transaction DAO
- transaction repository
- base transaction use cases
- transaction list
- create/edit transaction form

### Tasks
- define `TransactionEntity`
- define `CategoryEntity`
- create DAO and repository
- implement creation of:
  - income
  - expense
  - transfer
- implement validation by transaction type
- create chronological transaction list
- create transaction form
- support optional notes

### Definition of Done
- user can save an income
- user can save an expense
- user can save a transfer
- data is persisted correctly
- transaction list reflects saved data

## 8. Sprint 3 — Balance and Budget Core

### Goal
Implement core balance and budget logic.

### Deliverables
- account current balance calculation
- persisted BudgetProfile
- persisted PlannedExpense
- budget use cases
- working budget screen

### Tasks
- define `BudgetProfileEntity`
- define `PlannedExpenseEntity`
- implement budget/planned expense repositories
- implement use cases:
  - get/save budget profile
  - create/update planned expense
  - get available budget
  - get remaining budget
- show available and remaining budget
- support monthly income, target budget, and planned expenses

### Definition of Done
- user can set monthly income
- user can set target budget
- user can add planned expenses
- available budget is calculated
- remaining budget is calculated using actual monthly expenses

## 9. Sprint 4 — Home Dashboard and Period Summary

### Goal
Provide a concise view of the financial situation.

### Deliverables
- home summary
- period summary
- base time-range filter
- main dashboard metrics

### Tasks
- implement aggregate queries for:
  - current month income
  - current month expenses
  - current month net
  - total balance
- implement `PeriodSummary`
- support periods:
  - 1 month
  - 2 months
  - 3 months
  - 12 months
- create home dashboard with concise cards
- add quick action for new transaction

### Definition of Done
- home shows key numbers
- period summary is consistent with saved data
- transfers do not alter net
- period filter works for supported intervals

## 10. Sprint 5 — UX Refinement and Quality Pass

### Goal
Improve user experience, robustness, and quality.

### Deliverables
- improved validations
- empty states
- minimal loading/error states
- form UI improvements
- critical-case tests
- naming and visual consistency refinement

### Tasks
- improve save feedback
- reduce form friction
- introduce coherent empty states
- improve microcopy
- add critical use case tests
- add core calculation tests
- refine spacing, typography, and CTAs

### Definition of Done
- smoother and more readable UX
- common errors better handled
- critical calculations covered by tests
- coherent app for V1 demo/personal use

## 11. Suggested Task Granularity for the Agent
Each ideal task should cover one of:
- one entity + DAO + small repository
- one single screen
- one single use-case chain
- one single business calculation
- one local and contained refactor

## 12. Example Task Sequence
Example sequence of small tasks:

### Sprint 0
1. bootstrap Android project + Compose
2. configure Hilt
3. configure Room
4. create navigation shell
5. create placeholder screens

### Sprint 1
6. implement `AccountEntity`
7. implement `AccountDao`
8. implement `AccountRepository`
9. implement `CreateAccountUseCase`
10. create `AccountsListScreen`
11. create `AccountFormScreen`

### Sprint 2
12. implement `TransactionEntity`
13. implement `CategoryEntity`
14. implement transaction DAO/repository
15. implement create income/expense/transfer
16. create `TransactionsListScreen`
17. create `TransactionFormScreen`

### Sprint 3
18. implement `BudgetProfileEntity`
19. implement `PlannedExpenseEntity`
20. implement budget repository
21. implement available budget calculation
22. implement remaining budget calculation
23. create `BudgetScreen`

### Sprint 4
24. implement dashboard aggregate queries
25. implement `GetHomeSummaryUseCase`
26. implement `GetPeriodSummaryUseCase`
27. create `HomeScreen`
28. add period selector

### Sprint 5
29. add empty states
30. improve UI validations
31. add use-case tests
32. perform visual polish

## 13. Release Readiness Checklist
Before declaring V1 ready:

- [ ] account creation works
- [ ] account editing works
- [ ] account archiving works
- [ ] expense creation works
- [ ] income creation works
- [ ] transfer creation works
- [ ] transaction list works
- [ ] budget profile persisted
- [ ] planned expenses persisted
- [ ] available budget correct
- [ ] remaining budget correct
- [ ] dashboard with key metrics
- [ ] period summary correct
- [ ] base validations present
- [ ] UI usable without major friction
- [ ] no dependency on external services

## 14. Risks and Mitigations

### Risk 1 — Early overengineering
**Mitigation:** keep V1 narrow and tasks small.

### Risk 2 — Ambiguity about balances and budget
**Mitigation:** follow the domain model and already defined formulas.

### Risk 3 — UI too heavy
**Mitigation:** apply minimalist guidelines and progressive disclosure.

### Risk 4 — Token waste with oversized tasks
**Mitigation:** use short prompts focused only on relevant files.

## 15. Recommended Immediate Next Step
After these documents, the ideal next step is to create:

- `docs/en/04-ui-guidelines.md`
- `docs/en/decision-log.md`

and then start with a first Sprint 0 task:
**bootstrap the Android project with base stack and navigation shell**.
