package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Lieferant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieferantDAO implements GenericDAO<Lieferant> {

    // === Stufe 2 — ANLEGEN (Objekt → DB-INSERT) ===
    @Override
    public Lieferant create(Lieferant lieferant) throws SQLException {
        String sql = "INSERT INTO lieferanten (name, kontakt, telefon, email) "
                + "VALUES (?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, lieferant.getName());
            anweisung.setString(2, lieferant.getKontakt());
            anweisung.setString(3, lieferant.getTelefon());
            anweisung.setString(4, lieferant.getEmail());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    lieferant.setId(schluessel.getInt(1));
                }
            }
        }
        return lieferant;
    }

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

    // === Stufe 3 — BEARBEITEN (Objekt → DB-UPDATE) ===
    @Override
    public void update(Lieferant lieferant) throws SQLException {
        String sql = "UPDATE lieferanten SET name = ?, kontakt = ?, telefon = ?, email = ? "
                + "WHERE lieferant_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, lieferant.getName());
            anweisung.setString(2, lieferant.getKontakt());
            anweisung.setString(3, lieferant.getTelefon());
            anweisung.setString(4, lieferant.getEmail());
            anweisung.setInt(5, lieferant.getId());
            anweisung.executeUpdate();
        }
    }

    // === Stufe 4 — LÖSCHEN (ID → DB-DELETE) ===
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM lieferanten WHERE lieferant_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
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

