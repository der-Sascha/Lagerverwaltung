# Vollständige Projektanalyse — DoIT Lagerverwaltung
**Erstellt:** 2026-06-07  
**Zweck:** Detaillierte Übergabe-Zusammenfassung für nächstes Modell / nächste Session  
**Autor der Analyse:** Claude (Cowork-Modus)  

---

## 1. PROJEKTÜBERSICHT

### Person & Projekt
- **Sascha Schulz**, Gruppe 2551, SRH Berufliche Rehabilitation GmbH
- **Betreuer:** Frau Cramer
- **Projekttitel:** Entwicklung einer Software zur Lagerverwaltung von medizinischem Verbrauchsmaterial im Krankenhaus
- **Stack:** Java 17 + JavaFX + MySQL 8, MVC + DAO-Pattern, lokal (kein Internet)
- **Zeitraum:** 04.05.2026 – 26.06.2026 (6 Wochen, 2 Blöcke)
- **Abgabe Dokumentation:** 17.07.2026 als PDF in Moodle
- **Bewertung:** Dokumentation 30 % | Präsentation 20 % | Fachgespräch 50 %

### Heutiger Stand (07.06.2026)
- **Block 2 startet morgen (KW24, 08.06.2026)**
- Phase 1–3 vollständig abgeschlossen
- Phase 4 (UI) bereits vorgearbeitet: MainController.java = 703 Zeilen, vollständiges CRUD
- Gesamter Code: ~1.700 Zeilen

---

## 2. DATEISTRUKTUR (IST-ZUSTAND)

### Code-Dateien (C:\Users\schul\IdeaProjects\lagerverwaltung\src\main\java\de\doit\)

| Datei | Zeilen | Status |
|---|---|---|
| `controller/MainController.java` | 703 | ✅ Vollständig CRUD alle 6 Entitäten + Filter + Umlagerung |
| `db/DBConnection.java` | ~37 | ✅ Singleton, Lazy Init, URL korrigiert auf `krankenhaus_lager` |
| `model/Material.java` | 43 | ✅ POJO |
| `model/Kategorie.java` | 30 | ✅ POJO |
| `model/Stationslager.java` | 38 | ✅ POJO |
| `model/Lieferant.java` | 43 | ✅ POJO |
| `model/Bestellung.java` | 62 | ✅ POJO |
| `model/Bestandsbewegung.java` | 65 | ✅ POJO |
| `model/BewegungsTyp.java` | 6 | ✅ ENUM: EINGANG, AUSGANG |
| `model/BestandView.java` | 37 | ✅ Berechnete Sicht |
| `dao/MaterialDAO.java` | 108 | ✅ CRUD + findAll |
| `dao/KategorieDAO.java` | 92 | ✅ CRUD |
| `dao/StationslagerDAO.java` | 90 | ✅ CRUD |
| `dao/LieferantDAO.java` | 93 | ✅ CRUD |
| `dao/BestellungDAO.java` | 118 | ✅ CRUD |
| `dao/BestandsbewegungDAO.java` | 160 | ✅ CRUD + findBestandViews + getBestand |
| `Main.java` | ~24 | ✅ JavaFX Application |
| `Launcher.java` | ~10 | ✅ Einstiegspunkt |
| `resources/fxml/main.fxml` | — | ✅ FXML-Layout |
| **TOTAL** | **~1.700** | |

### Datenbank (testdaten_krankenhaus_lager.sql)
- 6 Tabellen: kategorien, stationslager, lieferanten, materialien, bestellungen, bestandsbewegungen
- 20 Materialien, 5 Lager, 4 Lieferanten, 6 Kategorien
- 26 Bestandsbewegungen, 4 offene Bestellungen
- 6 Warnungs-Datensätze (Bestand ≤ Mindestbestand): Pflaster-Set, Desinfektionsmittel, OP-Handschuhe M, Nahtmaterial Vicryl, Blutzucker-Teststreifen, Einmalhandschuhe S

### Dokumentations-Dateien (C:\Users\schul\Documents\WI\DOIT\)

| Datei | Inhalt | Status |
|---|---|---|
| `Doku_A_Verzeichnisse.docx` | Deckblatt, Inhaltsverzeichnis, Abbildungsverz., Tabellenverz., Quellenverz. | Rohversion |
| `Doku_B_Ausgangssituation.docx` | Kap. 1 (Ausgangssituation) + Kap. 2 (Ressourcen & Planung) | Rohversion |
| `Doku_C_Durchfuehrung.docx` | Kap. 3 (Durchführung) Phase 1–4 | Mit Fehler, unvollständig |
| `Doku_D_Ergebnisse.docx` | Kap. 4 (Ergebnisse) + Kap. 5 (Reflexion) | Platzhalter, noch leer |
| `Doku_E_Anhang.docx` | Anhang A–E (ER-Diagramm, SQL, Benutzerhandbuch, Wochenplan, Antrag) | Teilweise Platzhalter |
| `Projektdokumentation_Sascha_Schulz.docx` | Haupt-Abgabedokument (zusammengeführt) | Veraltet, Fehler |
| `Benutzerhandbuch_Sascha_Schulz.docx` | Vollständiges Benutzerhandbuch | ✅ Vorhanden, separat |
| `Glossar_Java_API.docx` | 48 Java-API-Begriffe | ✅ Vorhanden |
| `GLOSSAR.md` | Java/JDBC Lernbegriffe | Lernmaterial |
| `DOIT Krankenhaus.drawio` | ER-Diagramm (Quelle) | ✅ Standard |
| `testdaten_krankenhaus_lager.sql` | DB-Schema + Testdaten | ✅ Aktuell |
| `Projekttagebuch.md` | Laufendes Log aller Probleme/Entscheidungen | ✅ Detailliert |

---

## 3. KRITISCHE DIFFERENZEN (FEHLER & INKONSISTENZEN)

### 3.1 PHASENZÄHLUNG — GRAVIERENDER FEHLER

| Dokument | Phasen | Stunden |
|---|---|---|
| `Projektplan.md` (KORREKT) | **7 Phasen** | 20+20+20+40+25+25+30=180h |
| `Doku_B_Ausgangssituation.docx` (Kap. 2.1) | **7 Phasen** ✅ | Korrekt |
| `Doku_C_Durchfuehrung.docx` (Einleitungssatz) | **„5 Phasen"** ❌ | — |
| `Doku_C` (Kap. 3.4 Überschrift) | Phase 4 = **65 Stunden** ❌ | Soll: 40h |
| `Projektdokumentation_Sascha_Schulz.docx` (Kap. 2.1) | **5 Phasen** ❌ | Phase 4=65h, Phase 5=55h |

**Korrektur nötig:** Hauptdoku und Doku_C müssen auf 7 Phasen korrigiert werden. Phase 4=40h (Grundfunktionen), Phase 5=25h (Erweiterte Funktionen), Phase 6=25h (Tests), Phase 7=30h (Doku).

### 3.2 FALSCHER KLASSENNAME in Doku_C (Kap. 3.3.1 MVC-Tabelle)

| Falsch (in Doku) | Richtig (in Code) |
|---|---|
| `StationslagDAO` | `StationslagerDAO` |
| `BewegungDAO` | `BestandsbewegungDAO` |

**Bereits im Tagebuch (02.06.) als Korrektur vermerkt.** Die Hauptdoku und Doku_C müssen noch angepasst werden.

### 3.3 FALSCHE METHODE in Doku_C (Kap. 3.3.2)

- `getAll()` → muss `findAll()` heißen (tatsächliche Methode laut Code)

### 3.4 KAPITELNAME INKONSISTENT

- `Doku_D` / `Doku_A` (TOC): Kapitel 5 heißt **„Reflexion & Bewertung"**
- `Projektdokumentation_Sascha_Schulz.docx`: Kapitel 5 heißt **„Gestaltung des Portfolios"** ❌

### 3.5 KAPITELSTRUKTUR UNTERSCHIED (Doku_A vs. Hauptdoku)

`Doku_A` (korrekte Struktur mit 5 Unterpunkten in Kap. 2):
- 2.1 Zeitplanung, 2.2 Wochenübersicht, **2.3 Personalplanung**, 2.4 Technische Ressourcen, **2.5 Kostenplanung**, 2.6 Risiken

`Hauptdoku` (abweichende Struktur):
- 2.1 Zeitplanung, 2.2 Wochenübersicht, 2.3 Technische Ressourcen (**kein 2.3 Personalplanung, kein 2.5 Kostenplanung**)

**Hauptdoku fehlen:** Personalplanung (Kap. 2.3) und Kostenplanung fiktiv (Kap. 2.5).

### 3.6 BENUTZERHANDBUCH FALSCH PLATZIERT

- `Benutzerhandbuch_Sascha_Schulz.docx` existiert als **separate Datei** — das ist laut Vorgaben **FALSCH**
- Laut Dokumentationsvorgaben: Benutzerhandbuch gehört als **Anhang in die Projektdokumentation** (Anhang C)
- `Doku_E_Anhang.docx` hat nur eine kurze Zusammenfassung (nicht vollständiges Handbuch)
- **Fix:** Vollständiges Benutzerhandbuch muss in Anhang C von Doku_E integriert werden

### 3.7 PLATZHALTER IN Doku_D (NOCH LEER)

| Abschnitt | Problem |
|---|---|
| Kap. 4.1 Soll-Ist-Vergleich | Alle `[Status]` Felder nicht ausgefüllt |
| Kap. 4.2 Testergebnisse | `[Gesamtergebnis eintragen]` — leer |
| Kap. 4.3.4 Abnahmeprotokoll | `[Datum eintragen]`, `[Abgenommen/...]` — leer |
| Kap. 5.1 Reflexion | Nur Leitfragen als Platzhalter, kein Fließtext |
| Kap. 5.2 Lernzuwachs | Nur Kategorien-Liste, kein Text |
| Kap. 5.3 Verbesserungspotenzial | Nur Stichpunkte, kein Fließtext |

### 3.8 QUELLENVERZEICHNIS UNVOLLSTÄNDIG

`Doku_A` hat 5 Quellen (Oracle Java SE 17, Oracle JavaFX 17, MySQL 8.0, SRH DOIT-Leitfaden, GoF Design Patterns). **Fehlende Quellen für JavaFX-Features und Maven:**

| Thema | Benötigte Quelle |
|---|---|
| JavaFX TableView | Oracle/OpenJFX API Doku |
| JavaFX ObservableList / FXCollections | Oracle/OpenJFX API Doku |
| JavaFX FilteredList | Oracle/OpenJFX API Doku |
| JavaFX FXML (SceneBuilder) | Oracle FXML-Tutorial |
| JavaFX Dialog / ButtonType | Oracle/OpenJFX API Doku |
| JavaFX TabPane / Tab | Oracle/OpenJFX API Doku |
| JavaFX RowFactory / TableRow | Oracle/OpenJFX API Doku |
| Maven Build System | Apache Maven Dokumentation |
| MySQL JDBC Connector/J | Oracle/MySQL Dev Doku |
| MVC-Architekturmuster | Microsoft/Oracle/Buch |
| DAO-Muster | Oracle Java EE Patterns |
| Singleton-Muster | GoF (Gamma et al.) — schon in Quelle [5] |

### 3.9 WOCHENPLAN-TABELLE in Doku_B ABGESCHNITTEN

- In Doku_B Kap. 2.2: Woche 5 hat keinen Stunden-Wert (Ausgabe bricht ab bei Woche 5)

### 3.10 DREI BEREITS BEHOBENE CODE-BUGS (für Doku relevant)

Aus Projekttagebuch 02.06.2026 — bereits im Code korrigiert, müssen aber in Dokumentation erwähnt werden:

1. **BUG #1**: `BestandsbewegungDAO.java` — PK-Name `bestandsbewegung_id` → `bewegung_id` (5 Stellen)
2. **BUG #2**: `DBConnection.java` — DB-Name `DOIT` → `krankenhaus_lager`
3. **BUG #3**: `BewegungsTyp.java` — ENUM hatte `TRANSFER` (nicht im SQL-Schema) → entfernt

---

## 4. WAS SASCHA DURCH FRAU CRAMER GELERNT HAT (Dino-Park-Beispiel)

Frau Cramers Lehrinhalt umfasst genau:
- `DBConnection.java` — Singleton, einmalige Verbindung
- `Model.java` — POJO, spiegelt Tabelle, zwei Konstruktoren
- `ModelDAO.java` — CRUD mit PreparedStatement
- `Main.java` — Demo-Einstiegspunkt

**Alles andere im Projekt kam aus Eigenrecherche/Lernhandbuch:**

| Thema | Herkunft |
|---|---|
| JavaFX (TableView, ObservableList, FilteredList, Dialog, TabPane, FXML) | Eigenrecherche |
| PropertyValueFactory | Eigenrecherche |
| @FXML Annotation | Eigenrecherche |
| Maven + pom.xml | Eigenrecherche (Lernhandbuch Kap. 2–3, 12) |
| SQL JOIN mit SUM/CASE WHEN Aggregation | Eigenrecherche |
| Umlagerung (Doppel-Buchung) | Eigenrecherche |
| RowFactory für Zeilenfärbung | Eigenrecherche |
| JavaFX DatePicker, ComboBox | Eigenrecherche |
| SceneBuilder / FXML-Design | Eigenrecherche |
| TableRow Styling | Eigenrecherche |

→ **Für Reflexion/Lernzuwachs** und **Quellenverzeichnis** wichtig: Alle JavaFX-Themen benötigen Internet-Quellen.

---

## 5. INHALT DER LERNMATERIALIEN

### Lernhandbuch (14 Kapitel, vollständig):
1. IntelliJ IDEA als Werkzeug verstehen
2. Was IntelliJ automatisch macht
3. Projekt anlegen, Maven-Setup, Verzeichnisstruktur
4. Die Datenbank anlegen — SQL-Skript
5. DBConnection.java — Datenbankverbindung
6. Die sechs Model-Klassen + BestandView
7. Die sechs DAO-Klassen mit JOIN-Abfrage
8. Launcher.java — main()-Methode
9. Main.java — start()-Methode und Application-Setup
10. MainController.java — Brücke zwischen Oberfläche und Daten
11. main.fxml — Layout der Oberfläche
12. pom.xml — Maven-Konfiguration
13. Datenfluss — was passiert beim Klick auf „Suchen"
14. Häufigste Fehlermeldungen + Ursachen

→ Das Lernhandbuch deckt **alle** im Projekt verwendeten Technologien ab. Es kann als Nachweis dienen, dass Sascha sich diese Themen selbstständig erarbeitet hat.

---

## 6. ABSCHLUSS-CHECKLISTE (CLAUDE.md Stand)

| Vorgabe | Aktueller Status |
|---|---|
| Deckblatt vorhanden | ✅ in Doku_A |
| Inhaltsverzeichnis vorhanden | ✅ in Doku_A (manuell, keine Seitenzahlen) |
| Abbildungsverzeichnis vorhanden | ⚠️ in Doku_A vorhanden, aber Seitenzahlen = „–" |
| Tabellenverzeichnis vorhanden | ⚠️ in Doku_A vorhanden, aber Seitenzahlen = „–" |
| Quellenverzeichnis vorhanden | ⚠️ 5 Quellen in Doku_A — aber unvollständig (JavaFX fehlt) |
| Benutzerhandbuch im Anhang (nicht separat) | ❌ Separat als .docx — muss in Anhang C |
| Seitenanzahl 10–15 (exkl. Deckblatt/Verz./Anhang) | ⏳ Zu prüfen bei Zusammenführung |
| Zeilenabstand 1,5 | ⏳ Nicht geprüft |
| Schriftgröße 12 | ⏳ Nicht geprüft |
| Schriftart Arial/Aptos/TNR | ⏳ Nicht geprüft |
| Seitenränder korrekt (oben/li/re 2,5 cm, unten 2 cm) | ⏳ Nicht geprüft |
| Als PDF in Moodle hochgeladen bis 17.07.2026 | ❌ Noch nicht |

---

## 7. BEISPIEL-DOKUMENTATIONEN (Vorgaben-Ordner)

Zwei Beispiele vorhanden:
- `09_TN_Dokumentation_Gerda_Feldhaus_Beispiel.pdf` — Fachinformatikerin, professionelle Struktur
- `09_TN_Dokumentation_Markus_Amshove_Beispiel.pdf` — zweites Beispiel

**Gerda Feldhaus Struktur** (als Orientierung):
1. Einleitung (Projektbeschreibung, Ziel, Umfeld, Begründung, Schnittstellen, Abgrenzung)
2. Projektplanung (Phasen, Ressourcen, Entwicklungsprozess)
3. Analysephase (Ist-Analyse, Wirtschaftlichkeit, Use Cases, Lastenheft)
4. Entwurfsphase (Zielplattform, Architektur, UI-Entwurf, Datenmodell)
5. Implementierungsphase (Iterationen, Datenstrukturen, Geschäftslogik, UI)
6. Abnahme & Einführungsphase
7. Dokumentation
8. Fazit (Soll-Ist, Lessons Learned, Ausblick)
+ Anhang A–H

**Stil:** Sachlich, Fließtext, wenige Listen, Codebeispiele mit Erklärung, Abbildungen nummeriert.

---

## 8. VOLLSTÄNDIGER AUFGABENPLAN FÜR FERTIGSTELLUNG

### PHASE A: Korrekturen (vor Zusammenführung)

**A1 — Doku_C korrigieren:**
- Einleitungssatz: „5 Phasen" → „7 Phasen"
- Kap. 3.3.1 Tabelle: `StationslagDAO` → `StationslagerDAO`, `BewegungDAO` → `BestandsbewegungDAO`
- Kap. 3.3.2: `getAll()` → `findAll()`
- Kap. 3.4 Überschrift: „65 Stunden" → „40 Stunden"
- Kap. 3.4 aufteilen: Phase 4 (40h Grundfunktionen), Phase 5 (25h Erweiterte Funktionen), Phase 6 (25h Tests), Phase 7 (30h Doku)
- Neue Sektion 3.6 „Herausforderungen & Lösungen": Bugs aus Tagebuch 02.06. einarbeiten

**A2 — Hauptdoku korrigieren:**
- Kap. 2.1: 5 Phasen-Tabelle → 7 Phasen (wie in Doku_B)
- Kap. 2.3 Personalplanung ergänzen
- Kap. 2.5 Kostenplanung fiktiv ergänzen
- Kap. 5 Überschrift: „Gestaltung des Portfolios" → „Reflexion & Bewertung"

**A3 — Doku_B ergänzen:**
- Kap. 2.2 Wochenübersicht: Stunden für Wochen 5+6 ergänzen

### PHASE B: Inhalt füllen

**B1 — Doku_D ausfüllen:**
- Kap. 4.1 Soll-Ist: Alle 6 Anforderungen auf „Erfüllt" setzen (laut Code-Status)
- Kap. 4.2 Testergebnisse: Testfälle dokumentieren (min. 8 Testfälle)
  - Material anlegen ✅
  - Material bearbeiten ✅
  - Material löschen ✅
  - Suche nach Materialname ✅
  - Filter nach Station ✅
  - Mindestbestand-Warnung (Rot) ✅
  - Bestandsbuchung EINGANG ✅
  - Bestandsbuchung AUSGANG ✅
  - Umlagerung zwischen Lagern ✅
  - Bestellung anlegen + Statuswechsel ✅
- Kap. 4.3.4 Abnahmeprotokoll: Datum + Ergebnis (nach KW26 zu befüllen)
- Kap. 5.1 Reflexion: Min. 1 Seite Fließtext schreiben (aus Tagebuch ableiten)
- Kap. 5.2 Lernzuwachs: Fließtext über JavaFX, JDBC, SQL, MVC, Projektmanagement
- Kap. 5.3 Verbesserungspotenzial: Fließtext

**B2 — Doku_E (Anhang C) ausfüllen:**
- Vollständiges Benutzerhandbuch aus `Benutzerhandbuch_Sascha_Schulz.docx` integrieren
- Anhang A: ER-Diagramm-Bild aus drawio exportieren und einfügen
- Anhang B: SQL-Skript-Auszug korrekt formatieren

**B3 — Quellenverzeichnis erweitern (in Doku_A):**
Folgende Quellen ergänzen:
- [6] Oracle/OpenJFX: JavaFX 17 TableView API — https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/TableView.html (Abruf: [Datum])
- [7] Oracle/OpenJFX: JavaFX 17 ObservableList API — https://openjfx.io/javadoc/17/javafx.base/javafx/collections/ObservableList.html (Abruf: [Datum])
- [8] Oracle/OpenJFX: JavaFX 17 FilteredList API — https://openjfx.io/javadoc/17/javafx.base/javafx/collections/transformation/FilteredList.html (Abruf: [Datum])
- [9] Oracle: Introduction to FXML — https://docs.oracle.com/javase/8/javafx/fxml-tutorial/get_started.htm (Abruf: [Datum])
- [10] Apache Software Foundation: Maven Getting Started Guide — https://maven.apache.org/guides/getting-started/ (Abruf: [Datum])
- [11] Oracle: MySQL Connector/J Developer Guide — https://dev.mysql.com/doc/connector-j/8.0/en/ (Abruf: [Datum])
- [12] Oracle/OpenJFX: JavaFX 17 Dialog API — https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/Dialog.html (Abruf: [Datum])
- [13] SEO-Küche: Was ist localhost? — https://www.seo-kueche.de/lexikon/localhost/ (Abruf: 2026-05-20)
- [14] Oracle/OpenJFX: JavaFX 17 TabPane API — https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/TabPane.html (Abruf: [Datum])
- [15] Oracle Java SE 8 API: java.sql.PreparedStatement — https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html (Abruf: [Datum])

### PHASE C: Glossar

**C1 — Glossar_Java_API.docx** (48 Begriffe) → bleibt als Anhang D  
**C2 — GLOSSAR.md** (ausführliche JDBC-Erklärungen) → Lernmaterial, nicht in Doku  
**C3 — Kein Abkürzungsverzeichnis** im aktuellen Plan — prüfen ob nötig (SRH fordert es nicht explizit)

### PHASE D: Zusammenführung

**D1 — Reihenfolge der Zusammenführung:**
1. Deckblatt (aus Doku_A)
2. Inhaltsverzeichnis (aktualisiert)
3. Kapitel 1 Ausgangssituation (aus Doku_B)
4. Kapitel 2 Ressourcen & Planung (aus Doku_B, korrigiert)
5. Kapitel 3 Durchführung (aus Doku_C, korrigiert)
6. Kapitel 4 Ergebnisse (aus Doku_D, gefüllt)
7. Kapitel 5 Reflexion (aus Doku_D, gefüllt)
8. Abgabeerklärung
9. Abbildungsverzeichnis (aus Doku_A, Seitenzahlen aktualisiert)
10. Tabellenverzeichnis (aus Doku_A, Seitenzahlen aktualisiert)
11. Quellenverzeichnis (aus Doku_A, erweitert)
12. Anhang A: ER-Diagramm
13. Anhang B: SQL-Skript-Auszug
14. Anhang C: Benutzerhandbuch (vollständig)
15. Anhang D: Glossar Java API
16. Anhang E: Wochenplan
17. Anhang F: Projektantrag

**D2 — Format-Prüfung (nach Zusammenführung):**
- Zeilenabstand 1,5 prüfen
- Schriftgröße 12 prüfen
- Schriftart Arial/Aptos/TNR prüfen
- Seitenränder: oben/links/rechts 2,5 cm, unten 2 cm
- Seitenanzahl 10–15 (exkl. Deckblatt, Verzeichnisse, Anhang)
- Abbildungen und Code ≤ 1/3 pro Seite
- Seitenzahlen in Verzeichnissen aktualisieren
- Nummerierungsfehler prüfen

**D3 — PDF erstellen und hochladen:**
- Als PDF speichern (Word → Exportieren → PDF)
- Moodle-Upload bis 17.07.2026

---

## 9. BEKANNTE BUGS AUS PROJEKTTAGEBUCH (Zusammenfassung)

Alle in Code behoben, aber relevant für Dokumentation Kap. 3.6:

| Datum | Bug | Problem | Lösung |
|---|---|---|---|
| 02.06.2026 | BUG #1 BestandsbewegungDAO | PK-Name `bestandsbewegung_id` statt `bewegung_id` → SQLException | Alle 5 Stellen korrigiert |
| 02.06.2026 | BUG #2 DBConnection | DB-Name `DOIT` statt `krankenhaus_lager` → Verbindung fehlgeschlagen | URL korrigiert |
| 02.06.2026 | BUG #3 BewegungsTyp | ENUM hatte `TRANSFER` (nicht im SQL-Schema) → INSERT-Fehler | TRANSFER entfernt |
| 18.05.2026 | Port-Fehler | Falscher Port in JDBC-URL → Communications link failure | Port korrigiert auf 3324 |
| 18.05.2026 | Lernhandbuch S. 38 | Konstruktoren in Lieferant.java mit Stationslager-Parametern | Korrigiert |
| 06.05.2026 | SQL-Fehler | Fehlendes Semikolon in SQL-Script | Ergänzt |
| 31.05.2026 | Backup-Problem | Backup-Restore spielte ältere Versionen ein | Bereinigung durchgeführt |

---

## 10. AKTUELLER CONTENT DER KAPITEL (ZUSAMMENFASSUNG)

### Doku_B / Hauptdoku Kap. 1 (Ausgangssituation) — VOLLSTÄNDIG
- 1.1 Auftraggeber: SRH, Frau Cramer, fiktives Krankenhaus ✅
- 1.2 Ist-Analyse: Manuelle Listen, kein Warnsystem, keine History ✅
- 1.3 Soll-Konzept: JavaFX Desktop-App, MySQL, strukturierte Verwaltung ✅
- 1.4 Anforderungen: 4 Pflicht (DB 5 Tabellen, CRUD, MVC, Suche/Filter) + 4 zusätzlich ✅

### Doku_B Kap. 2 (Ressourcen & Planung) — WEITGEHEND VOLLSTÄNDIG
- 2.1 Zeitplanung 7 Phasen 180h ✅
- 2.2 Wochenübersicht ✅ (Woche 5+6 Stunden fehlen)
- 2.3 Personalplanung ⏳ (in Doku_B vorhanden, in Hauptdoku fehlt)
- 2.4 Technische Ressourcen ✅
- 2.5 Kostenplanung fiktiv ⏳ (fehlt in Hauptdoku)
- 2.6 Risiken und Gegenmaßnahmen ✅

### Doku_C Kap. 3 (Durchführung) — VORHANDEN, MIT FEHLERN
- 3.1 Phase 1 Analyse & Planung ✅ (inkl. DB-Design, 6 Tabellen)
- 3.2 Phase 2 Datenbankimplementierung ✅ (SQL-Beispiel, JDBC-Singleton)
- 3.3 Phase 3 DAO-Schicht ✅ (mit 2 Fehlern: Klassenname + Methodenname)
- 3.4 Phase 4 JavaFX ✅ (mit Fehler: 65h statt 40h, Phasennummer stimmt)
- 3.5 Phase 5 fehlt (Test & Abschluss)
- 3.6 fehlt (Herausforderungen / Bugs)

### Doku_D Kap. 4+5 — PLATZHALTER
- Kap. 4.1 Soll-Ist: Tabelle da, [Status] leer
- Kap. 4.2 Testergebnisse: leer
- Kap. 4.3 Abnahme: Kriterien da, Protokoll leer
- Kap. 4.4 Bekannte Einschränkungen ✅
- Kap. 5.1–5.3 Reflexion: Nur Leitfragen, kein Inhalt

### Doku_E Anhang — TEILWEISE
- Anhang A ER-Diagramm: Tabellen-Übersicht da, Bild fehlt (aus drawio zu exportieren)
- Anhang B SQL-Skript: Verweis auf Datei, Auszug fehlt
- Anhang C Benutzerhandbuch: Nur Zusammenfassung (3 Seiten) — vollständiges Handbuch (9 Seiten) separat
- Anhang D–E: Verweise auf externe Dateien

---

## 11. WICHTIGE KONTEXT-INFORMATIONEN FÜR FOLGE-SESSION

### Was das Lernhandbuch bedeutet für die Doku
Das Lernhandbuch (14 Kapitel) ist **nicht** die Projektdokumentation. Es ist Saschas persönliches Lernmaterial, das zeigt, wie er sich JavaFX/Maven selbst beigebracht hat. Die **Quellenangaben im Lernhandbuch** (IntelliJ-Download-URL, Maven-Guide, etc.) sind gleichzeitig die **Quellen für die Projektdokumentation**.

### Port 3324 vs. 3306
Das Projekt nutzt Port **3324** (nicht Standard-MySQL-Port 3306). Das ist Saschas lokale MySQL-Installation. In Dokumentations-Codebeispielen immer 3324 verwenden.

### Datenbankname: krankenhaus_lager
Der tatsächliche Datenbankname ist `krankenhaus_lager`. Alle älteren Dokumente, die `DOIT` als Datenbanknamen zeigen, sind falsch.

### Bestand-Berechnung ist eine Design-Entscheidung
Es gibt **kein** `bestand`-Feld in `materialien`. Bestand = `SUM(CASE WHEN bewegungstyp='EINGANG' THEN menge ELSE -menge END)`. Das ist **bewusst** so (D-001) und muss in Reflexion/Fachgespräch erklärt werden können.

### Umlagerung-Feature (nicht im ursprünglichen Plan)
Der MainController implementiert `onUmlagerung()` — ein Feature das **nicht im Projektantrag** stand. Es erscheint im Code als Erweiterung. In Dokumentation als „zusätzlich realisiertes Feature" erwähnen (positiv für Bewertung).

### Git-Versionskontrolle
Das Projekt hat ein `.git`-Repository. Commit-Historie könnte für Fachgespräch relevant sein.

---

## 12. EMPFOHLENE ARBEITSREIHENFOLGE FÜR NÄCHSTE SESSION

1. **Quellen recherchieren** (JavaFX-URLs, Maven-URL etc. per Websuche aktualisieren)
2. **Doku_C korrigieren** (Phasen + Klassenname + Bugs-Sektion)
3. **Doku_D füllen** (Soll-Ist + Tests + Reflexion)
4. **Doku_B ergänzen** (Wochen 5+6 Stunden, ggf. Personalplanung/Kosten)
5. **Benutzerhandbuch in Doku_E integrieren**
6. **Quellenverzeichnis in Doku_A erweitern**
7. **Alle Teile in Projektdokumentation_Sascha_Schulz.docx zusammenführen**
8. **Formatierung prüfen und anpassen**
9. **Kohärenzprüfung** (Zählung, Namen, Daten)
10. **PDF exportieren**

### Geschätzter Aufwand
- Korrekturen in Doku_C: ~2h
- Doku_D füllen (Reflexion + Tests): ~3h
- Zusammenführung + Format: ~2h
- Kohärenzprüfung + PDF: ~1h
- **Gesamt: ~8h** (realistisch in 1–2 Arbeitstagen)

---

## 13. BEWERTUNGSRELEVANTE HINWEISE

**Für Fachgespräch vorbereiten:**
- Warum kein `bestand`-Feld in `materialien`? (D-001, SUM aus Bewegungen)
- Singleton-Pattern erklären (DBConnection)
- Trennung Model / DAO / Controller (MVC)
- PreparedStatement vs. Statement (SQL-Injection-Schutz)
- Was ist ein Foreign Key? (Datenintegrität)
- Wie funktioniert JavaFX ObservableList?
- Was passiert beim Klick auf „Suchen"? (FilteredList + Predicate)
- Warum 6 Tabellen statt 3? (Normalisierung, 3NF, SRH-Anforderung ≥ 5)

**Dokumentation prüft:**
- Vollständigkeit aller Pflichtbestandteile
- Seitenanzahl 10–15 (Haupttext)
- Quellenangaben korrekt
- Konsistenz zwischen Plänen und Umsetzung
