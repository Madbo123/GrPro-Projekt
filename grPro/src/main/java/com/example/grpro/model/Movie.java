package com.example.grpro.model;

import java.util.List;

public class Movie extends Media {

    public Movie(String title, List<String> categories, double rating, int releaseYear) {
        super(title, categories, rating, releaseYear);
    }

}
