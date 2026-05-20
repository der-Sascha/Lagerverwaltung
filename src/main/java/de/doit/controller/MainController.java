package de.doit.controller;

import de.doit.dao.BestandsbewegungDAO;
import de.doit.dao.KategorieDAO;
import de.doit.dao.LieferantDAO;
import de.doit.dao.MaterialDAO;
import de.doit.dao.StationslagerDAO;
import de.doit.model.BestandView;
import de.doit.model.Kategorie;
import de.doit.model.Lieferant;
import de.doit.model.Stationslager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainController {

    // ---------- @FXML-Felder fuer die Suchleiste ----------
    @FXML private TextField              txtSuche;
    @FXML private ComboBox<Stationslager> cmbLager;
    @FXML private Label                  lblWarnungAnzahl;

    // ---------- @FXML-Felder fuer die Tabelle ----------
    @FXML private TableView<BestandView>           tabBestand;
    @FXML private TableColumn<BestandView,String>  colMaterial;
    @FXML private TableColumn<BestandView,String>  colEinheit;
    @FXML private TableColumn<BestandView,String>  colLager;
    @FXML private TableColumn<BestandView,Integer> colBestand;
    @FXML private TableColumn<BestandView,Integer> colMindest;
    @FXML private TableColumn<BestandView,String>  colStatus;

    // ---------- DAO-Instanzen ----------
    private final MaterialDAO            materialDao        = new MaterialDAO();
    private final KategorieDAO           kategorieDao       = new KategorieDAO();
    private final StationslagerDAO       lagerDao           = new StationslagerDAO();
    private final LieferantDAO           lieferantDao       = new LieferantDAO();
    private final BestandsbewegungDAO    bewegungDao        = new BestandsbewegungDAO();

    // ---------- Liste, die die TableView speist ----------
    private final ObservableList<BestandView> bestandListe =
            FXCollections.observableArrayList();

    // ---------- Zwischenspeicher: aktueller, ungefilterter Datensatz ----------
    private List<BestandView> alleBestaende;

    // ---------- initialize: nach FXML-Laden automatisch aufgerufen ----------
    @FXML
    public void initialize() {
        // Spalten an die Felder von BestandView binden
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colEinheit.setCellValueFactory (new PropertyValueFactory<>("einheit"));
        colLager.setCellValueFactory   (new PropertyValueFactory<>("lagerName"));
        colBestand.setCellValueFactory (new PropertyValueFactory<>("bestand"));
        colMindest.setCellValueFactory (new PropertyValueFactory<>("mindestbestand"));
        colStatus.setCellValueFactory  (new PropertyValueFactory<>("status"));

        // ObservableList an die Tabelle haengen
        tabBestand.setItems(bestandListe);

        // Zeilen rot einfaerben, wenn Warnung
        tabBestand.setRowFactory(tv -> new TableRow<BestandView>() {
            @Override
            protected void updateItem(BestandView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                } else if (item.isWarnung()) {
                    setStyle("-fx-background-color: #FADBD8;");
                } else {
                    setStyle("");
                }
            }
        });

        // ComboBox Lager befuellen, NULL-Eintrag fuer "Alle Lager"
        try {
            cmbLager.getItems().add(null);
            cmbLager.getItems().addAll(lagerDao.findAll());

            // Erste Anzeige
            ladeBestaende();
        } catch (SQLException e) {
            zeigeFehler("Laden fehlgeschlagen", e);
        }
    }

    // ---------- Hilfsmethode: alle Bestaende neu laden ----------
    private void ladeBestaende() throws SQLException {
        alleBestaende = bewegungDao.findBestandViews();
        anzeigeFiltern();
    }

    // ---------- Hilfsmethode: vorhandene Bestaende nach UI-Filtern auswerten ----------
    private void anzeigeFiltern() {
        if (alleBestaende == null) {
            return;
        }
        String suchtext = txtSuche.getText() == null ? "" : txtSuche.getText().trim().toLowerCase();
        Stationslager gewaehltesLager = cmbLager.getValue();

        ObservableList<BestandView> gefiltert = FXCollections.observableArrayList();
        int warnungen = 0;
        for (BestandView v : alleBestaende) {
            boolean passtName  = suchtext.isEmpty()
                    || v.getMaterialName().toLowerCase().contains(suchtext);
            boolean passtLager = gewaehltesLager == null
                    || v.getLagerId() == gewaehltesLager.getId();
            if (passtName && passtLager) {
                gefiltert.add(v);
                if (v.isWarnung()) {
                    warnungen++;
                }
            }
        }
        bestandListe.setAll(gefiltert);
        lblWarnungAnzahl.setText("Warnungen: " + warnungen);
    }

    // ---------- Aktion: Suchen-Knopf ----------
    @FXML
    public void onSuchen() {
        anzeigeFiltern();
    }

    // ---------- Aktion: Lager-ComboBox geaendert ----------
    @FXML
    public void onLagerChanged() {
        anzeigeFiltern();
    }

    // ---------- Aktion: Aktualisieren-Knopf ----------
    @FXML
    public void onAktualisieren() {
        try {
            ladeBestaende();
        } catch (SQLException e) {
            zeigeFehler("Aktualisieren fehlgeschlagen", e);
        }
    }

    // ---------- Aktion: Zuruecksetzen-Knopf ----------
    @FXML
    public void onZuruecksetzen() {
        txtSuche.clear();
        cmbLager.getSelectionModel().clearSelection();
        anzeigeFiltern();
    }

    // ---------- Hilfsmethode: Fehlermeldung als Dialog anzeigen ----------
    private void zeigeFehler(String titel, Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titel);
        alert.setHeaderText(titel);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}

