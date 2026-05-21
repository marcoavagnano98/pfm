# Personal Finance Manager (PFM) — Product Vision

## 1. Product Name
Personal Finance Manager (PFM)

## 2. Product Goal
PFM è un'app Android nativa pensata per consentire a un singolo utente di tracciare manualmente la propria situazione finanziaria personale in modo semplice, veloce, privato e completamente offline.

L'obiettivo principale è offrire uno strumento affidabile per:
- monitorare reddito e spese;
- gestire conti e altre posizioni finanziarie;
- comprendere il bilancio su base temporale;
- impostare e controllare il budget mensile;
- mantenere tutti i dati in locale sul dispositivo, senza dipendere da servizi terzi.

## 3. Target User
Utente singolo, proprietario del dispositivo Android, con forte attenzione a:
- privacy;
- controllo totale dei dati;
- semplicità d'uso;
- velocità di inserimento dati;
- visione chiara della propria situazione finanziaria.

## 4. Problem Statement
Le applicazioni di finanza personale disponibili sul mercato spesso:
- richiedono account e sincronizzazione cloud;
- raccolgono dati sensibili;
- risultano troppo complesse o invasive;
- non sono sufficientemente flessibili per rappresentare situazioni finanziarie personali specifiche.

L'utente ha bisogno di uno strumento personale che permetta di registrare manualmente redditi, spese, conti, investimenti e altre posizioni finanziarie, mantenendo i dati esclusivamente in locale.

## 5. Core Value Proposition
PFM deve essere:
- **privata**: nessun obbligo di usare servizi esterni;
- **offline-first**: il funzionamento base non dipende dalla rete;
- **semplice**: inserire dati deve richiedere il minimo sforzo possibile;
- **estensibile**: devono poter essere aggiunti nuovi tipi di posizione finanziaria;
- **chiara**: deve aiutare a capire rapidamente budget, spese e bilancio.

## 6. Non-Negotiable Constraints
I seguenti vincoli sono obbligatori:

- piattaforma iniziale: **Android**
- linguaggio: **Kotlin**
- dati salvati in modo **persistente e locale**
- nessuna integrazione necessaria con servizi esterni nella V1
- gestione manuale dei dati nella V1
- UI **minimale**, **non invasiva**, **user friendly**
- esperienza di inserimento dati molto rapida
- architettura progettata per futura estendibilità

## 7. Product Principles
1. **Privacy by default**  
   Tutti i dati rimangono sul dispositivo nella V1.

2. **Fast capture first**  
   L'inserimento di una spesa o di un'entrata deve essere il più rapido possibile.

3. **Minimal UI**  
   L'interfaccia deve privilegiare chiarezza, pochi elementi per schermata e basso carico cognitivo.

4. **Finance without friction**  
   L'app deve aiutare l'utente a mantenere l'abitudine di registrare i dati, non ostacolarla.

5. **Progressive extensibility**  
   Le basi del dominio devono permettere l'introduzione futura di nuove tipologie di account, asset e regole di budgeting.

## 8. In-Scope for V1
- gestione di conti e posizioni finanziarie
- inserimento manuale di entrate, spese e trasferimenti
- salvataggio persistente locale
- reddito mensile
- budget mensile
- spese previste
- calcolo del budget residuo
- bilancio per intervalli temporali (mensile, bimestrale, trimestrale, annuale)
- dashboard iniziale con riepilogo sintetico

## 9. Out of Scope for V1
- sincronizzazione cloud
- connessioni bancarie
- import automatici
- multi-device sync
- multi-user
- OCR ricevute o scontrini
- export avanzati
- automazioni finanziarie complesse
- motore avanzato di ricorrenze

## 10. Success Criteria for V1
PFM V1 è considerata valida se consente all'utente di:

- creare e gestire le proprie posizioni finanziarie;
- registrare rapidamente movimenti manuali;
- visualizzare il saldo generale e i movimenti principali;
- impostare un budget mensile;
- vedere quanto può ancora spendere nel mese;
- consultare bilanci su diversi intervalli temporali;
- utilizzare l'app in modo continuativo senza percepirla come pesante o scomoda.
