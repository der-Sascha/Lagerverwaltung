package de.doit.controller.crud;

import de.doit.dao.LeseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;

/**
 * Gemeinsame Grundlage fuer einen Reiter, der eine Tabelle anzeigt (read-only).
 *
 * <p>Diese Basisklasse uebernimmt das Laden der Daten in die Tabelle &ndash;
 * derselbe Ablauf fuer alle sechs Reiter (Material, Kategorie, Stationslager,
 * Lieferant, Bestellung, Bestandsbewegung). Sie nutzt dafuer nur die
 * Lese-Schnittstelle {@link LeseDAO}.</p>
 *
 * <p><b>Lern-Stand 2026-06-18:</b> Die Anwendung ist bewusst read-only.
 * Das Schreiben (Anlegen/Bearbeiten/Loeschen) traegst du fuer Material und
 * Kategorie selbst nach &ndash; siehe {@code MD/Anleitung_CRUD_eintragen.md}.
 * Dort entsteht je eine Unterklasse {@code MaterialCrud}/{@code KategorieCrud},
 * die von dieser Basis erbt und die Schreib-Methoden ergaenzt.</p>
 *
 * @param <T> die Modellklasse, die der Reiter anzeigt
 */
public class EntityCrud<T> {

    protected final TableView<T>      tabelle;
    protected final ObservableList<T> liste = FXCollections.observableArrayList();
    protected final LeseDAO<T>        dao;
    protected final String            bezeichnung;

    // Konstruktor
    public EntityCrud(TableView<T> tabelle, LeseDAO<T> dao, String bezeichnung) {
        this.tabelle     = tabelle;
        this.dao         = dao;
        this.bezeichnung = bezeichnung;
        tabelle.setItems(liste);
    }

    // === Stufe 1 — LESEN (Liste → Tabelle) ===
    /** Laedt alle Datensaetze neu aus der Datenbank in die Tabelle. */
    public void load() {
        try {
            liste.setAll(dao.findAll());
        } catch (SQLException e) {
            Dialoge.fehler("Laden fehlgeschlagen", e);
        }
    }
}
