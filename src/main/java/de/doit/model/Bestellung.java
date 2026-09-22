package de.doit.model;

import java.time.LocalDate;

public class Bestellung {

    private int id;
    private int materialId;
    private int lieferantId;
    private int lagerId;
    private int menge;
    private LocalDate bestelldatum;
    private LocalDate lieferdatum;
    private String status;

    // 1te für neu anlegen (ID auto_increment, DB vergibt sie -> id vorerst 0)
    public Bestellung(int materialId, int lieferantId, int lagerId, int menge,
                      LocalDate bestelldatum, LocalDate lieferdatum, String status) {
        this(0, materialId, lieferantId, lagerId, menge, bestelldatum, lieferdatum, status);
    }

    // 2te für laden - die ID zieht er mit weil vorhanden
    public Bestellung(int id, int materialId, int lieferantId, int lagerId, int menge,
                      LocalDate bestelldatum, LocalDate lieferdatum, String status) {
        this.id = id;
        this.materialId = materialId;
        this.lieferantId = lieferantId;
        this.lagerId = lagerId;
        this.menge = menge;
        this.bestelldatum = bestelldatum;
        this.lieferdatum = lieferdatum;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public int getLieferantId() { return lieferantId; }
    public void setLieferantId(int lieferantId) { this.lieferantId = lieferantId; }

    public int getLagerId() { return lagerId; }
    public void setLagerId(int lagerId) { this.lagerId = lagerId; }

    public int getMenge() { return menge; }
    public void setMenge(int menge) { this.menge = menge; }

    public LocalDate getBestelldatum() { return bestelldatum; }
    public void setBestelldatum(LocalDate bestelldatum) { this.bestelldatum = bestelldatum; }

    public LocalDate getLieferdatum() { return lieferdatum; }
    public void setLieferdatum(LocalDate lieferdatum) { this.lieferdatum = lieferdatum; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
