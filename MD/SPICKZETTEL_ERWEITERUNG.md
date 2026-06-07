# 🔄 SPICKZETTEL-ERWEITERUNG: Tracking neuer Konzepte

**Ziel:** Während das Projekt wächst, neue wichtige Konzepte identifizieren und auf SPICKZETTEL.md ergänzen.

---

## 📋 KRITERIEN: Kommt ein Konzept auf den Spickzettel?

**Alle 4 Fragen müssen mit JA beantwortet sein:**

1. ✅ **Code-Verständnis:** Brauchst du es zum Verstehen von Code?
2. ✅ **Häufige Verwirrung:** Wirst du oft davon verwirrt?
3. ✅ **Kompakt:** Lässt es sich in 3 Zeilen oder kürzer erklären?
4. ✅ **Häufig verwendet:** Brauchst du es täglich im Projekt?

**Falls alle JA:** Hinzufügen auf SPICKZETTEL.md
**Falls mind. 1 NEIN:** Nur im GLOSSAR.md dokumentieren

---

## 📍 PHASEN DES PROJEKTS & NEUE KONZEPTE

### Phase 1: Datenbankverbindung (✅ AKTUELL)

**Jetzt auf Spickzettel:**
- Statische vs. Instanzmethoden
- Punkt-Operator
- Typname (Interface/Klasse unsichtbar)
- JDBC-Kurz
- DBConnection-Pattern
- Häufige Fehler

---

### Phase 2: DAOs & Queries (📌 BALD)

**Konzepte zu evaluieren:**

| Konzept | Code-Verständnis | Häufig verwirrend | Kompakt | Häufig | Spickzettel? |
|---------|-----------------|------------------|---------|--------|-------------|
| **Statement** | ✅ | ❓ | ✅ | ✅ | ? |
| **ResultSet** | ✅ | ❓ | ✅ | ✅ | ? |
| **Try-Catch** | ✅ | ✅ | ✅ | ✅ | **✅ JA** |
| **SQL-Injection** | ✅ | ✅ | ✅ | ✅ | **✅ JA** |
| **PreparedStatement** | ✅ | ✅ | ✅ | ✅ | **✅ JA** |
| **DAO-Pattern** | ✅ | ✅ | ❌ | ✅ | ❌ NEIN (zu komplex) |
| **Generics** | ✅ | ❓ | ❌ | ✅ | ❌ NEIN (zu komplex) |

**Vorschlag für Phase 2:**
- Try-Catch hinzufügen
- SQL-Injection warnung
- PreparedStatement vs. Statement

---

### Phase 3: Collections & Listen (📌 SPÄTER)

**Konzepte zu evaluieren:**

| Konzept | Kandidat? |
|---------|-----------|
| **ArrayList** | Wahrscheinlich ✅ |
| **Generics `<Type>`** | Wahrscheinlich ✅ |
| **List, Set, Map** | Schwer kompakt — eher GLOSSAR |
| **Iterator** | Eher GLOSSAR |

---

### Phase 4: GUI & MVC (📌 SPÄTER)

**Konzepte zu evaluieren:**

| Konzept | Kandidat? |
|---------|-----------|
| **Controller** | Kurz erklärt — ✅ |
| **FXML vs. Code** | Schwer kompakt — GLOSSAR |
| **Event Handling** | Schwer kompakt — GLOSSAR |

---

## 🔍 CHECKPOINTS: Wann evaluieren?

**Nach jeder neuen Feature/Kapitel im Projekt:**

1. **Was ist neu gelernt?**
   - Liste die Konzepte auf

2. **Erfüllt es die 4 Kriterien?**
   - Nutze die Tabelle oben

3. **Füge hinzu oder dokumentiere nur im GLOSSAR**
   - Spickzettel: Kurz hinzufügen
   - GLOSSAR: Detailliert dokumentieren

4. **Aktualisiere diese Datei**
   - Markiere als "✅ Hinzugefügt" oder "❌ Nur GLOSSAR"

---

## 🤖 AUTOMATISCHE TRIGGER & AUSFÜHRUNG

**System erkennt automatisch, wenn Spickzettel aktualisiert werden muss:**

### Trigger 1: Phase-Wechsel (Freitag-Evaluation)
- Jeden Freitag: Prüfe, ob neue Phase begonnen
- Beispiel: Phase 1 (Verbindung) → Phase 2 (DAOs)
- Aktion: Evaluation-Tabelle konsultieren → neue Konzepte hinzufügen

### Trigger 2: Code-Kommentar-Trigger (Manuell)
- In deinem Code oder Notizen: `//SPICKZETTEL: Try-Catch` einfügen
- Format: `//SPICKZETTEL: [KONZEPT-NAME]`
- Aktion: Konzept wird erkannt, Kriterien geprüft, hinzugefügt oder ins GLOSSAR dokumentiert

### Trigger 3: Fehleranalyse-Trigger
- Schreib im Projekt oder Chat: "Das verwirrt mich oft: [KONZEPT]"
- System prüft Häufigkeit → wenn oft: Auf Spickzettel
- Beispiel: "Das verwirrt mich oft: SQL-Injection" → Phase 2 Evaluation

### Trigger 4: Expliziter Befehl
- Direkt im Chat: "Ergänze SPICKZETTEL mit Try-Catch"
- Aktion: Sofortige Evaluation + Hinzufügen

---

## 📝 TEMPLATE: Neues Konzept evaluieren

**Kopiere dies, wenn ein neues Konzept evaluiert wird:**

```
### Phase X: [THEMA]

**Neues Konzept: [NAME]**

- Code-Verständnis: [✅/❌]
- Häufig verwirrend: [✅/❌]
- Kompakt (≤3 Zeilen): [✅/❌]
- Häufig verwendet: [✅/❌]

**Entscheidung:** [✅ SPICKZETTEL / ❌ NUR GLOSSAR]

**Falls SPICKZETTEL:** 
- Kurze Erklärung:
  ```
  [TEXT]
  ```
- Beispiel:
  ```java
  [CODE]
  ```

**Falls NUR GLOSSAR:**
- Link: GLOSSAR.md → Kapitel X.X
```

---

## 🚀 BEISPIEL: Try-Catch hinzufügen

**Szenario:** Du arbeitest an Phase 2 (DAOs) und merkst, dass Try-Catch super wichtig ist.

**Evaluation:**
- Code-Verständnis: ✅ (Fehlerbehandlung essentiell)
- Häufig verwirrend: ✅ (Syntax ist knifflig)
- Kompakt: ✅ (3 Zeilen)
- Häufig verwendet: ✅ (in jedem DAO)

**Entscheidung:** ✅ Auf Spickzettel!

**Dann in SPICKZETTEL.md hinzufügen:**

```markdown
## 🛡️ TRY-CATCH (Fehlerbehandlung)

```java
try {
    Connection conn = DBConnection.getConnection();
    // Code hier
} catch (SQLException e) {
    System.err.println("Fehler: " + e.getMessage());
}
```

**Wann:** Datenbankoperationen, die scheitern können
**Details:** GLOSSAR Kapitel 6.1
```

---

## 📊 AKTUELLE STATUS

| Phase | Status | Spickzettel | Glossar |
|-------|--------|-------------|---------|
| 1: Verbindung | ✅ Aktiv | ✅ 6 Einträge | ✅ 11 Kapitel |
| 2: DAOs | 🔜 Bald | ⏳ In Evaluation | ⏳ In Vorbereitung |
| 3: Collections | 📅 Plan | ⏳ Später | ⏳ Später |
| 4: GUI/MVC | 📅 Plan | ⏳ Später | ⏳ Später |

---

## 🔗 DATEIEN

- **SPICKZETTEL.md** — Das Wichtigste (kurz)
- **GLOSSAR.md** — Alles Detailliert (lang)
- **SPICKZETTEL_ERWEITERUNG.md** — Diese Datei (Tracking)

---

## 💡 REGELN FÜR SPICKZETTEL-ERWEITERUNG

1. **Weniger ist mehr** — Nur essentiell
2. **Konsistent formatieren** — Tabellen, Code-Blöcke, Bullet Points
3. **Immer mit GLOSSAR verlinken** — "Details → GLOSSAR Kapitel X.X"
4. **Monatlich reviewen** — Ist noch alles aktuell?
5. **Diese Datei updaten** — Dokumentiere deine Entscheidungen

---

---

## 📋 WORKFLOW: SPICKZETTEL UPDATEN

**Situation: Neues Konzept in Code auftauchen**

```
Neue Phase/Fehler aufgetreten
    ↓
Konzept identifizieren (z.B. "Try-Catch")
    ↓
Trigger nutzen:
  - Phase-Wechsel: Automatisch Freitag
  - Code-Kommentar: //SPICKZETTEL: Try-Catch
  - Verwirrung: "Das verwirrt mich oft"
  - Befehl: "Ergänze SPICKZETTEL mit..."
    ↓
Kriterien checken (4x JA?)
    ↓
    JA → SPICKZETTEL.md + SPICKZETTEL.docx updaten
    NEIN → GLOSSAR.md updaten
    ↓
Diese Datei updaten (✅ oder ❌)
```

---

**Zuletzt aktualisiert:** 2026-05-21  
**Projekt:** DOIT Lagerverwaltung  
**Nächste Evaluation:** Nach Phase 2 (DAOs) oder manueller Trigger
**System Status:** ✅ Automatische Trigger aktiv
