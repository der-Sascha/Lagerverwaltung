package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Bestellung;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BestellungDAO {

    public Bestellung create(Bestellung bestellung) throws SQLException {
        String sql = "INSERT INTO bestellungen (material_id, lieferant_id, lager_id, "
                + "menge, bestelldatum, lieferdatum, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bestellung.getMaterialId());
            ps.setInt(2, bestellung.getLieferantId());
            ps.setInt(3, bestellung.getLagerId());
            ps.setInt(4, bestellung.getMenge());
            ps.setDate(5, Date.valueOf(bestellung.getBestelldatum()));
            if (bestellung.getLieferdatum() == null) {
                ps.setNull(6, java.sql.Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(bestellung.getLieferdatum()));
            }
            ps.setString(7, bestellung.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    bestellung.setId(keys.getInt(1));
                }
            }
        }
        return bestellung;
    }

//    public Bestellung findById(int id) throws SQLException {
//        String sql = "SELECT bestellung_id, material_id, lieferant_id, lager_id, "
//                + "menge, bestelldatum, lieferdatum, status "
//                + "FROM bestellungen WHERE bestellung_id = ?";
//        Connection conn = DBConnection.getConnection();
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return mapRow(rs);
//                }
//            }
//        }
//        return null;
//    }

    public List<Bestellung> findAll() throws SQLException {
        String sql = "SELECT bestellung_id, material_id, lieferant_id, lager_id, "
                + "menge, bestelldatum, lieferdatum, status "
                + "FROM bestellungen ORDER BY bestelldatum DESC";
        List<Bestellung> result = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        }
        return result;
    }

    public void update(Bestellung bestellung) throws SQLException {
        String sql = "UPDATE bestellungen SET material_id = ?, lieferant_id = ?, "
                + "lager_id = ?, menge = ?, bestelldatum = ?, lieferdatum = ?, "
                + "status = ? WHERE bestellung_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bestellung.getMaterialId());
            ps.setInt(2, bestellung.getLieferantId());
            ps.setInt(3, bestellung.getLagerId());
            ps.setInt(4, bestellung.getMenge());
            ps.setDate(5, Date.valueOf(bestellung.getBestelldatum()));
            if (bestellung.getLieferdatum() == null) {
                ps.setNull(6, java.sql.Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(bestellung.getLieferdatum()));
            }
            ps.setString(7, bestellung.getStatus());
            ps.setInt(8, bestellung.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM bestellungen WHERE bestellung_id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Bestellung mapRow(ResultSet rs) throws SQLException {
        Date sqlBestelldatum = rs.getDate("bestelldatum");
        Date sqlLieferdatum  = rs.getDate("lieferdatum");
        LocalDate bestelldatum = sqlBestelldatum == null ? null : sqlBestelldatum.toLocalDate();
        LocalDate lieferdatum  = sqlLieferdatum  == null ? null : sqlLieferdatum.toLocalDate();
        return new Bestellung(
                rs.getInt("bestellung_id"),
                rs.getInt("material_id"),
                rs.getInt("lieferant_id"),
                rs.getInt("lager_id"),
                rs.getInt("menge"),
                bestelldatum,
                lieferdatum,
                rs.getString("status"));
    }
}
