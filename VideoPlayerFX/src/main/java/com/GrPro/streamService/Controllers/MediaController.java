package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;

import java.util.ArrayList;
import java.util.List;

public class MediaController {

    public static List<Media> FilterByTitle(List<Media> list, String stringToSortBy) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getTitle().toLowerCase().contains(stringToSortBy.toLowerCase())) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByType(List<Media> list, String typeToSortBy) {
        List<Media> filtered = new ArrayList<Media>();

        for (Media m : list) {
            if (m.getTypeOfMedia().equals(typeToSortBy)) {
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
    
    public static List<Media> ApplyFilters(String searchWord, String type, String genre) {
        List<Media> copiedMediaList = Singleton.getInstance().getMedia();

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


}
