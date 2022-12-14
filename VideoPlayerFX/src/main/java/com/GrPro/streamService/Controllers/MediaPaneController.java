package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.File;
import java.io.IOException;


public class MediaPaneController {

    @FXML
    private AnchorPane mediaPaneAnchor;
    @FXML
    private ImageView mediaImage;

    //Kan udvides med mere info hvis der er tid. (Description, director, actors, genres f.eks)
    @FXML
    private Label mediaTitle, mediaReleaseYear;

    @FXML
    private Text mediaRatingAvg;

    @FXML
    private TextFlow mediaGenres;

    @FXML
    private ImageView favoriteStar;
    private Media media;

    @FXML
    private void initialize() {
        if (Singleton.getInstance().getUser() != null) {
            favoriteStar.setPickOnBounds(true);
        }
        else favoriteStar.setVisible(false);
    }
    
    public void initializeMediaPane(Media media) throws IOException {
        this.media = media;
        if (Singleton.getInstance().getUser() != null) {
            for (Media m: Singleton.getInstance().getUser().getFavorites()) {
                if (m.getTitle().equals(this.media.getTitle())) {
                    this.media.setFavoriteOfCurrentUser(true);
                    favoriteStar.setImage(new Image(getClass().getResource("star_filled.png").openStream()));
                }
            }
            favoriteStar.setOnMouseClicked(event -> {
                //kaster en illegalArumentException, men ser ikke ud til at der sker noget med den
                if (!this.media.getFavoriteOfCurrentUser()) {
                    Singleton.getInstance().getUser().addFavorite(media);
                    media.setFavoriteOfCurrentUser(true);
                    try {
                        favoriteStar.setImage(new Image(getClass().getResource("star_filled.png").openStream()));
                        IOController.save_User(Singleton.getInstance().getUser());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Singleton.getInstance().getUser().removeFavorite(media);
                    media.setFavoriteOfCurrentUser(false);
                    try {
                        favoriteStar.setImage(new Image(getClass().getResource("star_hollow.png").openStream()));
                        IOController.save_User(Singleton.getInstance().getUser());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // set favorite or remove favorite
            });
        }
        mediaTitle.setText(media.getTitle());
        mediaRatingAvg.setText(Double.toString(media.getRating()));
        //setMediaBorder(media);
        setMediaSrc(media);
        setMediaGenres(media);
        setMediaPaneImage(media);
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

    public void setMedia(Media media) {
        this.media = media;
    }

    //Sæt gold border til høje ratings
    /*public void setMediaBorder(Media media) {
        if (media.getRating() > 8.6) {
            mediaPaneAnchor.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.valueOf("#ffad00"), 27, 0, 0, 0));
        }
    } */
}
