# Lagerverwaltung Krankenhaus

## Projektübersicht

Dies ist eine JavaFX-basierte Lagerverwaltungsanwendung für ein Krankenhaus mit folgender Struktur:

### Projektstruktur
```
src/
├── main/
│   ├── java/
│   │   └── de/doit/
│   │       ├── Main.java                 # Hauptklasse zum Starten der Anwendung
│   │       ├── Launcher.java             # Alternative Startklasse
│   │       ├── controller/               # Controller-Klassen für die GUI
│   │       │   └── MainController.java   # Hauptcontroller für die Bestandsansicht
│   │       ├── model/                    # Datenmodelle
│   │       │   ├── BestandView.java      # View-Modell für die Bestandsanzeige
│   │       │   ├── Material.java
│   │       │   ├── Kategorie.java
│   │       │   ├── Lieferant.java
│   │       │   ├── Bestellung.java
│   │       │   ├── Stationslager.java
│   │       │   ├── Bestandsbewegung.java
│   │       │   └── BewegungsTyp.java
│   │       └── dao/                      # Data Access Objects
│   │           ├── MaterialDAO.java
│   │           ├── KategorieDAO.java
│   │           ├── LieferantDAO.java
│   │           ├── BestellungDAO.java
│   │           ├── StationslagerDAO.java
│   │           └── BestandsbewegungDAO.java
│   └── resources/
│       └── fxml/
│           └── main.fxml                 # FXML-Datei für die Hauptansicht
└── main/resources/
    └── db/
        └── DBConnection.java             # Datenbankverbindungsklasse
```

### Hauptkomponenten

#### Datenbankverbindung
- `DBConnection.java`: Stellt die Verbindung zur MySQL-Datenbank her
- Verbindung: `jdbc:mysql://127.0.0.1:3324/DOIT`
- Benutzer: `root`
- Passwort: `1234`

#### Datenmodelle
- `BestandView.java`: View-Modell zur Darstellung des Bestands in der Tabelle
- `Bestandsbewegung.java`: Modell für Bestandsbewegungen (Eingang/Ausgang)
- `Stationslager.java`: Lager-Modell
- `Material.java`: Materialien-Modell
- `Kategorie.java`: Kategorien-Modell
- `Lieferant.java`: Lieferanten-Modell

#### Datenzugriff (DAOs)
- `BestandsbewegungDAO.java`: Haupt-DAO für Bestandsabfragen
- `MaterialDAO.java`, `KategorieDAO.java`, `LieferantDAO.java`, `StationslagerDAO.java`: DAOs für andere Entitäten

#### Benutzeroberfläche
- `MainController.java`: Controller für die Hauptansicht
- `main.fxml`: FXML-Datei mit der GUI-Struktur
- Suchfunktion für Materialien und Lager
- Filterung nach Lager
- Warnungsfarben bei Mindestbestand-Unterschreitung

### Funktionen
- Anzeige des aktuellen Bestands pro Lager
- Suchfunktion nach Materialnamen
- Filterung nach Lager
- Warnung bei unterschrittenem Mindestbestand (rote Hintergrundfarbe)
- Aktualisierung der Daten
- Zurücksetzen der Filter

### Datenbankabfragen
Die `BestandsbewegungDAO` führt komplexe SQL-Abfragen durch, die:
- Bestandsbewegungen auswerten
- Summen für Eingänge und Ausgänge berechnen
- Aktuellen Bestand pro Material und Lager ermitteln
- Mindestbestand-Überschreitungen erkennen

### Build-Konfiguration
Das Projekt verwendet Maven mit folgenden Abhängigkeiten:
- JavaFX Controls und FXML
- MySQL Connector J

Das Projekt kann mit folgenden Maven-Befehlen gebaut werden:
- `mvn clean compile` - Kompilieren
- `mvn javafx:run` - Programm starten
- `mvn package` - Erzeugen eines Fat JAR

## Entwicklungsumgebung
- Java 17
- Maven 3.8+
- MySQL 8.0+
- JavaFX 21