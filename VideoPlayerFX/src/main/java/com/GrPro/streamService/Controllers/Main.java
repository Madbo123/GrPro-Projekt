package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Utility.Utilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.GrPro.streamService.Utility.Utilities.centerStage;


public class Main extends Application {

    @Override
    public void start(Stage startStage) throws Exception {
        IOController.loadMedia();
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        startStage.setScene(scene);
        startStage.show();
        centerStage(startStage);
    }

    public static void main(String[] args) { launch(args);}
}

