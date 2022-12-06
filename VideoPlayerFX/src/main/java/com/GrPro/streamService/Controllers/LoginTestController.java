package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginTestController {
    @FXML
    private Button ExitButton;

    @FXML
    private Label displaynameText, usernameText;

    @FXML
    public void initialize() {
        displaynameText.setText(IOController.currentUser.getDisplayName());
        usernameText.setText(IOController.currentUser.getUsername());
    }

    public void ExitButtonEvent() {
        Stage stage = (Stage)ExitButton.getScene().getWindow();
        stage.close();
    }

}
