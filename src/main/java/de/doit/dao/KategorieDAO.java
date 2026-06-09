package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Kategorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategorieDAO {
    // Übermittelt wird die Referenz also die Speicheradresse im RAM wo es gespeichert ist
    // Daß ist die Methode mit dem Namen create
    //   Rückgabetyp       Parametertyp
    //            Methodenname        Parameter
    public Kategorie create(Kategorie kategorie) throws SQLException {
        String sql = "INSERT INTO kategorien (name, beschreibung) VALUES (?, ?)";
        //  Typ für die Variable conn / Was ist conn? Ein String? Ein int? = daher der Typ
        //        Variable (eine Referenz auf ein Connection-Objekt)
        //                Methodenaufruf (eine Datenbankverbindung)
        Connection conn = DBConnection.getConnection();
        //                              . ist der Methodenaufruf-Operator
        //                                  eine Methode der Connection-Klasse (Java Standardbibliothek (JDK))
        try (PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
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

//    public Kategorie findById(int id) throws SQLException {
//        String sql = "SELECT kategorie_id, name, beschreibung FROM kategorien "
//                + "WHERE kategorie_id = ?";
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
