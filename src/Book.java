public class Book extends Medium {
    // Attribute
    private String autor;
    private String genre;
    private int seitenanzahl;

    // der Konstruktor Aufbau, jede Klasse benötigt einen Konstruktor damit man Objekte dieses Datentyps überhaupt
    // instanziieren kann
    // Der Konstruktor ist die einzige Methode, für die man keinen Rückgabetyp angibt
    public Book(String titel, int jahr, String autor, String genre, int seitenanzahl) {
        super(titel, jahr);
        this.autor = autor;
        this.genre = genre;
        this.seitenanzahl = seitenanzahl;
    }

    // ein kontrollierten, öffentlichen Lesezugriff weil (siehe oben unter private) ist privat
    // Datenkapselung
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSeitenanzahl() {
        return seitenanzahl;
    }

    public void setSeitenanzahl(int seitenanzahl) {
        this.seitenanzahl = seitenanzahl;
    }




}
