package edu.esprit.services;

import edu.esprit.entities.JeuPartie;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jeu_partieCrud implements ICrud<JeuPartie>{

        private Connection connection;

        public void Jeu_partieService() throws SQLException {
            connection = MyConnection.getInstance().getCnx();
        }

        @Override
        public void ajouter(JeuPartie jeu_partie) throws SQLException {
            String sql = "insert into jeu_partie (id,jeu_id_id,score,joueur_id,partie_type)  VALUES (?,?,?, ?, ?)";
            try {
                // Assurez-vous que la connexion est correctement initialisée ailleurs dans votre classe
                if (connection == null) {
                    // Gérez le cas où la connexion est nulle
                    System.out.println("La connexion à la base de données est nulle. Impossible d'exécuter la requête.");
                    return;
                }

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, jeu_partie.getId());
                preparedStatement.setInt(2, jeu_partie.getJeu_id());
                preparedStatement.setInt(3, jeu_partie.getScore());
                preparedStatement.setInt(4, jeu_partie.getJoueur_id());
                preparedStatement.setString(5, jeu_partie.getPartie_type());
                System.out.println("Article ajouté !");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }



    @Override
    public void ajouter2(JeuPartie jeuPartie) throws SQLException {

    }

        @Override
        public JeuPartie recuperer2(String i) throws SQLException, IOException {
            int val=Integer.parseInt(i);
            String sql = "select * from jeu where id="+val;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            JeuPartie jeu = new JeuPartie();
            if (rs.next()) {
                jeu.setId(rs.getInt("id"));
                jeu.setJeu_id(rs.getInt("jeu_id"));
                jeu.setScore(rs.getInt("score"));
                jeu.setJoueur_id(rs.getInt("joueur_id"));
                jeu.setPartie_type(rs.getString("partie_type"));

            } else {
                throw new SQLException("No article found with ID "+i);
            }
            return jeu;
        }


    @Override
    public void modifier(JeuPartie jeuPartie) throws SQLException {
            String sql = "update jeu set jeu_id = ?, nom = ?, genre = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, jeuPartie.getJeu_id());
            preparedStatement.setInt(2, jeuPartie.getScore());
            preparedStatement.setInt(2, jeuPartie.getJoueur_id());
            preparedStatement.setString(3, jeuPartie.getPartie_type());
            preparedStatement.setInt(4, jeuPartie.getId());
            preparedStatement.executeUpdate();
        }

        @Override
        public void supprimer(JeuPartie jeuPartie) throws SQLException {
            String sql = "delete from jeu where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, jeuPartie.getId());
            preparedStatement.executeUpdate();
        }




        @Override
        public List<JeuPartie> afficher() throws SQLException {
            String sql = "select id, jeu_id, nom, genre from jeu";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<JeuPartie> jeuPartie = new ArrayList<>();
            while (rs.next()) {
                JeuPartie p = new JeuPartie();
                p.setId(rs.getInt("id"));
                p.setJeu_id(rs.getInt("jeu_id"));
                p.setScore(rs.getInt("score"));
                p.setJoueur_id(rs.getInt("joueur_id"));
                p.setPartie_type(rs.getString("partie_type"));
                jeuPartie.add(p);
            }
            return jeuPartie;
        }

        public boolean jeuPartieExists(int jeuId) throws SQLException {
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
