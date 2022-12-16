package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Movie;
import com.GrPro.streamService.Model.Serie;
import com.GrPro.streamService.Model.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.GrPro.streamService.Controllers.MediaScreenController.currentMediaScreenController;
import static com.GrPro.streamService.Controllers.MediaScreenController.paneMap;
import static com.GrPro.streamService.Controllers.UserController.getCurrentUser;
import static com.GrPro.streamService.Utility.Utilities.*;

public class MediaAboutController {

    @FXML
    private Button PlayButton, FavoriteButton, BackButton;
    @FXML
    private ComboBox SeasonComboBox;
    @FXML
    private ImageView MediaImage;
    @FXML
    private Label MediaTitleTextArea, GenresTextField, ReleaseYearTextField, RatingTextField;
    @FXML
    private VBox vBox;

    private Media activeMedia;
    private InputStream posterFolderStream;

    private int episodesPerRow = 7;
    private int episodeSpacing = 20;
    private int episodeWidth = 100;




    public void InitialiseAboutPage(Media media) {

        activeMedia = media;
        initUserRank();
        if (activeMedia.getFavoriteOfCurrentUser()) {
            FavoriteButton.setDisable(true);
            FavoriteButton.setText("Favorited!");
        }
        // Determines which folder is necessary to look into to find the corresponding poster
        // also determines the visibilty of Series specific components of the page

        //Ændret instanceof til media.getTypeofMedia - Mads B.
        String PosterFolder;
        if (media.getTypeOfMedia().equals("Movie")) {
            SeasonComboBox.setVisible(false);
            PosterFolder = "filmplakater";
            setMediaImage(PosterFolder);
        } else {
            PosterFolder = "serieforsider";
            setMediaImage(PosterFolder);
            updateSeasons();
        }

        // Initializes the text and image of the about page to that of the selected media
        setTextFields();

    }

    private void setTextFields() {

        // Generates the string of genres in order to display them correctly
        String Genres = "";
        for (String s : activeMedia.getCategories()) {
            if (Genres.equals("")) Genres += s;
            else Genres += ", " + s;
        }

        // Fills in the text fields on the page
        MediaTitleTextArea.setText(activeMedia.getTitle());
        GenresTextField.setText("Genres: " + Genres);
        if (activeMedia instanceof Serie) ReleaseYearTextField.setText("Released: " + activeMedia.getReleaseYear() +
                                                                 " - " + ((Serie) activeMedia).getEndedYear());
        else ReleaseYearTextField.setText("Released: " + activeMedia.getReleaseYear());
        RatingTextField.setText("Rating: " + activeMedia.getRating() + "/10");
    }

    private void setMediaImage(String PosterFolder) {
        // Locates the correct media poster and displays it
        posterFolderStream = getClass().getResourceAsStream("/Data/" + PosterFolder + "/" + activeMedia.getTitle() + ".jpg");
        Image image = new Image(posterFolderStream);
        MediaImage.setImage(image);
    }

    private void updateSeasons() {
       for (int i = 1; i <= ((Serie) activeMedia).getSeasons().size(); i++) {
           SeasonComboBox.getItems().add("Season " + i);
       }
       SeasonComboBox.setValue(SeasonComboBox.getItems().get(0));
    }

    public void updateEpisodeList() {
        while (vBox.getChildren().size() > 2) {
            vBox.getChildren().remove(vBox.getChildren().size() - 1);
        }

        int season = Integer.parseInt(SeasonComboBox.valueProperty().getValue().toString().replaceAll("[a-zA-Z ]", ""));
        int episodesInSeason = ((Serie) activeMedia).getSeasons().get(season - 1);


        HBox hBox =  new HBox();
        for (int i = 0; i < episodesInSeason; i++) {

            if (i % episodesPerRow == 0) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
            }

            VBox newVBox = new VBox();
            ImageView imageView = new ImageView(MediaImage.getImage());

            imageView.setFitWidth(episodeWidth);
            imageView.setFitHeight(episodeWidth * 1.42857);
            newVBox.setPadding(new Insets(episodeSpacing,0,10,episodeSpacing));


            TextField textField = new TextField("Ep. " + (i+1));
            textField.setPrefWidth(episodeWidth);
            textField.setEditable(false);

            newVBox.getChildren().add(0, imageView);
            newVBox.getChildren().add(1, textField);

            hBox.getChildren().add(newVBox);
        }
        vBox.getChildren().add(hBox);


    }

    public void PlayMedia(MouseEvent event) throws IOException {
        FXMLLoader videoLoader = new FXMLLoader(getClass().getResource("MediaPlayer.fxml"));
        Parent root = videoLoader.load();
        Scene sceneSave = ((Node)event.getSource()).getScene();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);




        MediaPlayerController mediaPlayerController = videoLoader.getController();

        //Funktion til hvis serier havde episoder

        /* if (activeMedia.getTypeOfMedia().equals("Serie")) {
            int Season = SeasonComboBox.getPromptText();

            int Episode = ((Node) event.getSource()).getId();

            her ville man så getSeason(int Season, int Index);

            mediaPlayerController.initializeVideoPlayer(File som media, sceneSave);
        } */



        mediaPlayerController.initializeVideoPlayer(activeMedia, sceneSave);

        stage.show();
        centerStage(stage);
    }

    public void FavorMedia() {
        getCurrentUser().addFavorite(activeMedia);
        activeMedia.setFavoriteOfCurrentUser(true);
        FavoriteButton.setDisable(true);
        FavoriteButton.setText("Favorited!");

        paneMap.get(activeMedia.getTitle()).updateStars();



        try {
            IOController.save_User(getCurrentUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackButtonEvent() {
        currentMediaScreenController.overlayPane.getChildren().clear();
        currentMediaScreenController.overlayPane.setDisable(true);
        currentMediaScreenController.overlayPane.setVisible(false);

        currentMediaScreenController.mediaScrollPane.setDisable(false);
    }

    public void initUserRank() {
        if (getCurrentUser().getUserRank().equals("Guest")) {
            FavoriteButton.setDisable(true);
        }
    }


}
