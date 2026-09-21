# Dateiübersicht — Lagerverwaltung-Projekt (SRH)

**Stand:** 2026-06-10 (nach KW24-Aufräumen)

---

## Projektstand auf einen Blick

- **Block 1 (KW19–KW21) abgeschlossen:** Phasen 1–3 fertig (DB-Schema, 6 Tabellen, DAO-Schicht, ~1.700 Zeilen Code). UI (MainController, 703 Zeilen) bereits in KW21 vorgearbeitet.
- **Block 2 läuft (KW24, 08.06.–12.06.2026):** Phase 4 UI-Grundfunktionen (40h) aktiv. Phasen 5–7 folgen KW25–KW26.
- **Projektdokumentation:** fertig zusammengeführt und **vorgabenkonform** (Times New Roman 12pt, 1,5-zeilig, Ränder 2,5/2,5/2,5/2,0 cm, alle Pflichtverzeichnisse, 13 Quellen, Benutzerhandbuch in Anhang C, schema-korrektes ER-Diagramm). Endabgabe-Datei: `Doku/Projektdokumentation_Sascha_Schulz.docx` (+ PDF).
- **Noch offen (nach Phase 6/7):** Testergebnisse (4.2), Abnahmeprotokoll (4.3.4) und Reflexion (Kap. 5) sind als **klar markierte Entwürfe** drin und werden nach Durchführung final ausgefüllt.
- **Abgabe:** PDF in Moodle bis **17.07.2026**.

---

## Wurzelverzeichnis

| Datei | Zweck | Status |
|---|---|---|
| `README.md` | Projekt-Übersicht (GitHub) | aktuell |
| `pom.xml` | Maven-Build-Konfiguration | aktuell |
| `lagerverwaltung.iml` | IntelliJ-Modulkonfiguration | aktuell |

## `Präsentationen/` — Projektpräsentation

| Datei | Inhalt | Status |
|---|---|---|
| `Praesentation_Lagerverwaltung_Sascha_Schulz.pptx` | **Projektpräsentation**, 11 Folien (16:9), mit Sprechernotizen; Reise-Struktur Zettel→Amondis→Heute | aktuell |
| `Praesentation_Lagerverwaltung_Sascha_Schulz.pdf` | PDF-Export der Folien (zuletzt 10.06.) | aktuell |
| `assets/build.js` | Build-Skript (@resvg/resvg-js, reproduzierbar) | aktuell |
| `assets/er.png`, `bg_dark.png`, `bg_light.png` | Grafiken/Hintergründe der Folien | aktuell |

Roter Faden: Amondis-Berufsgeschichte (Anwender) läuft synonym parallel zum Projekt (Entwickler). Geprüft gegen Rubrik `Vorgaben/07_TN_Bewertung-Präsentation.pdf` (30/30/20/20).

## `Doku/` — Arbeits-Einzelteile der Dokumentation + Masterdatei

`Doku_A`–`Doku_E` sind Bausteine derselben Dokumentation (aufgeteilt zum Bearbeiten). Änderungen immer im jeweiligen Teil, dann in `Projektdokumentation_Sascha_Schulz.docx` zusammenführen → PDF → Moodle.

| Datei | Inhalt |
|---|---|
| `Projektdokumentation_Sascha_Schulz.docx` | **finale Abgabe-Dokumentation** (zusammengeführt, 13 Quellen, Amshove-Schema) | 
| `Projektdokumentation_Sascha_Schulz.pdf` | PDF-Export der Abgabe (zuletzt 10.06.) |
| `Doku_A_Verzeichnisse.docx` | Deckblatt, Inhalts-, Abbildungs-, Tabellen-, Quellenverzeichnis |
| `Doku_B_Ausgangssituation.docx` | Kap. 1 Ausgangssituation + Kap. 2 Ressourcen/Planung |
| `Doku_C_Durchfuehrung.docx` | Kap. 3 Durchführung (7 Phasen, Code-Beispiele) |
| `Doku_D_Ergebnisse.docx` | Kap. 4 Ergebnisse/Abnahme + Kap. 5 Reflexion + Abgabeerklärung |
| `Doku_E_Anhang.docx` | Anhang A–D (ER-Diagramm, SQL-Auszug, Benutzerhandbuch, Glossar) |

## `MD/` — Markdown-Notizen

| Datei | Zweck | Status |
|---|---|---|
| `Projektplan.md` | Phasen, Stunden, Wochenplan, Datenmodell | aktuell |
| `Projekttagebuch.md` | laufendes Log (Änderungen/Probleme/Erkenntnisse) | aktuell (Stand 07.06.) |
| `Entscheidungen.md` | Designentscheidungen D-001–D-006 | aktuell |
| `Dateiuebersicht.md` | diese Datei | aktuell |
| `KAPITEL_5_6_PLAN.md` | Lernplan Kapitel 5+6 (DBConnection + Models) | Referenz |
| `GLOSSAR.md` | Glossar Fachbegriffe (Lernmaterial) | persönlich |
| `LERNMATERIAL_README.md` | Übersicht Lernmaterial | persönlich |
| `SPICKZETTEL.md` | Kurzreferenz (Markdown) | persönlich |
| `SPICKZETTEL_ERWEITERUNG.md` | erweiterter Spickzettel | persönlich |
| `FARB_ANALYSE.md` | Farb-Audit IDE-Syntax (nicht mehr aktiv genutzt) | Altbestand |

## `Projektdateien/` — Quellen & Artefakte

| Datei | Zweck | Status |
|---|---|---|
| `DOIT Krankenhaus.drawio` | **Standard-ER-Modell** (maßgebliche Quelle, korrektes Schema) | aktiv |
| `ER_Diagramm_aktuell.png` | schema-korrektes ER-Bild (in Anhang A der Doku eingebettet) | aktiv |
| `ER_Diagramm_Sascha_Schulz.docx` | älteres ER-Dokument (eingebettetes PNG veraltet) | Altbestand |
| `testdaten_krankenhaus_lager.sql` | DB-Schema + Testdaten (6 Tabellen) | aktiv |
| `Projektantrag_Sascha_Schulz_v2.docx` | **finaler Projektantrag** (180 h, 7 Phasen) | aktiv |
| `Wochenplan_Sascha_Schulz.xlsx` | Wochenplan-Tracking | aktiv |

## `Lernmaterial/` — Java-Lerndokumente

Konsolidiert 10.06.: 4 Dateien (Glossar_Java_API, Java_Handbuch_Start, Java_Konzept_Zusammenhaenge, SPICKZETTEL) → Archiv/abgeloest_Lernmaterial_2026-06-10/

| Datei | Inhalt |
|---|---|
| `Spickzettel_DoIT.docx` | **Konsolidierter Spickzettel** (5 S.): Teil A IntelliJ, Teil B Java/JDBC, Teil C Architektur/Warum (mit Diagramm) |

## `Lernhandbuch/` — persönliches Lernhandbuch (14 Kapitel)

Hauptdatei (korrigiert.docx) → Archiv/ verschoben (20.05., veraltet). Aktiver Stand = Einzelteile Teil1–4.

| Datei | Inhalt |
|---|---|
| `Teil1_Intro_Kap1-3_NEU.docx` | Kapitel 1–3 (IntelliJ-Symbole/-Farben, Syntax-Farben korrigiert 10.06.) |
| `Teil2_Kap4-6_neu.docx` | Kapitel 4–6 (DB-Name auf DOIT korrigiert 10.06.) |
| `Teil3_Kap7-9_neu.docx` | Kapitel 7–9 |
| `Teil4_Kap10-14_neu.docx` | Kapitel 10–14 (DB-Name auf DOIT korrigiert 10.06.) |
| `Glossar_Vereinigt.docx` | zusammengeführtes Glossar (90 Begriffe, Anhang D der Doku) |
| `Lernhandbuch_Pruefungsfragen_Loesungen.docx` | Prüfungsfragen + Lösungen |

## `Vorgaben/` — SRH-Originale (nicht bearbeiten)

Anleitung, Leitfaden, Vorlagen (Antrag/Wochenplan), Bewertungsbögen (Projektarbeit 1+2, Präsentation, Fachgespräch), zwei Beispiel-Dokumentationen, `11_DOIT_2551_Dokumentationsvorgaben.pdf`.

## `Archiv/` — historischer/abgelöster Bestand

| Datei/Ordner | Grund |
|---|---|
| `Benutzerhandbuch_Sascha_Schulz.docx` | Inhalt jetzt in Anhang C der Doku — Altbestand |
| `Lernhandbuch_Lagerverwaltung_Krankenhaus_korrigiert.docx` | veraltet (ohne Kap. 10a), ersetzt durch Teil1–4 |
| `Projektantrag_Sascha_Schulz_v2.docx` | Meilenstein-Backup (12.05.) |
| `_backup_2026-06-07/` | Backup der Original-Doku vor Überarbeitung 07.06. (Doku_A–E + Master) |
| `abgeloest_Lernmaterial_2026-06-10/` | 4 abgelöste Lernmaterial-Dateien (10.06.): Glossar_Java_API, Java_Handbuch_Start, Java_Konzept_Zusammenhaenge, SPICKZETTEL |

## Weitere Ordner

| Ordner | Inhalt |
|---|---|
| `Ablage/` | Zwischenablage für Entwürfe (kein aktiver Inhalt im Repo) |
| `Projektmanagement/` | `Kohaerenzpruefung_Word-Dateien_2026-06-10.md` |
| `Vorgaben/` | SRH-Originale (Anleitungen, Vorlagen, Bewertungsbögen, Beispiel-Dokus) — nicht bearbeiten |
| `src/` | Java/Maven-Quellcode (de.doit: db/, model/, dao/, controller/, Main.java, Launcher.java; FXML unter resources/fxml/) |

---

## Bewertung (Gewichtung)

| Teil | Anteil | Bewertungsbogen |
|---|---|---|
| Dokumentation | 30 % | `Vorgaben/05_…Projektarbeit-1.pdf` + `06_…Projektarbeit-2.pdf` |
| Präsentation | 20 % | `Vorgaben/07_…Präsentation.pdf` |
| Fachgespräch | 50 % | `Vorgaben/08_…Fachgespräch.pdf` |

## Abgabe-Deadline

**17.07.2026** — PDF-Upload in Moodle. Verspätung wirkt sich negativ auf die Note aus.
