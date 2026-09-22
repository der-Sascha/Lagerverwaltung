package de.doit.controller;

import de.doit.controller.crud.*;
import de.doit.dao.*;
import de.doit.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainController {

    //  BESTANDSÜBERSICHT
    @FXML private TextField                         txtSuche;
    @FXML private ComboBox<Stationslager>           comboBoxLager;
    @FXML private Label                             lblWarnungAnzahl;
    @FXML private TableView<BestandView>            tabBestandsuebersicht;
    // ich bin eine Tabellenspalte und (generics) alles aus dem Objekt BestandView und gebe es als String wieder :)
    @FXML private TableColumn<BestandView, String>  colMaterial;
    @FXML private TableColumn<BestandView, String>  colEinheit;
    @FXML private TableColumn<BestandView, String>  colLager;
    @FXML private TableColumn<BestandView, Integer> colBestand;
    @FXML private TableColumn<BestandView, Integer> colMindest;
    @FXML private TableColumn<BestandView, String>  colStatus;

    //  MATERIALIEN
    @FXML private TableView<Material>            tabMaterialien;
    @FXML private TableColumn<Material, String>  colMatName;
    @FXML private TableColumn<Material, String>  colMatEinheit;
    @FXML private TableColumn<Material, Integer> colMatMindest;
    @FXML private TableColumn<Material, Integer> colMatKategorie;

    //  KATEGORIEN
    @FXML private TableView<Kategorie>           tabKategorien;
    @FXML private TableColumn<Kategorie, String> colKatName;
    @FXML private TableColumn<Kategorie, String> colKatBeschr;

    //  STATIONSLAGER
    @FXML private TableView<Stationslager>           tabLager;
    @FXML private TableColumn<Stationslager, String> colLagerName;
    @FXML private TableColumn<Stationslager, String> colLagerStandort;
    @FXML private TableColumn<Stationslager, String> colLagerTyp;

    //  LIEFERANTEN
    @FXML private TableView<Lieferant>           tabLieferanten;
    @FXML private TableColumn<Lieferant, String> colLiefName;
    @FXML private TableColumn<Lieferant, String> colLiefKontakt;
    @FXML private TableColumn<Lieferant, String> colLiefTelefon;
    @FXML private TableColumn<Lieferant, String> colLiefEmail;

    // BESTELLUNGEN
    @FXML private TableView<Bestellung>              tabBestellungen;
    @FXML private TableColumn<Bestellung, Integer>   colBestMat;
    @FXML private TableColumn<Bestellung, Integer>   colBestLief;
    @FXML private TableColumn<Bestellung, Integer>   colBestLager;
    @FXML private TableColumn<Bestellung, Integer>   colBestMenge;
    @FXML private TableColumn<Bestellung, LocalDate> colBestBestell;
    @FXML private TableColumn<Bestellung, LocalDate> colBestLiefer;
    @FXML private TableColumn<Bestellung, String>    colBestStatus;

    //  BESTANDSBEWEGUNGEN
    @FXML private TableView<Bestandsbewegung>                  tabBewegungen;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewMat;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewLager;
    @FXML private TableColumn<Bestandsbewegung, BewegungsTyp>  colBewTyp;
    @FXML private TableColumn<Bestandsbewegung, Integer>       colBewMenge;
    @FXML private TableColumn<Bestandsbewegung, LocalDateTime> colBewDatum;
    @FXML private TableColumn<Bestandsbewegung, LocalDate>     colBewAblauf;
    @FXML private TableColumn<Bestandsbewegung, String>        colBewBem;

    // geht ohne Konstruktor weil beim erzeugen der DAOs keine Parameter benötigt werden
    private final MaterialDAO         materialDao   = new MaterialDAO();
    private final KategorieDAO        kategorieDao  = new KategorieDAO();
    private final StationslagerDAO stationslagerDao = new StationslagerDAO();
    private final LieferantDAO        lieferantDao  = new LieferantDAO();
    private final BestellungDAO       bestellungDao = new BestellungDAO();
    private final BestandsbewegungDAO bewegungDao   = new BestandsbewegungDAO();

    //  Reiter-Lader je Tabelle (gemeinsame generische Basis EntityCrud<T>, jetzt mit Anlegen/Bearbeiten/Löschen)
    private EntityCrud<Material>         materialCrud;
    private EntityCrud<Kategorie>        kategorieCrud;
    private EntityCrud<Stationslager> stationslagerCrud;
    private EntityCrud<Lieferant>        lieferantCrud;
    private EntityCrud<Bestellung>       bestellungCrud;
    private EntityCrud<Bestandsbewegung> bewegungCrud;

    //  Bestand (berechnete Sicht, bleibt hier)
    private final ObservableList<BestandView> bestandListe = FXCollections.observableArrayList();
    private List<BestandView> alleBestaende;


    @FXML
    // wird automatisch einmalig ausgeführt sobald die Oberfläche bereit ist
    public void initialize() {
        // Spalten der Tabellen mit den Model-Eigenschaften verbinden
        colMaterial.setCellValueFactory(new PropertyValueFactory<BestandView, String>("materialName"));
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

        // tabMaterialien ist der Klick aus FXML und materialDAO die DB Zugriffe usw...
        // in den Diamand Operator werden die Objekte weitergegeben
        materialCrud      = new EntityCrud<>(tabMaterialien, materialDao);
        kategorieCrud     = new EntityCrud<>(tabKategorien, kategorieDao);
        stationslagerCrud = new EntityCrud<>(tabLager, stationslagerDao);
        lieferantCrud     = new EntityCrud<>(tabLieferanten, lieferantDao);
        bestellungCrud    = new EntityCrud<>(tabBestellungen, bestellungDao);
        bewegungCrud      = new EntityCrud<>(tabBewegungen, bewegungDao);

        // wird sofort bei start gebraucht, das was schon steht und gleich in Farbe
        // alles andere mit "beim Klicken" -> muss mit "on" anfangen
        tabBestandsuebersicht.setItems(bestandListe);
        tabBestandsuebersicht.setRowFactory(tv -> new TableRow<BestandView>() {
            @Override protected void updateItem(BestandView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setStyle("");
                else if (item.isWarnung()) setStyle("-fx-background-color: #FADBD8;");
                else                       setStyle("");
            }
        });

        try {
            comboBoxLager.getItems().add(null);
            comboBoxLager.getItems().addAll(stationslagerDao.findAll());
            ladeBestaende();
        } catch (SQLException e) {
            Dialoge.zeigeFehlerfenster("Laden fehlgeschlagen", e);
        }
    }


    private void ladeBestaende() throws SQLException {
        alleBestaende = bewegungDao.findBestandViews();
        anzeigeFiltern();
    }

    private void anzeigeFiltern() {
        if (alleBestaende == null) return;
        String suchtext = txtSuche.getText() == null ? "" : txtSuche.getText().trim().toLowerCase();
        Stationslager gewaehltesLager = comboBoxLager.getValue();
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
        try { ladeBestaende(); } catch (SQLException e) { Dialoge.zeigeFehlerfenster("Aktualisieren fehlgeschlagen", e); }
    }

    @FXML public void onBestandTabSelected(Event e) {
        if (((Tab) e.getSource()).isSelected())
            try { ladeBestaende(); } catch (SQLException ex) { Dialoge.zeigeFehlerfenster("Laden fehlgeschlagen", ex); }
    }

    @FXML public void onZuruecksetzen() {
        txtSuche.clear();
        comboBoxLager.getSelectionModel().clearSelection();
        anzeigeFiltern();
    }


    @FXML public void onMaterialienTabSelected(Event e)   { if (istAktiv(e)) materialCrud.load(); }
    @FXML public void onKategorienTabSelected(Event e)    { if (istAktiv(e)) kategorieCrud.load(); }
    @FXML public void onStationslagerTabSelected(Event e) { if (istAktiv(e)) stationslagerCrud.load(); }
    @FXML public void onLieferantenTabSelected(Event e)   { if (istAktiv(e)) lieferantCrud.load(); }
    @FXML public void onBestellungenTabSelected(Event e)  { if (istAktiv(e)) bestellungCrud.load(); }
    @FXML public void onBewegungTabSelected(Event e)      { if (istAktiv(e)) bewegungCrud.load(); }

    /** true, wenn der ausloesende Reiter gerade aktiv geworden ist. */
    private boolean istAktiv(Event e) {
        return ((Tab) e.getSource()).isSelected();
    }


    // ==================== MATERIAL ====================

    @FXML public void onMaterialAnlegen() {
        materialCrud.anlegen(() -> Dialoge.formular("Material anlegen",
                List.of("Name", "Einheit", "Mindestbestand", "Kategorie-ID"),
                List.of("", "", "0", "1"))
                .map(w -> new Material(w.get(0), w.get(1),
                        Integer.parseInt(w.get(2)), Integer.parseInt(w.get(3)))));
    }

    @FXML public void onMaterialBearbeiten() {
        Material markiert = tabMaterialien.getSelectionModel().getSelectedItem();
        materialCrud.bearbeiten(markiert, m -> Dialoge.formular("Material bearbeiten",
                List.of("Name", "Einheit", "Mindestbestand", "Kategorie-ID"),
                List.of(m.getName(), m.getEinheit(),
                        String.valueOf(m.getMindestbestand()), String.valueOf(m.getKategorieId())))
                .map(w -> {
                    m.setName(w.get(0));
                    m.setEinheit(w.get(1));
                    m.setMindestbestand(Integer.parseInt(w.get(2)));
                    m.setKategorieId(Integer.parseInt(w.get(3)));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onMaterialLoeschen() {
        Material markiert = tabMaterialien.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Kein Material gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Material löschen",
                "\"" + markiert.getName() + "\" wirklich löschen?");
        if (ok) materialCrud.loesche(markiert.getId());
    }


    // ==================== KATEGORIE ====================

    @FXML public void onKategorieAnlegen() {
        kategorieCrud.anlegen(() -> Dialoge.formular("Kategorie anlegen",
                List.of("Name", "Beschreibung"), List.of("", ""))
                .map(w -> new Kategorie(w.get(0), w.get(1))));
    }

    @FXML public void onKategorieBearbeiten() {
        Kategorie markiert = tabKategorien.getSelectionModel().getSelectedItem();
        kategorieCrud.bearbeiten(markiert, k -> Dialoge.formular("Kategorie bearbeiten",
                List.of("Name", "Beschreibung"), List.of(k.getName(), k.getBeschreibung()))
                .map(w -> {
                    k.setName(w.get(0));
                    k.setBeschreibung(w.get(1));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onKategorieLoeschen() {
        Kategorie markiert = tabKategorien.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Keine Kategorie gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Kategorie löschen",
                "\"" + markiert.getName() + "\" wirklich löschen?");
        if (ok) kategorieCrud.loesche(markiert.getId());
    }


    // ==================== STATIONSLAGER ====================

    @FXML public void onStationslagerAnlegen() {
        stationslagerCrud.anlegen(() -> Dialoge.formular("Lager anlegen",
                List.of("Name", "Standort", "Typ"), List.of("", "", ""))
                .map(w -> new Stationslager(w.get(0), w.get(1), w.get(2))));
    }

    @FXML public void onStationslagerBearbeiten() {
        Stationslager markiert = tabLager.getSelectionModel().getSelectedItem();
        stationslagerCrud.bearbeiten(markiert, s -> Dialoge.formular("Lager bearbeiten",
                List.of("Name", "Standort", "Typ"), List.of(s.getName(), s.getStandort(), s.getTyp()))
                .map(w -> {
                    s.setName(w.get(0));
                    s.setStandort(w.get(1));
                    s.setTyp(w.get(2));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onStationslagerLoeschen() {
        Stationslager markiert = tabLager.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Kein Lager gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Lager löschen",
                "\"" + markiert.getName() + "\" wirklich löschen?");
        if (ok) stationslagerCrud.loesche(markiert.getId());
    }


    // ==================== LIEFERANT ====================

    @FXML public void onLieferantAnlegen() {
        lieferantCrud.anlegen(() -> Dialoge.formular("Lieferant anlegen",
                List.of("Name", "Kontakt", "Telefon", "E-Mail"), List.of("", "", "", ""))
                .map(w -> new Lieferant(w.get(0), w.get(1), w.get(2), w.get(3))));
    }

    @FXML public void onLieferantBearbeiten() {
        Lieferant markiert = tabLieferanten.getSelectionModel().getSelectedItem();
        lieferantCrud.bearbeiten(markiert, l -> Dialoge.formular("Lieferant bearbeiten",
                List.of("Name", "Kontakt", "Telefon", "E-Mail"),
                List.of(l.getName(), l.getKontakt(), l.getTelefon(), l.getEmail()))
                .map(w -> {
                    l.setName(w.get(0));
                    l.setKontakt(w.get(1));
                    l.setTelefon(w.get(2));
                    l.setEmail(w.get(3));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onLieferantLoeschen() {
        Lieferant markiert = tabLieferanten.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Kein Lieferant gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Lieferant löschen",
                "\"" + markiert.getName() + "\" wirklich löschen?");
        if (ok) lieferantCrud.loesche(markiert.getId());
    }


    // ==================== BESTELLUNG ====================

    @FXML public void onBestellungAnlegen() {
        bestellungCrud.anlegen(() -> Dialoge.formular("Bestellung anlegen",
                List.of("Material-ID", "Lieferant-ID", "Lager-ID", "Menge",
                        "Bestelldatum (JJJJ-MM-TT)", "Lieferdatum (JJJJ-MM-TT, leer=offen)", "Status"),
                List.of("1", "1", "1", "1", LocalDate.now().toString(), "", "offen"))
                .map(w -> new Bestellung(
                        Integer.parseInt(w.get(0)), Integer.parseInt(w.get(1)), Integer.parseInt(w.get(2)),
                        Integer.parseInt(w.get(3)), LocalDate.parse(w.get(4)),
                        w.get(5).isBlank() ? null : LocalDate.parse(w.get(5)),
                        w.get(6))));
    }

    @FXML public void onBestellungBearbeiten() {
        Bestellung markiert = tabBestellungen.getSelectionModel().getSelectedItem();
        bestellungCrud.bearbeiten(markiert, b -> Dialoge.formular("Bestellung bearbeiten",
                List.of("Material-ID", "Lieferant-ID", "Lager-ID", "Menge",
                        "Bestelldatum (JJJJ-MM-TT)", "Lieferdatum (JJJJ-MM-TT, leer=offen)", "Status"),
                List.of(String.valueOf(b.getMaterialId()), String.valueOf(b.getLieferantId()),
                        String.valueOf(b.getLagerId()), String.valueOf(b.getMenge()),
                        b.getBestelldatum().toString(),
                        b.getLieferdatum() == null ? "" : b.getLieferdatum().toString(), b.getStatus()))
                .map(w -> {
                    b.setMaterialId(Integer.parseInt(w.get(0)));
                    b.setLieferantId(Integer.parseInt(w.get(1)));
                    b.setLagerId(Integer.parseInt(w.get(2)));
                    b.setMenge(Integer.parseInt(w.get(3)));
                    b.setBestelldatum(LocalDate.parse(w.get(4)));
                    b.setLieferdatum(w.get(5).isBlank() ? null : LocalDate.parse(w.get(5)));
                    b.setStatus(w.get(6));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onBestellungLoeschen() {
        Bestellung markiert = tabBestellungen.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Keine Bestellung gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Bestellung löschen",
                "Bestellung Nr. " + markiert.getId() + " wirklich löschen?");
        if (ok) bestellungCrud.loesche(markiert.getId());
    }


    // ==================== BESTANDSBEWEGUNG ====================

    @FXML public void onBewegungAnlegen() {
        bewegungCrud.anlegen(() -> Dialoge.formular("Bestandsbewegung anlegen",
                List.of("Material-ID", "Lager-ID", "Typ (EINGANG/AUSGANG)", "Menge",
                        "Ablaufdatum (JJJJ-MM-TT, optional)", "Bemerkung"),
                List.of("1", "1", "EINGANG", "1", "", ""))
                .map(w -> new Bestandsbewegung(
                        Integer.parseInt(w.get(0)), Integer.parseInt(w.get(1)),
                        BewegungsTyp.valueOf(w.get(2).trim().toUpperCase()),
                        Integer.parseInt(w.get(3)),
                        w.get(4).isBlank() ? null : LocalDate.parse(w.get(4)),
                        LocalDateTime.now(),
                        w.get(5))));
    }

    @FXML public void onBewegungBearbeiten() {
        Bestandsbewegung markiert = tabBewegungen.getSelectionModel().getSelectedItem();
        // Hinweis: datum (Buchungszeitpunkt) bleibt unveraendert - Bestandsbewegung hat keinen Setter dafuer
        bewegungCrud.bearbeiten(markiert, bw -> Dialoge.formular("Bestandsbewegung bearbeiten",
                List.of("Material-ID", "Lager-ID", "Typ (EINGANG/AUSGANG)", "Menge",
                        "Ablaufdatum (JJJJ-MM-TT, optional)", "Bemerkung"),
                List.of(String.valueOf(bw.getMaterialId()), String.valueOf(bw.getLagerId()),
                        bw.getBewegungstyp().name(), String.valueOf(bw.getMenge()),
                        bw.getAblaufdatum() == null ? "" : bw.getAblaufdatum().toString(), bw.getBemerkung()))
                .map(w -> {
                    bw.setMaterialId(Integer.parseInt(w.get(0)));
                    bw.setLagerId(Integer.parseInt(w.get(1)));
                    bw.setBewegungstyp(BewegungsTyp.valueOf(w.get(2).trim().toUpperCase()));
                    bw.setMenge(Integer.parseInt(w.get(3)));
                    bw.setAblaufdatum(w.get(4).isBlank() ? null : LocalDate.parse(w.get(4)));
                    bw.setBemerkung(w.get(5));
                    return true;
                }).orElse(false));
    }

    @FXML
    public void onBewegungLoeschen() {
        Bestandsbewegung markiert = tabBewegungen.getSelectionModel().getSelectedItem();
        if (markiert == null) {
            Dialoge.hinweis("Keine Bewegung gewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        boolean ok = Dialoge.bestaetigen("Bestandsbewegung löschen",
                "Bestandsbewegung Nr. " + markiert.getId() + " wirklich löschen?");
        if (ok) bewegungCrud.loesche(markiert.getId());
    }
}
