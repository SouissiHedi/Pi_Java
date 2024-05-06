package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.JeuCrud;
import edu.esprit.services.TournoiCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ModifierTournoi {
    public int id_t;

    public void setId_t(int id_t) {
        System.out.println(id_t);
        this.id_t = id_t;
    }

    private final TournoiCrud tr= new TournoiCrud();

    private final JeuCrud js=new JeuCrud();
    public int updating=0;
    @FXML
    private DatePicker date;

    @FXML
    private HBox homeNav;

    @FXML
    private Label idcontroller;

    @FXML
    private Button enreg;

    @FXML
    private Label datecontroller;

    @FXML
    private TextField type;

    @FXML
    private Text title;

    @FXML
    private Label jeucontroller;

    @FXML
    private Label d1;

    @FXML
    private Label typecontrolleur;

    @FXML
    private Label d2;

    @FXML
    private Label d3;

    @FXML
    private Label d4;

    @FXML
    private ComboBox<String> jeu;

    @FXML
    private TextField id;

    public ModifierTournoi(String labelId) throws SQLException {
        setId_t(Integer.parseInt(labelId));
    }

    @FXML
    void initialize() throws SQLException, IOException {
        List<String> items =js.recupererId();
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        jeu.setItems(observableItems);
        change();
        date.valueProperty().addListener((observable, oldValue, newValue) -> {
            datecontroller.setVisible(newValue== null);
            updating=1;
        });
        jeucontroller.visibleProperty().bind(jeu.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        type.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]+$") ) {
                typecontrolleur.setVisible(true);
            } else {
                typecontrolleur.setVisible(false);
            }
            updating=1;
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") || Integer.parseInt(newValue) <= 0) {
                idcontroller.setVisible(true);
            } else {
                idcontroller.setVisible(false);
            }
            updating=1;
        });

        enreg.setOnMouseClicked(event -> {
            try {
                modifier();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    void modifier() throws SQLException, IOException {
        if (updating==1) {
            if (id.getText().isEmpty() || jeu.getValue() == null || type.getText().isEmpty() || date.getValue() == null) {
                showAlert("Erreur", "Tous les champs doivent être remplis.");
            } else if (!type.getText().matches("^[a-zA-Z]+$")) {
                showAlert("Erreur", "Le type ne peut pas être vide et ne contenir que des caractères.");
            } else if (jeucontroller.isVisible()) {
                showAlert("Erreur", "Veuillez sélectionner un type.");
            } else if (idcontroller.isVisible()) {
                showAlert("Erreur", "L'ID ne peut contenir que des chiffres.");
            } else if (check(Integer.parseInt(id.getText()))) {
                showAlert("Erreur", "La Reference choisie est utilisée");
            } else {
                LocalDate selectedDate = date.getValue();
                LocalDate currentDate = LocalDate.now();
                if (selectedDate.isBefore(currentDate)) {
                    showAlert("Erreur", "La date ne peut pas être antérieure à la date actuelle.");
                } else {
                    Tournoi t = new Tournoi();
                    t.setId(Integer.parseInt(id.getText()));
                    t.setJeuID(js.getOneByName(jeu.getValue()));
                    t.setType(type.getText());
                    t.setTournoiId(tr.recuperer(Integer.toString(id_t)).getTournoiId());

                    tr.modifier(t);
                    showAlert("Succès", "Le tournoi a été modifié");
                    updating = 0;
                    change();
                }
            }
        }

    }
    private boolean check(int i) throws SQLException, IOException {
        if (tr.recuperer(String.valueOf(id_t)).getTournoiId()!=i){
            return tr.tournoiU(i);
        }
        return false;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
    private void change() throws SQLException, IOException {
        Tournoi tournoi = tr.recuperer(Integer.toString(id_t));
        System.out.println(tournoi);
        if (tournoi != null) {
            id.setText(Integer.toString(id_t));
            jeu.setValue(tournoi.getJeuID().getNom());
            type.setText(tournoi.getType());
            date.setValue(tournoi.getDate().toLocalDate());

            idcontroller.setVisible(false);
            typecontrolleur.setVisible(false);
            datecontroller.setVisible(false);
        } else {
            // Handle the case when tournoi is null
            showAlert("Erreur", "Impossible de trouver le tournoi avec l'ID spécifié.");
        }
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
        redirect("/backTournoi.fxml",mouseEvent);
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