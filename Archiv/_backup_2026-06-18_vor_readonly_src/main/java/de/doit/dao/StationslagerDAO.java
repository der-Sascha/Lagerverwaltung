package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Stationslager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationslagerDAO implements GenericDAO<Stationslager> {

    // === Stufe 2 — ANLEGEN (Objekt → DB-INSERT) ===
    @Override
    public Stationslager create(Stationslager lager) throws SQLException {
        String sql = "INSERT INTO stationslager (name, standort, typ) VALUES (?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, lager.getName());
            anweisung.setString(2, lager.getStandort());
            anweisung.setString(3, lager.getTyp());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    lager.setId(schluessel.getInt(1));
                }
            }
        }
        return lager;
    }

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

    // === Stufe 3 — BEARBEITEN (Objekt → DB-UPDATE) ===
    @Override
    public void update(Stationslager lager) throws SQLException {
        String sql = "UPDATE stationslager SET name = ?, standort = ?, typ = ? "
                + "WHERE lager_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, lager.getName());
            anweisung.setString(2, lager.getStandort());
            anweisung.setString(3, lager.getTyp());
            anweisung.setInt(4, lager.getId());
            anweisung.executeUpdate();
        }
    }

    // === Stufe 4 — LÖSCHEN (ID → DB-DELETE) ===
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM stationslager WHERE lager_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }

    private Stationslager zeileLesen(ResultSet datensatz) throws SQLException {
        return new Stationslager(
                datensatz.getInt("lager_id"),
                datensatz.getString("name"),
                datensatz.getString("standort"),
                datensatz.getString("typ"));
    }
}


