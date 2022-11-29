package com.GrPro.streamService.Model;

import java.util.List;

public abstract class Media {
    String _title = "Title not set";
    List<String> _categories;
    double _rating;
    int _releaseYear;

    public Media(String title, List<String> categories, double rating, int releaseYear) {
        this._title = title;
        this._categories = categories;
        this._rating = rating;
        this._releaseYear = releaseYear;
    }

}