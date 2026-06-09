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

**Konsequenz:** Zu- und Abgänge werden ausschließlich als gebuchte Bestandsbewegungen erfasst (manuell bzw. über die Funktionen Warenentnahme und Umlagerung). Der Bestellstatus (offen/geliefert/storniert) dokumentiert nur den Bestellvorgang.

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
└── BewegungDAO
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
