# Donnerstags-Audit-Skript — DoIT Lagerverwaltung

**Automatisierter wöchentlicher Checkpoint (jeden Donnerstag ausführen)**

---

## Ablauf

### 1. Dateien laden
```
CLAUDE.md                          (Kontext + Person + Stack)
Projektplan.md                     (Phasen, Stunden, Meilensteine)
Dateiuebersicht.md                 (alle Dateien im Ordner)
Entscheidungen.md                  (Designentscheidungen D-001 bis D-006)
Projekttagebuch.md                 (laufendes Log)
Projektdateien\Wochenplan_Sascha_Schulz.xlsx   (Stunden-Tracking, falls vorhanden)
```

### 2. Prüfungen durchführen

#### A) Projektplan ↔ Tagebuch (Abweichungen?)
- [ ] **Aktuelle KW:** Welche Phase sollte diese Woche laufen (Projektplan)?
- [ ] **Tagebuch:** Welche Phase wird tatsächlich gemacht? Einträge für diese KW vorhanden?
- [ ] **Stunden:** Geplante Stunden vs. tatsächliche Stunden (aus Tagebuch oder Wochenplan)?
- [ ] **Rückstände:** Sind Aufgaben der Vorwoche offen? Falls ja: Problem-Eintrag im Tagebuch?

#### B) Entscheidungen ↔ Code (konsistent?)
- [ ] **D-001 (kein Bestandsfeld):** Hat `Material.java` kein `bestand`-Attribut?
- [ ] **D-002 (6 Tabellen):** Existieren alle 6 Models + 6 DAOs im Code?
- [ ] **D-003 (drawio Standard):** Spaltennamen in Code = Spaltennamen in `DOIT Krankenhaus.drawio`?
- [ ] **D-004 (DAO-Pattern):** `DBConnection.java` Singleton vorhanden? Alle DAOs nutzen es?
- [ ] **D-005 (lokal):** Verbindungsstring zeigt auf `localhost`?
- [ ] **D-006 (Stunden):** Antrag = 180h, Wochenplan = 192h (mit 12h Puffer)?

#### C) Dateiübersicht.md (aktuell?)
- [ ] Alle aufgelisteten Dateien existieren tatsächlich?
- [ ] Archiv-Ordner `Archiv/` aktuell?
- [ ] Vorgaben-Ordner `Vorgaben/` vollständig?
- [ ] Neue Dateien, die nicht in Übersicht aufgeführt sind?

#### D) Offene TODOs / Probleme (Tagebuch)
- [ ] Gibt es [Problem]-Einträge, die noch nicht gelöst sind?
- [ ] Gibt es [Änderung]-Einträge ohne entsprechenden Code-Status?
- [ ] Gibt es unbeantwortete Fragen / Blockierer?

#### E) Dokumentation (Fortschritt)
- [ ] Ist `Doku\Projektdokumentation_Sascha_Schulz.docx` (+ .pdf) aktualisiert?
- [ ] Fehlende Verzeichnisse: Abbildungs-/Tabellen-/Quellcode-/Quellenverzeichnis?
- [ ] Benutzerhandbuch im Anhang (Anhang C), nicht separat?

---

## Output-Format (Markdown-Tabelle)

```markdown
## Audit Donnerstag, DD.MM.YYYY (KW XX)

### Status Überblick

| Bereich | Phase | Soll | Ist | Status | Abweichung |
|---|---|---|---|---|---|
| Wochenplan | Ph. X | Y h | Z h | ✅/⚠️ | +/- N h |
| Tagebuch | — | aktuell | aktuell/outdated | ✅/⚠️ | — |
| Code | Ph. X | Model+DAO | ja/nein | ✅/⚠️ | — |
| Entscheidungen | alle | D-001–D-006 | implementiert | ✅/⚠️ | — |
| Doku | Phase 7 | — | % Fortschritt | ✅/⏳ | — |

### Detailfunde

**✅ Konsistent / Fertig:**
- ...

**⚠️ Abweichung / Fehlt:**
- ...

**📋 Empfehlung nächste Woche:**
- ...

### Tagebuch-Eintrag

[Automatisch generiert → am Ende in Projekttagebuch.md einfügen]

```

---

## Prüf-Checkliste (PDF-Download)

Diese Checkliste sollte jede Woche ausgefüllt werden (als Anlage im Tagebuch):

```
Donnerstags-Audit KW __

Projektplan vs. Tagebuch:
  [ ] Phase __ geplant
  [ ] Phase __ tatsächlich gemacht
  [ ] Stunden geplant: ___ h
  [ ] Stunden tatsächlich: ___ h
  [ ] Abweichung: +/- ___ h

Entscheidungen vs. Code:
  [ ] D-001 (kein Bestandsfeld): ✅/❌
  [ ] D-002 (6 Tabellen): ✅/❌
  [ ] D-003 (drawio Standard): ✅/❌
  [ ] D-004 (DAO-Pattern): ✅/❌
  [ ] D-005 (lokal): ✅/❌
  [ ] D-006 (Stunden): ✅/❌

Offene Punkte:
  [ ] Probleme aus Vorwoche gelöst?
  [ ] Neue Probleme aufgetreten?
  [ ] Blockierer vorhanden?

Dokumentation:
  [ ] Projektdoku aktualisiert?
  [ ] Verzeichnisse vollständig?
  [ ] Benutzerhandbuch integriert?

Nächste Woche:
  [ ] Stunden eingeplant korrekt
  [ ] Aufgaben klar
  [ ] Keine Blockierer
```

---

## Template für Tagebuch-Eintrag (automatisch)

```markdown
## YYYY-MM-DD (Donnerstags-Audit KW XX — automatisch)

- [Erkenntnis] **Phase X Status:**
  - Geplant: Y h
  - Tatsächlich: Z h
  - ✅/⚠️ im Plan / ⚠️ Rückstand X h

- [Problem] (falls vorhanden) — Beschreibung

- [Erkenntnis] **Code-Konsistenz:**
  - ✅ Alle Entscheidungen D-001–D-006 implementiert
  - ✅ Models + DAOs aktuell
  - ✅/⚠️ Dokumentation: Stand X%

- [Empfehlung] **Nächste Woche (KW XX+1):**
  - Phase X fortsetzen
  - Blockie rer Y auflösen
  - Dokumentation Verzeichnis Z ergänzen
```

---

## Durchführung (Anleitung für Claude)

1. **Lese alle 5 MD-Dateien** (CLAUDE.md, Projektplan.md, Dateiuebersicht.md, Entscheidungen.md, Projekttagebuch.md)
2. **Scanne Java-Projekt** mit Glob/Read auf:
   - Alle Models vorhanden?
   - Alle DAOs vorhanden?
   - DBConnection.java vorhanden?
   - Sind Spaltennamen konsistent mit Entscheidungen?
3. **Vergleiche Projektplan (KW) mit Tagebuch (tatsächlicher Fortschritt)**
4. **Erstelle Audit-Tabelle** mit Status ✅/⚠️
5. **Schreibe Audit-Eintrag** ins Tagebuch
6. **Update Dateiuebersicht.md**, falls Dateien fehlen/hinzukommen
7. **Gib kompakte Zusammenfassung** an Sascha (für seine Reflexion)

---

## Häufige Fragezeichen

**F: Was tun, wenn Tagebuch zu alt ist?**  
A: [Erkenntnis]-Eintrag schreiben: "Letzte Tagebuch-Einträge sind von Datum X. Aktuelle KW zeigt keine neuen Einträge — prüfen, ob Dokumentation zu spät erfolgt ist."

**F: Was tun, wenn Stunden nicht eingeplant sind?**  
A: [Problem]-Eintrag: "Wochenplan_Sascha_Schulz.xlsx fehlt oder ist nicht aktuell. Kann Stunden-Abweichung nicht prüfen."

**F: Blockierer vorhanden?**  
A: [Problem]-Eintrag mit Details + nächste Schritte zur Auflösung

**F: Dokumentation zu spät?**  
A: [Erkenntnis]: "Phase 7 (Doku) erst ab KW25 geplant. Aktuell KW21, noch X Wochen Zeit."

