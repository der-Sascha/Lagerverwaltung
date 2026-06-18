# Kohärenzprüfung Word-Dateien (2026-06-10)

Geprüft: alle 16 .docx (Doku, Projektdateien, Lernhandbuch, Lernmaterial) + Abgleich mit src/, SQL-Skript, pom.xml, CLAUDE.md.

## A. Fehler — beheben

| # | Datei | Befund | Korrektur |
|---|---|---|---|
| 1 | `Doku/Doku_C_Durchfuehrung.docx` | Listing 2 (DBConnection): URL `…3324/krankenhaus_lager` | → `…3324/DOIT` (wie Endabgabe + Code) |
| 2 | `Doku/Doku_E_Anhang.docx` | 3× `krankenhaus_lager`: Anhang-B-Einleitung, `CREATE DATABASE`, Systemvoraussetzungen („Datenbank … muss angelegt sein") | → `DOIT` (Endabgabe ist schon korrekt; Einzelteil wurde bei DB-Umbenennung 10.06. vergessen) |
| 3 | `Doku/Doku_E_Anhang.docx` | Enthält Überschrift „Anhang D – Glossar", eigene Anhangsübersicht sagt aber D = Wochenplan, E = Projektantrag | Entscheiden: Glossar raus (wie Endabgabe) oder Übersicht anpassen |
| 4 | `Doku/Projektdokumentation_Sascha_Schulz.docx` | Anhangsübersicht listet Anhang D (Wochenplan) + E (Projektantrag), es existieren aber keine Abschnitte D/E im Dokument | Entweder Abschnitte D/E ergänzen (Verweis auf Beilagen genügt) oder Übersicht auf A–C kürzen |
| 5 | Endabgabe + `Doku_D` | Testfall-Tabelle springt T8 → T10, T9 fehlt | T10 → T9 umbenennen oder T9 ergänzen |
| 6 | Endabgabe + `Doku_D` | Tippfehler Fazit 8.1: „Unterm Strich hat **des** gut geklappt" | → „das" |
| 7 | Endabgabe + `Doku_E` | FAQ-Tabelle (Anhang C.10) ohne Umlaute: „Loesung", „pruefen", „laesst", „loeschen", „Geloeschte" | Umlaute wiederherstellen (Stilbruch zum Rest) |
| 8 | `Projektdateien/ER_Diagramm_Sascha_Schulz.docx` | Veraltet: `krankenhaus_lager`, `localhost:3306` (Code/Doku: `DOIT`, `127.0.0.1:3324`). Tabellen/Spalten selbst stimmen | DB-Name + JDBC-URL angleichen oder Datei als veraltet ins Archiv |
| 9 | `Lernhandbuch/Lernhandbuch_Pruefungsfragen_Loesungen.docx` | Enthält offenes To-do zu DB-Name/Port-Inkonsistenzen (Kap. 4/5/13) — laut Teil 2 + Teil 4 bereits behoben | To-do entfernen/als erledigt markieren |
| 10 | `CLAUDE.md` (Checkliste) | „Tabellenverzeichnis (11 Tabellen)" — tatsächlich 9 Einträge | Checkliste auf 9 korrigieren |

## B. Hinweise — prüfen

| # | Datei | Befund |
|---|---|---|
| H1 | `Projektantrag_Sascha_Schulz_v2.docx` | Felder Projektbeginn/Projektende leer; „Heidelberg," ohne Datum. Falls Abgabeversion: ausfüllen |
| H2 | `Projektantrag_Sascha_Schulz_v2.docx` | Projektziel-Fließtext ist als „SRH Überschrift 2" formatiert statt als Normaltext |
| H3 | Endabgabe + `Doku_B` | Fiktive Kostenrechnung „150 h Entwicklung und Test" passt nicht zur Phasentabelle: Phasen 2–6 = 130 h, gesamt 180 h. Zahl prüfen/begründen |
| H4 | projektweit | Drei Schreibweisen: „Do-IT-Projektphase" (Deckblatt/Einleitung), „DoIT" (Lernmaterial), „DOIT" (DB, Quelle [4], Vorgaben-Dateinamen). In der Doku eine Schreibweise wählen (Vorgaben nutzen „DOIT") |

## C. Geprüft und konsistent ✅

- Projekttitel identisch in Antrag, Doku_A, Endabgabe
- Verzeichnisse = Body: 5 Abbildungen, 9 Tabellen, 6 Listings (Nummern + Titel deckungsgleich, Doku_A = Endabgabe)
- Pflichtbestandteile vorhanden: Deckblatt, Inhalts-, Abbildungs-, Tabellen-, Quellcode-, Quellenverzeichnis (13 Quellen, Format ok), Abgabeerklärung, Anhang
- Doku_A, Doku_B, Doku_D inhaltlich synchron zur Endabgabe (Doku_C: 1 Abweichung, Doku_E: s. A2/A3)
- Eckdaten überall gleich: 180 h / 192 h / 12 h Puffer, 04.05.–26.06.2026, Abgabe 17.07.2026, Frau Cramer, Gruppe 2551
- Klassennamen Doku ↔ src/ (6 Models, 6 DAOs, MainController, DBConnection, main.fxml) ✅
- Code-Listings ↔ src/: Port 3324, USER root, PASSWORD 1234, DB DOIT ✅
- ER-Spalten ↔ SQL-Skript: identisch ✅
- Antrag-Phasensumme = 180 h = Doku-Phasentabelle ✅


---

## Status (10.06.2026, nach Korrekturlauf)

Alle Befunde A1–A10 und Hinweise H1–H4 behoben:

- A1/A2/A8: DB-Name → `DOIT`, JDBC-URL → `127.0.0.1:3324` (Doku_C, Doku_E, ER-docx)
- A3/A4: Anhänge neu strukturiert — D = Wochenplan, E = Projektantrag, **F = Glossar** (Nutzerentscheidung); Abschnitte + Übersicht + Inhaltsverzeichnis in Doku_E, Doku_A und Endabgabe ergänzt; Glossar (91 Begriffe) in Endabgabe übernommen (S. 24, Anhang zählt nicht zur Seitenbegrenzung)
- A5: T10 → T9 | A6: „des" → „das" | A7: FAQ-Umlaute wiederhergestellt (beide Dateien)
- A9: Prüfungsfragen-To-do als erledigt markiert | A10: CLAUDE.md-Checkliste korrigiert (9 Tabellen) + abgeschnittene Zeile vervollständigt
- H1: Antrag Projektbeginn/-ende eingetragen (04.05./26.06.2026) | H2: Projektziel-Stil → Normal
- H3: Kostenrechnung umformuliert: „150 h Projektarbeit (alle Phasen außer Dokumentation)" (180 − 30 = 150 ✓)
- H4: Schreibweise einheitlich **DOIT** (Deckblatt + Einleitung in Doku_A/B + Endabgabe)
- PDF neu erzeugt; Verzeichnis-Seitenzahlen gegen PDF geprüft (A=19, B=19, C=21, D/E/F=24; 53 Seiten gesamt)
