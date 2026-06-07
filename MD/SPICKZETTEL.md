# 📝 SPICKZETTEL: Java JDBC — Das Wichtigste

Kurzes Nachschlagewerk für Code-Verständnis. Detaillierte Erklärungen → GLOSSAR.md

---

## ⚡ STATISCHE vs. INSTANZMETHODEN

| Statische Methode | Instanzmethode |
|-------------------|----------------|
| `Klasse.methode()` | `variable.methode()` |
| Gehört der Klasse | Gehört dem Objekt |
| Kein Objekt nötig | Objekt benötigt |
| `public static ...` | `public ...` (kein static) |
| `DriverManager.getConnection()` | `connection.isClosed()` |

---

## 🔤 PUNKT-OPERATOR (.)

```
[Vor Punkt]          [Nach Punkt]
Klasse               → statische Methode     (DriverManager.getConnection())
Variable/Objekt      → Instanzmethode        (connection.close())
```

**Fehler:** `Connection.close()` ❌ (Connection ist Typ, nicht Objekt)

---

## 📌 TYPNAME ≠ Interface/Klasse

```java
Connection connection;  // ← Nur der Typname sichtbar
```

**Im Code:** Keine Unterscheidung zwischen Interface und Klasse
**IDE:** Hover oder Ctrl+Click → zeigt Unterschied

---

## 🗂️ JDBC-KURZ

| Konzept | Was ist es? | Beispiel |
|---------|-----------|----------|
| **Connection** | Interface (DB-Verbindung) | `private static Connection connection;` |
| **DriverManager** | Klasse (erstellt Connections) | `DriverManager.getConnection(...)` |
| **getConnection()** | Statische Methode | `DriverManager.getConnection(URL, USER, PASSWORD)` |
| **isClosed()** | Instanzmethode | `if (connection.isClosed()) { }` |

---

## 🛡️ DBConnection-Pattern

```java
// Singleton + Lazy Initialization
private static Connection connection;

public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}
```

---

## ❌ HÄUFIGE FEHLER

```java
// FALSCH
Connection.close();              // ❌ Typ, nicht Objekt
if (connection.isClosed()) { }   // ❌ Null-Prüfung vergessen
connection = new Connection();   // ❌ Connection ist Interface

// RICHTIG
connection.close();              // ✓ Variable vor Punkt
if (connection == null || connection.isClosed()) { }  // ✓ Null + isClosed
connection = DriverManager.getConnection(...);  // ✓ Factory-Methode
```

---

## 🔍 CODE RICHTIG LESEN

**Schritt 1:** Punkt sehen?
- Ja → Was steht davor? (Klasse oder Variable?)
- Nein → Deklaration oder Typ

**Schritt 2:** Vor dem Punkt
- Großbuchstabe + keine Deklaration → Klasse (statische Methode)
- Kleinbuchstabe + vorher deklariert → Variable (Instanzmethode)

**Schritt 3:** IDE nutzen
- Ctrl+Click auf Name → Deklaration sehen
- Hover → Informationen anzeigen

---

## 📚 WANN INS GLOSSAR GEHEN?

Diese Spickzettel-Punkte reichen für 80% der Anfragen. Falls mehr nötig:

- **Lazy Initialization Details** → GLOSSAR Kapitel 5.1
- **Singleton-Pattern Details** → GLOSSAR Kapitel 5.2
- **SQLException-Handling** → GLOSSAR Kapitel 6.1
- **Überladung (Overloading)** → GLOSSAR Kapitel 5.3
- **Best Practices** → GLOSSAR Kapitel 10

---

## ➕ NEUE KONZEPTE (HINZUFÜGEN)

Während du das Projekt entwickelst, können neue wichtige Konzepte hinzukommen.

**Checkliste: Kommt das auf den Spickzettel?**

- ✅ Brauchst du es zum **Verständnis von Code**?
- ✅ Wirst du **häufig verwirrt** davon?
- ✅ Ist es **3 Zeilen oder kürzer** erklärbar?
- ✅ Benutzt du es **täglich im Projekt**?

**Falls JA zu allen:** Auf den Spickzettel!

**Beispiele für neue Konzepte:**
- Try-Catch Syntax (SQLException)
- Statement / ResultSet (nach Einführung)
- DAO-Pattern (nach Einführung)
- Überladung (Overloading) — wenn häufig verwendet
- Datentyp-Konvertierung (falls problematisch)

---

## 🔄 WORKFLOW: SPICKZETTEL NUTZEN

**Situation 1: Ich lese Code und verstehe nicht, was das ist**
1. Schau hier: "CODE RICHTIG LESEN" (3 Schritte)
2. Immer noch verwirrt? → GLOSSAR Kapitel 0-1

**Situation 2: Ich schreibe Code und mache Fehler**
1. Schau hier: "HÄUFIGE FEHLER"
2. Immer noch falsch? → GLOSSAR Kapitel 1.1 & 3

**Situation 3: Ich verstehe ein Konzept nicht**
1. Schau Tabellen auf diesem Spickzettel
2. Brauche mehr Details? → GLOSSAR (Kapitel in Tabelle angegeben)

**Situation 4: Neue Technologie kommt ins Projekt**
1. Lerne sie im Glossar
2. Ist es essentiell? → Ergänze hier auf Spickzettel

---

## 📌 MERKSÄTZE (AUSWENDIG)

Diese 3 Sätze merken:

1. **"Punkt = immer Klasse ODER Variable davor"**
2. **"Statisch = Klasse vor Punkt, Instanz = Variable vor Punkt"**
3. **"Im Code sieht man nicht, ob Interface oder Klasse — nur der Typname"**

---

**Zuletzt aktualisiert:** 2026-05-21  
**Projekt:** DOIT Lagerverwaltung  
**Für Schnell-Abfragen:** Hier bleiben  
**Für Deep-Dives:** → GLOSSAR.md
