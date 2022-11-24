package com.example.grpro.model;

import java.util.List;
import java.util.ArrayList;

public class Serie extends Media {
    int _endedYear;
    List<Integer> _season;

    public Serie(String title, List<String> categories, double rating, int releaseYear) {
        super(title, categories, rating, releaseYear);
    }
}
