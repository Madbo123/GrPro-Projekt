package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class FilterController {

    static String searchWord = "", type = "", genre = "";

    //Genres skal nok have mulighed for flere inputs.
    static List<String> genres = new ArrayList<>();
    static double ratingMin = 0, ratingMax = 0;
    static int releaseYearMin = 0, releaseYearMax = 0;
    static boolean fTitle, fType, fGenre, fRating, fRelease;

    public static List<Media> FilterMedia() {
        List<Media> copiedMediaList = new ArrayList<>(Singleton.getInstance().getMediaList());
        ValidateParameters();

        return ApplyAll(copiedMediaList);
    }


    public static List<Media> ApplyAll(List<Media> copiedMediaList) {

        if (fTitle) {
            FilterByTitle(copiedMediaList);
            System.out.println("Applied Filter SearchWord: " + searchWord);
        }
        if (fType) {
            FilterByType(copiedMediaList);
            System.out.println("Applied Filter Type: " + type);
        }
        if (fGenre) {
            FilterByGenre(copiedMediaList);
            System.out.println("Applied Filter Genre: " + genre);
        }
        if (fRating) {
            FilterByRating(copiedMediaList);
            System.out.println("Applied Filter Rating: " + ratingMin + " - " + ratingMax);
        }
        if (fRelease) {
            FilterByRelease(copiedMediaList);
            System.out.println("Applied Filter Release: " + releaseYearMin + " - " + releaseYearMax);
        }

        return copiedMediaList;
    }

    public static void ValidateParameters() {
        fTitle = searchWord != null && !searchWord.equals("");
        fType = type.equals("Movie") || type.equals("Serie");
        fGenre = genre != null && !genre.equals("");
        fRating = ratingMax >= ratingMin && ratingMax > 0 && ratingMax <= 10;
        fRelease = releaseYearMax >= releaseYearMin && releaseYearMin >= 0 && releaseYearMax > 0;
    }


    public static void FilterByTitle(List<Media> copiedMediaList) {

        copiedMediaList.removeIf(media -> !media.getTitle().toLowerCase().contains(searchWord.toLowerCase()));
    }

    public static void FilterByType(List<Media> copiedMediaList) {

        copiedMediaList.removeIf(media -> !media.getTypeOfMedia().equalsIgnoreCase(type));
    }

    public static void FilterByGenre(List<Media> copiedMediaList) {

        copiedMediaList.removeIf(media -> !media.getCategories().contains(genre));
    }

    public static void FilterByRating(List<Media> copiedMediaList) {

        //Har vendt checks så de fjerner frem for at tilføje
        copiedMediaList.removeIf(media -> media.getRating() < ratingMin || media.getRating() > ratingMax);
    }

    public static void FilterByRelease(List<Media> copiedMediaList) {

        //Har vendt checks så de fjerner frem for at tilføje
        copiedMediaList.removeIf(media -> media.getReleaseYear() < releaseYearMin || media.getReleaseYear() > releaseYearMax);
    }
}
