package edu.esprit.services;

import edu.esprit.entities.Article;
import edu.esprit.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ArticleCrud implements ICrud<Article>{
    @Override
    public void ajouter(Article article) throws SQLException {
        String req1="INSERT INTO article(nom,prix) VALUES ('"+article.getNom()+"','"+article.getPrix()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Article ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter2(Article article) throws SQLException {
        String req1="INSERT INTO article(nom,prix) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1,article.getNom());
            pst.setString(2,article.getPrix());
            pst.executeUpdate();
            System.out.println("Article ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Article article) {
        String nom = article.getNom();
        String prix = article.getPrix();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Entrer le nom:");
            nom = reader.readLine();

            System.out.println("Entrer le prix:");
            prix = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for ID and Age.");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Error closing BufferedReader: " + e.getMessage());
            }
        }
        String req = "UPDATE article SET nom = '" + nom + "', prix = '" + prix + "' WHERE nom = '" + article.getNom()+"'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req);
            if (rowsAffected > 0) {
                System.out.println("Article mise à jour !");
            } else {
                System.out.println("Aucun article trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'article: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Article article) {
        String req2 = "DELETE FROM article WHERE nom = '" + article.getNom() + "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("Article supprimé !");
            } else {
                System.out.println("Aucun article trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'article: " + e.getMessage());
        }
    }

    @Override
    public List<Article> afficher() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String req3 ="SELECT * FROM article";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while(rs.next()){
                Article a = new Article();
                a.setId(rs.getInt(1));
                a.setNom(rs.getString("nom"));
                a.setPrix(rs.getString("prix"));
                a.setDescription(rs.getString("description"));
                articles.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return articles;
    }
}
