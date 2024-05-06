package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Participation;
import edu.esprit.entities.Tournoi;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationCrud implements ICrud<Participation>{
    private Connection connection;

    public ParticipationCrud() throws SQLException {
        connection= MyConnection.getInstance().getCnx();
    }

    @Override
    public int ajouter(Participation participation) throws SQLException {
        String req1="INSERT INTO participation(id_tournoi_id) VALUES ('"+participation.getId() + "')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Participation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int ajouter2(Participation participation) throws SQLException {

        return 0;
    }


    @Override
    public void modifier(Participation participation) throws SQLException {
        try {
            if (connection.isClosed()) {
                // Reopen the connection if it's closed
                connection = MyConnection.getInstance().getCnx();
            }

            String req = "UPDATE participation SET id_tournoi_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, participation.getId_tournoi_id());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }



    @Override
    public void supprimer(Participation participation) {
        String req2 = "DELETE FROM participation WHERE id_tournoi_id = '" + participation.getId_tournoi_id() + "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("Participation supprimé !");
            } else {
                System.out.println("Aucune participation trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du participation: " + e.getMessage());
        }
    }

    @Override
    public List<Participation> afficher() {
        List<Participation> participations = new ArrayList<>();
        String req3 = "SELECT * FROM participation";

        try (Connection connection = MyConnection.getInstance().getCnx();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(req3)) {

            while (resultSet.next()) {
                Participation p = new Participation();
                p.setId(resultSet.getInt("id"));
                p.setId_tournoi_id(resultSet.getInt("id_tournoi_id"));

                participations.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching participatons: " + e.getMessage());
        }

        return participations;
    }




    @Override
    public Participation getOneById(int id) throws SQLException {
        String req = "SELECT * FROM participation WHERE id="+id;
        Participation participation = null;


        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int a = rs.getInt("id");
                    int b = rs.getInt("id_tournoi_id");
                    participation = new Participation(a, b);
                }
            }
        } catch (SQLException e) {
            // Log the error or throw a custom exception
            e.printStackTrace();
        }
        return participation;
    }

    @Override
    public Jeu getOneByName(String nom) {
        return null;
    }

    @Override
    public Participation recuperer(int i) throws SQLException, IOException {
        return null;
    }

    @Override
    public Participation recuperer(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from participation where id="+val;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Participation participation = new Participation();
        if (rs.next()) {
            participation.setId(rs.getInt("id"));
            participation.setId_tournoi_id(rs.getInt("id_tournoi_id"));

        } else {
            throw new SQLException("No tournoi found with ID "+val);
        }
        return participation;
    }

    @Override
    public List<String> recupererId() throws SQLException, IOException {
        return null;
    }
    public boolean tournoiExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM tournoi WHERE id = ?";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    @Override
    public void supprimer(int id) throws SQLException {
    }

    public void supprimerT(int id) throws SQLException {
        String sql = "delete from participation where id_tournoi_id = ?";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Tournoi recupererType(String i) throws SQLException, IOException {
        return null;
    }

}