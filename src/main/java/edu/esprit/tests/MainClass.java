package edu.esprit.tests;

import edu.esprit.entities.Personne;
import edu.esprit.services.PersonneCrud;
import edu.esprit.tools.MyConnection;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        Personne p = new Personne("bidi","aziwez");
        PersonneCrud pc = new PersonneCrud();
        pc.modifier(p);
        System.out.println(pc.afficher());
    }
}
