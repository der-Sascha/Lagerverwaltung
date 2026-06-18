# Umbau-Plan: Code nach Schwierigkeit + Ablauf staffeln

**Erstellt:** 2026-06-18 · **Status:** Entwurf, wartet auf Saschas Freigabe · **Code noch unangetastet**

---

## 1. Ziel (deine Vorgaben)

- Code **nach Schwierigkeit UND nach Ablauf** ordnen — beides zusammen.
- Funktionen dürfen **entfernt** werden, auch wenn das Projekt kleiner wird.
- Beobachtung: **„Lesen" liegt über mehrere Dateien** — wie jede andere Funktion auch.
- **6 Tabellen bleiben fest** (SRH-Pflicht: mind. 5). Die Abfragen/Funktionen werden gedanklich **bei null** neu aufgebaut und stufenweise hochgezogen.

## 2. Grundidee: vertikale Stufen statt horizontaler Schichten

Heute ist der Code nach **Schicht** sortiert (alle Models, alle DAOs, alle Controller). Dadurch verteilt sich *eine* Funktion über 5–6 Dateien — genau dein Problem.

Der Umbau ordnet stattdessen in **Stufen**. Eine Stufe = **eine Operation komplett durch alle Schichten** (DB → DAO → Basis → Reiter → Controller → FXML). Das vereint automatisch beides:

- **Ablauf** = Reihenfolge, in der Daten durch das Programm fließen.
- **Schwierigkeit** = jede Stufe baut auf der vorigen auf, von trivial bis komplex.

Man liest/baut also **eine Funktion am Stück fertig**, bevor die nächste beginnt.

## 3. Der Stufenplan (einfach → komplex = Baureihenfolge)

| Stufe | Operation | Beteiligte Dateien (der „rote Faden") | Warum hier |
|---|---|---|---|
| **0 — Fundament** | App startet, leeres Fenster | `Launcher` → `Main` → `DBConnection` → `main.fxml` | Keine Logik, nur Gerüst. Einstieg ins Verstehen. |
| **1 — LESEN** | Tabelle zeigt Daten | `Kategorie` (POJO) → `GenericDAO.findAll` → `KategorieDAO.findAll` + `zeileLesen` → `EntityCrud.load` → `MainController` (Spalten/Binding) → `main.fxml` (TableView) | Einfachster Datenfluss: DB → Bildschirm, nur Richtung „raus". |
| **2 — ANLEGEN** | Neuer Datensatz übers Formular | `…DAO.create` → `EntityCrud.create` + `formularAnzeigen` → `Dialoge` → `main.fxml` (Button „Neu") | Erste Richtung „rein", erstes Formular + `RETURN_GENERATED_KEYS`. |
| **3 — BEARBEITEN** | Auswahl ändern | `…DAO.update` → `EntityCrud.edit` (nutzt `formularAnzeigen` wieder) | Wie Anlegen, aber mit vorbelegtem Formular + Auswahlprüfung. |
| **4 — LÖSCHEN** | Auswahl entfernen | `…DAO.delete` → `EntityCrud.delete` + `idVon` → `Dialoge.bestaetigen` | Rückfrage-Logik, abstrakte Methode `idVon`. |
| **5 — SUCHEN / FILTERN** | Bestandsübersicht filtern | `BestandView` (berechnetes Modell) → `BestandsbewegungDAO` (SUM EINGANG − AUSGANG, Joins) → `MainController` (Such-/Filterlogik) | Höchste Komplexität: Aggregation, abgeleitete Daten, kein 1:1-Tabellenbezug. |

> Lesreihenfolge fürs Fachgespräch = genau diese Tabelle von oben nach unten. Du kannst jede Stufe als abgeschlossene Einheit erklären.

## 4. Was wird kleiner / entfernt (Streichliste)

Der Code ist bereits schlank und gut faktorisiert — es gibt **wenig totes Material**. Kandidaten:

**Sicher (kein Pflicht-Risiko):**

1. **`_ORDNERINFO.md` (src) korrigieren** — erwähnt `findById()`, das im Code gar nicht existiert. Stale, raus.
2. **Doppelte Demo-/Kommentar-Altlasten** in `Main.java` straffen (viele Lern-Kommentare; Funktion bleibt).
3. **`Stufe-0`-Konsolidierung:** prüfen, ob `Launcher` + `Main` zusammengelegt werden können (zwei Einstiegsklassen für JavaFX nötig? — meist nur wegen Modulpfad; ggf. bleibt es).

**Optional (Projekt schrumpft spürbar, Pflicht bleibt erfüllt):**

4. **Volles CRUD nur auf den Kern-Entitäten** (z.B. `Material`, `Kategorie`), restliche Reiter zunächst **read-only** (nur Stufe 1). DB-Schema mit 6 Tabellen bleibt → SRH-Pflicht (mind. 5 Tabellen) bleibt erfüllt. Du holst die Stufen 2–4 für weitere Reiter später nach.
5. **`BestandView`** nur behalten, wenn Stufe 5 wirklich gebaut wird; sonst zurückstellen.

> **Pflicht-Hinweis:** CRUD-Funktionalität ist SRH-Bewertungskriterium. Vollständig wegfallen darf CRUD nicht — Punkt 4 reduziert nur die *Anzahl* der Reiter mit Vollausstattung, nicht CRUD selbst. Mindestens 1–2 Entitäten brauchen Stufen 1–4 komplett.

## 5. Umsetzungs-Reihenfolge (wenn du den Plan freigibst)

1. `src/_ORDNERINFO.md` korrigieren (findById raus, Main als Einstieg statt „Demo").
2. Festlegen, welche Entitäten **Voll-CRUD** behalten (Vorschlag: `Material` + `Kategorie`) und welche **read-only** werden.
3. Code physisch nach dem Stufenmodell anordnen/kommentieren — pro Stufe ein klarer Abschnitt, gleiche Methodennamen über alle Reiter, damit der „rote Faden" sichtbar ist.
4. Read-only-Reiter: Buttons „Neu/Bearbeiten/Löschen" aus `main.fxml` für diese Reiter entfernen.
5. Kompilieren (`mvn compile`), Start testen, jede Stufe einzeln prüfen.
6. `MD/Dateiuebersicht.md`, `MD/Entscheidungen.md`, `MD/Projekttagebuch.md` nachziehen.

## 6. Offene Entscheidung für dich

- **Variante A (sicher):** 6 Tabellen + Voll-CRUD bleiben, nur intern nach Stufen geordnet + Streichliste 1–3. Projekt fast gleich groß, aber sauber gestaffelt.
- **Variante B (schlanker):** Voll-CRUD nur auf 2 Entitäten, Rest read-only (Streichliste + Punkt 4). Projekt deutlich kleiner, Pflicht bleibt erfüllt.

Sag mir A oder B (oder Mischung), dann setze ich Schritt 5 (Umsetzung) um.
