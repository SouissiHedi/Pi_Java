package services;

import edu.esprit.entities.Article;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    int ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException, IOException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException, IOException;

    List<Article> recuperer(String nom, String desc, String prix,String type) throws SQLException, IOException;

    List<String> recupererId() throws SQLException, IOException;

    T recuperer1(int i) throws SQLException, IOException;

    T recuperer2(String i) throws SQLException, IOException;

    Article recupererNom(String n) throws SQLException, IOException;
}