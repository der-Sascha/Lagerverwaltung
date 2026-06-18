# Backup: Warenentnahme & Umlagerung (Stand vor Entfernung)

**Datum:** 2026-06-17
**Grund:** Die beiden Komfort-Funktionen „Warenentnahme" und „Umlagerung" wurden
entfernt, um das Projekt auf den genehmigten Antragsumfang zu reduzieren. Sie waren
keine zugesagten Kernfunktionen — Ein-/Ausgänge inkl. Umlagerung lassen sich weiterhin
über den Reiter „Bestandsbewegungen" buchen.

## Was hier gesichert ist (Originalstand)

| Datei | Original-Zielort |
|---|---|
| `src/MainController.java` | `src/main/java/de/doit/controller/MainController.java` |
| `src/main.fxml` | `src/main/resources/fxml/main.fxml` |
| `doku/Doku_B_Ausgangssituation.docx` | `Doku/Doku_B_Ausgangssituation.docx` |
| `doku/Doku_C_Durchfuehrung.docx` | `Doku/Doku_C_Durchfuehrung.docx` |
| `doku/Doku_E_Anhang.docx` | `Doku/Doku_E_Anhang.docx` |
| `doku/Projektdokumentation_Sascha_Schulz.docx` | `Doku/Projektdokumentation_Sascha_Schulz.docx` |

## Wiederherstellung (Einzelfall)

Die jeweilige Datei aus diesem Ordner zurück an den Original-Zielort kopieren
(vorhandene Datei überschreiben). Danach in IntelliJ neu kompilieren; bei der Doku
das PDF neu erzeugen.

Konkret entfernt wurden:
- FXML: zwei Buttons „Warenentnahme" und „Umlagerung" in `main.fxml`.
- Java: Methoden `onWarenentnahme()` und `onUmlagerung()` im `MainController`
  sowie der dadurch ungenutzte Import `javafx.scene.layout.GridPane`.
- Doku: die Textstellen, die die beiden Schaltflächen beschrieben (Doku_B, Doku_C,
  Doku_E und die Endabgabe).
