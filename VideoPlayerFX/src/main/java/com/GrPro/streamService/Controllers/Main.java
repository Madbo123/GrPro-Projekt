package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Singleton;
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
        Parent root = FXMLLoader.load(getClass().getResource("MediaAboutScreen.fxml"));
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1400,800);
        startStage.setScene(scene);
        startStage.show();
    }

    public static void main(String[] args) { launch(args);}
}

