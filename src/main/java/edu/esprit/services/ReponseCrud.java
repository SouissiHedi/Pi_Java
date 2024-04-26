package edu.esprit.services;

import edu.esprit.entities.Reponse;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseCrud implements ICrud<Reponse>{
    private Connection connection;
    @Override
    public void ajouter(Reponse reponse) throws SQLException {

    }

    public ReponseCrud() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter2(Reponse reponse) throws SQLException {
        String req1="INSERT INTO reponse(id_rep,content) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,reponse.getId());
            pst.setString(2,reponse.getRep());
            pst.executeUpdate();
            System.out.println("reponse ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Reponse reponse) throws SQLException{
        try {
            if (connection.isClosed()) {
                // Reopen the connection if it's closed
                connection = MyConnection.getInstance().getCnx();
            }

            String req = "UPDATE reponse SET id_rep = ?, content = ?  WHERE id_rep = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, reponse.getId());
                preparedStatement.setString(2, reponse.getRep());
                ;


                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }

    @Override
    public void supprimer(Reponse reponse) {
        String req2 = "DELETE FROM reponse WHERE id_rep = '" + reponse.getId()+ "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("reponse supprimé !");
            } else {
                System.out.println("Aucune reponse trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la reclamation: " + e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Reponse> afficher() throws SQLException {
        List<Reponse> reponse = new ArrayList<>();
        String req3 ="SELECT * FROM reponse";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while(rs.next()){
                Reponse a = new Reponse();
                a.setId(rs.getInt(2));
                a.setRep(rs.getString(3));

                reponse.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponse;
    }

    @Override
    public Reponse getOneById(int id) throws SQLException {

    String req = "SELECT * FROM reponse WHERE id_rep="+id;
    Reponse t = null;


        try (Connection connection = MyConnection.getInstance().getCnx()) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int a = rs.getInt("id_rep");
                    String b = rs.getString("content");
                    t = new Reponse(a, b);
                }
            }
        }
    } catch (SQLException e) {
        // Log the error or throw a custom exception
        e.printStackTrace();
    }
        return t;
}
    public boolean repExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM reponse WHERE id_rep = ?";
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
    public Reponse recuperer(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from reponse where id_rep="+val;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Reponse t = new Reponse();
        if (rs.next()) {
            t.setId(rs.getInt("id_rec"));
            t.setRep(rs.getString("content"));

            System.out.println(t);

        } else {
            throw new SQLException("No tournoi found with ID "+val);
        }
        return t;
    }


}
