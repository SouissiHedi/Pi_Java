package services;


import edu.esprit.entities.Article;
import edu.esprit.tools.MyConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleService implements IService<Article> {

    private final CategotyArticleService cs = new CategotyArticleService();
    private final Connection connection;

    public ArticleService() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @Override
    public int ajouter(Article article) throws SQLException {
        String query = "INSERT INTO article (article_id,nom, description, prix,type_id, image) VALUES (?,?,?, ?, ?, ?)";
        if (this.articleUnique(article.getIdA())){
            showAlert("Erreur", "La réference choisie est déja utilisée.");
            return 1;
        }else {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, article.getIdA());
                preparedStatement.setString(2, article.getNom());
                preparedStatement.setString(3, article.getDescription());
                preparedStatement.setString(4, article.getPrix());
                preparedStatement.setInt(5, article.getType().getId());
                String img;
                String url = article.getUrl();
                url = replaS(url);

                String local = url.substring(0, url.lastIndexOf('/') + 1);
                String lastPart = url.substring(url.lastIndexOf("/") + 1);
                if (!local.equals("http://localhost/images/")) {
                    File file = new File("C:/wamp64/www/images/", lastPart);
                    if (!(file.exists() && file.isFile())) {
                        try {
                            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(article.getImage(), null);
                            ImageIO.write(bufferedImage, "png", file);
                        } catch (IOException e) {
                            System.err.println("Error saving image: " + e.getMessage());
                        }
                    }
                }
                img = lastPart;
                preparedStatement.setString(6, img);
                preparedStatement.executeUpdate();
                System.out.println("Article ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return 0;
        }
    }
    public static String replaS(String i) {
        return i.replace("\\", "/");
    }
    @Override
    public void modifier(Article article) throws SQLException, IOException {
        String sql = "update article set article_id = ?,nom = ?, description = ?, prix = ?,type_id = ?, image = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, article.getIdA());
        preparedStatement.setString(2, article.getNom());
        preparedStatement.setString(3, article.getDescription());
        preparedStatement.setString(4, article.getPrix());
        preparedStatement.setInt(5, article.getType().getId());
        String img ;
        String url = article.getUrl();
        url= replaS(url);

        String local = url.substring(0,url.lastIndexOf('/')+1 );
        String lastPart = url.substring(url.lastIndexOf("/") + 1);
        if (!local.equals("http://localhost/images/")){
            File file = new File("C:/wamp64/www/images/", lastPart);
            if (!(file.exists() && file.isFile())) {
                try {
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(article.getImage(), null);
                    ImageIO.write(bufferedImage, "png", file);
                } catch (IOException e) {
                    System.err.println("Error saving image: " + e.getMessage());
                }
            }
        }
        img=lastPart;
        preparedStatement.setString(6, img);
        preparedStatement.setInt(7, article.getId());
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
    public List<Article> recuperer() throws SQLException, IOException {
        String sql = "SELECT * FROM article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article p = new Article();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getString("prix"));
            p.setDescription(rs.getString("description"));
            p.setType(cs.recuperer1(rs.getInt("type_id")));


            String imageUrl = rs.getString("image");
            imageUrl = "http://localhost/images/" + imageUrl;
            try {
                Image finalImg = new Image(new URL(imageUrl).toString());
                p.setImage(finalImg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            articles.add(p);
        }
        return articles;
    }

    @Override
    public List<Article> recuperer(String nom, String desc, String prix,String type) throws SQLException, IOException {
        String[] typeIds = type.split(",");
        String sql = "SELECT * FROM article WHERE nom LIKE ? AND description LIKE ? AND prix LIKE ? AND type_id IN (" +
                String.join(",", Collections.nCopies(typeIds.length, "?")) + ")";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + nom + "%");
        statement.setString(2, "%" + desc + "%");
        statement.setString(3, "%" + prix + "%");

        // Set the type IDs as parameters for the IN clause
        for (int i = 0; i < typeIds.length; i++) {
            statement.setString(4 + i, typeIds[i]);
        }
        ResultSet rs = statement.executeQuery();
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article p = new Article();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getString("prix"));
            p.setDescription(rs.getString("description"));
            p.setType(cs.recuperer1(rs.getInt("type_id")));


            String imageUrl = rs.getString("image");
            imageUrl = "http://localhost/images/" + imageUrl;
            try {
                Image finalImg = new Image(new URL(imageUrl).toString());
                p.setImage(finalImg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            articles.add(p);
        }
        return articles;
    }

    @Override
    public List<String> recupererId() {
        return null;
    }

    @Override
    public Article recuperer1(int i) throws SQLException, IOException {
        String sql = "select * from article where article_id="+i;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Article article = new Article();
        if (rs.next()) {
            article.setId(rs.getInt("id"));
            article.setIdA(rs.getInt("article_id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getString("prix"));
            article.setDescription(rs.getString("description"));
            article.setType(cs.recuperer1(rs.getInt("type_id")));

            String imageUrl = rs.getString("image");
            imageUrl = "http://localhost/images/" + imageUrl;
            try {
                // Create an Image object from the URL
                Image finalImg = new Image(new URL(imageUrl).toString());
                article.setImage(finalImg);
            } catch (MalformedURLException e) {
                // Handle malformed URL exception
                e.printStackTrace();
            }
        } else {
            throw new SQLException("No article found with ID "+i);
        }
        return article;
    }

    @Override
    public Article recuperer2(String i) throws SQLException, IOException {
        int val=Integer.parseInt(i);
        String sql = "select * from article where id="+val;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Article article = new Article();
        if (rs.next()) {
            article.setId(rs.getInt("id"));
            article.setIdA(rs.getInt("article_id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getString("prix"));
            article.setDescription(rs.getString("description"));
            article.setType(cs.recuperer1(rs.getInt("type_id")));

            String imageUrl = rs.getString("image");
            imageUrl = "http://localhost/images/" + imageUrl;
            try {
                // Create an Image object from the URL
                Image finalImg = new Image(new URL(imageUrl).toString());
                article.setImage(finalImg);
            } catch (MalformedURLException e) {
                // Handle malformed URL exception
                e.printStackTrace();
            }
        } else {
            throw new SQLException("No article found with ID "+i);
        }
        return article;
    }

    @Override
    public Article recupererNom(String n) throws SQLException, IOException {
        String sql = "select * from article where nom LIKE '" + n + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Article article = new Article();
        if (rs.next()) {
            article.setId(rs.getInt("id"));
            article.setIdA(rs.getInt("article_id"));
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getString("prix"));
            article.setDescription(rs.getString("description"));
            article.setType(cs.recuperer1(rs.getInt("type_id")));

            String imageUrl = rs.getString("image");
            imageUrl = "http://localhost/images/" + imageUrl;
            try {
                // Create an Image object from the URL
                Image finalImg = new Image(new URL(imageUrl).toString());
                article.setImage(finalImg);
            } catch (MalformedURLException e) {
                // Handle malformed URL exception
                e.printStackTrace();
            }
        } else {
            throw new SQLException("No article found with name "+n);
        }
        return article;
    }


    public boolean articleExists(int articleId) throws SQLException {
        String query = "SELECT COUNT(*) FROM article WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public boolean articleUnique(int articleId) throws SQLException {
        String query = "SELECT COUNT(*) FROM article WHERE article_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    public int articleCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM article";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return -1;
    }

}