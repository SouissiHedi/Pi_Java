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
    public int updating=0;

    @FXML
    private Label D;

    @FXML
    private Label CH;

    @FXML
    private Label CHT;

    @FXML
    private TextField prix;

    @FXML
    private Button upload;

    @FXML
    private Button b;

    @FXML
    private Button suppr;

    @FXML
    private Button Aj;

    @FXML
    private Label Xprix;

    @FXML
    private Label Ximage;

    @FXML
    private Label I;

    @FXML
    private Text title;

    @FXML
    private TextField chois;

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
        if (desc.getText().isEmpty() || prix.getText().isEmpty() || nom.getText().isEmpty() || id.getText().isEmpty() || type.getValue() == null || imgState != 0) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
        } else if (desc.getText().length() < 10) {
            showAlert("Erreur", "La description ne peut pas être vide et contenir moins de 10 caractères.");
        } else if (!prix.getText().matches("\\d+(\\.\\d+)?") || Float.parseFloat(prix.getText()) < 0) {
            showAlert("Erreur", "Le prix ne peut pas être vide et ne contient que des chiffres. \n L'entrée doit être positive.");
        } else if (nom.getText().length() < 8) {
            showAlert("Erreur", "Le nom ne peut pas être vide et contenir moins de 8 caractères.");
        } else if (Xtype.isVisible()) {
            showAlert("Erreur", "Veuillez sélectionner un type");
        } else if (Xid.isVisible()) {
            showAlert("Erreur", "L'id ne peut pas être vide (chiffres uniquement).");
        } else if (Ximage.isVisible()) {
            showAlert("Erreur", "Veuillez choisir une image");
        } else {
            a.setDescription(desc.getText());
            a.setType(cs.recuperer2(type.getValue()));
            a.setIdA(Integer.parseInt(id.getText()));
            a.setPrix(prix.getText());
            a.setImage(imag);
            a.setNom(nom.getText());
            ps.ajouter(a);
        }
    }

    public AjouterArticleController() throws SQLException {
    }

    public int id_a=-1;
    @FXML
    void initialize() throws SQLException, IOException {
        chois.setVisible(false);
        CH.setVisible(false);
        CHT.setVisible(false);
        suppr.setVisible(false);
        List<String> items =cs.recupererId();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        type.setItems(observableItems);

        title.setText("Ajouter Article");

        if(id_a!=-1 && id_a!=-2) {
            change();
        }
        desc.textProperty().addListener((observable, oldValue, newValue) -> {
            Xdesc.setVisible(newValue.length() < 10);
            updating=1;
        });
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            Xnom.setVisible(newValue.length() < 8);
            updating=1;
        });
        Xtype.visibleProperty().bind(type.getSelectionModel().selectedIndexProperty().isEqualTo(-1));

        prix.textProperty().addListener((observable, oldValue, newValue) -> {
            Xprix.setVisible(!isValidPrice(newValue));
            updating=1;
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            updating=1;
            if (!newValue.matches("\\d+") || Integer.parseInt(newValue) <= 0) {
                Xid.setVisible(true);
            } else {
                Xid.setVisible(false);
            }
        });
        upload.setOnAction(e->{
            updating=1;
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
            if (file != null) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    Image prev = new Image(fileInputStream);
                    imag = prev;
                    preview.setImage(prev);
                    Ximage.setVisible(false);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private boolean isValidPrice(String text) {
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

    public void supprimer(ActionEvent actionEvent) throws SQLException {
        ps.supprimer(id_a);
    }
    public void choisir(ActionEvent actionEvent) throws SQLException, IOException {
        if (id_a == -1) {
            chois.setVisible(true);
            CH.setVisible(true);
            CHT.setVisible(true);
            id_a = -2;
        } else if (id_a  == -2){
            String enteredValue = chois.getText();
            if (isValidArticleId(enteredValue)) {
                id_a= Integer.parseInt(enteredValue);
                change();
                updating=0;
                chois.setVisible(false);
                CH.setVisible(false);
                CHT.setVisible(false);
            } else {
                showAlert("ID Invalide", "La valeur saisie n'est pas un ID valide d'un article.");
            }
        }else{
            if (updating==1){
                if (desc.getText().isEmpty() || prix.getText().isEmpty() || nom.getText().isEmpty() || id.getText().isEmpty() || type.getValue() == null || imgState != 0) {
                    showAlert("Erreur", "Tous les champs doivent être remplis.");
                } else if (desc.getText().length() < 10) {
                    showAlert("Erreur", "La description ne peut pas être vide et contenir moins de 10 caractères.");
                } else if (!prix.getText().matches("\\d+(\\.\\d+)?") || Float.parseFloat(prix.getText()) < 0) {
                    showAlert("Erreur", "Le prix ne peut pas être vide et ne contient que des chiffres. \n L'entrée doit être positive.");
                } else if (nom.getText().length() < 8) {
                    showAlert("Erreur", "Le nom ne peut pas être vide et contenir moins de 8 caractères.");
                } else if (Xtype.isVisible()) {
                    showAlert("Erreur", "Veuillez sélectionner un type");
                } else if (Xid.isVisible()) {
                    showAlert("Erreur", "L'id ne peut pas être vide (chiffres uniquement).");
                } else if (Ximage.isVisible()) {
                    showAlert("Erreur", "Veuillez choisir une image");
                }else{
                    Article a =new Article();
                    a.setDescription(desc.getText());
                    a.setId(Integer.parseInt(chois.getText()));
                    a.setType(cs.recuperer2(type.getValue()));
                    a.setIdA(Integer.parseInt(id.getText()));
                    a.setPrix(prix.getText());
                    a.setImage(imag);
                    a.setNom(nom.getText());
                    ps.modifier(a);
                    showAlert("Succès", "L'Article a été modifié");
                    b.setVisible(true);
                    updating=0;
                    id_a=-1;
                }
            }else {
                chois.setVisible(true);
                CH.setVisible(true);
                CHT.setVisible(true);
                id_a=-2;
            }
        }
    }

    private boolean isValidArticleId(String id) {
        try {
            int articleId = Integer.parseInt(id);
            ArticleService articleService = new ArticleService();
            return articleService.articleExists(articleId);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void change() throws SQLException, IOException {
        title.setText("Modifier Article");
        b.setVisible(false);
        suppr.setVisible(true);
        ArticleService as = new ArticleService();
        Article article = as.recuperer2(String.valueOf(id_a));
        id.setText(Integer.toString(article.getIdA()));
        nom.setText(article.getNom());
        desc.setText(article.getDescription());
        type.setValue(article.getType().getNomCat());
        prix.setText(String.valueOf(article.getPrix()));

        preview.setImage(article.getImage());
        imag =article.getImage();
        Xid.setVisible(false);
        Xnom.setVisible(false);
        Xdesc.setVisible(false);
        Ximage.setVisible(false);
        Xprix.setVisible(false);
    }
}