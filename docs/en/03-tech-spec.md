# Personal Finance Manager (PFM) — Technical Specification v1

## 1. Purpose
This document defines the technical stack and initial architectural decisions for PFM V1.

The goal is to have a foundation that is:
- modern;
- easy to maintain;
- consistent with native Android;
- optimal for incremental development through a coding agent;
- suitable for offline-first local persistence.

## 2. Platform and Language
- Platform: **Android**
- Language: **Kotlin**
- UI framework: **Jetpack Compose**

## 3. Core Tech Stack
Recommended stack for V1:

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Room** for local database
- **DataStore** for lightweight preferences
- **Coroutines**
- **Flow**
- **ViewModel**
- **Hilt** for dependency injection

## 4. Architecture Style
Recommended architecture:

- **MVVM**
- **Repository Pattern**
- **Use Cases** for core application logic
- separation into layers:
  - `ui`
  - `domain`
  - `data`

## 5. Architectural Goals
- clear separation of responsibilities;
- business logic not mixed with UI;
- persistence encapsulated in the data layer;
- ease of testing;
- support for feature-by-feature incremental development;
- minimized coupling.

## 6. Proposed Package Structure

```text
app/src/main/java/<package>/ 
  di/
  data/
    local/
      db/
      dao/
      entity/
      mapper/
    repository/
    datastore/
  domain/
    model/
    repository/
    usecase/
  ui/
    navigation/
    theme/
    common/
    features/
      home/
      accounts/
      transactions/
      budget/
      settings/
```

## 7. Layer Responsibilities

### 7.1 UI Layer
Responsibilities:
- Compose screens
- UI state holder
- user events
- navigation
- rendering of UI models

Typical components:
- `Screen`
- `ViewModel`
- `UiState`
- `UiEvent`

### 7.2 Domain Layer
Responsibilities:
- domain models
- repository interfaces
- use cases
- core business rules

The domain layer must not depend on Room or Android-specific details, except for reasonable minimal simplifications.

### 7.3 Data Layer
Responsibilities:
- Room database
- DAO
- local entities
- entity <-> domain mapping
- repository implementation
- DataStore for simple preferences

## 8. Persistence Strategy

## 8.1 Room
Use Room for:
- accounts
- transactions
- categories
- planned expenses
- budget profile

Reasons:
- robust structured persistence
- aggregate queries useful for balances and reporting
- suitable for local relational data

## 8.2 DataStore
Use DataStore for:
- lightweight app preferences
- non-relational UI configurations
- possible future onboarding flags

Examples:
- displayed base currency
- display preferences
- simple non-critical configurations

## 9. Initial Database Design

### Tables
- `accounts`
- `transactions`
- `categories`
- `planned_expenses`
- `budget_profile`

### Notes
- `budget_profile` can initially contain only one record
- use surrogate keys (`Long`)
- use indexes for frequently filtered fields:
  - `transactions.date`
  - `transactions.type`
  - `transactions.sourceAccountId`
  - `transactions.destinationAccountId`

## 10. Amount Representation
For financial data, avoid `Float` and `Double` as the primary source of truth.

Recommended choice for V1:
- use amounts as `Long` in minor units (e.g., cents) in the persistence layer
- expose readable wrappers or mappers in domain/UI if needed

Example:
- 12.34 EUR → `1234`

Reason:
- avoids rounding errors
- simplifies aggregations
- makes the model more reliable for financial logic

## 11. Date and Time Handling
Recommended choices:
- `LocalDate` for transaction date
- `Instant` for `createdAt` and `updatedAt`

Reason:
- a financial transaction is mainly tied to a date, not a high-precision timestamp
- creation/update metadata can use full timestamp

## 12. Navigation Model
Initial navigation with `Navigation Compose`.

Main sections:
- Home
- Accounts
- Transactions
- Budget
- Settings

Approach:
- bottom navigation with a few tabs
- simple and stable routes
- create/edit screens as dedicated destinations

## 13. UI Strategy

## 13.1 Design Goals
- minimalism
- clarity
- low cognitive load
- fast input
- simple visual hierarchy

## 13.2 Reusable Components
Plan reusable components such as:
- summary amount card
- account row
- transaction row
- empty state
- period selector
- primary FAB for quick add
- labeled amount text

## 13.3 Form Design
- few required fields
- secondary optional fields
- immediate but non-invasive validations
- clear CTAs
- quick save

## 14. State Management
Each feature should have:
- immutable `UiState`
- `ViewModel` exposing state via `StateFlow`
- explicit UI events
- use cases invoked by the ViewModel

Recommended pattern:
- UI observes `StateFlow`
- UI sends events to the ViewModel
- ViewModel coordinates use cases and updates state

## 15. Repository Design
Suggested initial repositories:
- `AccountRepository`
- `TransactionRepository`
- `CategoryRepository`
- `BudgetRepository`
- `PlannedExpenseRepository`

Responsibility examples:
- CRUD
- main data streams
- aggregate queries
- calculations partially delegated to use cases when appropriate

## 16. Suggested Initial Use Cases

### Accounts
- `CreateAccountUseCase`
- `UpdateAccountUseCase`
- `ArchiveAccountUseCase`
- `GetAccountsUseCase`
- `GetAccountCurrentBalanceUseCase`

### Transactions
- `CreateTransactionUseCase`
- `UpdateTransactionUseCase`
- `DeleteTransactionUseCase`
- `GetTransactionsUseCase`
- `GetPeriodSummaryUseCase`

### Budget
- `GetBudgetProfileUseCase`
- `SaveBudgetProfileUseCase`
- `GetPlannedExpensesUseCase`
- `CreatePlannedExpenseUseCase`
- `UpdatePlannedExpenseUseCase`
- `GetAvailableBudgetUseCase`
- `GetRemainingBudgetUseCase`

### Dashboard
- `GetHomeSummaryUseCase`

## 17. Mapping Strategy
Use dedicated mappers between:
- Room entities
- domain models
- UI models when necessary

Rule:
- do not expose Room entities directly to the UI

## 18. Dependency Injection
Use Hilt for:
- database
- DAO
- repository
- use case provider if useful
- ViewModel injection

Reason:
- modern Android standard
- simplifies wiring
- helps testing and modularity

## 19. Testing Strategy

## 19.1 V1 Minimum Testing
Test priorities:
1. critical business use cases
2. main repository logic
3. budget and summary calculations
4. essential validations

## 19.2 What to Test First
- account balance calculation
- period balance calculation
- available budget calculation
- remaining budget calculation
- transfer validation between distinct accounts
- essential mappings

## 19.3 UI Testing
In V1:
- limit to focused tests of main flows only if sustainable
- prioritize correctness of domain and persistence

## 20. Error Handling
Initial approach:
- validation errors handled close to ViewModel/UI state
- persistent/logical errors exposed as simple UI messages
- avoid complex error-handling infrastructure in V1

## 21. Logging and Debugging
In V1:
- minimal and targeted logging
- avoid advanced analytics or remote logging systems
- use logs only for local debugging during development

## 22. Performance Considerations
- efficient aggregate queries for dashboard and reports
- avoid unnecessary loading of the entire dataset
- use Flow where useful for reactive updates
- keep Compose stable and components small
- avoid unnecessary recompositions

## 23. Security and Privacy
Constraints:
- no data sent to external services in V1
- no cloud synchronization
- data kept locally on the device

Possible future evolutions, out of V1 scope:
- encrypted export
- controlled local backup
- app access protection

## 24. Technical Constraints for the Coding Agent
The agent must:
- respect package structure
- not introduce unapproved libraries
- prefer small and incremental changes
- not change architecture without explicit request
- avoid overengineering
- maintain separation between UI, domain, and data

## 25. Recommended Build Order
Recommended implementation order:

1. project setup
2. theme and navigation shell
3. initial Room schema
4. base Account feature
5. base Transaction feature
6. base Budget feature
7. Home summary
8. UI/UX refinement
9. critical tests

## 26. V1 Trade-Offs Accepted
To speed up V1, the following are accepted:
- single app module
- domain not excessively abstracted
- initially selective test coverage
- no multi-module architecture
- no anticipated complexity for future integrations not yet required
