package de.doit.controller.crud;

import de.doit.dao.BestandsbewegungDAO;
import de.doit.dao.MaterialDAO;
import de.doit.dao.StationslagerDAO;
import de.doit.model.Bestandsbewegung;
import de.doit.model.BewegungsTyp;
import de.doit.model.Material;
import de.doit.model.Stationslager;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.List;

/** Verwaltet den Reiter "Bestandsbewegungen" (mit Bestandspruefung bei AUSGANG). */
public class BewegungCrud extends EntityCrud<Bestandsbewegung> {

    private final BestandsbewegungDAO bewegungDao;
    private final MaterialDAO         materialDao;
    private final StationslagerDAO    lagerDao;

    public BewegungCrud(TableView<Bestandsbewegung> tabelle, BestandsbewegungDAO dao,
                        MaterialDAO materialDao, StationslagerDAO lagerDao) {
        super(tabelle, dao, "Bestandsbewegung");
        this.bewegungDao = dao;
        this.materialDao = materialDao;
        this.lagerDao    = lagerDao;
    }

    @Override
    protected int idVon(Bestandsbewegung b) {
        return b.getId();
    }

    @Override
    protected void formularAnzeigen(Bestandsbewegung vorhanden) {
        try {
            List<Material>      materialien = materialDao.findAll();
            List<Stationslager> lagerListe = lagerDao.findAll();

            ComboBox<Material>      cbMat    = new ComboBox<>(FXCollections.observableArrayList(materialien));
            ComboBox<Stationslager> cbLag    = new ComboBox<>(FXCollections.observableArrayList(lagerListe));
            ComboBox<BewegungsTyp>  cbTyp    = new ComboBox<>(FXCollections.observableArrayList(BewegungsTyp.values()));
            TextField               tfMenge  = new TextField(vorhanden != null ? String.valueOf(vorhanden.getMenge()) : "1");
            DatePicker              dpAblauf = new DatePicker(vorhanden != null ? vorhanden.getAblaufdatum() : null);
            TextField               tfBem    = new TextField(vorhanden != null && vorhanden.getBemerkung() != null ? vorhanden.getBemerkung() : "");
            tfBem.setPrefWidth(250);

            if (vorhanden != null) {
                materialien.stream().filter(m -> m.getId() == vorhanden.getMaterialId()).findFirst().ifPresent(cbMat::setValue);
                lagerListe.stream().filter(l -> l.getId() == vorhanden.getLagerId()).findFirst().ifPresent(cbLag::setValue);
                cbTyp.setValue(vorhanden.getBewegungstyp());
            } else {
                cbTyp.setValue(BewegungsTyp.EINGANG);
            }

            GridPane grid = Dialoge.gitter();
            grid.addRow(0, new Label("Material:"),    cbMat);
            grid.addRow(1, new Label("Lager:"),       cbLag);
            grid.addRow(2, new Label("Typ:"),         cbTyp);
            grid.addRow(3, new Label("Menge:"),       tfMenge);
            grid.addRow(4, new Label("Ablaufdatum:"), dpAblauf);
            grid.addRow(5, new Label("Bemerkung:"),   tfBem);

            Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Bestandsbewegung anlegen" : "Bestandsbewegung bearbeiten", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            // Pflichtauswahl pruefen, sonst NullPointerException bei getValue().getId()
            if (cbMat.getValue() == null) { Dialoge.warnung("Bitte ein Material auswählen."); return; }
            if (cbLag.getValue() == null) { Dialoge.warnung("Bitte ein Lager auswählen.");    return; }

            int menge = Integer.parseInt(tfMenge.getText());

            if (cbTyp.getValue() == BewegungsTyp.AUSGANG) {
                int bestand = bewegungDao.getBestand(cbMat.getValue().getId(), cbLag.getValue().getId());
                // beim Bearbeiten zaehlt die alte Menge derselben Bewegung nicht mit
                int verfuegbar = bestand;
                if (vorhanden != null) {
                    verfuegbar = vorhanden.getBewegungstyp() == BewegungsTyp.AUSGANG
                            ? bestand + vorhanden.getMenge()
                            : bestand - vorhanden.getMenge();
                }
                if (menge > verfuegbar) { Dialoge.warnung("Nicht genug Bestand. Verfügbar: " + verfuegbar); return; }
            }

            if (vorhanden == null) {
                dao.create(new Bestandsbewegung(
                        cbMat.getValue().getId(), cbLag.getValue().getId(), cbTyp.getValue(),
                        menge, dpAblauf.getValue(), LocalDateTime.now(), tfBem.getText()));
            } else {
                vorhanden.setMaterialId(cbMat.getValue().getId());
                vorhanden.setLagerId(cbLag.getValue().getId());
                vorhanden.setBewegungstyp(cbTyp.getValue());
                vorhanden.setMenge(menge);
                vorhanden.setAblaufdatum(dpAblauf.getValue());
                vorhanden.setBemerkung(tfBem.getText());
                dao.update(vorhanden);
            }
            laden();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}