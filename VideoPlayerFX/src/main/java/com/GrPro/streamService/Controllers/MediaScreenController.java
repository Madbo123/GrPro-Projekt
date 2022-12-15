package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.GrPro.streamService.Controllers.FilterController.*;
import static com.GrPro.streamService.Controllers.IOController.save_User;
import static com.GrPro.streamService.Controllers.UserController.*;
import static com.GrPro.streamService.Utility.Utilities.*;


public class MediaScreenController implements Initializable {

    @FXML
    private Pane openMenuBar, userImageButton, openAccMenuButton, closeAccMenuButton;
    @FXML
    private Button homeButton, filterMoviesButton, filterSeriesButton, favoritesButton, LogOutButton, confirmChangeButton, changePassButton, changeDNButton;
    @FXML
    private PasswordField changePasswordField, changePasswordFieldConf;
    @FXML
    protected TextField mediaSearchField;
    @FXML
    private TextField changeDisplaynameField, changeDisplaynameFieldConf;
    @FXML
    private Label accDisplaynameLabel, accUsernameLabel, toolBarAccNameLabel;
    @FXML
    private ScrollPane mediaScrollPane;
    @FXML
    private VBox menuBox;
    @FXML
    private Circle userImageCircle;
    @FXML
    private AnchorPane toolBar, accMenuBox, accManagementBox;
    @FXML
    public AnchorPane mediaScreenAnchor;
    @FXML
    private FlowPane mediaFlowPane;

    private Media videoMedia;
    double x, y = 0;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (url.equals(getClass().getResource("MediaScreen.fxml"))) {
            System.out.println("Initializing for Media Screen");
            try {
                currentUserNullFailsafe();
                InitAllMedia();
                InitUserData();
                InitScenePermissions();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    //One-time funktion. Henter alt.
    public void InitAllMedia() throws IOException {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMediaList());


        for (Media media : copiedMediaList) {
            FXMLLoader mediaPaneLoader = new FXMLLoader(getClass().getResource("Media.fxml"));
            AnchorPane mediaPane = mediaPaneLoader.load();
            MediaPaneController mediaPaneController = mediaPaneLoader.getController();
            //mediaPaneController.setMedia(media);
            mediaPaneController.initializeMediaPane(media);
            mediaPane.setId(media.getTitle());
            mediaFlowPane.getChildren().add(mediaPane);
        }
    }

    public void InitUserData() {
        //TESTFUNKTION : Image userImg = new Image(new File("src/main/resources/Assets/CustomMediaImages/placeholder.jpg").toURI().toString());
        UserController.currentUserNullFailsafe();

        Image userImg = new Image(getCurrentUser().getUserImg());
        userImageCircle.setFill(new ImagePattern(userImg));
        accDisplaynameLabel.setText(getCurrentUser().getDisplayName());
        toolBarAccNameLabel.setText(getCurrentUser().getDisplayName());

        //Det her er usikkert, men skal forestille en substitut for email-adresse, a la:  @email.com
        accUsernameLabel.setText(getCurrentUser().getUsername());
    }

    public void InitScenePermissions() {
        AccManagement();
    }


    public void updateMediaDisplay() {
        List<Media> FilterResult = FilterController.FilterMedia();

        for (Node mediaPane : mediaFlowPane.getChildren()) {
            if (FilterResult.contains((Media)mediaPane.getUserData())) {
                enableNode(mediaPane);
            } else if (!FilterResult.contains((Media)mediaPane.getUserData())) {
                disableNode(mediaPane);
            }
        }
    }



    public void searchFieldEvent(KeyEvent input) {
        searchWord = mediaSearchField.getText();
        updateMediaDisplay();
    }




    //Nulstiller type filter.
    public void homeButton() {
        if (!type.equals("")) {
            dimButton(homeButton);
            type = "";
        }
        updateMediaDisplay();
    }


    //Type filters
    public void filterMoviesbutton() {
        if (!type.equals("Movie")) {
            dimButton(filterMoviesButton);
            type = "Movie";
        }
        updateMediaDisplay();
    }

    public void filterSeriesButton() {
        if (!type.equals("Serie")) {
            dimButton(filterSeriesButton);
            type = "Serie";
        }
        updateMediaDisplay();
    }


    //Fav display her
    public void favoritesButton() {
        for (Node mediaPane : mediaFlowPane.getChildren()) {
            for (Media media: getCurrentUser().getFavorites()) {
                if (media.getTitle().equals(((Media) mediaPane.getUserData()).getTitle())) {
                    enableNode(mediaPane);
                    break;
                }
                else {
                    disableNode(mediaPane);
                }
            }
        }
    }

    //Vis aktive filterknap
    public void dimButton(Button button) {
        button.setEffect(new ColorAdjust(0, 0, -0.17, 0));

        String buttonName = button.getText();

        switch (buttonName) {
            case "Home" -> {
                filterMoviesButton.setEffect(null);
                filterSeriesButton.setEffect(null);
            }
            case "Movies" -> {
                homeButton.setEffect(null);
                filterSeriesButton.setEffect(null);
            }
            case "Series" -> {
                homeButton.setEffect(null);
                filterMoviesButton.setEffect(null);
            }
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
        if (accMenuBox.isDisabled()) {
            refocusEffect();
        }
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
        if (menuBox.isDisabled()) {
            refocusEffect();
        }
    }

    public void cPassEvent() {
        disableNode(changeDisplaynameField);
        disableNode(changePasswordFieldConf);
        enableNode(changePasswordField);
        enableNode(changePasswordFieldConf);
        enableNode(confirmChangeButton);
    }

    public void cDNEvent() {
        disableNode(changePasswordField);
        disableNode(changePasswordFieldConf);
        enableNode(changeDisplaynameField);
        enableNode(changeDisplaynameFieldConf);
        enableNode(confirmChangeButton);
    }

    //Det her er verdenens værste funktion, pagtens ark i tekstform.
    public void confChanges() {
        if (changeDisplaynameField.isDisabled() && cleanInput(changePasswordField.getText()).equals(cleanInput(changePasswordFieldConf.getText()))
                    && cleanInput(changePasswordField.getText()).length() > 4) {

            getCurrentUser().setPassword(cleanInput(changePasswordField.getText()));
            try {
                save_User(getCurrentUser());
            } catch (Exception e) {
                e.printStackTrace();
            }

            disableNode(changePasswordField);
            disableNode(changePasswordFieldConf);
            disableNode(confirmChangeButton);

        } else if (changePasswordField.isDisabled() && cleanInput(changeDisplaynameField.getText()).equals(cleanInput(changeDisplaynameFieldConf.getText()))
                    && cleanInput(changeDisplaynameField.getText()).length() > 4) {

            getCurrentUser().setDisplayname(cleanInput(changeDisplaynameField.getText()));
            try {
                save_User(getCurrentUser());
            } catch (Exception e) {
                e.printStackTrace();
            }

            disableNode(changeDisplaynameField);
            disableNode(changeDisplaynameFieldConf);
            disableNode(confirmChangeButton);
        }
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
        if (getCurrentUser().getUserRank().equals("Guest") || getCurrentUser() == null) {
            accManagementBox.setDisable(true);
            userImageButton.setDisable(true);
            changePassButton.setText("Disabled | Guest");
            changePassButton.setDisable(true);
            changeDNButton.setText("Disabled | Guest");
            changeDNButton.setDisable(true);
            favoritesButton.setText("Disabled | Guest");
            favoritesButton.setFont(Font.font(16));
            favoritesButton.setDisable(true);
        }
    }


    public void logOutButton(ActionEvent event) throws IOException {
        setUser(null);
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        centerStage(stage);
    }
}
