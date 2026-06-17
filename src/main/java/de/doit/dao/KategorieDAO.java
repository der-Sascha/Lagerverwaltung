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
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, kategorie.getName());
            ps.setString(2, kategorie.getBeschreibung());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    kategorie.setId(keys.getInt(1));
                }
            }
        }
        return kategorie;
    }

    @Override
    public List<Kategorie> findAll() throws SQLException {
        String sql = "SELECT kategorie_id, name, beschreibung FROM kategorien "
                + "ORDER BY name";
        List<Kategorie> result = new ArrayList<>();
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
    public void update(Kategorie kategorie) throws SQLException {
        String sql = "UPDATE kategorien SET name = ?, beschreibung = ? "
                + "WHERE kategorie_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kategorie.getName());
            ps.setString(2, kategorie.getBeschreibung());
            ps.setInt(3, kategorie.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kategorien WHERE kategorie_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Kategorie mapRow(ResultSet rs) throws SQLException {
        return new Kategorie(
                rs.getInt("kategorie_id"),
                rs.getString("name"),
                rs.getString("beschreibung"));
    }
}
