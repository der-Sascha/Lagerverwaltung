package de.doit.controller.crud;

import de.doit.dao.BestellungDAO;
import de.doit.dao.LieferantDAO;
import de.doit.dao.MaterialDAO;
import de.doit.dao.StationslagerDAO;
import de.doit.model.Bestellung;
import de.doit.model.Lieferant;
import de.doit.model.Material;
import de.doit.model.Stationslager;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;

/** Verwaltet den Reiter "Bestellungen" (mit Auswahllisten fuer Fremdschluessel). */
public class BestellungCrud extends EntityCrud<Bestellung> {

    private final MaterialDAO      materialDao;
    private final LieferantDAO     lieferantDao;
    private final StationslagerDAO lagerDao;

    public BestellungCrud(TableView<Bestellung> tabelle, BestellungDAO dao,
                          MaterialDAO materialDao, LieferantDAO lieferantDao, StationslagerDAO lagerDao) {
        super(tabelle, dao, "Bestellung");
        this.materialDao  = materialDao;
        this.lieferantDao = lieferantDao;
        this.lagerDao     = lagerDao;
    }

    @Override
    protected int idVon(Bestellung b) {
        return b.getId();
    }

    @Override
    protected void formularAnzeigen(Bestellung vorhanden) {
        try {
            List<Material>      materialien  = materialDao.findAll();
            List<Lieferant>     lieferanten = lieferantDao.findAll();
            List<Stationslager> lagerListe  = lagerDao.findAll();

            ComboBox<Material>      cbMat    = new ComboBox<>(FXCollections.observableArrayList(materialien));
            ComboBox<Lieferant>     cbLief   = new ComboBox<>(FXCollections.observableArrayList(lieferanten));
            ComboBox<Stationslager> cbLag    = new ComboBox<>(FXCollections.observableArrayList(lagerListe));
            TextField               tfMenge  = new TextField(vorhanden != null ? String.valueOf(vorhanden.getMenge()) : "1");
            DatePicker              dpBest   = new DatePicker(vorhanden != null ? vorhanden.getBestelldatum() : LocalDate.now());
            DatePicker              dpLief   = new DatePicker(vorhanden != null ? vorhanden.getLieferdatum()  : null);
            ComboBox<String>        cbStatus = new ComboBox<>(FXCollections.observableArrayList("offen", "geliefert", "storniert"));
            cbStatus.setValue(vorhanden != null ? vorhanden.getStatus() : "offen");

            if (vorhanden != null) {
                materialien .stream().filter(m -> m.getId() == vorhanden.getMaterialId()) .findFirst().ifPresent(cbMat::setValue);
                lieferanten.stream().filter(l -> l.getId() == vorhanden.getLieferantId()).findFirst().ifPresent(cbLief::setValue);
                lagerListe .stream().filter(l -> l.getId() == vorhanden.getLagerId())    .findFirst().ifPresent(cbLag::setValue);
            }

            GridPane grid = Dialoge.gitter();
            grid.addRow(0, new Label("Material:"),     cbMat);
            grid.addRow(1, new Label("Lieferant:"),    cbLief);
            grid.addRow(2, new Label("Lager:"),        cbLag);
            grid.addRow(3, new Label("Menge:"),        tfMenge);
            grid.addRow(4, new Label("Bestelldatum:"), dpBest);
            grid.addRow(5, new Label("Lieferdatum:"),  dpLief);
            grid.addRow(6, new Label("Status:"),       cbStatus);

            Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Bestellung anlegen" : "Bestellung bearbeiten", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            // Pflichtauswahl pruefen, sonst NullPointerException bei getValue().getId()
            if (cbMat.getValue() == null)  { Dialoge.warnung("Bitte ein Material auswählen.");  return; }
            if (cbLief.getValue() == null) { Dialoge.warnung("Bitte einen Lieferanten auswählen."); return; }
            if (cbLag.getValue() == null)  { Dialoge.warnung("Bitte ein Lager auswählen.");     return; }

            if (vorhanden == null) {
                dao.create(new Bestellung(
                        cbMat.getValue().getId(), cbLief.getValue().getId(), cbLag.getValue().getId(),
                        Integer.parseInt(tfMenge.getText()), dpBest.getValue(), dpLief.getValue(),
                        cbStatus.getValue()));
            } else {
                vorhanden.setMaterialId(cbMat.getValue().getId());
                vorhanden.setLieferantId(cbLief.getValue().getId());
                vorhanden.setLagerId(cbLag.getValue().getId());
                vorhanden.setMenge(Integer.parseInt(tfMenge.getText()));
                vorhanden.setBestelldatum(dpBest.getValue());
                vorhanden.setLieferdatum(dpLief.getValue());
                vorhanden.setStatus(cbStatus.getValue());
                dao.update(vorhanden);
            }
            laden();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
