package controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ReclamationCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class ReponseController {
    private final ReclamationCrud reclamationcrud = new ReclamationCrud();
    @FXML
    private Text Xdesc;
    @FXML
    private Button modifier;
    @FXML
    private TextField chois;
    @FXML
    private Text CH;

    @FXML
    private Text Xnum;

    @FXML
    private Text Xtype;
    public int updating=0;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnReturn;

    @FXML
    private TextField description;

    @FXML
    private Text error;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField id;

    @FXML
    private Label t1;

    @FXML
    private Label titre;

    @FXML
    private ChoiceBox<String> type;
    public int id_r=-1;

    public ReponseController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        chois.setVisible(false);
        CH.setVisible(false);
        Xnum.setVisible(false); // Pour rendre l'élément "a" invisible
        Xtype.setVisible(false);
        Xdesc.setVisible(false);
        if (id_r != -1) {
            ReclamationCrud reclamation = new ReclamationCrud();
            Reclamation rec = reclamation.getOneById(id_r);
            id.setText(Integer.toString((rec.getId())));
            type.setValue(rec.getType());
            description.setText(rec.getDescription());
            Xnum.setVisible(false); // Pour rendre l'élément "a" invisible
            Xtype.setVisible(false);
            Xdesc.setVisible(false);
            System.out.println(id_r);

        }
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+") || Integer.parseInt(newValue) <= 0) {
                Xnum.setVisible(true);
            } else {
                Xnum.setVisible(false);
            }
            updating = 1;
        });
    }

    public void choisir(ActionEvent event) throws SQLException, IOException {
        if (id_r == -1) {
            chois.setVisible(true);
            CH.setVisible(true);
            id_r = -2;
        } else if (id_r  == -2){
            String enteredValue = chois.getText();
            if (isValidReclamationId(enteredValue)) {
                id_r= Integer.parseInt(enteredValue);
                change();
                updating=0;
                chois.setVisible(false);
                CH.setVisible(false);
            } else {
                showAlert("ID Invalide", "La valeur saisie n'est pas un ID valide d'un article.");
            }
        }else{
            if (updating==1){
                if (id.getText().isEmpty() || type.getValue() == null || description.getText().isEmpty()  ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Tous les champs doivent être remplis.");
                    alert.showAndWait();
                } else if (!description.getText().matches("^[a-zA-Z]+$") ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le type ne peut pas être vide et ne contient que des caratères. ");
                    alert.showAndWait();
                } else if (Xtype.isVisible()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez sélectionner un type");
                    alert.showAndWait();
                }else if (Xnum.isVisible()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("L'id ne peut pas être vide (chiffres uniquement).");
                    alert.showAndWait();
                }else{
                    Reclamation t =new Reclamation();

                    t.setId(Integer.parseInt(id.getText()));
                    t.setType(type.getValue());
                    t.setDescription(description.getText());

                    reclamationcrud.modifier(t);
                    showAlert("Succès", "Le tournoi a été modifié");
                    btnCreate.setVisible(true);
                    updating=0;
                    id_r=-1;
                }
            }else {
                chois.setVisible(true);
                CH.setVisible(true);
                id_r=-2;
            }
        }
    }

    private void change() throws SQLException, IOException {
        titre.setText("Modifier Tournoi");
        btnCreate.setVisible(false);
        ReclamationCrud rec = new ReclamationCrud();
        Reclamation r = rec.recuperer(String.valueOf(id_r));
        if (r != null) {
            id.setText(Integer.toString(r.getId()));
            type.setValue(r.getType());
            description.setText(r.getType());
            Xnum.setVisible(false); // Pour rendre l'élément "a" invisible
            Xtype.setVisible(false);
            Xdesc.setVisible(false);



        } else {
            // Handle the case when tournoi is null
            showAlert("Erreur", "Impossible de trouver le tournoi avec l'ID spécifié.");
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidReclamationId(String id) {
        try {
            int recId = Integer.parseInt(id);
            ReclamationCrud tournoiCrud = new ReclamationCrud();
            return tournoiCrud.recExists(recId);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @FXML
    void enregistrer(ActionEvent event) throws SQLException {

        Reclamation R = new Reclamation();
        if (id.getText().isEmpty() || type.getValue() == null || description.getText().isEmpty()  ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
        } else if (!description.getText().matches("^[a-zA-Z]+$") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le type ne peut pas être vide et ne contient que des caratères. ");
            alert.showAndWait();
        } else if (Xtype.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un type");
            alert.showAndWait();
        }else if (Xnum.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'id ne peut pas être vide (chiffres uniquement).");
            alert.showAndWait();
        } else {
            R.setId(Integer.parseInt(id.getText()));
            R.setDescription(description.getText());
            R.setType(type.getValue());

            reclamationcrud.ajouter2(R);
        }
    }



    public boolean validateForm(String i,String t,String d) {
        if (!(i.isEmpty() || t.isEmpty() || d.isEmpty())) {
            try {
                int p1 = Integer.parseInt(i);
                if (p1 < 0) {
                    errorLabel.setText("id is a number !");
                    return false; // Priority is out of range

                }
            } catch (NumberFormatException e) {
                errorLabel.setText("id is a number !");
                return false;
            }
            return true;
        } else {
            errorLabel.setText("empty fields !");
            return false;
        }
    }

    @FXML
    public void handleQuestionInput(){
        if (description.getText().isEmpty()){
            description.setStyle("-fx-border-color: red;");
            Xdesc.setVisible(true);

        }
        else{
            description.setStyle("-fx-border-color: green;");
            Xdesc.setVisible(false);

        }
    }

    @FXML
    public void handlePriorityInput(){
        String i = id.getText();

        if(!i.isEmpty()){
            if (!i.matches("[0-9]+")){
                id.setStyle("-fx-border-color: red;");
                Xnum.setVisible(true);
            }
            else{
                id.setStyle("-fx-border-color: green;");
                Xnum.setVisible(false);
            }
        }
        else{
            id.setStyle("-fx-border-color: red;");
            Xnum.setVisible(true);

        }
    }


    private Stage stage ;
    private Scene scene ;

    private Parent root ;
    @FXML
    public void switchToScene(ActionEvent event, String  link) throws IOException {
        root = FXMLLoader.load(getClass().getResource(link));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void returnToQuestions(ActionEvent actionEvent) throws IOException {
        switchToScene(actionEvent,"/getQuestions.fxml");
    }

}
