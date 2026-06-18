# Ordner: Projektdateien/

Projekt-Artefakte: Datenmodell, SQL, Antrag, Wochenplan.

| Datei | Inhalt |
|---|---|
| `DOIT Krankenhaus.drawio` | **Standard**-Datenmodell-Quelle (drawio) |
| `ER_Diagramm_aktuell.png` | Aktuelles, schema-korrektes ER-Bild |
| `ER_Diagramm_Sascha_Schulz.docx` | ER-Diagramm als Word-Dokument |
| `testdaten_krankenhaus_lager.sql` | SQL-Schema + Testdaten (6 Tabellen) |
| `Projektantrag_Sascha_Schulz_v2.docx` | Aktueller Projektantrag (v2) |
| `Wochenplan_Sascha_Schulz.xlsx` | Wochenplan |

**Hinweis:** 6 Tabellen (`kategorien`, `materialien`, `stationslager`, `lieferanten`, `bestellungen`, `bestandsbewegungen`). Bestand = `SUM(EINGANG) − SUM(AUSGANG)` aus `bestandsbewegungen`, nicht gespeichert.
