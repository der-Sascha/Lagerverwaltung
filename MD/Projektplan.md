# Projektplan — Lagerverwaltung Krankenhaus

**Quelle:** Projektantrag v2 (`Projektdateien/Projektantrag_Sascha_Schulz_v2.docx`)
**Stand:** 2026-06-07 (Block 2 startet 08.06.)

---

## Eckdaten

| Feld | Wert |
|---|---|
| Antragsteller | Sascha Schulz, Sanddornweg 4, 67346 Speyer |
| Gruppe | 2551 |
| Projektzeitraum | 04.05.2026 – 26.06.2026 |
| Betreuerin | Frau Cramer |
| Geplanter Aufwand | 180 h |

## Stack

- **Sprache:** Java
- **GUI:** JavaFX
- **Datenbank:** MySQL 8 (lokal)
- **Architektur:** MVC + DAO-Pattern
- **Plattform:** Lokale Desktop-Anwendung, kein Internet

---

## Phasen (7 Phasen, 180 h)

| # | Stunden | Inhalt | Status (2026-06-07) |
|---|---|---|---|
| 1 | 20 h | Projektziele festlegen, Datenbankstruktur planen, ER-Diagramm erstellen | ✅ Erledigt (KW19) |
| 2 | 20 h | Datenbank anlegen, Tabellen verknüpfen, Testdaten einfügen, DB-Verbindung herstellen | ✅ Erledigt (KW19–KW20) |
| 3 | 20 h | Programmlogik: Datenzugriff für die Kernbereiche (DAO-Schicht) | ✅ Erledigt (KW20–KW21) — 1773 Zeilen gesamt |
| 4 | 40 h | UI Grundfunktionen: Hauptfenster, Registerkarten, vollständiges CRUD für alle Bereiche | ⚡ Vorgearbeitet in KW21 (703 Zeilen MainController) — offizieller Start KW24 (08.06.) |
| 5 | 25 h | UI Erweiterte Funktionen: Suche nach Materialname, Stationsfilter, Warnmeldung | ⏳ KW25 |
| 6 | 25 h | Alle Funktionen testen, Fehler beheben, Oberfläche aufräumen | ⏳ KW25–KW26 |
| 7 | 30 h | Benutzerhandbuch + Projektdokumentation schreiben, Abgabe vorbereiten | ⏳ KW26 |
| **Σ** | **180 h** | | **Verbrauch: ~60 h (Phasen 1–3). Block 2 (Phasen 4–7) startet 08.06.2026 (KW24).** |

> **Doku-Stand 07.06.:** Projektdokumentation zusammengeführt und vorgabenkonform (Format, Verzeichnisse, 14 Quellen, Handbuch in Anhang C). Tests/Abnahme/Reflexion als Entwürfe markiert, Finalisierung nach Phase 6/7.

---

## Wochenplan (6 Wochen × 32 h = 192 h, 12 h Puffer)

| Woche | KW | Block | Phase(n) | Hauptaufgaben |
|---|---|---|---|---|
| 1 | KW19 (04.05.–08.05.) | Block 1 | 1 | Analyse, ER-Diagramm, Datenbankschema |
| 2 | KW20 (11.05.–15.05.) | Block 1 | 2 | SQL anlegen, Testdaten, JDBC-Verbindung |
| 3 | KW21 (18.05.–22.05.) | Block 1 | 3 | DAO + Model-Klassen |
| | Pause | | | (23.05.–07.06.) |
| 4 | KW24 (08.06.–12.06.) | Block 2 | 4 | UI Grundfunktionen, CRUD-Masken |
| 5 | KW25 (15.06.–19.06.) | Block 2 | 5 + 6 (Anfang) | Suche/Filter, Warnmeldung, Test-Start |
| 6 | KW26 (22.06.–26.06.) | Block 2 | 6 + 7 | Tests, Bugfixes, Doku, Abgabe |

---

## Datenmodell — 6 Tabellen (alle Pflicht)

| Tabelle | Zweck |
|---|---|
| `kategorien` | Materialgruppen (Verbandsmaterial, Injektion, Schutzausrüstung etc.) |
| `materialien` | Stammdaten der Verbrauchsmaterialien (kein Bestand-Feld!) |
| `stationslager` | Stationen/Lager (Station 1, OP, ICU, Zentrallager) |
| `lieferanten` | Lieferanten-Stammdaten |
| `bestellungen` | Bestellpositionen mit Status |
| `bestandsbewegungen` | Buchungshistorie EINGANG/AUSGANG; Bestand = SUM(EINGANG) − SUM(AUSGANG) |

**Standard-Schema:** `DOIT Krankenhaus.drawio` (drawio) + `testdaten_krankenhaus_lager.sql`

---

## Kernfunktionen

- CRUD für alle 6 Tabellen
- Suche nach Materialname
- Filter nach Station
- Filter nach Kategorie
- Warnmeldung bei Bestand ≤ Mindestbestand (rot in der UI)
- Automatische EINGANG-Buchung bei Bestellstatus „geliefert"

## Optionale Erweiterungen

- Login / Benutzerrollen
- Export (PDF / Excel)
- Reports (Verbrauchsstatistik)
- Datenbank-Backup-Funktion

---

## Risiken

| Risiko | Gegenmaßnahme |
|---|---|
| JavaFX-Lernkurve |
