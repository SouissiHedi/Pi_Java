package edu.esprit.services;

import edu.esprit.entities.Jeux;
import edu.esprit.tools.MyConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JeuxCrud  implements ICrud<Jeux>{

    private Connection connection;

    public JeuxCrud() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Jeux jeux) throws SQLException {
        String sql = "insert into jeu (id,jeu_id,nom,genre,developpeur,image)  VALUES (?,?,?,?, ?, ?)";
        try {
            // Assurez-vous que la connexion est correctement initialisée ailleurs dans votre classe
            if (connection == null) {
                // Gérez le cas où la connexion est nulle
                System.out.println("La connexion à la base de données est nulle. Impossible d'exécuter la requête.");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, jeux.getId());
            preparedStatement.setInt(2, jeux.getJeu_id());
            preparedStatement.setString(3, jeux.getNom());
            preparedStatement.setString(4, jeux.getGenre());
            preparedStatement.setString(5, " ");
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(jeux.getImage(), null);
            ByteArrayOutputStream BI = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", BI);
            byte[] img = BI.toByteArray();
            preparedStatement.setBytes(6, img);
            System.out.println("Article ajouté !");
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void ajouter2(Jeux jeux) throws SQLException {

    }

    @Override
    public Jeux recuperer2(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from jeu where id="+val;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Jeux jeu = new Jeux();
        if (rs.next()) {
            jeu.setId(rs.getInt("id"));
            jeu.setJeu_id(rs.getInt("jeu_id"));
            jeu.setNom(rs.getString("nom"));
            jeu.setGenre(rs.getString("genre"));

            Blob blob = rs.getBlob("image");
            InputStream in = blob.getBinaryStream();
            Image finalImg = new Image(in);
            jeu.setImage(finalImg);
        } else {
            throw new SQLException("No article found with ID "+i);
        }
        return jeu;
    }

    @Override
    public void modifier(Jeux jeux) throws SQLException {
        String sql = "update jeu set jeu_id = ?, nom = ?, genre = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, jeux.getJeu_id());
        preparedStatement.setString(2, jeux.getNom());
        preparedStatement.setString(3, jeux.getGenre());
        preparedStatement.setInt(4, jeux.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(Jeux jeux) throws SQLException {
        String sql = "delete from jeu where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, jeux.getId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from jeu where id = ?";

        try {
            // Assurez-vous que la connexion est correctement initialisée ailleurs dans votre classe
            if (connection == null) {
                // Gérez le cas où la connexion est nulle
                System.out.println("La connexion à la base de données est nulle. Impossible d'exécuter la requête.");
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Jeux> afficher() throws SQLException {
        String sql = "select id, jeu_id, nom, genre from jeu";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Jeux> jeux = new ArrayList<>();
        while (rs.next()) {
            Jeux p = new Jeux();
            p.setId(rs.getInt("id"));
            p.setJeu_id(rs.getInt("jeu_id"));
            p.setNom(rs.getString("nom"));
            p.setGenre(rs.getString("genre"));
            jeux.add(p);
        }
        return jeux;
    }

    public boolean jeuExists(int jeuId) throws SQLException {
        String query = "SELECT COUNT(*) FROM jeu WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, jeuId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
