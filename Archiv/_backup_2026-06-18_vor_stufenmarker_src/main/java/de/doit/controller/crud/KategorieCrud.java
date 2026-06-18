package de.doit.controller.crud;

import de.doit.dao.KategorieDAO;
import de.doit.model.Kategorie;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Verwaltet den Reiter "Kategorien". */
public class KategorieCrud extends EntityCrud<Kategorie> {

    public KategorieCrud(TableView<Kategorie> tabelle, KategorieDAO dao) {
        super(tabelle, dao, "Kategorie");
    }

    @Override
    protected int idVon(Kategorie k) {
        return k.getId();
    }

    @Override
    protected void formularAnzeigen(Kategorie vorhanden) {
        TextField tfName   = new TextField(vorhanden != null ? vorhanden.getName()         : "");
        TextField tfBeschr = new TextField(vorhanden != null ? vorhanden.getBeschreibung() : "");
        tfBeschr.setPrefWidth(300);

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),         tfName);
        grid.addRow(1, new Label("Beschreibung:"), tfBeschr);

        Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Kategorie anlegen" : "Kategorie bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                dao.create(new Kategorie(0, tfName.getText(), tfBeschr.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setBeschreibung(tfBeschr.getText());
                dao.update(vorhanden);
            }
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
