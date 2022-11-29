package com.GrPro.streamService.GUI;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

public class PlayerLauncher extends Application {

    VideoPlayer videoPlayer;
    FileChooser fileChooser;

    public void start(final Stage primaryStage) {

        MenuItem open = new MenuItem("Open");
        Menu file = new Menu("File");
        MenuBar menu = new MenuBar();

        file.getItems().add(open);
        menu.getMenus().add(file);

        fileChooser = new FileChooser();

        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                videoPlayer.videoPlayer.pause();
                File file = fileChooser.showOpenDialog(primaryStage);

                if (file != null) {
                    try {
                        videoPlayer = new VideoPlayer(file.toURI().toURL().toExternalForm());
                        Scene scene = new Scene(videoPlayer, 720, 535, Color.BLACK);
                        primaryStage.setScene(scene);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        videoPlayer = new VideoPlayer("doggo.mp4");
        videoPlayer.setTop(menu);
        Scene scene = new Scene(videoPlayer, 720, 535, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}