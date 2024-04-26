package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.TournoiCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherTournoiController {
    private final TournoiCrud ps = new TournoiCrud();
    @FXML
    private TableColumn<Tournoi, Integer> tournoiIdCol;
    @FXML
    private TableColumn<Tournoi, String> typeCol;
    @FXML
    private TableColumn<Tournoi, Integer> jeuIdCol;
    @FXML
    private TableColumn<Tournoi, Date> dateCol;
    @FXML
    private TableColumn<Tournoi, Integer> countCol;
    @FXML
    private TableView<Tournoi> tableView;

    public AfficherTournoiController() throws SQLException {
    }

    @FXML
    void initialize() {
        List<Tournoi> tournois = ps.afficher();

        ObservableList<Tournoi> observableList = FXCollections.observableList(tournois);
        tableView.setItems(observableList);

        tournoiIdCol.setCellValueFactory(new PropertyValueFactory<>("tournoiId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        jeuIdCol.setCellValueFactory(new PropertyValueFactory<>("jeuIdId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
}