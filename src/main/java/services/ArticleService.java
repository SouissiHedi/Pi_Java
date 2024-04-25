package services;


import edu.esprit.entities.Article;
import edu.esprit.tools.MyConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService implements IService<Article> {

    private final CategotyArticleService cs = new CategotyArticleService();
    private final Connection connection;

    public ArticleService() throws SQLException {
        connection = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Article article) {
        String query = "INSERT INTO article (article_id,nom, description, prix,type_id, image) VALUES (?,?,?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, article.getIdA());
            preparedStatement.setString(2, article.getNom());
            preparedStatement.setString(3, article.getDescription());
            preparedStatement.setString(4, article.getPrix());
            preparedStatement.setInt(5, article.getType().getId());
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(article.getImage(), null);
            ByteArrayOutputStream BI = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", BI);
            byte[] img = BI.toByteArray();
            preparedStatement.setBytes(6, img);
            preparedStatement.executeUpdate();
            System.out.println("Article ajoutée !");
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Article article) throws SQLException {
        String sql = "update article set nom = ?, prix = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getNom());
        preparedStatement.setString(2, article.getPrix());
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
    public List<Article> recuperer() throws SQLException, IOException {
        String sql = "select * from article";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Article> articles = new ArrayList<>();
        while (rs.next()) {
            Article p = new Article();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getString("prix"));
            p.setDescription(rs.getString("description"));

            Blob blob = rs.getBlob("image");
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            Image finalImg = SwingFXUtils.toFXImage(image, null );
            p.setImage(finalImg);
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
            article.setNom(rs.getString("nom"));
            article.setPrix(rs.getString("prix"));
            article.setDescription(rs.getString("description"));
            article.setType(cs.recuperer1(rs.getInt("type_id")));

            Blob blob = rs.getBlob("image");
            InputStream in = blob.getBinaryStream();
            Image finalImg = new Image(in);
            article.setImage(finalImg);
        } else {
            throw new SQLException("No article found with ID "+i);
        }
        return article;
    }

    @Override
    public Article recuperer2(String i) {
        return null;
    }
}