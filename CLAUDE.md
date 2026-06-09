# Kontext: DoIT-Projektphase (SRH)

**WICHTIG:** Diese Datei enthält den Kontext für jeden neuen Chat. Schlank halten.

**Ordnerstruktur (Stand 2026-06-09):**
- `src/` — Java/Maven-Quellcode (`de.doit`: `db/`, `model/`, `dao/`, `controller/`, `Main.java`, `Launcher.java`; FXML unter `src/main/resources/fxml/`)
- `Doku/` — Arbeits-Einzelteile Doku_A–E **+ Endabgabe `Projektdokumentation_Sascha_Schulz.docx`/`.pdf`**
- `MD/` — alle Markdown-Notizen (Projektplan, Tagebuch, Entscheidungen, Dateiübersicht usw.)
- `Projektdateien/` — drawio, ER-Bild (PNG/docx), SQL-Testdaten, Antrag v2, Wochenplan
- `Lernhandbuch/` — Lernhandbuch-Teile 1–4 + `Glossar_Vereinigt.docx`
- `Lernmaterial/` — Java-Lerndokumente (Handbuch-Start, Konzept-Zusammenhänge, Spickzettel, Code-Review-PDF)
- `Vorgaben/` — SRH-Originalvorgaben (Anleitung, Antrags-/Wochenplan-Vorlagen, Bewertungsbögen, Doku-Vorgaben, Beispiele)
- `Projektmanagement/` — Kohärenzprüfungen
- `Ablage/` — Audits & Entwürfe (`Audits_und_Entwuerfe/`)
- `Archiv/` — alte/abgelöste Dateien (Benutzerhandbuch, alte Audits, altes Lernhandbuch, `_backup_2026-06-07/`)
- Wurzel: `CLAUDE.md`, `README.md`, `pom.xml`, `DOIT.iml`
- **Sync: Google Drive** (kein OneDrive) · **Repo: github.com/der-Sascha/Lagerverwaltung**

Detail-Dateien:
- `MD/Projektplan.md` — Phasen, Stunden, Wochenplan, Zeitplan
- `MD/Dateiuebersicht.md` — alle Dateien im Ordner mit Zweck
- `MD/Entscheidungen.md` — Designentscheidungen + Begründung
- `MD/Projekttagebuch.md` — laufendes Log: Änderungen, Probleme, Erkenntnisse, Quellen

**Dokumentationsstruktur:**
`Doku/Doku_A` bis `Doku/Doku_E` sind Arbeits-Einzelteile der Projektdokumentation.
Änderungen immer in der jeweiligen `Doku_X`-Datei — am Ende alles in `Doku/Projektdokumentation_Sascha_Schulz.docx` zusammenführen → PDF → Moodle.
Detailübersicht der Teile: siehe `MD/Dateiuebersicht.md` → Abschnitt "Doku_A–E Struktur-Erklärung".

**Tagebuch-Regel:** Bei jeder Analyse, Änderung oder Erkenntnis immer einen Eintrag in `MD/Projekttagebuch.md` schreiben (Datum + Kategorie: [Änderung] / [Problem] / [Erkenntnis] / [Quelle] / [Manuell]).

---

## Person

- **Sascha Schulz**, Sanddornweg 4, 67346 Speyer
- E-Mail: sascha0schulz@gmail.com
- Beruf: **Wirtschaftsinformatiker (WI)**
- Gruppe: **2551**
- Ausbildungsstätte: SRH Berufliche Rehabilitation GmbH

## Hauptprojekt

- **Titel:** Entwicklung einer Software zur Lagerverwaltung von medizinischem Verbrauchsmaterial in einem Krankenhaus
- **Stack:** Java + JavaFX + MySQL, MVC + DAO-Pattern, lokal (kein Internet)
- **Projektzeitraum:** 04.05.2026 – 26.06.2026 (6 Wochen, 2 Blöcke)
- **Geplanter Aufwand:** 180 h (laut Antrag v2) — Wochenrechnung 192 h enthält 12 h Puffer
- **Betreuerin:** Frau Cramer

## SRH-Anforderungen Hauptprojekt

- MySQL-Datenbank: **mind. 5 Tabellen**
- CRUD-Funktionalität
- MVC-Architektur
- Such-/Filterfunktionen

## Bewertung

- Dokumentation 30 %
- Präsentation 20 %
- Fachgespräch 50 %

## Datenmodell (6 Tabellen, alle Pflicht)

`kategorien`, `materialien`, `stationslager`, `lieferanten`, `bestellungen`, `bestandsbewegungen`

Standard ist `Projektdateien/DOIT Krankenhaus.drawio`. SQL-Schema in `Projektdateien/testdaten_krankenhaus_lager.sql`. Aktuelles, schema-korrektes ER-Bild: `Projektdateien/ER_Diagramm_aktuell.png`. Bestand wird aus `bestandsbewegungen` berechnet (`SUM(EINGANG) − SUM(AUSGANG)`), nicht in `materialien` gespeichert.

---

## Lernstil — Java + DB

Bei Java/MySQL-Themen immer **DAO-Muster** als Erklärungsgrundlage (Sascha kennt es aus dem Dino-Park-Beispiel von Frau Cramer):

- `DBConnection.java` — Singleton, einmalige Verbindung
- `Model.java` (z. B. `Material.java`) — POJO, spiegelt Tabelle, zwei Konstruktoren (mit/ohne ID)
- `ModelDAO.java` — CRUD: `create()`, `findById()`, `findAll()`, `update()`, `delete()` mit `PreparedStatement`
- `Main.java` — Demo-Einstiegspunkt

Neue Konzepte daran andocken.

## Dokumentationsvorgaben (Quelle: `11_DOIT_2551_Dokumentationsvorgaben.pdf`)

**Abgabe:** 17.07.2026 als PDF-Upload in Moodle — Verspätung wirkt sich negativ auf Note aus.

**Format:**
- Zeilenabstand 1,5 | Schriftgröße 12 | Schriftart: Arial, Aptos oder Times New Roman
- Ränder: Oben/Links/Rechts 2,5 cm, Unten 2 cm

**Seitenanzahl:** 10–15 Seiten (exkl. Deckblatt, Anhang, alle Verzeichnisse)
- Abbildungen/Code/Tabellen dürfen Teil sein, aber max. 1/3 pro Seite
- Anhang darf beliebig lang sein; Benutzerhandbuch gehört in den Anhang

**Pflichtbestandteile:**
- Deckblatt
- Inhaltsverzeichnis
- Abbildungsverzeichnis
- Tabellenverzeichnis
- Quellenverzeichnis (Literatur: min. Titel + Autor + Jahr | Web: min. URL + Abrufdatum)

---

## Abschluss-Checkliste Dokumentation (`Projektdokumentation_Sascha_Schulz.docx`)

Bei jedem Chat zur Dokumentation bitte folgenden Stand prüfen und aktualisieren:

| Vorgabe | Status |
|---|---|
| Deckblatt vorhanden | ✅ vorhanden |
| Inhaltsverzeichnis vorhanden | ✅ vorhanden (echte Seitenzahlen) |
| Abbildungsverzeichnis vorhanden | ✅ vorhanden |
| Tabellenverzeichnis vorhanden | ✅ vorhanden (11 Tabellen) |
| Quellenverzeichnis vorhanden | ✅ vorhanden (13 Quellen, URL+Abruf bzw. Titel/Autor/Jahr) |
| Benutzerhandbuch im Anhang (nicht sep
