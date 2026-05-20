package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.BestandView;
import de.doit.model.Bestandsbewegung;
import de.doit.model.BewegungsTyp;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BestandsbewegungDAO {

    public Bestandsbewegung create(Bestandsbewegung b) throws SQLException {
        String sql = "INSERT INTO bestandsbewegungen "
                + "(material_id, lager_id, bewegungstyp, menge, ablaufdatum, datum, bemerkung) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getMaterialId());
            ps.setInt(2, b.getLagerId());
            ps.setString(3, b.getBewegungstyp().name());
            ps.setInt(4, b.getMenge());
            if (b.getAblaufdatum() == null) ps.setNull(5, java.sql.Types.DATE);
            else ps.setDate(5, Date.valueOf(b.getAblaufdatum()));
            ps.setTimestamp(6, Timestamp.valueOf(b.getDatum() == null ? LocalDateTime.now() : b.getDatum()));
            ps.setString(7, b.getBemerkung());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) b.setId(keys.getInt(1));
            }
        }
        return b;
    }

    public Bestandsbewegung findById(int id) throws SQLException {
        String sql = "SELECT bewegung_id, material_id, lager_id, bewegungstyp, "
                + "menge, ablaufdatum, datum, bemerkung "
                + "FROM bestandsbewegungen WHERE bewegung_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public List<Bestandsbewegung> findAll() throws SQLException {
        String sql = "SELECT bewegung_id, material_id, lager_id, bewegungstyp, "
                + "menge, ablaufdatum, datum, bemerkung "
                + "FROM bestandsbewegungen ORDER BY datum DESC";
        List<Bestandsbewegung> result = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(mapRow(rs));
        }
        return result;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM bestandsbewegungen WHERE bewegung_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    public List<BestandView> findBestandViews() throws SQLException {
        String sql =
                "SELECT m.material_id AS material_id, "
                        + "       m.name        AS material_name, "
                        + "       m.einheit     AS einheit, "
                        + "       s.lager_id    AS lager_id, "
                        + "       s.name        AS lager_name, "
                        + "       SUM(CASE WHEN b.bewegungstyp = 'EINGANG' THEN b.menge ELSE -b.menge END) AS bestand, "
                        + "       m.mindestbestand AS mindestbestand "
                        + "FROM bestandsbewegungen b "
                        + "JOIN materialien   m ON m.material_id = b.material_id "
                        + "JOIN stationslager s ON s.lager_id    = b.lager_id "
                        + "GROUP BY b.material_id, b.lager_id, m.name, s.name, m.einheit, m.mindestbestand "
                        + "ORDER BY s.name, m.name";
        List<BestandView> result = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new BestandView(
                        rs.getInt("material_id"),
                        rs.getString("material_name"),
                        rs.getString("einheit"),
                        rs.getInt("lager_id"),
                        rs.getString("lager_name"),
                        rs.getInt("bestand"),
                        rs.getInt("mindestbestand")));
            }
        }
        return result;
    }

    private Bestandsbewegung mapRow(ResultSet rs) throws SQLException {
        Date sqlAblauf = rs.getDate("ablaufdatum");
        LocalDate ablauf = sqlAblauf == null ? null : sqlAblauf.toLocalDate();
        Timestamp ts = rs.getTimestamp("datum");
        LocalDateTime datum = ts == null ? null : ts.toLocalDateTime();
        BewegungsTyp typ = BewegungsTyp.valueOf(rs.getString("bewegungstyp"));
        return new Bestandsbewegung(
                rs.getInt("bewegung_id"),
                rs.getInt("material_id"),
                rs.getInt("lager_id"),
                typ,
                rs.getInt("menge"),
                ablauf,
                datum,
                rs.getString("bemerkung"));
    }
}

