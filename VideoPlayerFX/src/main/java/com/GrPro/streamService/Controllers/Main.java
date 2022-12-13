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
        IOController.loadMedia();
        Parent root = FXMLLoader.load(getClass().getResource("MediaScreen.fxml"));
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        startStage.setScene(scene);
        startStage.show();
    }

    public static void main(String[] args) { launch(args);}
}

