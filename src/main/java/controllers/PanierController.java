package controllers;

import edu.esprit.entities.Article;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ArticleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class PanierController {
    ArticleService as = new ArticleService();
    @FXML
    private VBox container;
    @FXML
    private Button BK;
    @FXML
    private Button BY;

    static Stage panStage;
    private static int Tot=0;

    public static int getTot() {
        return Tot;
    }

    public static void setTot(int tot) {
        Tot = tot;
    }

    public static void incTot(int tot) {
        Tot = Tot + tot;
    }

    private static List<Article> locallist = new ArrayList<>();

    public static Stage getPanStage() {
        return panStage;
    }

    public static void setPanStage(Stage panStage) {
        PanierController.panStage = panStage;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showDialog(String title, String content) throws SQLException, IOException {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText("Confirmer le retrait de "+content+" du panier.");

        // Add buttons for "Yes" and "No"
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Handle button actions
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            locallist.remove(as.recupererNom(content));
            showAlert("Retirer du panier","Suppression Réussie");
            updateV();
        } else {
            showAlert("Retirer du panier","Suppression Annulée");
        }
    }

    public PanierController(Stage panStage,List<Article> la) throws SQLException {
        setPanStage(panStage);
        for (Article article : la) {
            if (!locallist.contains(article)) {
                locallist.add(article);
            }else {
                showAlert("Erreur","Cet Article est déja dans votre panier.");
            }
        }
        System.out.println(locallist);
    }

    public void updateV(){
        container.getChildren().clear();
        if (!locallist.isEmpty()) {
            int it=0;
            setTot(0);
            for (Article article : locallist){
                HBox hbox = createHBox(article,it==0);
                String idH ="Art"+Integer.toString(it);
                hbox.setId(idH);
                container.getChildren().add(hbox);
                incTot(Integer.parseInt(article.getPrix()));
                it++;

                Node node = container.lookup("#" + idH);
                node.setOnMouseClicked(mouseEvent -> {
                    try {
                        showDialog("Supprimer",article.getNom());
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                if(it>6){
                    Pane a = (Pane) container.getParent();
                    a.setPrefHeight(376+52*(it-6));
                }
            }
        }
    }

    @FXML
    public void initialize() throws SQLException, IOException {
        updateV();
        BK.setOnAction(this::handleCheckout);
        BY.setOnAction(this::effectuer);
    }

    private void effectuer(ActionEvent actionEvent) {
        int wallet=1000;
        if (getTot()>wallet){
            showAlert("You're Poor","Go ask for money or smth");
        }else {
            // decrementer wallet from user et ajouter article à l'inventaire
            locallist.clear();
            showAlert("Félicitation","Vos achats on été effectuées");
            updateV();
        }
    }


    private HBox createHBox(Article article, boolean isFirst) {
        // Create an HBox to represent the article
        HBox hbox = new HBox();
        hbox.setStyle("-fx-padding: 5;"); // Add padding to HBox
        hbox.setAlignment(Pos.CENTER_LEFT);
        // Set top margin based on whether it's the first HBox or not
        if (isFirst) {
            hbox.setStyle("-fx-padding: 15 5 5 5;"); // Larger top margin for the first HBox
        } else {
            hbox.setStyle("-fx-padding: 5;"); // Smaller top margin for subsequent HBoxes
        }

        // Create ImageView for the article image
        ImageView imageView = new ImageView();
        imageView.setImage(article.getImage()); // Assuming you have a method to get the image URL from the Article
        imageView.setFitWidth(58);
        imageView.setFitHeight(47);

        // Create Labels for the article name and price
        Label nameLabel = new Label(article.getNom());
        nameLabel.setPrefWidth(165);
        nameLabel.setPadding(new Insets(0, 0, 0, 10));

        Label priceLabel = new Label(article.getPrix());
        priceLabel.setPrefWidth(68);
        priceLabel.setAlignment(Pos.CENTER_RIGHT);
        priceLabel.setPadding(new Insets(0, 5, 0, 0));

        ImageView coinImageView = new ImageView();
        coinImageView.setImage(new Image("http://localhost/images/coin.png"));
        coinImageView.setFitWidth(21);
        coinImageView.setFitHeight(21);
        priceLabel.setGraphic(coinImageView);
        priceLabel.setContentDisplay(ContentDisplay.RIGHT);

        // Add UI elements to the HBox
        hbox.getChildren().addAll(imageView, nameLabel, priceLabel);

        return hbox;
    }

    private void handleCheckout(ActionEvent actionEvent) {
        panStage.close();
    }

}
