package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.TournoiCrud;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
}
