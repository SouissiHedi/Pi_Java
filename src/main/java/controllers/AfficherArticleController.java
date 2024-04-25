package controllers;

import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ArticleService;
import javafx.scene.control.TableCell;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherArticleController {
    private final ArticleService ps = new ArticleService();
    @FXML
    private TableColumn<Article, Integer> idCol;
    @FXML
    private TableColumn<Article, String> nomCol;
    @FXML
    private TableColumn<Article, String> descCol;
    @FXML
    private TableColumn<Article, String> typeCol;
    @FXML
    private TableColumn<Article, String> prixCol;
    @FXML
    private TableColumn<Article, Image> imageCol;
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
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type_id"));
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

            imageCol.setCellValueFactory(cellData -> {
                Image image = cellData.getValue().getImage();
                return new SimpleObjectProperty<>(image);
            });

            // Customize cell rendering for the image column
            imageCol.setCellFactory(column -> new TableCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(Image image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        setGraphic(imageView);
                    }
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}