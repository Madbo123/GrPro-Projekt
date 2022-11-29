package com.GrPro.streamService.GUI;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;


public class MediaBar extends HBox {

    Slider timeBar = new Slider();
    Slider volBar = new Slider();
    Button playButton = new Button("||");
    Label vol = new Label("Volume: ");
    MediaPlayer player;

    public MediaBar(MediaPlayer player) {
        this.player = player;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 10, 5, 10));
        volBar.setPrefWidth(70); volBar.setMinWidth(30); volBar.setValue(100);
        HBox.setHgrow(timeBar, Priority.ALWAYS);
        playButton.setPrefWidth(30);

        getChildren().add(playButton);
        getChildren().add(timeBar);
        getChildren().add(volBar);
        getChildren().add(vol);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Status status = player.getStatus();

                if (status == status.PLAYING) {
                    if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {
                        player.seek(player.getStartTime());
                        player.play();
                    } else {
                        player.pause();
                        playButton.setText(">");
                    }
                }
                if (status == status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                    player.play();
                    playButton.setText("||");
                }
            }
        });

        player.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable obs) {
                updateVals();
            }
        });

        timeBar.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable obs) {
                if (timeBar.isPressed()) {
                    player.seek(player.getMedia().getDuration().multiply(timeBar.getValue() / 100));
                }
            }
        });

        volBar.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable obs) {
                if (volBar.isPressed()) {
                    player.setVolume(volBar.getValue() / 100);
                }
            }
        });
    }

    protected void updateVals() {
        Platform.runLater(new Runnable() {
            public void run() {
                timeBar.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
            }
        });
    }
}
