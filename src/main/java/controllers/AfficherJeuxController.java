package controllers;
import edu.esprit.entities.Jeux;
import edu.esprit.services.JeuxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ArticleService;

import java.sql.SQLException;
import java.util.List;

public class AfficherJeuxController {
    private final JeuxCrud ps = new JeuxCrud();
    @FXML
    private TableColumn<Jeux, Integer> idCol;
    @FXML
    private TableColumn<Jeux, Integer> jeu_idCol;
    @FXML
    private TableColumn<Jeux, String> nomCol;
    @FXML
    private TableColumn<Jeux, String> genreCol;
    @FXML
    private TableView<Jeux> tableView;

    public AfficherJeuxController() throws SQLException {
    }

    @FXML
    void initialize() {
        try {
            List<Jeux> jeux = ps.afficher();
            ObservableList<Jeux> observableList = FXCollections.observableList(jeux);
            tableView.setItems(observableList);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            jeu_idCol.setCellValueFactory(new PropertyValueFactory<>("jeu_id"));
            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
