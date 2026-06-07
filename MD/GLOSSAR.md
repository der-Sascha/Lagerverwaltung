# GLOSSAR: Java JDBC & Datenbankverbindungen

Systematische Erklärung aller Konzepte aus dem Lagerverwaltungs-Projekt mit logischen Zusammenhängen.

---

## 0. ANLEITUNG: WIE MAN JAVA-CODE RICHTIG LIEST

**Ziel:** Erkennen, ob etwas eine **Klasse**, **Variable**, **Methode** oder **Typ** ist — nur durch Syntax-Analyse.

### 0.1 Erkennungsmerkmal: Der Punkt (.)

Der Punkt ist der Schlüssel. Danach erkennt man automatisch, was davor kommt.

**Regel 1: Vor dem Punkt = Objekt/Variable ODER Klasse (mit statischer Methode)**

```java
connection.close();         // ← connection ist VARIABLE
DriverManager.getConnection();  // ← DriverManager ist KLASSE
```

**Regel 2: Kein Punkt = Deklaration oder Typ**

```java
Connection connection;      // ← Keine Variable noch nicht erstellt, nur Typ deklariert
private static final String URL;  // ← Deklaration einer Konstanten
public static Connection getConnection() // ← Methodendeklaration
```

---

### 0.2 Statische vs. Instanzmethoden erkennen

| Situation | Syntax | Was ist es? | Beispiel |
|-----------|--------|-----------|----------|
| **Statische Methode** | `Klasse.Methode()` | Klasse, nicht Variable | `DriverManager.getConnection()` |
| **Instanzmethode** | `Variable.Methode()` | Variable/Objekt | `connection.isClosed()` |
| **Deklaration statisch** | `public static Typ Methode()` | Gehört zur Klasse | `public static Connection getConnection()` |
| **Deklaration Instanz** | `public Typ Methode()` | Gehört zu Objekten | `public boolean isClosed()` |

**Praxistipps:**

```java
// ERKENNUNGSMUSTER 1: Klasse vor Punkt?
DriverManager.getConnection()
// DriverManager = Großbuchstabe am Anfang?
// Ja → Likely eine Klasse
// Die Methode ist statisch (gehört zur Klasse)

// ERKENNUNGSMUSTER 2: Variable vor Punkt?
connection.isClosed()
// connection = Kleinbuchstabe am Anfang?
// Ja → Likely eine Variable
// Die Methode gehört zum Objekt (Instanzmethode)
```

---

### 0.3 Klasse vs. Interface erkennen (fortgeschritten)

**Im Code sieht man keinen Unterschied:**

```java
Connection connection;  // ← Nur der Typname, nicht "interface Connection"
```

**Wie erkenne ich trotzdem, ob Connection Klasse oder Interface ist?**

| Methode | Hinweis |
|---------|---------|
| **Dokumentation** | `java.sql.Connection` → Interface ist in der Dokumentation gekennzeichnet |
| **IDE (Hover)** | IDE zeigt: "interface Connection" |
| **IDE (Ctrl+Click)** | Click auf Connection → Quellcode zeigt `interface Connection { }` |
| **Großbuchstabe** | Großbuchstabe ist Konvention, aber Klassen UND Interfaces folgen ihr |

**Praktisch im Projekt:**

```java
// Im Code: Identisch
Connection conn1 = ...;  // Interface
String str = ...;        // Klasse

// Unterschied nur durch Dokumentation/IDE sichtbar
// Für Programmierung ist es oft unwichtig!
```

---

### 0.4 Schnell-Erkennungstabelle

**Diese Tabelle hilft beim "Code-Lesen":**

```java
Code                                    | Typ          | Erkennungszeichen
----------------------------------------|--------------|-------------------
DriverManager.getConnection()           | Klasse + Methode (static) | Großbuchstabe + keine Deklaration vorher
connection.close()                      | Variable + Methode        | Kleinbuchstabe + vorher deklariert
Connection connection;                  | Typ + Variable (deklariert) | Großbuchstabe (Typ) + Kleinbuchstabe (Name)
public static Connection getConnection()| Methodendeklaration (static) | "static" Schlüsselwort
private static Connection connection;   | Feld (Variable, statisch) | "static" Schlüsselwort + Kleinbuchstabe
connection == null                      | Variable                  | Kleinbuchstabe, Vergleich
connection.isClosed()                   | Methode aufrufen          | Punkt + Klammern
```

---

### 0.5 Workflow: Wie man Code-Zeilen analysiert

**Beispiel-Zeile:**
```java
connection = DriverManager.getConnection(URL, USER, PASSWORD);
```

**Schritt-für-Schritt-Analyse:**

1. **Punkt gesucht?** → Ja, zwei Punkte gefunden
   - Erster Punkt: `DriverManager.getConnection` → Klasse + Methode
   - Zweiter Punkt: Keiner (Zuweisung)

2. **`DriverManager` vor Punkt** → Großbuchstabe am Anfang → Likely Klasse

3. **`.getConnection()`** → Methodenaufruf auf Klasse → Statische Methode

4. **`= ...`** → Zuweisung mit Gleichheitszeichen

5. **`connection =`** → Kleinbuchstabe am Anfang → Variable

6. **Resultat:**
   - `connection` = Variable (wird dem Rückgabewert zugewiesen)
   - `DriverManager` = Klasse
   - `getConnection()` = Statische Methode der Klasse DriverManager
   - `URL, USER, PASSWORD` = Parameter (Konstanten)

---

### 0.6 Was man NICHT auswendig lernen muss

❌ **Nicht nötig:**
- Alle Methoden von Connection auswendig kennen
- Alle Klassen aus java.sql auswendig kennen
- Welche Methode statisch und welche nicht ist

✅ **Stattdessen:**
- **IDE nutzen** (Ctrl+Click, Hover)
- **Java-Dokumentation** nachschlagen: https://docs.oracle.com/javase/8/docs/api/
- **Pattern erkennen** (Punkt-Operator = Objekt/Klasse vor dem Punkt)
- **Praxis nutzen** (mit Code experimentieren)

**Beispiel mit IDE:**

```java
connection.  // ← Punkt tippen
// IDE zeigt alle Methoden von Connection
// Du siehst: isClosed(), close(), createStatement(), ...
```

---

## 1. GRUNDOPERATOREN UND SYNTAX

### 1.1 Punkt-Operator (.) — Erkennung anhand der Syntax

**Definition:** Der Punkt ist der **Memberzugriff-Operator**. Er greift auf Methoden und Felder eines Objekts zu.

**Wichtig:** Der Punkt steht IMMER vor einem Objekt/einer Variable ODER vor einer Klasse (bei statischen Methoden).

**Struktur:**
```
[Objekt/Variable].[Methode/Feld]
ODER
[Klasse].[Statische Methode/Feld]
```

---

## 📌 MERKREGEL: STATISCHE vs. INSTANZMETHODEN

### Statische Methode
- **Gehört:** Der Klasse, nicht einem Objekt
- **Aufruf:** `Klasse.methode()`
- **Voraussetzung:** Keine Objekt-Erstellung nötig
- **Beispiel:** `DriverManager.getConnection()`
- **Code:** `public static Connection getConnection(...)`

### Instanzmethode
- **Gehört:** Einem Objekt (Instanz)
- **Aufruf:** `variable.methode()`
- **Voraussetzung:** Erstelltes Objekt benötigt
- **Beispiel:** `connection.isClosed()`
- **Code:** `public boolean isClosed()`

### Kurz
```
Statisch:  Klasse.methode()  → Klasse
Instanz:   objekt.methode()  → Objekt
```

---

## VERGLEICH: Statische Methode vs. Instanzmethode

#### Statische Methode: `DriverManager.getConnection()`

```java
DriverManager.getConnection(URL, USER, PASSWORD);

// Analyse:
// - DriverManager = Klasse (nicht Variable)
// - . = Memberzugriff
// - getConnection() = Statische Methode der Klasse
// - In der Deklaration: public static Connection getConnection(...)
// - Keine Variable vor dem Punkt nötig
```

**Merkmale:**
- Klasse vor Punkt: `DriverManager.`
- Schlüsselwort `static` in der Deklaration
- Gehört zur Klasse, nicht zu einzelnen Objekten
- Kann ohne Objekt-Erstellung aufgerufen werden

---

#### Instanzmethode: `connection.isClosed()`

```java
connection.isClosed();

// Analyse:
// - connection = Variable (ein konkretes Objekt)
// - . = Memberzugriff
// - isClosed() = Instanzmethode des Objekts
// - In der Deklaration: public boolean isClosed()
// - Variable MUSS vor dem Punkt stehen
```

**Merkmale:**
- Variable vor Punkt: `connection.`
- Kein `static` in der Deklaration
- Gehört zu einzelnen Objekten, nicht zur Klasse
- Braucht ein Objekt (Variable) zum Aufrufen

---

**Weitere Beispiele aus dem Projekt:**

```java
// Beispiel 1: Instanzmethode
connection.close();  // ← connection ist Variable, close() ist Instanzmethode

// Beispiel 2: Statische Methode
DriverManager.getConnection(URL, USER, PASSWORD);  // ← DriverManager ist Klasse, getConnection() ist statische Methode

// Beispiel 3: Statisches Feld (Variable der Klasse)
System.out.println(Math.PI);  // ← Math ist Klasse, PI ist statisches Feld

// Beispiel 4: Instanzfeld (Variable des Objekts)
String text = "Hallo";
int length = text.length();  // ← text ist Variable, length() ist Methode
```

---

**Häufiger Fehler:**

```java
// FALSCH: Punkt vor Typ-Name (ohne static)
Connection.close();  // ❌ Connection ist nur ein Typ, kein Objekt
// Compiler-Fehler: close() ist keine statische Methode von Connection

// RICHTIG: Punkt vor Variable/Objekt
connection.close();  // ✓ connection ist ein Objekt (Variable)

// FALSCH: Statische Methode mit Objekt (funktioniert, aber nicht idiomatic)
DBConnection db = new DBConnection();  // Geht nicht, Konstruktor ist privat
db.getConnection();  // ❌ Nicht empfohlen

// RICHTIG: Statische Methode mit Klasse
DBConnection.getConnection();  // ✓ Standard bei statischen Methoden
```

---

**Woher weiß ich, ob eine Methode statisch ist?**

| Merkmal | Statische Methode | Instanzmethode |
|---------|-------------------|----------------|
| **Deklaration** | `public static Connection getConnection()` | `public boolean isClosed()` |
| **Aufruf** | `DriverManager.getConnection()` | `connection.isClosed()` |
| **Vor Punkt** | Klasse | Variable/Objekt |
| **Schlüsselwort** | `static` | kein `static` |
| **Objekt nötig** | Nein | Ja |

**Zwei Wege zur Erkennung:**

1. **Code lesen:** Deklaration nach `static` suchen
2. **IDE nutzen:** Auf Methode hovern oder Ctrl+Click → zeigt Deklaration

---

### 1.2 Zuweisungsoperator (=)

**Definition:** Der Zuweisungsoperator speichert einen Wert in einer Variable.

**Struktur:**
```
[Variable] = [Wert];
```

**Beispiele aus dem Projekt:**

```java
// Beispiel 1: Typ-Deklaration + Zuweisung
private static Connection connection;  // Deklaration (noch kein Wert)
connection = DriverManager.getConnection(URL, USER, PASSWORD);  // Zuweisung

// Beispiel 2: Kombiniert
Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
// Typ + Variablenname = Rückgabewert der Methode

// Beispiel 3: Konstante
private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
// final = Wert kann nicht geändert werden
```

**Unterschied: Deklaration vs. Zuweisung**

```java
Connection connection;  // NUR Deklaration (kein = erforderlich)
connection = DriverManager.getConnection(...);  // Zuweisung (mit =)
Connection conn = DriverManager.getConnection(...);  // Beides zusammen
```

---

## 2. VARIABLEN & TYPEN

### 2.1 Variable vs. Klasse — Erkennung im Code

**Definition:**
- **Variable:** Ein Container für einen Wert; hat einen Typ und einen Namen; kann mit = einen Wert zugewiesen bekommen
- **Klasse:** Ein Bauplan für Objekte; hat Methoden und Felder; wird NICHT mit = zugewiesen (nur wenn sie VOR einem Punkt kommt)
- **Interface:** Eine Schnittstelle; ähnlich wie Klasse, aber kein direktes Objekt, nur als Typ

**Wie erkenne ich den Unterschied?**

| Merkmal | Variable | Klasse |
|---------|----------|--------|
| Großbuchstabe am Anfang | Eher nein | Ja (Konvention) |
| Deklaration vorher | Ja: `Connection conn = ...` | Nein |
| Vor dem Punkt | Immer Variable | Nur statische Methoden |
| Beispiel | `connection` | `DriverManager`, `Connection` |

**Beispiele aus dem Projekt:**

```java
// VARIABLE: connection
private static Connection connection;
// connection ist eine Variable vom Typ Connection
// Sie wurde deklariert, aber hat noch keinen Wert

// VARIABLE: conn
Connection conn = DriverManager.getConnection(url, user, password);
// conn ist eine Variable, der ein Connection-Objekt zugewiesen wurde

// KLASSE: DriverManager
DriverManager.getConnection(URL, USER, PASSWORD);
// DriverManager ist eine Klasse, die Methoden bereitstellt

// KLASSE: Connection
public static Connection getConnection() throws SQLException {
// Connection ist hier nur ein Typ (Rückgabetyp), nicht eine Variable
```

**Praxistest:**

```java
// Frage: Ist das eine Variable oder Klasse?
connection.close();
// Antwort: connection ist eine Variable
// Grund: Sie wurde vorher mit `private static Connection connection;` deklariert

// Frage: Ist das eine Variable oder Klasse?
DriverManager.getConnection(...);
// Antwort: DriverManager ist eine Klasse
// Grund: DriverManager ist direkt in der java.sql-Bibliothek definiert, nicht deklariert
```

---

### 2.1.5 Typname in Deklarationen — "Interface" oder "class" ist NICHT sichtbar

**Häufige Anfängerfrage:** "Wie erkenne ich, ob `Connection` ein Interface oder eine Klasse ist?"

**Antwort:** **Im Code NICHT!**

```java
// Im Code sieht man NUR den Typnamen:
Connection connection;  // ← Nur "Connection", nicht "interface Connection"
private static Connection connection;  // ← Gleich

// NICHT sichtbar im Code:
// - Ob es Klasse oder Interface ist
// - Ob es abstract ist
// - Welche Methoden es hat (nur in der IDE/Dokumentation)
```

**Wo findet man die Information?**

```java
// Im Quellcode von Connection (mit Ctrl+Click in IDE):
public interface Connection extends Wrapper {
    // Methoden...
}

// Oder in der Dokumentation: java.sql.Connection
```

**Praktische Konsequenz:**

```java
// Das funktioniert genau gleich, egal ob Interface oder Klasse:
Connection conn1 = DriverManager.getConnection(...);  // Interface
String str = "Hallo";  // Klasse

// Beide haben Methoden:
conn1.close();  // ← Works
str.length();   // ← Works
```

**Merkregel:** Im Code schreibt man NUR den Typnamen (`Connection`, nicht `interface Connection`). Der Compiler weiß, was es ist, aber der Code zeigt es nicht.

---

### 2.2 Typdeklaration

**Definition:** Die Angabe des Datentyps EINER Variablen.

**Struktur:**
```
[Typ] [Variablenname];
```

**Beispiele aus dem Projekt:**

```java
// Beispiel 1: Ohne Punkt
private static Connection connection;
// Connection = Typ (welches Datenformat?)
// connection = Variablenname (wie heißt sie?)
// Keine Zuweisung mit =

// Beispiel 2: Mit Zuweisung
private static final String URL = "jdbc:mysql://...";
// String = Typ (Text)
// URL = Variablenname
// = zugewiesen (Wert kommt jetzt)
// final = kann nicht mehr geändert werden

// Beispiel 3: In Methodenparameter
public static Connection getConnection() throws SQLException {
// Connection = Rückgabetyp (die Methode gibt ein Connection-Objekt zurück)
```

**Wichtig: Typ ≠ Punkt**

```java
Connection connection;  // ← Typ ohne Punkt
connection.close();     // ← Punkt nach der Variablen
```

---

## 3. METHODEN

---

### ⚡ QUICK-CHECK: Statisch oder Instanz?

Schau auf die **Deklaration**, nicht auf den Aufruf:

```java
public static Connection getConnection() { }     // ← "static" vorhanden? → STATISCH
public boolean isClosed() { }                     // ← kein "static"? → INSTANZ
```

Dann weißt du beim Aufruf, was zu tun ist:

```java
DriverManager.getConnection();  // ← Statische Methode, also Klasse vor Punkt
connection.isClosed();          // ← Instanzmethode, also Variable vor Punkt
```

---

### 3.1 Methodendeklaration vs. Methodenaufruf

**Definition:**
- **Methodendeklaration:** Die Definition einer Methode (wie heißt sie, was gibt sie zurück, welche Parameter braucht sie?)
- **Methodenaufruf:** Die Verwendung einer Methode (jetzt wird sie ausgeführt)

**Struktur Deklaration:**
```java
[Zugriffsmodifikator] [static?] [Rückgabetyp] [Methodenname]([Parameter]) {
    // Rumpf
}
```

**Struktur Aufruf:**
```
[Objekt/Klasse].[Methodenname]([Argumente]);
```

**Beispiel aus dem Projekt:**

```java
// DEKLARATION (Definition in der DBConnection-Klasse)
public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}
// Methode heißt: getConnection
// Rückgabetyp: Connection
// Parameter: keine ()
// throws: SQLException (Fehlerbehandlung)

// AUFRUF (Verwendung anderswo im Code)
Connection conn = DBConnection.getConnection();
// getConnection() wird aufgerufen
// Das Ergebnis wird in conn gespeichert
```

**Warum heißt die Deklarations-Methode und die Aufrufs-Methode beide "getConnection"?**

Das ist **Konsistenz und Logik**:
- Die Deklaration bestimmt: "Ich bin eine Methode namens getConnection"
- Der Aufruf nutzt: "Ich rufe getConnection auf"
- Es geht um dieselbe Methode, verschiedene Kontexte (Definition vs. Ausführung)

---

### 3.2 Statische Methoden

**Definition:** Eine statische Methode gehört zur **Klasse**, nicht zu einer Instanz (Objekt). Sie kann aufgerufen werden, ohne ein Objekt zu erstellen.

**Erkennzeichen:** Das Schlüsselwort `static` in der Deklaration.

**Struktur:**
```java
public static [Rückgabetyp] [Methodenname]([Parameter]) { }
```

**Beispiele aus dem Projekt:**

```java
// STATISCHE METHODE IN DriverManager
public static Connection getConnection(String url, String user, String password) {
    // Diese Methode gehört zur Klasse DriverManager
    // Sie wird aufgerufen, ohne ein DriverManager-Objekt zu erstellen
}

// AUFRUF:
DriverManager.getConnection(URL, USER, PASSWORD);
// DriverManager ist die Klasse, nicht ein Objekt
// getConnection() ist eine statische Methode

// STATISCHE METHODE IN DBConnection
public static Connection getConnection() throws SQLException {
    // Diese Methode gehört zur Klasse DBConnection
}

// AUFRUF:
DBConnection.getConnection();
// DBConnection ist die Klasse, nicht ein Objekt
// getConnection() ist eine statische Methode
```

**Warum gibt es vor statischen Methoden eine Klasse statt Variable?**

Weil statische Methoden an der Klasse selbst "befestigt" sind, nicht an Objekten. Sie gelten für alle Instanzen oder für keine.

```java
// NICHT-STATISCH (gehört zu einem Objekt)
Connection conn = new Connection();  // Objekt erstellen
conn.close();  // Methode auf dem Objekt aufrufen

// STATISCH (gehört zur Klasse)
DriverManager.getConnection(...);  // Keine Objekt-Erstellung nötig
```

---

### 3.3 Rückgabetyp

**Definition:** Der Datentyp, den eine Methode zurückgibt (oder `void`, wenn nichts zurückkommt).

**Struktur:**
```java
public [Rückgabetyp] methodeName() {
    return [Wert vom Rückgabetyp];
}
```

**Beispiele aus dem Projekt:**

```java
// RÜCKGABETYP: Connection
public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;  // ← Gibt ein Connection-Objekt zurück
}

// Aufruf und Verwendung:
Connection conn = getConnection();  // conn erhält das Connection-Objekt

// RÜCKGABETYP: void (nichts)
public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
    // Kein return erforderlich
}

// Aufruf:
closeConnection();  // Nichts wird zurückgegeben
```

**Wie weiß der Compiler den Rückgabetyp?**

Der Rückgabetyp ist in der **Methodendefinition** festgelegt. Der Compiler prüft:
1. Die Methode wurde mit einem bestimmten Rückgabetyp deklariert
2. Die Methode gibt tatsächlich einen Wert dieses Typs zurück
3. Der Aufrufer kann den Wert erwarten

```java
// Definition
public static Connection getConnection() throws SQLException {
    // Typ = Connection
    // Compiler weiß: Diese Methode gibt ein Connection-Objekt zurück
    return connection;
}

// Aufruf
Connection conn = getConnection();
// Compiler weiß: getConnection() gibt Connection zurück
// conn wird als Connection behandelt
```

---

## 4. JDBC & DATENBANKVERBINDUNG

### 4.1 JDBC (Java Database Connectivity)

**Definition:** JDBC ist eine **API** (Application Programming Interface), die Java-Programmen ermöglicht, mit Datenbanken zu kommunizieren.

**Komponenten:**
- **Connection:** Stellt eine Verbindung zur Datenbank dar (Interface)
- **DriverManager:** Verwaltet JDBC-Treiber und erstellt Verbindungen (Werkzeugklasse)
- **SQLException:** Fehler bei Datenbankoperationen

**Beispiel aus dem Projekt:**

```java
import java.sql.Connection;      // Interface für Datenbankverbindung
import java.sql.DriverManager;   // Werkzeugklasse für Verbindungen
import java.sql.SQLException;    // Fehlerbehandlung

// JDBC in Aktion
Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
// DriverManager verwaltet den Verbindungsaufbau
// Rückgabe: Ein Connection-Objekt
```

---

### 4.2 DriverManager

**Definition:** Eine **Werkzeugklasse** aus der java.sql-Bibliothek, die:
1. JDBC-Treiber verwaltet (z. B. MySQL-Treiber)
2. Datenbankverbindungen erstellt
3. Verbindungen zu verschiedenen Datenbanken ermöglicht

**Wichtig:** DriverManager ist eine Klasse, nicht eine Variable.

**Statische Methode in DriverManager:**

```java
public static Connection getConnection(String url, String user, String password) throws SQLException {
    // Diese Methode:
    // 1. Findet den richtigen JDBC-Treiber basierend auf der URL
    // 2. Baut eine Verbindung zur Datenbank auf
    // 3. Gibt ein Connection-Objekt zurück
}
```

**Beispiel aus dem Projekt:**

```java
private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
private static final String USER = "root";
private static final String PASSWORD = "1234";

// DriverManager.getConnection() wird aufgerufen
connection = DriverManager.getConnection(URL, USER, PASSWORD);
// DriverManager = Klasse (nicht Variable)
// getConnection() = statische Methode (gehört zur Klasse)
// URL, USER, PASSWORD = Parameter (was wird benötigt?)
// Rückgabewert = Connection-Objekt (wird in connection gespeichert)
```

**Warum DriverManager und nicht direkt Connection?**

DriverManager ist ein **Factory**-Objekt:
- Es "fabriziert" (erstellt) Connection-Objekte
- Es kümmert sich um die technischen Details
- Der Programmierer muss nur URL, Benutzer, Passwort angeben

---

### 4.3 Connection (Interface)

**Definition:** Ein **Interface** (Schnittstelle) aus java.sql, das eine **offene Datenbankverbindung** darstellt.

**Wichtig:** Connection ist KEIN Objekt selbst, sondern ein **Typ/Vertrag**:
- Der DriverManager gibt ein Objekt zurück, das das Connection-Interface implementiert
- Die konkrete Klasse ist vom Treiber abhängig (MySQL, PostgreSQL, Oracle, etc.)

---

**Interface vs. Klasse — Im Code unsichtbar**

**Das sieht man im Code:**
```java
Connection connection;  // ← Nur der Typname
private static Connection connection;
```

**Das sieht man NICHT im Code:**
- Ob `Connection` eine Klasse oder ein Interface ist
- Keine Schlüsselwörter wie `interface Connection` in der Deklaration
- Der Compiler weiß es, aber der Code zeigt es nicht

**Wie erkenne ich trotzdem, ob es Interface oder Klasse ist?**

| Methode | Beschreibung | Beispiel |
|---------|-------------|---------|
| **Java-Dokumentation** | `java.sql.Connection` ist als Interface gekennzeichnet | https://docs.oracle.com/javase/8/docs/api/java/sql/Connection.html |
| **IDE: Hover** | Maus über `Connection` halten | Zeigt: "interface Connection" |
| **IDE: Ctrl+Click** | Click auf `Connection` → Quellcode öffnet sich | Code zeigt: `interface Connection { }` |
| **Erkennung im Code** | Kann man nicht sehen, nur aus Dokumentation | Im Projekt: trusted source (java.sql) |

**Wichtig: Im Projekt ist es meist NICHT nötig, dies zu wissen!**

```java
Connection connection;  // ← Es ist egal, ob Interface oder Klasse
connection.close();     // ← Beide haben die Methode close()
connection.isClosed();  // ← Beide haben die Methode isClosed()
```

---

**Was ist der praktische Unterschied?**

- **Interface:** Ein "Vertrag" — mehrere Klassen können es implementieren
- **Klasse:** Eine konkrete Implementierung

**Beispiel aus der Realität:**

```java
// DriverManager.getConnection() gibt NICHT direkt ein Connection-Objekt zurück
// Stattdessen gibt es eine Implementierung zurück, z. B.:

// Bei MySQL: MySQL's Connection-Implementierung
// Bei PostgreSQL: PostgreSQL's Connection-Implementierung
// Bei Oracle: Oracle's Connection-Implementierung

// Für den Programmierer ist es egal:
Connection conn = DriverManager.getConnection(url, user, password);
conn.close();  // ← Funktioniert bei allen Treibern gleich
```

---

**Merkregel:**

**Im Code sieht man keinen Unterschied zwischen Interface und Klasse**, wenn man sie als Typ verwendet. Der Unterschied ist nur:
- In der Dokumentation
- In der IDE
- In der tatsächlichen Implementierung (unsichtbar für dich)

Für Anfänger: **Nicht wichtig zu unterscheiden, nur zu wissen, dass es Methoden gibt, die man nutzen kann.**

**Häufig genutzte Methoden:**

```java
Connection connection = DriverManager.getConnection(url, user, password);

// Methode 1: Abfrage, ob verbindung offen ist
boolean closed = connection.isClosed();
// Gibt true zurück, wenn die Verbindung geschlossen ist
// Gibt false zurück, wenn die Verbindung offen ist

// Methode 2: Verbindung schließen
connection.close();
// Beendet die Datenbankverbindung

// Methode 3: SQL-Statement erstellen
Statement stmt = connection.createStatement();
// Wird verwendet, um SQL-Befehle auszuführen
```

**Beispiel aus dem Projekt:**

```java
public static Connection getConnection() throws SQLException {
    // Prüfung: Ist die Verbindung null oder geschlossen?
    if (connection == null || connection.isClosed()) {
        // Wenn ja, neue Verbindung erstellen
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}

public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();  // ← Methode aus dem Connection-Interface
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
```

---

### 4.4 isClosed()

**Definition:** Eine Methode der **Connection**-Schnittstelle, die prüft, ob eine Datenbankverbindung geschlossen ist.

**Rückgabewert:**
- `true`: Verbindung ist geschlossen
- `false`: Verbindung ist offen

**Syntax:**

```java
boolean status = connection.isClosed();
```

**Woher kommt isClosed()?**

- Es ist eine Methode der `java.sql.Connection`-Schnittstelle (Standard-Java-Bibliothek)
- Sie wird vom JDBC-Treiber (z. B. MySQL-Treiber) implementiert
- Sie ist Teil des Java-Standards, nicht selbst geschrieben

**Beispiel aus dem Projekt:**

```java
public static Connection getConnection() throws SQLException {
    // Prüfung in zwei Teilen:
    if (connection == null || connection.isClosed()) {
        // 1. connection == null: Wurde die Verbindung noch nie erstellt?
        // 2. connection.isClosed(): Ist die Verbindung geschlossen?
        
        // Wenn ja → neue Verbindung erstellen
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}
```

**Warum beide Checks?**

```java
// Check 1: null-Prüfung
if (connection == null) {
    // Erste Nutzung: connection wurde noch nie erstellt
}

// Check 2: isClosed()-Prüfung
if (connection.isClosed()) {
    // Wiederverwendung: connection existiert, aber wurde geschlossen
}

// Kombiniert mit OR (||):
if (connection == null || connection.isClosed()) {
    // Beide Fälle: Neu erstellen, wenn connection leer ODER geschlossen ist
}
```

**Häufiger Fehler:**

```java
// FALSCH: null-Prüfung vergessen
if (connection.isClosed()) {  // ❌ NullPointerException, wenn connection == null
    connection = DriverManager.getConnection(...);
}

// RICHTIG:
if (connection == null || connection.isClosed()) {  // ✓
    connection = DriverManager.getConnection(...);
}
```

---

## 5. ENTWURFSMUSTER & KONZEPTE

### 5.1 Lazy Initialization

**Definition:** Ein Entwurfsmuster, bei dem eine Ressource (z. B. eine Datenbankverbindung) erst dann erstellt wird, wenn sie **zum ersten Mal benötigt** wird (nicht beim Start der Anwendung).

**Vorteile:**
- Schnellerer Programmstart
- Ressourcen werden nur erstellt, wenn nötig
- Speichereffizienz

**Beispiel aus dem Projekt:**

```java
public static Connection getConnection() throws SQLException {
    // Lazy Initialization in Aktion:
    if (connection == null || connection.isClosed()) {
        // Erste Nutzung: Verbindung wird JETZT erst erstellt
        // Nicht beim Programmstart, sondern bei erster Nutzung
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}
```

**Vergleich:**

```java
// OHNE Lazy Initialization (Eager Initialization)
// Datenbankverbindung beim Start erstellen
public class DBConnection {
    private static Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    // Problem: Fehler beim Start, wenn DB nicht verfügbar
}

// MIT Lazy Initialization
// Datenbankverbindung erst bei Bedarf erstellen
public class DBConnection {
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
```

---

### 5.2 Singleton-Pattern

**Definition:** Ein Entwurfsmuster, das sicherstellt, dass eine Klasse **nur eine Instanz** (ein Objekt) im gesamten Programm hat.

**Merkmale:**
1. **Privater Konstruktor:** Verhindert, dass von außen Objekte erstellt werden
2. **Statische Variable:** Hält die einzige Instanz
3. **Statische Getter-Methode:** Rückgabe der einzigen Instanz

**Beispiel aus dem Projekt:**

```java
public class DBConnection {
    // 1. Statische Variable (nur eine Instanz)
    private static Connection connection;
    
    // 2. Privater Konstruktor (verhindert neue Objekte)
    private DBConnection() {
        // Singleton Muster = eine Datenbankverbindung pro Anwendung geben soll
    }
    
    // 3. Statische Getter-Methode (rückgabe der Instanz)
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
```

**Warum Singleton für Datenbankverbindung?**

```java
// PROBLEM ohne Singleton: Mehrere Verbindungen
Connection conn1 = new DBConnection();  // Neue Instanz
Connection conn2 = new DBConnection();  // Noch eine neue Instanz
Connection conn3 = new DBConnection();  // Und noch eine...
// Zu viele Verbindungen → Datenbankserver wird überlastet

// LÖSUNG mit Singleton: Eine Verbindung
Connection conn = DBConnection.getConnection();  // Erste Nutzung: wird erstellt
Connection conn2 = DBConnection.getConnection();  // Zweite Nutzung: wird wiederverwendet
// Nur eine Verbindung → Ressourcen effizient genutzt
```

**Aufruf von außen:**

```java
// So wird das Singleton genutzt:
Connection conn = DBConnection.getConnection();
// Nicht: new DBConnection() ← Das geht nicht, Konstruktor ist privat

// Der private Konstruktor verhindert:
DBConnection db = new DBConnection();  // ❌ Compiler-Fehler: privat!
```

---

### 5.3 Überladung (Overloading)

**Definition:** Mehrere Methoden mit **gleichem Namen**, aber **unterschiedlichen Parametern**.

**Regel:** Parameter müssen sich unterscheiden in:
- Anzahl der Parameter
- Typ der Parameter
- Reihenfolge der Parameter

**Beispiel: DriverManager.getConnection()**

```java
// ÜBERLADUNG 1: Mit URL, Benutzer, Passwort
public static Connection getConnection(String url, String user, String password) 
    throws SQLException { }

// ÜBERLADUNG 2: Mit URL und Properties
public static Connection getConnection(String url, Properties info) 
    throws SQLException { }

// ÜBERLADUNG 3: Mit nur URL
public static Connection getConnection(String url) 
    throws SQLException { }
```

**Aufruf:**

```java
// Aufruf 1: Mit 3 Parametern
Connection conn1 = DriverManager.getConnection(URL, USER, PASSWORD);

// Aufruf 2: Mit Properties
Properties props = new Properties();
props.setProperty("user", "root");
props.setProperty("password", "1234");
Connection conn2 = DriverManager.getConnection(URL, props);

// Aufruf 3: Mit nur URL
Connection conn3 = DriverManager.getConnection(URL);
```

**Warum gibt es mehrere Versionen?**

- Flexibilität: Unterschiedliche Informationen bereitstellen
- Einfachheit: Nur die benötigten Parameter übergeben
- Lesbarkeit: Klar, was wird benötigt

---

## 6. FEHLERBEHANDLUNG

### 6.1 SQLException

**Definition:** Eine **Exception** (Ausnahmefehler) aus der java.sql-Bibliothek, die bei Datenbankfehlern geworfen wird.

**Beispiele für Fehler:**
- Datenbankserver nicht erreichbar
- Authentifizierung gescheitert (falscher Benutzer/Passwort)
- SQL-Fehler
- Verbindung unterbrochen

**Syntax im Code:**

```java
public static Connection getConnection() throws SQLException {
    // throws SQLException = Diese Methode kann einen SQLException werfen
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
}
```

**Fehlerbehandlung mit try-catch:**

```java
public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();  // Kann SQLException werfen
        } catch (SQLException e) {
            // Fehlerbehandlung
            System.err.println("Verbindung konnte nicht geschlossen werden: " 
                + e.getMessage());
        }
    }
}
```

**Zwei Strategien:**

```java
// STRATEGIE 1: throws (Fehler weitergeben)
public static Connection getConnection() throws SQLException {
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
    return connection;
}

// STRATEGIE 2: try-catch (Fehler abfangen)
public static void closeConnection() {
    try {
        connection.close();
    } catch (SQLException e) {
        System.err.println("Fehler: " + e.getMessage());
    }
}
```

**Wann welche Strategie?**

```java
// throws: Wenn der Aufrufer den Fehler behandeln soll
public static Connection getConnection() throws SQLException { }

// try-catch: Wenn die Methode selbst den Fehler behandelt
public static void closeConnection() {
    try { connection.close(); } 
    catch (SQLException e) { }
}
```

---

## 7. ZUSAMMENFASSUNG: BEGRIFFE & ZUORDNUNG

| Konzept | Typ | Beispiel | Erklärung |
|---------|-----|---------|-----------|
| **Connection** | Interface | `private static Connection connection;` | Stellt eine DB-Verbindung dar |
| **DriverManager** | Klasse | `DriverManager.getConnection(...)` | Erstellt Connection-Objekte |
| **getConnection()** | Methode (statisch) | `DriverManager.getConnection()` | Erzeugt eine neue DB-Verbindung |
| **isClosed()** | Methode | `connection.isClosed()` | Prüft, ob Verbindung geschlossen |
| **connection** | Variable | `connection = ...` | Speichert die DB-Verbindung |
| **.** | Operator | `connection.close()` | Zugriff auf Objekt-Methoden |
| **=** | Operator | `connection = value;` | Zuweisung eines Wertes |
| **Singleton** | Pattern | Private Konstruktor + statische Methode | Nur eine Instanz |
| **Lazy Init.** | Pattern | if (null) erstellen | Ressource erst bei Bedarf |
| **SQLException** | Exception | `throws SQLException` | Fehlerbehandlung |

---

## 8. HÄUFIGE ANFÄNGERFEHLER

### Fehler 1: Punkt vor Klassenname (ohne static)
```java
// ❌ FALSCH
Connection.close();

// ✓ RICHTIG
connection.close();  // connection ist Variable/Objekt
```

### Fehler 2: Null-Prüfung vergessen
```java
// ❌ FALSCH
if (connection.isClosed()) {  // NullPointerException
    connection = DriverManager.getConnection(...);
}

// ✓ RICHTIG
if (connection == null || connection.isClosed()) {
    connection = DriverManager.getConnection(...);
}
```

### Fehler 3: Verwirrung zwischen Methoden- und Variablennamen
```java
public static Connection getConnection() {  // Methodenname
    connection = DriverManager.getConnection(URL, USER, PASSWORD);  // Variable, nicht die Methode
    return connection;
}
```

### Fehler 4: Mehrere Connection-Objekte erstellen
```java
// ❌ FALSCH (nicht Singleton)
public class DBConnection {
    public static Connection createConnection() {
        return DriverManager.getConnection(...);  // Jedes Mal neue Verbindung
    }
}

// ✓ RICHTIG (Singleton)
public class DBConnection {
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(...);  // Nur eine
        }
        return connection;
    }
}
```

---

## 9. VERWENDUNG IM PROJEKT

**In der DBConnection-Klasse:**

```java
// 1. Konstanten (Verbindungsdaten)
private static final String URL = "jdbc:mysql://127.0.0.1:3324/DOIT?serverTimezone=Europe/Berlin";
private static final String USER = "root";
private static final String PASSWORD = "1234";

// 2. Singleton-Variable
private static Connection connection;

// 3. Privater Konstruktor (Singleton)
private DBConnection() { }

// 4. getConnection() mit Lazy Initialization
public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
}

// 5. closeConnection() mit Fehlerbehandlung
public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
```

**Verwendung in DAOs:**

```java
// Verbindung abrufen
Connection conn = DBConnection.getConnection();

// SQL-Befehle ausführen
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM Material");

// Wenn fertig: Verbindung schließen
DBConnection.closeConnection();
```

---

## 10. BEST PRACTICES

1. **Immer null-Prüfung vor Methoden-Aufruf auf Objekten**
   ```java
   if (connection != null && !connection.isClosed()) {
       connection.close();
   }
   ```

2. **Datenbankverbindung am Programmende schließen**
   ```java
   Runtime.getRuntime().addShutdownHook(new Thread(() -> {
       DBConnection.closeConnection();
   }));
   ```

3. **Passwörter nicht im Code hardcodieren**
   ```java
   // ❌ Besser nicht
   private static final String PASSWORD = "1234";
   
   // ✓ Besser: Konfigurationsdatei nutzen
   ```

4. **Connection-Pool für Performance** (advanced)
   ```java
   // Für größere Projekte: Connection-Pooling statt Singleton
   // z. B. HikariCP, Apache Commons DBCP
   ```

5. **Statische Imports für bessere Lesbarkeit**
   ```java
   import static java.sql.DriverManager.getConnection;
   ```

---

## 11. SCHNELLREFERENZ

```java
// STRUKTUR EINER JDBC-ABFRAGE
Connection connection = DBConnection.getConnection();  // Verbindung abrufen
Statement stmt = connection.createStatement();          // Statement erstellen
ResultSet rs = stmt.executeQuery("SELECT ...");        // Abfrage ausführen
while (rs.next()) {
    // Daten verarbeiten
}
rs.close();                                            // Ressourcen freigeben
stmt.close();
```

---

**Glossar erstellt:** 2026-05-21  
**Projekt:** DOIT Lagerverwaltung  
**Java-Version:** 11+  
**JDBC-Treiber:** MySQL Connector/J
