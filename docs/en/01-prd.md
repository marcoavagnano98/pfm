# Personal Finance Manager (PFM) — Product Vision

## 1. Product Name
Personal Finance Manager (PFM)

## 2. Product Goal
PFM is a native Android app designed to allow a single user to manually track their personal financial situation in a simple, fast, private, and fully offline way.

The main goal is to provide a reliable tool to:
- monitor income and expenses;
- manage accounts and other financial positions;
- understand balance over time;
- set and monitor a monthly budget;
- keep all data locally on the device, without relying on third-party services.

## 3. Target User
Single user, owner of the Android device, with strong focus on:
- privacy;
- full control of data;
- ease of use;
- fast data entry;
- clear visibility of their financial situation.

## 4. Problem Statement
Personal finance applications available on the market often:
- require accounts and cloud synchronization;
- collect sensitive data;
- are too complex or invasive;
- are not flexible enough to represent specific personal financial situations.

The user needs a personal tool that allows manual recording of income, expenses, accounts, investments, and other financial positions, while keeping data exclusively local.

## 5. Core Value Proposition
PFM must be:
- **private**: no obligation to use external services;
- **offline-first**: core functionality does not depend on network connectivity;
- **simple**: entering data should require the least effort possible;
- **extensible**: new types of financial positions must be addable;
- **clear**: it must help quickly understand budget, expenses, and balance.

## 6. Non-Negotiable Constraints
The following constraints are mandatory:

- initial platform: **Android**
- language: **Kotlin**
- data saved in a **persistent and local** way
- no integration required with external services in V1
- manual data management in V1
- **minimal**, **non-invasive**, **user-friendly** UI
- very fast data entry experience
- architecture designed for future extensibility

## 7. Product Principles
1. **Privacy by default**  
   All data stays on-device in V1.

2. **Fast capture first**  
   Entering an expense or income must be as fast as possible.

3. **Minimal UI**  
   The interface must prioritize clarity, few elements per screen, and low cognitive load.

4. **Finance without friction**  
   The app must help the user maintain the habit of recording data, not hinder it.

5. **Progressive extensibility**  
   The domain foundations must allow future introduction of new account types, assets, and budgeting rules.

## 8. In-Scope for V1
- management of accounts and financial positions
- manual entry of income, expenses, and transfers
- local persistent storage
- monthly income
- monthly budget
- planned expenses with an explicit planned date
- remaining budget calculation
- safe deletion of accounts and transactions with explicit confirmation
- balance by selectable time intervals in Home
- yearly trend visualization for income, expenses, or balance
- lightweight CSV export of balance history from Settings
- optional automatic monthly budget suggestion with manual override
- initial dashboard with concise summary

## 9. Out of Scope for V1
- cloud synchronization
- bank connections
- automatic imports
- multi-device sync
- multi-user
- OCR for receipts
- advanced or customizable exports beyond the balance CSV
- complex financial automations beyond the optional monthly budget suggestion
- advanced recurrence engine

## 10. Success Criteria for V1
PFM V1 is considered successful if it allows the user to:

- create, update, and safely delete their financial positions;
- quickly record and remove manual transactions when needed;
- view overall balance, main transactions, and yearly trends;
- set a monthly budget manually or with an automatic suggestion;
- plan expenses with a dedicated date and see how much can still be spent in the month;
- consult balances across selectable time intervals in Home;
- export a local balance CSV for offline analysis;
- use the app continuously without perceiving it as heavy or inconvenient.
