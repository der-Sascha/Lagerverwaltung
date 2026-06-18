package de.doit.controller.crud;

import de.doit.dao.GenericDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;

/**
 * Gemeinsame Grundlage fuer das Verwalten einer Tabelle (Anlegen, Bearbeiten,
 * Loeschen, Laden).
 *
 * <p>Alle sechs Reiter mit Stammdaten (Material, Kategorie, Stationslager,
 * Lieferant, Bestellung, Bestandsbewegung) liefen frueher als fast gleicher
 * Code im MainController. Dieser wiederkehrende Ablauf steht jetzt einmal hier.
 * Jede Entitaet bekommt eine eigene kleine Klasse, die von {@code EntityCrud<T>}
 * erbt und nur noch das ergaenzt, was sich unterscheidet: ihre ID
 * ({@link #idVon}) und ihr Eingabeformular ({@link #formularAnzeigen}).</p>
 *
 * <p>Der Typ-Platzhalter {@code <T>} ist die jeweilige Modellklasse
 * (Referenztyp). Der Zugriff auf die Datenbank laeuft ueber die generische
 * Schnittstelle {@link GenericDAO}.</p>
 *
 * @param <T> die Modellklasse, die der Reiter anzeigt
 */
public abstract class EntityCrud<T> {

    protected final TableView<T>      tabelle;
    protected final ObservableList<T> liste = FXCollections.observableArrayList();
    protected final GenericDAO<T>     dao;
    protected final String            bezeichnung;

    // Konstruktor
    protected EntityCrud(TableView<T> tabelle, GenericDAO<T> dao, String bezeichnung) {
        this.tabelle     = tabelle;
        this.dao         = dao;
        this.bezeichnung = bezeichnung;
        tabelle.setItems(liste);
    }


    /** Laedt alle Datensaetze neu aus der Datenbank in die Tabelle. */
    public void load() {
        try {
            liste.setAll(dao.findAll());
        } catch (SQLException e) {
            Dialoge.fehler("Laden fehlgeschlagen", e);
        }
    }

    /** Oeffnet das Formular fuer einen neuen Datensatz. */
    public void create() {
        formularAnzeigen(null);
    }


    /** Oeffnet das Formular fuer den ausgewaehlten Datensatz. */
    public void edit() {
        T sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte " + bezeichnung + " auswählen."); return; }
        formularAnzeigen(sel);
    }


    /** Loescht den ausgewaehlten Datensatz nach Rueckfrage. */
    public void delete() {
        T sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte " + bezeichnung + " auswählen."); return; }
        if (!Dialoge.bestaetigen(bezeichnung + " löschen", bezeichnung + " wirklich löschen?")) return;
        try {
            dao.delete(idVon(sel));
            load();
        } catch (SQLException e) {
            Dialoge.fehler("Löschen fehlgeschlagen", e);
        }
    }

    // Konvention Template-Method-Muster. erst fertige Methoden dann abstrakte Klassen
    /** Liefert die ID eines Datensatzes (fuer das Loeschen). */
    protected abstract int idVon(T obj);


    /**
     * Zeigt das Eingabeformular. Bei {@code vorhanden == null} wird ein neuer
     * Datensatz angelegt, sonst der uebergebene b