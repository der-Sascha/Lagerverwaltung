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
    public Bestandsbewegung create(Bestandsbewegung bewegung) throws SQLException {
        // datum (Buchungszeitpunkt) wird von Java gesetzt (bewegung.getDatum()), nicht von der DB
        String sql = "INSERT INTO bestandsbewegungen (material_id, lager_id, bewegungstyp, menge, "
                + "ablaufdatum, datum, bemerkung) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setInt(1, bewegung.getMaterialId());
            anweisung.setInt(2, bewegung.getLagerId());
            anweisung.setString(3, bewegung.getBewegungstyp().name());
            anweisung.setInt(4, bewegung.getMenge());
            if (bewegung.getAblaufdatum() == null) {
                anweisung.setNull(5, Types.DATE);
            } else {
                anweisung.setDate(5, Date.valueOf(bewegung.getAblaufdatum()));
            }
            anweisung.setTimestamp(6, Timestamp.valueOf(bewegung.getDatum()));
            anweisung.setString(7, bewegung.getBemerkung());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    bewegung.setId(schluessel.getInt(1));
                }
            }
        }
        return bewegung;
    }

    @Override
    public List<Bestandsbewegung> findAll() throws SQLException {
        String sql = "SELECT bewegung_id, material_id, lager_id, bewegungstyp, "
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


    @Override
    public void update(Bestandsbewegung bewegung) throws SQLException {
        // datum (Buchungszeitpunkt) bewusst NICHT im UPDATE -> unveraenderlich (kein Setter im Model)
        String sql = "UPDATE bestandsbewegungen SET material_id = ?, lager_id = ?, bewegungstyp = ?, "
                + "menge = ?, ablaufdatum = ?, bemerkung = ? WHERE bewegung_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, bewegung.getMaterialId());
            anweisung.setInt(2, bewegung.getLagerId());
            anweisung.setString(3, bewegung.getBewegungstyp().name());
            anweisung.setInt(4, bewegung.getMenge());
            if (bewegung.getAblaufdatum() == null) {
                anweisung.setNull(5, Types.DATE);
            } else {
                anweisung.setDate(5, Date.valueOf(bewegung.getAblaufdatum()));
            }
            anweisung.setString(6, bewegung.getBemerkung());
            anweisung.setInt(7, bewegung.getId());
            anweisung.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM bestandsbewegungen WHERE bewegung_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }

    private Bestandsbewegung zeileLesen(ResultSet datensatz) throws SQLException {
        Date sqlAblauf = datensatz.getDate("ablaufdatum");
        LocalDate ablauf = sqlAblauf == null ? null : sqlAblauf.toLocalDate();

        Timestamp ts = datensatz.getTimestamp("datum");
        LocalDateTime datum = ts == null ? null : ts.toLocalDateTime();

        BewegungsTyp typ = BewegungsTyp.valueOf(datensatz.getString("bewegungstyp"));
        return new Bestandsbewegung(
                datensatz.getInt("bewegung_id"),
                datensatz.getInt("material_id"),
                datensatz.getInt("lager_id"),
                typ,
                datensatz.getInt("menge"),
                ablauf,
                datum,
                datensatz.getString("bemerkung"));
    }
}
