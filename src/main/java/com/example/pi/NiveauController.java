package com.example.pi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class NiveauController implements Initializable {
    @FXML
    private TableView<Niveau> tNiveau;

    @FXML
    private TextField tNum;

    @FXML
    private TextField tDivision;

    @FXML
    private TableColumn<Niveau, Number> numero;

    @FXML
    private TableColumn<Niveau, String> division;

    @FXML
    private TableColumn<Niveau, String> imageColumn;

    @FXML
    private ImageView imageView;

    private File selectedImageFile;

    private Connection connection;

    @FXML
    private void uploadImage(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void clear() {
        tNum.clear();
        tDivision.clear();
        imageView.setImage(null); // Efface l'image affichée
        selectedImageFile = null; // Réinitialise le fichier sélectionné
    }

    @FXML
    void createNiveau() {
        String num = tNum.getText().trim();
        String div = tDivision.getText().trim();
        String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : "";

        // Vérifier si les champs sont vides
        if (num.isEmpty() || div.isEmpty() || imagePath.isEmpty()) {
            showNotification("Veuillez remplir tous les champs");
            return;
        }

        // Validation supplémentaire : Vérifier si num est un entier valide
        try {
            int numeroValue = Integer.parseInt(num);
            // Autres validations supplémentaires si nécessaire

            // Insérer l'enregistrement avec le chemin de l'image
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO niveau(numero, division, image) VALUES (?, ?, ?)");
            preparedStatement.setString(1, num);
            preparedStatement.setString(2, div);
            preparedStatement.setString(3, imagePath);
            preparedStatement.executeUpdate();
            refreshTable();
            showNotification("Niveau ajouté avec succès");
        } catch (NumberFormatException e) {
            showNotification("Veuillez saisir un numéro de niveau valide");
        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Erreur lors de l'ajout du niveau : " + e.getMessage());
        }
    }

    @FXML
    void updateNiveau() {
        Niveau selectedNiveau = tNiveau.getSelectionModel().getSelectedItem();
        if (selectedNiveau != null) {
            String num = tNum.getText().trim();
            String div = tDivision.getText().trim();
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : "";

            // Vérifier si les champs sont vides
            if (num.isEmpty() || div.isEmpty()) {
                showNotification("Veuillez remplir tous les champs");
                return;
            }

            // Validation supplémentaire : Vérifier si num est un entier valide
            try {
                int numeroValue = Integer.parseInt(num);
                // Autres validations supplémentaires si nécessaire

                // Mettre à jour l'enregistrement avec le chemin de la nouvelle image
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE niveau SET numero = ?, division = ?, image = ? WHERE id = ?");
                preparedStatement.setString(1, num);
                preparedStatement.setString(2, div);
                preparedStatement.setString(3, imagePath);
                preparedStatement.setInt(4, selectedNiveau.getId());
                preparedStatement.executeUpdate();
                refreshTable();
                showNotification("Niveau mis à jour avec succès");
            } catch (NumberFormatException e) {
                showNotification("Veuillez saisir un numéro de niveau valide");
            } catch (SQLException e) {
                e.printStackTrace();
                showNotification("Erreur lors de la mise à jour du niveau : " + e.getMessage());
            }
        } else {
            showNotification("Veuillez sélectionner un niveau à mettre à jour");
        }
    }

    private void showNotification(String message) {
        Notifications.create()
                .title("Notification")
                .text(message)
                .position(Pos.CENTER)
                .owner(tNiveau.getScene().getWindow())
                .showInformation();
    }
    @FXML
    void deleteNiveau() {
        Niveau selectedNiveau = tNiveau.getSelectionModel().getSelectedItem();
        if (selectedNiveau != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM niveau WHERE id = ?");
                preparedStatement.setInt(1, selectedNiveau.getId());
                preparedStatement.executeUpdate();
                refreshTable();
                showNotification("Niveau supprimé avec succès");
            } catch (SQLException e) {
                e.printStackTrace();
                showNotification("Erreur lors de la suppression du niveau");
            }
        } else {
            showNotification("Veuillez sélectionner un niveau à supprimer");
        }
    }


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DBConnexion.getCon();
        setupTableView();
        refreshTable();

        // Ajouter un écouteur de sélection à la table
        tNiveau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Mettre à jour les champs avec les données de la ligne sélectionnée
                tNum.setText(newSelection.getNumero());
                tDivision.setText(newSelection.getDivision());
                String imagePath = newSelection.getImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image(new File(imagePath).toURI().toString());
                    imageView.setImage(image);
                } else {
                    imageView.setImage(null);
                }
            } else {
                // Réinitialiser les champs si aucune ligne n'est sélectionnée
                tNum.clear();
                tDivision.clear();
                imageView.setImage(null);
            }
        });
    }


    private void setupTableView() {
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
    }

    private void refreshTable() {
        ObservableList<Niveau> niveaux = FXCollections.observableArrayList();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM niveau");
            while (resultSet.next()) {
                Niveau n = new Niveau(resultSet.getInt("id"), resultSet.getString("numero"), resultSet.getString("division"), resultSet.getString("image"));
                niveaux.add(n);
            }
            tNiveau.setItems(niveaux);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showNiveau(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/Niveau.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Niveau");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showTrophee(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/Trophee.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Trophee");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showNiveauUser(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/NiveauUser.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Niveau User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showTropheeUser(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/TropheeUser.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Trophee User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
