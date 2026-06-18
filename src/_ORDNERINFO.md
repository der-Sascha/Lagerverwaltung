# Ordner: src/

Java/Maven-Quellcode, Package `de.doit`, MVC + DAO-Pattern.

Struktur unter `src/main/java/de/doit/`:

| Pfad | Inhalt |
|---|---|
| `db/DBConnection.java` | Singleton, einmalige DB-Verbindung |
| `model/` | POJOs: `Material`, `Kategorie`, `Lieferant`, `Bestellung`, `Stationslager`, `Bestandsbewegung`, `BestandView`, Enum `BewegungsTyp` |
| `dao/LeseDAO.java` | Lese-Schnittstelle `LeseDAO<T>` (Vertrag: nur `findAll`) — read-only |
| `dao/GenericDAO.java` | Volles CRUD-Interface `GenericDAO<T> extends LeseDAO<T>` (`create`, `update`, `delete`). Aktuell von keinem DAO implementiert — Vorlage für das Eintragen von CRUD |
| `dao/` | je Tabelle: `MaterialDAO`, `KategorieDAO`, `LieferantDAO`, `BestellungDAO`, `StationslagerDAO`, `BestandsbewegungDAO` — aktuell alle `implements LeseDAO<T>` (read-only, nur `findAll` + `zeileLesen`). `BestandsbewegungDAO` zusätzlich `findBestandViews`/`getBestand` |
| `controller/MainController.java` | JavaFX-Controller: FXML-Felder, `initialize`, Bestandsübersicht (Suche/Filter) + Laden der read-only-Reiter über `EntityCrud` |
| `controller/crud/` | `Dialoge` (Dialog-/Meldungs-Hilfen) + konkrete read-only-Basis `EntityCrud<T>` (nur `load()`). Schreib-Subklassen (`MaterialCrud` …) sind entfernt — siehe `MD/Anleitung_CRUD_eintragen.md` zum Wieder-Eintragen |
| `Main.java` | JavaFX-Einstieg (`Application`): lädt `main.fxml`, zeigt Fenster, schließt DB beim Beenden |
| `Launcher.java` | JavaFX-Launcher |
| `main/resources/fxml/main.fxml` | UI-Layout (FXML) |

**Read-only-Stand (2026-06-18):** Die App ist bewusst auf Lesen reduziert. DAOs implementieren nur `LeseDAO<T>` (`findAll`), alle UI-Reiter zeigen Daten an (FXML: „Nur-Lese-Ansicht" statt Buttons). Das volle CRUD (`create/update/delete`) ist im Vertrag `GenericDAO<T>` beschrieben und wird für Material + Kategorie per `MD/Anleitung_CRUD_eintragen.md` von Hand nachgetragen. **Achtung: für die SRH-Abgabe ist CRUD Pflicht** — die Anleitung vor Abgabe umsetzen.

**Stufen-Marker (Stand 2026-06-18):** Der Code ist nach Schwierigkeit + Ablauf in Stufen gegliedert. Jede Operation trägt über alle Schichten denselben `// === Stufe N — … ===`-Marker: 0 Fundament (Launcher/Main/DBConnection), 1 LESEN (`findAll`/`load`), 2 ANLEGEN (`create`), 3 BEARBEITEN (`update`/`edit`), 4 LÖSCHEN (`delete`), 5 SUCHEN/FILTERN (`findBestandViews`/`BestandView`/`MainController`-Filter). Im Projekt nach „Stufe N" suchen zeigt alle Bausteine einer Funktion auf einen Blick.
