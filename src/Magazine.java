public class Magazine extends Medium {
    // Attribute
    private int ausgabennummer;

    // der Konstruktor Aufbau, jede Klasse benötigt einen Konstruktor damit man Objekte dieses Datentyps überhaupt
    // instanziieren kann
    // Der Konstruktor ist die einzige Methode, für die man keinen Rückgabetyp angibt
    public Magazine(String titel, int jahr, int ausgabennummer){
        super(titel, jahr);
        this.ausgabennummer = ausgabennummer;
    }

    // ein kontrollierten, öffentlichen Lesezugriff weil (siehe oben unter private) ist privat
    // Datenkapselung
    public int getAusgabennummer() {
        return ausgabennummer;
    }

    public void setAusgabennummer(int ausgabennummer) {
        this.ausgabennummer = ausgabennummer;
    }


}
