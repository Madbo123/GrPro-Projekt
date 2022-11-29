package com.GrPro.streamService.GUI;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class VideoPlayer extends BorderPane {

    Media video;
    MediaPlayer videoPlayer;
    MediaView videoView;

    File file;
    Pane pane;
    MediaBar bar;

    public VideoPlayer(String media) {
        file = new File("src/main/java/com/GrPro/streamService/assets/" + media);
        video = new Media(file.toURI().toString());
        videoPlayer = new MediaPlayer(video);
        videoView = new MediaView(videoPlayer);

        pane = new Pane();
        pane.getChildren().add(videoView);
        bar = new MediaBar(videoPlayer);

        setCenter(pane);
        setBottom(bar);
        setStyle("-fx-background-color:#bfc2c7");

        videoPlayer.play();
    }
}