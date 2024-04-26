package com.example.pi;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TropheeController implements Initializable {
    @FXML
    private TableView<Trophee> tTrophee;

    @FXML
    private TextField tNom;

    @FXML
    private TextField tDescription;

    @FXML
    private TableColumn<Trophee, String> nom;

    @FXML
    private TableColumn<Trophee, String> description;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    private Connection connection;

    @FXML
    void clear() {
        tNom.clear();
        tDescription.clear();
    }

    @FXML
    void createTrophee() {
        String nom = tNom.getText().trim();
        String description = tDescription.getText().trim();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || description.isEmpty()) {
            showNotification("Veuillez remplir tous les champs");
            return;
        }
        if (nom.length() > 8) {
            showNotification("Le nom du trophée ne peut pas dépasser 8 caractères");
            return;
        }

        if (description.length() > 100) {
            showNotification("La description du trophée ne peut pas dépasser 100 caractères");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trophee(nom, description) VALUES (?, ?)");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
            refreshTable();
            showNotification("Trophée ajouté avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Erreur lors de l'ajout du trophée");
        }
    }

    @FXML
    void deleteTrophee() {
        Trophee selectedTrophee = tTrophee.getSelectionModel().getSelectedItem();
        if (selectedTrophee != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trophee WHERE id = ?");
                preparedStatement.setInt(1, selectedTrophee.getId());
                preparedStatement.executeUpdate();
                refreshTable();
                showNotification("Trophée supprimé avec succès");
            } catch (SQLException e) {
                e.printStackTrace();
                showNotification("Erreur lors de la suppression du trophée");
            }
        } else {
            showNotification("Veuillez sélectionner un trophée à supprimer");
        }
    }

    @FXML
    void updateTrophee() {
        Trophee selectedTrophee = tTrophee.getSelectionModel().getSelectedItem();
        if (selectedTrophee != null) {
            String nom = tNom.getText().trim();
            String description = tDescription.getText().trim();

            // Vérifier si les champs sont vides
            if (nom.isEmpty() || description.isEmpty()) {
                showNotification("Veuillez remplir tous les champs");
                return;
            }
            // Vérifier la longueur du nom et de la description
            if (nom.length() > 8) {
                showNotification("Le nom du trophée ne peut pas dépasser 8 caractères");
                return;
            }

            if (description.length() > 100) {
                showNotification("La description du trophée ne peut pas dépasser 100 caractères");
                return;
            }

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trophee SET nom = ?, description = ? WHERE id = ?");
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, selectedTrophee.getId());
                preparedStatement.executeUpdate();
                refreshTable();
                showNotification("Trophée mis à jour avec succès");
            } catch (SQLException e) {
                e.printStackTrace();
                showNotification("Erreur lors de la mise à jour du trophée");
            }
        } else {
            showNotification("Veuillez sélectionner un trophée à mettre à jour");
        }
    }
    private void showNotification(String message) {
        Notifications.create()
                .title("Notification")
                .text(message)
                .position(Pos.CENTER)
                .owner(tTrophee.getScene().getWindow())
                .showInformation();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DBConnexion.getCon();
        setupTableView();
        refreshTable();
    }

    private void setupTableView() {
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        tTrophee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tNom.setText(newValue.getNom());
                tDescription.setText(newValue.getDescription());
            }
        });
    }

    private void refreshTable() {
        ObservableList<Trophee> trophees = FXCollections.observableArrayList();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM trophee");
            while (resultSet.next()) {
                Trophee t = new Trophee(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description"));
                trophees.add(t);
            }
            tTrophee.setItems(trophees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
