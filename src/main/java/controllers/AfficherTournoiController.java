package controllers;
//hey
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import edu.esprit.services.TournoiCrud;
import edu.esprit.entities.Tournoi;
import edu.esprit.services.ParticipationCrud;
import edu.esprit.entities.Participation;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AfficherTournoiController {
    private final TournoiCrud ps = new TournoiCrud();
    private final ParticipationCrud prs = new ParticipationCrud();


    public int page =0;
    @FXML
    private Button v1;
    @FXML
    private Button v2;
    @FXML
    private Button v3;
    @FXML
    private Button v4;
    @FXML
    private Button v5;
    @FXML
    private Button v6;

    @FXML
    private Button p1;

    @FXML
    private Button p2;

    @FXML
    private Label n1;

    @FXML
    private Button p3;

    @FXML
    private Label n2;

    @FXML
    private Button p4;

    @FXML
    private HBox homeNav;

    @FXML
    private Label n3;

    @FXML
    private Button p5;

    @FXML
    private Label n4;

    @FXML
    private Button p6;

    @FXML
    private Label n5;

    @FXML
    private ImageView i1;

    @FXML
    private Label n6;

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
    @FXML
    private Button tog;

    @FXML
    private Button togL;




    public AfficherTournoiController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Label[] labels = {n1,n2,n3,n4,n5,n6};
        ImageView[] views = {i1,i2,i3,i4,i5,i6};
        Button[] buttons = {p1,p2,p3,p4,p5,p6};
        Button[] b = {v1,v2,v3,v4,v5,v6};
        List<Tournoi> tournois = ps.afficher();
        int c=ps.tournoiCount();
        AtomicInteger aff= new AtomicInteger();
        int pageMax=((int)c/6);

        p1.setOnMouseClicked(event -> {
            try {
                participer(event, p1);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        p2.setOnMouseClicked(event -> {
            try {
                participer(event, p2);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        p3.setOnMouseClicked(event -> {
            try {
                participer(event, p3);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        p4.setOnMouseClicked(event -> {
            try {
                participer(event, p4);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        p5.setOnMouseClicked(event -> {
            try {
                participer(event, p5);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        p6.setOnMouseClicked(event -> {
            try {
                participer(event, p6);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        v1.setOnMouseClicked(event -> {
            try {
                visualiser(event, v1);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        v2.setOnMouseClicked(event -> {
            try {
                visualiser(event,v2);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        v3.setOnMouseClicked(event -> {
            try {
                visualiser(event, v3);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        v4.setOnMouseClicked(event -> {
            try {
                visualiser(event, v4);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        v5.setOnMouseClicked(event -> {
            try {
                visualiser(event, v5);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        v6.setOnMouseClicked(event -> {
            try {
                visualiser(event, v6);
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
        for (int a=page*6;a<(page+1)*6-c.get();a++) {
            labels[a%6].setText(tournois.get(a).getType());
            InputStream in = tournois.get(a).getJeuID().getImage().getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            Image finalImg = SwingFXUtils.toFXImage(image, null);
            views[a%6].setImage(finalImg);
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

    void participer(MouseEvent event, Button b) throws SQLException, IOException {
        Button[] buttons = {p1, p2, p3, p4, p5, p6};
        Label[] labels = {n1, n2, n3, n4, n5, n6};
        int index = getIndex(buttons, b);
        String labelId = labels[index].getText();

        // Création de l'instance Participation
        Participation participation = new Participation();
        participation.setId(ps.recupererType(labelId).getId()); // Convertir l'ID en entier
        showAlert("Félicitation", "Vous êtes inscrit au tournoi");
        prs.ajouter(participation);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/participation.fxml"));

            // Get the controller of the loaded FXML file
            ParticipationController controller = new ParticipationController(labelId);
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
    void visualiser(MouseEvent event, Button b) throws SQLException, IOException {
        Button[] buttons = {v1,v2,v3,v4,v5,v6};
        Label[] labels = {n1, n2, n3, n4, n5, n6};
        int index = getIndex(buttons, b);
        String labelId = labels[index].getText();


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/participation.fxml"));

            // Get the controller of the loaded FXML file
            ParticipationController controller = new ParticipationController(labelId);
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}