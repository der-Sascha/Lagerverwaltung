# 📚 LERNMATERIAL: Java JDBC im Lagerverwaltungs-Projekt

**3-Datei-System für effizientes Lernen und Nachschlauen**

---

## 🎯 WELCHE DATEI WANN?

### 1️⃣ SPICKZETTEL.md — Schnell nachschlagen ⚡

**Größe:** 4,7 KB | **Länge:** ~170 Zeilen | **Zeit:** 2-3 Min lesen

**Nutze das, wenn:**
- ✅ Du **schnell erinnern** möchtest, wie etwas funktioniert
- ✅ Du **während des Programmierens** eine kurze Info brauchst
- ✅ Du **Fehlermeldungen verstehen** möchtest
- ✅ Du **Code-Zeilen lesen** und verstehen möchtest

**Inhalt:**
- Statische vs. Instanzmethoden (Tabelle)
- Punkt-Operator Regeln
- JDBC-Kurz-Übersicht
- DBConnection-Pattern
- Häufige Fehler
- Code richtig lesen (3 Schritte)

**Merksätze:** 3 Sätze zum Auswendiglernen

---

### 2️⃣ GLOSSAR.md — Alles detailliert 📖

**Größe:** 37 KB | **Länge:** 1.300+ Zeilen | **Zeit:** 20-30 Min (vollständig)

**Nutze das, wenn:**
- ✅ Du **Details verstehen** möchtest
- ✅ Du **Konzepte tiefer lernen** willst
- ✅ Du **Beispiele sehen** möchtest
- ✅ Du **Fehler analysieren** willst
- ✅ Du **Best Practices** erfahren möchtest

**Inhalt (11 Kapitel):**
1. Code richtig lesen — Erkennungsmuster
2. Grundoperatoren (. und =)
3. Variablen & Typen
4. Methoden (Deklaration, Aufruf, statisch)
5. JDBC & Datenbankverbindung
6. Entwurfsmuster (Singleton, Lazy Init, Overloading)
7. Fehlerbehandlung (SQLException)
8. Zusammenfassung (Begriffe & Zuordnung)
9. Häufige Anfängerfehler
10. Verwendung im Projekt (DBConnection)
11. Best Practices

---

### 3️⃣ SPICKZETTEL_ERWEITERUNG.md — Tracking neuer Konzepte 🔄

**Größe:** 4,9 KB | **Länge:** ~195 Zeilen | **Zeit:** 5-10 Min lesen

**Nutze das, wenn:**
- ✅ Du **neue Konzepte** lernst (z. B. DAOs, Try-Catch)
- ✅ Du **evaluierst**, ob etwas auf den Spickzettel gehört
- ✅ Du das **Projekt erweiterst**
- ✅ Du **neue Phasen** startest

**Inhalt:**
- Kriterien: Wann kommt ein Konzept auf den Spickzettel?
- Phasen des Projekts (1-4) mit Konzept-Evaluations-Tabellen
- Checkpoints: Wann neu evaluieren?
- Template: Neue Konzepte evaluieren
- Beispiel: Try-Catch hinzufügen
- Status-Übersicht

---

## 🔄 WORKFLOW: So nutzt du das System

### Szenario 1: Ich programmiere und weiß nicht, was das bedeutet

```
Code sehen → SPICKZETTEL konsultieren
           → Immer noch verwirrt?
           → GLOSSAR.md (referenzierter Abschnitt)
           → Problem gelöst!
```

**Beispiel:**
- Code: `DriverManager.getConnection(URL, USER, PASSWORD);`
- Frage: "DriverManager, was ist das?"
- SPICKZETTEL: "Statische vs. Instanzmethoden" Tabelle
- Antwort: "DriverManager ist eine Klasse, getConnection() ist statisch"
- Weiterer Bedarf? → GLOSSAR Kapitel 4.2

---

### Szenario 2: Ich lerne neues Konzept (z. B. DAOs)

```
Phase 2 starten → Neues Konzept gelernt
                → SPICKZETTEL_ERWEITERUNG konsultieren
                → Erfüllt alle 4 Kriterien?
                → JA: Auf Spickzettel hinzufügen
                → NEIN: Im Glossar dokumentieren
                → Dokumentation aktualisieren
```

**Beispiel:**
- Neues Konzept: "Try-Catch Syntax"
- Kriterien-Check: ✅ ✅ ✅ ✅ (alle erfüllt)
- Entscheidung: "Auf Spickzettel!"
- Aktion: Neuen Abschnitt "🛡️ TRY-CATCH" in SPICKZETTEL.md einfügen
- Update: SPICKZETTEL_ERWEITERUNG.md aktualisieren

---

### Szenario 3: Ich bin verwirrt und verstehe einfach nicht

```
Fehler im Code → SPICKZETTEL "Häufige Fehler"
               → Ist es dabei?
               → JA: Lösung sofort
               → NEIN: Code richtig lesen (3 Schritte)
               → Immer noch nicht: GLOSSAR Kapitel 0
               → Deep Dive in relevante Kapitel
```

---

## 📋 STRUKTUR ZUSAMMENGEFASST

```
SPICKZETTEL.md (Kurz, essentiell)
    ↓
    ├─ Du verstehst es schnell? → Weiterprogrammieren
    ├─ Du brauchst Details? → GLOSSAR.md
    └─ Du brauchst neue Konzepte? → Phase 2+

GLOSSAR.md (Detailliert, alle Erklärungen)
    ↓
    ├─ Kapitel 0: Code richtig lesen
    ├─ Kapitel 1-7: Alle Konzepte
    ├─ Kapitel 8-11: Zusammenfassung, Fehler, Best Practices
    └─ Verweise auf IDE-Funktionen (Ctrl+Click, Hover)

SPICKZETTEL_ERWEITERUNG.md (Meta-Tracking)
    ↓
    ├─ Phasen 1-4 des Projekts
    ├─ Evaluation neuer Konzepte
    ├─ Checkpoints & Kriterien
    └─ Template & Beispiele
```

---

## ✅ PHASEN DES PROJEKTS

### Phase 1: Datenbankverbindung (✅ JETZT)

**Gelernte Konzepte:**
- Statische vs. Instanzmethoden
- Punkt-Operator
- JDBC (Connection, DriverManager)
- Singleton-Pattern
- Lazy Initialization

**Auf Spickzettel:** 6 Abschnitte
**Im Glossar:** 11 Kapitel
**Status:** ✅ Komplett dokumentiert

---

### Phase 2: DAOs & Queries (🔜 BALD)

**Neue Konzepte zu lernen:**
- Statement / ResultSet
- Try-Catch Syntax
- SQL-Injection (Sicherheit)
- PreparedStatement
- SQL Grundlagen

**Evaluation:**
- Welche gehören auf Spickzettel?
- Welche nur ins Glossar?
→ **Nutze SPICKZETTEL_ERWEITERUNG.md!**

---

### Phase 3: Collections (📅 SPÄTER)

**Neue Konzepte:**
- ArrayList
- Generics `<Type>`
- List, Set, Map
- ...

---

### Phase 4: GUI & MVC (📅 SPÄTER)

**Neue Konzepte:**
- Controller-Pattern
- FXML
- Event Handling
- ...

---

## 🎓 LERNSTRATEGIE

**Empfohlener Ablauf:**

1. **Erste Woche:** GLOSSAR Kapitel 0 + 1 lesen (30 Min)
   - Verstehe, wie Code zu lesen ist

2. **Täglich während Programmierung:** SPICKZETTEL konsultieren
   - Schnelle Orientierung

3. **Bei Fehlern:** SPICKZETTEL "Häufige Fehler" checken
   - Problem oft sofort gelöst

4. **Bei Verwirrung:** GLOSSAR relevante Kapitel lesen
   - Tieferes Verständnis

5. **Neue Phasen:** SPICKZETTEL_ERWEITERUNG nutzen
   - Neue Konzepte richtig dokumentieren

---

## 📊 ÜBERSICHT: UMFANG

| Datei | Größe | Länge | Zweck | Lesedauer |
|-------|-------|-------|-------|-----------|
| SPICKZETTEL.md | 4,7 KB | ~170 | Schnell nachschlagen | 2-3 Min |
| GLOSSAR.md | 37 KB | 1.300+ | Detailliert lernen | 20-30 Min |
| SPICKZETTEL_ERWEITERUNG.md | 4,9 KB | ~195 | Meta-Tracking | 5-10 Min |
| **GESAMT** | **~46 KB** | **~1.700** | **Komplett** | **30-45 Min** |

---

## 🔗 DATEIEN ÖFFNEN

- [SPICKZETTEL.md](SPICKZETTEL.md) — Schnell nachschlagen
- [GLOSSAR.md](GLOSSAR.md) — Detailliert lernen
- [SPICKZETTEL_ERWEITERUNG.md](SPICKZETTEL_ERWEITERUNG.md) — Tracking

---

## 💡 TIPPS ZUR NUTZUNG

1. **Lesezeichen setzen** (Browser/IDE)
   - SPICKZETTEL.md in IDE-Favoriten
   - GLOSSAR.md für Deep-Dives

2. **Ausdrucken**
   - SPICKZETTEL.md (1 Seite) neben dem Monitor

3. **Suchen verwenden**
   - GLOSSAR.md: Strg+F nach Begriff
   - SPICKZETTEL.md: Strg+F für schnelles Finden

4. **Mit Code experimentieren**
   - GLOSSAR-Beispiele kopieren
   - Im IDE-Editor testen
   - Fehler absichtlich machen (Lernen!)

5. **Regelmäßig updaten**
   - Nach jeder neuen Phase
   - SPICKZETTEL_ERWEITERUNG.md konsultieren

---

## ❓ FAQ

**F: Sollte ich alles im Glossar auswendig lernen?**
A: Nein! Konzepte verstehen ist wichtiger. IDE und Dokumentation helfen immer.

**F: Wie oft sollte ich Spickzettel konsultieren?**
A: Täglich während Programmierung. Nach jeder Fehlermeldung. Bei Verwirrung.

**F: Wann wird Spickzettel erweitert?**
A: Am Ende jeder Phase. Nutze SPICKZETTEL_ERWEITERUNG.md als Anleitung.

**F: Ist das Glossar zu lang?**
A: Es ist **eine Referenz**, nicht zum Durchlesen. Nutze Strg+F zum Suchen.

**F: Was, wenn ich etwas nicht finde?**
A: 1. Spickzettel durchsuchen
   2. Glossar-Inhaltsverzeichnis nutzen
   3. Strg+F verwenden
   4. IDE-Hilfe nutzen (Ctrl+Click)

---

**Zuletzt aktualisiert:** 2026-05-21  
**Projekt:** DOIT Lagerverwaltung  
**Phase:** 1 (Datenbankverbindung) ✅  
**Status:** Komplett dokumentiert und einsatzbereit
