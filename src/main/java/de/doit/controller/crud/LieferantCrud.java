package de.doit.controller.crud;

import de.doit.dao.LieferantDAO;
import de.doit.model.Lieferant;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Verwaltet den Reiter "Lieferanten". */
public class LieferantCrud extends EntityCrud<Lieferant> {

    public LieferantCrud(TableView<Lieferant> tabelle, LieferantDAO dao) {
        super(tabelle, dao, "Lieferant");
    }

    @Override
    protected int idVon(Lieferant l) {
        return l.getId();
    }

    @Override
    protected void formularAnzeigen(Lieferant vorhanden) {
        TextField tfName    = new TextField(vorhanden != null ? vorhanden.getName()    : "");
        TextField tfKontakt = new TextField(vorhanden != null ? vorhanden.getKontakt() : "");
        TextField tfTelefon = new TextField(vorhanden != null ? vorhanden.getTelefon() : "");
        TextField tfEmail   = new TextField(vorhanden != null ? vorhanden.getEmail()   : "");

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),    tfName);
        grid.addRow(1, new Label("Kontakt:"), tfKontakt);
        grid.addRow(2, new Label("Telefon:"), tfTelefon);
        grid.addRow(3, new Label("E-Mail:"),  tfEmail);

        Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Lieferant anlegen" : "Lieferant bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                dao.create(new Lieferant(0,
                        tfName.getText(), tfKontakt.getText(), tfTelefon.getText(), tfEmail.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setKontakt(tfKontakt.getText());
                vorhanden.setTelefon(tfTelefon.getText());
                vorhanden.setEmail(tfEmail.getText());
                dao.update(vorhanden);
            }
            laden();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
