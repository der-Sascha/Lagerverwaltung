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
    // final muss stehen, Datum darf nicht geändert werden (Buchungszeitpunkt)
    private final LocalDateTime datum;
    private String bemerkung;

    // 1te für neu anlegen (ID auto_increment, DB vergibt sie -> id vorerst 0).
    // datum wird beim Anlegen von der aufrufenden Stelle gesetzt (LocalDateTime.now()),
    // NICHT von der Datenbank - deshalb hier weiterhin Pflichtparameter.
    public Bestandsbewegung(int materialId, int lagerId, BewegungsTyp bewegungstyp,
                            int menge, LocalDate ablaufdatum,
                            LocalDateTime datum, String bemerkung) {
        this(0, materialId, lagerId, bewegungstyp, menge, ablaufdatum, datum, bemerkung);
    }

    // 2te für laden - die ID zieht er mit weil vorhanden
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

    // !! kein Setter für Datum - muss manipuliersicher sein
    public LocalDateTime getDatum() { return datum; }

    public String getBemerkung() { return bemerkung; }
    public void setBemerkung(String bemerkung) { this.bemerkung = bemerkung; }
}
