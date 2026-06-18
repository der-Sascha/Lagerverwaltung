package de.doit.controller.crud;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * Gemeinsame Hilfsmethoden fuer Dialoge und Meldungen.
 *
 * <p>Frueher standen diese Methoden mehrfach im MainController. Sie sind hier
 * als statische Methoden gebuendelt, damit der MainController und alle
 * CRUD-Klassen ({@link EntityCrud}) dieselben Dialoge verwenden.</p>
 */
public final class Dialoge {

    private Dialoge() { } // Hilfsklasse: keine Objekte noetig

    /** Erzeugt ein leeres Formular-Gitter mit einheitlichen Abstaenden. */
    public static GridPane gitter() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        return grid;
    }

    /** Erzeugt einen OK/Abbrechen-Dialog mit dem uebergebenen Inhalt. */
    public static Dialog<ButtonType> dialog(String titel, GridPane inhalt) {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle(titel);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dlg.getDialogPane().setContent(inhalt);
        return dlg;
    }

    /** Zeigt eine Fehlermeldung mit der Ausnahme-Nachricht. */
    public static void fehler(String titel, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titel);
        alert.setHeaderText(titel);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }

    /** Zeigt einen kurzen Hinweis (z. B. "Bitte etwas auswählen"). */
    public static void warnung(String nachricht) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hinweis");
        alert.setHeaderText(null);
        alert.setContentText(nachricht);
        alert.showAndWait();
    }

    /** Fragt OK/Abbrechen ab und gibt true zurueck, wenn OK gewaehlt wurde. */
    public static boolean bestaetigen(String titel, String nachricht) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(nachricht);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
