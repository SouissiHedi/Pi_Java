package controllers;

import edu.esprit.entities.Tournoi;
import edu.esprit.services.JeuCrud;
import edu.esprit.services.TournoiCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GererTournoi {
    private final JeuCrud js=new JeuCrud();
    private final TournoiCrud ps = new TournoiCrud();
    public int page =0;
    @FXML
    private Button mod5;

    @FXML
    private Button mod4;

    @FXML
    private Button mod3;

    @FXML
    private Label n1;

    @FXML
    private Button mod2;

    @FXML
    private Button supp1;

    @FXML
    private Label n2;

    @FXML
    private Label n3;

    @FXML
    private Label n4;

    @FXML
    private Label n5;

    @FXML
    private Button mod6;

    @FXML
    private Label n6;

    @FXML
    private Text title;

    @FXML
    private Button tog;

    @FXML
    private Button togL;

    @FXML
    private Button supp5;

    @FXML
    private Button supp4;

    @FXML
    private Button supp3;

    @FXML
    private Button supp2;

    @FXML
    private Button mod1;

    @FXML
    private Button supp6;

    @FXML
    private HBox homeNav;

    @FXML
    private ImageView i1;

    @FXML
    private ImageView i2;

    @FXML
    private ImageView i3;

    @FXML
    private ImageView i4;

    @FXML
    private ImageView i5;

    @FXML
    private ImageView i6;

    public GererTournoi() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Label[] labels = {n1,n2,n3,n4,n5,n6};
        ImageView[] views = {i1,i2,i3,i4,i5,i6};
        Button[] buttons = {supp1,supp2,supp3,supp4,supp5,supp6};
        Button[] button = {mod1,mod2,mod3,mod4,mod5,mod6};

        List<Tournoi> tournois = ps.afficher();
        int c=ps.tournoiCount();
        AtomicInteger aff= new AtomicInteger();
        int pageMax=((int)c/6);

        supp1.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp1);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        supp2.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp2);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        supp3.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp3);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        supp4.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp4);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        supp5.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp5);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        supp6.setOnMouseClicked(event -> {
            try {
                supprimer(event, supp6);
                resetT(tournois,labels,views,aff);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod1.setOnMouseClicked(event -> {
            try {
                modifier(event, mod1);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod2.setOnMouseClicked(event -> {
            try {
                modifier(event, mod2);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod3.setOnMouseClicked(event -> {
            try {
                modifier(event, mod3);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod4.setOnMouseClicked(event -> {
            try {
                modifier(event, mod4);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod5.setOnMouseClicked(event -> {
            try {
                modifier(event, mod5);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        mod6.setOnMouseClicked(event -> {
            try {
                modifier(event, mod6);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });


        resetT(tournois,labels,views,aff);
        tog.setOnMouseClicked(event -> {
            try {
                page++;
                if (page == pageMax){
                    tog.setVisible(false);
                    aff.set(6-(c % 6));
                }
                resetT(tournois,labels,views,aff);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        togL.setOnMouseClicked(event -> {
            try {
                page--;
                if (page == 0){
                    togL.setVisible(false);
                }
                resetT(tournois,labels,views,aff);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
    public void resetT(List<Tournoi> tournois, Label[] labels, ImageView[] views, AtomicInteger c) throws SQLException, IOException {
        tournois = ps.afficher();
        for (int a=page*6;a<(page+1)*6-c.get();a++) {
            labels[a%6].setText(Integer.toString(tournois.get(a).getTournoiId()));
            InputStream in = tournois.get(a).getJeuID().getImage().getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            Image finalImg = SwingFXUtils.toFXImage(image, null);
            views[a%6].setImage(finalImg);
        }
    }
    @FXML
    void navhome(MouseEvent mouseEvent) {
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
        redirect("/affichTournoi.fxml",mouseEvent);
    }

    @FXML
    void navjeu(MouseEvent mouseEvent) {
        redirect("/home.fxml",mouseEvent);
    }

    @FXML
    void navbout(MouseEvent mouseEvent) {
        redirect("/AjoutTournoi.fxml",mouseEvent);
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
    public static int getIndex(Button[] array, Button targetValue) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i].getId(), targetValue.getId())) {
                return i;
            }
        }
        return -1;
    }

    void modifier(MouseEvent event, Button b) throws SQLException, IOException {
        Button[] button = {mod1,mod2,mod3,mod4,mod5,mod6};
        Label[] labels = {n1, n2, n3, n4, n5, n6};
        int index = getIndex(button, b);
        String labelId = labels[index].getText();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ModifierTournoi.fxml"));

            // Get the controller of the loaded FXML file
            ModifierTournoi controller = new ModifierTournoi(labelId);
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
    void supprimer(MouseEvent event, Button b) throws SQLException, IOException {
        Button[] button = {supp1,supp2,supp3,supp4,supp5,supp6};
        Label[] labels = {n1, n2, n3, n4, n5, n6};
        int index = getIndex(button, b);
        String labelId = labels[index].getText();
        ps.supprimer(Integer.parseInt(labelId));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
















}
