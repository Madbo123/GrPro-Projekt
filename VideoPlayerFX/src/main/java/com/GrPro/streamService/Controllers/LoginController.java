package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.GrPro.streamService.Controllers.UserController.setUser;
import static com.GrPro.streamService.Utility.Utilities.centerStage;
import static com.GrPro.streamService.Utility.Utilities.getFieldInput;


public class LoginController {

    //INFO: Det ser desværre ud til at man bliver nød til at erklære sine felter separat således mht FXML.
    //Ændr endelig hvis bedre måde opdages.
    @FXML
    private Button LoginButton, ExitButton, CreateAccountButton;
    @FXML
    private Pane DragArea;
    @FXML
    private ImageView LoginTextBubble;
    @FXML
    private Label LoginFeedbackLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    double x, y = 0;



    public boolean authenticate(String username, String password) throws IOException {
        File userMap = new File("src/main/resources/Data/Users/UUID_MAP.dat");
        Scanner mapReader = new Scanner(userMap);

        while(mapReader.hasNextLine()) {
            String[] userdata = mapReader.nextLine().split(" ", 3);
            if (userdata[0].equals(username) && userdata[1].equals(password)) {
                System.out.println("Login succeeded with credentials: " + userdata[0] + " + " + userdata[1]);
                try {
                    setUser(IOController.load_User(userdata[2]));
                    return true;
                } catch (IOException io) {
                    System.out.println("File(s) for the given user could not be found");
                    io.printStackTrace();
                    return false;
                } catch (ClassNotFoundException objectVersion) {
                    System.out.println("Object SerialVersionUID mismatch");
                    objectVersion.printStackTrace();
                    return false;
                }
            }
        }
        loginFeedback("Username or password is incorrect");
        return false;
    }


    public void attemptLogin(ActionEvent event) {
        String usernameInput = getFieldInput(usernameField);
        String passwordInput = getFieldInput(passwordField);
        if (validateCredentials(usernameInput, passwordInput)) {
            try {
                if (authenticate(usernameInput, passwordInput)) {swapToKatflixMenu(event);}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public boolean validateCredentials(String usernameInput, String passwordInput) {
        if (!usernameField.getText().matches("^[a-zA-Z0-9]+$") || !passwordField.getText().matches("^[a-zA-Z0-9]+$")) {
            loginFeedback("Non-alphanumeric inputs make me angry");
            return false;
        }

        switch (usernameInput.isBlank() + "-" + passwordInput.isBlank()) {
            case "false-false" -> {
                loginFeedback("Attempting login...");
                return true;
            }
            case "false-true" -> loginFeedback("Please enter a password");
            case "true-false" -> loginFeedback("Please enter a username");
            case "true-true" -> loginFeedback("Please enter username and password");
        }
        return false;
    }


    public void loginFeedback(String msg) {
        LoginTextBubble.setOpacity(1);
        LoginFeedbackLabel.setText(msg);
    }

    public void setUsernameField(String input) {
        usernameField.setText(input);
    }

    public void swapToKatflixMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MediaScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        centerStage(stage);
    }

    public void swapToCreateAccScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CreateAccountScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        centerStage(stage);
    }

    public void LoginAsGuestEvent(ActionEvent event) throws IOException {
        UserController.login_as_guest();
        swapToKatflixMenu(event);
    }

    //Til dragging af vinduet. Henter koords ved start af klik, bruger dem som referencepunkt til at opdatere position.

    public void StartDragEvent(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void DragEvent(MouseEvent event) {
        Stage stage = (Stage) DragArea.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void ExitButtonEvent() {
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }

}