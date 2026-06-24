package de.doit.modele;

public class Lieferant {

    private int id;
    private String name;
    private String kontakt;
    private String telefon;
    private String email;

    public Lieferant(String name, String kontakt, String telefon, String email) {
        this.name = name;
        this.kontakt = kontakt;
        this.telefon = telefon;
        this.email = email;
    }

    public Lieferant(int id, String name, String kontakt, String telefon, String email) {
        this.id = id;
        this.name = name;
        this.kontakt = kontakt;
        this.telefon = telefon;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getKontakt() { return kontakt; }
    public void setKontakt(String kontakt) { this.kontakt = kontakt; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // hier wird einfach nur der name zurückgegeben - ohne weitere Zeichen
    @Override
    public String toString() { return name; }
}
