package controllers;

import edu.esprit.entities.CategoryArticle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ArticleService;
import services.CategotyArticleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RechercheController {
    ArticleService as = new ArticleService();
    CategotyArticleService cs = new CategotyArticleService();

    List<CategoryArticle> cat =cs.recuperer();
    static int categoryCount = 0;
    public AfficherBoutiqueController boutique;

    static Stage panStage;

    public static Stage getPanStage() {
        return panStage;
    }

    public static void setPanStage(Stage panStage) {
        RechercheController.panStage = panStage;
    }



    public RechercheController(Stage panStage, AfficherBoutiqueController boutique) throws SQLException, IOException {
        setPanStage(panStage);
        this.boutique = boutique;
        categoryCount=cs.count();
        categoryCheckBoxes=new CheckBox[categoryCount];
    }

    @FXML
    private VBox container;
    @FXML
    private TextField nomC;
    @FXML
    private TextField descC;
    @FXML
    private TextField prixC;

    private static CheckBox allCategoriesCheckBox;
    private static CheckBox[] categoryCheckBoxes ;

    @FXML
    public void initialize() throws SQLException, IOException {
        createHBox();
        allCategoriesCheckBox = (CheckBox) container.lookup("#CKB0");
        allCategoriesCheckBox.setSelected(true);
        for (int i = 1; i <= categoryCount; i++) {
            categoryCheckBoxes[i-1] = (CheckBox) container.lookup("#CKB" + i);
            categoryCheckBoxes[i-1].setSelected(true);
        }
        allCategoriesCheckBox.setOnMouseClicked(mouseEvent -> {
            for (CheckBox cbx : categoryCheckBoxes){
                cbx.setSelected(allCategoriesCheckBox.isSelected());
            }
            try {
                boutique.getTest().setUrl(updateType());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        for (CheckBox cbx : categoryCheckBoxes){
            cbx.setOnMouseClicked(mouseEvent -> {
                if (!cbx.isSelected()){
                    allCategoriesCheckBox.setSelected(false);
                }
                try {
                    boutique.getTest().setUrl(updateType());
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        nomC.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boutique.getTest().setUrl(updateType());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            boutique.getTest().setNom(newValue);
        });
        descC.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boutique.getTest().setUrl(updateType());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            boutique.getTest().setDescription(newValue);
        });
        prixC.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boutique.getTest().setUrl(updateType());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            boutique.getTest().setPrix(newValue);
        });

    }

    private String updateType() throws SQLException, IOException {
        String T = "-1";
        for (CheckBox cbx : categoryCheckBoxes){
            if (cbx.isSelected()){
                int a = cat.get(Integer.parseInt(String.valueOf(cbx.getId().charAt(cbx.getId().length() - 1)))-1).getId();
                T+=","+ a;
            }
        }
        return T;
    }
    private void createHBox() throws SQLException, IOException {
        int itc=0;
        container.getChildren().clear();
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        CheckBox ch = new CheckBox();
        ch.setPrefWidth(167);
        ch.setPrefHeight(21);
        ch.setWrapText(true);
        ch.setText("Toutes les categories");
        ch.setId("CKB"+Integer.toString(itc));
        hbox.getChildren().add(ch);
        container.getChildren().add(hbox);
        for (CategoryArticle categorie : cat) {
            itc++;
            hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            ch = new CheckBox();
            ch.setPrefWidth(167);
            ch.setPrefHeight(21);
            ch.setWrapText(true);
            ch.setText(categorie.getNomCat());
            ch.setId("CKB"+Integer.toString(itc));
            hbox.getChildren().add(ch);
            container.getChildren().add(hbox);
        }
    }

    private void handleCheckout(ActionEvent actionEvent) {
        panStage.close();
    }

}
