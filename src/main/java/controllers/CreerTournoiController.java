package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import edu.esprit.services.JeuCrud;
import edu.esprit.services.TournoiCrud;
import edu.esprit.entities.Tournoi;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CreerTournoiController {

    private final TournoiCrud tr= new TournoiCrud();
    LocalDate currentDate = LocalDate.now();
    private final JeuCrud js=new JeuCrud();
    @FXML
    private Button enreg;
    @FXML
    private Text title;

    @FXML
    private Label jeucontroller;
    @FXML
    private Label datecontroller;

    @FXML
    private Label d4;

    @FXML
    private DatePicker date;

    @FXML
    private Label typecontrolleur;

    @FXML
    private Label idcontroller;

    @FXML
    private ComboBox<String> jeu;

    @FXML
    private TextField id;

    @FXML
    private TextField type;

    @FXML
    private Label d1;

    @FXML
    private Label d2;

    @FXML
    private Label d3;
    @FXML
    void enregistrer(ActionEvent event) throws SQLException {
        Tournoi t = new Tournoi();
        if (id.getText().isEmpty() || jeu.getValue() == null || type.getText().isEmpty() || date.getValue() == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
        } else if (!type.getText().matches("^[a-zA-Z]+$")) {
            showAlert("Erreur", "Le type ne peut pas être vide et ne contenir que des caractères.");
        } else if (jeucontroller.isVisible()) {
            showAlert("Erreur", "Veuillez sélectionner un type.");
        } else if (idcontroller.isVisible()) {
            showAlert("Erreur", "L'ID ne peut contenir que des chiffres.");
        } else {
            LocalDate selectedDate = date.getValue();
            LocalDate currentDate = LocalDate.now();
            if (selectedDate.isBefore(currentDate)) {
                showAlert("Erreur", "La date ne peut pas être antérieure à la date actuelle.");
            } else {
                t.setTournoiId(Integer.parseInt(id.getText()));
                t.setJeuID(js.getOneByName(jeu.getValue()));
                t.setType(type.getText());
                t.setDate(Date.valueOf(selectedDate)); // Convertir LocalDate en SQL Date
                if (tr.ajouter2(t)==0){
                    showNotification("Succès", "Le tournoi a été ajouté.");
                }
            }
        }
    }

    public CreerTournoiController() throws SQLException {
    }

    public int id_t=-1;
    @FXML
    void initialize() throws SQLException, IOException {
        List<String> items =js.recupererId();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        jeu.setItems(observableItems);
        title.setText("Ajouter Tournoi");
        if(id_t!=-1) {
            TournoiCrud as = new TournoiCrud();
            Tournoi tournoi = as.getOneById(id_t);
            id.setText(Integer.toString(tournoi.getTournoiId()));
            jeu.setValue(tournoi.getJeuID().getNom());
            type.setText(tournoi.getType());
            date.setValue(tournoi.getDate().toLocalDate());
            idcontroller.setVisible(false); // Pour rendre l'élément "a" invisible
            jeucontroller.setVisible(false);
            typecontrolleur.setVisible(false);
            datecontroller.setVisible(false);
            System.out.println(id_t);
        }
        date.valueProperty().addListener((observable, oldValue, newValue) -> {
            datecontroller.setVisible(newValue== null);
        });
        jeucontroller.visibleProperty().bind(jeu.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        type.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]+$") ) {
                typecontrolleur.setVisible(true);
            } else {
                typecontrolleur.setVisible(false);
            }
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") || Integer.parseInt(newValue) <= 0) {
                idcontroller.setVisible(true);
            } else {
                idcontroller.setVisible(false);
            }
        });


    }


    private boolean isValidTournoiId(String id) {
        try {
            int tournoiId = Integer.parseInt(id);
            TournoiCrud tournoiCrud = new TournoiCrud();
            return tournoiCrud.tournoiExists(tournoiId);
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
    private void showNotification(String title, String content) {
        Notifications notification =Notifications.create()
                .title(title)
                .text(content);

        Platform.runLater(() -> notification.showInformation());
    }

}




