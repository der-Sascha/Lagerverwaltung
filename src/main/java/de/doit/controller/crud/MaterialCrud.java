package de.doit.controller.crud;

import de.doit.dao.MaterialDAO;
import de.doit.model.Material;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Verwaltet den Reiter "Materialien". */
public class MaterialCrud extends EntityCrud<Material> {

    public MaterialCrud(javafx.scene.control.TableView<Material> tabelle, MaterialDAO dao) {
        super(tabelle, dao, "Material");
    }

    @Override
    protected int idVon(Material m) {
        return m.getId();
    }

    @Override
    protected void formularAnzeigen(Material vorhanden) {
        TextField tfName    = new TextField(vorhanden != null ? vorhanden.getName()    : "");
        TextField tfEinheit = new TextField(vorhanden != null ? vorhanden.getEinheit() : "");
        TextField tfMindest = new TextField(vorhanden != null ? String.valueOf(vorhanden.getMindestbestand()) : "0");
        TextField tfKatId   = new TextField(vorhanden != null ? String.valueOf(vorhanden.getKategorieId())    : "1");

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),          tfName);
        grid.addRow(1, new Label("Einheit:"),        tfEinheit);
        grid.addRow(2, new Label("Mindestbestand:"), tfMindest);
        grid.addRow(3, new Label("Kategorie-ID:"),   tfKatId);

        Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Material anlegen" : "Material bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                dao.create(new Material(
                        tfName.getText(), tfEinheit.getText(),
                        Integer.parseInt(tfMindest.getText()), Integer.parseInt(tfKatId.getText())));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setEinheit(tfEinheit.getText());
                vorhanden.setMindestbestand(Integer.parseInt(tfMindest.getText()));
                vorhanden.setKategorieId(Integer.parseInt(tfKatId.getText()));
                dao.update(vorhanden);
            }
            laden();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
