package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.TournoiCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ParticipationController {
    private final TournoiCrud ps = new TournoiCrud();
    public String idT ;

    public void setIdT(String idT) {
        this.idT = idT;
    }

    public ParticipationController(String a) throws SQLException, IOException {
        Tournoi t = ps.recupererType(a);
        System.out.println(t);
        setIdT(Integer.toString(t.getId()));
    }
    @FXML
    private Button buttonFB;

    @FXML
    private Label d4;
    @FXML
    private ImageView I;
    @FXML
    private Label pourc;


    @FXML
    private Label R;

    @FXML
    private Label T;

    @FXML
    private Label D;

    @FXML
    private HBox homeNav;

    @FXML
    private Label J;

    @FXML
    private Label d1;

    @FXML
    private Label d2;

    @FXML
    private Label d3;

    @FXML
    private ImageView pu;

    @FXML
    private ImageView pd;

    @FXML
    private Rectangle pr;

    void rate(Rectangle pr,int note){
        pr.setWidth((double) (340 * note) /100);
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Tournoi t =ps.recuperer(idT);
        R.setText(Integer.toString(t.getId()));
        J.setText(t.getJeuID().getNom());
        T.setText(t.getType());
        D.setText(String.valueOf(t.getDate()));
        I.setImage(t.getJeuID().getImage());
        rate(pr,t.getNote());
        pourc.setText(String.valueOf(t.getNote())+" %");
        pu.setOnMouseClicked(mouseEvent -> {
            try {
                ps.noter(t.getId(),100);
                Tournoi t2 =ps.recuperer(idT);
                rate(pr,t2.getNote());
                pourc.setText(String.valueOf(t2.getNote())+" %");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        pd.setOnMouseClicked(mouseEvent -> {
            try {
                ps.noter(t.getId(),0);
                Tournoi t2 =ps.recuperer(idT);
                rate(pr,t2.getNote());
                pourc.setText(String.valueOf(t2.getNote())+" %");
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



    @FXML
    void navhome(MouseEvent mouseEvent) {
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
        redirect("/affichTournoi.fxml",mouseEvent);
    }

    @FXML
    void navjeu(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navbout(MouseEvent mouseEvent) {
        redirect("/AjoutTournoi.fxml",mouseEvent);
    }
    @FXML
    void Retour(MouseEvent mouseEvent) {
        redirect("/affichTournoi.fxml",mouseEvent);
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
    @FXML
    private void shareOnFacebook(MouseEvent event ) {
        // Récupérer les détails du tournoi que vous souhaitez partager
        String tournoiDetails = "Détails du tournoi : " + R.getText() + ", " + J.getText() + ", " + T.getText() + ", " + D.getText();

        // Utiliser l'API de partage Facebook pour partager les détails du tournoi
        // Remplacez "YOUR_FACEBOOK_APP_ID" par votre ID d'application Facebook
        String facebookShareUrl = "https://www.facebook.com/dialog/share?414632347985913&display=popup&quote=" + tournoiDetails;

        // Ouvrir le lien de partage dans un navigateur ou un WebView
        try {
            Desktop.getDesktop().browse(new URI(facebookShareUrl));
        } catch (IOException | URISyntaxException e) {
            // Gérer les exceptions en cas de problème avec l'ouverture du lien
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du partage sur Facebook : " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
