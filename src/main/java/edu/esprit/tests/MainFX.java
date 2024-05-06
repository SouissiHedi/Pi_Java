package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArticleBack.fxml"));
        Parent root = loader.load();
        //Scene scene = new Scene(root);
        Scene scene=new Scene(root,1335,891);
        primaryStage.setTitle("Gérer articles");
        primaryStage.setScene(scene);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}