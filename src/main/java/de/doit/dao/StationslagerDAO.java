package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Stationslager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationslagerDAO implements LeseDAO<Stationslager> {

    // === Stufe 1 — LESEN (DB-SELECT → Liste) ===
    @Override
    public List<Stationslager> findAll() throws SQLException {
        String sql = "SELECT lager_id, name, standort, typ FROM stationslager "
                + "ORDER BY name";
        List<Stationslager> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) {
                ergebnis.add(zeileLesen(datensatz));
            }
        }
        return ergebnis;
    }

    private Stationslager zeileLesen(ResultSet datensatz) throws SQLException {
        return new Stationslager(
                datensatz.getInt("lager_id"),
                datensatz.getString("name"),
                datensatz.getString("standort"),
                datensatz.getString("typ"));
    }
}


