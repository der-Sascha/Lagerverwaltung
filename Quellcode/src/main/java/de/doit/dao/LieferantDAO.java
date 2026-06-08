package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Lieferant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieferantDAO {

    public Lieferant create(Lieferant lieferant) throws SQLException {
        String sql = "INSERT INTO lieferanten (name, kontakt, telefon, email) "
                + "VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, lieferant.getName());
            ps.setString(2, lieferant.getKontakt());
            ps.setString(3, lieferant.getTelefon());
            ps.setString(4, lieferant.getEmail());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    lieferant.setId(keys.getInt(1));
                }
            }
        }
        return lieferant;
    }

    public Lieferant findById(int id) throws SQLException {
        String sql = "SELECT lieferant_id, name, kontakt, telefon, email FROM lieferanten "
                + "WHERE lieferant_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Lieferant> findAll() throws SQLException {
        String sql = "SELECT lieferant_id, name, kontakt, telefon, email FROM lieferanten "
                + "ORDER BY name";
        List<Lieferant> result = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    public void update(Lieferant lieferant) throws SQLException {
        String sql = "UPDATE lieferanten SET name = ?, kontakt = ?, telefon = ?, email = ? "
                + "WHERE lieferant_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lieferant.getName());
            ps.setString(2, lieferant.getKontakt());
            ps.setString(3, lieferant.getTelefon());
            ps.setString(4, lieferant.getEmail());
            ps.setInt(5, lieferant.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM lieferanten WHERE lieferant_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Lieferant mapRow(ResultSet rs) throws SQLException {
        return new Lieferant(
                rs.getInt("lieferant_id"),
                rs.getString("name"),
                rs.getString("kontakt"),
                rs.getString("telefon"),
                rs.getString("email"));
    }
}

