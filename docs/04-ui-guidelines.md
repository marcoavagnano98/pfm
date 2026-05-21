# Personal Finance Manager (PFM) — UI Guidelines v1

## 1. Purpose
Questo documento definisce le linee guida UI/UX per PFM V1.

L'obiettivo è costruire un'interfaccia:
- minimale;
- veloce da usare;
- chiara;
- poco invasiva;
- ottimizzata per inserimento dati frequente;
- sostenibile nel tempo per uso personale quotidiano.

## 2. Core UI Principles

### 2.1 Minimal First
Ogni schermata deve mostrare solo ciò che serve davvero.  
Evitare:
- densità eccessiva;
- troppe CTA primarie;
- overload visivo;
- informazioni secondarie in primo piano.

### 2.2 Fast Data Entry
Le azioni più frequenti devono essere facili e rapide:
- aggiungere una spesa;
- aggiungere un'entrata;
- aggiungere un trasferimento;
- consultare budget residuo;
- controllare saldi principali.

### 2.3 Low Cognitive Load
La UI deve ridurre al minimo lo sforzo mentale:
- pochi campi obbligatori;
- etichette chiare;
- numeri ben leggibili;
- gerarchia visiva semplice;
- pattern coerenti tra schermate.

### 2.4 Calm Finance Experience
L'app non deve comunicare ansia o aggressività visiva.  
Preferire:
- colori sobri;
- spacing ampio;
- feedback discreti;
- componenti puliti;
- animazioni minime.

### 2.5 Progressive Disclosure
Mostrare prima il necessario.  
Campi e opzioni avanzate devono essere:
- opzionali;
- secondarie;
- nascoste finché non utili.

## 3. Visual Style Direction

## 3.1 General Tone
- sobrio
- pulito
- ordinato
- moderno
- funzionale
- personale

## 3.2 Avoid
- dashboard troppo rumorose
- troppi colori saturi
- cards gigantesche inutili
- icone decorative senza funzione
- elementi gamificati non richiesti
- layout che ricordano prodotti finanziari enterprise

## 3.3 Prefer
- superfici semplici
- cards compatte
- spacing consistente
- tipografia leggibile
- importi in evidenza
- azioni frequenti sempre accessibili

## 4. Information Hierarchy

Ogni schermata dovrebbe seguire questa gerarchia:

1. informazione principale
2. azione primaria
3. informazioni di supporto
4. dettagli opzionali

Esempio Home:
1. saldo totale / budget residuo
2. aggiungi movimento
3. entrate e uscite del mese
4. dettaglio periodo

## 5. Navigation Guidelines

## 5.1 Main Navigation
Usare bottom navigation con massimo 5 sezioni:

- Home
- Accounts
- Transactions
- Budget
- Settings

## 5.2 Navigation Rules
- evitare livelli profondi inutili;
- create/edit come schermate dedicate;
- mantenere nomi semplici e stabili;
- evitare percorsi lunghi per azioni frequenti.

## 6. Screen Guidelines

## 6.1 Home
Scopo:
- offrire una fotografia immediata della situazione attuale.

Deve mostrare in alto:
- saldo totale
- budget residuo
- netto mese corrente

Elementi secondari:
- entrate mese
- uscite mese
- selettore periodo semplice
- scorciatoia per nuovo movimento

Regole:
- niente sovraccarico di grafici nella V1;
- numeri principali leggibili a colpo d'occhio;
- quick action sempre evidente.

## 6.2 Accounts Screen
Scopo:
- vedere rapidamente tutte le posizioni finanziarie.

Ogni riga account deve mostrare:
- nome
- tipo
- saldo corrente
- stato archivio se rilevante

Regole:
- lista pulita;
- nessuna informazione superflua nella riga;
- dettaglio/modifica accessibile con tap semplice;
- CTA per aggiunta account ben visibile.

## 6.3 Transactions Screen
Scopo:
- vedere e aggiungere movimenti rapidamente.

Ogni riga movimento deve mostrare:
- tipo
- importo
- data
- account principale
- categoria se disponibile

Regole:
- ordinamento cronologico chiaro;
- differenza visiva tra entrata e spesa leggibile ma non aggressiva;
- i trasferimenti devono essere distinguibili;
- il filtro periodo deve essere semplice.

## 6.4 Transaction Form
Scopo:
- inserire un movimento nel minor tempo possibile.

Campi visibili subito:
- tipo movimento
- importo
- data
- account rilevante
- categoria

Campi secondari:
- nota
- opzioni avanzate

Regole:
- cambiare i campi mostrati in base al tipo:
  - income
  - expense
  - transfer
- validazioni immediate ma sobrie;
- CTA salva sempre chiara;
- minimizzare lo scrolling.

## 6.5 Budget Screen
Scopo:
- configurare reddito, target e spese previste;
- visualizzare budget disponibile e residuo.

Gerarchia:
1. budget disponibile
2. budget residuo
3. reddito mensile
4. budget target
5. elenco spese previste

Regole:
- numeri principali in alto;
- configurazione semplice;
- spese previste modificabili facilmente;
- evitare tabelle dense.

## 6.6 Settings Screen
Scopo:
- ospitare impostazioni secondarie.

Regole:
- schermata semplice;
- voci raggruppate;
- evitare complessità nella V1.

## 7. Component Guidelines

## 7.1 Summary Cards
Usare cards riusabili per:
- saldo totale
- budget residuo
- netto periodo
- entrate
- uscite

Regole:
- una singola metrica primaria per card;
- testo secondario ridotto;
- altezza coerente tra cards;
- niente elementi grafici inutili.

## 7.2 Rows
Rows per account e transaction devono essere:
- facilmente scannerizzabili;
- consistenti tra liste;
- con spacing sufficiente;
- con importo ben leggibile.

## 7.3 Buttons
Preferenze:
- una primary action chiara per schermata;
- secondary action meno prominenti;
- evitare più pulsanti primari affiancati.

## 7.4 FAB
Utilizzare una FAB per quick add movimento se coerente con la schermata.
Regole:
- una sola FAB visibile per contesto;
- icona chiara;
- eventualmente label breve.

## 7.5 Empty States
Ogni sezione principale deve avere empty states utili.

Esempi:
- nessun account creato
- nessun movimento registrato
- nessuna spesa prevista
- nessun dato nel periodo selezionato

Regole:
- tono semplice;
- CTA chiara;
- niente testo lungo o paternalistico.

## 8. Form UX Rules
- usare tastiera numerica per importi;
- impostare default intelligenti quando possibile;
- minimizzare i campi richiesti;
- validare senza interrompere il flusso;
- mostrare errori vicino al campo;
- evitare modali inutili.

## 9. Typography Rules
- numeri finanziari sempre leggibili e prominenti;
- titoli chiari ma non enormi;
- testo secondario discreto;
- evitare troppi livelli tipografici.

## 10. Color Rules
- palette sobria;
- verde/rosso usati con moderazione per importi;
- non affidarsi solo al colore per comunicare significato;
- contrasto sufficiente;
- modalità chiara iniziale, dark theme opzionale se semplice da supportare.

## 11. Feedback Rules
Feedback da prevedere:
- salvataggio riuscito
- validazione non superata
- nessun dato disponibile
- operazione completata

Regole:
- feedback brevi;
- non invadenti;
- coerenti;
- nessun linguaggio tecnico.

## 12. Accessibility Basics
Anche nella V1, rispettare almeno:
- testo leggibile
- touch target adeguati
- contrasto sufficiente
- label chiare
- contenuti non dipendenti solo dal colore

## 13. UX Anti-Patterns to Avoid
Non fare:
- schermate con troppi campi aperti insieme;
- form lunghi senza gerarchia;
- grafici pesanti non essenziali;
- colori eccessivi;
- CTA ambigue;
- inserimento movimenti macchinoso;
- terminologia finanziaria inutilmente complessa.

## 14. Default UX Bias
Se esistono più opzioni:
- scegliere quella con meno attrito;
- scegliere quella più leggibile;
- scegliere quella che velocizza l'inserimento dati;
- scegliere quella più coerente con una app personale e minimale.
