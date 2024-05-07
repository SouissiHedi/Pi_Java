package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Tournoi;
import edu.esprit.tools.MyConnection;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JeuCrud implements ICrud<Jeu>{
    private final Connection connection = MyConnection.getInstance().getCnx();

    public JeuCrud() throws SQLException {
    }

    @Override
    public int ajouter(Jeu j) throws SQLException {
        String req1="INSERT INTO tournoi(jeu_id,nom,genre,developpeur,image,game) VALUES ('"+j.getJeu_id() + "','"+j.getNom()+"','"+j.getGenre()+ "','"+j.getDeveloppeur()+ "','"+j.getImage()+"','"+j.getGame()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Jeu ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int ajouter2(Jeu j) throws SQLException {
        /*String req1="INSERT INTO jeu(jeu_id,nom,genre,developpeur,image,game) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,j.getJeu_id());
            pst.setString(2,j.getNom());
            pst.setString(3,j.getGenre());
            pst.setString(4,j.getDeveloppeur());
            pst.setString(5,j.getImage());
            pst.setBlob(5,j.getGame());
            pst.executeUpdate();
            System.out.println("Jeu ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        return 0;
    }

    @Override
    public void modifier(Jeu jeu) {
    }

    @Override
    public void supprimer(Jeu jeu) {
    }

    @Override
    public List<Jeu> afficher() throws SQLException {
        return null;
    }
    @Override
    public Jeu getOneById(int id) {
        String req = "SELECT * FROM jeu WHERE id=?";
        Jeu jeu = null;
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int jeuId = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String genre = rs.getString("genre");
                    String dev = rs.getString("developpeur");
                    String imageUrl = rs.getString("image");
                    Blob game = rs.getBlob("game");
                    imageUrl = "http://localhost/images/" + imageUrl;
                    try {
                        // Create an Image object from the URL
                        Image finalImg = new Image(new URL(imageUrl).toString());
                        jeu = new Jeu(id,jeuId, nom, genre, dev, finalImg, game);
                    } catch (MalformedURLException e) {
                        // Handle malformed URL exception
                        e.printStackTrace();
                    }

                }
            }
        } catch (SQLException e) {
            // Handle SQLException appropriately (e.g., log or throw an exception)
            System.out.println("Error while retrieving Jeu by ID: " + e.getMessage());
        }
        return jeu;
    }
    @Override
    public Jeu getOneByName(String nom) {
        String req = "SELECT * FROM jeu WHERE nom=?";
        Jeu jeu = null;
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, nom);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int jeuId = rs.getInt("jeu_id");
                    String name = rs.getString("nom");
                    String genre = rs.getString("genre");
                    String dev = rs.getString("developpeur");
                    Blob game = rs.getBlob("game");
                    String imageUrl = rs.getString("image");
                    imageUrl = "http://localhost/images/" + imageUrl;
                    try {
                        // Create an Image object from the URL
                        Image finalImg = new Image(new URL(imageUrl).toString());
                        jeu = new Jeu(id,jeuId, nom, genre, dev, finalImg, game);
                    } catch (MalformedURLException e) {
                        // Handle malformed URL exception
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQLException appropriately (e.g., log or throw an exception)
            System.out.println("Error while retrieving Jeu by Name: " + e.getMessage());
        }
        return jeu;
    }
    @Override
    public Jeu recuperer(int i) throws SQLException, IOException {
        return null;
    }

    @Override
    public Jeu recuperer(String i) throws SQLException, IOException {
        return null;
    }


    @Override
    public List<String> recupererId() throws SQLException, IOException {
        String sql = "select nom from jeu";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(sql);
        ResultSet rs = statement.executeQuery(sql);
        List<String> categoryArticles = new ArrayList<>();
        while (rs.next()) {
            categoryArticles.add(rs.getString(1));
        }
        return categoryArticles;
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public Tournoi recupererType(String i) throws SQLException, IOException {
        return null;
    }
}
