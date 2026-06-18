package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Kategorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategorieDAO implements LeseDAO<Kategorie> {

    // === Stufe 1 — LESEN (DB-SELECT → Liste) ===
    @Override
    public List<Kategorie> findAll() throws SQLException {
        String sql = "SELECT kategorie_id, name, beschreibung FROM kategorien "
                + "ORDER BY name";
        List<Kategorie> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) {
                ergebnis.add(zeileLesen(datensatz));
            }
        }
        return ergebnis;
    }

    private Kategorie zeileLesen(ResultSet datensatz) throws SQLException {
        return new Kategorie(
                datensatz.getInt("kategorie_id"),
                datensatz.getString("name"),
                datensatz.getString("beschreibung"));
    }
}
