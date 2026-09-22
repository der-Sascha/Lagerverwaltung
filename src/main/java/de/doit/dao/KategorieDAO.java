package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Kategorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategorieDAO implements GenericDAO<Kategorie> {

    @Override
    public Kategorie create(Kategorie kategorie) throws SQLException {
        String sql = "INSERT INTO kategorien (name, beschreibung) VALUES (?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, kategorie.getName());
            anweisung.setString(2, kategorie.getBeschreibung());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    kategorie.setId(schluessel.getInt(1));
                }
            }
        }
        return kategorie;
    }

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

    @Override
    public void update(Kategorie kategorie) throws SQLException {
        String sql = "UPDATE kategorien SET name = ?, beschreibung = ? WHERE kategorie_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, kategorie.getName());
            anweisung.setString(2, kategorie.getBeschreibung());
            anweisung.setInt(3, kategorie.getId());
            anweisung.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kategorien WHERE kategorie_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }

    private Kategorie zeileLesen(ResultSet datensatz) throws SQLException {
        return new Kategorie(
                datensatz.getInt("kategorie_id"),
                datensatz.getString("name"),
                datensatz.getString("beschreibung"));
    }
}
