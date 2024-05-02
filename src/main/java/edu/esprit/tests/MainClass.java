package edu.esprit.tests;

import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;
import edu.esprit.services.ArticleCrud;
import edu.esprit.tools.MyConnection;
import services.CategotyArticleService;
import java.awt.Desktop;
import java.net.URI;

import java.sql.SQLException;

public class MainClass {

            public static void main(String[] args) {
                try {
                    String imageURL = "http://localhost/images/logo.png";
                    Desktop.getDesktop().browse(new URI(imageURL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
}
