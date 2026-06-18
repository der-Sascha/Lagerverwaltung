package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO implements LeseDAO<Material> {

    // === Stufe 1 — LESEN (DB-SELECT → Liste) ===
    @Override
    public List<Material> findAll() throws SQLException {
        String sql = "SELECT material_id, name, einheit, mindestbestand, kategorie_id "
                + "FROM materialien ORDER BY name";
        List<Material> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) {
                ergebnis.add(zeileLesen(datensatz));
            }
        }
        return ergebnis;
    }

    private Material zeileLesen(ResultSet datensatz) throws SQLException {
        return new Material(
                datensatz.getInt("material_id"),
                datensatz.getString("name"),
                datensatz.getString("einheit"),
                datensatz.getInt("mindestbestand"),
                datensatz.getInt("kategorie_id"));
    }
}
