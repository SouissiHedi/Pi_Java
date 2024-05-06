package services;


import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;
import edu.esprit.tools.MyConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategotyArticleService implements IService<CategoryArticle> {

    private Connection connection;

    public CategotyArticleService() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public int ajouter(CategoryArticle categoryArticle) throws SQLException {
        String query = "INSERT INTO category_article (nom_cat) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryArticle.getNomCat());
            preparedStatement.executeUpdate();
            System.out.println("Catégorie d'articles ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    @Override
    public void modifier(CategoryArticle categoryArticle) throws SQLException {
        String sql = "update category_article set nom_cat = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, categoryArticle.getNomCat());
        preparedStatement.setInt(2, categoryArticle.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from category_article where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<CategoryArticle> recuperer() throws SQLException, IOException {
        String sql = "select * from category_article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<CategoryArticle> categoryArticles = new ArrayList<>();
        while (rs.next()) {
            CategoryArticle p = new CategoryArticle();
            p.setId(rs.getInt(1));
            p.setNomCat(rs.getString(2));
            categoryArticles.add(p);
        }
        return categoryArticles;
    }

    @Override
    public List<String> recupererId() throws SQLException, IOException {
        String sql = "select nom_cat from category_article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<String> categoryArticles = new ArrayList<>();
        while (rs.next()) {
            categoryArticles.add(rs.getString(1));
        }
        return categoryArticles;
    }

    @Override
    public CategoryArticle recuperer1(int i) throws SQLException, IOException {
        String sql = "select * from category_article where id="+i;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        CategoryArticle categoryArticle = new CategoryArticle();
        if (rs.next()) {
            categoryArticle.setId(rs.getInt(1));
            categoryArticle.setNomCat(rs.getString(2));

        } else {
            throw new SQLException("No category of articles found with ID "+i);
        }
        return categoryArticle;
    }

    @Override
    public CategoryArticle recuperer2(String i) throws SQLException, IOException {
        String sql = "select * from category_article where nom_cat=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, i);
        ResultSet rs = preparedStatement.executeQuery();
        CategoryArticle categoryArticle = new CategoryArticle();
        if (rs.next()) {
            categoryArticle.setId(rs.getInt(1));
            categoryArticle.setNomCat(rs.getString(2));

        } else {
            throw new SQLException("No category of articles found with ID "+i);
        }
        return categoryArticle;
    }

    @Override
    public Article recupererNom(String n) throws SQLException, IOException {
        return null;
    }
}