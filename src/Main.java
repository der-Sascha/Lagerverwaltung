public class Main {
    public static void main(String[] args) {
        Book myBook = new Book("Buch a", 1930, "Sascha", "Modern", 123);
        Magazine myMagazine = new Magazine("Magazin a", 2000, 001);
        Movie myMovie = new Movie("Film a", 2010, "Sascha", "Action", "Deutsch");

        System.out.println("Das Buch " + myBook.getTitel() + " wurde aus dem Jahr " + myBook.getJahr() + " mit Autor " + myBook.getAutor() + " und Genre " + myBook.getGenre() + " geschrieben.");

    }
}
