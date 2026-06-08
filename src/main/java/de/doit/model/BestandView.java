package de.doit.model;

public class BestandView {

    private int materialId;
    private String materialName;
    private String einheit;
    private int lagerId;
    private String lagerName;
    private int bestand;
    private int mindestbestand;

    public BestandView(int materialId, String materialName, String einheit,
                       int lagerId, String lagerName,
                       int bestand, int mindestbestand) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.einheit = einheit;
        this.lagerId = lagerId;
        this.lagerName = lagerName;
        this.bestand = bestand;
        this.mindestbestand = mindestbestand;
    }
    public int getMaterialId()      { return materialId; }
    public String getMaterialName() { return materialName; }
    public String getEinheit()      { return einheit; }
    public int getLagerId()         { return lagerId; }
    public String getLagerName()    { return lagerName; }
    public int getBestand()         { return bestand; }
    public int getMindestbestand()  { return mindestbestand; }
    public boolean isWarnung() { return bestand <= mindestbestand; }
    public String getStatus() { return isWarnung() ? "WARNUNG" : "OK"; }

    //@Override public String toString() {
    //    return materialName + " @ " + lagerName + " = " + bestand + " (min " + mindestbestand + ")";
    //}
}
