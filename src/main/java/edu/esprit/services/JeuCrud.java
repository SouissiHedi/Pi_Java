package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Tournoi;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JeuCrud implements ICrud<Jeu>{
    private final Connection connection = MyConnection.getInstance().getCnx();

    public JeuCrud() throws SQLException {
    }

    @Override
    public void ajouter(Jeu j) throws SQLException {
        String req1="INSERT INTO tournoi(jeu_id,nom,genre,developpeur,image,game) VALUES ('"+j.getJeu_id() + "','"+j.getNom()+"','"+j.getGenre()+ "','"+j.getDeveloppeur()+ "','"+j.getImage()+"','"+j.getGame()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Jeu ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter2(Jeu j) throws SQLException {
        String req1="INSERT INTO jeu(jeu_id,nom,genre,developpeur,image,game) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,j.getJeu_id());
            pst.setString(2,j.getNom());
            pst.setString(3,j.getGenre());
            pst.setString(4,j.getDeveloppeur());
            pst.setBlob(5,j.getImage());
            pst.setBlob(5,j.getGame());
            pst.executeUpdate();
            System.out.println("Jeu ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                    Blob image = rs.getBlob("image");
                    Blob game = rs.getBlob("game");

                    jeu = new Jeu(id,jeuId, nom, genre, dev, image, game);
                }
            }
        } catch (SQLException e) {
            // Handle SQLException appropriately (e.g., log or throw an exception)
            System.out.println("Error while retrieving Jeu by ID: " + e.getMessage());
        }
        return jeu;
    }

    @Override
    public Tournoi recuperer(int i) throws SQLException, IOException {
        return null;
    }

    @Override
    public Tournoi recuperer(String i) throws SQLException, IOException {
        return null;
    }


    @Override
    public List<Integer> recupererId() throws SQLException, IOException {
        String sql = "select id from jeu";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(sql);
        ResultSet rs = statement.executeQuery(sql);
        List<Integer> categoryArticles = new ArrayList<>();
        while (rs.next()) {
            categoryArticles.add(rs.getInt(1));
        }
        return categoryArticles;
    }
}
