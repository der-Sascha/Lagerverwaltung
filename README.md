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
│   │       │   └── MainController.java   # Hauptcontroller für die Bestandsanzeige
│   │       ├── model/                    # Datenmodelle
│   │       ├── dao/                      # Data Access Objects
│   │       └── db/                       # Datenbankverbindung
│   └── resources/
│       └── fxml/
│           └── main.fxml                 # FXML-Datei für die Hauptansicht
```

### Build-Konfiguration

```bash
mvn clean compile    # Kompilieren
mvn javafx:run       # Programm starten
mvn package          # Fat JAR erzeugen
```

## Entwicklungsumgebung

- Java 17
- Maven 3.8+
- MySQL 8.0+
- JavaFX 21
