package com.example.pi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TropheeUserController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger les trophées depuis la base de données et les afficher dans les cartes
        loadTrophees();
    }

    private void loadTrophees() {
        try {
            Statement statement = DBConnexion.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM trophee");

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");

                // Créer une carte pour chaque trophée
                VBox card = createTropheeCard(nom, description);

                // Ajouter la carte au FlowPane
                flowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createTropheeCard(String nom, String description) {
        // Créer une carte pour un trophée avec le nom et la description donnés
        VBox card = new VBox();
        card.getStyleClass().add("trophee-card");

        Text nomText = new Text(nom);
        nomText.getStyleClass().add("trophee-nom");

        Text descriptionText = new Text(description);
        descriptionText.getStyleClass().add("trophee-description");

        card.getChildren().addAll(nomText, descriptionText);

        return card;
    }
}