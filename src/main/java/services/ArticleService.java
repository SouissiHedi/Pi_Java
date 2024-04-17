package services;


import edu.esprit.entities.Article;
import edu.esprit.services.ArticleCrud;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IService<Article> {

    private Connection connection;

    public ArticleService() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Article article) throws SQLException {
        String sql = "insert into article (nom, prix) " +
                "values('" + article.getNom() + "','" +  article.getPrix() + ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Article article) throws SQLException {
        String sql = "update article set nom = ?, prix = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getNom());
        preparedStatement.setInt(2, article.getPrix());
        preparedStatement.setInt(3, article.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from article where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Article> recuperer() throws SQLException {
        String sql = "select * from article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article p = new Article();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getInt("prix"));

            articles.add(p);
        }
        return articles;
    }
}