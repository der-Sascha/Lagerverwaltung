# Ordner: src/

Java/Maven-Quellcode, Package `de.doit`, MVC + DAO-Pattern.

Struktur unter `src/main/java/de/doit/`:

| Pfad | Inhalt |
|---|---|
| `db/DBConnection.java` | Singleton, einmalige DB-Verbindung |
| `model/` | POJOs: `Material`, `Kategorie`, `Lieferant`, `Bestellung`, `Stationslager`, `Bestandsbewegung`, `BestandView`, Enum `BewegungsTyp` |
| `dao/LeseDAO.java` | Einzige DAO-Schnittstelle: `LeseDAO<T>` (Vertrag: nur `findAll`) — read-only |
| `dao/` | je Tabelle: `MaterialDAO`, `KategorieDAO`, `LieferantDAO`, `BestellungDAO`, `StationslagerDAO`, `BestandsbewegungDAO` — alle `implements LeseDAO<T>` (read-only, nur `findAll` + `zeileLesen`). `BestandsbewegungDAO` zusätzlich `findBestandViews`/`getBestand`. Schreib-Methoden (`create`/`update`/`delete`) werden bei Bedarf direkt im DAO ergänzt (siehe Anleitung) |
| `controller/MainController.java` | JavaFX-Controller: FXML-Felder, `initialize`, Bestandsübersicht (Suche/Filter) + Laden der read-only-Reiter über `EntityCrud` |
| `controller/crud/` | `Dialoge` (Dialog-/Meldungs-Hilfen) + konkrete read-only-Basis `EntityCrud<T>` (nur `load()`). Schreib-Subklassen (`MaterialCrud` …) sind entfernt — siehe `MD/Anleitung_CRUD_eintragen.md` zum Wieder-Eintragen |
| `Main.java` | JavaFX-Einstieg (`Application`): lädt `main.fxml`, zeigt Fenster, schließt DB beim Beenden |
| `Launcher.java` | JavaFX-Launcher |
| `main/resources/fxml/main.fxml` | UI-Layout (FXML) |

**Read-only-Stand (2026-06-18):** Die App ist bewusst auf Lesen reduziert. DAOs implementieren nur `LeseDAO<T>` (`findAll`), alle UI-Reiter zeigen Daten an (FXML: „Nur-Lese-Ansicht" statt Buttons). Das volle CRUD (`create/update/delete`) ist im Vertrag `GenericDAO<T>` beschrieben und wird für Material + Kategorie per `MD/Anleitung_CRUD_eintragen.md` von Hand nachgetragen. **Achtung: für die SRH-Abgabe ist CRUD Pflicht** — die Anleitung vor Abgabe umsetzen.

**Stufen-Marker (Stand 2026-06-18, read-only):** Der Code ist nach Schwierigkeit + Ablauf mit `// === Stufe N — … ===`-Markern gegliedert. **Aktiv im Code vorhanden:** Stufe 0 Fundament (Launcher/Main/DBConnection), Stufe 1 LESEN (`findAll` in allen DAOs, `EntityCrud.load`, Reiter-Laden im MainController), Stufe 5 SUCHEN/FILTERN (`BestandsbewegungDAO.findBestandViews`/`getBestand`, `BestandView`, MainController-Filter). **Aktuell nicht vorhanden:** Stufe 2 ANLEGEN / 3 BEARBEITEN / 4 LÖSCHEN gibt es im Code (noch) nicht — diese Methoden trägst du für Material + Kategorie direkt in die jeweiligen DAOs + eigene `…Crud`-Klassen ein, per `MD/Anleitung_CRUD_eintragen.md`. Im Projekt nach „Stufe N" suchen zeigt die Bausteine je Stufe.
