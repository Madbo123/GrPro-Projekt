package com.GrPro.streamService.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage startStage) throws Exception {
        IOController.loadMedia();
        //System.out.println(MediaController.ApplyAllFilters("", "", "", 0,0,0,0));
        //System.out.println(MediaController.getAllGenres());
//        URL url = new File("VideoPlayerFX/src/main/resources/com/GrPro/streamService/Controllers/MediaScreen.fxml").toURI().toURL();
//        Parent root = FXMLLoader.load(url);
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 900, 600);
        startStage.setScene(scene);
        startStage.show();
    }

    public static void main(String[] args) { launch(args);}
}
