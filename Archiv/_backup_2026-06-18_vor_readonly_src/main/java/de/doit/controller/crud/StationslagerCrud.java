package de.doit.controller.crud;

import de.doit.dao.StationslagerDAO;
import de.doit.model.Stationslager;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Verwaltet den Reiter "Stationslager". */
public class StationslagerCrud extends EntityCrud<Stationslager> {

    public StationslagerCrud(TableView<Stationslager> tabelle, StationslagerDAO dao) {
        super(tabelle, dao, "Stationslager");
    }

    @Override
    protected int idVon(Stationslager s) {
        return s.getId();
    }

    @Override
    protected void formularAnzeigen(Stationslager vorhanden) {
        TextField tfName     = new TextField(vorhanden != null ? vorhanden.getName()     : "");
        TextField tfStandort = new TextField(vorhanden != null ? vorhanden.getStandort() : "");
        TextField tfTyp      = new TextField(vorhanden != null ? vorhanden.getTyp()      : "");

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),     tfName);
        grid.addRow(1, new Label("Standort:"), tfStandort);
        grid.addRow(2, new Label("Typ:"),      tfTyp);

        Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Stationslager anlegen" : "Stationslager bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                dao.create(new Stationslager(0, tfName.getText(), tfStandort.getText(), tfTyp.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setStandort(tfStandort.getText());
                vorhanden.setTyp(tfTyp.getText());
                dao.update(vorhanden);
            }
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
