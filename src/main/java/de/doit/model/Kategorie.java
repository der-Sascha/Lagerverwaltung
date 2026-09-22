package de.doit.model;

public class Kategorie {

    private int id;
    private String name;
    private String beschreibung;

    // 1te für neu anlegen (ID auto_increment, DB vergibt sie -> id vorerst 0)
    public Kategorie(String name, String beschreibung) {
        this(0, name, beschreibung);
    }

    // 2te für laden - die ID zieht er mit weil vorhanden
    public Kategorie(int id, String name, String beschreibung) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBeschreibung() {return beschreibung; }
    public void setBeschreibung(String beschreibung) {this.beschreibung = beschreibung; }

    // hier wird einfach nur der name zurückgegeben - ohne weitere Zeichen
    @Override
    public String toString() { return name; }
}
