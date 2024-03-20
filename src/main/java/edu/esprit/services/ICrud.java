package edu.esprit.services;
import java.sql.SQLException;
import java.util.List;
public interface ICrud <T>{
    void ajouter(T t) throws SQLException;

    void ajouter2(T t) throws SQLException;
    void modifier(T t);
    void supprimer(T t);
    List<T> afficher() throws SQLException;

}
