package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static com.GrPro.streamService.Controllers.MediaScreenController.currentMediaScreenController;
import static com.GrPro.streamService.Controllers.UserController.getCurrentUser;
import static com.GrPro.streamService.Utility.Utilities.*;


public class MediaPaneController {

    @FXML
    private AnchorPane mediaPaneAnchor;
    @FXML
    private Pane starPane;
    @FXML
    private ImageView mediaImage;

    //Kan udvides med mere info hvis der er tid. (Description, director, actors, genres f.eks
    @FXML
    private Label mediaTitle, mediaReleaseYear;

    @FXML
    private Text mediaRatingAvg;

    @FXML
    private TextFlow mediaGenres;

    @FXML
    public ImageView starFill;

    @FXML
    public ImageView starHollow;

    private Media media;




    public void initializeMediaPane(Media media) throws IOException {
        this.media = media;
        setMediaSrc(media);
        setMediaGenres(media);
        setMediaPaneImage(media);

        initFavs();
        mediaTitle.setText(media.getTitle());
        mediaRatingAvg.setText(Double.toString(media.getRating()));
    }


    public void setFavorite() {
        media.setFavoriteOfCurrentUser(!media.getFavoriteOfCurrentUser());

        try {
            if (media.getFavoriteOfCurrentUser()) {
                getCurrentUser().addFavorite(media);
                disableNode(starHollow);
                enableNode(starFill);
                System.out.println("Added " + media.getTitle() + " to favorites");
            } else {
                getCurrentUser().removeFavorite(media);
                disableNode(starFill);
                enableNode(starHollow);
                System.out.println("Removed " + media.getTitle() + " from favorites");
            }
            IOController.save_User(getCurrentUser());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void favoriteButton(MouseEvent event) {
        setFavorite();
    }

    public void setMediaSrc(Media media) {
        mediaPaneAnchor.setUserData(media);
    }

    public void setMediaPaneImage(Media media) {
        if (media.getTypeOfMedia().equals("Movie") && new File("src/main/resources/Data/Filmplakater/" + media.getTitle() + ".jpg").exists()) {
            media.setMediaImage("src/main/resources/Data/Filmplakater/" + media.getTitle() + ".jpg");
        } else if (media.getTypeOfMedia().equals("Serie") && new File("src/main/resources/Data/Serieforsider/" + media.getTitle() + ".jpg").exists()) {
            media.setMediaImage("src/main/resources/Data/Serieforsider/" + media.getTitle() + ".jpg");
        } else {
            System.out.println("Image not found for " + media.getTypeOfMedia() + " " + media.getTitle());
        }

        File imageFile = new File(media.getMediaImage());
        Image imageSrc = new Image(imageFile.toURI().toString());
        mediaImage.setImage(imageSrc);
    }

    public void setMediaGenres(Media media) {
        Text temp = new Text("");
        for (String categories : media.getCategories()) {
            temp.setText(temp.getText() + categories + ", ");
        }
        temp.setText(temp.getText().substring(0, temp.getText().length() - 2));
        temp.setFill(Color.WHITE);
        temp.setFont(Font.font("System", FontPosture.REGULAR, 14));


        mediaGenres.getChildren().add(temp);
    }

    public void displayMediaInfoEvent(ActionEvent event) {

    }

    //Sætter medier til favorit
    public void setStars() {
        for (Media med : getCurrentUser().getFavorites()) {
            if (med.getTitle().equals(media.getTitle())) {
                media.setFavoriteOfCurrentUser(true);
                disableNode(starHollow);
                enableNode(starFill);
            }
        }
    }

    //Henter brugerens rank og begræns favorit-funktionalitet hvis det er en gæst.
    public void initFavs() {
        if (!getCurrentUser().getUserRank().equals("Guest") && getCurrentUser() != null) {
            setStars();
        } else {
            disableNode(starPane);
        }
    }

    public void setMedia(Media media) {
        this.media = media;
    }


    public void updateStars() {
        for (Media med : getCurrentUser().getFavorites()) {
            if (med.getTitle().equals(media.getTitle())) {
                disableNode(starHollow);
                enableNode(starFill);
            }
        }
    }



    //Gemmer scenen, som brugeren er på. Starter en ny scene og kan gå tilbage til den gamle scene på denne måde.
    // (Her fjernes scenen midlertidigt for at minimere performance påvirkelse)
    public void playMedia(MouseEvent event) throws IOException {
        //DET HER VAR IKKE SJOVT AT FINDE UD AF HVORDAN MAN GJORDE

        FXMLLoader videoLoader = new FXMLLoader(getClass().getResource("MediaPlayer.fxml"));
        Parent root = videoLoader.load();
        Scene sceneSave = ((Node)event.getSource()).getScene();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MediaPlayerController mediaPlayerController = videoLoader.getController();
        mediaPlayerController.initializeVideoPlayer(media, sceneSave);

        stage.show();
        centerStage(stage);
    }

    //Det her er en suboptimal løsning. Men alternativet er en samlet controller der styrer det hele, og det er der ikke tid til.
    public void openAboutPage(MouseEvent event) throws IOException {
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("MediaAboutScreen.fxml"));
        AnchorPane aboutPageInstance = aboutLoader.load();
        MediaAboutController aboutController = aboutLoader.getController();
        aboutController.InitialiseAboutPage(media);




        currentMediaScreenController.mediaScrollPane.setDisable(true);
        currentMediaScreenController.overlayPane.getChildren().add(aboutPageInstance);
        currentMediaScreenController.overlayPane.setDisable(false);
        currentMediaScreenController.overlayPane.setVisible(true);
    }
}
