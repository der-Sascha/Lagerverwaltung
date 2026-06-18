package de.doit.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generische DAO-Schnittstelle (Data Access Object).
 *
 * <p>Definiert den gemeinsamen CRUD-Vertrag, den alle DAO-Klassen des Projekts
 * erfuellen. Ein Interface enthaelt nur die Methodenkoepfe (den "Vertrag"),
 * keine Implementierung. Jede Klasse, die {@code implements GenericDAO<T>}
 * schreibt, MUSS diese vier Methoden ausprogrammieren &ndash; der Compiler
 * erzwingt das.</p>
 *
 * <p>Der Typ-Platzhalter {@code <T>} (engl. <em>Type</em>) macht die Schnittstelle
 * generisch: Er wird erst beim {@code implements} mit einer konkreten Klasse
 * gefuellt, z.&nbsp;B. {@code MaterialDAO implements GenericDAO<Material>}. So
 * genuegt eine einzige Schnittstelle fuer alle sechs Tabellen-DAOs.</p>
 *
 * <p>Bezug zu Wertetypen/Referenztypen: {@code T} steht immer fuer einen
 * Referenztyp (ein Objekt, z.&nbsp;B. {@code Material}); die {@code id} in
 * {@link #delete(int)} ist ein Wertetyp ({@code int}). {@link java.util.List}
 * ist eine Collection und nimmt mehrere Referenzen desselben Typs auf.</p>
 *
 * @param <T> der Modell-/Entitaetstyp, den das DAO verwaltet (Referenztyp)
 */
public interface GenericDAO<T> {

    // === Stufe 2 — ANLEGEN (Vertrag) ===
    /** Legt einen neuen Datensatz an und gibt das Objekt inkl. erzeugter ID zurueck. */
    T create(T obj) throws SQLException;

    // === Stufe 1 — LESEN (Vertrag) ===
    /** Liest alle Datensaetze als Collection (Liste von Referenzen). */
    List<T> findAll() throws SQLException;

    // === Stufe 3 — BEARBEITEN (Vertrag) ===
    /** Aktualisiert einen vorhandenen Datensatz anhand seiner ID. */
    void update(T obj) throws SQLException;

    // === Stufe 4 — LÖSCHEN (Vertrag) ===
    /** Loescht den Datensatz mit der uebergebenen ID (Wertetyp int). */
    void delete(int id) throws SQLException;
}
