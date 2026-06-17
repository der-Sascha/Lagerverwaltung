# Designentscheidungen — Lagerverwaltung Krankenhaus

Hier landen alle Architektur- und Modellierungsentscheidungen mit Begründung. Bei jeder neuen Entscheidung **Eintrag mit Datum** anhängen.

---

## D-001: Bestand wird nicht in `materialien` gespeichert

**Datum:** Mai 2026
**Entscheidung:** Tabelle `materialien` hat **kein** `bestand`-Feld. Der aktuelle Bestand pro Material und Lager wird zur Laufzeit aus `bestandsbewegungen` berechnet:

```sql
SUM(CASE WHEN bewegungstyp = 'EINGANG' THEN menge ELSE -menge END)
```

**Begründung:**
- Kein doppelter Datenstand möglich (Bestandsfeld vs. Bewegungssumme)
- Lückenlose Buchungshistorie ist Voraussetzung für Krankenhaus-Controlling
- Atomare Bewegungen sind nachvollziehbar

**Konsequenz:** Zu- und Abgänge werden ausschließlich als gebuchte Bestandsbewegungen erfasst (über den Reiter „Bestandsbewegungen“; eine Umlagerung als AUSGANG im Quell- und EINGANG im Ziellager). Der Bestellstatus (offen/geliefert/storniert) dokumentiert nur den Bestellvorgang. *(Hinweis 2026-06-17: die früheren Komfort-Buttons Warenentnahme/Umlagerung wurden entfernt, siehe D-009.)*

---

## D-002: 6 Tabellen, alle Pflicht (nicht 3 Kern + 3 optional)

**Datum:** Mai 2026
**Entscheidung:** Alle 6 Tabellen (`kategorien`, `materialien`, `stationslager`, `lieferanten`, `bestellungen`, `bestandsbewegungen`) sind Pflicht-Bestandteil.

**Begründung:**
- SRH-Vorgabe: **mind. 5 Tabellen** — bei nur 3 Kerntabellen wäre Anforderung verletzt
- `bestandsbewegungen` ist zwingend, weil Bestand daraus berechnet wird (siehe D-001)
- `bestellungen` + `lieferanten` ergeben gemeinsam einen sinnvollen Workflow (Nachbestellung bei Warnung)

**Optionale Erweiterungen** sind stattdessen: Login/Rollen, PDF-Export, Reports, DB-Backup.

---

## D-003: drawio ist Standard für ER-Modell

**Datum:** Mai 2026
**Entscheidung:** `DOIT Krankenhaus.drawio` ist die **maßgebliche Quelle** für Tabellen- und Spaltennamen. Alle anderen Dokumente (ER-Diagramm.docx, Projektdoku, SQL-Skript) müssen daran angeglichen sein.

**Standard-Spaltennamen lt. drawio:**

| Tabelle | PK | Spalten |
|---|---|---|
| kategorien | kategorie_ID | name, beschreibung |
| materialien | material_ID | name, einheit, mindestbestand, *kategorie_ID* |
| lieferanten | lieferant_ID | **name** (nicht „firmenname"), kontakt, telefon, email |
| stationslager | lager_ID | name, standort, typ |
| bestellungen | bestellung_ID | menge, bestelldatum, lieferdatum, status, *material_ID, lieferant_ID, lager_ID* |
| bestandsbewegungen | bewegung_ID | bewegungstyp, menge, ablaufdatum, datum, bemerkung, *material_ID, lager_ID* |

---

## D-004: DAO-Pattern + Singleton-DBConnection

**Datum:** Mai 2026
**Entscheidung:** Datenbankzugriff erfolgt ausschließlich über DAO-Klassen. Verbindung wird als Singleton in `DBConnection.java` verwaltet.

**Begründung:**
- Konsistenz mit Frau Cramers Java-Lehrstoff (Dino-Park-Beispiel)
- Trennt SQL-Code von UI-Logik (MVC)
- PreparedStatement schützt vor SQL-Injection
- Singleton-Verbindung spart Ressourcen bei Desktop-Anwendung

**Klassenstruktur:**

```
DBConnection.java
├── KategorieDAO
├── MaterialDAO
├── StationslagerDAO
├── LieferantDAO
├── BestellungDAO
└── BestandsbewegungDAO
```

---

## D-005: Lokal, kein Internet

**Datum:** Mai 2026
**Entscheidung:** Anwendung läuft lokal mit MySQL-Server auf demselben PC. Keine Netzwerkfunktion, keine Cloud.

**Begründung:**
- Krankenhaus-Datenschutz (Patientenkontext, Materialverbrauch)
- Einfacher Aufbau, kein Server-Setup nötig
- Reicht für Demo + Bewertung im Projektkontext

---

## D-006: Stunden-Diskrepanz 180 h vs. 192 h

**Datum:** Mai 2026
**Entscheidung:** Antrag dokumentiert **180 h Aufwand**, Wochenplan zeigt **6 × 32 h = 192 h** Anwesenheit. Differenz von **12 h ist Pufferzeit** für unerwartete Probleme.

**Begründung:**
- Anwesenheitspflicht laut SRH = 32 h/Woche × 6 Wochen
- Realistische Effektivzeit liegt unter Anwesenheit (Pausen, organisatorisches)
- Puffer nicht im Antrag aufführen → wirkt sonst wie Reservelöcher

---

## D-007: Generisches DAO-Interface `GenericDAO<T>`

**Datum:** 2026-06
**Entscheidung:** Der gemeinsame CRUD-Vertrag aller DAOs wird in einer generischen Schnittstelle `GenericDAO<T>` zentral definiert. Die sechs DAO-Klassen implementieren sie (`implements GenericDAO<Modellklasse>`) und kennzeichnen die vier Vertragsmethoden mit `@Override`.

```java
public interface GenericDAO<T> {
    T       create(T obj)   throws SQLException;
    List<T> findAll()       throws SQLException;
    void    update(T obj)   throws SQLException;
    void    delete(int id)  throws SQLException;
}
```

**Begründung:**
- Der wiederkehrende CRUD-Vertrag (in allen 6 DAOs identisch) steht an einer Stelle statt sechsfach implizit; der Compiler erzwingt die Einhaltung.
- Demonstriert die OO-Konzepte Abstraktion, Schnittstelle und Generics (Typ-Platzhalter `<T>` = Referenztyp) — fachlich relevant fürs Fachgespräch.
- Rein additive Änderung: Verhalten der App bleibt unverändert, geringes Risiko.
- Knüpft an den Java-Unterricht zu Wertetypen/Referenztypen/Collections an (`delete(int)` = Wertetyp, `T`/`List<T>` = Referenztyp/Collection).

**Abgrenzung:** Nicht-generische Sondermethoden (`findById`, `findBestandViews`, `getBestand`) bleiben außerhalb des Interface. Das Interface wird NICHT im Benutzerhandbuch dokumentiert (interne Architektur → Kap. 4/5 + Quellcodeverzeichnis).

**Konsequenz:** Neue Datei `dao/GenericDAO.java`; Doku Kap. 4.2 + 5.2 und Quellcodeverzeichnis (jetzt 7 Listings) ergänzt.

---

## D-008: MainController aufgeteilt (schlanker Verteiler + CRUD-Klassen)

**Datum:** 2026-06
**Entscheidung:** Der über 700 Zeilen lange `MainController` wurde aufgeteilt. Der wiederkehrende CRUD-Ablauf (Laden, Anlegen, Bearbeiten, Löschen) steht jetzt einmal in der abstrakten Basisklasse `EntityCrud<T>`. Je Reiter gibt es eine kleine, gleich aufgebaute Klasse (`MaterialCrud`, `KategorieCrud`, `StationslagerCrud`, `LieferantCrud`, `BestellungCrud`, `BewegungCrud`) im Paket `de.doit.controller.crud`. Gemeinsame Dialoge/Meldungen liegen in `Dialoge`. Der `MainController` ist nur noch Verteiler (rund 330 Zeilen).

**Begründung:**
- 700 Zeilen waren schwer nachvollziehbar; jeder Reiter war fast identischer Code.
- Gemeinsamer Ablauf an einer Stelle (Basisklasse) statt sechsfach → weniger Wiederholung, einheitlicher Aufbau.
- Zeigt Vererbung + Generics (`EntityCrud<T>`) — gut fürs Fachgespräch.
- Bewusst der „leichte Weg": eine FXML-Datei bleibt, nur Logik in eigene Klassen → geringes Risiko, App-Verhalten unverändert.

**Abgrenzung:** Der Reiter „Bestandsübersicht" (berechnete, schreibgeschützte Sicht mit Suche und Filter) bleibt im `MainController`, weil er kein normales Stammdaten-CRUD ist. *(Hinweis 2026-06-17: die ursprünglich hier verorteten Komfortfunktionen Warenentnahme/Umlagerung wurden entfernt, siehe D-009.)*

**Konsequenz:** Neues Paket `controller/crud/` (8 Klassen); Kompilierung mit JavaFX geprüft; Doku Kap. 4.2 + 8.2 ergänzt.

---

## Vorlage für neue Einträge

```markdown
## D-XXX: <Kurztitel>

**Datum:** <YYYY-MM>
**Entscheidung:** <was wurde entschieden>

**Begründung:**
- <Punkt 1>
- <Punkt 2>

**Konsequenz:** <Folgen für Code/Doku/Modell>
```

---

## D-009: Reduktion auf Antragsumfang — Warenentnahme & Umlagerung entfernt (2026-06-17)

**Entscheidung:** Die beiden Komfort-Schaltflächen „Warenentnahme" und „Umlagerung" in der Bestandsübersicht werden entfernt.

**Begründung:**

- Beide waren Zusätze über den genehmigten Projektantrag hinaus (keine Kernfunktion).
- Reduziert Komplexität (besonders fürs Fachgespräch); Umlagerung war die komplexeste Stelle (Doppelbuchung).
- Kein Funktionsverlust: Ein-/Ausgänge inkl. Umlagerung bleiben über den Reiter „Bestandsbewegungen" buchbar (AUSGANG bzw. AUSGANG+EINGANG).

**Konsequenz:** FXML-Buttons + `onWarenentnahme()`/`onUmlagerung()` entfernt; Doku (Doku_B/C/E + Endabgabe) umformuliert; Backup unter `Archiv/_backup_2026-06-17_Warenentnahme_Umlagerung/`. Korrigiert D-008 (dort hieß es „bleiben erhalten").
