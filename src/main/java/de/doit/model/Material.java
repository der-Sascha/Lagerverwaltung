package de.doit.model;

public class Material {

    private int id;
    private String name;
    private String einheit;
    private int mindestbestand;
    private int kategorieId;

    public Material(String name, String einheit, int mindestbestand, int kategorieId) {
        this.name = name;
        this.einheit = einheit;
        this.mindestbestand = mindestbestand;
        this.kategorieId = kategorieId;
    }

    public Material(int id, String name, String einheit, int mindestbestand, int kategorieId) {
        this.id = id;
        this.name = name;
        this.einheit = einheit;
        this.mindestbestand = mindestbestand;
        this.kategorieId = kategorieId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEinheit() { return einheit; }
    public void setEinheit(String einheit) { this.einheit = einheit; }

    public int getMindestbestand() { return mindestbestand; }
    public void setMindestbestand(int mindestbestand) { this.mindestbestand = mindestbestand; }

    public int getKategorieId() { return kategorieId; }
    public void setKategorieId(int kategorieId) { this.kategorieId = kategorieId; }

    @Override
    public String toString() { return name; }
}
