package controllers;

import edu.esprit.entities.Article;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ArticleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private Label R;

    List<Article> la = new ArrayList<Article>();


    public ArticleController(String name) throws SQLException {
        setName(name);
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Article a =ps.recupererNom(name);
        la.add(a);
        N.setText(name);
        I.setImage(a.getImage());
        L.setText(a.getPrix());
        T.setText(a.getType().getNomCat());
        R.setText(Integer.toString(a.getIdA()));
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

    @FXML
    void achat(MouseEvent mouseEvent) throws SQLException {
        Stage panStage = new Stage();
        PanierController controller = new PanierController(panStage,la);
        redirect("/template.fxml",mouseEvent);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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