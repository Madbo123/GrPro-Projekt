package com.GrPro.streamService.Controllers;


import com.GrPro.streamService.Model.Media;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static com.GrPro.streamService.Utility.Utilities.*;

public class MediaPlayerController {

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView playStatus, exitVideoButton;
    @FXML
    private Label mediaName, volLabel;
    @FXML
    private Slider timeSlider, volumeSlider;


    private Scene savedScene;

    private Boolean isPlaying = true;

    private javafx.scene.media.Media mediaPlayable;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File file;



    public void initializeVideoPlayer(Media media, Scene scene) {
        this.media = media;
        savedScene = scene;
        initMedia();
        mediaName.setText(media.getTitle());

        mediaPlayer.setVolume(0.30);
        volLabel.setText("" + Math.round(mediaPlayer.getVolume() * 100));
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                volLabel.setText("" + Math.round(mediaPlayer.getVolume() * 100));
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                timeSlider.setValue(t1.toSeconds());
            }
        });

        timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(timeSlider.getValue()));
            }
        });

        timeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(timeSlider.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = mediaPlayable.getDuration();
                timeSlider.setMax(total.toSeconds());
            }
        });


        mediaPlayer.play();
    }



    public void exitVideo(MouseEvent event) throws IOException {
        mediaPlayer.pause();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(savedScene);
        stage.show();
        centerStage(stage);
    }

    public void initMedia() {
        mediaPlayable = new javafx.scene.media.Media(media.getMediaFile().toURI().toString());
        mediaPlayer = new MediaPlayer(mediaPlayable);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    public void setMedia(Media media) {
        this.media = media;
    }


    public void playPauseButton(MouseEvent event) {
        if (isPlaying) {
            mediaPlayer.pause();
            enableNode(playStatus);
            isPlaying = false;
        } else {
            mediaPlayer.play();
            disableNode(playStatus);
            isPlaying = true;
        }


    }

}
