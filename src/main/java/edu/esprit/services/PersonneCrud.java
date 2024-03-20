package edu.esprit.services;

import edu.esprit.entities.Personne;
import edu.esprit.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PersonneCrud implements ICrud<Personne>{
    @Override
    public void ajouter(Personne personne) throws SQLException {
        String req1="INSERT INTO personne(nom,prenom) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req1);
            System.out.println("Personne ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter2(Personne personne) throws SQLException {
        String req1="INSERT INTO personne(nom,prenom) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1,personne.getNom());
            pst.setString(2,personne.getPrenom());
            pst.executeUpdate();
            System.out.println("Personne ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Personne personne) {
        String nom = personne.getNom();
        String prenom = personne.getPrenom();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Entrer le nom:");
            nom = reader.readLine();

            System.out.println("Entrer le prenom:");
            prenom = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for ID and Age.");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Error closing BufferedReader: " + e.getMessage());
            }
        }
        String req = "UPDATE personne SET nom = '" + nom + "', prenom = '" + prenom + "' WHERE nom = '" + personne.getNom()+"'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req);
            if (rowsAffected > 0) {
                System.out.println("Personne mise à jour !");
            } else {
                System.out.println("Aucune personne trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la personne: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Personne personne) {
        String req2 = "DELETE FROM personne WHERE nom = '" + personne.getNom() + "'";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            int rowsAffected = st.executeUpdate(req2);
            if (rowsAffected > 0) {
                System.out.println("Personne supprimée !");
            } else {
                System.out.println("Aucune personne trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la personne: " + e.getMessage());
        }
    }

    @Override
    public List<Personne> afficher() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        String req3 ="SELECT * FROM personne";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while(rs.next()){
                Personne p = new Personne();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString(3));
                personnes.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personnes;
    }
}
