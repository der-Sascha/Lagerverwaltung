package de.doit.controller;

import de.doit.controller.crud.*;
import de.doit.dao.*;
import de.doit.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller des Hauptfensters.
 *
 * <p>Der Controller ist nur noch der "Verteiler": Die FXML-Oberflaeche ruft
 * seine Methoden auf, und er reicht die Arbeit an die passende
 * CRUD-Klasse weiter (siehe Paket {@code de.doit.controller.crud}). Den
 * wiederkehrenden Ablauf (Laden/Anlegen/Bearbeiten/Loeschen) enthaelt die
 * gemeinsame Basisklasse {@link de.doit.controller.crud.EntityCrud}, sodass
 * jeder Reiter gleich aufgebaut ist. Nur der Reiter "Bestandsuebersicht"
 * (Suche, Filter) bleibt direkt hier, weil er
 * eine berechnete, schreibgeschuetzte Sicht ist.</p>
 */
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

    // ===== CRUD-Klassen je Reiter (gleicher Aufbau, siehe EntityCrud) =====
    private MaterialCrud      materialCrud;
    private KategorieCrud     kategorieCrud;
    private StationslagerCrud lagerCrud;
    private LieferantCrud     lieferantCrud;
    private BestellungCrud    bestellungCrud;
    private BewegungCrud      bewegungCrud;

    // ===== Bestand (berechnete Sicht, bleibt hier) =====
    private final ObservableList<BestandView> bestandListe = FXCollections.observableArrayList();
    private List<BestandView> alleBestaende;

    // =========================================================================
    // INITIALIZE
    // =========================================================================
    @FXML
    public void initialize() {
        // --- Spalten der Tabellen mit den Modell-Eigenschaften verbinden ---
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colEinheit .setCellValueFactory(new PropertyValueFactory<>("einheit"));
        colLager   .setCellValueFactory(new PropertyValueFactory<>("lagerName"));
        colBestand .setCellValueFactory(new PropertyValueFactory<>("bestand"));
        colMindest .setCellValueFactory(new PropertyValueFactory<>("mindestbestand"));
        colStatus  .setCellValueFactory(new PropertyValueFactory<>("status"));

        colMatName     .setCellValueFactory(new PropertyValueFactory<>("name"));
        colMatEinheit  .setCellValueFactory(new PropertyValueFactory<>("einheit"));
        colMatMindest  .setCellValueFactory(new PropertyValueFactory<>("mindestbestand"));
        colMatKategorie.setCellValueFactory(new PropertyValueFactory<>("kategorieId"));

        colKatName .setCellValueFactory(new PropertyValueFactory<>("name"));
        colKatBeschr.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));

        colLagerName    .setCellValueFactory(new PropertyValueFactory<>("name"));
        colLagerStandort.setCellValueFactory(new PropertyValueFactory<>("standort"));
        colLagerTyp     .setCellValueFactory(new PropertyValueFactory<>("typ"));

        colLiefName   .setCellValueFactory(new PropertyValueFactory<>("name"));
        colLiefKontakt.setCellValueFactory(new PropertyValueFactory<>("kontakt"));
        colLiefTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colLiefEmail  .setCellValueFactory(new PropertyValueFactory<>("email"));

        colBestMat    .setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colBestLief   .setCellValueFactory(new PropertyValueFactory<>("lieferantId"));
        colBestLager  .setCellValueFactory(new PropertyValueFactory<>("lagerId"));
        colBestMenge  .setCellValueFactory(new PropertyValueFactory<>("menge"));
        colBestBestell.setCellValueFactory(new PropertyValueFactory<>("bestelldatum"));
        colBestLiefer .setCellValueFactory(new PropertyValueFactory<>("lieferdatum"));
        colBestStatus .setCellValueFactory(new PropertyValueFactory<>("status"));

        colBewMat   .setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colBewLager .setCellValueFactory(new PropertyValueFactory<>("lagerId"));
        colBewTyp   .setCellValueFactory(new PropertyValueFactory<>("bewegungstyp"));
        colBewMenge .setCellValueFactory(new PropertyValueFactory<>("menge"));
        colBewDatum .setCellValueFactory(new PropertyValueFactory<>("datum"));
        colBewAblauf.setCellValueFactory(new PropertyValueFactory<>("ablaufdatum"));
        colBewBem   .setCellValueFactory(new PropertyValueFactory<>("bemerkung"));

        // --- CRUD-Klassen erzeugen (jede setzt ihre Tabelle selbst) ---
        materialCrud   = new MaterialCrud(tabMaterialien, materialDao);
        kategorieCrud  = new KategorieCrud(tabKategorien, kategorieDao);
        lagerCrud      = new StationslagerCrud(tabLager, lagerDao);
        lieferantCrud  = new LieferantCrud(tabLieferanten, lieferantDao);
        bestellungCrud = new BestellungCrud(tabBestellungen, bestellungDao, materialDao, lieferantDao, lagerDao);
        bewegungCrud   = new BewegungCrud(tabBewegungen, bewegungDao, materialDao, lagerDao);

        // --- Bestand-Tab (berechnete Sicht mit Warnungs-Faerbung) ---
        tabBestand.setItems(bestandListe);
        tabBestand.setRowFactory(tv -> new TableRow<BestandView>() {
            @Override protected void updateItem(BestandView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setStyle("");
                else if (item.isWarnung()) setStyle("-fx-background-color: #FADBD8;");
                else                       setStyle("");
            }
        });

        try {
            cmbLager.getItems().add(null);
            cmbLager.getItems().addAll(lagerDao.findAll());
            ladeBestaende();
        } catch (SQLException e) {
            Dialoge.fehler("Laden fehlgeschlagen", e);
        }
    }

    // =========================================================================
    // BESTANDSUEBERSICHT (Suche, Filter)
    // =========================================================================
    // === Stufe 5 — SUCHEN/FILTERN (berechnete Bestandssicht laden) ===
    private void ladeBestaende() throws SQLException {
        alleBestaende = bewegungDao.findBestandViews();
        anzeigeFiltern();
    }

    // === Stufe 5 — SUCHEN/FILTERN (Such-/Filterlogik) ===
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
        try { ladeBestaende(); } catch (SQLException e) { Dialoge.fehler("Aktualisieren fehlgeschlagen", e); }
    }

    @FXML public void onBestandTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { ladeBestaende(); } catch (SQLException ex) { Dialoge.fehler("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onZuruecksetzen() {
        txtSuche.clear();
        cmbLager.getSelectionModel().clearSelection();
        anzeigeFiltern();
    }

    // =========================================================================
    // REITER MIT STAMMDATEN: nur noch Weiterleitung an die CRUD-Klassen
    // =========================================================================
    // === Stufen 1-4 — Weiterleitung je Reiter an EntityCrud (load/create/edit/delete) ===
    @FXML public void onMaterialienTabSelected(Event e)   { if (istAktiv(e)) materialCrud.load(); }
    @FXML public void onMaterialAnlegen()                 { materialCrud.create(); }
    @FXML public void onMaterialBearbeiten()              { materialCrud.edit(); }
    @FXML public void onMaterialLoeschen()                { materialCrud.delete(); }

    @FXML public void onKategorienTabSelected(Event e)    { if (istAktiv(e)) kategorieCrud.load(); }
    @FXML public void onKategorieAnlegen()                { kategorieCrud.create(); }
    @FXML public void onKategorieBearbeiten()             { kategorieCrud.edit(); }
    @FXML public void onKategorieLoeschen()               { kategorieCrud.delete(); }

    @FXML public void onStationslagerTabSelected(Event e) { if (istAktiv(e)) lagerCrud.load(); }
    @FXML public void onLagerAnlegen()                    { lagerCrud.create(); }
    @FXML public void onLagerBearbeiten()                 { lagerCrud.edit(); }
    @FXML public void onLagerLoeschen()                   { lagerCrud.delete(); }

    @FXML public void onLieferantenTabSelected(Event e)   { if (istAktiv(e)) lieferantCrud.load(); }
    @FXML public void onLieferantAnlegen()                { lieferantCrud.create(); }
    @FXML public void onLieferantBearbeiten()             { lieferantCrud.edit(); }
    @FXML public void onLieferantLoeschen()               { lieferantCrud.delete(); }

    @FXML public void onBestellungenTabSelected(Event e)  { if (istAktiv(e)) bestellungCrud.load(); }
    @FXML public void onBestellungAnlegen()               { bestellungCrud.create(); }
    @FXML public void onBestellungBearbeiten()            { bestellungCrud.edit(); }
    @FXML public void onBestellungLoeschen()              { bestellungCrud.delete(); }

    @FXML public void onBewegungTabSelected(Event e)      { if (istAktiv(e)) bewegungCrud.load(); }
    @FXML public void onBewegungAnlegen()                 { bewegungCrud.create(); }
    @FXML public void onBewegungBearbeiten()              { bewegungCrud.edit(); }
    @FXML public void onBewegungLoeschen()                { bewegungCrud.delete(); }

    /** true, wenn der ausloesende Reiter gerade aktiv geworden ist. */
    private boolean istAktiv(Event e) {
        return ((Tab) e.getSource()).isSelected();
    }
}
                                                            