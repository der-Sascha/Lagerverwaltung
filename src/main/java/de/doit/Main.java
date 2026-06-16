package de.doit;

import de.doit.db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Lagerverwaltung Krankenhaus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    // wird von JavaFX automatisch aufgerufen, wenn ich das Programm schließe
    public void stop() {
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}