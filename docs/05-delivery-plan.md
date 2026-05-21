# Personal Finance Manager (PFM) — Delivery Plan v1

## 1. Purpose
Questo documento definisce il piano di consegna della V1 di PFM in task e sprint piccoli, adatti a essere eseguiti da un coding agent in modo ordinato, verificabile e con uso controllato dei token.

## 2. Delivery Strategy
Principi:
- sviluppo incrementale;
- task piccoli e verticali;
- priorità alle fondamenta;
- UI e dominio costruiti insieme ma in modo progressivo;
- ogni sprint deve lasciare il progetto in uno stato coerente.

## 3. Overall Milestones
Le milestone principali della V1 sono:

1. Fondazioni progetto
2. Gestione account
3. Gestione movimenti
4. Budgeting
5. Dashboard e riepiloghi
6. Polish, validazioni e test critici

## 4. Sprint Structure
Ogni sprint deve contenere:
- obiettivo chiaro;
- output verificabili;
- pochi task ben delimitati;
- nessun cambio architetturale non necessario.

## 5. Sprint 0 — Project Foundation

### Goal
Impostare la base tecnica del progetto.

### Deliverables
- progetto Android configurato
- Jetpack Compose attivo
- Material 3 configurato
- Navigation Compose configurato
- Hilt configurato
- Room configurato
- DataStore configurato
- package structure iniziale
- schermate placeholder principali
- tema base minimale

### Tasks
- creare progetto Android in Kotlin
- configurare dipendenze principali
- creare struttura package `data/domain/ui/di`
- configurare `AppDatabase`
- configurare bottom navigation shell
- creare screen placeholder:
  - Home
  - Accounts
  - Transactions
  - Budget
  - Settings

### Definition of Done
- l'app si avvia correttamente
- la navigazione base funziona
- il database è inizializzato
- dependency injection funzionante
- struttura del progetto pronta per le feature

## 6. Sprint 1 — Accounts Foundation

### Goal
Implementare la gestione base delle posizioni finanziarie.

### Deliverables
- entity/domain model Account
- enum AccountType
- DAO account
- repository account
- use case base account
- lista account
- schermata create/edit account
- archiviazione account

### Tasks
- definire Room entity `AccountEntity`
- creare mapper entity-domain
- creare `AccountRepository`
- implementare:
  - create account
  - update account
  - archive account
  - get accounts
- creare UI lista account
- creare form account
- validare campi minimi

### Definition of Done
- l'utente può creare un account
- l'account viene persistito
- la lista mostra gli account
- l'utente può modificare un account
- l'utente può archiviarlo

## 7. Sprint 2 — Transactions Foundation

### Goal
Consentire l'inserimento manuale dei movimenti.

### Deliverables
- entity/domain model Transaction
- enum TransactionType
- category base
- DAO transaction
- repository transaction
- use case base transaction
- lista movimenti
- form create/edit movimento

### Tasks
- definire `TransactionEntity`
- definire `CategoryEntity`
- creare DAO e repository
- implementare creazione di:
  - income
  - expense
  - transfer
- implementare validazioni per tipo movimento
- creare lista movimenti cronologica
- creare form movimento
- supportare note opzionali

### Definition of Done
- l'utente può salvare una entrata
- l'utente può salvare una spesa
- l'utente può salvare un trasferimento
- i dati persistono correttamente
- la lista movimenti riflette i dati salvati

## 8. Sprint 3 — Balance and Budget Core

### Goal
Implementare la logica centrale di saldo e budget.

### Deliverables
- calcolo saldo corrente account
- BudgetProfile persistito
- PlannedExpense persistita
- use case budget
- schermata budget funzionante

### Tasks
- definire `BudgetProfileEntity`
- definire `PlannedExpenseEntity`
- implementare repository budget/planned expenses
- implementare use case:
  - get/save budget profile
  - create/update planned expense
  - get available budget
  - get remaining budget
- mostrare budget disponibile e residuo
- supportare reddito mensile, target budget e spese previste

### Definition of Done
- l'utente può impostare il reddito mensile
- l'utente può impostare il budget target
- l'utente può aggiungere spese previste
- il budget disponibile viene calcolato
- il budget residuo viene calcolato usando le spese effettive del mese

## 9. Sprint 4 — Home Dashboard and Period Summary

### Goal
Fornire una visione sintetica della situazione finanziaria.

### Deliverables
- summary home
- riepilogo periodo
- filtro intervallo temporale base
- metriche principali dashboard

### Tasks
- implementare query aggregate per:
  - entrate mese corrente
  - uscite mese corrente
  - netto mese corrente
  - saldo totale
- implementare `PeriodSummary`
- supportare periodi:
  - 1 mese
  - 2 mesi
  - 3 mesi
  - 12 mesi
- creare dashboard home con card sintetiche
- aggiungere quick action per nuovo movimento

### Definition of Done
- la home mostra i numeri principali
- il riepilogo periodo è coerente coi dati salvati
- i trasferimenti non alterano il netto
- il filtro periodo funziona per gli intervalli supportati

## 10. Sprint 5 — UX Refinement and Quality Pass

### Goal
Migliorare esperienza d'uso, robustezza e qualità.

### Deliverables
- validazioni migliorate
- empty states
- loading/error states minimi
- miglioramenti UI forms
- test dei casi critici
- rifinitura naming e coerenza visiva

### Tasks
- migliorare feedback salvataggio
- ridurre attrito nei form
- introdurre empty states coerenti
- migliorare microcopy
- aggiungere test use case critici
- aggiungere test calcoli principali
- rifinire spacing, typography, CTA

### Definition of Done
- UX più fluida e leggibile
- errori comuni gestiti meglio
- calcoli critici coperti da test
- app coerente per demo/uso personale V1

## 11. Suggested Task Granularity for the Agent
Ogni task ideale dovrebbe coprire uno tra:
- una entity + DAO + repository piccolo
- una schermata singola
- una singola use case chain
- un singolo calcolo di business
- un refactor locale e contenuto

## 12. Example Task Sequence
Esempio di sequenza di task piccoli:

### Sprint 0
1. bootstrap progetto Android + Compose
2. configurare Hilt
3. configurare Room
4. creare navigation shell
5. creare placeholder screen

### Sprint 1
6. implementare `AccountEntity`
7. implementare `AccountDao`
8. implementare `AccountRepository`
9. implementare `CreateAccountUseCase`
10. creare `AccountsListScreen`
11. creare `AccountFormScreen`

### Sprint 2
12. implementare `TransactionEntity`
13. implementare `CategoryEntity`
14. implementare DAO/repository transaction
15. implementare create income/expense/transfer
16. creare `TransactionsListScreen`
17. creare `TransactionFormScreen`

### Sprint 3
18. implementare `BudgetProfileEntity`
19. implementare `PlannedExpenseEntity`
20. implementare repository budget
21. implementare calcolo available budget
22. implementare calcolo remaining budget
23. creare `BudgetScreen`

### Sprint 4
24. implementare query aggregate dashboard
25. implementare `GetHomeSummaryUseCase`
26. implementare `GetPeriodSummaryUseCase`
27. creare `HomeScreen`
28. aggiungere selettore periodo

### Sprint 5
29. aggiungere empty states
30. migliorare validazioni UI
31. aggiungere test use case
32. fare polish visuale

## 13. Release Readiness Checklist
Prima di dichiarare pronta la V1:

- [ ] creazione account funzionante
- [ ] modifica account funzionante
- [ ] archiviazione account funzionante
- [ ] creazione expense funzionante
- [ ] creazione income funzionante
- [ ] creazione transfer funzionante
- [ ] lista movimenti funzionante
- [ ] budget profile persistito
- [ ] planned expenses persistite
- [ ] available budget corretto
- [ ] remaining budget corretto
- [ ] dashboard con metriche principali
- [ ] riepilogo periodo corretto
- [ ] validazioni base presenti
- [ ] UI utilizzabile senza attriti gravi
- [ ] nessuna dipendenza da servizi esterni

## 14. Risks and Mitigations

### Risk 1 — Overengineering early
**Mitigation:** mantenere V1 stretta e task piccoli.

### Risk 2 — Ambiguità su saldi e budget
**Mitigation:** rispettare domain model e formule già definite.

### Risk 3 — UI troppo pesante
**Mitigation:** applicare linee guida minimaliste e progressive disclosure.

### Risk 4 — Spreco token con task troppo grandi
**Mitigation:** usare prompt brevi, focalizzati e limitati ai file rilevanti.

## 15. Recommended Immediate Next Step
Dopo questi documenti, il passo ideale è creare:

- `docs/04-ui-guidelines.md`
- `docs/decision-log.md`

e poi iniziare con un primo task di Sprint 0:
**bootstrap del progetto Android con stack base e navigation shell**.
