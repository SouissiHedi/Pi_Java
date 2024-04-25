package edu.esprit.tests;

import edu.esprit.entities.Article;
import edu.esprit.services.ArticleCrud;
import edu.esprit.tools.MyConnection;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) throws SQLException {
        Article p = new Article("bidi","25","hey");
        ArticleCrud pc = new ArticleCrud();
        pc.ajouter(p);
        System.out.println(pc.afficher());
    }
}
