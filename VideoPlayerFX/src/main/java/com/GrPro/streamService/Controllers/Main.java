package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Utility.Utilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.GrPro.streamService.Controllers.MediaScreenController.currentController;
import static com.GrPro.streamService.Utility.Utilities.centerStage;


public class Main extends Application {

    @Override
    public void start(Stage startStage) throws Exception {
        IOController.loadMedia();
        FXMLLoader loadStart = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loadStart.load();
        //currentController(loadStart.getController()); Til test via MediaScreen.fxml
        startStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        startStage.setScene(scene);
        startStage.show();
        centerStage(startStage);
    }

    public static void main(String[] args) { launch(args);}
}

