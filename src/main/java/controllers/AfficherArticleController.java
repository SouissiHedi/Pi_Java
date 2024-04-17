package controllers;

import edu.esprit.entities.Article;
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

public class AfficherArticleController {
    private final ArticleService ps = new ArticleService();
    @FXML
    private TableColumn<Article, Integer> prixCol;
    @FXML
    private TableColumn<Article, String> nomCol;
    @FXML
    private TableView<Article> tableView;

    public AfficherArticleController() throws SQLException {
    }

    @FXML
    void initialize() {
        try {
            List<Article> articles = ps.recuperer();
            ObservableList<Article> observableList = FXCollections.observableList(articles);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}