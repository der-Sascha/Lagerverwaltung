package de.doit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Stellt die zentrale Datenbankverbindung nach dem Singleton-Muster bereit:
 * Privater Konstruktor und eine statische Zugriffsmethode garantieren, dass
 * im gesamten Programm nur eine Verbindung existiert. Die Verbindung wird
 * lazy aufgebaut (erst bei der ersten Nutzung).
 */
public class DBConnection {

    // Port 3324: abweichender MySQL-Port (Standard 3306)
    private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // im Produktivbetrieb in Konfigurationsdatei auslagern

    private static Connection connection;

    // Privater Konstruktor: verhindert weitere Instanzen (Singleton).
    private DBConnection() { }

    // === Stufe 0 — FUNDAMENT (DB-Verbindung, Singleton) ===
    /** Liefert die gemeinsame Verbindung; baut sie bei Bedarf auf (Lazy Initialization). */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    /** Schliesst die Verbindung, sofern sie geoeffnet ist. */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Verbindung konnte nicht geschlossen werden: "
                        + e.getMessage());
            }
        }
    }
}
