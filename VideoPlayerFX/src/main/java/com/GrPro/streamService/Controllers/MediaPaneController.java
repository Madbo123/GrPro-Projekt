package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Serie;
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
import java.util.Properties;


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


    public void initializeMediaPane(Media media) {
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


    //Sæt gold border til høje ratings
    /*public void setMediaBorder(Media media) {
        if (media.getRating() > 8.6) {
            mediaPaneAnchor.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.valueOf("#ffad00"), 27, 0, 0, 0));
        }
    } */
}
