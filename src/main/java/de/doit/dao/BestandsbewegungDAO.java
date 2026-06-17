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

public class BestandsbewegungDAO implements GenericDAO<Bestandsbewegung> {

    @Override
    public Bestandsbewegung create(Bestandsbewegung b) throws SQLException {
        String sql = "INSERT INTO bestandsbewegungen "
                + "(material_id, lager_id, bewegungstyp, menge, ablaufdatum, datum, bemerkung) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setInt(1, b.getMaterialId());
            anweisung.setInt(2, b.getLagerId());
            anweisung.setString(3, b.getBewegungstyp().name());
            anweisung.setInt(4, b.getMenge());
            if (b.getAblaufdatum() == null) anweisung.setNull(5, java.sql.Types.DATE);
            else anweisung.setDate(5, Date.valueOf(b.getAblaufdatum()));
            anweisung.setTimestamp(6, Timestamp.valueOf(b.getDatum() == null ? LocalDateTime.now() : b.getDatum()));
            anweisung.setString(7, b.getBemerkung());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) b.setId(schluessel.getInt(1));
            }
        }
        return b;
    }

    @Override
    public List<Bestandsbewegung> findAll() throws SQLException {
        String sql = "SELECT bestandsbewegung_id, material_id, lager_id, bewegungstyp, "
                + "menge, ablaufdatum, datum, bemerkung "
                + "FROM bestandsbewegungen ORDER BY datum DESC";
        List<Bestandsbewegung> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) ergebnis.add(zeileLesen(datensatz));
        }
        return ergebnis;
    }

    @Override
    public void update(Bestandsbewegung b) throws SQLException {
        String sql = "UPDATE bestandsbewegungen SET material_id=?, lager_id=?, bewegungstyp=?, "
                + "menge=?, ablaufdatum=?, datum=?, bemerkung=? WHERE bestandsbewegung_id=?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, b.getMaterialId());
            anweisung.setInt(2, b.getLagerId());
            anweisung.setString(3, b.getBewegungstyp().name());
            anweisung.setInt(4, b.getMenge());
            if (b.getAblaufdatum() == null) anweisung.setNull(5, java.sql.Types.DATE);
            else anweisung.setDate(5, Date.valueOf(b.getAblaufdatum()));
            anweisung.setTimestamp(6, Timestamp.valueOf(b.getDatum() == null ? LocalDateTime.now() : b.getDatum()));
            anweisung.setString(7, b.getBemerkung());
            anweisung.setInt(8, b.getId());
            anweisung.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM bestandsbewegungen WHERE bestandsbewegung_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }


    public List<BestandView> findBestandViews() throws SQLException {
        String sql =
                "SELECT m.material_id AS material_id, "
                        + "       m.name        AS material_name, "
                        + "       m.einheit     AS einheit, "
                        + "       s.lager_id    AS lager_id, "
                        + "       s.name        AS lager_name, "
                        + "       SUM(CASE WHEN b.bewegungstyp = 'EINGANG' THEN b.menge "
                        + "                WHEN b.bewegungstyp = 'AUSGANG' THEN -b.menge "
                        + "                ELSE 0 END) AS bestand, "
                        + "       m.mindestbestand AS mindestbestand "
                        + "FROM bestandsbewegungen b "
                        + "JOIN materialien   m ON m.material_id = b.material_id "
                        + "JOIN stationslager s ON s.lager_id    = b.lager_id "
                        + "GROUP BY b.material_id, b.lager_id, m.name, s.name, m.einheit, m.mindestbestand "
                        + "ORDER BY s.name, m.name";
        List<BestandView> ergebnis = new ArrayList<>();
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql);
             ResultSet datensatz = anweisung.executeQuery()) {
            while (datensatz.next()) {
                ergebnis.add(new BestandView(
                        datensatz.getInt("material_id"),
                        datensatz.getString("material_name"),
                        datensatz.getString("einheit"),
                        datensatz.getInt("lager_id"),
                        datensatz.getString("lager_name"),
                        datensatz.getInt("bestand"),
                        datensatz.getInt("mindestbestand")));
            }
        }
        return ergebnis;
    }

    public int getBestand(int materialId, int lagerId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(CASE WHEN bewegungstyp = 'EINGANG' THEN menge "
                   + "                        WHEN bewegungstyp = 'AUSGANG' THEN -menge "
                   + "                        ELSE 0 END), 0) "
                   + "FROM bestandsbewegungen WHERE material_id = ? AND lager_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, materialId);
            anweisung.setInt(2, lagerId);
            try (ResultSet datensatz = anweisung.executeQuery()) {
                return datensatz.next() ? datensatz.getInt(1) : 0;
            }
        }
    }

    private Bestandsbewegung zeileLesen(ResultSet datensatz) throws SQLException {
        Date sqlAblauf = datensatz.getDate("ablaufdatum");
        LocalDate ablauf = sqlAblauf == null ? null : sqlAblauf.toLocalDate();
        Timestamp ts = datensatz.getTimestamp("datum");
        LocalDateTime datum = ts == null ? null : ts.toLocalDateTime();
        BewegungsTyp typ = BewegungsTyp.valueOf(datensatz.getString("bewegungstyp"));
        return new Bestandsbewegung(
                datensatz.getInt("bestandsbewegung_id"),
                datensatz.getInt("material_id"),
                datensatz.getInt("lager_id"),
                typ,
                datensatz.getInt("menge"),
                ablauf,
                datum,
                datensatz.getString("bemerkung"));
    }
}

