package de.doit.db;

// Java-Standardbibliothek, also Teil des JDK daher keine eingabe für Abhängigkeit in pom.xml
// Connection ist das Interface, das eine offene Datenbankverbindung darstellt
import java.sql.Connection;
// die Werkzeugklasse, die eine neue Verbindung aufbaut
import java.sql.DriverManager;
// wenn etwas mit der Datenbank schiefgeht
import java.sql.SQLException;

public class DBConnection {
    // jdbc:mysql:// ist das Protokoll und timezone ist wegen Logfehler, weil er die Zeitzone nicht kennt (kann kommen)
    private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
    private static final String USER = "root";
    // normalerweiße in einer Konfigurationsdatei auslagern
    private static final String PASSWORD = "1234";

    // die einzige offene Verbindung der gesamten Anwendung , noch ist sie leer ert mit getConnection wird sie befüllt
    private static Connection connection;

    private DBConnection() {
        // Privater Konstruktor das es nur eine verbindung gibt pro Anwendung geben soll - Singleton Muster
        // Singleton Muster = eine Datenbankverbindung pro Anwendung
        // für hier: eine Klasse mit einem privaten Konstruktor und einer öffentlichen Zugriffsmethode,
        // die garantiert, dass es im gesamten Programm nur eine Instanz gibt
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
        // hier der aktive Aufbau der Verbindung zu SQL-Datenbank - wird als connection zurückgegeben
        // ist eine Lazy Initialization also erst bei aktiver Nutzung wird die VErbindung aufgebaut
        // das ist ein gängiges Muster in der Softwareentwicklung, um Ressourcen optimal zu nutzen (Google)
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Verbindung konnte nicht geschlossen werden: "
                        + e.getMessage());
                // warum closeConenncton? keine toten VErbindungen und
                // kein erneuter Aufbau einer geschlossenen Verbindung
            }
        }
    }
}
