package controllers;

import com.stripe.model.tax.Registration;
import edu.esprit.entities.Article;
import edu.esprit.entities.CategoryArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ArticleService;
import services.CategotyArticleService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AfficherBoutiqueController {
    private final ArticleService ps = new ArticleService();
    private final CategotyArticleService cs = new CategotyArticleService();
    int page =0;
    int c=ps.articleCount();
    int pageMax=(c-3);
    Article test =new Article();

    public Article getTest() {
        return test;
    }

    public void setTest(Article test) {
        this.test = test;
    }


    @FXML
    private Label P1;

    @FXML
    private Label P2;

    @FXML
    private Label N1;

    @FXML
    private StackPane pan;

    @FXML
    private Label P3;

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
    private StackPane sp1;

    @FXML
    private StackPane sp2;

    @FXML
    private StackPane sp3;

    @FXML
    private ImageView left;

    @FXML
    private ChoiceBox<String> triCB;

    @FXML
    private ImageView tri;

    @FXML
    private ImageView search;

    List<Article> articles ;

    Label[] names = {N1,N2,N3};
    Label[] descs = {D1,D2,D3};
    Label[] prices = {P1,P2,P3};
    ImageView[] views = {im1,im2,im3};
    StackPane[] stak = {sp1,sp2,sp3};

    public AfficherBoutiqueController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        triCB.getItems().setAll("-Défaut-","Nom","Description","Prix");
        triCB.setValue("-Défaut-");
        if(pageMax==0){
            right.setVisible(false);
        }

        left.setVisible(false);
        Label[] names = {N1,N2,N3};
        Label[] descs = {D1,D2,D3};
        Label[] prices = {P1,P2,P3};
        ImageView[] views = {im1,im2,im3};
        StackPane[] stak = {sp1,sp2,sp3};
        ImageView[] buttons = {B1,B2,B3};
        this.articles = ps.recuperer();



        B1.setOnMouseClicked(event -> {
            try {
                achat(event, B1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        B2.setOnMouseClicked(event -> {
            try {
                achat(event, B2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        B3.setOnMouseClicked(event -> {
            try {
                achat(event, B3);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        P1.setOnMouseClicked(event -> {
            try {
                achat(event, B1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        P2.setOnMouseClicked(event -> {
            try {
                achat(event, B2);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        P3.setOnMouseClicked(event -> {
            try {
                achat(event, B3);
            } catch (SQLException e) {
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

        tri.setOnMouseClicked(mouseEvent -> {
            if (triCB.isVisible()){
                trier(articles,triCB.getValue());
                try {
                    resetT(articles,names, descs,prices,views,stak);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            switchVis(triCB);
        });

        search.setOnMouseClicked(mouseEvent -> {
            test.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    try {
                        articles=ps.recuperer(emp(test.getNom()),emp(test.getDescription()),emp(test.getPrix()),emp2(test.getUrl()));
                        System.out.println(test);
                        System.out.println(articles);
                        trier(articles,triCB.getValue());
                        resetT(articles, names, descs, prices, views, stak);
                        page=0;
                        left.setVisible(false);
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/recherche.fxml"));
                Stage rechStage = new Stage();
                RechercheController controller = new RechercheController(rechStage,this);
                loader.setController(controller);
                Parent rootR = loader.load();
                // Create the rectangular clip to round the corners
                javafx.scene.shape.Rectangle rect = new Rectangle(1024, 768);
                rect.setArcHeight(60.0);
                rect.setArcWidth(60.0);

                // Create the scene
                Scene Rscene = new Scene(rootR, 700, 250);
                Rscene.setFill(Color.TRANSPARENT);
                rechStage.setTitle("Fenetre de Recherche");
                rechStage.setScene(Rscene);
                rechStage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        pan.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
                Stage panStage = new Stage();
                List<Article> la = new ArrayList<Article>();
                PanierController controller = new PanierController(panStage,la);
                loader.setController(controller);
                Parent root2 = loader.load();
                // Create the rectangular clip to round the corners
                javafx.scene.shape.Rectangle rect = new Rectangle(1024, 768);
                rect.setArcHeight(60.0);
                rect.setArcWidth(60.0);

                // Create the scene
                Scene scene2 = new Scene(root2, 350, 500);
                panStage.initStyle(StageStyle.TRANSPARENT);
                scene2.setFill(Color.TRANSPARENT);
                panStage.setTitle("Panier");
                panStage.setScene(scene2);
                panStage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private static int compareIgnoreCase(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int minLength = Math.min(len1, len2);

        for (int i = 0; i < minLength; i++) {
            char c1 = Character.toLowerCase(str1.charAt(i));
            char c2 = Character.toLowerCase(str2.charAt(i));
            if (c1 != c2) {
                return c1 - c2;
            }
        }

        return len1 - len2;
    }

    public void trier(List<Article> articles,String value) {
        switch (value) {
            case "-Défaut-":
                articles.sort(Comparator.comparing(Article::getId));
                break;
            case "Nom":
                articles.sort(Comparator.comparing(Article::getCaseNom));
                break;
            case "Description":
                articles.sort(Comparator.comparing(Article::getCaseDescription));
                break;
            case "Prix":
                articles.sort(Comparator.comparing(Article::getIntPrix));
                break;
        }
    }

    public void resetT(List<Article> articles, Label[] names, Label[] descs, Label[] prices, ImageView[] views, StackPane[] stak) throws SQLException, IOException {
        ImageView[] cards = {C1,C2,C3};
        ImageView[] buttons = {B1,B2,B3};
        int max = articles.size();
        pageMax=max-3;
        right.setVisible(pageMax > 0);
        for (int a=0;a<+3;a++) {
            if(page+a>=max){
                names[a].setVisible(false);
                views[a].setVisible(false);
                descs[a].setVisible(false);
                prices[a].setVisible(false);
                stak[a].setVisible(false);
                cards[a].setVisible(false);
                buttons[a].setVisible(false);
            }else {
                names[a].setVisible(true);
                views[a].setVisible(true);
                descs[a].setVisible(true);
                prices[a].setVisible(true);
                stak[a].setVisible(true);
                cards[a].setVisible(true);
                buttons[a].setVisible(true);
                names[a].setText(articles.get(page + a).getNom());
                views[a].setImage(articles.get(page + a).getImage());
                descs[a].setText(articles.get(page + a).getDescription());
                prices[a].setText(articles.get(page + a).getPrix());
                double h = (views[a].getFitHeight() - views[a].getBoundsInParent().getHeight()) / 2;
                double w = (views[a].getFitWidth() - views[a].getBoundsInParent().getWidth()) / 2;
                stak[a].setMargin(views[a], new javafx.geometry.Insets(h, 0, 0, w));
                right.setVisible(pageMax !=page);
            }
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

    void switchVis(ChoiceBox<String> ch) {
        ch.setVisible(!ch.isVisible());
    }

    public String emp(String k) {
        if(k==null){
            return "";
        }
        return k;
    }

    public String emp2(String k) {
        if(k==null){
            return "-1";
        }
        return k;
    }


    void achat(MouseEvent event, ImageView b) throws SQLException {
        Label[] names = {N1,N2,N3};
        ImageView[] buttons = {B1,B2,B3};
        int index = getIndex(buttons, b);
        String name = names[index].getText();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherArticle.fxml"));

            // Get the controller of the loaded FXML file
            ArticleController controller = new ArticleController(name);
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

