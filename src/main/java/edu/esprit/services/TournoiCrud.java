package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Tournoi;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournoiCrud implements ICrud<Tournoi>{
    private final JeuCrud js=new JeuCrud();
    private Connection connection;

    public TournoiCrud() throws SQLException {
        connection= MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Tournoi tournoi) throws SQLException {
        String req1="INSERT INTO tournoi(tournoi_id,type,jeu_id_id,date,count) VALUES ('"+tournoi.getTournoiId() + "','"+tournoi.getType()+"','"+tournoi.getJeuID()+ "','"+tournoi.getDate()+ "','"+0+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Tournoi ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter2(Tournoi tournoi) {
        String req1 = "INSERT INTO tournoi(tournoi_id,type,jeu_id_id,date,count) VALUES (?,?,?,?,?)";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement pst = connection.prepareStatement(req1)) {
            // Check if the connection is closed before proceeding
            if (connection.isClosed()) {
                System.out.println("Connection is closed. Reopening...");
                MyConnection.getInstance().reconnect(); // Example method to reconnect
            }
            pst.setInt(1, tournoi.getTournoiId());
            pst.setString(2, tournoi.getType());
            pst.setInt(3, tournoi.getJeuID().getId());
            pst.setDate(4, tournoi.getDate());
            pst.setInt(5, 0);
            pst.executeUpdate();
            System.out.println("Tournoi ajoutée !");
        } catch (SQLException e) {
            System.out.println("Error while adding tournoi: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @Override
    public void modifier(Tournoi tournoi) throws SQLException {
        try {
            if (connection.isClosed()) {
                // Reopen the connection if it's closed
                connection = MyConnection.getInstance().getCnx();
            }

            String req = "UPDATE tournoi SET tournoi_id = ?, type = ?, count = ?, participation_id = null, jeu_id_id = ?, date = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, tournoi.getTournoiId());
                preparedStatement.setString(2, tournoi.getType());
                preparedStatement.setInt(3, tournoi.getCount());
                preparedStatement.setInt(4, tournoi.getJeuID().getId());
                preparedStatement.setDate(5, tournoi.getDate());
                preparedStatement.setInt(6, tournoi.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }



    @Override
    public void supprimer(Tournoi tournoi) {
        String req2 = "DELETE FROM tournoi WHERE TournoiId = '" + tournoi.getTournoiId() + "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("Tournoi supprimé !");
            } else {
                System.out.println("Aucun tournoi trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du tournoi: " + e.getMessage());
        }
    }

    @Override
    public List<Tournoi> afficher() {
        List<Tournoi> tournois = new ArrayList<>();
        String req3 = "SELECT * FROM tournoi";

        try (Connection connection = MyConnection.getInstance().getCnx();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(req3)) {

            while (resultSet.next()) {
                Tournoi t = new Tournoi();
                t.setTournoiId(resultSet.getInt("id"));
                t.setType(resultSet.getString("type"));
                t.setDate(resultSet.getDate("date"));
                t.setCount(resultSet.getInt("count"));
                t.setJeuID(js.getOneById(resultSet.getInt("jeu_id_id")));
                tournois.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tournois: " + e.getMessage());
        }

        return tournois;
    }




    @Override
    public Tournoi getOneById(int id) throws SQLException {
        String req = "SELECT * FROM tournoi WHERE id="+id;
        Tournoi t = null;


        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int a = rs.getInt("id");
                    String b = rs.getString("type");
                    Date c = rs.getDate("date");
                    Jeu d = js.getOneById(rs.getInt("jeu_id_id"));
                    int e = rs.getInt("count");
                    t = new Tournoi(a, b, c, d, e);
                }
            }
        } catch (SQLException e) {
            // Log the error or throw a custom exception
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public Tournoi recuperer(int i) throws SQLException, IOException {
        return null;
    }

    @Override
    public Tournoi recuperer(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from tournoi where id="+val;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Tournoi t = new Tournoi();
        if (rs.next()) {
            t.setId(rs.getInt("id"));
            t.setType(rs.getString("type"));
            t.setDate(rs.getDate("date"));
            t.setCount(rs.getInt("count"));
            Jeu a =js.getOneById(rs.getInt("jeu_id_id"));
            t.setJeuID(a);
            System.out.println(a);

        } else {
            throw new SQLException("No tournoi found with ID "+val);
        }
        return t;
    }

    @Override
    public List<Integer> recupererId() throws SQLException, IOException {
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

}
