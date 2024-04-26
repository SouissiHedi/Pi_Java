package edu.esprit.services;
import edu.esprit.entities.Jeux;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface ICrud <T>{
    void ajouter(T t) throws SQLException;

    void ajouter2(T t) throws SQLException;

    T recuperer2(String i) throws SQLException, IOException;

    void modifier(T t) throws SQLException;
    void supprimer(T t) throws SQLException;



    List<T> afficher() throws SQLException;

}
