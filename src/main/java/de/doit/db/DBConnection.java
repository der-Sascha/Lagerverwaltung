package de.doit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// hier wird genau eine Verbindung hergestellt - lazy connection - der private Konstruktor plus eine statische Methode
public class DBConnection {

    // Port 3324: abweichender MySQL-Port (Standard 3306) Aufpassen bei VPN, da ändert sich der Port
    private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // im Produktivbetrieb in Konfigurationsdatei auslagern

    private static Connection connection;

    // Privater Konstruktor: verhindert weitere Instanzen (Singleton).
    private DBConnection() { }

    // durch static genau eine Verbindung (Lazy Initialization), baut sie erst bei Bedarf auf und bleibt bis close aktiv
    // privater konstruktor kann kein new Objekt erstellen daher static - dann aufrufbar ohne Objekt
    // ohne static muss ein new Objekt erstellt werden das die Methode arbeitet - mit static geht es (Google Suche)
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // schließt Verbindung wenn ich x klicke in der UI, muss man nicht ist aber sauberer
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
