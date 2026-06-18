package de.doit.dao;

import java.sql.SQLException;

/**
 * Generische DAO-Schnittstelle (Data Access Object) fuer das volle CRUD.
 *
 * <p>Erweitert {@link LeseDAO} (Lesen) um das Schreiben: Anlegen, Aendern,
 * Loeschen. Ein DAO, das alle vier Operationen anbietet, schreibt
 * {@code implements GenericDAO<T>}; ein reines Lese-DAO schreibt nur
 * {@code implements LeseDAO<T>}.</p>
 *
 * <p>Der Typ-Platzhalter {@code <T>} (engl. <em>Type</em>) macht die
 * Schnittstelle generisch: Er wird erst beim {@code implements} mit einer
 * konkreten Klasse gefuellt, z.&nbsp;B. {@code MaterialDAO implements
 * GenericDAO<Material>}.</p>
 *
 * <p><b>Hinweis (Lern-Stand 2026-06-18):</b> Aktuell ist die Anwendung
 * read-only ausgeliefert &ndash; alle DAOs implementieren nur {@link LeseDAO}.
 * Diese Schnittstelle beschreibt den vollen CRUD-Vertrag, den du beim
 * Eintragen des Schreib-Codes (siehe {@code MD/Anleitung_CRUD_eintragen.md})
 * fuer Material und Kategorie nutzt.</p>
 *
 * @param <T> der Modell-/Entitaetstyp, den das DAO verwaltet (Referenztyp)
 */
public interface GenericDAO<T> extends LeseDAO<T> {

    // === Stufe 2 — ANLEGEN (Vertrag) ===
    /** Legt einen neuen Datensatz an und gibt das Objekt inkl. erzeugter ID zurueck. */
    T create(T obj) throws SQLException;

    // === Stufe 3 — BEARBEITEN (Vertrag) ===
    /** Aktualisiert einen vorhandenen Datensatz anhand seiner ID. */
    void update(T obj) throws SQLException;

    // === Stufe 4 — LÖSCHEN (Vertrag) ===
    /** Loescht den Datensatz mit der uebergebenen ID (Wertetyp int). */
    void delete(int id) throws SQLException;
}
