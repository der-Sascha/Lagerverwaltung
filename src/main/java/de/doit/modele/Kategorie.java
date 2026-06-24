package de.doit.modele;

public class Kategorie {

    private int id;
    private String name;
    private String beschreibung;


    public Kategorie() {}

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
