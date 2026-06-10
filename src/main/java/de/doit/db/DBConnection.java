package de.doit.db;

// STRG ALT L = zum Formatieren
// ALT Enter = Quick Fix
// CTRL Space = Code Vervollständigen
// CTRL Klick = was macht der Code
// 2 x Schift = öffnet Suchfeld
// ALT 7 = Struktur mit Icons

// Angepasste Farben
// Dunkelblau, fett —       Schlüsselwörter
// Türkis —                 Klassen, Interfaces und Enums
// Schwarz (ohne Farbe) —   Methodennamen
// Grün —                   lokale Variablen, Parameter fett-kursiv
// Lila/Violett —           Felder der Klasse
// Grün, fett —             Zeichenketten (Strings)
// Blau —                   Zahlen
// Grau, kursiv —           Kommentare
// Oliv/Dunkelgelb, unterstrichen — Annotationen

// Java-Standardbibliothek, also Teil des JDK daher keine eingabe für Abhängigkeit in pom.xml
// Connection ist die Schnittstelle, das eine offene Datenbankverbindung darstellt

import java.sql.Connection;
// die Werkzeugklasse, die eine neue Verbindung aufbaut (ist ein Interface)
import java.sql.DriverManager;
// wenn etwas mit der Datenbank schiefgeht
import java.sql.SQLException;

public class DBConnection {
    // JDBC-Treiber ist der Treiber, der das Protokoll der jeweiligen Datenbank implementiert, um die Kommunikation zu ermöglichen.
    // 127.0.0.1 Standart IP für localhost
    // Port 3324: abweichender MySQL-Port (Standard 3306)
    private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
    private static final String USER = "root";
    // normalerweiße in einer Konfigurationsdatei auslagern
    private static final String PASSWORD = "1234";

    // die einzige offene Verbindung der gesamten Anwendung , noch ist sie leer ert mit getConnection wird sie befüllt
    private static Connection connection;

    // private Konstruktor
    private DBConnection() {
        // Privater Konstruktor das es nur eine verbindung gibt pro Anwendung geben soll - Singleton Muster
        // Singleton Muster = eine Datenbankverbindung pro Anwendung
        // für hier: eine Klasse mit einem privaten Konstruktor und einer öffentlichen Zugriffsmethode,
        // die garantiert, dass es im gesamten Programm nur eine Instanz gibt
    }

    // Zugriffsmethode zur Verbindung
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        // hier der aktive Aufbau der Verbindung zu SQL-Datenbank - wird als connection zurückgegeben
        // ist eine Lazy Initialization also erst bei aktiver Nutzung wird die VErbindung aufgebaut
        // das ist ein gängiges Muster in der Softwareentwicklung, um Ressourcen optimal zu nutzen (Google)
        return connection;
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
