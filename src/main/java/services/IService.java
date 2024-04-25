package services;

import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException, IOException;

    List<String> recupererId() throws SQLException, IOException;

    T recuperer1(int i) throws SQLException, IOException;

    T recuperer2(String i) throws SQLException, IOException;
}