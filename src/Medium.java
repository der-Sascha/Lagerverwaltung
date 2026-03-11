public class Medium {
    // Attribute
    private String titel;
    private int jahr;


    // der Konstruktor Aufbau, jede Klasse benötigt einen Konstruktor damit man Objekte dieses Datentyps überhaupt
    // instanziieren kann
    // Der Konstruktor ist die einzige Methode, für die man keinen Rückgabetyp angibt
    public Medium(String titel, int jahr) {
        this.titel = titel;
        this.jahr = jahr;
    }

    // ein kontrollierten, öffentlichen Lesezugriff weil title ist privat
    // Datenkapselung
    public String getTitel (){
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }

    // für jahr
    public int getJahr() {
        return jahr;
    }
    public void setJahr(int jahr) {
        this.jahr = jahr;
    }



}
