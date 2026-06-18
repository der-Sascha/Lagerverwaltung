# Anleitung: volles CRUD für Material + Kategorie selbst eintragen

**Stand:** 2026-06-18 · Für: Sascha · Ziel: das in der read-only-Version entfernte Schreiben (Anlegen/Bearbeiten/Löschen) für **Material** und **Kategorie** wieder von Hand eintippen.

> **Warum überhaupt?** Die App ist gerade bewusst read-only (nur Lesen). Du baust CRUD selbst ein, um den Ablauf über alle Schichten zu verstehen. Jede Operation läuft über mehrere Dateien — genau die Stufen 2 (ANLEGEN), 3 (BEARBEITEN), 4 (LÖSCHEN).
>
> **SRH-Pflicht:** Erst wenn du diese Schritte gemacht hast, hat das Projekt wieder funktionierendes CRUD. Für die Abgabe ist CRUD Pflicht — also vor Abgabe mindestens diese Anleitung umsetzen.

## Überblick: was wo passiert (der rote Faden)

| Stufe | Material-Datei | Kategorie-Datei |
|---|---|---|
| DB-Zugriff | `dao/MaterialDAO.java` | `dao/KategorieDAO.java` |
| Formular + Logik | `controller/crud/MaterialCrud.java` (neu) | `controller/crud/KategorieCrud.java` (neu) |
| Verdrahtung | `controller/MainController.java` | `controller/MainController.java` |
| Buttons | `resources/fxml/main.fxml` | `resources/fxml/main.fxml` |

Reihenfolge zum Eintippen: **DAO → Crud-Klasse → MainController → FXML**. (Von innen nach außen, wie der Datenfluss.)

---

# TEIL A — Material

## Schritt 1: `MaterialDAO` auf volles CRUD umstellen

Datei `src/main/java/de/doit/dao/MaterialDAO.java`.

**1a.** Die Klassen-Zeile von `LeseDAO` auf `GenericDAO` ändern:

```java
public class MaterialDAO implements GenericDAO<Material> {
```

**1b.** Diese drei Methoden in die Klasse einfügen (z. B. direkt nach `findAll`, vor `zeileLesen`):

```java
    // === Stufe 2 — ANLEGEN (Objekt → DB-INSERT) ===
    @Override
    public Material create(Material material) throws SQLException {
        String sql = "INSERT INTO materialien (name, einheit, mindestbestand, kategorie_id) "
                + "VALUES (?, ?, ?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, material.getName());
            anweisung.setString(2, material.getEinheit());
            anweisung.setInt(3, material.getMindestbestand());
            anweisung.setInt(4, material.getKategorieId());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    material.setId(schluessel.getInt(1));
                }
            }
        }
        return material;
    }

    // === Stufe 3 — BEARBEITEN (Objekt → DB-UPDATE) ===
    @Override
    public void update(Material material) throws SQLException {
        String sql = "UPDATE materialien SET name = ?, einheit = ?, mindestbestand = ?, "
                + "kategorie_id = ? WHERE material_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, material.getName());
            anweisung.setString(2, material.getEinheit());
            anweisung.setInt(3, material.getMindestbestand());
            anweisung.setInt(4, material.getKategorieId());
            anweisung.setInt(5, material.getId());
            anweisung.executeUpdate();
        }
    }

    // === Stufe 4 — LÖSCHEN (ID → DB-DELETE) ===
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM materialien WHERE material_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }
```

> Die Imports `java.sql.*` stehen schon oben in der Datei — `Statement`, `ResultSet` usw. sind damit abgedeckt.

## Schritt 2: neue Klasse `MaterialCrud` anlegen

Neue Datei `src/main/java/de/doit/controller/crud/MaterialCrud.java`. Sie erbt vom read-only-`EntityCrud` (das `load()` mitbringt) und ergänzt nur das Schreiben:

```java
package de.doit.controller.crud;

import de.doit.dao.MaterialDAO;
import de.doit.model.Material;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Reiter "Materialien" mit vollem CRUD (erbt Lesen von EntityCrud). */
public class MaterialCrud extends EntityCrud<Material> {

    private final MaterialDAO materialDao;   // DAO mit Schreibrechten

    public MaterialCrud(TableView<Material> tabelle, MaterialDAO dao) {
        super(tabelle, dao, "Material");     // Basis nutzt es zum Laden
        this.materialDao = dao;              // hier für create/update/delete
    }

    // === Stufe 2 — ANLEGEN (leeres Formular) ===
    public void create() {
        formularAnzeigen(null);
    }

    // === Stufe 3 — BEARBEITEN (vorbelegtes Formular) ===
    public void edit() {
        Material sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte Material auswählen."); return; }
        formularAnzeigen(sel);
    }

    // === Stufe 4 — LÖSCHEN (mit Rückfrage) ===
    public void delete() {
        Material sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte Material auswählen."); return; }
        if (!Dialoge.bestaetigen("Material löschen", "Material wirklich löschen?")) return;
        try {
            materialDao.delete(sel.getId());
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Löschen fehlgeschlagen", ex);
        }
    }

    /** Formular für Anlegen (material == null) bzw. Bearbeiten. */
    private void formularAnzeigen(Material material) {
        TextField tfName    = new TextField(material != null ? material.getName()    : "");
        TextField tfEinheit = new TextField(material != null ? material.getEinheit() : "");
        TextField tfMindest = new TextField(material != null ? String.valueOf(material.getMindestbestand()) : "0");
        TextField tfKatId   = new TextField(material != null ? String.valueOf(material.getKategorieId())    : "1");

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),          tfName);
        grid.addRow(1, new Label("Einheit:"),        tfEinheit);
        grid.addRow(2, new Label("Mindestbestand:"), tfMindest);
        grid.addRow(3, new Label("Kategorie-ID:"),   tfKatId);

        Dialog<ButtonType> dlg = Dialoge.dialog(material == null ? "Material anlegen" : "Material bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (material == null) {
                materialDao.create(new Material(
                        tfName.getText(), tfEinheit.getText(),
                        Integer.parseInt(tfMindest.getText()), Integer.parseInt(tfKatId.getText())));
            } else {
                material.setName(tfName.getText());
                material.setEinheit(tfEinheit.getText());
                material.setMindestbestand(Integer.parseInt(tfMindest.getText()));
                material.setKategorieId(Integer.parseInt(tfKatId.getText()));
                materialDao.update(material);
            }
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
```

## Schritt 3: `MainController` verdrahten

Datei `src/main/java/de/doit/controller/MainController.java`.

**3a.** Feldtyp ändern (von `EntityCrud<Material>` auf `MaterialCrud`):

```java
    private MaterialCrud materialCrud;
```

**3b.** In `initialize()` die Erzeugung anpassen:

```java
        materialCrud = new MaterialCrud(tabMaterialien, materialDao);
```

**3c.** Drei Aktions-Methoden ergänzen (im Stammdaten-Abschnitt, neben `onMaterialienTabSelected`):

```java
    @FXML public void onMaterialAnlegen()    { materialCrud.create(); }
    @FXML public void onMaterialBearbeiten() { materialCrud.edit(); }
    @FXML public void onMaterialLoeschen()   { materialCrud.delete(); }
```

## Schritt 4: Buttons in der FXML zurückholen

Datei `src/main/resources/fxml/main.fxml`, im **Tab "Materialien"**. Die Zeile

```xml
                <Label text="Nur-Lese-Ansicht" style="-fx-font-style: italic; -fx-text-fill: #666;"/>
```

ersetzen durch:

```xml
                <HBox spacing="8">
                    <Button text="Anlegen"    onAction="#onMaterialAnlegen"/>
                    <Button text="Bearbeiten" onAction="#onMaterialBearbeiten"/>
                    <Button text="Löschen"    onAction="#onMaterialLoeschen"/>
                </HBox>
```

→ Material hat jetzt wieder volles CRUD.

---

# TEIL B — Kategorie (gleiches Muster)

## Schritt 1: `KategorieDAO` auf volles CRUD

`dao/KategorieDAO.java`: Klassenzeile auf `implements GenericDAO<Kategorie>` und die drei Methoden einfügen:

```java
    // === Stufe 2 — ANLEGEN ===
    @Override
    public Kategorie create(Kategorie kategorie) throws SQLException {
        String sql = "INSERT INTO kategorien (name, beschreibung) VALUES (?, ?)";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            anweisung.setString(1, kategorie.getName());
            anweisung.setString(2, kategorie.getBeschreibung());
            anweisung.executeUpdate();
            try (ResultSet schluessel = anweisung.getGeneratedKeys()) {
                if (schluessel.next()) {
                    kategorie.setId(schluessel.getInt(1));
                }
            }
        }
        return kategorie;
    }

    // === Stufe 3 — BEARBEITEN ===
    @Override
    public void update(Kategorie kategorie) throws SQLException {
        String sql = "UPDATE kategorien SET name = ?, beschreibung = ? WHERE kategorie_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setString(1, kategorie.getName());
            anweisung.setString(2, kategorie.getBeschreibung());
            anweisung.setInt(3, kategorie.getId());
            anweisung.executeUpdate();
        }
    }

    // === Stufe 4 — LÖSCHEN ===
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM kategorien WHERE kategorie_id = ?";
        Connection verbindung = DBConnection.getConnection();
        try (PreparedStatement anweisung = verbindung.prepareStatement(sql)) {
            anweisung.setInt(1, id);
            anweisung.executeUpdate();
        }
    }
```

## Schritt 2: neue Klasse `KategorieCrud`

Neue Datei `controller/crud/KategorieCrud.java`:

```java
package de.doit.controller.crud;

import de.doit.dao.KategorieDAO;
import de.doit.model.Kategorie;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/** Reiter "Kategorien" mit vollem CRUD (erbt Lesen von EntityCrud). */
public class KategorieCrud extends EntityCrud<Kategorie> {

    private final KategorieDAO kategorieDao;

    public KategorieCrud(TableView<Kategorie> tabelle, KategorieDAO dao) {
        super(tabelle, dao, "Kategorie");
        this.kategorieDao = dao;
    }

    public void create() { formularAnzeigen(null); }

    public void edit() {
        Kategorie sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte Kategorie auswählen."); return; }
        formularAnzeigen(sel);
    }

    public void delete() {
        Kategorie sel = tabelle.getSelectionModel().getSelectedItem();
        if (sel == null) { Dialoge.warnung("Bitte Kategorie auswählen."); return; }
        if (!Dialoge.bestaetigen("Kategorie löschen", "Kategorie wirklich löschen?")) return;
        try {
            kategorieDao.delete(sel.getId());
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Löschen fehlgeschlagen", ex);
        }
    }

    private void formularAnzeigen(Kategorie vorhanden) {
        TextField tfName   = new TextField(vorhanden != null ? vorhanden.getName()         : "");
        TextField tfBeschr = new TextField(vorhanden != null ? vorhanden.getBeschreibung() : "");
        tfBeschr.setPrefWidth(300);

        GridPane grid = Dialoge.gitter();
        grid.addRow(0, new Label("Name:"),         tfName);
        grid.addRow(1, new Label("Beschreibung:"), tfBeschr);

        Dialog<ButtonType> dlg = Dialoge.dialog(vorhanden == null ? "Kategorie anlegen" : "Kategorie bearbeiten", grid);
        if (dlg.showAndWait().filter(b -> b == ButtonType.OK).isEmpty()) return;

        try {
            if (vorhanden == null) {
                kategorieDao.create(new Kategorie(0, tfName.getText(), tfBeschr.getText()));
            } else {
                vorhanden.setName(tfName.getText());
                vorhanden.setBeschreibung(tfBeschr.getText());
                kategorieDao.update(vorhanden);
            }
            load();
        } catch (Exception ex) {
            Dialoge.fehler("Speichern fehlgeschlagen", ex);
        }
    }
}
```

## Schritt 3: `MainController`

```java
    private KategorieCrud kategorieCrud;            // Feldtyp
```
```java
        kategorieCrud = new KategorieCrud(tabKategorien, kategorieDao);   // in initialize()
```
```java
    @FXML public void onKategorieAnlegen()    { kategorieCrud.create(); }
    @FXML public void onKategorieBearbeiten() { kategorieCrud.edit(); }
    @FXML public void onKategorieLoeschen()   { kategorieCrud.delete(); }
```

## Schritt 4: FXML (Tab "Kategorien")

Das `Nur-Lese-Ansicht`-Label im Kategorie-Tab ersetzen durch:

```xml
                <HBox spacing="8">
                    <Button text="Anlegen"    onAction="#onKategorieAnlegen"/>
                    <Button text="Bearbeiten" onAction="#onKategorieBearbeiten"/>
                    <Button text="Löschen"    onAction="#onKategorieLoeschen"/>
                </HBox>
```

---

# Checkliste / Test

- [ ] `MaterialDAO` + `KategorieDAO` → `implements GenericDAO<…>` und je 3 Methoden ergänzt
- [ ] `MaterialCrud.java` + `KategorieCrud.java` neu angelegt
- [ ] `MainController`: 2 Feldtypen, 2 `new …Crud(…)`, 6 `@FXML`-Methoden
- [ ] FXML: 2× Label durch Button-HBox ersetzt
- [ ] In IntelliJ kompilieren (Sandbox kann kein JavaFX). MySQL auf Port 3324 läuft.
- [ ] App starten: in „Materialien" und „Kategorien" Anlegen/Bearbeiten/Löschen testen.

# Wenn du es selbst weiterführen willst

Die anderen 4 Reiter (Stationslager, Lieferanten, Bestellungen, Bestandsbewegungen) gehen **exakt gleich**: DAO auf `GenericDAO` + 3 Methoden, eigene `…Crud`-Klasse mit Formular, MainController-Verdrahtung, FXML-Buttons. Die SQL-Spalten je Tabelle stehen in `Projektdateien/testdaten_krankenhaus_lager.sql`.
