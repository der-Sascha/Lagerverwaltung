package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Bestellung;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BestellungDAO implements LeseDAO<Bestellung> {

    // === Stufe 1 — LESEN (DB-SELECT → Liste) ===
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
