# PFM — Codex Operating Rules

## 1. Purpose
This document defines how the coding agent must work on the PFM project to:
- reduce token waste;
- avoid ambiguity;
- maintain architectural consistency;
- produce small, verifiable, and progressive changes.

## 2. Global Working Rules

### 2.1 Respect the Documents
The agent must consider as the primary source of truth:
1. `docs/en/00-product-vision.md`
2. `docs/en/01-prd.md`
3. future technical documents (`docs/en/02-domain-model.md`, `docs/en/03-tech-spec.md`, etc.)

If an implementation detail is missing:
- do not invent complex logic that was not requested;
- choose the simplest solution consistent with the documents;
- state assumptions explicitly.

### 2.2 Work in Small Increments
Each task must be small, vertical, and easy to verify.

Prefer:
- one screen;
- one CRUD flow;
- one single use-case chain;
- one local change across a few files.

Avoid:
- massive changes;
- broad unrequested refactors;
- simultaneous introduction of many features.

### 2.3 Minimize Context Usage
The agent must:
- read only files needed for the current task;
- avoid rereading the whole project if unnecessary;
- not rewrite files untouched by the task;
- avoid verbose explanations unless requested.

### 2.4 No Unapproved Libraries
Do not introduce new libraries or frameworks without explicit approval, unless they:
- are standard and already part of the approved technical stack;
- are necessary for the project's basic operation.

### 2.5 Preserve Architecture
The agent must not arbitrarily alter:
- naming conventions;
- package structure;
- architectural layers;
- navigation model;
- established data model.

## 3. Development Principles

### 3.1 Simplicity First
When multiple options exist:
- choose the simplest;
- avoid overengineering;
- prefer easy-to-maintain components.

### 3.2 Offline-First
Core features must work without network.

### 3.3 Privacy by Default
No dependency on external services in V1, unless explicitly requested in the future.

### 3.4 UX Priority
For each feature, the agent must prioritize:
- few steps;
- few required fields;
- visual clarity;
- immediate success confirmations;
- easy data entry.

## 4. Task Execution Format
For each implementation task, the agent must follow this order:

1. understand the task;
2. list files to create or edit;
3. declare any minimal assumptions;
4. implement the change;
5. summarize concisely:
   - what was done
   - touched files
   - any limits or follow-up

## 5. Definition of Done
A task is complete only if it:
- compiles logically in the project context;
- respects existing architecture and naming;
- introduces only what is necessary;
- covers the required behavior;
- does not intentionally break existing features;
- keeps UI and domain coherent with documents.

## 6. Rules for Code Changes

### 6.1 File Discipline
- edit as few files as possible;
- do not reformat entire files without reason;
- do not move code between packages without real need;
- do not rename stable symbols without clear benefit.

### 6.2 UI Discipline
- reuse common components when available;
- avoid overly dense screens;
- prefer clear and minimal layouts;
- avoid unnecessary visual effects;
- use short and clear text.

### 6.3 Domain Discipline
- respect the distinction between `income`, `expense`, `transfer`;
- do not treat transfers as expenses or income;
- keep budget logic separate from balance logic;
- avoid shortcuts that compromise financial correctness.

## 7. Communication Rules for the Agent
When replying after a task, the agent must be concise.

Preferred format:
- **Goal**
- **Changes**
- **Touched files**
- **Notes**
- **Suggested next step**

It must not:
- repeat the full content of files;
- explain unrequested theory;
- produce long texts if the task was simple.

## 8. Prompting Rules
Future prompts to the agent must contain:
- task goal;
- reference documents;
- relevant files;
- constraints;
- definition of done.

### Prompt Template
Prefer using this structure:

```text
Task:
[precise task description]

Context:
- docs/en/00-product-vision.md
- docs/en/01-prd.md
- [other relevant files]

Constraints:
- do not introduce new libraries
- keep existing architecture
- modify as few files as possible

Definition of Done:
- [verifiable criteria]
```

## 9. Recommended Task Size
Ideal task size:
- 1 small feature
or
- 1 screen
or
- 1 end-to-end use case
or
- 1 limited local refactor

Good examples:
- create Room entity for Account
- implement Accounts list screen
- add use case to create a Transaction
- implement remaining budget calculation

Bad examples:
- build the whole app
- redo all navigation
- implement all screens together
- fully refactor data/domain/ui

## 10. Decision Logging
When a stable decision emerges, add it to a future `docs/en/decision-log.md` file.

Examples:
- chosen UI stack
- budget formulas
- transfer rules
- naming conventions

## 11. Priority Rules in Case of Conflict
In case of conflicting guidance:
1. approved product/technical documents
2. explicit current task
3. implementation simplicity
4. preference for smaller changes

## 12. Forbidden Behaviors
The agent must not:
- introduce unrequested features;
- create unnecessary future complexity;
- change stack without approval;
- use cloud or external APIs;
- alter already defined business rules;
- treat assumptions as certain requirements.

## 13. Default Technical Bias
Unless specified otherwise, the agent should prefer:
- idiomatic Kotlin
- clear and testable architecture
- small and composable components
- simple UI state
- robust local persistence
- explicit naming
