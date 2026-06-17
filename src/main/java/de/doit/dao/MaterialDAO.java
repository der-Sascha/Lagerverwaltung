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
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, material.getName());
            ps.setString(2, material.getEinheit());
            ps.setInt(3, material.getMindestbestand());
            ps.setInt(4, material.getKategorieId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    material.setId(keys.getInt(1));
                }
            }
        }
        return material;
    }

    @Override
    public List<Material> findAll() throws SQLException {
        String sql = "SELECT material_id, name, einheit, mindestbestand, kategorie_id "
                + "FROM materialien ORDER BY name";
        List<Material> result = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    @Override
    public void update(Material material) throws SQLException {
        String sql = "UPDATE materialien SET name = ?, einheit = ?, mindestbestand = ?, "
                + "kategorie_id = ? WHERE material_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, material.getName());
            ps.setString(2, material.getEinheit());
            ps.setInt(3, material.getMindestbestand());
            ps.setInt(4, material.getKategorieId());
            ps.setInt(5, material.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM materialien WHERE material_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Material mapRow(ResultSet rs) throws SQLException {
        return new Material(
                rs.getInt("material_id"),
                rs.getString("name"),
                rs.getString("einheit"),
                rs.getInt("mindestbestand"),
                rs.getInt("kategorie_id"));
    }
}
