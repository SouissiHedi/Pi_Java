package edu.esprit.tests;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.TournoiCrud;
import edu.esprit.tools.MyConnection;
import java.sql.Date;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        java.sql.Date date = java.sql.Date.valueOf("2079-04-19");
        TournoiCrud pc = new TournoiCrud();
        System.out.println(pc.afficher());
    }
}
