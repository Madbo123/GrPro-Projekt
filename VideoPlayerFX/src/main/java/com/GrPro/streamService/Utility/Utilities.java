package com.GrPro.streamService.Utility;

import javafx.scene.control.TextField;

public class Utilities {

    //Metoder der ikke passer ind andre steder og kaldes fra mange forskellige steder

    public static String getFieldInput(TextField field) {
        return cleanInput(field.getText());
    }

    public static String cleanInput(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    }
}
