package de.doit.controller.crud;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Dialoge {

    // Hilfsklasse weil
    // privater Konstruktor
    // alle Methoden static
    // kein Zustand
    // Aufruf über Klassenanmen
    private Dialoge() { }

    // Zeigt nur eine visuele Fehlermeldung in der UI also dem Nutzer
    public static void zeigeFehlerfenster(String titelText, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // ab hier das Fenster befüllen
        alert.setTitle(titelText);
        alert.setHeaderText(titelText);
        alert.setContentText(ex.getMessage());
        // erst wenn Fenster geschlossen läuft der Code weiter
        alert.showAndWait();
    }

    // true nur, wenn der Nutzer wirklich OK klickt (X am Fenster = Abbruch)
    public static boolean bestaetigen(String titel, String frage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(frage);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    // Kurzer Hinweis an den Nutzer (z. B. "erst eine Zeile markieren")
    public static void hinweis(String titel, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Generisches Eingabe-Formular: eine TextField-Zeile je Eintrag in labels
     * (startwerte an derselben Position, gleiche Laenge wie labels).
     * Gibt die eingegebenen Werte in derselben Reihenfolge zurueck,
     * oder Optional.empty() wenn der Nutzer abgebrochen hat.
     */
    public static Optional<List<String>> formular(String titel, List<String> labels, List<String> startwerte) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        List<TextField> felder = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            TextField feld = new TextField(startwerte.get(i));
            feld.setPrefWidth(220);
            felder.add(feld);
            grid.addRow(i, new Label(labels.get(i) + ":"), feld);
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(titel);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        return dialog.showAndWait()
                .filter(b -> b == ButtonType.OK)
                .map(b -> {
                    List<String> werte = new ArrayList<>();
                    for (TextField feld : felder) {
                        werte.add(feld.getText());
                    }
                    return werte;
                });
    }
}
