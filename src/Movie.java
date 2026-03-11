public class Movie extends Medium{
    // Attribute
   private String regisseur;
   private String genre;
   private String originalsprache;

    // der Konstruktor Aufbau, jede Klasse benötigt einen Konstruktor damit man Objekte dieses Datentyps überhaupt
    // instanziieren kann
    // Der Konstruktor ist die einzige Methode, für die man keinen Rückgabetyp angibt
    public Movie(String titel, int jahr, String regisseur, String genre, String originalsprache){
        super(titel, jahr);
        this.regisseur = regisseur;
        this.genre = genre;
        this.originalsprache = originalsprache;
    }

    // ein kontrollierten, öffentlichen Lesezugriff weil (siehe oben unter private) ist privat
    // Datenkapselung
    public String getRegisseur() {
        return regisseur;
    }

    public void setRegisseur(String regisseur) {
        this.regisseur = regisseur;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOriginalsprache() {
        return originalsprache;
    }

    public void setOriginalsprache(String originalsprache) {
        this.originalsprache = originalsprache;
    }



}

