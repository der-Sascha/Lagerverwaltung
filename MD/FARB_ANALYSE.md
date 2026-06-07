# 🎨 FARB-ANWENDUNGS-AUDIT: DOIT-Ordner

**Ziel:** Übersicht aller Dateien, die IDE-Syntax-Farben brauchen.  
**Farb-Schema:** "Sascha angepasst" (neue Farbwerte, siehe unten)

---

## 📊 FARB-SCHEMA (Referenz)

```
Keyword:           #000080 (fett)
Class:             #00627a (normal)
Interface:         #00627a (fett)
Enum:              #00627a (fett)
Abstract Class:    #00627a (fett)
Variable (lokal):  #1a7a1a (normal)
Parameter:         kein Farbe, fett kursiv
Type Parameter:    kein Farbe, fett kursiv + unterstrichen
Method (normal):   kein Farbe (schwarz)
Method (static):   kein Farbe, kursiv
Instance Field:    #660e7a (fett)
Static Field:      #660e7a (kursiv)
Static Final Field: #660e7a (fett kursiv)
String:            #008000 (fett)
Number:            #0000ff (normal)
Comment:           #808080 (kursiv)
Annotation:        #808000 (unterstrichen)
```

---

## 📁 DATEIEN MIT CODE-BEISPIELEN

### ✅ BEREITS AKTUALISIERT

| Datei | Status | Grund |
|-------|--------|-------|
| **SPICKZETTEL.docx** | ✅ Fertig | Neu erstellt mit allen Farben |
| **SPICKZETTEL.md** | ✅ Vorlage | Markdown (keine Farben nötig) |

---

### ⏳ MÜSSEN AKTUALISIERT WERDEN

#### 📚 LERNHANDBUCH (4 Dateien — HOHE PRIORITÄT)

| Datei | Code-Menge | Farb-Status | Notiz |
|-------|-----------|-----------|-------|
| **Teil1_Intro_Kap1-3.docx** | ⭐⭐⭐⭐⭐ | ❌ Alt | Viele JDBC-Beispiele, Konstruktoren, Klassen |
| **Teil2_Kap4-6_neu.docx** | ⭐⭐⭐⭐⭐ | ❌ Alt | DAOs, PreparedStatement, Queries |
| **Teil3_Kap7-9_neu.docx** | ⭐⭐⭐⭐ | ❌ Alt | Collections, ArrayList, Generics |
| **Teil4_Kap10-14_neu.docx** | ⭐⭐⭐⭐ | ❌ Alt | GUI, Controller, MVC-Pattern |

**Aktion:** Extract Code → Apply Colors → Recreate with docx-js

---

#### 📖 EINZELNE GLOSSARE & DOKUMENTATIONEN

| Datei | Code-Menge | Farb-Status | Notiz |
|-------|-----------|-----------|-------|
| **Lernhandbuch/Glossar_Vereinigt.docx** | ⭐⭐⭐ | ❌ Alt | Alphabetische Glossar-Einträge mit Code |
| **Ablage/.../Java_Handbuch_Start.docx** | ⭐⭐⭐ | ❌ Alt | Einführungs-Beispiele |
| **Ablage/.../Glossar_Java_API.docx** | ⭐⭐ | ❌ Alt | API-Referenz mit Code-Schnipseln |
| **Ablage/.../Java_Konzept_Zusammenhaenge.docx** | ⭐⭐ | ❌ Alt | Konzept-Diagramme, wenig Code |
| **Dokumentation/Benutzerhandbuch_Sascha_Schulz.docx** | ⭐⭐ | ⚠️ Minimal | Nur wenige SQL/Query-Beispiele |
| **Dokumentation/Projektdokumentation_Sascha_Schulz.docx** | ⭐ | ⚠️ Minimal | Überblick, fast kein Code |

**Aktion:** Extract → Colorize → Recreate

---

#### 📝 MARKDOWN-DATEIEN (Tracking & Planung)

| Datei | Code-Menge | Aktion |
|-------|-----------|--------|
| **GLOSSAR.md** | ⭐⭐⭐⭐ | Markdown-Syntax verwenden (backticks + ```java) — HTML nicht nötig |
| **SPICKZETTEL_ERWEITERUNG.md** | ⭐ | Nur Meta-Information, wenig Code |
| **Projektmanagement/*.md** | ⭐⭐ | Planung & Tagbuch — keine Farben nötig |

**Aktion:** Für Markdown: Syntax-Highlighting im Viewer, keine Farben ändern

---

#### 🏗️ BACKUP & ARCHIV (OPTIONAL)

| Datei | Status | Empfehlung |
|-------|--------|-----------|
| **Ablage/.../Doku_B/C/D/E.docx** | ❌ Alt | Optional — nur wenn aktiv verwendet |
| **Ablage/.../Projektdokumentation_backup.docx** | ❌ Alt | Optional — Archiv |
| **Archiv/Projektantrag_v2.docx** | ❌ Alt | Archiv — nicht aktualisieren |

---

## 🔍 DETAIL-ANALYSE NACH DATEI

### 1️⃣ TEIL1_INTRO_KAP1-3.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐⭐⭐ (sehr viel)  
**Beispiele:**
- Klassen-Deklarationen (`public class DBConnection`)
- Konstruktoren (`public DBConnection()`)
- JDBC-Imports (`import java.sql.*`)
- Singleton-Pattern Code
- Connection-Beispiele

**Farben zu prüfen:**
- `public` / `class` / `static` / `void` → #000080 (fett)
- `DBConnection`, `Connection`, `DriverManager` → #00627a (normal)
- `getConnection` → no color (Methode)
- Variablen-Namen → #1a7a1a

**Aktion:** 
1. DOCX entpacken → XML lesen
2. Alle `<w:t>` in Code-Blöcken identifizieren
3. Farbwerte in `<w:color>` + Styling eintragen
4. Neu packen

---

### 2️⃣ TEIL2_KAP4-6_NEU.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐⭐⭐ (sehr viel)  
**Beispiele:**
- DAO-Pattern-Code
- PreparedStatement-Beispiele
- SQL-Queries (Strings in #008000)
- Try-Catch-Blöcke
- ResultSet-Iteration

**Farben zu prüfen:**
- `try`, `catch`, `throw` → #000080 (fett)
- `Statement`, `ResultSet`, `SQLException` → #00627a (normal)
- `prepare`, `execute`, `next` → no color (Methode)
- SQL-String-Inhalte → #008000 (fett)
- Zahlen/Indices → #0000ff

**Aktion:**
1. DOCX entpacken
2. Try-Catch-Blöcke highlighten
3. SQL-Strings grün färben
4. Neu packen

---

### 3️⃣ TEIL3_KAP7-9_NEU.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐⭐  
**Beispiele:**
- ArrayList-Deklarationen
- Generics (`<Bestand>`)
- For-each-Loops
- Collections-Methoden

**Farben zu prüfen:**
- `ArrayList`, `List` → #00627a (normal/fett)
- Type-Parameter `<Bestand>` → no color (fett kursiv unterstrichen)
- `size()`, `add()`, `get()` → no color (Methode)

**Aktion:**
1. Generics korrekt formatieren
2. ArrayList-Deklarationen
3. Loop-Syntax

---

### 4️⃣ TEIL4_KAP10-14_NEU.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐⭐  
**Beispiele:**
- @FXML-Annotationen → #808000 (unterstrichen)
- Controller-Klassen → #00627a (normal)
- Event-Handler-Methoden → no color (Methode)
- Listener-Interfaces → #00627a (fett)

**Farben zu prüfen:**
- `@FXML`, `@Override` → #808000 (unterstrichen)
- `Stage`, `Button`, `TextField` → #00627a (normal)
- Event-Handler-Parameter → Parameter-Stil

---

### 5️⃣ GLOSSAR_VEREINIGT.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐ (mittel)  
**Struktur:**
- Alphabetische Einträge (A-Z)
- Jeder Eintrag hat Definition + Code-Beispiel

**Farben zu prüfen:**
- Keyword + Klasse in jedem Eintrag
- Konsistent mit Spickzettel färben

---

### 6️⃣ JAVA_HANDBUCH_START.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐⭐  
**Beispiele:**
- Basis-Syntax (Klassen, Methoden)
- Hello-World-Varianten
- Imports und Package-Struktur

---

### 7️⃣ GLOSSAR_JAVA_API.DOCX
**Status:** ❌ MUSS AKTUALISIERT WERDEN  
**Code-Menge:** ⭐⭐  
**Fokus:**
- API-Referenz (Kurz-Code-Schnipsel)
- Weniger Vollständigkeit als Glossar_Vereinigt

---

### 8️⃣ BENUTZERHANDBUCH_SASCHA_SCHULZ.DOCX
**Status:** ⚠️ OPTIONAL  
**Code-Menge:** ⭐⭐ (sehr wenig)  
**Inhalt:**
- Hauptsächlich Text/Anleitung
- Nur einige SQL-Query-Beispiele

**Wenn aktualisiert:** SQL-Strings → #008000 (fett)

---

## 🎯 PRIORITÄT & REIHENFOLGE

### PHASE 1: LERNHANDBUCH (kritisch)
1. Teil1_Intro_Kap1-3.docx
2. Teil2_Kap4-6_neu.docx
3. Teil3_Kap7-9_neu.docx
4. Teil4_Kap10-14_neu.docx

**Aufwand:** 4 × 30-40 min = 2-2,5 Std  
**Impact:** Sehr hoch (Kern-Lernmaterial)

---

### PHASE 2: GLOSSARE
1. Glossar_Vereinigt.docx
2. Java_Handbuch_Start.docx
3. Glossar_Java_API.docx

**Aufwand:** 3 × 20-30 min = 1-1,5 Std  
**Impact:** Hoch (Nachschlagewerk)

---

### PHASE 3: DOKUMENTATION (optional)
1. Benutzerhandbuch_Sascha_Schulz.docx
2. Projektdokumentation_Sascha_Schulz.docx (nur wenn viel Code)

**Aufwand:** 2 × 10-15 min = 0,5 Std  
**Impact:** Mittel

---

## 📋 CHECKLISTE ZUM KOPIEREN

```
FARB-UPDATE PLAN FÜR DOIT-ORDNER

📚 LERNHANDBUCH:
☐ Teil1_Intro_Kap1-3.docx
☐ Teil2_Kap4-6_neu.docx
☐ Teil3_Kap7-9_neu.docx
☐ Teil4_Kap10-14_neu.docx

📖 GLOSSARE:
☐ Glossar_Vereinigt.docx
☐ Java_Handbuch_Start.docx
☐ Glossar_Java_API.docx
☐ Java_Konzept_Zusammenhaenge.docx

📄 DOKUMENTATION:
☐ Benutzerhandbuch_Sascha_Schulz.docx
☐ Projektdokumentation_Sascha_Schulz.docx

✅ FERTIG:
☑ SPICKZETTEL.docx
```

---

## 🛠️ TECHNISCHE ANMERKUNGEN

**Für DOCX-Updates:**
- Verwende Python-Script `unpack.py` + `pack.py` (wie für Spickzettel)
- ODER: Rekonstruiere mit docx-js (wie bei Spickzettel) — schneller für viel Code
- ODER: Manuell in XML (langsam, aber möglich)

**Für MARKDOWN:**
- Nutze Code-Fence Syntax:
  ```java
  public class DBConnection {
      public static void main(String[] args) {}
  }
  ```
- Online-Viewer (GitHub/IDE) zeigt Syntax-Highlighting automatisch

**Farb-Konsistenz:**
- Alle Dateien sollen **exakt** das gleiche Farb-Schema verwenden
- Referenz-Datei: SPICKZETTEL.docx (als Vorbild)

---

**Erstellt:** 2026-05-21  
**Dateiformat:** Markdown  
**Nächster Schritt:** Phase 1 starten oder diesen Plan kopieren + einen neuen Chat öffnen
