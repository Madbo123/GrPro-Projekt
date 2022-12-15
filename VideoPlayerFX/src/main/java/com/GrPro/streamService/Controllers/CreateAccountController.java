package com.GrPro.streamService.Controllers;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.GrPro.streamService.Utility.Utilities.centerStage;
import static com.GrPro.streamService.Utility.Utilities.getFieldInput;

public class CreateAccountController {

    @FXML
    private Button CancelAccCreateButton, ExitButton, CreateAccountButton;
    @FXML
    private Pane DragArea;
    @FXML
    private Label displaynameLabel, usernameLabel, passwordLabel, AccCreatedLabel;
    @FXML
    private TextField usernameField, displaynameField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    double x, y = 0;


    public void CreateAccountButtonEvent(ActionEvent event) throws IOException {
        String displayname = getFieldInput(displaynameField);
        String username = getFieldInput(usernameField);
        String password = getFieldInput(passwordField);

        if (validateAll()) {
            if (duplicateUsernameCheck(username)) {
                AccCreatedLabel.setVisible(true);
                IOController.create_Account(displayname, username, password);
                swapToLoginScreen(event);
            }
        }
    }


    public boolean validateAll() {
        boolean displayname = validateField(displaynameField);
        boolean username = validateField(usernameField);
        boolean password = validateField(passwordField);


        if (displayname && username && password) {
            if (getFieldInput(confirmPasswordField).equals(getFieldInput(passwordField))) {
                return true;
            } else {
                passwordLabel.setText("Password fields do not match");
                return false;
            }
        }
        return false;
    }

    public boolean validateField(TextField field) {
        if (!blankInputCheck(field) || !specialCharCheck(field) || !charMinCheck(field)) {
            return false;
        }
        return true;
    }

    public boolean blankInputCheck(TextField field) {

        if (field.getText().isBlank()) {
            switch (field.getId()) {
                case "displaynameField" -> displaynameLabel.setText("Please enter a display name");
                case "usernameField" -> usernameLabel.setText("Please enter a username");
                case "passwordField" -> passwordLabel.setText("Please enter a password");

                case "confirmPasswordField" -> {
                    if (!passwordField.getText().isBlank()) {
                        passwordLabel.setText("Please confirm your password");
                    }
                }
            }
            return false;
        }
        return true;
    }

    public boolean specialCharCheck(TextField field) {

        if (!field.getText().matches("^[a-zA-Z0-9]+$")) {
            switch (field.getId()) {
                case "displaynameField" -> displaynameLabel.setText("Special characters aren't allowed");
                case "usernameField" -> usernameLabel.setText("Special characters aren't allowed");
                case "passwordField", "confirmPasswordField" -> passwordLabel.setText("Special characters aren't allowed");
            }
            return false;
        }
        return true;
    }

    public boolean duplicateUsernameCheck(String username) throws IOException {
        File read = new File("src/main/resources/Data/Users/UUID_MAP.dat");

        if (read.exists()) {
            Scanner readIds = new Scanner(read);
            while(readIds.hasNextLine()) {
                if (readIds.nextLine().contains(username)) {
                    usernameLabel.setText("Username already taken");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean charMinCheck(TextField field) {

        if (field.getText().length() <= 4) {
            switch (field.getId()) {
                case "displaynameField" -> displaynameLabel.setText("Display name has to be longer than 4 characters");
                case "usernameField" -> usernameLabel.setText("Username has to be longer than 4 characters");
                case "passwordField" -> passwordLabel.setText("Password has to be longer than 4 characters");
            }
            return false;
        }
        return true;
    }


    public void swapToLoginScreen(ActionEvent event) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        root = loginLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        if (event.getSource().equals(CreateAccountButton)) {
            LoginController loginController = loginLoader.getController();
            loginController.loginFeedback("Account created. Please login below.");
            loginController.setUsernameField(usernameField.getText());
        }
        stage.show();
        centerStage(stage);
    }

    public void hideFeedbackLabel(KeyEvent input) {
        TextField field = (TextField) input.getTarget();
        String fieldId = field.getId();
        //System.out.println("Testing label: " + fieldId);

        switch (fieldId) {
            case "displaynameField" -> displaynameLabel.setText("");
            case "usernameField" -> usernameLabel.setText("");
            case "passwordField", "confirmPasswordField" -> passwordLabel.setText("");
        }
    }

    public void CancelAccCreateButton(ActionEvent event) throws IOException {
        swapToLoginScreen(event);
    }

    public void ExitButtonEvent() {
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }

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
