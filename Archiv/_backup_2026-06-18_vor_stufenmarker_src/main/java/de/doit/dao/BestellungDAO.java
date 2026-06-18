package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Bestellung;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BestellungDAO implements GenericDAO<Bestellung> {

    @Override
    public Bestellung create(Bestellung bestellung) throws SQLException {
        String sql = "INSERT INTO bestellungen (material_id, lieferant_id, lager_id, "
                + "menge, bestelldatum, lieferdatum, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setInt(1, bestellung.getMaterialId());
            anweisung.setInt(2, bestellung.getLieferantId());
            anweisung.setInt(3, bestellung.getLagerId());
            anweisung.setInt(4, bestellung.getMenge());
            anweisung.setDate(5, Date.valueOf(bestellung.getBestelldatum()));
            if (bestellung.getLieferdatum() == null) {
                anweisung.setNull(6, java.sql.Types.DATE);
            } else {
                anweisung.setDate(6, Date.valueOf(bestellung.getLieferdatum()));
            }
            anweisung.setString(7, bestellung.getStatus());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    bestellung.setId(schluessel.getInt(1));
                }
            }
        }
        return bestellung;
    }

    @Override
    public List<Bestellung> findAll() throws SQLException {
        String sql = "SELECT bestellung_id, material_id, lieferant_id, lager_id, "
                + "menge, bestelldatum, lieferdatum, status "
                + "FROM bestellungen ORDER BY bestelldatum DESC";
        List<Bestellung> ergebnis = new ArrayList<>();
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
    public void update(Bestellung bestellung) throws SQLException {
        String sql = "UPDATE bestellungen SET material_id = ?, lieferant_id = ?, "
                + "lager_id = ?, menge = ?, bestelldatum = ?, lieferdatum = ?, "
                + "status = ? WHERE bestellung_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, bestellung.getMaterialId());
            anweisung.setInt(2, bestellung.getLieferantId());
            anweisung.setInt(3, bestellung.getLagerId());
            anweisung.setInt(4, bestellung.getMenge());
            anweisung.setDate(5, Date.valueOf(bestellung.getBestelldatum()));
            if (bestellung.getLieferdatum() == null) {
                anweisung.setNull(6, java.sql.Types.DATE);
            } else {
                anweisung.setDate(6, Date.valueOf(bestellung.getLieferdatum()));
            }
            anweisung.setString(7, bestellung.getStatus());
            anweisung.setInt(8, bestellung.getId());
            anweisung.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM bestellungen WHERE bestellung_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }

    private Bestellung zeileLesen(ResultSet datensatz) throws SQLException {
        Date sqlBestelldatum = datensatz.getDate("bestelldatum");
        Date sqlLieferdatum  = datensatz.getDate("lieferdatum");
        LocalDate bestelldatum = sqlBestelldatum == null ? null : sqlBestelldatum.toLocalDate();
        LocalDate lieferdatum  = sqlLieferdatum  == null ? null : sqlLieferdatum.toLocalDate();
        return new Bestellung(
                datensatz.getInt("bestellung_id"),
                datensatz.getInt("material_id"),
                datensatz.getInt("lieferant_id"),
                datensatz.getInt("lager_id"),
                datensatz.getInt("menge"),
                bestelldatum,
                lieferdatum,
                datensatz.getString("status"));
    }
}
