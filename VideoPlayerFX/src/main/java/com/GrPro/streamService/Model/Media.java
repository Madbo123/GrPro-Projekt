package com.GrPro.streamService.Model;

import java.util.List;

public abstract class Media {
    private String _title = "Title not set";
    private List<String> _categories;
    private double _rating;
    private int _releaseYear;
    protected String typeOfMedia;

    public Media(String title, List<String> categories, double rating, int releaseYear) {
        this._title = title;
        this._categories = categories;
        this._rating = rating;
        this._releaseYear = releaseYear;
    }

    public List<String> getCategories() {return _categories;}

    public String getTitle() {return _title;}

    public double getRating() {return _rating;}

    public int getReleaseYear() {return _releaseYear;}

    public String getTypeOfMedia() {return typeOfMedia;}


    @Override
    public java.lang.String toString() {
        return _title + "," + _categories + "," + typeOfMedia;
    }
}