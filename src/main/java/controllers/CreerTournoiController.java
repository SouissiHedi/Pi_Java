package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.JeuCrud;
import edu.esprit.services.TournoiCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreerTournoiController {

    private final TournoiCrud tr= new TournoiCrud();

    private final JeuCrud js=new JeuCrud();
    public int updating=0;
    @FXML
    private TextField chois;
    @FXML
    private Button updt;
    @FXML
    private Button enreg;
    @FXML
    private Label D;

    @FXML
    private Label CH;
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
    private ComboBox<Integer> jeu;

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
        if (id.getText().isEmpty() || jeu.getValue() == null || type.getText().isEmpty() || date.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
        } else if (!type.getText().matches("^[a-zA-Z]+$") ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le type ne peut pas être vide et ne contient que des caratères. ");
        alert.showAndWait();
        } else if (jeucontroller.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un type");
            alert.showAndWait();
        }else if (idcontroller.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'id ne peut pas être vide (chiffres uniquement).");
            alert.showAndWait();
        } else {
            t.setTournoiId(Integer.parseInt(id.getText()));
            t.setJeuID(js.getOneById(jeu.getValue()));
            t.setType(type.getText());
            try {
                // Assuming "date" is a DatePicker control
                String dateString = date.getValue().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = format.parse(dateString);
                t.setDate(new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the parse exception if necessary
                e.printStackTrace();
            }
            tr.ajouter2(t);
        }
    }

    public CreerTournoiController() throws SQLException {
    }

    public int id_t=-1;
    @FXML
    void initialize() throws SQLException, IOException {
        chois.setVisible(false);
        CH.setVisible(false);
        List<Integer> items =js.recupererId();
        ObservableList<Integer> observableItems = FXCollections.observableArrayList(items);
        jeu.setItems(observableItems);
        title.setText("Ajouter Tournoi");
        if(id_t!=-1) {
            TournoiCrud as = new TournoiCrud();
            Tournoi tournoi = as.getOneById(id_t);
            id.setText(Integer.toString(tournoi.getTournoiId()));
            jeu.setValue(tournoi.getJeuID().getId());
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


    }

    public void choisir(ActionEvent actionEvent) throws SQLException, IOException {
        if (id_t == -1) {
            chois.setVisible(true);
            CH.setVisible(true);
            id_t = -2;
        } else if (id_t  == -2){
            String enteredValue = chois.getText();
            if (isValidTournoiId(enteredValue)) {
                id_t= Integer.parseInt(enteredValue);
                change();
                updating=0;
                chois.setVisible(false);
                CH.setVisible(false);
            } else {
                showAlert("ID Invalide", "La valeur saisie n'est pas un ID valide d'un article.");
            }
        }else{
            if (updating==1){
                if (id.getText().isEmpty() || jeu.getValue() == null || type.getText().isEmpty() || date.getValue() == null ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Tous les champs doivent être remplis.");
                    alert.showAndWait();
                } else if (!type.getText().matches("^[a-zA-Z]+$") ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le type ne peut pas être vide et ne contient que des caratères. ");
                    alert.showAndWait();
                } else if (jeucontroller.isVisible()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez sélectionner un type");
                    alert.showAndWait();
                }else if (idcontroller.isVisible()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("L'id ne peut pas être vide (chiffres uniquement).");
                    alert.showAndWait();
                }else{
                    Tournoi t =new Tournoi();
                    t.setId(Integer.parseInt(id.getText()));
                    t.setJeuID(js.getOneById(jeu.getValue()));
                    t.setType(type.getText());
                    try {
                        // Assuming "date" is a DatePicker control
                        String dateString = date.getValue().toString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = format.parse(dateString);
                        t.setDate(new Date(parsedDate.getTime()));
                    } catch (ParseException e) {
                        // Handle the parse exception if necessary
                        e.printStackTrace();
                    }
                    tr.modifier(t);
                    showAlert("Succès", "Le tournoi a été modifié");
                    enreg.setVisible(true);
                    updating=0;
                    id_t=-1;
                }
            }else {
                chois.setVisible(true);
                CH.setVisible(true);
                id_t=-2;
            }
        }
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
    private void change() throws SQLException, IOException {
        title.setText("Modifier Tournoi");
        enreg.setVisible(false);
        TournoiCrud tr = new TournoiCrud();
        Tournoi tournoi = tr.recuperer(String.valueOf(id_t));
        if (tournoi != null) {
            id.setText(Integer.toString(tournoi.getId()));
            jeu.setValue(tournoi.getJeuID().getId());
            type.setText(tournoi.getType());
            date.setValue(tournoi.getDate().toLocalDate());

            idcontroller.setVisible(false);
            idcontroller.setVisible(false);
            jeucontroller.setVisible(false);
            typecontrolleur.setVisible(false);
            datecontroller.setVisible(false);
        } else {
            // Handle the case when tournoi is null
            showAlert("Erreur", "Impossible de trouver le tournoi avec l'ID spécifié.");
        }
    }

}




