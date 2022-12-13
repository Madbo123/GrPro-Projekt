package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import static com.GrPro.streamService.Controllers.IOController.currentUser;
import static com.GrPro.streamService.Utility.Utilities.disableNode;
import static com.GrPro.streamService.Utility.Utilities.enableNode;


public class MediaScreenController implements Initializable {

    @FXML
    private Pane openMenuBar, userImageButton, openAccMenuButton, closeAccMenuButton;
    @FXML
    private Button homeButton, filterMoviesButton, filterSeriesButton;
    @FXML
    private PasswordField changePasswordField, changePasswordFieldConf;
    @FXML
    private TextField mediaSearchField, changeDisplaynameField, changeDisplaynameFieldConf;
    @FXML
    private Label accDisplaynameLabel, accUsernameLabel, toolBarAccNameLabel;
    @FXML
    private HBox toolBar;
    @FXML
    private ScrollPane mediaScrollPane;
    @FXML
    private VBox menuBox, accMenuBox, accManagementBox;
    @FXML
    private Circle userImageCircle;
    @FXML
    private AnchorPane mediaScreenAnchor;
    @FXML
    private FlowPane mediaFlowPane;


    double x, y = 0;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (url.equals(getClass().getResource("MediaScreen.fxml"))) {
            System.out.println("Initializing for Media Screen");
            try {
                InitAllMedia();
                InitUserData();
                InitScenePermissions();
            } catch (IOException io) {
                io.printStackTrace();
            }

            //mediaScreenAnchor.getStylesheets().add(new File("src/main/resources/Assets/GUIassets/KatflixStyles.css").toURI().toString());
        }
    }

    //One-time funktion. Henter alt.
    public void InitAllMedia() throws IOException {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMediaList());


        for (Media media : copiedMediaList) {
            FXMLLoader mediaPaneLoader = new FXMLLoader(getClass().getResource("Media.fxml"));
            AnchorPane mediaPane = mediaPaneLoader.load();
            MediaPaneController mediaPaneController = mediaPaneLoader.getController();
            mediaPaneController.initializeMediaPane(media);
            mediaPane.setId(media.getTitle());
            mediaFlowPane.getChildren().add(mediaPane);
        }
    }



    public void InitUserData() {
        //TESTFUNKTION : Image userImg = new Image(new File("src/main/resources/Assets/CustomMediaImages/placeholder.jpg").toURI().toString());
        UserController.currentUserNullFailsafe();

        Image userImg = new Image(currentUser.getUserImg());
        userImageCircle.setFill(new ImagePattern(userImg));
        accDisplaynameLabel.setText(currentUser.getDisplayName());
        toolBarAccNameLabel.setText(currentUser.getDisplayName());

        //Det her er usikkert, men skal forestille en substitut for email-adresse, a la:  @email.com
        accUsernameLabel.setText(currentUser.getUsername());
    }

    public void InitScenePermissions() {
        AccManagement();
    }



    public void searchFieldEvent(KeyEvent input) {
        if (input.getCode() == KeyCode.ENTER) {
            //Søger via enter key. Kan generaliseres til søgning per input.
        }
    }


    public void FilterMedia(String searchWord, String type, String genre, double ratingMin, double ratingMax, int releaseYearMin, int releaseYearMax) {

        List<String> filterList = new ArrayList<>();
        for (Media media : ApplyAllFilters(searchWord, type, genre, ratingMin, ratingMax, releaseYearMin, releaseYearMax)) {
            filterList.add(media.getTitle());
        }


        for (Node mediaPane : mediaFlowPane.getChildren()) {
            if (!filterList.contains(mediaPane.getId())) {
                //System.out.println("Media mismatch: " + mediaPane.getId());
                disableNode(mediaPane);
            } else if (filterList.contains(mediaPane.getId())) {
                //System.out.println("Media match: " + mediaPane.getId());
                enableNode(mediaPane);
            }
        }
    }








    public static List<Media> FilterByTitle(List<Media> list, String searchWord) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getTitle().toLowerCase().contains(searchWord.toLowerCase())) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByType(List<Media> list, String typeToSortBy) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getTypeOfMedia().equalsIgnoreCase(typeToSortBy)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByGenre(List<Media> list, String genreToSortBy) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getCategories().contains(genreToSortBy)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByRating(List<Media> list, double min, double max) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getRating() > min && m.getRating() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }

    public static List<Media> FilterByRelease(List<Media> list, int min, int max) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getReleaseYear() > min && m.getReleaseYear() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }
    
    public static List<Media> ApplyAllFilters(String searchWord, String type, String genre, double ratingMin, double ratingMax, int releaseYearMin, int releaseYearMax) {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMediaList());

        if (searchWord != null && !searchWord.equals("")) {
            copiedMediaList = FilterByTitle(copiedMediaList, searchWord);
            System.out.println("Applied Filter: SearchWord");
        }
        if (type.equals("Movie")  || type.equals("Serie")) {
            copiedMediaList = FilterByType(copiedMediaList, type);
            System.out.println("Applied Filter: Type");
        }
        if (genre != null && !genre.equals("")) {
            copiedMediaList = FilterByGenre(copiedMediaList, genre);
            System.out.println("Applied Filter: Genre");
        }
        if (ratingMax >= ratingMin && ratingMax > 0 && ratingMax <= 10) {
            copiedMediaList = FilterByRating(copiedMediaList, ratingMin, ratingMax);
            System.out.println("Applied Filter: Rating");
        }
        if (releaseYearMax >= releaseYearMin && releaseYearMin >= 0 && releaseYearMax > 0) {
            copiedMediaList = FilterByRelease(copiedMediaList, releaseYearMin, releaseYearMax);
            System.out.println("Applied Filter: Release Year");
        }


        return copiedMediaList;
    }




    public void filterMoviesbutton() {
        //Filter for film
        FilterMedia("", "Movie", "", 0, 10.0, 0, Integer.MAX_VALUE);
    }

    public void filterSeriesButton() {
        //Filter for serier
        FilterMedia("", "Serie", "", 0, Double.MAX_VALUE, 0, Integer.MAX_VALUE);
    }


    public void ShowMedia(ArrayList<Media> mediaList){

        for( int i = 0; i < mediaList.size(); i++) {

            Image image = new Image("src/main/resources/Filmplakater/" + mediaList.get(i).getTitle() + ".jpg");
            ImageView imv = new ImageView(image);
            mediaFlowPane.getChildren().add(imv);
        }


    }

    public static List<String> getAllGenres() {

        List<String> genres = new ArrayList<>();
        try {

            List<Media> copiedMediaList = new ArrayList(Singleton.getInstance().getMediaList());

            for (Media m : copiedMediaList) {
                for (String s : m.getCategories()) {
                    if (!genres.contains(s)) genres.add(s);
                }
            }

            Collections.sort(genres);

        } catch (NullPointerException e) {
            System.out.println("Error at MediaController. getAllGenres(). Message: " + e.getMessage());
        }

        return genres;
    }

    public static List<String> getAllGenres(List<Media> list) {

        List<String> genres = new ArrayList<>();
        try {

            for (Media m : list) {
                for (String s : m.getCategories()) {
                    if (!genres.contains(s)) genres.add(s);
                }
            }

            Collections.sort(genres);

        } catch (NullPointerException e) {
            System.out.println("Error at MediaController.getAllGenres(List<Media). Message: " + e.getMessage());
        }

        return genres;
    }




    public void StartDragEvent(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    public void DragEvent(MouseEvent event) {
        Stage stage = (Stage) toolBar.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    //Generiske menu openers/closers. Kan samles under utilities og forkortes hvis de skal bruges oftere/andetsteds.

    public void openMenuEvent() {
        menuBox.setDisable(false);
        menuBox.setVisible(true);
        defocusEffect();
    }

    public void closeMenuEvent() {
        menuBox.setDisable(true);
        menuBox.setVisible(false);
        refocusEffect();
    }

    public void openAccMenuEvent() {
        accMenuBox.setDisable(false);
        accMenuBox.setVisible(true);
        openAccMenuButton.setDisable(true);
        openAccMenuButton.setVisible(false);
        defocusEffect();
    }

    public void closeAccMenuEvent() {
        accMenuBox.setDisable(true);
        accMenuBox.setVisible(false);
        openAccMenuButton.setDisable(false);
        openAccMenuButton.setVisible(true);
        refocusEffect();
    }


    //TIL UTILITIES
    public void defocusEffect(/*Node*/) {
        mediaScrollPane.setEffect(new ColorAdjust(0, 0, -0.3, 0));
        toolBar.setEffect(new ColorAdjust(0, 0, -0.3, 0));
    }

    public void refocusEffect(/*Node*/) {
        mediaScrollPane.setEffect(new ColorAdjust(0, 0, 0, 0));
        toolBar.setEffect(new ColorAdjust(0, 0, 0, 0));
    }

    public void AccManagement() {
        if (currentUser.getPermissions().equals("Guest")) {
            accManagementBox.setDisable(true);
            userImageButton.setDisable(true);
            changePasswordField.setPromptText("Disabled in Guest Mode");
            changePasswordFieldConf.setPromptText("Disabled in Guest Mode");
            changeDisplaynameField.setPromptText("Disabled in Guest Mode");
            changeDisplaynameFieldConf.setPromptText("Disabled in Guest Mode");
        }
    }

}
