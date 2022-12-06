package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MediaController {

    public static List<Media> FilterByTitle(List<Media> list, String searchWord) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getTitle().toLowerCase().contains(searchWord.toLowerCase())) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByType(List<Media> list, String typeToSortBy) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getTypeOfMedia().equalsIgnoreCase(typeToSortBy)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByGenre(List<Media> list, String genreToSortBy) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getCategories().contains(genreToSortBy)) {
                filtered.add(m);
            }
        }
        return filtered;
    }

    public static List<Media> FilterByRating(List<Media> list, double min, double max) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getRating() > min && m.getRating() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }

    public static List<Media> FilterByRelease(List<Media> list, int min, int max) {
        List<Media> filtered = new ArrayList<>();

        for (Media m : list) {
            if (m.getReleaseYear() > min && m.getReleaseYear() < max) {
                filtered.add(m);
            }
        }

        return filtered;
    }
    
    public static List<Media> ApplyAllFilters(String searchWord, String type, String genre, double ratingMin, double ratingMax, int releaseYearMin, int releaseYearMax) {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMedia());

        if (searchWord != null && !searchWord.equals("")) {
            copiedMediaList = FilterByTitle(copiedMediaList, searchWord);
            System.out.println("Applied Filter: SearchWord");
        }
        if (type.equals("Movie")  || type.equals("Serie")) {
            copiedMediaList = FilterByType(copiedMediaList, type);
            System.out.println("Applied Filter: Type");
        }
        if (genre != null && !genre.equals("")) {
            copiedMediaList = FilterByGenre(copiedMediaList, genre);
            System.out.println("Applied Filter: Genre");
        }
        if (ratingMax >= ratingMin && ratingMax > 0 && ratingMax <= 10) {
            copiedMediaList = FilterByRating(copiedMediaList, ratingMin, ratingMax);
            System.out.println("Applied Filter: Rating");
        }
        if (releaseYearMax >= releaseYearMin && releaseYearMin >= 0 && releaseYearMax > 0) {
            copiedMediaList = FilterByRelease(copiedMediaList, releaseYearMin, releaseYearMax);
            System.out.println("Applied Filter: Release Year");
        }


        return copiedMediaList;
    }
    @FXML
    FlowPane FP;
    public void ShowMedia(ArrayList<Media> mediaList){

        for( int i = 0; i < mediaList.size();i++ ){

            Image image = new Image("file:///C:/Users/Bruger/Desktop/gruppeProj/GrPro-Projekt/VideoPlayerFX/src/main/resources/Data/filmplakater/filmplakater/" + mediaList.get(i).getTitle() + ".jpg");
            ImageView imv = new ImageView(image);
            FP.getChildren().add(imv);
        }


    }
    public void ClearMedia(){
        FP.getChildren().clear();

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
