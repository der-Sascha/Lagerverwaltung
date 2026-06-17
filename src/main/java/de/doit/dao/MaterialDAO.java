package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO implements GenericDAO<Material> {

    @Override
    public Material create(Material material) throws SQLException {
        String sql = "INSERT INTO materialien (name, einheit, mindestbestand, kategorie_id) "
                + "VALUES (?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, material.getName());
            anweisung.setString(2, material.getEinheit());
            anweisung.setInt(3, material.getMindestbestand());
            anweisung.setInt(4, material.getKategorieId());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    material.setId(schluessel.getInt(1));
                }
            }
        }
        return material;
    }

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

    @Override
    public void update(Material material) throws SQLException {
        String sql = "UPDATE materialien SET name = ?, einheit = ?, mindestbestand = ?, "
                + "kategorie_id = ? WHERE material_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, material.getName());
            anweisung.setString(2, material.getEinheit());
            anweisung.setInt(3, material.getMindestbestand());
            anweisung.setInt(4, material.getKategorieId());
            anweisung.setInt(5, material.getId());
            anweisung.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM materialien WHERE material_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
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
