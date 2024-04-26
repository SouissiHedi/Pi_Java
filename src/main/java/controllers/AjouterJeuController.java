package controllers;

import edu.esprit.entities.Article;
import edu.esprit.entities.Jeux;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import edu.esprit.services.JeuxCrud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterJeuController {
    private final JeuxCrud js = new JeuxCrud();
    public int imgState=0;
    public Image imag;
    @FXML
    private ImageView preview;
    public int updating=0;

    @FXML
    private Label D;

    @FXML
    private TextField chois;
    @FXML
    private Label CH;

    @FXML
    private Label CHT;

    @FXML
    private TextField idJ;

    @FXML
    private Button upload;

    @FXML
    private Button b;

    @FXML
    private Label Xgenre;

    @FXML
    private Label Ximage;

    @FXML
    private Label I;

    @FXML
    private Text title;


    @FXML
    private TextField nom;

    @FXML
    private TextField genre;

    @FXML
    private Label XidJ;

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
    private Label Xnom;



    @FXML
    void enregistrer(ActionEvent event) throws SQLException, IOException {
        Jeux j = new Jeux();
        if (idJ.getText().isEmpty() || genre.getText().isEmpty() || nom.getText().isEmpty() || id.getText().isEmpty() || imgState != 0) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
        } else if (nom.getText().length() < 3) {
            showAlert("Erreur", "Le nom doit contenir plus que  3 caractères .");
        } else if (genre.getText().isEmpty()) {
            showAlert("Erreur", "Le genre ne peut pas être vide .");
        } else if (nom.getText().length() < 3) {
            showAlert("Erreur", "Le nom ne peut pas être vide et contenir moins de 3 caractères.");
        } else if (!idJ.getText().matches("\\d+(\\.\\d+)?") || Float.parseFloat(idJ.getText()) < 0) {
            showAlert("Erreur", "L'id du joueur ne peut pas être vide (chiffres uniquement).");
        } else if (Xid.isVisible()) {
            showAlert("Erreur", "L'id ne peut pas être vide (chiffres uniquement).");
        } else if (Ximage.isVisible()) {
            showAlert("Erreur", "Veuillez choisir une image");
        } else {
            j.setId(Integer.parseInt(id.getText()));
            j.setJeu_id(Integer.parseInt(idJ.getText()));
            j.setGenre(genre.getText());
            j.setImage(imag);
            j.setNom(nom.getText());
            js.ajouter(j);
        }
    }

    public AjouterJeuController() throws SQLException {
    }

    public int id_a=-1;
    @FXML
    void initialize() throws SQLException, IOException {
        chois.setVisible(false);
        CH.setVisible(false);
        CHT.setVisible(false);

        title.setText("Ajouter Jeu");

        if(id_a!=-1 && id_a!=-2) {
            change();
        }
        genre.textProperty().addListener((observable, oldValue, newValue) -> {
            Xgenre.setVisible(newValue.isEmpty());
            updating=1;
        });
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            Xnom.setVisible(newValue.length() < 3);
            updating=1;
        });

        idJ.textProperty().addListener((observable, oldValue, newValue) -> {
            updating=1;
            XidJ.setVisible(!newValue.matches("\\d+") || newValue.length() < 3);
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            updating=1;
            Xid.setVisible(!newValue.matches("\\d+") || newValue.length() < 3);
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

    public void choisir(ActionEvent actionEvent) throws SQLException, IOException {
        if (id_a == -1) {
            chois.setVisible(true);
            CH.setVisible(true);
            CHT.setVisible(true);
            id_a = -2;
        } else if (id_a  == -2){
            String enteredValue = chois.getText();
            if (isValidJeuId(enteredValue)) {
                id_a= Integer.parseInt(enteredValue);
                change();
                updating=0;
                chois.setVisible(false);
                CH.setVisible(false);
                CHT.setVisible(false);
            } else {
                showAlert("ID Invalide", "La valeur saisie n'est pas un ID valide d'un jeu.");
            }
        }else{
            if (updating==1){
                    showAlert("Succès", "L'Article a été modifié");
                    b.setVisible(true);
                    updating=0;
                    id_a=-1;

            }else {
                chois.setVisible(true);
                CH.setVisible(true);
                CHT.setVisible(true);
                id_a=-2;
            }
        }
    }

    private boolean isValidJeuId(String id) {
        try {
            int JeuId = Integer.parseInt(id);
            JeuxCrud js = new JeuxCrud();
            return js.jeuExists(JeuId);
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
        JeuxCrud js = new JeuxCrud();
        Jeux jeux = js.recuperer2(String.valueOf(id_a));
        id.setText(Integer.toString(jeux.getId()));
        nom.setText(jeux.getNom());
        genre.setText(jeux.getGenre());
        idJ.setText(Integer.toString(jeux.getJeu_id()));

        preview.setImage(jeux.getImage());
        imag =jeux.getImage();
        Xid.setVisible(false);
        Xnom.setVisible(false);
        Xgenre.setVisible(false);
        Ximage.setVisible(false);
        XidJ.setVisible(false);
    }
}