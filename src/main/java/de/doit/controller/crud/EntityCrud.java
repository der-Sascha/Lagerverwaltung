package de.doit.controller.crud;

import de.doit.dao.GenericDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Generische Basis fuer einen CRUD-Reiter: eine Instanz pro Tabelle/Entitaet T.
 * Der Ablauf (laden, speichern, Fehlerbehandlung) steht hier einmal fuer alle
 * sechs Reiter; wie das Formular aussieht, entscheidet der MainController je Entitaet.
 */
public class EntityCrud<T> {

    protected final TableView<T> tabelle;
    protected final ObservableList<T> liste = FXCollections.observableArrayList();
    protected final GenericDAO<T> dao;

    public EntityCrud(TableView<T> tabelle, GenericDAO<T> dao) {
        this.tabelle = tabelle;
        this.dao = dao;
        tabelle.setItems(liste);
    }

    /** Laedt alle Datensaetze neu aus der Datenbank in die Tabelle. */
    public void load() {
        try {
            liste.setAll(dao.findAll());
        } catch (SQLException e) {
            Dialoge.zeigeFehlerfenster("Laden fehlgeschlagen", e);
        }
    }

    /**
     * ANLEGEN: ruft ersteller auf (zeigt i.d.R. Dialoge.formular an und baut daraus ein T).
     * Optional.empty() = Nutzer hat abgebrochen, dann passiert nichts.
     */
    public void anlegen(Supplier<Optional<T>> ersteller) {
        ersteller.get().ifPresent(neu -> {
            try {
                dao.create(neu);
                load();
            } catch (SQLException e) {
                Dialoge.zeigeFehlerfenster("Anlegen fehlgeschlagen", e);
            }
        });
    }

    /**
     * BEARBEITEN: aktualisierer aendert die Felder von ausgewaehlt (per Formular)
     * und gibt true zurueck, wenn gespeichert werden soll (false = Abbruch).
     */
    public void bearbeiten(T ausgewaehlt, Function<T, Boolean> aktualisierer) {
        if (ausgewaehlt == null) {
            Dialoge.hinweis("Nichts ausgewählt", "Bitte zuerst eine Zeile markieren.");
            return;
        }
        if (Boolean.TRUE.equals(aktualisierer.apply(ausgewaehlt))) {
            try {
                dao.update(ausgewaehlt);
                load();
            } catch (SQLException e) {
                Dialoge.zeigeFehlerfenster("Bearbeiten fehlgeschlagen", e);
            }
        }
    }

    /** LOESCHEN ueber ID (die Rueckfrage macht der Aufrufer vorher). */
    public void loesche(int id) {
        try {
            dao.delete(id);
            load();
        } catch (SQLException e) {
            // SQL-Fehlercode 23000 = Fremdschluessel-Verletzung
            if ("23000".equals(e.getSQLState())) {
                Dialoge.hinweis("Löschen nicht möglich", "Dieser Datensatz wird noch woanders verwendet");
            } else {
                Dialoge.zeigeFehlerfenster("Löschen fehlgeschlagen", e);
            }
        }
    }
}
