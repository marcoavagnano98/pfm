# Personal Finance Manager (PFM) — UI Guidelines v1

## 1. Purpose
This document defines the UI/UX guidelines for PFM V1.

The goal is to build an interface that is:
- minimal;
- fast to use;
- clear;
- non-invasive;
- optimized for frequent data entry;
- sustainable over time for daily personal use.

## 2. Core UI Principles

### 2.1 Minimal First
Each screen should show only what is truly needed.  
Avoid:
- excessive density;
- too many primary CTAs;
- visual overload;
- secondary information in the foreground.

### 2.2 Fast Data Entry
The most frequent actions must be easy and quick:
- add an expense;
- add an income;
- add a transfer;
- check remaining budget;
- check main balances.

### 2.3 Low Cognitive Load
The UI must minimize mental effort:
- few required fields;
- clear labels;
- readable numbers;
- simple visual hierarchy;
- consistent patterns across screens.

### 2.4 Calm Finance Experience
The app should not communicate anxiety or visual aggressiveness.  
Prefer:
- sober colors;
- generous spacing;
- subtle feedback;
- clean components;
- minimal animations.

### 2.5 Progressive Disclosure
Show what is needed first.  
Advanced fields and options must be:
- optional;
- secondary;
- hidden until useful.

## 3. Visual Style Direction

## 3.1 General Tone
- sober
- clean
- tidy
- modern
- functional
- personal

## 3.2 Avoid
- overly noisy dashboards
- too many saturated colors
- unnecessarily giant cards
- decorative icons without function
- unrequested gamified elements
- layouts that resemble enterprise financial products

## 3.3 Prefer
- simple surfaces
- compact cards
- consistent spacing
- readable typography
- highlighted amounts
- frequent actions always accessible

## 4. Information Hierarchy

Each screen should follow this hierarchy:

1. primary information
2. primary action
3. supporting information
4. optional details

Home example:
1. total balance / remaining budget
2. add transaction
3. monthly income and expenses
4. period details

## 5. Navigation Guidelines

## 5.1 Main Navigation
Use bottom navigation with a maximum of 5 sections:

- Home
- Accounts
- Transactions
- Budget
- Settings

## 5.2 Navigation Rules
- avoid unnecessary deep levels;
- create/edit as dedicated screens;
- keep names simple and stable;
- avoid long paths for frequent actions.

## 6. Screen Guidelines

## 6.1 Home
Goal:
- provide an immediate snapshot of the current situation.

Must show at the top:
- total balance
- remaining budget
- current month net

Secondary elements:
- monthly income
- monthly expenses
- explicit period selector
- yearly trend card with income / expenses / balance toggle
- shortcut for new transaction

Rules:
- keep charts compact and focused on one yearly trend at a time;
- key numbers readable at a glance;
- period selection must be faster than editing filters manually;
- quick action always prominent.

## 6.2 Accounts Screen
Goal:
- quickly view all financial positions.

Each account row must show:
- name
- type
- current balance
- archive status when relevant

Rules:
- clean list;
- no unnecessary row information;
- detail/edit accessible with simple tap;
- swipe right-to-left must expose delete with confirmation;
- clearly visible CTA to add an account.

## 6.3 Transactions Screen
Goal:
- quickly view and add transactions.

Each transaction row must show:
- type
- amount
- date
- main account
- category if available

Rules:
- clear chronological ordering;
- visual distinction between income and expense should be readable but not aggressive;
- transfers must be distinguishable;
- swipe right-to-left must expose delete with confirmation;
- period filter should be simple.

## 6.4 Transaction Form
Goal:
- enter a transaction in the least possible time.

Fields visible immediately:
- transaction type
- amount
- date
- relevant account
- category

Secondary fields:
- note
- advanced options

Rules:
- change shown fields based on type:
  - income
  - expense
  - transfer
- immediate but subtle validation;
- always clear save CTA;
- minimize scrolling.

## 6.5 Budget Screen
Goal:
- configure income, target, and planned expenses;
- show available and remaining budget;
- optionally help the user with an automatic monthly budget suggestion.

Hierarchy:
1. available budget
2. remaining budget
3. monthly income
4. target budget / automatic suggestion control
5. planned expenses list

Rules:
- key numbers at the top;
- simple configuration;
- each planned expense must expose a planned date;
- planned expenses easily editable;
- automatic budgeting must stay optional and easy to disable;
- avoid dense tables.

## 6.6 Settings Screen
Goal:
- host secondary settings and local export actions.

Rules:
- simple screen;
- grouped items;
- export actions should live under a clear Export section;
- avoid complexity in V1.

## 7. Component Guidelines

## 7.1 Summary Cards
Use reusable cards for:
- total balance
- remaining budget
- period net
- income
- expenses

Rules:
- one primary metric per card;
- minimal secondary text;
- consistent card height;
- no unnecessary graphic elements.

## 7.2 Rows
Rows for accounts and transactions must be:
- easy to scan;
- consistent across lists;
- with sufficient spacing;
- with clearly readable amount;
- compatible with swipe-to-delete gestures without hiding the main content.

## 7.3 Buttons
Preferences:
- one clear primary action per screen;
- less prominent secondary actions;
- avoid multiple adjacent primary buttons.

## 7.4 FAB
Use a FAB for quick transaction add when consistent with the screen.
Rules:
- one FAB visible per context;
- clear icon;
- optionally short label.

## 7.5 Empty States
Each main section must have useful empty states.

Examples:
- no account created
- no transaction recorded
- no planned expense
- no data in selected period

Rules:
- simple tone;
- clear CTA;
- no long or patronizing text.

## 8. Form UX Rules
- use numeric keyboard for amounts;
- set smart defaults when possible;
- minimize required fields;
- validate without interrupting the flow;
- show errors near the field;
- destructive actions require confirmation;
- avoid unnecessary modals.

## 9. Typography Rules
- financial numbers always readable and prominent;
- clear but not huge titles;
- discreet secondary text;
- avoid too many typographic levels.

## 10. Color Rules
- sober palette;
- green/red used moderately for amounts;
- do not rely only on color to convey meaning;
- sufficient contrast;
- initial light mode, optional dark theme if simple to support.

## 11. Feedback Rules
Feedback to provide:
- successful save
- validation not passed
- no data available
- operation completed

Rules:
- short feedback;
- non-invasive;
- consistent;
- no technical language.

## 12. Accessibility Basics
Even in V1, at least follow:
- readable text
- adequate touch targets
- sufficient contrast
- clear labels
- content not dependent only on color

## 13. UX Anti-Patterns to Avoid
Do not:
- create screens with too many open fields at once;
- make long forms without hierarchy;
- add heavy or multi-purpose charts that compete with the core summary;
- use excessive colors;
- use ambiguous CTAs;
- make transaction entry cumbersome;
- hide destructive actions behind unclear gestures or terminology;
- use unnecessarily complex financial terminology.

## 14. Default UX Bias
If multiple options exist:
- choose the one with less friction;
- choose the most readable one;
- choose the one that speeds up data entry;
- choose the one most consistent with a personal and minimal app.
