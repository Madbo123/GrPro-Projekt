package com.GrPro.streamService.Model;

import java.util.List;

public class Serie extends Media {
    int endedYear;
    List<Integer> season;

    public Serie(String title, List<String> categories, double rating, int releaseYear, int _endedYear, List<Integer> _season) {
        super(title, categories, rating, releaseYear);
        typeOfMedia = "Serie";
        this.endedYear = _endedYear;
        this.season  = _season;
    }
}
