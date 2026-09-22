package de.doit.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    // ANLEGEN: Objekt (ohne ID) -> DB-INSERT, Objekt bekommt die generierte ID zurueck
    T create(T objekt) throws SQLException;

    // LESEN: alle Zeilen der Tabelle als Liste von Objekten
    List<T> findAll() throws SQLException;

    // BEARBEITEN: Objekt (mit ID) -> DB-UPDATE
    void update(T objekt) throws SQLException;

    // LOESCHEN: ID -> DB-DELETE
    void delete(int id) throws SQLException;
}
