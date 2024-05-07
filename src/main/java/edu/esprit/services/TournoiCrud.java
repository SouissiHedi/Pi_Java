package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Tournoi;
import edu.esprit.tools.MyConnection;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class TournoiCrud implements ICrud<Tournoi>{
    private final JeuCrud js=new JeuCrud();
    private final ParticipationCrud pc=new ParticipationCrud();
    private Connection connection;

    public TournoiCrud() throws SQLException {
        connection= MyConnection.getInstance().getCnx();
    }

    @Override
    public int ajouter(Tournoi tournoi) throws SQLException {
        if (tournoiU(tournoi.getTournoiId())){
            showAlert("Erreur","La Reference choisie est utilisée");
            return 1;
        }else {
            String req1 = "INSERT INTO tournoi(tournoi_id,type,jeu_id_id,date,count) VALUES ('" + tournoi.getTournoiId() + "','" + tournoi.getType() + "','" + tournoi.getJeuID() + "','" + tournoi.getDate() + "','" + 0 + "')";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                st.executeUpdate(req1);
                System.out.println("Tournoi ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return 0;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public int ajouter2(Tournoi tournoi) throws SQLException {
        if (tournoiU(tournoi.getTournoiId())){
            showAlert("Erreur","La Reference choisie est utilisée");
            return 1;
        }else {
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
            return 0;
        }
    }




    @Override
    public void modifier(Tournoi tournoi) throws SQLException {
        try {
            if (connection.isClosed()) {
                // Reopen the connection if it's closed
                connection = MyConnection.getInstance().getCnx();
            }

            String req = "UPDATE tournoi SET tournoi_id = ?, type = ?, count = ?, participation_id = null, jeu_id_id = ?, date = ?, review = ?, note = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                preparedStatement.setInt(1, tournoi.getTournoiId());
                preparedStatement.setString(2, tournoi.getType());
                preparedStatement.setInt(3, tournoi.getCount());
                preparedStatement.setInt(4, tournoi.getJeuID().getId());
                preparedStatement.setDate(5, tournoi.getDate());
                preparedStatement.setInt(6, tournoi.getReview());
                preparedStatement.setInt(7, tournoi.getNote());
                preparedStatement.setInt(8, tournoi.getId());

                preparedStatement.executeUpdate();
            }
            System.out.println(tournoi.getNote());
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
                t.setJeuIdInt(resultSet.getInt("jeu_id_id"));
                tournois.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tournois: " + e.getMessage());
        }
        for (Tournoi tournoi : tournois) {
            tournoi.setJeuID(js.getOneById(tournoi.getJeuIdInt()));
        }
        {

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
    public Jeu getOneByName(String nom) {
        return null;
    }

    @Override
    public Tournoi recuperer(int i) throws SQLException, IOException {
        return null;
    }@Override
    public Tournoi recuperer(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from tournoi where id="+val;
        connection= MyConnection.getInstance().getCnx();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Tournoi t = new Tournoi();
        if (rs.next()) {
            t.setTournoiId(rs.getInt("tournoi_id"));
            t.setId(rs.getInt("id"));
            t.setType(rs.getString("type"));
            t.setDate(rs.getDate("date"));
            t.setCount(rs.getInt("count"));
            t.setNote(rs.getInt("note"));
            t.setReview(rs.getInt("review"));
            Jeu a =js.getOneById(rs.getInt("jeu_id_id"));
            t.setJeuID(a);
            System.out.println(a);

        } else {
            throw new SQLException("No tournoi found with ID "+val);
        }
        return t;
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

    public int incrementCount(int id) throws SQLException, IOException {
        int c =recuperer(Integer.toString(id)).getCount();
        if(c<10){
            try {
                if (connection.isClosed()) {
                    // Reopen the connection if it's closed
                    connection = MyConnection.getInstance().getCnx();
                }

                String req = "UPDATE tournoi SET count = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                    preparedStatement.setInt(1, c+1);
                    preparedStatement.setInt(2, id);

                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                // Handle SQL exceptions appropriately
                e.printStackTrace();
                throw e; // Rethrow the exception if needed
            }
            return 1;
        }else{
            showAlert("Erreur","Tournoi Complet");
            return 0;
        }
    }
    public boolean tournoiU(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM tournoi WHERE tournoi_id = ?";
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

    public int tournoiCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM tournoi ";
        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return -1;
    }
    @Override
    public void supprimer(int id) throws SQLException {
        if (tournoiExists(id)) {
            pc.supprimerT(id);
            String sql = "delete from tournoi where id = ?";
            try (Connection connection = MyConnection.getInstance().getCnx();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }
    @Override
    public Tournoi recupererType(String i) throws SQLException, IOException {
        String sql = "select * from tournoi where type LIKE '"+i+"'";
        connection= MyConnection.getInstance().getCnx();
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
            throw new SQLException("No tournoi found with type "+i);
        }
        return t;
    }

    public void noter(int id,int currentN) throws SQLException, IOException {
        Tournoi t =recuperer(Integer.toString(id));
        int n =t.getNote();
        int nbr =t.getReview();
        int newN= (n *nbr +currentN)/(t.getReview()+1);
        t.setNote(newN);
        t.setReview(t.getReview()+1);
        modifier(t);
    }

}
