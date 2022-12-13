package com.GrPro.streamService.Model;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public abstract class Media {
    private String title = "Title not set";
    private String mediaImage = "src/main/resources/Assets/CustomMediaImages/placeholder.jpg";
    private List<String> categories;
    private double rating;
    private int releaseYear;
    protected String typeOfMedia;



    public Media(String title, List<String> categories, double rating, int releaseYear) {
        this.title = title;
        this.categories = categories;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }




    public List<String> getCategories() {return categories;}

    public String getTitle() {return title;}

    public String getMediaImage() {return mediaImage;}

    public double getRating() {return rating;}

    public int getReleaseYear() {return releaseYear;}

    public String getTypeOfMedia() {return typeOfMedia;}






    public void setCategories() {
        //Ekstra
    }

    public void setTitle(String newTitle) {
        //Ekstra
        title = newTitle;
    }

    public void setMediaImage(String newImagePath) {
        mediaImage = newImagePath;
    }

    public void setReleaseYear(int newReleaseYear) {
        //Ekstra
        releaseYear = newReleaseYear;
    }


    @Override
    public java.lang.String toString() {
        return title + "," + categories + "," + typeOfMedia;
    }
}