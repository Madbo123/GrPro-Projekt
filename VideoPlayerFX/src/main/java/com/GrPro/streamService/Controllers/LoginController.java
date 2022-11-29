package com.GrPro.streamService.Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.Scanner;


public class LoginController {

    //INFO: Det ser desværre ud til at man bliver nød til at erklære sine felter separat således mht FXML.
    //Ændr endelig hvis bedre måde opdages.
    @FXML
    private Button LoginButton, CreateAccountButton, CancelAccCreateButton, ExitButton, TestButton;
    @FXML
    private AnchorPane LoginBG, CreateAccountBG;
    @FXML
    private Pane DragArea, slider;
    @FXML
    private ImageView LoginTextBubble;
    @FXML
    public Label LoginFeedbackLabel;
    @FXML
    private TextField usernameField, displaynameField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    double x = 0;
    double y = 0;

    public void ExitButtonEvent() {
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }

    public void LoginButtonEvent() {
        String usernameInput = cleanInput(usernameField.getText());
        String passwordInput = cleanInput(passwordField.getText());
        attemptLogin(usernameInput, passwordInput);
    }





    public void attemptLogin(String usernameInput, String passwordInput) {
        if (fieldValidCheck(usernameInput, passwordInput)) {
            //Login
        }
    }



    public void CreateAccountButtonEvent() throws IOException {
        String displayname = cleanInput(displaynameField.getText());
        String username = cleanInput(usernameField.getText());
        String password = cleanInput(passwordField.getText());


        if (duplicateUsernameCheck(username)) {
            IOController.create_Account(displayname, username, password);
        } else {
            //Duplicate username msg
            System.out.println("Duplicate Username!");
        }
    }

    public boolean duplicateUsernameCheck(String username) throws IOException {
        File read = new File("src/main/java/com/GrPro/streamService/Data/Users/UUID_MAP.dat");
        Scanner readIds = new Scanner(read);
        while(readIds.hasNextLine()) {
            if (readIds.nextLine().contains(username)) {
                return false;
            }
        }
        return true;
    }

    public void TestButton() {

    }

    public boolean fieldValidCheck(String usernameInput, String passwordInput) {

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

    public static String cleanInput(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    }

    public void loginFeedback(String msg) {
        LoginTextBubble.setOpacity(1);
        LoginFeedbackLabel.setText(msg);
    }

    public void swapToCreateAccScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CreateAccountScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void swapToLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

}