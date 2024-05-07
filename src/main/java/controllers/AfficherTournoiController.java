package controllers;

import javafx.application.Platform;
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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AfficherTournoiController {
    private final TournoiCrud ps = new TournoiCrud();
    private final ParticipationCrud prs = new ParticipationCrud();


    public int page =0;
    @FXML
    private Label pourc;

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
    private Label n6;

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

    @FXML
    private Label c1;

    @FXML
    private Label c2;

    @FXML
    private Label c3;

    @FXML
    private Label c4;

    @FXML
    private Label c5;

    @FXML
    private Label c6;
    @FXML
    private Button tog;

    @FXML
    private Button togL;
    @FXML
    private Label ch1;

    @FXML
    private Label ch2;

    @FXML
    private Label ch3;

    @FXML
    private Label ch4;

    @FXML
    private Label ch5;

    @FXML
    private Label ch6;


    private ScheduledExecutorService scheduler;

    public AfficherTournoiController() throws SQLException {
    }

    public static String decrementTime(String timeString) {
        String[] parts = timeString.split("[,\\s:]");
        int days = Integer.parseInt(parts[0].trim());
        int hours = Integer.parseInt(parts[3].trim());
        int minutes = Integer.parseInt(parts[4].trim());
        int seconds = Integer.parseInt(parts[5].trim());

        // Decrement seconds
        seconds--;
        if (seconds < 0) {
            seconds = 59;
            minutes--;
            if (minutes < 0) {
                minutes = 59;
                hours--;
                if (hours < 0) {
                    hours = 23;
                    days--;
                }
            }
        }

        return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    }
    private void updateTimeLabel() {
        Label[] crono = {ch1,ch2,ch3,ch4,ch5,ch6};
        Platform.runLater(() -> {
            for (Label i : crono) {
                String s = i.getText();
                String a = decrementTime(s);
                i.setText(a);
            }
        });
    }

    @FXML
    void initialize() throws SQLException, IOException {
        Label[] labels = {n1,n2,n3,n4,n5,n6};
        Label[] count = {c1,c2,c3,c4,c5,c6};
        Label[] crono = {ch1,ch2,ch3,ch4,ch5,ch6};
        ImageView[] views = {i1,i2,i3,i4,i5,i6};
        Button[] buttons = {p1,p2,p3,p4,p5,p6};
        Button[] b = {v1,v2,v3,v4,v5,v6};
        List<Tournoi> tournois = ps.afficher();
        int c=ps.tournoiCount();
        AtomicInteger aff= new AtomicInteger();
        int pageMax=((int)c/6);
        int lastPage=c%6;
        togL.setVisible(false);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateTimeLabel, 0, 1, TimeUnit.SECONDS);


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




        resetT(tournois,labels,views,count,crono,aff);

        tog.setOnMouseClicked(event -> {
            if (page<pageMax) {
                try {
                    if (page == 0) {
                        togL.setVisible(true);
                    }
                    page++;
                    if (page == pageMax) {
                        aff.set(6-c%6);
                        tog.setVisible(false);
                    }
                    resetT(tournois, labels, views,count, crono,aff);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        togL.setOnMouseClicked(event -> {
            if(page!=0) {
                try {
                    if (page == pageMax) {
                        tog.setVisible(true);
                        aff.set(0);
                    }
                    page--;
                    if (page == 0) {
                        togL.setVisible(false);
                    }
                    resetT(tournois, labels, views,count,crono, aff);

                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public static String getFormattedDifference(Date date1, Date date2) {
        // Calculate the difference between the two dates in milliseconds
        long millisecondsDifference = date2.getTime() - date1.getTime();

        // Convert milliseconds to days, hours, minutes, and seconds
        long secondsDifference = millisecondsDifference / 1000;
        long days = secondsDifference / (24 * 60 * 60);
        long hours = (secondsDifference % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((secondsDifference % (24 * 60 * 60)) % (60 * 60)) / 60;
        long seconds = ((secondsDifference % (24 * 60 * 60)) % (60 * 60)) % 60;

        // Create a formatted string
        return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    }

    public void resetT(List<Tournoi> tournois, Label[] labels, ImageView[] views,Label[] count,Label[] crono, AtomicInteger c) throws SQLException, IOException {
        Button[] buttons = {p1,p2,p3,p4,p5,p6};
        Button[] b = {v1,v2,v3,v4,v5,v6};
        for (int a=page*6;a<(page+1)*6-c.get();a++) {
            labels[a%6].setText(tournois.get(a).getType());
            views[a%6].setImage(tournois.get(a).getJeuID().getImage());
            count[a%6].setText(String.valueOf(tournois.get(a).getCount())+" / 10");
            Date dateTournoi = tournois.get(a).getDate();
            Date maintenant = new Date();
            String difference = getFormattedDifference( maintenant, dateTournoi);

            crono[a % 6].setText(difference);
        }
        if(c.get()!=0){
            for (int a=6-c.get();a<6;a++) {
                labels[a%6].setVisible(false);
                views[a%6].setVisible(false);
                b[a%6].setVisible(false);
                buttons[a%6].setVisible(false);
                count[a%6].setVisible(false);
                crono[a % 6].setVisible(false);
            }
        }else{
            for (int a=0;a<6;a++) {
                labels[a%6].setVisible(true);
                views[a%6].setVisible(true);
                b[a%6].setVisible(true);
                buttons[a%6].setVisible(true);
                count[a%6].setVisible(true);
                crono[a % 6].setVisible(true);
            }
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


        if(ps.incrementCount(ps.recupererType(labelId).getId())==1) {
            prs.ajouter(participation);
            showAlert("Félicitation", "Vous êtes inscrit au tournoi");
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