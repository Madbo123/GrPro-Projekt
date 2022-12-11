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
import java.io.InputStream;

public class MediaAboutController {

    @FXML
    private Button PlayButton, FavoriteButton, TempButton;
    @FXML
    private ComboBox SeasonComboBox;
    @FXML
    private ImageView MediaImage;
    @FXML
    private TextArea MediaTitleTextArea;
    @FXML
    private TextField GenresTextField, ReleaseYearTextField, RatingTextField;
    @FXML
    private VBox vBox;

    private Media activeMedia;
    private InputStream posterFolderStream;

    private int episodesPerRow = 7;
    private int episodeSpacing = 20;
    private int episodeWidth = 100;

    public void TestInitialiseAboutPage() {

        // This line is for test purposes only
        Media media = (Media) Singleton.getInstance().getMedia().get(103);
        activeMedia = media;

        // Determines which folder is necessary to look into to find the corresponding poster
        // also determines the visibilty of Series specific components of the page
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

    public void InitialiseAboutPage(Media media) {

        activeMedia = media;

        // Determines which folder is necessary to look into to find the corresponding poster
        // also determines the visibilty of Series specific components of the page
        String PosterFolder;
        if (media instanceof Movie) {
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
        RatingTextField.setText("Rating: " + activeMedia.getRating());
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

        System.out.println(episodesInSeason);

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

            newVBox.getChildren().add(0, imageView);
            newVBox.getChildren().add(1, textField);

            hBox.getChildren().add(newVBox);
        }
        vBox.getChildren().add(hBox);


    }

    public void PlayMedia(){

    }

    public void FavorMedia() {

    }






}
