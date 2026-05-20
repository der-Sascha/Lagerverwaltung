package de.doit.model;

public class Stationslager {

    private int id;
    private String name;
    private String standort;
    private String typ;

    public Stationslager(String name, String standort, String typ) {
        this.name = name;
        this.standort = standort;
        this.typ = typ;
    }

    public Stationslager(int id, String name, String standort, String typ) {
        this.id = id;
        this.name = name;
        this.standort = standort;
        this.typ = typ;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStandort() { return standort; }
    public void setStandort(String standort) { this.standort = standort; }

    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }


    //@Override
    //public String toString() { return name + " (" + standort + ")"; }
}
