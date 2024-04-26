package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationCrud implements ICrud<Reclamation> {


    private Connection connection ;

    public ReclamationCrud() throws SQLException {
        this.connection = MyConnection.getCnx();
    }

    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {

    }

    @Override
    public void ajouter2(Reclamation reclamation) throws SQLException {
        String req1 = "INSERT INTO reclamation(id_rec,description,type) VALUES (?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getCnx().prepareStatement(req1);
            pst.setInt(1, reclamation.getId());
            pst.setString(2, reclamation.getDescription());
            pst.setString(3, reclamation.getType());
            pst.executeUpdate();
            System.out.println("reclamation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        try {
            if (connection.isClosed()) {
                // Reopen the connection if it's closed
                connection = MyConnection.getCnx();
            }
            System.out.println("tihqyyq");
            String req = "UPDATE reclamation SET id_rec = ?, type = ?, description = ? WHERE id_rec = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, reclamation.getId());
                preparedStatement.setString(2, reclamation.getType());
                preparedStatement.setString(3, reclamation.getDescription());
                preparedStatement.setInt(4, reclamation.getId());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }

    @Override
    public void supprimer(Reclamation reclamation) {

        Connection connection = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            if (connection == null) {
                throw new SQLException("Connection is not initialized.");
            }
            System.out.println("rqni");
            String req2 = "DELETE FROM reclamation WHERE id_rec = ?";
            try (PreparedStatement pst = connection.prepareStatement(req2)) {
                pst.setInt(1, reclamation.getId());
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reclamation supprimée !");
                } else {
                    System.out.println("Aucune réclamation trouvée avec cet ID.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la réclamation: " + e.getMessage());
            }
        } catch (SQLException e) {
            // Handle SQLException
            System.out.println("Erreur lors de la récupération de la connexion: " + e.getMessage());
        } finally {
            // Close the connection if it's not null
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
                }
            }
        }
    }


    @Override
    public void supprimer(int idR) throws SQLException {
        Connection connection = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            if (connection == null) {
                throw new SQLException("Connection is not initialized.");
            }
            System.out.println("rqni");
            String req2 = "DELETE FROM reclamation WHERE id_rec = ?";
            try (PreparedStatement pst = connection.prepareStatement(req2)) {
                pst.setInt(1, idR);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reclamation supprimée !");
                } else {
                    System.out.println("Aucune réclamation trouvée avec cet ID.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression de la réclamation: " + e.getMessage());
            }
        } catch (SQLException e) {
            // Handle SQLException
            System.out.println("Erreur lors de la récupération de la connexion: " + e.getMessage());
        } finally {
            // Close the connection if it's not null
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<Reclamation> afficher() throws SQLException {
        List<Reclamation> reclamation = new ArrayList<>();
        String req3 = "SELECT * FROM article";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while (rs.next()) {
                Reclamation a = new Reclamation();
                a.setId(rs.getInt(1));
                a.setDescription(rs.getString("nom"));
                a.setType(rs.getString(3));
                reclamation.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamation;
    }

    @Override
    public Reclamation getOneById(int id) throws SQLException {
        String req = "SELECT * FROM reclamation WHERE id_rec=" + id;
        Reclamation t = null;


        try (Connection connection = MyConnection.getInstance().getCnx()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        int a = rs.getInt("id");
                        String b = rs.getString("type");
                        String e = rs.getString("description");
                        t = new Reclamation(a, b, e);
                    }
                }
            }
        } catch (SQLException e) {
            // Log the error or throw a custom exception
            e.printStackTrace();
        }
        return t;
    }


    public boolean recExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM reclamation WHERE id_rec = ?";
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

    public Reclamation recuperer(String i) throws SQLException, IOException {
        try (Connection connection = MyConnection.getInstance().getCnx()) {
            int val = Integer.parseInt(i);
            if (connection == null) {
                // Handle the case where the connection is not initialized
                throw new SQLException("Connection is not initialized.");
            }

            String sql = "select * from reclamation where id_rec=" + val;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            Reclamation t = new Reclamation();
            if (rs.next()) {
                t.setId(rs.getInt(2));
                t.setType(rs.getString(4));
                t.setDescription(rs.getString(3));
            } else {
                throw new SQLException("No reclamation found with ID " + val);
            }
            return t;
        } catch (SQLException e) {
            // Attempt to reconnect and try again
            MyConnection.reconnect();
            // Retry the operation recursively
            return recuperer(i);
        }
    }

}

