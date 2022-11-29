package com.GrPro.streamService.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage startStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 900, 600);
        startStage.setScene(scene);
        startStage.show();
    }

    public static void main(String[] args) { launch(args);}
}
