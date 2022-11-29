package com.GrPro.streamService.Model;

import java.util.List;

public class Serie extends Media {
    int _endedYear;
    List<Integer> _season;

    public Serie(String title, List<String> categories, double rating, int releaseYear) {
        super(title, categories, rating, releaseYear);
        typeOfMedia = "Serie";
    }
}
