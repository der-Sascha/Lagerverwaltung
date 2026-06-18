# Ordner: src/

Java/Maven-Quellcode, Package `de.doit`, MVC + DAO-Pattern.

Struktur unter `src/main/java/de/doit/`:

| Pfad | Inhalt |
|---|---|
| `db/DBConnection.java` | Singleton, einmalige DB-Verbindung |
| `model/` | POJOs: `Material`, `Kategorie`, `Lieferant`, `Bestellung`, `Stationslager`, `Bestandsbewegung`, `BestandView`, Enum `BewegungsTyp` |
| `dao/GenericDAO.java` | Generische Schnittstelle `GenericDAO<T>` (Vertrag: `create`, `findAll`, `update`, `delete`) |
| `dao/` | CRUD je Tabelle: `MaterialDAO`, `KategorieDAO`, `LieferantDAO`, `BestellungDAO`, `StationslagerDAO`, `BestandsbewegungDAO` (alle `implements GenericDAO<T>`) |
| `controller/MainController.java` | Schlanker JavaFX-Controller (Verteiler): FXML-Felder, `initialize`, Bestandsübersicht (Suche/Filter) + 1-Zeilen-Weiterleitung an die CRUD-Klassen |
| `controller/crud/` | `Dialoge` (gemeinsame Dialog-/Meldungs-Hilfen), abstrakte Basis `EntityCrud<T>` (laden/anlegen/bearbeiten/löschen) + je Reiter `MaterialCrud`, `KategorieCrud`, `StationslagerCrud`, `LieferantCrud`, `BestellungCrud`, `BewegungCrud` |
| `Main.java` | Einstiegspunkt / Demo |
| `Launcher.java` | JavaFX-Launcher |
| `main/resources/fxml/main.fxml` | UI-Layout (FXML) |

**Muster:** DAO mit `create()`, `findById()`, `findAll()`, `update()`, `delete()` über `PreparedStatement`. Die vier wiederkehrenden CRUD-Methoden sind im generischen Interface `GenericDAO<T>` als Vertrag gebündelt; jedes DAO kennzeichnet sie mit `@Override`.
