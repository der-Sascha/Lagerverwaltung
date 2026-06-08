package de.doit.controller;

import de.doit.dao.*;
import de.doit.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MainController {

    // ===== BESTAND =====
    @FXML private TextField               txtSuche;
    @FXML private ComboBox<Stationslager> cmbLager;
    @FXML private Label                   lblWarnungAnzahl;
    @FXML private TableView<BestandView>           tabBestand;
    @FXML private TableColumn<BestandView, String>  colMaterial;
    @FXML private TableColumn<BestandView, String>  colEinheit;
    @FXML private TableColumn<BestandView, String>  colLager;
    @FXML private TableColumn<BestandView, Integer> colBestand;
    @FXML private TableColumn<BestandView, Integer> colMindest;
    @FXML private TableColumn<BestandView, String>  colStatus;

    // ===== MATERIALIEN =====
    @FXML private TableView<Material>           tabMaterialien;
    @FXML private TableColumn<Material, String>  colMatName;
    @FXML private TableColumn<Material, String>  colMatEinheit;
    @FXML private TableColumn<Material, Integer> colMatMindest;
    @FXML private TableColumn<Material, Integer> colMatKategorie;

    // ===== KATEGORIEN =====
    @FXML private TableView<Kategorie>          tabKategorien;
    @FXML private TableColumn<Kategorie, String> colKatName;
    @FXML private TableColumn<Kategorie, String> colKatBeschr;

    // ===== STATIONSLAGER =====
    @FXML private TableView<Stationslager>          tabLager;
    @FXML private TableColumn<Stationslager, String> colLagerName;
    @FXML private TableColumn<Stationslager, String> colLagerStandort;
    @FXML private TableColumn<Stationslager, String> colLagerTyp;

    // ===== LIEFERANTEN =====
    @FXML private TableView<Lieferant>          tabLieferanten;
    @FXML private TableColumn<Lieferant, String> colLiefName;
    @FXML private TableColumn<Lieferant, String> colLiefKontakt;
    @FXML private TableColumn<Lieferant, String> colLiefTelefon;
    @FXML private TableColumn<Lieferant, String> colLiefEmail;

    // ===== BESTELLUNGEN =====
    @FXML private TableView<Bestellung>             tabBestellungen;
    @FXML private TableColumn<Bestellung, Integer>   colBestMat;
    @FXML private TableColumn<Bestellung, Integer>   colBestLief;
    @FXML private TableColumn<Bestellung, Integer>   colBestLager;
    @FXML private TableColumn<Bestellung, Integer>   colBestMenge;
    @FXML private TableColumn<Bestellung, LocalDate> colBestBestell;
    @FXML private TableColumn<Bestellung, LocalDate> colBestLiefer;
    @FXML private TableColumn<Bestellung, String>    colBestStatus;

    // ===== BESTANDSBEWEGUNGEN =====
    @FXML private TableView<Bestandsbewegung>               tabBewegungen;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewMat;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewLager;
    @FXML private TableColumn<Bestandsbewegung, BewegungsTyp>  colBewTyp;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewMenge;
    @FXML private TableColumn<Bestandsbewegung, LocalDateTime> colBewDatum;
    @FXML private TableColumn<Bestandsbewegung, LocalDate>     colBewAblauf;
    @FXML private TableColumn<Bestandsbewegung, String>        colBewBem;

    // ===== DAOs =====
    private final MaterialDAO         materialDao   = new MaterialDAO();
    private final KategorieDAO        kategorieDao  = new KategorieDAO();
    private final StationslagerDAO    lagerDao      = new StationslagerDAO();
    private final LieferantDAO        lieferantDao  = new LieferantDAO();
    private final BestellungDAO       bestellungDao = new BestellungDAO();
    private final BestandsbewegungDAO bewegungDao   = new BestandsbewegungDAO();

    // ===== Observable Lists =====
    private final ObservableList<BestandView>      bestandListe    = FXCollections.observableArrayList();
    private final ObservableList<Material>         materialListe   = FXCollections.observableArrayList();
    private final ObservableList<Kategorie>        kategorieListe  = FXCollections.observableArrayList();
    private final ObservableList<Stationslager>    lagerListe      = FXCollections.observableArrayList();
    private final ObservableList<Lieferant>        lieferantListe  = FXCollections.observableArrayList();
    private final ObservableList<Bestellung>       bestellungListe = FXCollections.observableArrayList();
    private final ObservableList<Bestandsbewegung> bewegungListe   = FXCollections.observableArrayList();

    private List<BestandView> alleBestaende;

    // =========================================================================
    // INITIALIZE
    // =========================================================================
    @FXML
    public void initialize() {
        // Bestand-Tab
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colEinheit .setCellValueFactory(new PropertyValueFactory<>("einheit"));
        colLager   .setCellValueFactory(new PropertyValueFactory<>("lagerName"));
        colBestand .setCellValueFactory(new PropertyValueFactory<>("bestand"));
        colMindest .setCellValueFactory(new PropertyValueFactory<>("mindestbestand"));
        colStatus  .setCellValueFactory(new PropertyValueFactory<>("status"));
        tabBestand.setItems(bestandListe);
        tabBestand.setRowFactory(tv -> new TableRow<BestandView>() {
            @Override protected void updateItem(BestandView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setStyle("");
                else if (item.isWarnung()) setStyle("-fx-background-color: #FADBD8;");
                else                       setStyle("");
            }
        });

        // Materialien-Tab
        colMatName     .setCellValueFactory(new PropertyValueFactory<>("name"));
        colMatEinheit  .setCellValueFactory(new PropertyValueFactory<>("einheit"));
        colMatMindest  .setCellValueFactory(new PropertyValueFactory<>("mindestbestand"));
        colMatKategorie.setCellValueFactory(new PropertyValueFactory<>("kategorieId"));
        tabMaterialien.setItems(materialListe);

        // Kategorien-Tab
        colKatName .setCellValueFactory(new PropertyValueFactory<>("name"));
        colKatBeschr.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
        tabKategorien.setItems(kategorieListe);

        // Stationslager-Tab
        colLagerName    .setCellValueFactory(new PropertyValueFactory<>("name"));
        colLagerStandort.setCellValueFactory(new PropertyValueFactory<>("standort"));
        colLagerTyp     .setCellValueFactory(new PropertyValueFactory<>("typ"));
        tabLager.setItems(lagerListe);

        // Lieferanten-Tab
        colLiefName   .setCellValueFactory(new PropertyValueFactory<>("name"));
        colLiefKontakt.setCellValueFactory(new PropertyValueFactory<>("kontakt"));
        colLiefTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colLiefEmail  .setCellValueFactory(new PropertyValueFactory<>("email"));
        tabLieferanten.setItems(lieferantListe);

        // Bestellungen-Tab
        colBestMat    .setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colBestLief   .setCellValueFactory(new PropertyValueFactory<>("lieferantId"));
        colBestLager  .setCellValueFactory(new PropertyValueFactory<>("lagerId"));
        colBestMenge  .setCellValueFactory(new PropertyValueFactory<>("menge"));
        colBestBestell.setCellValueFactory(new PropertyValueFactory<>("bestelldatum"));
        colBestLiefer .setCellValueFactory(new PropertyValueFactory<>("lieferdatum"));
        colBestStatus .setCellValueFactory(new PropertyValueFactory<>("status"));
        tabBestellungen.setItems(bestellungListe);

        // Bestandsbewegungen-Tab
        colBewMat   .setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colBewLager .setCellValueFactory(new PropertyValueFactory<>("lagerId"));
        colBewTyp   .setCellValueFactory(new PropertyValueFactory<>("bewegungstyp"));
        colBewMenge .setCellValueFactory(new PropertyValueFactory<>("menge"));
        colBewDatum .setCellValueFactory(new PropertyValueFactory<>("datum"));
        colBewAblauf.setCellValueFactory(new PropertyValueFactory<>("ablaufdatum"));
        colBewBem   .setCellValueFactory(new PropertyValueFactory<>("bemerkung"));
        tabBewegungen.setItems(bewegungListe);

        // Bestand-Tab initialisieren
        try {
            cmbLager.getItems().add(null);
            cmbLager.getItems().addAll(lagerDao.findAll());
            ladeBestaende();
        } catch (SQLException e) {
            zeigeFehler("Laden fehlgeschlagen", e);
        }
    }

    // =========================================================================
    // BESTAND
    // =========================================================================
    private void ladeBestaende() throws SQLException {
        alleBestaende = bewegungDao.findBestandViews();
        anzeigeFiltern();
    }

    private void anzeigeFiltern() {
        if (alleBestaende == null) return;
        String suchtext = txtSuche.getText() == null ? "" : txtSuche.getText().trim().toLowerCase();
        Stationslager gewaehltesLager = cmbLager.getValue();
        ObservableList<BestandView> gefiltert = FXCollections.observableArrayList();
        int warnungen = 0;
        for (BestandView v : alleBestaende) {
            boolean passtName  = suchtext.isEmpty() || v.getMaterialName().toLowerCase().contains(suchtext);
            boolean passtLager = gewaehltesLager == null || v.getLagerId() == gewaehltesLager.getId();
            if (passtName && passtLager) {
                gefiltert.add(v);
                if (v.isWarnung()) warnungen++;
            }
        }
        bestandListe.setAll(gefiltert);
        lblWarnungAnzahl.setText("Warnungen: " + warnungen);
    }

    @FXML public void onSuchen()       { anzeigeFiltern(); }
    @FXML public void onLagerChanged() { anzeigeFiltern(); }

    @FXML public void onAktualisieren() {
        try { ladeBestaende(); } catch (SQLException e) { zeigeFehler("Aktualisieren fehlgeschlagen", e); }
    }

    @FXML public void onZuruecksetzen() {
        txtSuche.clear();
        cmbLager.getSelectionModel().clearSelection();
        anzeigeFiltern();
    }

    // =========================================================================
    // MATERIALIEN
    // =========================================================================
    @FXML public void onMaterialienTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { materialListe.setAll(materialDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onMaterialAnlegen()    { zeigeMaterialFormular(null); }
    @FXML public void onMaterialBearbeiten() {
        Material sel = tabMaterialien.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte ein Material auswählen."); return; }
        zeigeMaterialFormular(sel);
    }
    @FXML public void onMaterialLoeschen() {
        Material sel = tabMaterialien.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte ein Material auswählen."); return; }
        if (!bestaetigen("Material löschen", "Material \"" + sel.getName() + "\" wirklich löschen?")) return;
        try { materialDao.delete(sel.getId()); materialListe.setAll(materialDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    private void zeigeMaterialFormular(Material vorhanden) {
        TextField tfName    = new TextField(vorhanden != null ? vorhanden.getName()    : "");
        TextField tfEinheit = new TextField(vorhanden != null ? vorhanden.getEinheit() : "");
        TextField tfMindest = new TextField(vorhanden != null ? String.valueOf(vorhanden.getMindestbestand()) : "0");
        TextField tfKatId   = new TextField(vorhanden != null ? String.valueOf(vorhanden.getKategorieId())    : "1");

        GridPane grid = erstelleGrid();
        grid.addRow(0, new Label("Name:"),           tfName);
        grid.addRow(1, new Label("Einheit:"),         tfEinheit);
        grid.addRow(2, new Label("Mindestbestand:"),  tfMindest);
        grid.addRow(3, new Label("Kategorie-ID:"),    tfKatId);

        Dialog<ButtonType> dlg = erstelleDialog(vorhanden == null ? "Material anlegen" : "Material bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                materialDao.create(new Material(
                        tfName.getText(), tfEinheit.getText(),
                        Integer.parseInt(tfMindest.getText()), Integer.parseInt(tfKatId.getText())));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setEinheit(tfEinheit.getText());
                vorhanden.setMindestbestand(Integer.parseInt(tfMindest.getText()));
                vorhanden.setKategorieId(Integer.parseInt(tfKatId.getText()));
                materialDao.update(vorhanden);
            }
            materialListe.setAll(materialDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    // =========================================================================
    // KATEGORIEN
    // =========================================================================
    @FXML public void onKategorienTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { kategorieListe.setAll(kategorieDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onKategorieAnlegen()    { zeigeKategorieFormular(null); }
    @FXML public void onKategorieBearbeiten() {
        Kategorie sel = tabKategorien.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Kategorie auswählen."); return; }
        zeigeKategorieFormular(sel);
    }
    @FXML public void onKategorieLoeschen() {
        Kategorie sel = tabKategorien.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Kategorie auswählen."); return; }
        if (!bestaetigen("Kategorie löschen", "Kategorie \"" + sel.getName() + "\" wirklich löschen?")) return;
        try { kategorieDao.delete(sel.getId()); kategorieListe.setAll(kategorieDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    private void zeigeKategorieFormular(Kategorie vorhanden) {
        TextField tfName   = new TextField(vorhanden != null ? vorhanden.getName()         : "");
        TextField tfBeschr = new TextField(vorhanden != null ? vorhanden.getBeschreibung() : "");
        tfBeschr.setPrefWidth(300);

        GridPane grid = erstelleGrid();
        grid.addRow(0, new Label("Name:"),         tfName);
        grid.addRow(1, new Label("Beschreibung:"), tfBeschr);

        Dialog<ButtonType> dlg = erstelleDialog(vorhanden == null ? "Kategorie anlegen" : "Kategorie bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                kategorieDao.create(new Kategorie(0, tfName.getText(), tfBeschr.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setBeschreibung(tfBeschr.getText());
                kategorieDao.update(vorhanden);
            }
            kategorieListe.setAll(kategorieDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    // =========================================================================
    // STATIONSLAGER
    // =========================================================================
    @FXML public void onStationslagerTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { lagerListe.setAll(lagerDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onLagerAnlegen()    { zeigeLagerFormular(null); }
    @FXML public void onLagerBearbeiten() {
        Stationslager sel = tabLager.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte ein Stationslager auswählen."); return; }
        zeigeLagerFormular(sel);
    }
    @FXML public void onLagerLoeschen() {
        Stationslager sel = tabLager.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte ein Stationslager auswählen."); return; }
        if (!bestaetigen("Stationslager löschen", "Stationslager \"" + sel.getName() + "\" wirklich löschen?")) return;
        try { lagerDao.delete(sel.getId()); lagerListe.setAll(lagerDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    private void zeigeLagerFormular(Stationslager vorhanden) {
        TextField tfName     = new TextField(vorhanden != null ? vorhanden.getName()     : "");
        TextField tfStandort = new TextField(vorhanden != null ? vorhanden.getStandort() : "");
        TextField tfTyp      = new TextField(vorhanden != null ? vorhanden.getTyp()      : "");

        GridPane grid = erstelleGrid();
        grid.addRow(0, new Label("Name:"),     tfName);
        grid.addRow(1, new Label("Standort:"), tfStandort);
        grid.addRow(2, new Label("Typ:"),      tfTyp);

        Dialog<ButtonType> dlg = erstelleDialog(vorhanden == null ? "Stationslager anlegen" : "Stationslager bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                lagerDao.create(new Stationslager(0, tfName.getText(), tfStandort.getText(), tfTyp.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setStandort(tfStandort.getText());
                vorhanden.setTyp(tfTyp.getText());
                lagerDao.update(vorhanden);
            }
            lagerListe.setAll(lagerDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    // =========================================================================
    // LIEFERANTEN
    // =========================================================================
    @FXML public void onLieferantenTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { lieferantListe.setAll(lieferantDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onLieferantAnlegen()    { zeigeLieferantFormular(null); }
    @FXML public void onLieferantBearbeiten() {
        Lieferant sel = tabLieferanten.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte einen Lieferanten auswählen."); return; }
        zeigeLieferantFormular(sel);
    }
    @FXML public void onLieferantLoeschen() {
        Lieferant sel = tabLieferanten.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte einen Lieferanten auswählen."); return; }
        if (!bestaetigen("Lieferant löschen", "Lieferant \"" + sel.getName() + "\" wirklich löschen?")) return;
        try { lieferantDao.delete(sel.getId()); lieferantListe.setAll(lieferantDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    private void zeigeLieferantFormular(Lieferant vorhanden) {
        TextField tfName    = new TextField(vorhanden != null ? vorhanden.getName()    : "");
        TextField tfKontakt = new TextField(vorhanden != null ? vorhanden.getKontakt() : "");
        TextField tfTelefon = new TextField(vorhanden != null ? vorhanden.getTelefon() : "");
        TextField tfEmail   = new TextField(vorhanden != null ? vorhanden.getEmail()   : "");

        GridPane grid = erstelleGrid();
        grid.addRow(0, new Label("Name:"),    tfName);
        grid.addRow(1, new Label("Kontakt:"), tfKontakt);
        grid.addRow(2, new Label("Telefon:"), tfTelefon);
        grid.addRow(3, new Label("E-Mail:"),  tfEmail);

        Dialog<ButtonType> dlg = erstelleDialog(vorhanden == null ? "Lieferant anlegen" : "Lieferant bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                lieferantDao.create(new Lieferant(0,
                        tfName.getText(), tfKontakt.getText(), tfTelefon.getText(), tfEmail.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setKontakt(tfKontakt.getText());
                vorhanden.setTelefon(tfTelefon.getText());
                vorhanden.setEmail(tfEmail.getText());
                lieferantDao.update(vorhanden);
            }
            lieferantListe.setAll(lieferantDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    // =========================================================================
    // BESTELLUNGEN
    // =========================================================================
    @FXML public void onBestellungenTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { bestellungListe.setAll(bestellungDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onBestellungAnlegen()    { zeigeBestellungFormular(null); }
    @FXML public void onBestellungBearbeiten() {
        Bestellung sel = tabBestellungen.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Bestellung auswählen."); return; }
        zeigeBestellungFormular(sel);
    }
    @FXML public void onBestellungLoeschen() {
        Bestellung sel = tabBestellungen.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Bestellung auswählen."); return; }
        if (!bestaetigen("Bestellung löschen", "Bestellung #" + sel.getId() + " wirklich löschen?")) return;
        try { bestellungDao.delete(sel.getId()); bestellungListe.setAll(bestellungDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    private void zeigeBestellungFormular(Bestellung vorhanden) {
        try {
            List<Material>      mats  = materialDao.findAll();
            List<Lieferant>     liefs = lieferantDao.findAll();
            List<Stationslager> lags  = lagerDao.findAll();

            ComboBox<Material>      cbMat    = new ComboBox<>(FXCollections.observableArrayList(mats));
            ComboBox<Lieferant>     cbLief   = new ComboBox<>(FXCollections.observableArrayList(liefs));
            ComboBox<Stationslager> cbLag    = new ComboBox<>(FXCollections.observableArrayList(lags));
            TextField               tfMenge  = new TextField(vorhanden != null ? String.valueOf(vorhanden.getMenge()) : "1");
            DatePicker              dpBest   = new DatePicker(vorhanden != null ? vorhanden.getBestelldatum() : LocalDate.now());
            DatePicker              dpLief   = new DatePicker(vorhanden != null ? vorhanden.getLieferdatum()  : null);
            ComboBox<String>        cbStatus = new ComboBox<>(FXCollections.observableArrayList("offen", "geliefert", "storniert"));
            cbStatus.setValue(vorhanden != null ? vorhanden.getStatus() : "offen");

            if (vorhanden != null) {
                mats .stream().filter(m -> m.getId() == vorhanden.getMaterialId()) .findFirst().ifPresent(cbMat::setValue);
                liefs.stream().filter(l -> l.getId() == vorhanden.getLieferantId()).findFirst().ifPresent(cbLief::setValue);
                lags .stream().filter(l -> l.getId() == vorhanden.getLagerId())    .findFirst().ifPresent(cbLag::setValue);
            }

            GridPane grid = erstelleGrid();
            grid.addRow(0, new Label("Material:"),     cbMat);
            grid.addRow(1, new Label("Lieferant:"),    cbLief);
            grid.addRow(2, new Label("Lager:"),        cbLag);
            grid.addRow(3, new Label("Menge:"),        tfMenge);
            grid.addRow(4, new Label("Bestelldatum:"), dpBest);
            grid.addRow(5, new Label("Lieferdatum:"),  dpLief);
            grid.addRow(6, new Label("Status:"),       cbStatus);

            Dialog<ButtonType> dlg = erstelleDialog(vorhanden == null ? "Bestellung anlegen" : "Bestellung bearbeiten", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            if (vorhanden == null) {
                bestellungDao.create(new Bestellung(
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
                bestellungDao.update(vorhanden);
            }
            bestellungListe.setAll(bestellungDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    // =========================================================================
    // BESTANDSBEWEGUNGEN
    // =========================================================================
    @FXML public void onBewegungTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { bewegungListe.setAll(bewegungDao.findAll()); } catch (SQLException ex) { zeigeFehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onBewegungAnlegen() {
        try {
            List<Material>      mats = materialDao.findAll();
            List<Stationslager> lags = lagerDao.findAll();

            ComboBox<Material>      cbMat    = new ComboBox<>(FXCollections.observableArrayList(mats));
            ComboBox<Stationslager> cbLag    = new ComboBox<>(FXCollections.observableArrayList(lags));
            ComboBox<BewegungsTyp>  cbTyp    = new ComboBox<>(FXCollections.observableArrayList(BewegungsTyp.values()));
            TextField               tfMenge  = new TextField("1");
            DatePicker              dpAblauf = new DatePicker();
            TextField               tfBem    = new TextField();
            cbTyp.setValue(BewegungsTyp.EINGANG);
            tfBem.setPrefWidth(250);

            GridPane grid = erstelleGrid();
            grid.addRow(0, new Label("Material:"),    cbMat);
            grid.addRow(1, new Label("Lager:"),       cbLag);
            grid.addRow(2, new Label("Typ:"),         cbTyp);
            grid.addRow(3, new Label("Menge:"),       tfMenge);
            grid.addRow(4, new Label("Ablaufdatum:"), dpAblauf);
            grid.addRow(5, new Label("Bemerkung:"),   tfBem);

            Dialog<ButtonType> dlg = erstelleDialog("Bestandsbewegung anlegen", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            int menge = Integer.parseInt(tfMenge.getText());
            if (cbTyp.getValue() == BewegungsTyp.AUSGANG) {
                int bestand = bewegungDao.getBestand(cbMat.getValue().getId(), cbLag.getValue().getId());
                if (menge > bestand) { zeigeWarnung("Nicht genug Bestand. Verfügbar: " + bestand); return; }
            }
            bewegungDao.create(new Bestandsbewegung(
                    cbMat.getValue().getId(), cbLag.getValue().getId(), cbTyp.getValue(),
                    menge, dpAblauf.getValue(),
                    LocalDateTime.now(), tfBem.getText()));
            bewegungListe.setAll(bewegungDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    @FXML public void onWarenentnahme() {
        BestandView sel = tabBestand.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte einen Bestand auswählen."); return; }

        TextField tfMenge = new TextField("1");
        TextField tfBem   = new TextField();
        tfBem.setPrefWidth(250);

        GridPane grid = erstelleGrid();
        grid.addRow(0, new Label("Material:"),  new Label(sel.getMaterialName()));
        grid.addRow(1, new Label("Lager:"),     new Label(sel.getLagerName()));
        grid.addRow(2, new Label("Menge:"),     tfMenge);
        grid.addRow(3, new Label("Bemerkung:"), tfBem);

        Dialog<ButtonType> dlg = erstelleDialog("Warenentnahme", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            int menge = Integer.parseInt(tfMenge.getText());
            if (menge > sel.getBestand()) { zeigeWarnung("Entnahmemenge übersteigt den Bestand (" + sel.getBestand() + ")."); return; }
            bewegungDao.create(new Bestandsbewegung(
                    sel.getMaterialId(), sel.getLagerId(), BewegungsTyp.AUSGANG,
                    menge, null, LocalDateTime.now(), tfBem.getText()));
            ladeBestaende();
        } catch (Exception ex) { zeigeFehler("Entnahme fehlgeschlagen", ex); }
    }

    @FXML public void onUmlagerung() {
        BestandView sel = tabBestand.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte einen Bestand auswählen."); return; }

        try {
            List<Stationslager> lags = lagerDao.findAll();
            lags.removeIf(l -> l.getId() == sel.getLagerId());

            ComboBox<Stationslager> cbZiel  = new ComboBox<>(FXCollections.observableArrayList(lags));
            TextField               tfMenge = new TextField("1");
            TextField               tfBem   = new TextField();
            tfBem.setPrefWidth(250);

            GridPane grid = erstelleGrid();
            grid.addRow(0, new Label("Material:"),   new Label(sel.getMaterialName()));
            grid.addRow(1, new Label("Von Lager:"),  new Label(sel.getLagerName()));
            grid.addRow(2, new Label("Nach Lager:"), cbZiel);
            grid.addRow(3, new Label("Menge:"),      tfMenge);
            grid.addRow(4, new Label("Bemerkung:"),  tfBem);

            Dialog<ButtonType> dlg = erstelleDialog("Umlagerung", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            if (cbZiel.getValue() == null) { zeigeWarnung("Bitte ein Ziellager auswählen."); return; }
            int menge = Integer.parseInt(tfMenge.getText());
            if (menge > sel.getBestand()) { zeigeWarnung("Menge übersteigt den Bestand (" + sel.getBestand() + ")."); return; }

            String bem = tfBem.getText();
            LocalDateTime jetzt = LocalDateTime.now();
            bewegungDao.create(new Bestandsbewegung(
                    sel.getMaterialId(), sel.getLagerId(), BewegungsTyp.AUSGANG,
                    menge, null, jetzt,
                    "Umlagerung nach " + cbZiel.getValue().getName() + (bem.isEmpty() ? "" : ": " + bem)));
            bewegungDao.create(new Bestandsbewegung(
                    sel.getMaterialId(), cbZiel.getValue().getId(), BewegungsTyp.EINGANG,
                    menge, null, jetzt,
                    "Umlagerung von " + sel.getLagerName() + (bem.isEmpty() ? "" : ": " + bem)));
            ladeBestaende();
        } catch (Exception ex) { zeigeFehler("Umlagerung fehlgeschlagen", ex); }
    }

    @FXML public void onBewegungBearbeiten() {
        Bestandsbewegung sel = tabBewegungen.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Bestandsbewegung auswählen."); return; }

        try {
            List<Material>      mats = materialDao.findAll();
            List<Stationslager> lags = lagerDao.findAll();

            ComboBox<Material>      cbMat    = new ComboBox<>(FXCollections.observableArrayList(mats));
            ComboBox<Stationslager> cbLag    = new ComboBox<>(FXCollections.observableArrayList(lags));
            ComboBox<BewegungsTyp>  cbTyp    = new ComboBox<>(FXCollections.observableArrayList(BewegungsTyp.values()));
            TextField               tfMenge  = new TextField(String.valueOf(sel.getMenge()));
            DatePicker              dpAblauf = new DatePicker(sel.getAblaufdatum());
            TextField               tfBem    = new TextField(sel.getBemerkung() != null ? sel.getBemerkung() : "");
            tfBem.setPrefWidth(250);

            mats.stream().filter(m -> m.getId() == sel.getMaterialId()).findFirst().ifPresent(cbMat::setValue);
            lags.stream().filter(l -> l.getId() == sel.getLagerId()).findFirst().ifPresent(cbLag::setValue);
            cbTyp.setValue(sel.getBewegungstyp());

            GridPane grid = erstelleGrid();
            grid.addRow(0, new Label("Material:"),    cbMat);
            grid.addRow(1, new Label("Lager:"),       cbLag);
            grid.addRow(2, new Label("Typ:"),         cbTyp);
            grid.addRow(3, new Label("Menge:"),       tfMenge);
            grid.addRow(4, new Label("Ablaufdatum:"), dpAblauf);
            grid.addRow(5, new Label("Bemerkung:"),   tfBem);

            Dialog<ButtonType> dlg = erstelleDialog("Bestandsbewegung bearbeiten", grid);
            if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

            int neueMenge = Integer.parseInt(tfMenge.getText());
            if (cbTyp.getValue() == BewegungsTyp.AUSGANG) {
                int bestand = bewegungDao.getBestand(cbMat.getValue().getId(), cbLag.getValue().getId());
                int verfuegbar = sel.getBewegungstyp() == BewegungsTyp.AUSGANG
                        ? bestand + sel.getMenge()
                        : bestand - sel.getMenge();
                if (neueMenge > verfuegbar) { zeigeWarnung("Nicht genug Bestand. Verfügbar: " + verfuegbar); return; }
            }
            sel.setMaterialId(cbMat.getValue().getId());
            sel.setLagerId(cbLag.getValue().getId());
            sel.setBewegungstyp(cbTyp.getValue());
            sel.setMenge(neueMenge);
            sel.setAblaufdatum(dpAblauf.getValue());
            sel.setBemerkung(tfBem.getText());
            bewegungDao.update(sel);
            bewegungListe.setAll(bewegungDao.findAll());
        } catch (Exception ex) { zeigeFehler("Speichern fehlgeschlagen", ex); }
    }

    @FXML public void onBewegungLoeschen() {
        Bestandsbewegung sel = tabBewegungen.getSelectionModel().getSelectedItem();
        if (sel == null) { zeigeWarnung("Bitte eine Bestandsbewegung auswählen."); return; }
        if (!bestaetigen("Bewegung löschen", "Bewegung #" + sel.getId() + " wirklich löschen?")) return;
        try { bewegungDao.delete(sel.getId()); bewegungListe.setAll(bewegungDao.findAll()); }
        catch (SQLException ex) { zeigeFehler("Löschen fehlgeschlagen", ex); }
    }

    // =========================================================================
    // HILFSMETHODEN
    // =========================================================================
    private GridPane erstelleGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        return grid;
    }

    private Dialog<ButtonType> erstelleDialog(String titel, GridPane inhalt) {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle(titel);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dlg.getDialogPane().setContent(inhalt);
        return dlg;
    }

    private void zeigeFehler(String titel, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titel);
        alert.setHeaderText(titel);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }

    private void zeigeWarnung(String nachricht) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hinweis");
        alert.setHeaderText(null);
        alert.setContentText(nachricht);
        alert.showAndWait();
    }

    private boolean bestaetigen(String titel, String nachricht) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(nachricht);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
