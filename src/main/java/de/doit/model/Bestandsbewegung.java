package de.doit.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bestandsbewegung {

    private int id;
    private int materialId;
    private int lagerId;
    private BewegungsTyp bewegungstyp;
    private int menge;
    private LocalDate ablaufdatum;
    private final LocalDateTime datum;
    private String bemerkung;

    public Bestandsbewegung(int materialId, int lagerId, BewegungsTyp bewegungstyp,
                            int menge, LocalDate ablaufdatum,
                            LocalDateTime datum, String bemerkung) {
        this.materialId = materialId;
        this.lagerId = lagerId;
        this.bewegungstyp = bewegungstyp;
        this.menge = menge;
        this.ablaufdatum = ablaufdatum;
        this.datum = datum;
        this.bemerkung = bemerkung;
    }

    public Bestandsbewegung(int id, int materialId, int lagerId, BewegungsTyp bewegungstyp,
                            int menge, LocalDate ablaufdatum,
                            LocalDateTime datum, String bemerkung) {
        this.id = id;
        this.materialId = materialId;
        this.lagerId = lagerId;
        this.bewegungstyp = bewegungstyp;
        this.menge = menge;
        this.ablaufdatum = ablaufdatum;
        this.datum = datum;
        this.bemerkung = bemerkung;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public int getLagerId() { return lagerId; }
    public void setLagerId(int lagerId) { this.lagerId = lagerId; }

    public BewegungsTyp getBewegungstyp() { return bewegungstyp; }
    public void setBewegungstyp(BewegungsTyp bewegungstyp) { this.bewegungstyp = bewegungstyp; }

    public int getMenge() { return menge; }
    public void setMenge(int menge) { this.menge = menge; }

    public LocalDate getAblaufdatum() { return ablaufdatum; }
    public void setAblaufdatum(LocalDate ablaufdatum) { this.ablaufdatum = ablaufdatum; }

    public LocalDateTime getDatum() { return datum; }

    public String getBemerkung() { return bemerkung; }
    public void setBemerkung(String bemerkung) { this.bemerkung = bemerkung; }
}
