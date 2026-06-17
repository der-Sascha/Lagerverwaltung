package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Stationslager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationslagerDAO implements GenericDAO<Stationslager> {

    @Override
    public Stationslager create(Stationslager lager) throws SQLException {
        String sql = "INSERT INTO stationslager (name, standort, typ) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, lager.getName());
            ps.setString(2, lager.getStandort());
            ps.setString(3, lager.getTyp());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    lager.setId(keys.getInt(1));
                }
            }
        }
        return lager;
    }

    @Override
    public List<Stationslager> findAll() throws SQLException {
        String sql = "SELECT lager_id, name, standort, typ FROM stationslager "
                + "ORDER BY name";
        List<Stationslager> result = new ArrayList<>();
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
    public void update(Stationslager lager) throws SQLException {
        String sql = "UPDATE stationslager SET name = ?, standort = ?, typ = ? "
                + "WHERE lager_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lager.getName());
            ps.setString(2, lager.getStandort());
            ps.setString(3, lager.getTyp());
            ps.setInt(4, lager.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM stationslager WHERE lager_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Stationslager mapRow(ResultSet rs) throws SQLException {
        return new Stationslager(
                rs.getInt("lager_id"),
                rs.getString("name"),
                rs.getString("standort"),
                rs.getString("typ"));
    }
}


