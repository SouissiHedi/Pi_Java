package controllers;

import edu.esprit.entities.Article;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.ArticleService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AfficherArticleController {
    int page =0;

    private final ArticleService ps = new ArticleService();
    @FXML
    private Label P1;

    @FXML
    private Label P2;

    @FXML
    private Label N1;

    @FXML
    private Label P3;

    @FXML
    private Label M1;

    @FXML
    private Label M2;

    @FXML
    private Label M3;

    @FXML
    private Label Aj;

    @FXML
    private ImageView Aj1;

    @FXML
    private ImageView S1;

    @FXML
    private ImageView S2;

    @FXML
    private ImageView S3;

    @FXML
    private Label S11;

    @FXML
    private Label S21;

    @FXML
    private Label S31;

    @FXML
    private StackPane sp1;

    @FXML
    private StackPane sp2;

    @FXML
    private StackPane sp3;

    @FXML
    private Label N2;

    @FXML
    private Label N3;

    @FXML
    private HBox homeNav;

    @FXML
    private ImageView right;

    @FXML
    private ImageView im1;

    @FXML
    private Label D1;

    @FXML
    private ImageView C1;

    @FXML
    private ImageView im3;

    @FXML
    private Label D2;

    @FXML
    private ImageView C2;

    @FXML
    private ImageView B1;

    @FXML
    private ImageView im2;

    @FXML
    private Label D3;

    @FXML
    private ImageView C3;

    @FXML
    private ImageView B2;

    @FXML
    private ImageView B3;

    @FXML
    private ImageView left;

    public AfficherArticleController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        left.setVisible(false);
        Label[] names = {N1,N2,N3};
        Label[] descs = {D1,D2,D3};
        Label[] prices = {P1,P2,P3};
        ImageView[] views = {im1,im2,im3};
        ImageView[] buttons = {B1,B2,B3};
        StackPane[] stak = {sp1,sp2,sp3};
        List<Article> articles = ps.recuperer();

        int c=ps.articleCount();
        int pageMax=(c-3);

        B1.setOnMouseClicked(event -> {
            try {
                modif(event, B1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        B2.setOnMouseClicked(event -> {
            try {
                modif(event, B2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        B3.setOnMouseClicked(event -> {
            try {
                modif(event, B3);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        M1.setOnMouseClicked(event -> {
            try {
                modif(event, B1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        M2.setOnMouseClicked(event -> {
            try {
                modif(event, B2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        M3.setOnMouseClicked(event -> {
            try {
                modif(event, B3);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        S1.setOnMouseClicked(event -> {
            try {
                supp(B1);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        S2.setOnMouseClicked(event -> {
            try {
                supp(B2);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        S3.setOnMouseClicked(event -> {
            try {
                supp(B3);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        S11.setOnMouseClicked(event -> {
            try {
                supp(B1);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        S21.setOnMouseClicked(event -> {
            try {
                supp(B2);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        S31.setOnMouseClicked(event -> {
            try {
                supp(B3);
                resetT(articles,names, descs,prices,views,stak);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });


        resetT(articles,names, descs,prices,views,stak);
        right.setOnMouseClicked(event -> {
            if (page<pageMax) {
                try {
                    if(page==0){left.setVisible(true);}
                    page++;
                    if(page==pageMax){right.setVisible(false);}
                    resetT(articles, names, descs, prices, views,stak);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        left.setOnMouseClicked(event -> {
            if(page!=0) {
                try {
                    if(page==pageMax){right.setVisible(true);}
                    page--;
                    if(page==0){left.setVisible(false);}
                    resetT(articles, names, descs, prices, views,stak);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    public void resetT( List<Article> articles,Label[] names,Label[] descs,Label[] prices,ImageView[] views,StackPane[] stak) throws SQLException, IOException {
        articles = ps.recuperer();
        for (int a=0;a<+3;a++) {
            names[a].setText(articles.get(page+a).getNom());
            views[a].setImage(articles.get(page+a).getImage());
            descs[a].setText(articles.get(page+a).getDescription());
            prices[a].setText(articles.get(page+a).getPrix());
            double h=(views[a].getFitHeight() - views[a].getBoundsInParent().getHeight())/2;
            double w=(views[a].getFitWidth() - views[a].getBoundsInParent().getWidth())/2;
            stak[a].setMargin(views[a],new javafx.geometry.Insets(h, 0, 0, w));
        }
    }
    public static int getIndex(ImageView[] array, ImageView targetValue) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i].getId(), targetValue.getId())) {
                return i;
            }
        }
        return -1;
    }

    void modif(MouseEvent event, ImageView b) throws SQLException {
        Label[] names = {N1,N2,N3};
        ImageView[] buttons = {B1,B2,B3};
        int index = getIndex(buttons, b);
        String name = names[index].getText();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ModifierArt.fxml"));

            // Get the controller of the loaded FXML file
            ModifArtController controller = new ModifArtController(name);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void supp(ImageView b) throws SQLException, IOException {
        Label[] names = {N1,N2,N3};
        ImageView[] buttons = {B1,B2,B3};
        int index = getIndex(buttons, b);
        String name = names[index].getText();
        int id = ps.recupererNom(String.valueOf(name)).getId();
        ps.supprimer(id);
    }

    @FXML
    public void ajouter(MouseEvent mouseEvent) {
        redirect("/AjouterArt.fxml",mouseEvent);
    }
    @FXML
    public void navhome(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navrec(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navtroph(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navtour(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navjeu(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navbout(MouseEvent mouseEvent) {
        redirect("/template.fxml",mouseEvent);
    }

    public void redirect(String s,MouseEvent mouseEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(s));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

