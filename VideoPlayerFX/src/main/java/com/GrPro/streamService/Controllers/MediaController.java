package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaController {

    public static List<Media> FilterByTitle(List<Media> list, String searchWord) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getTitle().toLowerCase().contains(searchWord.toLowerCase())) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByType(List<Media> list, String typeToSortBy) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getTypeOfMedia().toLowerCase().equals(typeToSortBy.toLowerCase())) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByGenre(List<Media> list, String genreToSortBy) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getCategories().contains(genreToSortBy)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByRating(List<Media> list, double min, double max) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getRating() > min && m.getRating() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }

    public static List<Media> FilterByRelease(List<Media> list, int min, int max) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getReleaseYear() > min && m.getReleaseYear() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }
    
    public static List<Media> ApplyFilters(String searchWord, String type, String genre) {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMedia());

        if (searchWord != null && !searchWord.equals("")) {
            copiedMediaList = FilterByTitle(copiedMediaList, searchWord);
        }
        if (type.equals("Movie")  || type.equals("Serie")) {
            copiedMediaList = FilterByType(copiedMediaList, type);
        }
        if (genre != null && !genre.equals("")) {
            copiedMediaList = FilterByGenre(copiedMediaList, genre);
        }


        return copiedMediaList;
    }


    public static List<String> getAllGenres() {

        List<String> genres = new ArrayList<>();
        try {

            List<Media> copiedMediaList = new ArrayList(Singleton.getInstance().getMedia());

            for (Media m : copiedMediaList) {
                for (String s : m.getCategories()) {
                    if (!genres.contains(s)) genres.add(s);
                }
            }

            Collections.sort(genres);

        } catch (NullPointerException e) {
            System.out.println("Error at MediaController. getAllGenres(). Message: " + e.getMessage());
        }

        return genres;
    }

}
