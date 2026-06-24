package de.doit.controller.crud;

import javafx.scene.control.Alert;

public final class Dialoge {

    // bei Hilfsklasse keine Objekte nötig
    // privater Konstruktor
    private Dialoge() { }

    // Zeigt nur eine visuele Fehlermeldung in der UI
    public static void zeigeFehlerfenster(String titelText, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // ab hier das Fenster befüllen
        alert.setTitle(titelText);
        alert.setHeaderText(titelText);
        alert.setContentText(ex.getMessage());
        // erst wenn Fenster geschlossen läuft der Code weiter
        alert.showAndWait();
    }
}
