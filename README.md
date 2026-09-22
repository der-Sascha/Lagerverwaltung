# Lagerverwaltung Krankenhaus

JavaFX-Anwendung zur Lagerverwaltung von medizinischem Verbrauchsmaterial in einem Krankenhaus. Java + JavaFX + MySQL, MVC + DAO-Pattern mit Generics.

## Klassenübersicht

```
src/main/java/de/doit/
├── Main.java                        Hauptklasse zum Starten der Anwendung
├── Launcher.java                    Alternative Startklasse
├── controller/
│   ├── MainController.java          Controller für alle Reiter (Bestand, Materialien, Kategorien, Stationslager, Lieferanten, Bestellungen, Bestandsbewegungen)
│   └── crud/
│       ├── EntityCrud.java          Generische CRUD-Basis (laden/anlegen/bearbeiten/löschen) für alle Entitäten
│       └── Dialoge.java             Wiederverwendbare Dialogfenster (Fehler, Hinweis, Bestätigung, Eingabeformular)
├── dao/
│   ├── GenericDAO.java              Interface: create/findAll/update/delete
│   ├── MaterialDAO.java
│   ├── KategorieDAO.java
│   ├── StationslagerDAO.java
│   ├── LieferantDAO.java
│   ├── BestellungDAO.java
│   └── BestandsbewegungDAO.java     zusätzlich findBestandViews() für die Bestandsübersicht
├── model/
│   ├── Material.java
│   ├── Kategorie.java
│   ├── Stationslager.java
│   ├── Lieferant.java
│   ├── Bestellung.java
│   ├── Bestandsbewegung.java
│   ├── BewegungsTyp.java            Enum: EINGANG / AUSGANG
│   └── BestandView.java             berechnete Sicht für die Bestandsübersicht
└── db/
    └── DBConnection.java            Datenbankverbindung (Singleton)

src/main/resources/fxml/
└── main.fxml                        Oberfläche (alle Reiter)
```

## Datenmodell

6 Tabellen: `kategorien`, `materialien`, `stationslager`, `lieferanten`, `bestellungen`, `bestandsbewegungen`. Bestand wird aus `bestandsbewegungen` berechnet (`SUM(EINGANG) − SUM(AUSGANG)`), nicht in `materialien` gespeichert.
