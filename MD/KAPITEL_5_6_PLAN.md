# Kapitel 5 + 6: DBConnection.java & Model-Klassen — LERNPLAN

**Aktueller Stand:** 2026-05-18
**Phase:** 3/7 (DAO-Schicht abgeschlossen)
**Nächste Woche:** KW21 Fr (Abschluss Phase 3) → KW24 Mo (Phase 4 Start)

---

## Kapitel 5: DBConnection.java — Die Brücke zur Datenbank

### Inhalt (für Lernhandbuch + Dokumentation)

**Theorie:**
- Java-Datenbankkonnektivität: Warum braucht man DBConnection?
- JDBC-Konzept: DriverManager, Connection, SQLException
- Singleton-Pattern: Design, Implementierung, Vorteile
- Lazy-Loading vs. Early-Loading: Trade-offs

**Praxis (IntelliJ-Code DBConnection.java):**
```java
package de.doit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3324/DOIT";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    
    private static Connection connection;
    
    private DBConnection() {
        // Privater Konstruktor — Singleton
    }
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
```

**Lernziele:**
- Singleton-Pattern verstehen + implementieren können
- JDBC-Verbindungsparameter erklären (URL, USER, PASSWORD)
- PreparedStatement vs. Statement unterscheiden (SQL-Injection!)
- Exception-Handling mit SQLException

---

## Kapitel 6: Die Model-Klassen — Sechs Tabellen, Sechs Java-Klassen

### Inhalt (für Lernhandbuch + Dokumentation)

**Überblick:**
| Tabelle | Klasse | Felder | Wert |
|---|---|---|---|
| kategorien | Kategorie.java | 3 | kategorieId, name, beschreibung |
| materialien | Material.java | 7 | materialId, name, einheit, mindestbestand, kategorieId + getBestand() |
| stationslager | Stationslager.java | 4 | lagerId, name, standort, typ |
| lieferanten | Lieferant.java | 5 | lieferantId, name, kontakt, telefon, email |
| bestellungen | Bestellung.java | 8 | bestellungId, materialId, lieferantId, lagerId, menge, bestelldatum, lieferdatum, status |
| bestandsbewegungen | Bestandsbewegung.java | 8 | bewegungId, materialId, lagerId, bewegungstyp, menge, ablaufdatum, datum, bemerkung |

**Zusätzliche Klassen:**
- `BewegungsTyp.java` — ENUM (EINGANG, AUSGANG)
- `BestandView.java` — ViewModel für Bestandsdarstellung (materialId, lagerId, bestand)

### Template für jede Model-Klasse

```java
package de.doit.model;

public class Material {
    // 1. Felder
    private int materialId;
    private String name;
    private String einheit;
    private int mindestbestand;
    private int kategorieId;
    
    // 2. Konstruktoren: mit ID (DB-Rückgabe) + ohne ID (neue Einträge)
    public Material(String name, String einheit, int mindestbestand, int kategorieId) {
        this.name = name;
        this.einheit = einheit;
        this.mindestbestand = mindestbestand;
        this.kategorieId = kategorieId;
    }
    
    public Material(int materialId, String name, String einheit, int mindestbestand, int kategorieId) {
        this.materialId = materialId;
        this.name = name;
        this.einheit = einheit;
        this.mindestbestand = mindestbestand;
        this.kategorieId = kategorieId;
    }
    
    // 3. Getter + Setter
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // ... weitere Getter/Setter ...
    
    // 4. toString()
    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", name='" + name + '\'' +
                ", einheit='" + einheit + '\'' +
                ", mindestbestand=" + mindestbestand +
                ", kategorieId=" + kategorieId +
                '}';
    }
}
```

**Lernziele:**
- POJO-Konzept (Plain Old Java Object) verstehen
- Konstruktor-Überladung: mit/ohne ID
- Getter/Setter automatisch generieren (IDE-Tipp)
- toString() für Debugging

---

## Kapitel 7: DAO-Klassen — CRUD für jede Tabelle

### Überblick (Vorausblick auf nächste Woche)

**6 DAO-Klassen (eine pro Tabelle):**
1. KategorieDAO.java
2. MaterialDAO.java
3. StationslagerDAO.java
4. LieferantDAO.java
5. BestellungDAO.java
6. BestandsbewegungDAO.java

**Standard-Methoden (CRUD):**
- `create(Objekt)` → INSERT
- `findById(id)` → SELECT WHERE id = ?
- `findAll()` → SELECT *
- `update(Objekt)` → UPDATE WHERE id = ?
- `delete(id)` → DELETE WHERE id = ?

**Template:**
```java
package de.doit.dao;

import de.doit.db.DBConnection;
import de.doit.model.Kategorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategorieDAO {
    
    // CREATE
    public void create(Kategorie kategorie) throws SQLException {
        String sql = "INSERT INTO kategorien (name, beschreibung) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, kategorie.getName());
            pstmt.setString(2, kategorie.getBeschreibung());
            pstmt.executeUpdate();
            // Optional: generierte ID zurück ins Objekt schreiben
        }
    }
    
    // READ
    public Kategorie findById(int id) throws SQLException {
        String sql = "SELECT * FROM kategorien WHERE kategorie_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Kategorie(
                        rs.getInt("kategorie_ID"),
                        rs.getString("name"),
                        rs.getString("beschreibung")
                    );
                }
            }
        }
        return null;
    }
    
    // READ ALL
    public List<Kategorie> findAll() throws SQLException {
        List<Kategorie> kategorien = new ArrayList<>();
        String sql = "SELECT * FROM kategorien";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                kategorien.add(new Kategorie(
                    rs.getInt("kategorie_ID"),
                    rs.getString("name"),
                    rs.getString("beschreibung")
                ));
            }
        }
        return kategorien;
    }
    
    // UPDATE
    public void update(Kategorie kategorie) throws SQLException {
        String sql = "UPDATE kategorien SET name = ?, beschreibung = ? WHERE kategorie_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kategorie.getName());
            pstmt.setString(2, kategorie.getBeschreibung());
            pstmt.setInt(3, kategorie.getKategorieId());
            pstmt.executeUpdate();
        }
    }
    
    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kategorien WHERE kategorie_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
```

---

## Zeitleiste: Kapitel 5 + 6 im Projekttagebuch dokumentieren

**Nach KW21 Fr:**
- Eintrag in Projekttagebuch: "Kapitel 5: DBConnection.java abgeschlossen"
  - Singleton-Pattern erklärt
  - JDBC-Konzept dokumentiert
  - Code-Referenz zu IntelliJ

- Eintrag in Projekttagebuch: "Kapitel 6: Die Model-Klassen — 7 Klassen abgeschlossen"
  - Template-Überblick
  - Lernziele + Verständnis
  - Code-Referenz zu IntelliJ

**Lernhandbuch aktualisieren:**
- Kapitel 5 + 6 hinzufügen
- Screenshots aus IntelliJ einfügen (DBConnection.java, Material.java als Beispiel)
- Erklärungstexte + Code-Snippets

---

## Verknüpfung zu Lerntagebuch

Die Einträge im Projekttagebuch werden **nicht kopiert**, sondern **referenziert**:

Beispiel für Projekttagebuch-Eintrag (KW21 Fr):
```
## 2026-05-22 — Kapitel 5 + 6 im Lernhandbuch festgehalten

- [Änderung] Lernhandbuch: Kapitel 5 (DBConnection.java) + Kapitel 6 (7 Model-Klassen) geschrieben
- [Erkenntnis] DBConnection.java als Singleton = zentrale Verbindungsverwaltung, lazy-loaded
- [Erkenntnis] Jede Model-Klasse = POJO mit zwei Konstruktoren (mit/ohne ID) + Getter/Setter + toString()
- [Erkenntnis] Alle 7 Klassen spiegeln Datenbank 1:1: Material, Kategorie, Stationslager, Lieferant, Bestellung, Bestandsbewegung, + 2 Helper (BewegungsTyp Enum, BestandView ViewModel)
- [Erkenntnis] Diese Klassen sind Grundlage für alle 6 DAOs (Kapitel 7, nächste Phase)
```

---

## Abgabe-Checkliste (für 17.07.2026)

Was wird später aus diesen Kapiteln in die **Projektdokumentation** übernommen?

- ✅ Kapitel 5: DBConnection-Code + Singleton-Erklärung
- ✅ Kapitel 6: Model-Klassenschemata (mindestens Material.java + Kategorie.java als Beispiele)
- ✅ Kapitel 7: DAO-Klassenschema (mindestens MaterialDAO.java als Beispiel)
- ✅ Zusammenfassung: Architektur-Übersicht (Schichtmodell: DB ← DAO ← Model ← Controller)

**Zielformat:** 10–15 Seiten (+ Anhang), davon ~3–4 Seiten für Code + Beschreibungen dieser Kapitel.

---

**Autor:** Sascha Schulz (WI 2551)
**Datum:** 2026-05-18
**Stand:** KW21, Phase 3 Abschluss
