package com.GrPro.streamService.Model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Media implements Serializable {


    @Serial
    private static final long serialVersionUID = 222L;
    private String title = "Title not set";
    private String mediaImage = "src/main/resources/Assets/CustomMediaImages/placeholder.jpg";
    private List<String> categories;
    private double rating;
    private int releaseYear;
    protected String typeOfMedia;
    private Boolean favoriteOfCurrentUser = false;

    private File mediaFile = new File("MediaFiles/doggoTestVid.mp4");



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

    public Boolean getFavoriteOfCurrentUser() {
        return favoriteOfCurrentUser;
    }

    public File getMediaFile() {
        return mediaFile;
    }








    public void setMediaFile(String newMediaFile) {
        mediaFile = new File("MediaFiles/" + newMediaFile);
    }
    public void setFavoriteOfCurrentUser(Boolean favoriteOfCurrentUser) {
        this.favoriteOfCurrentUser = favoriteOfCurrentUser;
    }


    public void setCategories(List<String> newCategories) {
        //Ekstra
        categories = newCategories;
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