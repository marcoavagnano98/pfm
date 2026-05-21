# Personal Finance Manager (PFM) — Technical Specification v1

## 1. Purpose
Questo documento definisce lo stack tecnico e le decisioni architetturali iniziali per PFM V1.

L'obiettivo è avere una base:
- moderna;
- semplice da mantenere;
- coerente con Android nativo;
- ottimale per sviluppo incrementale tramite coding agent;
- adatta a persistenza locale offline-first.

## 2. Platform and Language
- Platform: **Android**
- Language: **Kotlin**
- UI framework: **Jetpack Compose**

## 3. Core Tech Stack
Stack consigliato per V1:

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Room** per database locale
- **DataStore** per preferenze leggere
- **Coroutines**
- **Flow**
- **ViewModel**
- **Hilt** per dependency injection

## 4. Architecture Style
Architettura consigliata:

- **MVVM**
- **Repository Pattern**
- **Use Cases** per la logica applicativa principale
- separazione in layer:
  - `ui`
  - `domain`
  - `data`

## 5. Architectural Goals
- separazione chiara delle responsabilità;
- business logic non mischiata con la UI;
- persistenza incapsulata nel layer data;
- facilità di test;
- supporto a sviluppo incrementale feature-by-feature;
- minimizzazione del coupling.

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
Responsabilità:
- schermate Compose
- state holder UI
- eventi utente
- navigation
- rendering di modelli UI

Componenti tipici:
- `Screen`
- `ViewModel`
- `UiState`
- `UiEvent`

### 7.2 Domain Layer
Responsabilità:
- modelli di dominio
- interfacce repository
- use case
- regole di business centrali

Il domain layer non deve dipendere da Room o dettagli Android specifici, salvo semplificazioni minime ragionevoli.

### 7.3 Data Layer
Responsabilità:
- Room database
- DAO
- entity locali
- mapping entity <-> domain
- implementazione repository
- DataStore per preferenze semplici

## 8. Persistence Strategy

## 8.1 Room
Usare Room per:
- account
- transactions
- categories
- planned expenses
- budget profile

Motivazioni:
- persistenza strutturata robusta
- query aggregate utili per saldi e report
- adatto a dati relazionali locali

## 8.2 DataStore
Usare DataStore per:
- preferenze app leggere
- configurazioni UI non relazionali
- eventuali flag onboarding futuri

Esempi:
- valuta base visualizzata
- preferenze display
- configurazioni semplici non critiche

## 9. Initial Database Design

### Tables
- `accounts`
- `transactions`
- `categories`
- `planned_expenses`
- `budget_profile`

### Notes
- `budget_profile` può contenere inizialmente un solo record
- usare chiavi surrogate (`Long`)
- usare indici per campi frequentemente filtrati:
  - `transactions.date`
  - `transactions.type`
  - `transactions.sourceAccountId`
  - `transactions.destinationAccountId`

## 10. Amount Representation
Per dati finanziari, evitare `Float` e `Double` come sorgente primaria di verità.

Scelta consigliata per V1:
- usare importi come `Long` in unità minime (es. centesimi) nel layer di persistenza
- esporre wrapper o mapper leggibili nel domain/UI se necessario

Esempio:
- 12.34 EUR → `1234`

Motivazione:
- evita errori di arrotondamento
- semplifica aggregazioni
- rende il modello più affidabile per logiche finanziarie

## 11. Date and Time Handling
Scelte consigliate:
- `LocalDate` per la data dei movimenti
- `Instant` per `createdAt` e `updatedAt`

Motivazione:
- il movimento finanziario è legato principalmente a una data, non a un timestamp di precisione
- i metadati di creazione/modifica possono usare timestamp completo

## 12. Navigation Model
Navigazione iniziale con `Navigation Compose`.

Sezioni principali:
- Home
- Accounts
- Transactions
- Budget
- Settings

Approccio:
- bottom navigation con poche tab
- rotte semplici e stabili
- schermate create/edit come destinazioni dedicate

## 13. UI Strategy

## 13.1 Design Goals
- minimalismo
- chiarezza
- basso carico cognitivo
- input rapido
- gerarchia visiva semplice

## 13.2 Reusable Components
Prevedere componenti riusabili come:
- summary card importi
- account row
- transaction row
- empty state
- period selector
- primary FAB per quick add
- labeled amount text

## 13.3 Form Design
- pochi campi obbligatori
- campi opzionali secondari
- validazioni immediate ma non invasive
- CTA chiare
- salvataggio rapido

## 14. State Management
Ogni feature dovrebbe avere:
- `UiState` immutabile
- `ViewModel` che espone state tramite `StateFlow`
- eventi UI espliciti
- use case invocati dal ViewModel

Pattern consigliato:
- UI osserva `StateFlow`
- UI invia eventi al ViewModel
- ViewModel coordina use case e aggiorna stato

## 15. Repository Design
Repository iniziali suggeriti:
- `AccountRepository`
- `TransactionRepository`
- `CategoryRepository`
- `BudgetRepository`
- `PlannedExpenseRepository`

Esempi di responsabilità:
- CRUD
- stream dei dati principali
- query aggregate
- calcoli delegati in parte ai use case quando opportuno

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
Usare mapper dedicati tra:
- Room entities
- domain models
- UI models quando necessario

Regola:
- non esporre direttamente entity Room alla UI

## 18. Dependency Injection
Usare Hilt per:
- database
- DAO
- repository
- use case provider se utile
- ViewModel injection

Motivazione:
- standard Android moderno
- semplifica wiring
- agevola testing e modularità

## 19. Testing Strategy

## 19.1 V1 Minimum Testing
Priorità test:
1. use case di business critici
2. repository logic principali
3. calcoli di budget e summary
4. validazioni essenziali

## 19.2 What to Test First
- calcolo saldo account
- calcolo bilancio periodo
- calcolo available budget
- calcolo remaining budget
- validazione transfer tra conti distinti
- mapping essenziali

## 19.3 UI Testing
Nella V1:
- limitarsi a test mirati dei flussi principali solo se sostenibile
- dare priorità alla correttezza del dominio e della persistenza

## 20. Error Handling
Approccio iniziale:
- errori di validazione gestiti vicino al ViewModel/UI state
- errori persistenti/logici esposti come messaggi UI semplici
- evitare infrastrutture complesse di error handling nella V1

## 21. Logging and Debugging
Nella V1:
- logging minimo e mirato
- evitare sistemi avanzati di analytics o remote logging
- usare log solo per debugging locale durante sviluppo

## 22. Performance Considerations
- query aggregate efficienti per dashboard e report
- evitare caricamenti inutili dell'intero dataset
- usare Flow dove utile per aggiornamenti reattivi
- mantenere Compose stable e componenti piccoli
- evitare recomposition inutili

## 23. Security and Privacy
Vincoli:
- nessun invio dati a servizi esterni nella V1
- nessuna sincronizzazione cloud
- dati mantenuti localmente sul dispositivo

Possibili evoluzioni future, fuori scope V1:
- export cifrato
- backup locale controllato
- protezione accesso app

## 24. Technical Constraints for the Coding Agent
L'agente deve:
- rispettare package structure
- non introdurre librerie non approvate
- preferire modifiche piccole e incrementali
- non cambiare architettura senza esplicita richiesta
- evitare overengineering
- mantenere separazione tra UI, domain e data

## 25. Recommended Build Order
Ordine consigliato di implementazione:

1. project setup
2. theme e navigation shell
3. Room schema iniziale
4. Account feature base
5. Transaction feature base
6. Budget feature base
7. Home summary
8. refinement UI/UX
9. test critici

## 26. V1 Trade-Offs Accepted
Per velocizzare la V1 si accettano:
- singolo modulo app
- dominio non eccessivamente astratto
- test coverage inizialmente selettiva
- niente multi-module architecture
- niente complessità anticipata per future integrazioni non ancora richieste
