package de.doit;

import de.doit.db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    // JavaFX ruft start() automatisch beim Programmstart auf
    // primaryStage ist das Hauptfenster — JavaFX erstellt es und übergibt es dir hier als Parameter.
    public void start(Stage primaryStage) throws Exception {
        //Übergabe vom main Pfad - beginnt im resources ORdner
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main.fxml"));
        // liest (load) die gesamte main.fxml aus loader und macht daraus eine UI (alle Buttons, Tabellen...)
        Parent root = loader.load();

        // Eine Scene ist der Inhalt des Fensters und die Größe holt er sich bei main.fxml Zeile 11
        Scene scene = new Scene(root);
        // name ändern vom Fenster
        primaryStage.setTitle("Lagerverwaltung Krankenhaus");
        // packt die scene (meineUI) ins Fenster
        primaryStage.setScene(scene);
        // zeige das Fenster
        primaryStage.show();
    }

    @Override
    // wird von JavaFX automatisch aufgerufen, wenn ich das Programm schließe
    public void stop() {
        DBConnection.closeConnection();
    }

    // Java Konvention - java sucht diesen Befehl main() egal wo er steht - denoch steht er immer am schluss
    public static void main(String[] args) {
        launch(args);
    }
}