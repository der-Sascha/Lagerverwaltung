package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.modele.Lieferant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieferantDAO implements LeseDAO<Lieferant> {

    // === Stufe 1 — LESEN (DB-SELECT → Liste) ===
    @Override
    public List<Lieferant> findAll() throws SQLException {
        String sql = "SELECT lieferant_id, name, kontakt, telefon, email FROM lieferanten "
                + "ORDER BY name";
        List<Lieferant> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) {
                ergebnis.add(zeileLesen(datensatz));
            }
        }
        return ergebnis;
    }

    private Lieferant zeileLesen(ResultSet datensatz) throws SQLException {
        return new Lieferant(
                datensatz.getInt("lieferant_id"),
                datensatz.getString("name"),
                datensatz.getString("kontakt"),
                datensatz.getString("telefon"),
                datensatz.getString("email"));
    }
}

