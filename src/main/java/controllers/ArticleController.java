package controllers;

import edu.esprit.entities.Article;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ArticleService;

import java.io.IOException;
import java.sql.SQLException;

public class ArticleController {
    private final ArticleService ps = new ArticleService();
    public String name ;

    public void setName(String name) {
        this.name = name;
    }
    @FXML
    private HBox homeNav;
    @FXML
    private Label T;

    @FXML
    private Label D;
    @FXML
    private ImageView I;

    @FXML
    private Label L;

    @FXML
    private Label N;


    public ArticleController(String name) throws SQLException {
        setName(name);
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Article a =ps.recupererNom(name);
        N.setText(name);
        I.setImage(a.getImage());
        L.setText(a.getPrix());
        T.setText(a.getType().getNomCat());
        D.setText(a.getDescription());
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