package controllers;

import edu.esprit.entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

public class AjoutArtController {
    private final ArticleService ps = new ArticleService();
    private final CategotyArticleService cs = new CategotyArticleService();
    public int imgState=0;
    public Image imag;
    String imagURL;
    @FXML
    private ImageView preview;

    @FXML
    private TextField prix;

    @FXML
    private Button upload;


    @FXML
    private Label Xprix;

    @FXML
    private Label Ximage;


    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField nom;

    @FXML
    private Label Xdesc;


    @FXML
    private Label Xid;


    @FXML
    private TextField id;

    @FXML
    private Label Xtype;

    @FXML
    private Label Xnom;

    @FXML
    private TextField desc;
    @FXML
    private HBox homeNav;

    public AjoutArtController() throws SQLException, IOException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        List<String> items =cs.recupererId();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        type.setItems(observableItems);

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
            if (file != null) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    Image prev = new Image(fileInputStream);
                    System.out.println(file.getAbsolutePath());
                    imag = prev;
                    imagURL=file.getAbsolutePath();
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void ajouter(MouseEvent mouseEvent) throws SQLException, IOException {
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
        } else if (Xid.isVisible() ){
            showAlert("Erreur", "L'id ne peut pas être vide (chiffres uniquement).");
        } else if (Ximage.isVisible()) {
            showAlert("Erreur", "Veuillez choisir une image");
        }else{
            Article a = new Article();
            a.setDescription(desc.getText());
            a.setType(cs.recuperer2(type.getValue()));
            a.setIdA(Integer.parseInt(id.getText()));
            a.setPrix(prix.getText());
            a.setUrl(imagURL);
            a.setImage(imag);
            a.setNom(nom.getText());
            if(ps.ajouter(a)==0){
                showAlert("Succès", "L'Article a été ajouté");
                getback(mouseEvent);
            }
        }
    }

    @FXML
    public void getback(MouseEvent mouseEvent) {
        redirect("/ArticleBack.fxml",mouseEvent);
    }

    @FXML
    public void navhome(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navrec(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navtroph(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navtour(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navjeu(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navbout(MouseEvent mouseEvent) {
        redirect("/template.fxml",mouseEvent);
    }

    public void redirect(String s,MouseEvent mouseEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(s));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}