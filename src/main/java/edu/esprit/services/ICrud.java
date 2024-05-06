package edu.esprit.services;

import edu.esprit.entities.Jeu;
import edu.esprit.entities.Tournoi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public interface ICrud <T>{
    int ajouter(T t) throws SQLException;

    int ajouter2(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(T t);
    List<T> afficher() throws SQLException;

    T getOneById(int id) throws SQLException;

    Jeu getOneByName(String nom);

    T recuperer(int i) throws SQLException, IOException;

    T recuperer(String i) throws SQLException, IOException;

    List<String> recupererId() throws SQLException, IOException;

    void supprimer(int id) throws SQLException;

    Tournoi recupererType(String i) throws SQLException, IOException;
}
