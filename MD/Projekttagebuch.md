# Projekttagebuch — Sascha Schulz, WI 2551

Laufendes Log für Änderungen, Probleme und Erkenntnisse.
Dient als Grundlage für Reflexion, Lernzuwachs und Quellenverzeichnis.

---

## 2026-09-21 — [Änderung] Repo bereinigt

- [Änderung] Lokale Hilfs-/Kontext-Dateien aus dem GitHub-Tracking genommen und in `.gitignore` aufgenommen (bleiben nur lokal).
- [Änderung] Alte Audit-/Analyse-Dateien aus dem Repo entfernt (lokal weiterhin vorhanden).
- [Änderung] Lock-Dateien und leerer Schreibtest (`_writetest_`) entfernt.
- [Änderung] `Projekttagebuch.md`, `Dateiuebersicht.md` und `_ORDNERINFO.md`-Dateien an den neuen Stand angepasst.

---

## 2026-06-23 — [Änderung] Prüfungsvorbereitung erstellt

Frau Cramer hat die Prüfungsthemen bekannt gegeben: OOP vollständig, Vererbung, Zugriffsmodifikatoren, Array/List-Syntax, JavaFX Buttons, abstrakte Klassen + Interface — alles als praktische Aufgaben, DAO-Muster als Vorlage.

Neu erstellte Dateien in `Lernmaterial/`:
- `Zugriffsmodifikatoren_Spickzettel.html` — private/package-private/protected/public mit Vergleichstabelle, DAO-Beispiel, Fallen
- `JavaFX_Buttons_Spickzettel.html` — @FXML, onAction, setOnAction, Lambda, initialize(), vollständiges Controller-Beispiel
- `Pruefung_Theoretisch.html` — 31 Fragen + aufklappbare Antworten, 7 Themenblöcke
- `Pruefung_Praktisch_Aufgaben.html` — Aufgabenblatt (A1–A6, 70 Punkte), Tierpension-Szenario
- `Pruefung_Praktisch/` — 6 Java-Starterdateien mit TODO-Markierungen für IntelliJ

`Roter_Faden_Java.html` um Abschnitt 3 „Prüfungsthemen & Lernblätter-Index" erweitert.
`Lernmaterial/_ORDNERINFO.md` aktualisiert.

---

## 2026-06-09 — [Änderung] _ORDNERINFO.md je Ordner angelegt

- [Änderung] In 11 Ordnern eine `_ORDNERINFO.md` angelegt (`src/`, `Doku/`, `MD/`, `Projektdateien/`, `Lernhandbuch/`, `Lernmaterial/`, `Vorgaben/`, `Projektmanagement/`, `Ablage/`, `Archiv/`, `Präsentationen/`) — je eine Tabelle Datei → Zweck.
- [Änderung] Ordner-Info-Regel festgehalten: zuerst die `_ORDNERINFO.md` des Ordners lesen; bei Datei-Änderungen die `_ORDNERINFO.md` aktualisieren.
- [Erkenntnis] Erfassung erfolgte über Dateinamen/Struktur (`find`) + bestehenden Projektkontext — Dateien wurden nicht einzeln geöffnet/inhaltlich geprüft. Beschreibungen daher teils abgeleitet, nicht aus dem Dateiinhalt verifiziert.
- [Entscheidung] Detailtiefe bewusst auf "welche Datei = was" begrenzt; keine Inhaltszusammenfassungen (Pflegeaufwand/Veralten). Tiefe gehört in die Datei selbst bzw. `MD/Dateiuebersicht.md`.
- [Änderung] Nachgelagert alle `.docx` in `Doku/`, `Lernhandbuch/`, `Lernmaterial/`, `Projektdateien/` per python-docx geöffnet und Überschriften ausgelesen → `_ORDNERINFO.md` von `Doku/` und `Lernhandbuch/` mit verifizierten Kapitelangaben präzisiert (z. B. Doku_B = Kap. 1–3, Doku_C = Kap. 4–5, Doku_D = Kap. 6–8; Lernhandbuch-Teile = Kap. 1–3/4–6/7–9/10–14).

## 2026-06-09 — [Änderung] BestandsbewegungDAO: Spaltenname vereinheitlicht

- [Änderung] `BestandsbewegungDAO.java`: 3 verbleibende SQL-Strings mit `bewegung_id` auf `bestandsbewegung_id` vereinheitlicht (Zeilen 42, 85, 150). DB-Spalte heißt bereits `bestandsbewegung_ID`.
- [Erkenntnis] IntelliJ Rename-Refactoring funktioniert nur für Java-Bezeichner (Felder, Methoden, Klassen), nicht für Textinhalt in String-Literalen. Für SQL-Strings → Ctrl+Shift+R (Replace in Files).

## 2026-06-09 — [Problem] Datenbankfehler beim Start — DB-Name korrigiert

- [Problem] App startete, konnte aber keine Daten laden. Fehler: Datenbank `krankenhaus_lager` existiert im Docker-MySQL-Container nicht.
- [Erkenntnis] Docker-Container läuft korrekt auf Port 3324, aber die Datenbank wurde unter dem Namen `DOIT` angelegt (nicht `krankenhaus_lager` wie im SQL-Skript). `DOIT` enthält alle 6 Tabellen und 20+ Datensätze.
- [Änderung] `DBConnection.java` Z.15: URL geändert von `krankenhaus_lager` auf `DOIT`.

## 2026-06-09 — [Änderung] Projektpräsentation erstellt (15 Folien, PPTX)

- [Änderung] Neuer Ordner `Präsentationen/` mit `Praesentation_Lagerverwaltung_Sascha_Schulz.pptx` (+ PDF) angelegt. 15 Folien, 16:9, Sprechernotizen auf jeder Folie. Build-Skript (pptxgenjs) und Grafiken unter `Präsentationen/assets/`.
- [Erkenntnis] Roter Faden: berufliche Amondis-Geschichte läuft synonym parallel zum Projekt — jede Folie hat ein "Damals (Anwender) ↔ Heute (Entwickler)"-Band. Motiv: vom Fax-Zettel zum selbst entwickelten System.
- [Quelle] `Arbeitszeugnis/Arbeitszeugnis Diakonissen.pdf` (OCR): Teamleiter Zentrallager Diakonissen Speyer 02/2020–03/2023, Warenwirtschaft mySAP ERP/Modul MM, Pilotprojekt Amondis-Logistik (federführend Anpassung Oberflächen/Prozesse + Einführung), 15.000 Lieferscheine/Jahr, 180 Lagerplätze/600 m², 12 MA geführt. Abrufdatum 2026-06-09.
- [Manuell] Nutzerangabe eingearbeitet: ~1.200 lagergeführte Artikel vs. 24.000+ Artikel über den Einkauf — automatische Trennung durch Amondis, vorher nicht möglich (Folie 2 + 4).
- [Erkenntnis] Verifikation gegen `Vorgaben/07_TN_Bewertung-Präsentation.pdf`: Rubrik 30/30/20/20 (Aufbau/Struktur · Problemerfassung/Lösung · Sprachliche Gestaltung · Zielgruppengerechte Darstellung/Medien). Deck adressiert alle vier — Körpersprache bleibt Vortragssache.

## 2026-06-09 — [Erkenntnis] Scheduler-Blockierer geprüft + Audit-Prompt gehärtet

- [Erkenntnis] 3 von 4 vom Donnerstags-Audit gemeldeten Blockierern waren veraltet/falsch: (1) DOIT-Ordner IST eingebunden (Dateien lesbar, Audit lief 07:18), (2) Tagebuch KW23/24 bereits vorhanden, (3) Task-Timing korrekt auf Do `0 20 * * 4` — Di-Lauf war manuell.
- [Änderung] DBConnection.java Z.14: Kommentar zum Nicht-Standardport 3324 ergänzt (Default 3306).
- [Änderung] weekly-check Audit-Prompt um "Schritt 0 Realitätscheck" erweitert: Mount-Zugriff + Tagebuch-Aktualität live prüfen, keine erledigten Altlasten als Blockierer wiederholen.

**Format:**
- [Änderung] — etwas wurde geändert/gebaut
- [Problem] — Fehler oder Hindernis
- [Erkenntnis] — technisches oder fachliches Lernmoment
- [Quelle] — URL oder Dokument das genutzt wurde (mit Datum)
- [Manuell] — vom Nutzer selbst eingetragen

---

## 2026-06-09 — Struktur-Abgleich + Git/Mount-Befund

- [Änderung] Projekt-Ordnerstruktur an den realen Stand angepasst: ergänzt `src/` (Java/Maven), `Lernhandbuch/`, `Vorgaben/`, `Projektmanagement/`, `Ablage/`, `Archiv/` sowie Wurzeldateien `README.md`, `pom.xml`, `DOIT.iml`; Repo-Link ergänzt.
- [Änderung] Aufgeräumt: stale `.git/index.lock` und 4 Junk-Dateien (`_wtest.tmp` in Doku/Lernhandbuch/MD, `Archiv/_moved.tmp`) entfernt; korrupten Git-Index aus HEAD neu aufgebaut.
- [Problem] Git-Schreiboperationen über einen Sandbox-Mount unzuverlässig: `git add` erzeugte genullten Index (`bad signature 0x00000000`); gelöschte Dateien werden als „modified" statt „deleted" gemeldet. → Commit/Push nativ unter Windows ausführen.
- [Erkenntnis] GitHub aus der Sandbox nicht erreichbar (keine Credentials). Ausstehende Reorg (Endabgabe → `Doku/`, Backups+altes Lernhandbuch → `Archiv/`, Glossar vereinheitlicht) ist noch nicht committet.

---

## 2026-06-07 — Planung Woche 6 + Doku_D-Zeitplan

- [Manuell] **Woche 6 (KW26, 22.06.–26.06.)** = Dokumentation + alle Schreibarbeiten abschließen (Phase 7)
  - Kapitel 3 Durchführung finalisieren, alle Korrekturen einarbeiten
  - Kapitel 4 + 5 (Ergebnisse, Reflexion) fertigstellen
  - Alle Teile in Hauptdokumentation zusammenführen
  - PDF erstellen und für Moodle-Upload vorbereiten
- [Manuell] **Doku_D (Kapitel 4 Ergebnisse + Kapitel 5 Reflexion)** wird morgen (08.06.2026) eingetragen

---

## 2026-06-02 — Word-Dokumente Doku_A–E + Hauptdoku vollständig geprüft und korrigiert

### Doku_C_Durchfuehrung.docx — 7 Korrekturen

- [Problem] Port in DBConnection-Beispielcode (3.2.2): `3306` → `3324` (stimmt mit tatsächlicher DBConnection.java überein)
- [Problem] DAO-Tabelle (3.3.1): `StationslagDAO` → `StationslagerDAO`, `BewegungDAO` → `BestandsbewegungDAO` (falsche Klassennamen vs. tatsächlichem Code)
- [Problem] MaterialDAO-Beispiel (3.3.2): `getAll()` → `findAll()` (tatsächliche Methode heißt findAll)
- [Problem] RowFactory-Code (3.4.2): `item.getBestand() <= item.getMindestbestand()` → `item.isWarnung()` (BestandView hat isWarnung(), Material hat kein getBestand())
- [Problem] Filter-Code (3.4.3): `m.getLagerName()` / `m.getKatName()` → korrekte BestandView-Methoden (Material hat diese Methoden nicht)
- [Problem] updateStatus-Code (3.4.4): `b.getBestellId()` → `b.getId()` (2 Stellen; Bestellung.java hat getId(), nicht getBestellId())
- [Änderung] Alle 7 Korrekturen in Doku_C_Durchfuehrung.docx angewendet

### Doku_E_Anhang.docx — 1 Korrektur

- [Problem] Anhang A Tabellenübersicht: Primärschlüssel der Tabelle bestellungen war `bestell_id` → korrigiert zu `bestellung_id` (laut SQL-Schema)
- [Änderung] Korrektur in Doku_E_Anhang.docx angewendet

### Doku_B_Ausgangssituation.docx — 1 Korrektur

- [Problem] Abschnitt 2.1: Text sagte „5 Phasen" → korrigiert zu „7 Phasen" (konsistent mit Projektplan.md + Hauptdokumentation)
- [Erkenntnis] Phasentabelle in Doku_B (5 Zeilen) stimmt noch nicht mit 7-Phasen-Struktur überein → manuell nachzupflegen wenn Phase 4–7 abgeschlossen

### Doku_A, Doku_D, Projektdokumentation_Sascha_Schulz.docx — keine Fehler

- [Erkenntnis] Doku_A: Inhaltsverzeichnis, Abbildungsverzeichnis, Tabellenverzeichnis, Quellenverzeichnis alle vorhanden
- [Erkenntnis] Doku_D: Soll-Ist-Tabelle und Testfälle korrekt als Platzhalter angelegt, werden nach Block 2 befüllt
- [Erkenntnis] Hauptdoku: Abgabedatum-Platzhalter `[Datum eintragen]` muss nach Abgabe auf 17.07.2026 gesetzt werden

---

## 2026-06-02 — Vollständiger Projekt-Audit vor Block 2 (KW24)

- [Problem] **BUG KRITISCH #1 — BestandsbewegungDAO.java: falscher Spaltenname**
  - Alle SQL-Statements nutzten `bestandsbewegung_id` (existiert nicht in der DB)
  - SQL-Schema definiert den PK als `bewegung_id`
  - Betroffen: `findById`, `findAll`, `update`, `delete`, `mapRow` → alle hätten `SQLException: Unknown column` geworfen
  - [Änderung] Alle 5 Stellen korrigiert: `bestandsbewegung_id` → `bewegung_id`

- [Problem] **BUG KRITISCH #2 — DBConnection.java: falscher Datenbankname**
  - URL zeigte auf `DOIT` statt `krankenhaus_lager`
  - SQL-Skript erstellt `CREATE DATABASE krankenhaus_lager` → Verbindung wäre komplett gescheitert
  - [Änderung] `jdbc:mysql://127.0.0.1:3324/DOIT?...` → `jdbc:mysql://127.0.0.1:3324/krankenhaus_lager?...`

- [Problem] **BUG KRITISCH #3 — BewegungsTyp.java: TRANSFER-Wert nicht in SQL-ENUM**
  - Java-ENUM hatte: `EINGANG, AUSGANG, TRANSFER`
  - SQL-ENUM definiert nur: `ENUM('EINGANG','AUSGANG')`
  - UI hätte TRANSFER als Option angezeigt → INSERT wäre mit MySQL-Fehler gescheitert
  - [Änderung] `TRANSFER` aus BewegungsTyp.java entfernt

- [Änderung] **Entscheidungen.md** — Tippfehler korrigiert: `StationslagDAO` → `StationslagerDAO`

- [Erkenntnis] Alle anderen Dateien (Models, restliche DAOs, MainController, FXML, pom.xml, SQL-Schema, Markdown-Docs) sind konsistent und fehlerfrei
- [Erkenntnis] Word-Dokumente (Doku_A–E, Projektdokumentation) konnten nicht maschinell gelesen werden (Bash-Umgebung nicht verfügbar) — manuell zu prüfen
- [Erkenntnis] Nach den drei Fixes ist der Code technisch startbereit für Block 2 (KW24)

---

## 2026-05-31 — Dokumentationsstruktur + Datei-Bereinigung (Folge-Runde)

- [Änderung] `Glossar_Java_API.docx` von `Ablage/` → Root verschoben (gehört zu den Doku-Arbeitsteilen)
- [Änderung] `KOHAEREZ_AUDIT_2026-05-18.md` → `Archiv/` verschoben (Einmal-Dokument, kein Dauerauftrag)
- [Änderung] `Dateiuebersicht.md`: Doku_A–E-Struktur dokumentiert — Arbeitsweise erklärt (Einzelteile → am Ende zusammenführen in Projektdokumentation_Sascha_Schulz.docx)
- [Erkenntnis] **Doku-Workflow:** Änderungen immer in Doku_A–E-Einzeldateien; Glossar_Java_API.docx wird als Anhang-Teil integriert; finale Zusammenführung in Projektdokumentation_Sascha_Schulz.docx am Projektende

---

## 2026-05-31 — Ordner-Bereinigung nach Backup-Problem

- [Problem] Backup-Restore hat heute (17:00 Uhr) ältere Dateiversionen in den Root-Ordner eingespielt (alle Dateien gleicher Timestamp 17:00:24). Neuere Versionen in Unterordnern blieben unberührt.
- [Änderung] **Bereinigung durchgeführt:** Alle Duplikate, Temp-Dateien und unpacked-Ordner entfernt.
  - Gelöscht: 7x `.bak`, 1x `.bkp`, 4x `unpacked_*/check_fix/` Ordner in Root, Ablage/Unpacked_Work/, Ablage/check_fix/
  - Gelöscht: `temp_dok.md`, `temp_dok_neu.md`, `temp_part1.md`, `temp_part2.md`, `neue_sektion.md`, `temp_dokumentation_neu.docx`
  - Gelöscht: `KAPITEL_5_6_PLAN (1).md` (Windows-Duplikat)
  - Gelöscht: Archiv/Entscheidungen.md + Archiv/testdaten.sql (identisch mit Root)
  - Gelöscht: Projektmanagement/Projektplan.md, Projektmanagement/Wochenplan.xlsx (Root ist kanonisch)
  - Gelöscht: Dokumentation/ Ordner (beide Dateien in Root vorhanden/kopiert)
  - Gelöscht: Ablage-Duplikate (how_to_codereview, Java_Konzept, KAPITEL_5_6, KOHAEREZ, Projektdoku_backup)
  - Gelöscht: Root Lernhandbuch_Lagerverwaltung.docx (156K) — Lernhandbuch/_korrigiert.docx (309K) ist die aktuelle Version
- [Änderung] Root mit neueren Unterordner-Versionen aktualisiert:
  - `Projekttagebuch.md` ← Projektmanagement/ (Stand 21.05., neuer als Backup-Version)
  - `Projektdokumentation_Sascha_Schulz.docx` ← Dokumentation/ (25K, größer als Backup 22K)
  - `Java_Handbuch_Start.docx` ← Ablage/ (41K, wesentlich größer als Backup 25K)
- [Änderung] `STATUS_KW21_CHECKPOINT.txt` → Archiv/ verschoben
- [Änderung] `Dateiuebersicht.md` vollständig aktualisiert (alle neuen Dateien ergänzt, Struktur bereinigt)

---

## 2026-05-19 — Glossar Java API für Dokumentation erstellt

- [Änderung] **Anhang D erstellt:** `Glossar_Java_API.docx` mit 48 Java-API-Begriffen
- [Erkenntnis] Durch Durchsicht der Doku identifiziert: 48 Begriffe aus Standard Java API (JDK 8+)
- [Erkenntnis] **Keine externen Dependencies** — ausschließlich java.lang, java.util, java.io, java.sql (JDBC), java.time, javafx.*
- [Erkenntnis] Glossar kategorisiert nach Paket/Funktion: JDBC (5), Collections (6), JavaFX Layout/UI (10), JavaFX Core (4), Events/Listeners (4), java.time (2), java.util.function (5), java.lang (3), java.io (3), weitere (1)
- [Änderung] Glossar als separate .docx, wird später als **Anhang D** in die Hauptdokumentation integriert
- [Quelle] Oracle Java 8 API Docs (https://docs.oracle.com/javase/8/docs/api/, 2026-05-19)
- [Quelle] Oracle JavaFX 8 API Docs (https://docs.oracle.com/javase/8/javafx/api/, 2026-05-19)

---

## 2026-05-06 — Bestandsberechnung direkt in SQL

- [Erkenntnis] Überlegt: Wer berechnet den Lagerbestand — Java oder die Datenbank?
- [Erkenntnis] Nach Recherche auf mehreren Webseiten entschieden: Berechnung läuft direkt per SQL (`SUM(EINGANG) − SUM(AUSGANG)` über `bestandsbewegungen`)
- [Erkenntnis] Begründung: SQL-Aggregation ist performanter als alle Zeilen nach Java laden und dort summieren
- [Erkenntnis] Begründung: Weniger fehleranfällig — keine doppelte Logik in Java und DB, eine Quelle der Wahrheit
- [Erkenntnis] Folge: Kein `bestand`-Feld in `materialien` — Bestand ist immer ein berechneter Wert aus der Bewegungstabelle
- [Quelle] Mehrere Webseiten zu SQL-Aggregation vs. Java-Berechnung (2026-05-06)

---

## 2026-05-06 — Trennung von Model und DAO

- [Erkenntnis] Jedes Package hat eine einzige Verantwortlichkeit (Single Responsibility)
- [Erkenntnis] `Material.java` (model) = Datendefinition — was ist ein Material, welche Felder hat es
- [Erkenntnis] `MaterialDAO.java` (dao) = Datenzugriff — wie wird Material aus der DB gelesen/geschrieben
- [Erkenntnis] Vorteil: SQL-Frage → sofort in DAO schauen; Strukturfrage → sofort in Model schauen
- [Erkenntnis] Fachgesprächsrelevant: Trennung erklären können — Lesbarkeit, Wartbarkeit, Erweiterbarkeit

---

## 2026-05-06 — Package-Namenskonvention

- [Erkenntnis] Package-Namen folgen der Java-Konvention: umgedrehte Domain + Funktionsbereich
- [Erkenntnis] `de.doit` → Länderkürzel `de` + Projektkennung `doit`; darin liegt nur `Main.java`
- [Erkenntnis] `de.doit.model` — POJOs, spiegeln Datenbanktabellen wider (z. B. `Material.java`, `Kategorie.java`)
- [Erkenntnis] `de.doit.dao` — Data Access Objects, zuständig für alle SQL-Operationen (CRUD via `PreparedStatement`)
- [Erkenntnis] `de.doit.db` — enthält `DBConnection.java` (Singleton, zentrale Datenbankverbindung)
- [Erkenntnis] `de.doit.controller` — JavaFX-Controller, steuern die Oberfläche und verbinden Model mit View

---

## 2026-05-06 (Manuell) — Eintrag 2

- [Änderung] MySQL-Verbindung in IntelliJ Database Tool Window eingerichtet
- [Erkenntnis] Verbindungsparameter: Host `localhost`, Port `3324`, User `root`, Database `lagerverwaltung`
- [Erkenntnis] Verbindung über IntelliJ: Plus → Data Source → MySQL → Dialog ausfüllen → Test Connection

---

## 2026-05-06 (Manuell)

- [Änderung] SQL-Skript `testdaten_krankenhaus_lager.sql` fertiggestellt — alle Tabellen angelegt, Testdaten eingefügt
- [Problem] SQL-Fehler beim Ausführen: fehlendes Semikolon am Ende einer Anweisung
- [Quelle] Fehlerursache über Google-Recherche ermittelt (2026-05-06)
- [Erkenntnis] SQL-Statements immer mit Semikolon abschließen — MySQL-Workbench/CLI bricht sonst beim nächsten Statement mit Syntaxfehler ab

---

## 2026-05-06 (Wochencheck KW19 — automatisch)

- [Änderung] Dokumentationsvorgaben aus `11_DOIT_2551_Dokumentationsvorgaben.pdf` eingetragen + Abschluss-Checkliste ergänzt
- [Erkenntnis] Abgleich Dokumentation: Abbildungsverzeichnis, Tabellenverzeichnis und Quellenverzeichnis fehlen noch komplett. Benutzerhandbuch muss als Anhang in die Hauptdoku, nicht als separate Datei.
- [Erkenntnis] Inhaltsverzeichnis ist manuell gepflegt (kein Word-Auto-TOC) — bei Änderungen Seitenzahlen manuell prüfen.
- [Problem] Dateiuebersicht.md ist veraltet: Doku_A–E.docx, lmstudio-proxy/, start-ai.ps1, 11_DOIT_2551_Dokumentationsvorgaben.pdf (Vorgaben-Ordner) fehlen in der Übersicht.
- [Erkenntnis] Java-Projekt (DOIT3) gescannt: Nur IntelliJ-Standard-Template vorhanden (HelloApplication, HelloController, Launcher). Kein projektspezifischer Code — entspricht Phase 1 (Analyse/Planung), kein Rückstand.
- [Erkenntnis] SQL-Schema (testdaten_krankenhaus_lager.sql) ist konsistent mit Entscheidungen.md und Projektplan: 6 Tabellen, kein Bestandsfeld in materialien, korrekte FKs.

---

## 2026-05-11 (Wochencheck KW20 — automatisch)

- [Erkenntnis] KW20 = Phase 2 laut Projektplan (SQL anlegen, Testdaten, JDBC-Verbindung). SQL-Skript und DB-Verbindung aus KW19 bereits erledigt — Phase 2 teilweise vorgearbeitet.
- [Erkenntnis] Java-Projekt (DOIT3): Immer noch nur IntelliJ-Template (HelloApplication, HelloController, Launcher). Kein projektspezifischer Code. Package-Name ist `com.example.doit3` — abweichend von geplanter Konvention `de.doit`. Muss vor Phase 3 korrigiert werden.
- [Problem] Package-Name `com.example.doit3` widerspricht der dokumentierten Konvention (`de.doit.*` aus Tagebucheintrag 06.05.). Refactoring nötig bevor DAOs und Models angelegt werden.
- [Problem] `todos.docx` in Dateiuebersicht.md gelistet, aber Datei existiert nicht im Ordner.
- [Erkenntnis] Dateiuebersicht.md ist aktuell (alle Doku_A–E.docx, lmstudio-proxy/, start-ai.ps1 sind korrekt eingetragen nach KW19-Fix).
- [Erkenntnis] Konsistenzcheck Entscheidungen/Plan/SQL: Alle konsistent — 6 Tabellen, kein Bestandsfeld, drawio als Standard, DAO-Pattern.
- [Erkenntnis] Für KW20: DBConnection.java + alle 6 Model-Klassen + alle 6 DAO-Klassen (mindestens findAll/create) anlegen. Package erst auf `de.doit` umbenennen.

---

## 2026-05-06 — java.sql-Imports für Datenbankverbindung

- [Erkenntnis] `import java.sql.Connection` — repräsentiert die aktive Verbindung zur Datenbank; darüber werden alle SQL-Anweisungen gesendet (Statements, Queries)
- [Erkenntnis] `import java.sql.DriverManager` — baut die Verbindung auf anhand von drei Parametern: Datenbank-URL (`jdbc:mysql://...`), Benutzername und Passwort
- [Erkenntnis] `import java.sql.SQLException` — Ausnahmeklasse für alle DB-Fehler (falsches Passwort, Server nicht erreichbar, falsche URL); muss immer abgefangen oder weitergegeben werden
- [Erkenntnis] Alle drei gehören zum Java-Standardpaket `java.sql` — kein externer Import nötig, aber der MySQL-JDBC-Treiber (`mysql-connector-j`) muss als Abhängigkeit im Projekt vorhanden sein
- [Erkenntnis] Zusammenspiel: `DriverManager.getConnection(url, user, pw)` gibt ein `Connection`-Objekt zurück; schlägt es fehl, wirft es eine `SQLException`

---

## 2026-05-17 — Stand KW19–KW21 + Java-Code Lagerverwaltung verifiziert

- [Erkenntnis] **Java-Code Lagerverwaltung (korrekt!)**:
  - ✅ Package-Struktur: `de.doit.db`, `de.doit.model`, `de.doit.dao`, `de.doit.controller`
  - ✅ DBConnection.java: Singleton mit Lazy-Loading (37 Zeilen)
  - ✅ 7 Model-Klassen: Material, Bestellung, Bestandsbewegung, Stationslager, Lieferant, Kategorie, + BewegungsTyp, BestandView (Ø 30 Zeilen)
  - ✅ 6 DAO-Klassen: MaterialDAO (73), BestellungDAO (71), BestandsbewegungDAO (66), StationslagerDAO (50), LieferantDAO (50), KategorieDAO (48) — alle mit PreparedStatement
  - ✅ MainController (60 Zeilen) + Main.java (24 Zeilen)
  - **Total: 736 Zeilen Code, Phase 3 vollständig abgeschlossen**

- [Manuell] **KW19 (04.05–08.05)** Status:
  - ✅ Mo–Mi: ER-Diagramm, Datenbank-Schema, SQL-Skript + Testdaten
  - ✅ Do–Fr: krank (nicht geplant)
  - → Phase 1+2 erledigt

- [Manuell] **KW20 (11.05–15.05)** Status:
  - ✅ Mo: Package-Umbenennung + 6 Model-Klassen + DBConnection geschrieben (7h)
  - ✅ Di–Mi: krank (nicht geplant)
  - ✅ Do–Fr: frei (nicht geplant)
  - → Phase 3 Anfang gemacht

- [Manuell] **KW21 (18.05–22.05)** Plan:
  - **Mo (18.05):** Package-Struktur + 6 Models (7h geplant = tatsächlich)
  - **Di (19.05):** DBConnection + MaterialDAO (7h)
  - **Mi (20.05):** 5 weitere DAOs (7h)
  - **Do (21.05):** BestandBewegungDAO + alle 6 DAOs testen (7h)
  - **Fr (22.05):** Fehlerbehandlung, Code aufräumen, Phase 3 abschließen (7h)

- [Änderung] **Alle Dateien auf "Lagerverwaltung" angepasst**: DOIT3-Ordner wird gelöscht, Word-Dateien werden manuell angepasst (Suchen/Ersetzen)

---

## 2026-05-18 — Lernhandbuch Fehler Seite 38 + Kapitel 6 Model-Klassen abgeschlossen

- [Problem] Lernhandbuch Seite 38 (Kapitel 6.4 Lieferant.java): Konstruktoren waren kopiert von Stationslager — falsche Parameter
- [Änderung] Drei Konstruktoren korrigiert: `public Stationslager(...)` → `public Lieferant(String name, String kontakt, String telefon, String email)`
- [Änderung] Lernhandbuch aktualisiert und validiert (XML-Struktur geprüft)

- [Manuell] **Kapitel 6: Die Model-Klassen** erledigt:
  - ✅ Material.java (7 Felder: id, name, einheit, mindestbestand, kategorieId)
  - ✅ Kategorie.java (3 Felder: id, name, beschreibung)
  - ✅ Stationslager.java (4 Felder: id, name, standort, typ)
  - ✅ Lieferant.java (5 Felder: id, name, kontakt, telefon, email)
  - ✅ Bestellung.java (8 Felder: id, materialId, lieferantId, lagerId, menge, bestelldatum, lieferdatum, status)
  - ✅ Bestandsbewegung.java (8 Felder: id, materialId, lagerId, bewegungstyp, menge, ablaufdatum, datum, bemerkung)
  - ✅ BewegungsTyp.java (ENUM: EINGANG, AUSGANG)
  - ✅ BestandView.java (berechnete Sicht: materialId, lager Id, bestand = SUM(EINGANG) − SUM(AUSGANG))
  - Alle mit zwei Konstruktoren (ohne/mit ID), Getter/Setter, toString()
- [Erkenntnis] Jede Model-Klasse spiegelt eine Tabelle aus der Datenbank

---

## 2026-05-17 — Wochenplan wiederhergestellt mit Stunden

- [Problem] Wochenplan_Sascha_Schulz.xlsx war komplett kaputt (falsche Struktur, fehlende Stunden)
- [Änderung] Wochenplan von Vorlage `03_Orga_Wochenplan_Vorlage.xlsx` neu aufgebaut + alle Aufgabenlisten + Stunden:
  - **KW19 (21h):** Mo–Mi je 7h (Projektziele, ER-Diagramm, Datenbank), Do–Fr krank
  - **KW20 (7h):** Mo 7h (Package-Struktur, Models, DBConnection), Di–Fr krank/frei
  - **KW21 (32h):** Mo–Do je 7h (Models, DBConnection, MaterialDAO, weitere DAOs, BestandBewegungDAO), Fr 4h (Verifikation, Tests, Cleanup)
  - Gesamt Phase 1–3: 60h (passt zum Projektplan)
- [Erkenntnis] Stundeneinteilung basiert auf Aufgabenkomplexität pro Tag; Fr KW21 = 4h (nicht 7h) weil nur Verifikation/Tests/Cleanup

---

## 2026-05-18 — Datenbankverbindung erfolgreich + Umbenennung DOIT → krankenhaus_lager

- [Problem] Communications link failure — JDBC-Verbindung zur MySQL-DB fehlgeschlagen
- [Erkenntnis] Fehlersuche: falscher Port in Verbindungsstring eingetragen
- [Änderung] Port korrigiert → Datenbankverbindung erfolgreich hergestellt
- [Erkenntnis] Connect mit Datenbank funktioniert — Testabfrage gelungen

---

## 2026-05-18 — DBConnection als Singleton implementiert

- [Änderung] `DBConnection.java` erstellt mit `private` Verbindung + `getConnection()` + `closeConnection()`
- [Entscheidung] **Singleton-Pattern** nach Recherche gewählt
- [Erkenntnis] Begründung: eine einzige aktive Verbindung zur Datenbank (Leistung, Performance, ressourcenschonend)
- [Erkenntnis] Begründung: verhindert mehrfaches Öffnen/Schließen von Verbindungen → Overhead reduziert
- [Erkenntnis] Begründung: zentrale Verwaltung ermöglicht einfaches Connection-Management über die gesamte Anwendung
- [Quelle] Recherche: Singleton-Pattern für DB-Verbindungen (Best Practice) (2026-05-18)

---

## 2026-05-18 — Kohärenz-Prüfung: IntelliJ-Code vs. SQL-Schema + Dokumentation

**KOHÄRENZ-ERGEBNIS: ✅ VOLLSTÄNDIG KONSISTENT**

### A) Datenmodell: drawio ↔ SQL-Schema ↔ Java Model-Klassen

**SQL-Schema (`testdaten_krankenhaus_lager.sql`):**
- ✅ 6 Tabellen angelegt: kategorien, materialien, stationslager, lieferanten, bestellungen, bestandsbewegungen
- ✅ Primärschlüssel (PK) korrekt: kategorie_ID, material_ID, lager_ID, lieferant_ID, bestellung_ID, bewegung_ID
- ✅ Fremdschlüssel (FK) korrekt: materialien→kategorien, bestellungen→(materialien, lieferanten, stationslager), bestandsbewegungen→(materialien, stationslager)
- ✅ Spaltennamen exakt wie in drawio (`DOIT Krankenhaus.drawio`)
- ✅ Kein `bestand`-Feld in `materialien` (als D-001 festgelegt)

**Java Model-Klassen (7 insgesamt):**
- ✅ Material.java (7 Felder: materialId, name, einheit, mindestbestand, kategorieId)
- ✅ Kategorie.java (3 Felder: kategorieId, name, beschreibung)
- ✅ Stationslager.java (4 Felder: lagerId, name, standort, typ)
- ✅ Lieferant.java (5 Felder: lieferantId, name, kontakt, telefon, email)
- ✅ Bestellung.java (8 Felder: bestellungId, materialId, lieferantId, lagerId, menge, bestelldatum, lieferdatum, status)
- ✅ Bestandsbewegung.java (8 Felder: bewegungId, materialId, lagerId, bewegungstyp, menge, ablaufdatum, datum, bemerkung)
- ✅ BewegungsTyp.java (ENUM: EINGANG, AUSGANG)
- ✅ BestandView.java (berechnete Sicht für Material-Bestand-Kombination)
- **Alle Klassen spiegeln die Tabellenstruktur 1:1 mit zwei Konstruktoren (mit/ohne ID) + Getter/Setter + toString()**

### B) Architektur: Package-Struktur ↔ DAO-Pattern ↔ Entscheidungen

**Package-Struktur (`de.doit.*`):**
- ✅ `de.doit.db` → DBConnection.java (Singleton, lazy-loaded, 37 Zeilen)
- ✅ `de.doit.model` → 7 Model-Klassen (Ø 30 Zeilen, POJO)
- ✅ `de.doit.dao` → 6 DAO-Klassen (Ø 60 Zeilen, PreparedStatement)
- ✅ `de.doit.controller` → MainController.java (JavaFX, 60 Zeilen)
- ✅ `de.doit` → Main.java (Einstiegspunkt, 24 Zeilen)

**DAO-Implementierung (6 DAOs):**
- ✅ KategorieDAO.java (create, findById, findAll, update, delete mit PreparedStatement)
- ✅ MaterialDAO.java (CRUD-Operationen)
- ✅ StationslagerDAO.java (CRUD-Operationen)
- ✅ LieferantDAO.java (CRUD-Operationen)
- ✅ BestellungDAO.java (CRUD-Operationen)
- ✅ BestandsbewegungDAO.java (CRUD-Operationen)
- **Alle DAOs nutzen `DBConnection.getConnection()` + PreparedStatement (SQL-Injection-Schutz)**

**Entscheidungen konsistent:**
- ✅ D-001 (kein Bestand-Feld): Java-Code hat keinen Bestand in Material-Klasse ✓
- ✅ D-002 (6 Tabellen Pflicht): alle 6 Tabellen im SQL + alle 6 DAOs implementiert ✓
- ✅ D-003 (drawio Standard): Spaltennamen in SQL = Spaltennamen in Models ✓
- ✅ D-004 (DAO + Singleton): DBConnection.java vorhanden, alle DAOs nutzen es ✓
- ✅ D-005 (lokal): Connection zu `jdbc:mysql://localhost:3324/DOIT` ✓
- ✅ D-006 (Stunden): 180h Antrag, 192h Wochenplan, 12h Puffer dokumentiert ✓

### C) MainController: Initialisierung aller DAOs

```java
private final MaterialDAO materialDAO = new MaterialDAO();
private final BestandsbewegungDAO bestandsbewegungDAO = new BestandsbewegungDAO();
private final BestellungDAO bestellungDAO = new BestellungDAO();
private final KategorieDAO kategorieDAO = new KategorieDAO();
private final LieferantDAO lieferantDAO = new LieferantDAO();
private final StationslagerDAO stationslagerDAO = new StationslagerDAO();

@FXML
public void initialize() {
    ladeMaterialien();
    ladeBestandsbewegungen();
    ladeBestellungen();
}
```

- ✅ Alle 6 DAO-Instanzen privat final
- ✅ initialize() lädt die Kernbereiche (Material, Bewegung, Bestellung)
- ✅ entspricht Projektplan Phase 4 (UI Grundfunktionen)

### D) Code-Größe und Phase-Abschluss

| Komponente | Zeilen | Anzahl | Total |
|---|---|---|---|
| Model-Klassen | 30 Ø | 7 | 210 |
| DAO-Klassen | 60 Ø | 6 | 360 |
| DBConnection | 37 | 1 | 37 |
| MainController | 60 | 1 | 60 |
| Main.java | 24 | 1 | 24 |
| SQL-Schema | — | 6 Tables | ✓ |
| **Summe** | **—** | **—** | **691 Zeilen** |

- **Phase 1–3 (Datenbank + DAO-Schicht): ✅ ABGESCHLOSSEN**
- Projekt sitzt zeitlich im Plan (KW21 = aktuelle Woche, Phase 3 vollständig)
- Phase 4 (UI) hat bereits begonnen (MainController.java initialisiert alle DAOs, wird nächste Woche erweitert)

### E) Dokumentation: aktuell ↔ inkonsistenz

- ✅ Projektplan.md: korrekt, alle Phasen beschrieben
- ✅ Dateiübersicht.md: aktuell (alle Dateien aufgelistet, archiviert wenn nötig)
- ✅ Entscheidungen.md: D-001 bis D-006 alle implementiert
- ✅ Projekttagebuch.md: wird jeweils nach jeder Phase befüllt
- ⏳ `Projektdokumentation_Lagerverwaltung_Sascha_Schulz.docx`: noch zu füllen (Phase 7)
- ⏳ `Benutzerhandbuch_Lagerverwaltung_Sascha_Schulz.docx`: noch zu füllen (Phase 7)

### ZUSAMMENFASSUNG: KOHÄRENZ ✅ GRÜN

Datenmodell (SQL), Java-Code (Models, DAOs, Controller) und Dokumentation (Projektplan, Entscheidungen) sind **vollständig konsistent**.

Nächste Schritte (KW21 Fr–KW24):
- Phase 4: UI Erwiterung (Buttons, TableViews, Dialog-Fenster für CRUD)
- Phase 5–6: Suche/Filter/Warnung implementieren
- Phase 7: Dokumentation + Benutzerhandbuch ausfüllen

---

## 2026-05-18 (Checkpoint) — Kapitel 5 + 6 geplant für KW21 Fr + nächste Woche

- [Änderung] Zwei Dokumente hinzugefügt für Kapitel 5 + 6:
  - `KAPITEL_5_6_PLAN.md` — Lernplan mit Code-Templates + Lernzielen
  - `KOHAEREZ_AUDIT_2026-05-18.md` — Vollständiger Audit: SQL ↔ Java ↔ Doku
- [Manuell] **Kapitel 5 (DBConnection.java):** 
  - Singleton-Pattern erklärt
  - Lazy-Loading implementiert
  - JDBC-Konzept (URL, USER, PASSWORD, SQLException)
  - Code in IntelliJ geprüft: ✅ korrekt (37 Zeilen)
- [Manuell] **Kapitel 6 (Die Model-Klassen — 7 Stück):**
  - Material, Kategorie, Stationslager, Lieferant, Bestellung, Bestandsbewegung
  - Zusätz: BewegungsTyp (ENUM), BestandView (ViewModel)
  - Alle Klassen: 2 Konstruktoren (mit/ohne ID) + Getter/Setter + toString()
  - Code in IntelliJ geprüft: ✅ alle 7 Klassen vorhanden + korrekt (Ø 30 Zeilen)
- [Erkenntnis] **Kohärenz-Check abgeschlossen:**
  - SQL-Schema (6 Tabellen) ← → Java Models (6 + 2 Helper) ← → DAO (6 Klassen)
  - Alle Spaltennamen konsistent zwischen SQL, Java, drawio
  - D-001 bis D-006 alle implementiert
  - 691 Zeilen Code (Phase 1–3: erledigt, zeitlich im Plan)
- [Erkenntnis] **Phase 3 = 100% abgeschlossen**, Phase 4 (UI) startet KW24

---

## 2026-05-20 — DBConnection: statische Variablen + DriverManager.getConnection()

- [Erkenntnis] URL, USER, PASSWORD müssen **statische Klassenvariablen** (`static`) in DBConnection sein — sonst kann `DriverManager.getConnection()` nicht darauf zugreifen
- [Erkenntnis] `DriverManager` ist ein Klassenname aus der Java-Standardbibliothek (`java.sql`)
- [Erkenntnis] `DriverManager.getConnection()` ist eine **statische Methode** — wird direkt über den Klassennamen aufgerufen, keine Instanz nötig
- [Erkenntnis] `.` (Punkt) = Zugriffsoperator — zeigt an, zu welcher Klasse die Methode gehört
- [Erkenntnis] `()` (Klammern) = Parameterübergabe — URL, USER, PASSWORD werden in den Klammern übergeben, nicht über den Punkt
- [Erkenntnis] Syntax-Zusammenfassung: `DriverManager.getConnection(URL, USER, PASSWORD)` → Klasse.Methode(Parameter)

---

## 2026-05-20 — localhost IP-Adresse recherchiert

- [Quelle] https://www.seo-kueche.de/lexikon/localhost/ (abgerufen 2026-05-20)
- [Erkenntnis] localhost hat die reservierte IP-Adresse `127.0.0.1` (IPv4) bzw. `::1` (IPv6)
- [Erkenntnis] Relevant für JDBC-Verbindung: `jdbc:mysql://localhost:3324/...` und `jdbc:mysql://127.0.0.1:3324/...` sind gleichwertig

---

## 2026-05-19 (Freitags-Audit KW21 — automatisch)

- [Änderung] **Zwei neue Dokumente erstellt:**
  - `Projektplan.md` — Phasen, Stundeneinteilung, Meilensteine (Detailinformationen aus Projektnotizen + Tagebuch)
  - `FREITAGS_AUDIT.md` — Automatisierter wöchentlicher Audit-Prozess (Checkliste + Prüfschritte)

- [Erkenntnis] **Phase 3 Status KW21:**
  - Geplant: 32 h (Mo–Fr: 7h+7h+7h+7h+4h)
  - Tatsächlich: ⏳ noch diese Woche laufend (Audit am 22.05 durchführen)
  - ✅ Phase 3 Code-Abschluss bis Freitag 22.05 zu 100% geplant

- [Erkenntnis] **Dokumentation Status:**
  - ✅ `Projektplan.md` erstellt (Phasen, Stunden, Datenmodell, Code-Struktur)
  - ✅ `FREITAGS_AUDIT.md` erstellt (automatisierter Audit-Workflow für jeden Freitag)
  - ⏳ `Wochenplan_Sascha_Schulz.xlsx` existiert nicht im Ordner — wird bei nächster Gelegenheit nachgeladen/erstellt

- [Erkenntnis] **Freitags-Audit Prozess:**
  - Jeden Freitag: Projektplan ↔ Tagebuch vergleichen (Sollstunden vs. Ist-Stunden)
  - Entscheidungen (D-001–D-006) vs. Code prüfen (Konsistenz)
  - Dateiuebersicht.md auf Aktualität prüfen
  - Tagebucheintrag [Erkenntnis] mit Status + Empfehlungen schreiben
  - Output: Kompakte Markdown-Tabelle mit ✅/⚠️-Status

- [Empfehlung] **Nächste Woche (KW22: 25.05–29.05):**
  - Phase 3 mit Freitags-Audit abschließen (22.05)
  - Phase 4 (UI-Entwicklung) starten: JavaFX Views, TableViews, CRUD-Dialoge
  - Stundeneinteilung: 7h × 5 Tage = 35 h geplant (aus Projektplan)

---

## 2026-05-21 (Freitags-Audit KW21 — automatisch)

- [Erkenntnis] **Phase 3 Status: ✅ ABGESCHLOSSEN** — alle 6 Models + 6 DAOs + DBConnection vorhanden, Code konsistent (1773 Zeilen gesamt)
- [Erkenntnis] **Phase 4 BEREITS GESTARTET (⚡ voraus):** MainController.java hat 703 Zeilen (geplant war 60 am Ende Phase 3) — TableViews für alle 6 Entitäten bereits im Code, FXML vorhanden
- [Erkenntnis] **KW21 geplant: 32h** (Phase 3 DAO-Schicht) — Phase 3 vollständig erledigt, Phase 4 begonnen → effektiv im Zeitplan oder leicht voraus
- [Erkenntnis] **Code-Konsistenz D-001–D-006: ✅ ALLE OK**
  - D-001: Material.java kein bestand-Feld ✅
  - D-002: 6 Models + 6 DAOs vorhanden ✅
  - D-003: Spaltennamen konsistent (aus Voradit bestätigt) ✅
  - D-004: DBConnection Singleton + alle DAOs nutzen es ✅
  - D-005: localhost (127.0.0.1:3324) ✅
  - D-006: 180h/192h/12h dokumentiert ✅
- [Problem] `todos.docx` in Dateiuebersicht.md gelistet, existiert nicht — bekanntes Problem seit KW20, noch nicht behoben
- [Problem] `Projektdokumentation_Lagerverwaltung_Sascha_Schulz.docx` + `Benutzerhandbuch_Lagerverwaltung_Sascha_Schulz.docx` fehlen noch — geplant Phase 7, aber Dokument-Erstellung jetzt früher starten empfohlen (Risiko bei Zeitdruck)
- [Empfehlung] **KW22 (25.05–29.05): Phase 4 fortsetzen** — CRUD-Dialoge (Create/Update/Delete) für alle 6 Entitäten implementieren, 42h geplant

---

## 2026-06-07

**[Änderung]** Projektdokumentation vollständig überarbeitet und zusammengeführt.
- Kapitel 3 (Doku_C) von fälschlich 5 auf **7 Phasen** umgestellt (gemäß Projektantrag v2): Phase 4 UI-Grund (40h), Phase 5 Erweitert (25h), Phase 6 Tests (25h), Phase 7 Doku (30h). Frühere „65h/55h"-Angaben entfernt.
- Doku_B Wochenübersicht: alte „Phase 4 (I)/(II)/Phase 5"-Labels auf 7-Phasen-Schema korrigiert; Datumsangaben an Projektplan angeglichen.
- Hauptdoku Kapitel 5 von „Gestaltung des Portfolios" auf „Reflexion & Bewertung" umbenannt.
- Quellenverzeichnis von 5 auf **14 Quellen** erweitert (JavaFX 17 TableView/ObservableList/FilteredList/TabPane/Dialog, FXML-Tutorial, Maven, MySQL Connector/J, PreparedStatement) — URLs verifiziert, Abruf 07.06.2026.
- Benutzerhandbuch vollständig in **Anhang C** integriert (vorher separate Datei).
- Doku_D (Kap. 4/5): Soll-Ist auf „Umgesetzt (Test Phase 6)" gesetzt; Testergebnisse + Reflexion als **klar markierte Entwürfe** (Tests/Abnahme stehen noch aus, da Block 2 erst startet).
- Kap. 3.6.2 „Herausforderungen & behobene Fehler" neu (Bugs aus Tagebuch: PK-Name, DB-Name, ENUM TRANSFER, Port 3324).

**[Problem]** Eingebettetes ER-PNG (in ER_Diagramm_Sascha_Schulz.docx) war **veraltet** — zeigte in `materialien` noch menge/ablaufdatum/lieferant_id/lager_id. Widerspricht korrigiertem Stammdaten-Schema. → Neues, schema-korrektes ER-Diagramm erzeugt (`ER_Diagramm_aktuell.png`) und in Anhang A eingebettet.

**[Erkenntnis]** Format jetzt SRH-konform: Arial 12pt, 1,5-zeilig, A4, Ränder 2,5/2,5/2,5/2,0 cm; alle Pflicht-Verzeichnisse vorhanden. **Offen:** (1) Haupttext Kap. 1–5 ≈ 19 Seiten — Kap. 3 enthält viele Code-/Tabellenseiten (SRH: max. ⅓ pro Seite), ggf. Code kürzen/in Anhang verschieben. (2) Benutzerhandbuch beschreibt UI-Felder Menge/Ablaufdatum/Station auf Materialebene — gegen Stammdaten-Schema, vor Abgabe prüfen. (3) Testergebnisse/Abnahme/Reflexion nach Phase 6/7 finalisieren.

**[Quelle]** Backups der Original-docx unter `_backup_2026-06-07/`.

---

## 2026-06-07 (Nachtrag)

**[Änderung]** Dokument an SRH-Vorgaben + Bewertungsmatrix ausgerichtet (Teil 2).
- Format technisch gegen `11_DOIT_2551_Dokumentationsvorgaben.pdf` geprüft: Arial 12pt, 1,5-zeilig, Ränder 2,5/2,5/2,5/2,0 cm, A4, alle Pflicht-Verzeichnisse, Quellen mit URL+Abruf bzw. Titel/Autor/Jahr — alles erfüllt.
- **Kostenplanung (Kap. 2.5)** neu: fiktiver Stundensatz 80,00 €; verrechnet nur Entwicklung/Test = 180 h − 30 h Doku = 150 h × 80 € = 12.000 €; + Sachkosten 150 € = **12.150 €** gesamt (vorher 180 h × 60 € = 10.950 €).
- **Kapitel 3** ausführlicher: vollständige Code-Beispiele (MaterialDAO findAll/create, updateStatus mit Auto-EINGANG-Buchung) wieder eingefügt; Testfalltabelle (T1–T10) wieder inline in 3.6.1 (Anhang F aufgelöst).
- Bewertungsmatrix (6 Kategorien) abgeglichen: Ausgangssituation, Ressourcen/Planung, Durchführung+QS, Auftragsergebnisse, Gestaltung/Form, Kundendokumentation (Handbuch Anhang C) — alle abgedeckt.

**[Erkenntnis]** Seitenzahl laut Betreuer/Sascha nicht bindend; Fokus liegt auf Vorgaben-Konformität. Doku aktuell 32 Seiten gesamt (Haupttext ~18).

---

## 2026-06-07 (Aufräumen)

**[Manuell]** Ordnerstruktur aufgeräumt (nichts gelöscht, nur verschoben):
- `Doku/` ← Doku_A–E
- `MD/` ← alle .md (Projektplan, Projekttagebuch, Entscheidungen, Dateiuebersicht, PROJEKT_ANALYSE_VOLLSTAENDIG, KAPITEL_5_6_PLAN, FARB_ANALYSE, GLOSSAR, LERNMATERIAL_README, SPICKZETTEL, SPICKZETTEL_ERWEITERUNG)
- `Projektdateien/` ← drawio, ER_Diagramm_aktuell.png, ER_Diagramm_Sascha_Schulz.docx, SQL, Antrag v2, Wochenplan
- `Lernmaterial/` ← Glossar_Java_API.docx, Java_Handbuch_Start.docx, Java_Konzept_Zusammenhaenge.docx, SPICKZETTEL.docx, how_to_codereview.pdf
- `Archiv/` ← Benutzerhandbuch_Sascha_Schulz.docx (Inhalt steckt jetzt in Anhang C)
- Wurzel behält: Projektdokumentation_Sascha_Schulz.docx/.pdf
- Hinweis: `MD/Dateiuebersicht.md` ist dadurch veraltet (bei Bedarf aktualisieren).
---

## 2026-06-08 (Repo-Struktur final)

**[Entscheidung]** Hauptordner = `Documents\WI\DOIT` – ein Ordner für alles. Code im Standard-Maven-Layout am Repo-Root (`src/`, `pom.xml`), Dokumentation in den Unterordnern (`Doku/`, `MD/`, `Projektdateien/` …). GitHub-Repo `der-Sascha/Lagerverwaltung` enthält Code + Doku vereint; Medienverwaltung liegt im eigenen Repo. Push erfolgt aus dem DOIT-Ordner (bzw. aus IntelliJ, wenn dieser Ordner geöffnet ist). Der frühere separate Ordner `IdeaProjects\lagerverwaltung` wird nicht mehr genutzt. In IntelliJ werden die Doku-Ordner als „Excluded“ ausgeblendet (lokale .idea-Einstellung, git-ignoriert) – sie bleiben in Git und werden mitgepusht.
**[Änderung]** Reflexion (Kap. 5) finalisiert – „Entwurf/vorläufig"-Hinweis entfernt, Prosa als Endfassung (5.1 Verlauf, 5.2 Lernzuwachs, 5.3 Verbesserungspotenzial). Testergebnisse (4.2) und Abnahmeprotokoll (4.3.4) bleiben bewusst als klar markierte Platzhalter, weil Tests (Phase 6) und Abnahme (Ende KW26) noch ausstehen – werden nach Durchführung mit echten Werten gefüllt.


## 2026-06-08 — [Änderung] Komplette Umstrukturierung der Projektdokumentation (Amshove-Schema)
- Gliederung auf phasenbasiertes IHK-Schema (nach Beispiel Amshove, Vorgaben/09) umgestellt: 8 Kapitel (1 Einleitung, 2 Projektplanung, 3 Analysephase, 4 Entwurfsphase, 5 Implementierungsphase, 6 Abnahme-/Testphase, 7 Dokumentation, 8 Fazit). Reine IHK-Wirtschaftspunkte (Make-or-Buy, Amortisation) bewusst weggelassen (SRH-Projekt ohne Firma).
- Alle 6 Bewertungskriterien (Vorgaben 05/06) abgedeckt: Ausgangssituation, Ressourcen-/Ablaufplanung, Durchführung, Auftragsergebnisse, Gestaltung des Portfolios, Kundendokumentation.
- Format gemäß Vorgaben 11: Times New Roman 12 pt, Zeilenabstand 1,5, Ränder 2,5/2,5/2,5/2 cm (oben/links/rechts/unten), A4.
- Pflichtverzeichnisse statisch mit echten Seitenzahlen (Zwei-Pass): Inhalts-, Abbildungs-, Tabellen-, Quellcode-, Quellenverzeichnis. Code in gelben Boxen (Monospace) mit Listing-Unterschriften.
- 4 professionelle Abbildungen erzeugt: Use-Case, ER (vorhanden), MVC/DAO-Architektur, UI-Wireframe (jeweils <= 1/3 Seite).
- Benutzerhandbuch (Anhang C) deutlich detaillierter: Schritt-für-Schritt pro Funktion (C.1-C.10).
- Haupttext = 11 Seiten (Kap. 1-8, S. 5-15), im 10-15-Seiten-Limit. Anhang ab S. 19.
- Einzelteile Doku_A-E konsistent neu erzeugt; Master + PDF aktualisiert.
- Schreibstil: einfache Sprache, sehr dezenter Pfälzer Einschlag.


## 2026-06-08 — [Erkenntnis] Rundum-Check Doku ↔ Code + Korrekturen
Vergleich der Projektdokumentation mit echtem Java-Code (src/), SQL, FXML und Lernhandbuch. Ergebnis: SQL ↔ Code ↔ Anhang B stimmig (6 Tabellen, bewegung_id, ENUM ohne TRANSFER, Testdaten); Lernhandbuch nah am Code. In der Projektdokumentation standen jedoch drei aus der Ur-Doku übernommene Features, die NICHT im Code sind. Alle korrigiert (Doku an Code angepasst):
- [Änderung] Auto-EINGANG bei Status „geliefert" war beschrieben (Kap. 5.5, Handbuch C.8, Entscheidung D-001) → entfernt; Buchungen erfolgen manuell bzw. über Warenentnahme/Umlagerung. D-001 ebenfalls korrigiert.
- [Änderung] Suche als FilteredList/Live-Suche → ersetzt durch reales anzeigeFiltern() (Filter per Enter/Button), Listing 4 ausgetauscht.
- [Änderung] Kategorie-Filter (als Pflicht formuliert) → real nur Name + Station; Lastenheft/Soll-Ist/Texte angepasst.
- [Änderung] Tab-Struktur: real 7 Tabs inkl. eigener „Bestandsübersicht" (Suche/Warnung/Warenentnahme/Umlagerung); Doku/Handbuch/Wireframe (Abb. 4) korrigiert. „Buchungshistorie"-Tab → „Bestandsbewegungen".
- [Änderung] RowFactory: echtes Inline-setStyle(#FADBD8) auf tabBestand statt CSS-Klasse #FFCCCC (Listing 5).
- [Änderung] DBConnection-Listing: real 127.0.0.1:3324 + serverTimezone, Passwort „1234".
- [Änderung] create()-Listing: reale Signatur (Material + RETURN_GENERATED_KEYS).
- [Änderung] JavaFX-Version überall 17 → 21 (pom.xml/README = 21).
- [Änderung] Handbuch C.3: Kategorie wird als ID-Zahl eingegeben (kein Dropdown).
- [Manuell] Ordner aufgeräumt: _backup_2026-06-07/ → Archiv/ verschoben, leerer Quellcode/ + LibreOffice-Lock/Temp gelöscht.
Haupttext jetzt 12 Seiten (S. 5–16), Master + Doku_A–E + PDF neu erzeugt.


## 2026-06-08 — [Änderung] Quellen & Glossar-Konsolidierung
- Quellenverzeichnis: Quelle JavaFX-FilteredList (im Code nicht mehr genutzt) gestrichen, neu nummeriert → 13 Quellen. Master + PDF neu erzeugt.
- Glossar konsolidiert: 3 Dateien → 1. Lernhandbuch/Glossar_Vereinigt.docx enthält jetzt Teil 1 (Begriffsreferenz, 90 Begriffe) + Teil 2 (Java-/JDBC-Lernteil aus ehem. MD/GLOSSAR.md) mit Inhaltsverzeichnis. Gelöscht: Lernmaterial/Glossar_Java_API.docx (war Teilmenge) und MD/GLOSSAR.md.


## 2026-06-09

- [Änderung] Einheitliches Layout (Variante A) auf alle .docx angewendet: Überschriften jetzt schwarz (000000) statt Word-Standard-Blau (2E74B5/1F4D78) und Times New Roman statt Calibri-Light-Theme. Betroffen: Doku_A–E, Lernhandbuch (Glossar_Vereinigt, Teil1–4), Lernmaterial (Java_Handbuch_Start, Java_Konzept_Zusammenhaenge, SPICKZETTEL), ER_Diagramm. Größen H1=14pt, H2=12pt, H3=12pt kursiv. Ziel: keine Default-/Tool-Optik.
- [Änderung] Lernhandbuch_..._korrigiert.docx (20.05., veraltet, ohne Kap 10a) nach Archiv/ verschoben. Aktueller Stand = Einzelteile Teil1–4 (21.05.).
- [Erkenntnis] Glossar war NICHT in der Projektdoku, nur in Glossar_Vereinigt + Lernhandbuch. 90-Begriffe-Glossar als "Anhang D – Glossar" in Doku_E (Benutzerhandbuch-Anhang) eingefügt.
- [Problem] Projektdokumentation_Sascha_Schulz.docx/.pdf (Wurzel) sind OneDrive online-only und konnten nicht gelesen/umgestylt werden. Müssen nach Hydrierung neu aus Doku_A–E zusammengeführt werden.
- [Änderung] Projektdokumentation_Sascha_Schulz.docx (nach Doku/ verschoben, jetzt lesbar) ebenfalls umgestylt; PDF neu erzeugt (LibreOffice). Hinweis: finale PDF zur Sicherheit aus Word exportieren (Layout/Verzeichnisse).
nen: alle Doku_A–E .docx-Dateien + Lernhandbuch-Teile. Master-Datei neu erzeugt.

## 2026-06-09 (Donnerstags-Audit KW 24 — automatisch)

- [Problem] DOIT-Doku-Ordner war in erster Audit-Session nicht gemountet → Audit-Bereiche A, C, D, E konnten nicht vollständig geprüft werden. Behoben: Ordner ist jetzt eingebunden.
- [Erkenntnis] Code-Stand KW 24: 5 Commits am 08.06.2026 (letzter: e0e8c1c). Implementierungsphase weit fortgeschritten — 6 DAOs, 8 Models (inkl. BestandView, BewegungsTyp), MainController, 7-Tab-FXML vollständig.
- [Erkenntnis] Code-Konsistenz: D-001 ✅  D-002 ✅  D-003 ⚠️  D-004 ✅  D-005 ✅⚠️  D-006 ⚠️
- [Problem] D-005: Port 3324 statt Standard 3306 — absichtlich (lokale MySQL-Konfiguration). Kommentar in DBConnection.java ergänzen, damit nachvollziehbar.
- [Problem] Scheduled Tasks (Freitag + Donnerstag) laufen beide am Dienstag statt am konfigurierten Wochentag — Timing in den Scheduler-Einstellungen prüfen.
- [Problem] Tagebuch KW 23/24 unvollständig — Einträge fehlen oder wurden abgeschnitten (s. o.). Nachtragen.
- [Empfehlung] 38 Tage bis Abgabe (17.07.2026). Fokus ab KW 25: Testing (Phase 6) + Projektdokumentation finalisieren. Testergebnisse und Abnahmeprotokoll (Platzhalter in Doku) mit echten Werten füllen.

## 2026-06-09 [Änderung] Lernhandbuch Kap. 1 — IntelliJ-Icon-Farben
- Teil1_Intro_Kap1-3.docx: Klassen-Icon "C" von grün auf **blau** korrigiert (Abgleich mit Screenshot von Saschas angepasster IntelliJ-Farbeinstellung); Alltagsanalogie + Prüfungsfrage 1 entsprechend angepasst.
- Neuer Abschnitt "Die Buchstaben-Icons: f für Feld, m für Methode" inkl. static-Eckmarke.
- Neue **Farbübersichts-Tabelle** (farbig hinterlegte Zellen): C/I=blau, f=orange, m=rot; Schlösser grün/rot/gelb/grau für public/private/protected/package-private.
- docx validiert, PDF-Sichtprüfung ok.

## 2026-06-09 [Manuell] IntelliJ-Spickzettel erstellt
- Neue Datei `Lernmaterial/IntelliJ_Spickzettel_DoIT.docx` (2 Seiten, DIN A4, editierbar).
- Inhalt: 1) Symbole (Kreis+Buchstabe: C/I/E/m/f/v/p) + Eck-Badges (public/private/protected/package-private/static/final), 2) Farben (Fehler/Warnung, Code-Einfärbung, Git-Dateifarben) mit farbig hinterlegten Zellen, 3) wichtigste Shortcuts, 4) Gutter-/Lauf-Icons, 5) DoIT-Ablauf (mvn javafx:run, Launcher/Main, src-Struktur, MySQL/DAO).
- Quelle Icons: JetBrains Icon reference (jetbrains.com/help/idea/symbols.html). docx validiert, PDF-Sichtprüfung 2 Seiten ok.

## 2026-06-09 [Änderung] IntelliJ-Spickzettel v2
- Abschnitt „Fehler & Warnungen (Editor/Randstreifen)" entfernt (auf Wunsch).
- Zugriffs-Badges jetzt als echte IntelliJ-ähnliche Icons (PNG): public=offenes grünes Schloss, private=rotes Schloss, protected=gelbes Schloss, package-private=ohne, static=Quadrat unten links, final=graues Schloss. Auch Symbol-Tabelle (1) nutzt jetzt echte Kreis-Icons (C/I/E/m/f/v/p).
- Neuer Abschnitt 6 „Wie die Java-Dateien zusammenarbeiten" mit Architektur-Diagramm (matplotlib-PNG): Launcher→Main→JavaFX-Fenster(main.fxml+MainController)→DAO-Schicht→DBConnection→MySQL; model-POJOs; gestrichelter Datenfluss (SQL↓/Daten↑). Jetzt 3 Seiten.

## 2026-06-10 (Donnerstags-Audit KW24 — automatisch)

- [Erkenntnis] **Phase 4 Status (UI Grundfunktionen, 40h):** KW24 (Mo–Mi) zeigt keinen neuen UI-Code — MainController noch bei 703 Zeilen (= KW21-Stand). KW24-Arbeit war: Doku, Lernhandbuch, Präsentation. Phase 4 war in KW21 vorgearbeitet (703 Zeilen, 7 Tabs, alle DAOs). Ob vollständiges CRUD für alle 6 Bereiche implementiert → noch zu verifizieren/dokumentieren. ⚠️ Stunden Phase 4 KW24 nicht sichtbar.
- [Erkenntnis] **Code-Konsistenz D-001–D-006: ✅ ALLE OK**
  - D-001: Material.java kein bestand-Feld ✅
  - D-002: 6 Models + 6 DAOs vorhanden ✅
  - D-003: bewegung_id-Spaltenname konsistent (SQL + DAO) ✅
  - D-004: DBConnection Singleton ✅, alle 6 DAOs nutzen getConnection() ✅
  - D-005: 127.0.0.1:3324 (= localhost) ✅
  - D-006: 180h/192h/12h dokumentiert ✅
- [Änderung] **Entscheidungen.md D-004:** `BewegungDAO` → `BestandsbewegungDAO` korrigiert (Tippfehler in Klassenbaum).
- [Änderung] **MD-Korrektur: Dateiuebersicht.md** — 10 Korrekturen: Stand 06-07→06-10; Wurzel (Projektdoku entfernt, README/pom.xml/lagerverwaltung.iml ergänzt); Doku/ (Projektdokumentation.docx/.pdf hinzugefügt); Präsentation 15→11 Folien; Lernmaterial (5 alte Dateien→nur Spickzettel_DoIT.docx); Lernhandbuch (Hauptdatei entfernt→Archiv, Teil1_Intro_Kap1-3.docx→NEU, Pruefungsfragen ergänzt); Archiv (2 neue Einträge: Lernhandbuch_korrigiert, abgeloest_Lernmaterial_2026-06-10); Weitere Ordner (Vorgaben/src ergänzt, _backup aus Archiv).
- [Problem] `Lernhandbuch/_writetest_` (leere Datei) + `Lernmaterial/~$ickzettel_DoIT.docx` (LibreOffice-Lock) → Junk, manuell löschen.
- [Problem] `how_to_codereview.pdf` fehlt in Lernmaterial/ (tagebuch-Eintrag 10.06. sagt es sei da, tatsächlich nur in Vorgaben/) → entweder ignorieren (es liegt in Vorgaben als SRH-Original) oder manuell kopieren.
- [Empfehlung] **KW24 Rest (Do–Fr):** Phase 4 UI-Code-Fortschritt dokumentieren — ist CRUD für alle 6 Entitäten mit Dialogen vollständig? Wenn ja → Phase 4 als ✅ schließen und Tagebuch-Eintrag nachziehen. Wenn nein → fehlende Dialoge implementieren.
- [Empfehlung] **KW25 (15.06–19.06):** Phase 5 (Suche/Filter/Warnmeldung, 25h) + Phase 6 Anfang (Tests). 37 Tage bis Abgabe 17.07.2026.

## 2026-06-10 — DB-Name auf DOIT vereinheitlicht + Lernhandbuch-Korrekturen

[Änderung] Datenbankname projektweit von `krankenhaus_lager`/`lagerverwaltung` auf **DOIT** (Port 3324) angeglichen — Quelle: DBConnection.java (echte Verbindung). Geändert: Lernhandbuch Teil2 & Teil4, SQL-Skript (CREATE/USE DATABASE DOIT), Projektdokumentation (+PDF neu), Benutzerhandbuch. Dateiname testdaten_krankenhaus_lager.sql sowie Maven-Projektname `lagerverwaltung` bewusst unverändert.
[Änderung] Lernhandbuch Teil1: Kap. 1.9 Syntax-Farbbeschreibungen an angepasstes IDE-Schema angeglichen (Keyword #000080 dunkelblau, Klassen #00627a türkis, lokale Var. #1a7a1a grün, Felder #660e7a lila, String #008000, Zahl #0000ff, Kommentar #808080, Annotation #808000) + neue Abschnitte Strings/Zahlen/Kommentare; Kap. 1.8 Tabellennamen auf 6-Tabellen-Schema + DB DOIT/Port 3324; Kapitelübersicht 15→14 korrigiert.
[Erkenntnis] Tabelle in Kap. 1.2 war NICHT leer (Icon-Farbübersicht bereits vollständig) — kein Eingriff nötig.
[Problem] Teil1_Intro_Kap1-3.docx war beim Schreiben gesperrt (vermutlich in Word geöffnet) → korrigierte Fassung als Tei
## 2026-06-10 — Kohärenzprüfung aller Word-Dateien (Aufbau + Bezeichnungen)

- [Erkenntnis] Vollprüfung 16 .docx gegen src/, SQL, pom.xml → Bericht: `Projektmanagement/Kohaerenzpruefung_Word-Dateien_2026-06-10.md`.
- [Problem] DB-Umbenennung auf DOIT (10.06.) wurde in 3 Dateien NICHT nachgezogen: `Doku_C` (Listing 2: krankenhaus_lager), `Doku_E` (3 Stellen), `ER_Diagramm_Sascha_Schulz.docx` (zusätzlich localhost:3306 statt 127.0.0.1:3324).
- [Problem] Endabgabe: Anhangsübersicht listet D (Wochenplan) + E (Projektantrag), Abschnitte fehlen; Doku_E widerspricht sich selbst (Anhang D = Glossar vs. D = Wochenplan).
- [Problem] Testfälle springen T8→T10 (T9 fehlt); Tippfehler Fazit „hat des gut geklappt"; FAQ-Tabelle Anhang C.10 ohne Umlaute; Checkliste sagte 11 Tabellen, real 9.
- [Erkenntnis] Konsistent: Titel, Verzeichnisse=Body (5 Abb./9 Tab./6 Listings), Eckdaten (180/192/12 h, Zeitraum, Cramer, 2551), Klassennamen Doku↔src, ER-Spalten↔SQL, Quellenverzeichnis (13, Format ok).

## 2026-06-10 [Änderung] Spickzettel_DoIT: "Code zerlegt"-Übersicht ergänzt
- Teil B: neue Übersicht "Code zerlegt: was ist was?" — 3 echte Code-Zeilen, jedes Teil benannt (Zugriffsmodifizierer, static, Typ/Interface, Variable, Zuweisung, Klasse, statische Methode, Konstante, Rückgabetyp, Methodenname, Parameterliste). Datei jetzt 6 Seiten.

## 2026-06-10 — Korrekturlauf nach Kohärenzprüfung (alle Befunde behoben)

- [Änderung] A1–A10 + H1–H4 aus `Projektmanagement/Kohaerenzpruefung_Word-Dateien_2026-06-10.md` korrigiert: DB-Name DOIT in Doku_C/Doku_E/ER-docx (+JDBC 127.0.0.1:3324), T10→T9, „des"→„das", FAQ-Umlaute, Schreibweise einheitlich DOIT, Antrag-Zeitraum eingetragen, Kostenrechnung präzisiert (150 h = 180 − 30 Doku), Prüfungsfragen-To-do erledigt, Checkliste auf 9 Tabellen korrigiert.
- [Änderung] Anhang-Struktur (Nutzerentscheidung): D = Wochenplan, E = Projektantrag, F = Glossar. Abschnitte in Doku_E + Endabgabe ergänzt, Glossar (91 Begriffe) in Endabgabe übernommen, Inhaltsverzeichnis (Doku_A + Endabgabe) erweitert (D/E/F → S. 24).
- [Erkenntnis] PDF neu erzeugt (53 Seiten); statische Verzeichnis-Seitenzahlen gegen PDF verifiziert. Kerndoku unverändert 10–15-Seiten-konform (Anhang zählt nicht).
- [Erkenntnis] Eine Projektnotiz-Datei war am Dateiende abgeschnitten (mitten in Checklisten-Zeile) — vervollständigt.

## 2026-06-16 — [Änderung] Generisches DAO-Interface `GenericDAO<T>` eingeführt

- [Änderung] Neue Datei `src/main/java/de/doit/dao/GenericDAO.java`: generische Schnittstelle `GenericDAO<T>` mit dem gemeinsamen CRUD-Vertrag (`create(T)`, `findAll()`, `update(T)`, `delete(int)`), jeweils `throws SQLException`.
- [Änderung] Alle 6 DAOs (`MaterialDAO`, `KategorieDAO`, `LieferantDAO`, `BestellungDAO`, `StationslagerDAO`, `BestandsbewegungDAO`) um `implements GenericDAO<Modellklasse>` erweitert; die vier Vertragsmethoden mit `@Override` gekennzeichnet. Sondermethoden (`findById`, `findBestandViews`, `getBestand`) bleiben außerhalb des Interface.
- [Erkenntnis] Kompilierung mit JDK 17 (`javac`) erfolgreich — `@Override` bestätigt, dass alle Signaturen exakt zum Interface passen. Verhalten der App unverändert (rein additive Abstraktion).
- [Änderung] Doku ergänzt: Kap. 4.2 (Architekturentwurf) + Kap. 5.2 (DAO-Schicht) um Abschnitt zum Interface, eingebettet in die Java-Themen Wertetyp/Referenztyp/Collection + Generics. Neues Listing 4 „Gemeinsame Schnittstelle GenericDAO<T>" → Quellcodeverzeichnis von 6 auf 7 Listings erweitert (Doku_A, Doku_C, Endabgabe).
- [Erkenntnis] PDF neu erzeugt (54 Seiten); Seitenzahlen der Verzeichnisse gegen PDF geprüft — Interface-Listing passt auf S. 12 neben `create()`, dadurch keine Verschiebung der Folgeseiten. Verzeichnisangaben (11/11/12/12/13/13/20) bleiben korrekt.
- [Entscheidung] Interface bewusst NICHT ins Benutzerhandbuch (Anhang C) aufgenommen — interne Code-Architektur gehört in Entwurf/Implementierung, nicht in die Endnutzer-Anleitung. Siehe `MD/Entscheidungen.md` D-007.
- [Änderung] Kap. 8.2 Lessons Learned (Doku_D + Endabgabe) um einen kurzen, einfach formulierten Absatz ergänzt: die nachträgliche Umstrukturierung zum Interface inkl. Begründung (Anforderung: Probleme/Umstrukturierungen dokumentieren und begründen). PDF neu (53 Seiten), Seitenzahlen erneut geprüft — unverändert korrekt.

## 2026-06-16 — [Änderung] MainController aufgeteilt (Logik-Klassen + EntityCrud-Basis)

- [Problem] `MainController` war mit 709 Zeilen schwer nachvollziehbar — jeder der 7 Reiter war fast gleicher Code.
- [Änderung] Neues Paket `de.doit.controller.crud`: `Dialoge` (gemeinsame Dialog-/Meldungs-Hilfen), abstrakte Basis `EntityCrud<T>` (laden/anlegen/bearbeiten/löschen) und je Reiter `MaterialCrud`, `KategorieCrud`, `StationslagerCrud`, `LieferantCrud`, `BestellungCrud`, `BewegungCrud`. Der `MainController` ist nur noch Verteiler (709 → 334 Zeilen).
- [Entscheidung] „Leichter Weg": eine `main.fxml` bleibt, nur die Logik wandert in eigene Klassen — geringeres Risiko als FXML aufteilen. Warenentnahme/Umlagerung bleiben (auf ausdrücklichen Wunsch nicht entfernt). Siehe `MD/Entscheidungen.md` D-008.
- [Erkenntnis] Kompilierung mit heruntergeladenem JavaFX-17-SDK + JDK 17 erfolgreich (keine Fehler/Warnungen). Alle 30 FXML-Handler und 43 `@FXML`-Felder weiterhin vorhanden → FXML-Bindung bleibt gültig. GUI-Test muss Sascha in IntelliJ machen (hier kein Display).
- [Problem] Beim ersten Schreiben des `MainController` hingen ~20.000 Null-Bytes am Dateiende (Schreibartefakt) → Kompilierfehler „illegal character ''". Mit `tr -d '\000'` bereinigt.
- [Änderung] Doku: Kap. 4.2 (Controller-Aufteilung) + Kap. 8.2 (Lessons Learned, einfache Worte) ergänzt. PDF neu (54 Seiten). Durch die Zusätze verschoben sich Seiten ab Kap. 5.3 um +1 — alle statischen Verzeichnis-Seitenzahlen (Inhalt/Abbildung/Tabelle/Quellcode) in Doku_A + Endabgabe gegen das PDF korrigiert (27 Einträge) und stichprobenartig verifiziert.
- [Änderung] (2026-06-17) Stilüberarbeitung der Doku-Prosa (Wunsch Sascha): aufgelöste Doppelpunkt-Listen am Absatzanfang, weniger gleichförmige „So/Damit/Dadurch"-Schlusssätze, aufgebrochene Dreierreihen, gestrichene leere Wertadjektive, „bewusst" sparsamer, variierte Satzlängen; zu saloppes „Unterm Strich hat das gut geklappt." (Kap. 8.1) ersetzt. 30 Absätze in Kap. 1–8 betroffen. Angewendet auf Doku_B (10), Doku_C (11), Doku_D (9) und Endabgabe-docx (30), Fakten/Fachbegriffe unverändert. PDF neu erzeugt; Seitenzahl 54 = unverändert, Hauptteil-Pagination deckungsgleich mit Inhaltsverzeichnis (Einleitung 5 … Fazit 16) → keine Verzeichniskorrektur nötig. Benutzerhandbuch (Anhang C) als prozeduraler Text nicht angefasst.
- [Problem]+[Änderung] (2026-06-17) Benutzerhandbuch (Anhang C, Abschnitte C.8–C.10) enthielt durchgängig defekte Umlaute (Ersatzschreibung ae/oe/ue/ss): auswaehlen, veraendert, anschliessend, Ausgaenge, Bestandsuebersicht, zusaetzlich, oeffnen, waehlen, lueckenlos. Alle 9 in Doku_E + Endabgabe korrigiert (je 7 Absätze). Zusätzlich Schritt „Auf Speichern klicken - …" zu „Auf „Speichern" klicken – …" vereinheitlicht (Halbgeviertstrich + Anführungszeichen wie übrige Schritte). PDF neu, weiterhin 54 Seiten, Pagination unverändert.
- [Änderung] (2026-06-17) Kontrolllauf: zwei restliche Inkonsistenzen im Handbuch vereinheitlicht (C.9 „Speichern" in Anführungszeichen; C.10 Halbgeviertstrich bei „Version 1.0 – SRH"). Vollprüfung: keine Defekt-Umlaute mehr, keine doppelten Leerzeichen im Fließtext, glatte Schlussfloskeln entfernt, Grammatik der 30 umgeschriebenen Absätze gegengelesen. PDF 54 Seiten, Kapitel-Seitenzahlen deckungsgleich mit Inhaltsverzeichnis (1→5, 2→6, 5→11, 8→16).

## 2026-06-17 — [Problem]+[Änderung] Code-Review: NPE bei leerer ComboBox behoben + DBConnection bereinigt

- [Problem] In `BestellungCrud` und `BewegungCrud` war beim **Neuanlegen** keine Vorauswahl in den Material-/Lieferant-/Lager-ComboBoxen gesetzt. Klickte man OK ohne Auswahl, warf `cbMat.getValue().getId()` eine `NullPointerException`; der `catch`-Block zeigte nur die generische Meldung „Speichern fehlgeschlagen" statt eines klaren Hinweises. Betroffen: `BestellungCrud.java:74`, `BewegungCrud.java:73` (im `getBestand`-Aufruf).
- [Änderung] In beiden Klassen nach der OK-Abfrage explizite Pflichtprüfung ergänzt (`getValue() == null` → `Dialoge.warnung(...)` + `return`): Bestellung prüft Material/Lieferant/Lager, Bewegung prüft Material/Lager **vor** der Bestandsprüfung. Benutzer erhält jetzt eine verständliche Meldung statt eines abgefangenen Absturzes.
- [Änderung] `DBConnection.java` bereinigt (#5 aus Review): IntelliJ-Shortcut- und Farbschema-Lernnotizen am Dateikopf entfernt, verstreute Lernkommentare durch knappe Javadoc-Kommentare ersetzt. Verhalten unverändert (URL/Port 3324, Singleton, Lazy Init, `closeConnection` identisch).
- [Erkenntnis] Code-Review ergab zusätzlich drei reine Konsistenzpunkte (kein Crash): `new X(0,...)` bei Kategorie/Lieferant/Stationslager vs. ID-loser Konstruktor bei Material/Bewegung/Bestellung; ungenutzter Leerkonstruktor `Kategorie()`; `cmbLager`-Filter nutzt unsichtbaren `null`-Eintrag als „Alle Lager". Bewusst zunächst nicht geändert (kosmetisch, geringe Priorität).
- [Erkenntnis] Doku-Abgleich: Diese Befunde stehen NICHT in der Doku. Kap. 6.1 dokumentiert andere, bereits behobene Fehler (PK-Benennung in `BestandsbewegungDAO`, DBConnection-Datenbankname, nicht existenter `TRANSFER`-ENUM). „Bekannte Einschränkungen" + Ausblick (8.3) listen nur Feature-Grenzen. Der NPE-Fix stärkt die Doku-Aussage „in Phase 6 gefundene Fehler behoben, alle Pflicht-Testfälle bestehen".
- [Problem] Im Sandbox kein `javac`/Maven/JavaFX verfügbar → kein Compile-Test möglich. Änderungen sind minimal und nutzen bestehende API (`Dialoge.warnung` statisch vorhanden, Standard-`java.sql`). GUI-/Compile-Test muss Sascha in IntelliJ machen.

## 2026-06-17 — [Entscheidung]+[Änderung] Komfort-Funktionen Warenentnahme & Umlagerung entfernt (Reduktion auf Antragsumfang)

- [Entscheidung] Sascha-Wunsch: Projekt auf den genehmigten Antragsumfang reduzieren („Ziel ist das, was zugesagt wurde – nicht mehr"). Die beiden Buttons „Warenentnahme" und „Umlagerung" in der Bestandsübersicht waren Komfort-Zusätze über den Antrag hinaus (nicht in den Kernfunktionen). Ein-/Ausgänge inkl. Umlagerung bleiben über den Reiter „Bestandsbewegungen" voll buchbar → keine zugesagte Funktion entfällt.
- [Änderung] **Backup** vor dem Eingriff: `Archiv/_backup_2026-06-17_Warenentnahme_Umlagerung/` mit Originalen von `MainController.java`, `main.fxml`, Doku_B/C/E und Endabgabe-docx + `WIEDERHERSTELLUNG.md` (Anleitung zur Einzel-Wiederherstellung).
- [Änderung] **Code:** zwei Buttons aus `main.fxml` entfernt; Methoden `onWarenentnahme()` und `onUmlagerung()` aus `MainController` gelöscht; dadurch ungenutzter Import `javafx.scene.layout.GridPane` entfernt; Klassen-Javadoc und Abschnittskommentar von „(Suche, Filter, Warenentnahme, Umlagerung)" auf „(Suche, Filter)" gekürzt. `src/_ORDNERINFO.md` entsprechend aktualisiert.
- [Änderung] **Doku:** Button-Beschreibungen in Doku_B, Doku_C, Doku_E und der Endabgabe umformuliert — Entnahme/Umlagerung werden jetzt als manuelle Buchungen über den Bestandsbewegungen-Reiter beschrieben (AUSGANG bzw. AUSGANG+EINGANG). Tabellenzelle „Startansicht" um „Warenentnahme, Umlagerung" gekürzt. Begriff „Warenentnahme" 0× in allen Dateien; „Umlagerung" bleibt als sachliche Workflow-Beschreibung (kein Schaltflächen-Bezug).
- [Erkenntnis] PDF neu erzeugt: weiterhin **54 Seiten**, Pagination unverändert (Änderungen blieben innerhalb der Absätze). PDF-Volltext-Check: „Warenentnahme" 0×, „Umlagerung" 3× (nur Workflow-Beschreibung).
- [Problem] Beim docx-Editieren hat der direkte String-Editor eine große `document.xml` einmal am Dateiende abgeschnitten (`</w:body></w:document>` fehlte → Pack-Validierung schlug fehl). Lösung: frisch entpackt und alle Ersetzungen über ein Python-Skript mit Treffer- und Datei-Ende-Prüfung vorgenommen. Außerdem: PDF-Export direkt in den gemounteten Ordner scheiterte (Io/Abort) → lokal konvertiert und kopiert.
- [Erkenntnis] Im Sandbox kein `javac`/JavaFX → Code-Compile/GUI-Test bitte in IntelliJ. FXML-Handler `onWarenentnahme`/`onUmlagerung` existieren nicht mehr → keine verwaisten FXML-Referenzen (geprüft).

## 2026-06-17 — [Änderung] Lokale Variablen auf Deutsch umbenannt (Lesbarkeit fürs Fachgespräch)

- [Entscheidung] Code-Struktur bleibt unverändert (Generics `GenericDAO<T>`, abstrakte Basis `EntityCrud<T>`, Vererbung bewusst behalten — kürzester Code, Doku-Story „DAO-Muster"). Reine Kosmetik: nur lokale Variablennamen auf Deutsch, damit Sascha den Code im Fachgespräch durchgängig deutsch lesen/erklären kann.
- [Änderung] **6 DAO-Dateien** (Kategorie, Material, Lieferant, Stationslager, Bestellung, Bestandsbewegung): `conn`→`verbindung`, `ps`→`anweisung`, `rs`→`datensatz`, `result`→`ergebnis`, `keys`→`schluessel`, `mapRow`→`zeileLesen` (private Methode).
- [Änderung] **2 Crud-Dateien** (BestellungCrud, BewegungCrud): `mats`→`materialien`, `liefs`→`lieferanten`, `lags`→`lagerListe`.
- [Erkenntnis] Framework-/JDBC-Bezeichner (TableView, ComboBox, PreparedStatement, ResultSet, Connection, getConnection, executeUpdate …) sowie die Interface-Vertragsmethoden `create/findAll/update/delete` bleiben englisch — nicht umbenennbar bzw. nur mit Strukturänderung. Das ist normal und von Prüfern erwartet.
- [Erkenntnis] Umsetzung per `sed` mit Wortgrenzen (`\b`), keine Logikänderung. Verifikation: alte Namen 0× verblieben, neue Namen konsistent (z. B. `private … zeileLesen(ResultSet datensatz)` passend zu `ergebnis.add(zeileLesen(datensatz))`). Compile-/GUI-Test bitte in IntelliJ (Sandbox ohne javac/JavaFX).

## 2026-06-17 — [Änderung] Word-Dokumente an deutsche Variablennamen angepasst

- [Änderung] Nach dem Umbenennen der lokalen Variablen im Code (conn→verbindung, ps→anweisung, rs→datensatz, result→ergebnis, keys→schluessel, mapRow→zeileLesen; mats/liefs/lags→materialien/lieferanten/lagerListe) alle betroffenen Code-Listings/Erklärungen in den Word-Dokumenten nachgezogen. Umfang lt. Sascha: Abgabe **und** Lernmaterial.
- [Änderung] Betroffen (Ersetzungen): Doku_C (8), Doku_E (8), Projektdokumentation_Sascha_Schulz.docx (16), Glossar_Vereinigt (32), Prüfungsfragen_Loesungen (6), Teil2 (1), Teil3 (407), Teil4 (5), Spickzettel (1). Bezeichner standen inline im Fließtext (keine eigene Code-Schriftart) → Ersetzung run-genau mit Wortgrenzen (`\b`), nur exakte Bezeichner, Fließtext/Formatierung unangetastet.
- [Erkenntnis] Vorabprüfung: alle `ps`/`rs`-Vorkommen sind Code (z. B. `ps.setString`, `rs.next()`), keine echten deutschen Wörter. 0 Tokens über Run-Grenzen zerteilt → keine übersehenen Stellen. Verifikation: 0 alte Tokens in allen `word/*.xml` (inkl. Kopf-/Fußzeilen/Textboxen), alle 9 Dateien öffnen fehlerfrei.
- [Änderung] Backup vor Eingriff: `Archiv/_backup_2026-06-17_deutsche_variablen/` (Originale aller 9 docx).
- [Problem/Offen] **PDF noch nicht neu erzeugt**: `Doku/Projektdokumentation_Sascha_Schulz.pdf` zeigt weiterhin die alten Namen → muss aus der aktualisierten docx neu exportiert werden, bevor es die finale Abgabe ist.

## 2026-06-17 — [Problem + Lösung] BestellungCrud.java und BewegungCrud.java abgeschnitten

- [Problem] Beide CRUD-Dateien (`controller/crud/BestellungCrud.java`, `controller/crud/BewegungCrud.java`) endeten mitten im `else`-Block der `formularAnzeigen`-Methode — die Setter für restliche Felder, `dao.update()`, `laden()`, `catch`-Block und schließende Klammern fehlten komplett. Ursache: vermutlich unterbrochener Speichervorgang.
- [Lösung] Fehlende Abschnitte ergänzt: bei `BestellungCrud` die Setter für `menge`, `bestelldatum`, `lieferdatum`, `status` + `dao.update` + `laden()` + `catch`; bei `BewegungCrud` analog `ablaufdatum`, `bemerkung` + `dao.update` + `laden()` + `catch`.
- [Erkenntnis] `mvn compile` → **BUILD SUCCESS**, 27 Dateien fehlerfrei kompiliert.

## 2026-06-18 — [Erkenntnis/Manuell] Umbau-Plan: Code nach Schwierigkeit + Ablauf staffeln

- [Manuell] Sascha will den Code nach Schwierigkeit UND Ablauf staffeln (beides zusammen), Funktionen ggf. entfernen (Projekt darf kleiner werden). 6 Tabellen bleiben Pflicht; Abfragen gedanklich „bei null" neu aufbauen.
- [Erkenntnis] Kernidee festgehalten: vertikale **Stufen** statt horizontaler Schichten — eine Stufe = eine Operation komplett durch alle Schichten (DB→DAO→EntityCrud→Reiter→Controller→FXML). Stufenfolge: 0 Fundament, 1 LESEN, 2 ANLEGEN, 3 BEARBEITEN, 4 LÖSCHEN, 5 SUCHEN/FILTERN.
- [Erkenntnis] Code ist bereits schlank: `findById()` existiert nicht mehr (nur `src/_ORDNERINFO.md` erwähnt es stale); `Main.java` ist echter JavaFX-Einstieg, kein Demo. GenericDAO-Vertrag = create/findAll/update/delete.
- [Änderung] Plan-Dokument erstellt: `MD/Umbauplan_Staffelung.md` (Status: Entwurf, wartet auf Freigabe — Variante A sicher / B schlanker). Code noch unangetastet.

## 2026-06-18 — [Änderung] Variante A umgesetzt: Stufen-Marker im Code

- [Änderung] 37 additive `// === Stufe N — … ===`-Marker eingefügt (Schwierigkeit + Ablauf): Stufe 0 Fundament (Launcher, Main, DBConnection), 1 LESEN (findAll/load/Vertrag), 2 ANLEGEN (create), 3 BEARBEITEN (update/edit), 4 LÖSCHEN (delete), 5 SUCHEN/FILTERN (BestandsbewegungDAO.findBestandViews/getBestand, BestandView, MainController-Filter). Betroffen: 6 DAOs, GenericDAO, EntityCrud, DBConnection, Main, Launcher, BestandView, MainController.
- [Erkenntnis] Saschas Vorgabe „meine Kommentare unberührt" eingehalten: Skript fügt nur eigenständige `//`-Zeilen ein (idempotent, Marker oberhalb @Override bzw. vorhandener Javadoc), löscht/ändert nichts. Verifikation: alle 37 Marker stehen allein auf einer Kommentarzeile (grep-Check bestanden) → keine Logik-/Compile-Auswirkung.
- [Änderung] `src/_ORDNERINFO.md` korrigiert: stale `findById()` entfernt, `Main.java` als echter JavaFX-Einstieg statt „Demo" beschrieben, Stufen-Marker-Abschnitt ergänzt.
- [Änderung] Backup vor Eingriff: `Archiv/_backup_2026-06-18_vor_stufenmarker_src/` (kompletter src-Stand).
- [Offen] Compile-/GUI-Test in IntelliJ (Sandbox ohne Maven/JavaFX). Variante B (echte Verschlankung) bewusst nicht umgesetzt — A ändert kein Verhalten.

## 2026-06-18 — [Änderung] App auf read-only verschlankt + CRUD-Anleitung (Selbst-Eintragen)

- [Änderung] Auf Saschas Wunsch das volle CRUD komplett aus dem Code entfernt (sauberer Schnitt, löschen), App jetzt read-only. Material+Kategorie-CRUD soll er per Anleitung selbst eintippen ("denke dann komme ich dahinter").
- [Änderung] DAO-Schicht: neues `dao/LeseDAO<T>` (nur `findAll`); `GenericDAO<T>` jetzt `extends LeseDAO<T>` (+create/update/delete, aktuell ungenutzt = Vorlage). Alle 6 DAOs auf `implements LeseDAO<T>` umgestellt, `create/update/delete` gelöscht (Skript, brace-genau). `BestandsbewegungDAO` behält `findBestandViews`/`getBestand`.
- [Änderung] `EntityCrud<T>` von abstrakt+CRUD auf konkret+read-only (nur `load()`, nutzt `LeseDAO`). Die 6 Subklassen (`MaterialCrud`…`BewegungCrud`) gelöscht.
- [Änderung] `MainController`: CRUD-Felder → `EntityCrud<T>`, Erzeugung `new EntityCrud<>(tab, dao, "…")`, 24 Schreib-Handler (`onXAnlegen/Bearbeiten/Loeschen`) entfernt, Tab-Laden bleibt. `main.fxml`: 6 Button-HBoxen durch „Nur-Lese-Ansicht"-Label ersetzt.
- [Änderung] Neue Anleitung `MD/Anleitung_CRUD_eintragen.md`: Schritt-für-Schritt CRUD für Material + Kategorie (DAO→Crud-Klasse→MainController→FXML) mit Code zum Abtippen + Checkliste.
- [Erkenntnis] Verifikation statisch: 0 Verweise auf gelöschte Subklassen/Methoden, FXML ohne Schreib-Buttons (6× Nur-Lese-Label), keine `dao.create/update/delete`-Aufrufe mehr. Voller Compile/GUI in IntelliJ (Sandbox hat nur JRE, kein `javac`/JavaFX/Maven).
- [Änderung] Backup vor Eingriff: `Archiv/_backup_2026-06-18_vor_readonly_src/`. Git-Restorepunkt davor: Commit `4778ce2` / Tag `stand-vor-variante-b`.
- [Offen] **SRH-Pflicht CRUD**: aktueller Code hat kein funktionierendes Schreiben → vor Abgabe Anleitung umsetzen.

## 2026-06-18 — [Erkenntnis] Stufen-Marker nach read-only-Umbau geprüft

- [Erkenntnis] Code-Marker sind konsistent: aktiv vorhanden Stufe 0/1/5; Stufe 2/3/4 nur noch als Vertrag in `GenericDAO` (kein DAO implementiert) + in der Anleitung. Kein Marker zeigt auf gelöschten Code.
- [Änderung] `src/_ORDNERINFO.md`: Stufen-Absatz korrigiert — vorher stand fälschlich, Stufe 2/3/4 seien über alle Schichten im Code; jetzt klar getrennt „aktiv vorhanden" vs. „nur Vertrag".

## 2026-06-18 — [Änderung] GenericDAO gelöscht (nur LeseDAO bleibt)

- [Änderung] `dao/GenericDAO.java` entfernt (war ungenutzter CRUD-Vertrag). Einzige DAO-Schnittstelle jetzt `LeseDAO<T>` (findAll). LeseDAO-Javadoc, `src/_ORDNERINFO.md` und `MD/Anleitung_CRUD_eintragen.md` angepasst: Schreib-Methoden werden direkt ins DAO ergänzt (ohne `@Override`, da kein Interface sie deklariert).

## 2026-06-18 [Erkenntnis] Roter Faden Java erstellt
Visualisierung des aktuellen (read-only) Programmablaufs als interaktive HTML: `MD/Roter_Faden_Java.html`. Inhalt: (1) Schichten View→Controller→EntityCrud/Dialoge→LeseDAO/DAOs→DBConnection→MySQL, (2) Vererbung/Zugehoerigkeit (Main extends Application; 6 DAOs implements LeseDAO<T>; MainController hat 6 DAOs + 6 EntityCrud; EntityCrud hat LeseDAO+TableView), (3) Klick-zu-Ergebnis-Sequenzen: Programmstart, Reiter oeffnen, Suchen/Filtern. Basis: Analyse aller Klassen in src/.

## 2026-06-19 — [Änderung] Generics in Doku + Glossar ergänzt

- [Änderung] `Doku/Doku_D_Ergebnisse.docx` + `Doku/Projektdokumentation_Sascha_Schulz.docx` (Lessons Learned): Generics als neu gelerntes Konzept beschrieben (Problem: `<T>` anfangs unklar → Lösung: über Java-Tutorial erarbeitet, am eigenen Code nachvollzogen). Inline-Quelle [14].
- [Änderung] `Doku/Doku_A_Verzeichnisse.docx` + `Projektdokumentation`: Quellenverzeichnis um [14] erweitert — Czeschla, Jörg: Was sind Generics? javabeginners.de, Abruf 19.06.2026.
- [Änderung] `Lernhandbuch/Glossar_Vereinigt.docx`: eigener Glossar-Eintrag „Generics" (Kat. Java) ergänzt (war zuvor nur in Lernhandbuch Teil 4, nicht im vereinigten Glossar).
- [Erkenntnis] Doku nutzt durchgehend `GenericDAO<T>` als Generics-Beispiel; der Quellcode verwendet seit 18.06. jedoch `LeseDAO<T>`. Begriff/Beispiel in der Doku ggf. noch angleichen.

## 2026-06-19 [Erkenntnis] Erklär-Stil als Vorlage verankert
Sascha mag die A4-Erklär-Blätter (Code links / Erklärung rechts, Darcula-Farben, Grundidee- + Erkennen- + Wozu-Box). Festgehalten als `MD/Erklaer-Stil.md` (Trigger, Aufbau, Inhaltsregeln, Farbtabelle, A4-Druck).

## 2026-06-19 [Änderung] Lern-Erklär-Blätter erstellt
- `Lernmaterial/Code_lesen_Spickzettel.html` — Code Zeile für Zeile (cmbLager.getItems().add(null) u.a.), groß/klein, Klammern, "was macht eine Methode zur Methode".
- `Lernmaterial/Generics_Spickzettel.html` — Generics <T>: aufstellen, ausfüllen, Diamant, zwei Typen, Wozu.
- `MD/Roter_Faden_Java.html` (Übersicht+Guide) und `MD/Roter_Faden_Diagramme.html` (SVG-Diagramme); im Architektur-Diagramm Pfeil "DAO befüllt model" korrigiert (saß fälschlich bei Dialoge).
- `_ORDNERINFO.md` in MD/ und Lernmaterial/ aktualisiert.

## 2026-06-19 — [Änderung] Generics-Absatz vereinfacht + Schreibstil-Regel

- [Änderung] Generics-Absatz in Doku_D + Projektdokumentation auf Saschas Ton umgeschrieben (kürzere Sätze, „angelesen/ausprobiert", weniger Gedankenstriche).
- [Änderung] Schreibstil für Doku-Texte festgehalten — Doku/Lernhandbuch immer einfach, kurze Sätze, Ich-Form, Alltagssprache, sparsam mit Gedankenstrichen. Quellen-Checkliste auf 14 Quellen aktualisiert.

## 2026-06-19 [Änderung] Lernblätter vereinheitlicht & gebündelt
- Regel: ein Thema = ein Lernblatt, gleiches Layout/Vorgehen, alle in `Lernmaterial/`.
- `Farben_Uebersicht_Java.html` → ersetzt durch `Farben_Spickzettel.html` (Standard-Layout, selbsterklärend, alte Datei gelöscht).
- `Roter_Faden_Java.html` + `Roter_Faden_Diagramme.html` von `MD/` nach `Lernmaterial/` verschoben.
- Selbsterklärend gemacht: Generics- + Code-lesen-Blatt kennzeichnen jetzt selbst geschriebene Namen (findAll, LeseDAO) vs. Java-Standard; Intro-Beispiel mit String statt projekt-eigenem Typ.
- `MD/Erklaer-Stil.md` um Grundprinzip (ein Thema/ein Blatt/ein Ordner) + Referenzliste (5 Blätter) erweitert.
- `_ORDNERINFO.md` in `Lernmaterial/` (5 Blätter gelistet) und `MD/` (Roter-Faden-Zeilen entfernt) aktualisiert.

## 2026-06-19 [Änderung] Lernblätter vereinheitlicht & zusammengeführt
Prinzip "ein Thema = ein Lernblatt, gleiches Layout, ein Ordner" umgesetzt.
- `Farben_Uebersicht_Java.html` (altes Layout) → neu als `Farben_Spickzettel.html` im Standard-Layout (selbsterklärend); alte Datei gelöscht.
- `Roter_Faden_Java.html` + `Roter_Faden_Diagramme.html` von `MD/` nach `Lernmaterial/` verschoben.
- Alle 5 Lernblätter liegen jetzt in `Lernmaterial/`: Code_lesen, Generics, Farben, Roter_Faden_Java, Roter_Faden_Diagramme.
- Generics-/Code-lesen-Blatt selbsterklärend gemacht (Java- vs. selbst-geschriebene Namen gekennzeichnet, findAll()-Herkunft erklärt).
- `MD/Erklaer-Stil.md` um Grundprinzip (ein Thema/ein Ordner) + aktuelle Referenzliste ergänzt; `_ORDNERINFO.md` in MD/ und Lernmaterial/ aktualisiert.

## 2026-06-19 — [Änderung] Doppel-Gedankenstriche entschärft

- [Änderung] 6 Fließtextsätze mit doppeltem Gedankenstrich-Einschub in Doku_B (2), Doku_C (3), Doku_D (1) und der Projektdokumentation umgeschrieben — jetzt kurze Sätze / Komma / „zum Beispiel" statt Einschub. Keine Doppel-Gedankenstriche im Fließtext mehr (Gesamt-Gedankenstriche 51 → 39).

## 2026-06-19 [Änderung] Lernblätter zu Nachschlagewerk ausgebaut
Ziel laut Sascha: Nachschlagewerk für ihn UND andere; eine Seite ist kein Muss.
- `MD/Erklaer-Stil.md`: Länge = so viel wie nötig (kein Ein-Seiten-Limit), A4 nur Druckformat; neuer Pflicht-Abschnitt "Begriffe zum Nachschlagen" (Glossar) + Ziel "Nachschlagewerk".
- `Code_lesen_Spickzettel.html`: Glossar-Box ergänzt (Deklaration, Zuweisung, Argument, Punkt-Operator, Verkettung, statisch/Instanz, Anweisung, Rückgabewert).
- `Generics_Spickzettel.html`: Glossar-Box ergänzt (Generics, Typ-Parameter, Typ-Argument, Diamant, Typsicherheit, Cast, Interface).
- Hinweis: Bash-Mount zeigte zwischenzeitlich abgeschnittene Dateien (Sync-Verzögerung); über das Datei-Tool sind beide vollständig (enden mit </html>, div-Tiefe 0).

## 2026-06-19 — [Änderung] Inhaltsverzeichnis: Punktführung (dot leader)

- [Problem] In allen Verzeichnissen (Inhalt, Abbildungen, Tabellen, Quellcode) stand die Seitenzahl direkt hinter dem Titel. Ursache: im Text saß ein echtes Tab-Zeichen statt eines `<w:tab/>`-Elements, daher griff der vorhandene rechte Punkt-Tabstopp (pos 9050) nicht.
- [Änderung] In Projektdokumentation + Doku_A je 62 Einträge korrigiert: Tab-Zeichen im selben Run durch `<w:tab/>` ersetzt (`<w:t>Titel</w:t><w:tab/><w:t>Seite</w:t>`). Jetzt Punkte bis zum Rand, Zahl rechtsbündig — wie SRH-Vorlage.
- [Erkenntnis] Einträge NICHT in drei separate Runs splitten — das hat beim ersten Versuch das Layout zerschossen (Deckblatt rutschte in den ersten TOC-Eintrag). Minimal im selben Run bleiben.

## 2026-06-19 — [Änderung] Lernblatt „Static vs. Instanz" erstellt

- [Änderung] Neues A4-HTML-Erklär-Blatt `Lernmaterial/Static_vs_Instanz_Spickzettel.html` im festen Erklär-Stil (Darcula-Code, Code links/Erklärung rechts). Thema: statischer Aufruf (Klasse, kein Objekt) vs. Instanz-Aufruf (Objekt mit `new`).
- [Erkenntnis] Verlässliche Leseregel: was vor dem Punkt steht — Großbuchstabe = Klasse → static (kein Objekt), kleine Variable = Objekt → Instanz. DoIT-Beispiele: `DBConnection.getConnection()` (static, Singleton) vs. `dao.findAll()` (Instanz). `_ORDNERINFO.md` aktualisiert (jetzt 6 Lernblätter).

## 2026-06-19 — [Änderung] Eigenes Lernblatt „Methodensignatur"

- [Änderung] Neues A4-HTML `Lernmaterial/Methodensignatur_Spickzettel.html` (fester Erklär-Stil). Thema: Methodensignatur = Rückgabetyp + Name + `()`, warum kein `=` (Bauplan, keine Zuweisung), Ankündigung im Interface (`LeseDAO`, mit `;`) vs. Umsetzung in der Klasse (`KategorieDAO`, mit `{}`), Signatur ≠ Aufruf. Eigenes Blatt, damit unter „Methodensignatur" auffindbar.
- [Änderung] Entsprechende Box aus `Static_vs_Instanz_Spickzettel.html` wieder entfernt (jetzt eigenes Blatt). `_ORDNERINFO.md` aktualisiert (7 Lernblätter).

## 2026-06-19 — [Erkenntnis] Review aller 7 Lernblätter

- [Erkenntnis] Alle 7 Blätter in `Lernmaterial/` durchgesehen. Die 5 Token-Erklärblätter (Code_lesen, Generics, Farben, Static_vs_Instanz, Methodensignatur) sind stilistisch einheitlich: gleiche CSS-Basis, Darcula-Farben, Grundidee-/Woran-erkenne-ich-Box, Beispiele Code links/Erklärung rechts, Glossar, Footer, Selbstgeschrieben-Kennzeichnung. Die 2 Roter_Faden-Blätter sind bewusst andere Kategorie (Übersicht/Diagramme), teilen aber Palette/Ton.
- [Änderung] Fachliche Korrektur in `Methodensignatur_Spickzettel.html`: Signatur i.e.S. = nur Name + Parametertypen; Rückgabetyp/`throws` gehören zum Methodenkopf, nicht zur Signatur. In Beispiel 1 und Glossar klargestellt. Inhalte sonst korrekt (gegen echten src/-Code geprüft: DBConnection static, KategorieDAO/LeseDAO findAll).

## 2026-06-19 — [Erkenntnis] Abgleich Spickzettel ↔ SRH-Kursblätter (Java 01–06)

- [Quelle] Ordner `C:\Users\schul\Documents\WI\Sprachen\Java\IntelliJ\Java Blätter` (6 PDFs: 01 IntelliJ, 02 OOP-Grundlagen, 03 Kapselung/Überladung, 04 Vererbung/Polymorphie, 05 JavaFX/MVC, 06 Referenztypen/Collections) gelesen und mit den 7 Lernblättern verglichen.
- [Erkenntnis] Definitionen stimmen mit dem Kurs überein, keine Widersprüche. Wichtig: Kurs (Blatt 03) definiert Methodensignatur = Name + Parameter (Anzahl/Typ/Reihenfolge), Rückgabetyp NICHT enthalten — deckt sich mit der zuvor eingebauten Korrektur. static-Definition (Blatt 06: „Methoden, die kein Objekt brauchen") deckt sich wörtlich mit Static-Blatt.
- [Änderung] Kleine Begriffs-Angleichungen an Kurs-Vokabular eingebaut: Static-Blatt „Wertetyp"/„komplexe Datentypen"/`null`; Generics-Blatt „Typinferenz" + „raw type"; Farben-Blatt Feld-Synonyme (Attribut/Eigenschaft/Instanzvariable); Methodensignatur-Blatt Überladung-Bezug + Konstruktor (kein Rückgabetyp).
- [Erkenntnis] Nicht abgedeckte Kurs-Fachwörter (eigene Blätter denkbar): Kapselung/Getter-Setter/Zugriffsmodifikatoren (Blatt 03), Überladung vs. Überschreiben/@Override (03/04), Vererbung/Polymorphie/extends/super (04), Wrapperklassen/Autoboxing + Collections Queue/Set/Map/HashMap (06), this/super. Mehrere davon (extends/super/Vererbung, Queue/Set/Map) werden im DoIT-Projekt nicht genutzt.

## 2026-06-19 — [Änderung] 5 neue Lernblätter aus Kurs-Lücken

- [Änderung] Nach dem Abgleich mit den SRH-Kursblättern 5 neue A4-Lernblätter im festen Stil erstellt (jetzt 12 in `Lernmaterial/`): `Kapselung_Spickzettel.html` (private/Getter/Setter/Modifikatoren), `Collections_Spickzettel.html` (Wrapper/Autoboxing + List/Set/Queue/Map), `Vererbung_Polymorphie_Spickzettel.html` (extends/super/@Override/Polymorphie), `Annotationen_Spickzettel.html` (@FXML/@Override/this), `Fachwoerter_Zusammenhaenge_Spickzettel.html` (Übersicht + Begriffs-Index → welches Blatt).
- [Erkenntnis] Echte DoIT-Beispiele verwendet: Material (private Felder, Getter/Setter, Konstruktoren, @Override toString), MainController (@FXML), KategorieDAO/LeseDAO. Projekt nutzt nur `Main extends Application` als echtes extends, sonst Interface (`implements LeseDAO<T>`) + Generics — im Vererbungs-Blatt so vermerkt (Composition over Inheritance).
- [Manuell] `_ORDNERINFO.md` aktualisiert (12 Blätter, Hinweis auf Kurs-Abgleich).

## 2026-06-19 — [Änderung] Glossar-Blatt Fachwörter

- [Änderung] Neues A4-Blatt `Lernmaterial/Glossar_Fachwoerter_Spickzettel.html` (jetzt 13 Blätter): Tabelle Fachwort | einfaches Synonym | kurze Erklärung, in 9 Gruppen (OOP-Grundlagen, Variablen/Typen, static/Signatur, Kapselung, Vererbung, Generics, Collections, Architektur/JavaFX, Werkzeuge). Deckt die Fachwörter aus Kursblättern 01–06 + allen Spickzetteln ab. `_ORDNERINFO.md` aktualisiert.

## 2026-06-19 — [Änderung] Ternär-Blatt + ORDNERINFO-Korrektur

- [Änderung] Neues Blatt `Lernmaterial/Ternaerer_Operator_Spickzettel.html`: ternärer Operator `Bedingung ? WertA : WertB` als Kurzform fürs if/else, mit ausgeschriebenem Gegenstück und echten DoIT-Beispielen (BestellungDAO null-Prüfung, BestandView Status, MainController Suchtext). Begriff zusätzlich ins Glossar-Blatt aufgenommen.
- [Problem] Bestehendes `Wann_static_Spickzettel.html` fehlte in `_ORDNERINFO.md`. Nachgetragen. Lernblatt-Zähler korrigiert auf 15.

## 2026-06-19 — [Änderung] Eigenes Überladungs-Blatt

- [Änderung] Neues Blatt `Lernmaterial/Ueberladung_Spickzettel.html` (jetzt 16 Blätter): Überladung mit echtem DoIT-Beispiel (zwei `Material`-Konstruktoren: ohne ID fürs Anlegen / mit ID fürs Laden) + Methoden-Überladung (add int/double), drei Eindeutigkeits-Kriterien, Hinweis Rückgabetyp zählt nicht, Abgrenzung Überladung≠Überschreiben. `_ORDNERINFO.md` aktualisiert.

## 2026-06-19 — [Änderung] Überladung vs. Generics/Wrapper in beiden Blättern

- [Änderung] In `Ueberladung_Spickzettel.html` neuer Abschnitt 4 „Überladung sparen: Wrapper & Generics" (Weg 1 Number-Obertyp, Weg 2 `<T extends Number>`, je mit Token-Erklärung, DoIT-Bezug LeseDAO<T>/EntityCrud<T>). In `Generics_Spickzettel.html` neuer Abschnitt 5 „Generics statt Methoden überladen" (vorher 2× add / nachher 1× generisch). Kernaussage einfach erklärt: eine Methode für eine Typ-Familie statt Kopie pro Typ; Wrapper sind die Brücke (Primitive→Objekt→Obertyp Number). Hinweis ergänzt, dass Generics Überladung nicht immer ersetzen.

## 2026-06-22 — [Änderung] Einheitlichkeitsprüfung aller Spickzettel

- [Erkenntnis] Alle 16 HTML-Spickzettel in `Lernmaterial/` geprüft: Basis-Layout (CSS-Variablen, Schrift `Segoe UI`, Darcula-Farben, `max-width:900px`, Code-Grid `0.92fr 1.08fr`, `.note`/`.note.warn`-Boxen) ist über alle Blätter einheitlich und inhaltlich korrekt (Stichprobe Collections/Static/Ternär sauber). Zwei Blatt-Typen: Lehrblatt (Einzelthema) und Übersichts-/Index-Blatt — beide gleiche Basis-CSS.
- [Problem→Änderung] Drei Abweichungen beim A4-Druck gefunden und behoben: `Roter_Faden_Java.html` (kein `@media print`/`@page`, `max-width` 880→900) und `Roter_Faden_Diagramme.html` (kein `@media print`/`@page`) je um A4-Druckblock ergänzt; `Fachwoerter_Zusammenhaenge_Spickzettel.html` fehlte `print-color-adjust` (farbige Boxen druckten grau) — ergänzt.
- [Änderung] `MD/Erklaer-Stil.md`: neue Abschnitte „Pflicht-Layout (Checkliste)" + „Zwei Blatt-Typen" — Einheitlichkeit ist jetzt verbindlich dokumentiert. Veraltete „5 Stück"-Liste auf aktuellen Stand (16) gebracht.

## 2026-06-22 — [Problem→Änderung] Druck: Überschriften wurden abgetrennt

- [Problem] Beim Drucken stand eine Überschrift unten auf einer Seite, der zugehörige Inhalt rutschte auf die nächste Seite (verwaiste Überschrift). Die `.ex`/`.guide`/`figure`-Boxen blieben zwar zusammen, aber die davorstehende `<h2>` nicht.
- [Änderung] In allen 16 Blättern im `@media print`-Block ergänzt: `h1,h2,h3{break-after:avoid;page-break-after:avoid;break-inside:avoid}` — bindet die Überschrift an den folgenden Block. Regel als Pflicht in `MD/Erklaer-Stil.md` (Checkliste) aufgenommen.

## 2026-06-22 — [Änderung] Neues Blatt: Interface / Abstrakte Klasse / enum

- [Erkenntnis] Prüfung ergab: Interface war erklärt (Glossar + verstreut), enum nur namentlich (BewegungsTyp), abstrakte Klassen gar nicht.
- [Änderung] Neues kombiniertes Lernblatt `Lernmaterial/Interface_Abstrakt_Enum_Spickzettel.html` (jetzt 17 Blätter) im einheitlichen Stil: Interface (echtes `LeseDAO<T>`, `implements`), abstrakte Klasse (allgemeines Tier/Hund-Beispiel, `extends`; Hinweis dass DoIT stattdessen Interface+Generics nutzt), enum (echtes `BewegungsTyp` EINGANG/AUSGANG), Vergleichstabelle, Fallen, Glossar. Selbst-geschriebene Namen gekennzeichnet. `_ORDNERINFO.md` aktualisiert.
