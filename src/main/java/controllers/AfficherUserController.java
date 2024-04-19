package controllers;

import edu.esprit.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.esprit.services.UserCrud;

import java.sql.SQLException;
import java.util.List;

public class AfficherUserController {
    private final UserCrud ps = new UserCrud();
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, String> passCol;
    @FXML
    private TableView<User> tableView;

    public AfficherUserController() throws SQLException {
    }

    @FXML
    void initialize() {
        try {
            List<User> users = ps.afficher();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            roleCol.setCellValueFactory(new PropertyValueFactory<>("roles"));
            passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}