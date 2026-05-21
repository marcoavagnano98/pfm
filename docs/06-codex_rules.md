# PFM — Codex Operating Rules

## 1. Purpose
Questo documento definisce come l'agente di coding deve lavorare sul progetto PFM per:
- ridurre sprechi di token;
- evitare ambiguità;
- mantenere coerenza architetturale;
- produrre modifiche piccole, verificabili e progressive.

## 2. Global Working Rules

### 2.1 Respect the Documents
L'agente deve considerare come fonte di verità primaria:
1. `docs/00-product-vision.md`
2. `docs/01-prd.md`
3. futuri documenti tecnici (`02-domain-model.md`, `03-tech-spec.md`, ecc.)

Se manca un dettaglio implementativo:
- non inventare logiche complesse non richieste;
- scegliere la soluzione più semplice coerente con i documenti;
- esplicitare le assunzioni.

### 2.2 Work in Small Increments
Ogni task deve essere piccolo, verticale e facilmente verificabile.

Preferire:
- una schermata;
- un flusso CRUD;
- una singola use case chain;
- una modifica locale a pochi file.

Evitare:
- modifiche massive;
- refactor ampi non richiesti;
- introduzione simultanea di molte feature.

### 2.3 Minimize Context Usage
L'agente deve:
- leggere solo i file necessari al task corrente;
- evitare di rileggere l'intero progetto se non serve;
- non riscrivere file non toccati dal task;
- evitare spiegazioni verbose se non richieste.

### 2.4 No Unapproved Libraries
Non introdurre nuove librerie o framework senza approvazione esplicita, a meno che:
- siano standard e già previste dallo stack tecnico approvato;
- siano necessarie al funzionamento di base del progetto.

### 2.5 Preserve Architecture
L'agente non deve alterare arbitrariamente:
- naming conventions;
- package structure;
- architectural layers;
- navigation model;
- data model stabilito.

## 3. Development Principles

### 3.1 Simplicity First
Quando esistono più opzioni:
- scegliere la più semplice;
- evitare overengineering;
- preferire componenti facili da mantenere.

### 3.2 Offline-First
Le feature core devono funzionare senza rete.

### 3.3 Privacy by Default
Nessuna dipendenza da servizi esterni nella V1, salvo esplicita richiesta futura.

### 3.4 UX Priority
Per ogni feature, l'agente deve privilegiare:
- pochi passaggi;
- pochi campi obbligatori;
- chiarezza visiva;
- conferme di successo immediate;
- facilità di inserimento dati.

## 4. Task Execution Format
Per ogni task implementativo, l'agente deve seguire questo ordine:

1. comprendere il task;
2. elencare i file da creare o modificare;
3. dichiarare eventuali assunzioni minime;
4. implementare la modifica;
5. riassumere in modo conciso:
   - cosa è stato fatto
   - file toccati
   - eventuali limiti o follow-up

## 5. Definition of Done
Un task è completato solo se:
- compila logicamente nel contesto del progetto;
- rispetta architettura e naming esistenti;
- introduce solo il minimo necessario;
- copre il comportamento richiesto;
- non rompe volontariamente feature già esistenti;
- mantiene UI e dominio coerenti con i documenti.

## 6. Rules for Code Changes

### 6.1 File Discipline
- modificare il minor numero possibile di file;
- non riformattare file interi senza motivo;
- non spostare codice tra package senza necessità reale;
- non rinominare simboli già stabili senza beneficio chiaro.

### 6.2 UI Discipline
- riusare componenti comuni quando disponibili;
- evitare schermate troppo dense;
- privilegiare layout chiari e minimali;
- evitare effetti visivi inutili;
- usare testi brevi e chiari.

### 6.3 Domain Discipline
- rispettare la distinzione tra `income`, `expense`, `transfer`;
- non trattare i trasferimenti come spese o entrate;
- mantenere separate logiche di budget e logiche di saldo;
- evitare scorciatoie che compromettano correttezza finanziaria.

## 7. Communication Rules for the Agent
Quando risponde dopo un task, l'agente deve essere conciso.

Formato preferito:
- **Obiettivo**
- **Modifiche**
- **File toccati**
- **Note**
- **Next step suggerito**

Non deve:
- ripetere l'intero contenuto dei file;
- spiegare teoria non richiesta;
- produrre lunghi testi se il task era semplice.

## 8. Prompting Rules
I prompt futuri verso l'agente devono contenere:
- obiettivo del task;
- documenti di riferimento;
- file rilevanti;
- vincoli;
- definition of done.

### Prompt Template
Usare preferibilmente questa struttura:

```text
Task:
[descrizione precisa del task]

Context:
- docs/00-product-vision.md
- docs/01-prd.md
- [altri file rilevanti]

Constraints:
- non introdurre nuove librerie
- mantieni architettura esistente
- modifica il minor numero possibile di file

Definition of Done:
- [criteri verificabili]
```

## 9. Recommended Task Size
Dimensione ideale di un task:
- 1 feature piccola
oppure
- 1 schermata
oppure
- 1 use case end-to-end
oppure
- 1 refactor locale limitato

Esempi buoni:
- creare entity Room per Account
- implementare schermata lista Account
- aggiungere use case per creare una Transaction
- implementare calcolo budget residuo

Esempi cattivi:
- costruire tutta l'app
- rifare tutta la navigazione
- implementare tutte le schermate insieme
- fare refactor totale di data/domain/ui

## 10. Decision Logging
Quando emerge una decisione stabile, aggiungerla a un futuro file `docs/decision-log.md`.

Esempi:
- stack UI scelto
- formule di budget
- regole sui trasferimenti
- convenzioni naming

## 11. Priority Rules in Case of Conflict
In caso di conflitto tra indicazioni:
1. documenti prodotto/tecnici approvati
2. task corrente esplicito
3. semplicità implementativa
4. preferenza per minori modifiche

## 12. Forbidden Behaviors
L'agente non deve:
- introdurre feature non richieste;
- creare complessità futura non necessaria;
- cambiare stack senza approvazione;
- usare cloud o API esterne;
- alterare regole di business già definite;
- trattare ipotesi come requisiti certi.

## 13. Default Technical Bias
Finché non specificato diversamente, l'agente dovrebbe preferire:
- Kotlin idiomatico
- architettura chiara e testabile
- componenti piccoli e componibili
- stato UI semplice
- persistenza locale robusta
- naming esplicito
