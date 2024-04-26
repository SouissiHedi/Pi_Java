package edu.esprit.services;

import edu.esprit.entities.Jeux;
import edu.esprit.entities.User;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class UserCrud implements ICrud<User>{
    @Override
    public void ajouter(User user) throws SQLException {
        String req1="INSERT INTO user(email, roles, password, image) VALUES ('"+user.getEmail()+"','"+user.getRoles()+"','"+user.getPassword()+"','"+user.getImage()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("User ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter2(User user) throws SQLException {
        String req1="INSERT INTO user(email, roles, password, image) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1,user.getEmail());
            pst.setString(2,user.getRoles());
            pst.setString(3,user.getPassword());
            pst.setBlob(4,user.getImage());
            pst.executeUpdate();
            System.out.println("User ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User recuperer2(String i) throws SQLException, IOException {
        return null;
    }

    @Override
    public void modifier(User user) {
        String req = "UPDATE user SET email = '" + user.getEmail() + "', roles = '" + user.getRoles() + "', password = '" + user.getPassword() + "', image = '" + user.getImage() + "' WHERE id = '" + user.getId()+"'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req);
            if (rowsAffected > 0) {
                System.out.println("User mise à jour !");
            } else {
                System.out.println("Aucun user trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'user: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(User user) {
        String req2 = "DELETE FROM user WHERE id = '" + user.getId() + "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("User supprimé !");
            } else {
                System.out.println("Aucun user trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
        }
    }



    @Override
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String req3 ="SELECT * FROM user";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while(rs.next()){
                User a = new User();
                a.setId(rs.getInt(1));
                a.setEmail(rs.getString(2));
                a.setRoles(rs.getString(3));
                a.setPassword(rs.getString(4));
                a.setImage(rs.getBlob(5));
                users.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
