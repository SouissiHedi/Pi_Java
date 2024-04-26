package com.example.pi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NiveauUserController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger les niveaux depuis la base de données et les afficher dans les cartes
        loadNiveaux();
    }

    private void loadNiveaux() {
        try {
            Statement statement = DBConnexion.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM niveau");

            while (resultSet.next()) {
                String numero = resultSet.getString("numero");
                String division = resultSet.getString("division");
                String imagePath = resultSet.getString("image");

                // Créer une carte pour chaque niveau avec l'image correspondante
                VBox card = createNiveauCard(numero, division, imagePath);

                // Ajouter la carte au FlowPane
                flowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createNiveauCard(String numero, String division, String imagePath) {
        // Créer une carte pour un niveau avec le nom, la description et l'image donnés
        VBox card = new VBox();
        card.getStyleClass().add("niveau-card");

        Text numeroText = new Text(numero);
        numeroText.getStyleClass().add("niveau-numero");

        Text divisionText = new Text(division);
        divisionText.getStyleClass().add("niveau-division");

        // Créer un ImageView pour afficher l'image du niveau
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); // Définir la largeur souhaitée de l'image
        imageView.setPreserveRatio(true);

        // Charger l'image à partir du chemin stocké dans la base de données
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView.setImage(image);
        }

        card.getChildren().addAll(numeroText, divisionText, imageView);

        return card;
    }

}