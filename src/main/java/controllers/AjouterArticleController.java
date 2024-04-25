package controllers;

import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ArticleService;
import services.CategotyArticleService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterArticleController {
    private final ArticleService ps = new ArticleService();
    private final CategotyArticleService cs = new CategotyArticleService();
    public int imgState=0;
    public Image imag;
    @FXML
    private ImageView preview;

    @FXML
    private Label D;

    @FXML
    private TextField prix;

    @FXML
    private Button upload;

    @FXML
    private Label Xprix;

    @FXML
    private Label Ximage;

    @FXML
    private Label I;

    @FXML
    private Text title;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField nom;

    @FXML
    private Label Xdesc;

    @FXML
    private Label N;

    @FXML
    private Label P;

    @FXML
    private Label Xid;

    @FXML
    private Label T;

    @FXML
    private TextField id;

    @FXML
    private Label Xtype;

    @FXML
    private Label Xnom;

    @FXML
    private TextField desc;

    @FXML
    void enregistrer(ActionEvent event) throws SQLException, IOException {
        Article a = new Article();
        if (desc.getText().isEmpty() || prix.getText().isEmpty() || nom.getText().isEmpty() || id.getText().isEmpty() || type.getValue() == null || imgState!=0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
        } else if (desc.getText().length() <10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La description ne peut pas être vide et contenir moins de 10 caractères.");
            alert.showAndWait();

        } else if (!prix.getText().matches("\\d+(\\.\\d+)?") || Float.parseFloat(prix.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le prix ne peut pas être vide et ne contient que des chiffres. \n L'entrée doit être positive.");
            alert.showAndWait();
        } else if (nom.getText().length() <8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le nom ne peut pas être vide et contenir moins de 8 caractères.");
            alert.showAndWait();
        } else if (Xtype.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un type");
            alert.showAndWait();
        }else if (Xid.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'id ne peut pas être vide (chiffres uniquement).");
            alert.showAndWait();
        }else if (Ximage.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir une image");
            alert.showAndWait();
        } else {
            a.setDescription(desc.getText());
            a.setType(cs.recuperer2(type.getValue()));
            a.setIdA(Integer.parseInt(id.getText()));
            a.setPrix(prix.getText());
            a.setImage(imag);
            a.setNom(nom.getText());
            System.out.println(a);
            ps.ajouter(a);
        }
    }

    public AjouterArticleController() throws SQLException {
    }

    public int id_a=-1;
    @FXML
    void initialize() throws SQLException, IOException {
        List<String> items =cs.recupererId();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        type.setItems(observableItems);
        if(id_a!=-1) {
            ArticleService as = new ArticleService();
            Article article = as.recuperer1(id_a);
            id.setText(Integer.toString(article.getIdA()));
            nom.setText(article.getNom());
            desc.setText(article.getDescription());
            type.setValue(article.getType().getNomCat());
            prix.setText(String.valueOf(article.getPrix()));
            Xid.setVisible(false);
            Xnom.setVisible(false);
            Xdesc.setVisible(false);
            Xtype.setVisible(false);
            Ximage.setVisible(true);
            Xprix.setVisible(false);
            System.out.println(id_a);
        }
        desc.textProperty().addListener((observable, oldValue, newValue) -> {
            Xdesc.setVisible(newValue.length() < 10);
        });
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            Xnom.setVisible(newValue.length() < 8);
        });
        Xtype.visibleProperty().bind(type.getSelectionModel().selectedIndexProperty().isEqualTo(-1));

        prix.textProperty().addListener((observable, oldValue, newValue) -> {
            Xprix.setVisible(!isValidPrice(newValue));
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") || Integer.parseInt(newValue) <= 0) {
                Xid.setVisible(true);
            } else {
                Xid.setVisible(false);
            }
        });
        upload.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Image prev = new Image(fileInputStream);
                imag = prev;
                preview.setImage(prev);
                Ximage.setVisible(false);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });
    }
    private boolean isValidPrice(String text) {
        // Vérifier si le texte est vide
        if (text == null || text.isEmpty()) {
            return false;
        }

        try {
            double price = Double.parseDouble(text);
            return price >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}