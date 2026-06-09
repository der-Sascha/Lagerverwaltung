# Dateiübersicht — Lagerverwaltung-Projekt (SRH)

**Stand:** 2026-06-07 (nach Ordner-Aufräumen)

---

## Projektstand auf einen Blick (für neue Chats)

- **Block 1 (KW19–KW21) abgeschlossen:** Phasen 1–3 fertig (DB-Schema, 6 Tabellen, DAO-Schicht, ~1.700 Zeilen Code). UI (MainController, 703 Zeilen) bereits in KW21 vorgearbeitet.
- **Block 2 startet KW24 (08.06.2026):** Phasen 4–7 (UI-Grundfunktionen, erweiterte Funktionen, Tests, Doku/Abgabe).
- **Projektdokumentation:** fertig zusammengeführt und **vorgabenkonform** (Arial 12pt, 1,5-zeilig, Ränder 2,5/2,5/2,5/2,0 cm, alle Pflichtverzeichnisse, 14 Quellen, Benutzerhandbuch in Anhang C, schema-korrektes ER-Diagramm). Endabgabe-Datei: `Projektdokumentation_Sascha_Schulz.docx` (+ PDF) im Wurzelverzeichnis.
- **Noch offen (nach Phase 6/7):** Testergebnisse (4.2), Abnahmeprotokoll (4.3.4) und Reflexion (Kap. 5) sind als **klar markierte Entwürfe** drin und werden nach Durchführung final ausgefüllt.
- **Abgabe:** PDF in Moodle bis **17.07.2026**.

---

## Wurzelverzeichnis

| Datei | Zweck | Status |
|---|---|---|
| `CLAUDE.md` | Kontext für jeden neuen Chat | aktuell |
| `Projektdokumentation_Sascha_Schulz.docx` | **finale Abgabe-Dokumentation** (zusammengeführt) | vorgabenkonform, Entwürfe für Tests/Reflexion |
| `Projektdokumentation_Sascha_Schulz.pdf` | PDF-Export der Abgabe | aktuell |

## `Präsentationen/` — Projektpräsentation

| Datei | Inhalt | Status |
|---|---|---|
| `Praesentation_Lagerverwaltung_Sascha_Schulz.pptx` | **Projektpräsentation**, 15 Folien (16:9), mit Sprechernotizen | aktuell |
| `Praesentation_Lagerverwaltung_Sascha_Schulz.pdf` | PDF-Export der Folien | aktuell |
| `assets/build.js` | pptxgenjs-Build-Skript (reproduzierbar) | aktuell |
| `assets/er.png`, `bg_dark.png`, `bg_light.png` | Grafiken/Hintergründe der Folien | aktuell |

Roter Faden: Amondis-Berufsgeschichte (Anwender) läuft synonym parallel zum Projekt (Entwickler). Quelle der Berufsdaten: `Arbeitszeugnis/Arbeitszeugnis Diakonissen.pdf`. Geprüft gegen IHK-Rubrik `Vorgaben/07_TN_Bewertung-Präsentation.pdf` (30/30/20/20).

## `Doku/` — Arbeits-Einzelteile der Dokumentation

`Doku_A`–`Doku_E` sind Bausteine derselben Dokumentation (aufgeteilt zum Bearbeiten). Änderungen immer im jeweiligen Teil, dann in `Projektdokumentation_Sascha_Schulz.docx` (Wurzel) zusammenführen → PDF → Moodle.

| Datei | Inhalt |
|---|---|
| `Doku_A_Verzeichnisse.docx` | Deckblatt, Inhalts-, Abbildungs-, Tabellen-, Quellenverzeichnis |
| `Doku_B_Ausgangssituation.docx` | Kap. 1 Ausgangssituation + Kap. 2 Ressourcen/Planung |
| `Doku_C_Durchfuehrung.docx` | Kap. 3 Durchführung (7 Phasen, Code-Beispiele) |
| `Doku_D_Ergebnisse.docx` | Kap. 4 Ergebnisse/Abnahme + Kap. 5 Reflexion + Abgabeerklärung |
| `Doku_E_Anhang.docx` | Anhang A–C (ER-Diagramm, SQL-Auszug, Benutzerhandbuch) |

## `MD/` — Markdown-Notizen

| Datei | Zweck | Status |
|---|---|---|
| `Projektplan.md` | Phasen, Stunden, Wochenplan, Datenmodell | aktuell |
| `Projekttagebuch.md` | laufendes Log (Änderungen/Probleme/Erkenntnisse) | aktuell (Stand 07.06.) |
| `Entscheidungen.md` | Designentscheidungen D-001–D-006 | aktuell |
| `Dateiuebersicht.md` | diese Datei | aktuell |
| `PROJEKT_ANALYSE_VOLLSTAENDIG.md` | große Übergabe-Analyse (Differenzen, Aufgabenplan) | Referenz (teils überholt) |
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

| Datei | Inhalt |
|---|---|
| `Glossar_Java_API.docx` | Java-API-Glossar (48 Begriffe) |
| `Java_Handbuch_Start.docx` | Java-Lernnotizen |
| `Java_Konzept_Zusammenhaenge.docx` | Java-Konzepte/Zusammenhänge |
| `SPICKZETTEL.docx` | Kurzreferenz (Word) |
| `how_to_codereview.pdf` | SRH-Anleitung Code-Review-Phase |

## `Lernhandbuch/` — persönliches Lernhandbuch (14 Kapitel)

| Datei | Inhalt |
|---|---|
| `Lernhandbuch_Lagerverwaltung_Krankenhaus_korrigiert.docx` | Hauptdatei Lernhandbuch |
| `Teil1_Intro_Kap1-3.docx` | Kapitel 1–3 |
| `Teil2_Kap4-6_neu.docx` | Kapitel 4–6 |
| `Teil3_Kap7-9_neu.docx` | Kapitel 7–9 |
| `Teil4_Kap10-14_neu.docx` | Kapitel 10–14 |
| `Glossar_Vereinigt.docx` | zusammengeführtes Glossar |

## `Vorgaben/` — SRH-Originale (nicht bearbeiten)

Anleitung, Leitfaden, Vorlagen (Antrag/Wochenplan), Bewertungsbögen (Projektarbeit 1+2, Präsentation, Fachgespräch), zwei Beispiel-Dokumentationen, `11_DOIT_2551_Dokumentationsvorgaben.pdf`.

## `Archiv/` — historischer/abgelöster Bestand

| Datei | Grund |
|---|---|
| `Benutzerhandbuch_Sascha_Schulz.docx` | Inhalt jetzt in Anhang C der Doku — Altbestand |
| `Projektantrag_Sascha_Schulz_v2.docx` | Meilenstein-Backup (12.05.) |
| `STATUS_KW21_CHECKPOINT.txt` | Statusbericht KW21 |
| `KOHAEREZ_AUDIT_2026-05-18.md` | Kohärenz-Audit 18.05. |

## Weitere Ordner

| Ordner | Inhalt |
|---|---|
| `Ablage/Audits_und_Entwuerfe/` | Audit-Definitionen/Entwürfe |
| `Projektmanagement/` | `Kohaerenzpruefung_Lernhandbuch.md` |
| `_backup_2026-06-07/` | Backup der Original-Doku vor der Überarbeitung 07.06. |

---

## Bewertung (Gewichtung)

| Teil | Anteil | Bewertungsbogen |
|---|---|---|
| Dokumentation | 30 % | `Vorgaben/05_…Projektarbeit-1.pdf` + `06_…Projektarbeit-2.pdf` |
| Präsentation | 20 % | `Vorgaben/07_…Präsentation.pdf` |
| Fachgespräch | 50 % | `Vorgaben/08_…Fachgespräch.pdf` |

## Abgabe-Deadline

**17.07.2026** — PDF-Upload in Moodle. Verspätung wirkt sich negativ auf die Note aus.
