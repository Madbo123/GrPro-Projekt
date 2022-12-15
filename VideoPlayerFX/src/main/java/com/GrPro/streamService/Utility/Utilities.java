package com.GrPro.streamService.Utility;

import com.GrPro.streamService.Model.Singleton;
import com.GrPro.streamService.Model.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Utilities {
    static double x, y = 0;

    //Metoder der ikke passer ind andre steder og/eller kaldes fra mange forskellige steder

    public static String getFieldInput(TextField field) {
        return cleanInput(field.getText());
    }

    public static String cleanInput(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    }

    public static void disableNode(Node node) {
        node.setDisable(true);
        node.setVisible(false);
        node.setManaged(false);
    }

    public static void enableNode(Node node) {
        node.setDisable(false);
        node.setVisible(true);
        node.setManaged(true);
    }

    public static void centerStage(Stage stage) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
    }
}
