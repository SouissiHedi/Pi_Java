package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainFX extends Application {
    public void start(Stage primaryStage) throws Exception {
        // Charger l'interface utilisateur à partir du fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichTournoi.fxml"));
        Parent root = loader.load();

        // Créer une scène avec la taille spécifiée
        Scene scene = new Scene(root, 1335, 891);

        primaryStage.setTitle("Gérer Tournoi");

        // Définir la scène principale
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}