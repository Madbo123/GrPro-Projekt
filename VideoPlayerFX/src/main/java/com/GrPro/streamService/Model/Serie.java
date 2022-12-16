package com.GrPro.streamService.Model;

import java.io.File;
import java.util.List;

public class Serie extends Media {
    private int endedYear;
    private List<Integer> season;

    private List<File> episodes;

    public Serie(String title, List<String> categories, double rating, int releaseYear, int endedYear, List<Integer> season) {
        super(title, categories, rating, releaseYear);
        typeOfMedia = "Serie";
        this.endedYear = endedYear;
        this.season  = season;
    }

    public List<Integer> getSeasons() { return season; }

    //Ikke funktionel, da vi ikke rent faktisk har mediefiler til serierne.
    public List<File> getEpisodes() {
        return episodes;
    }
    public int getEndedYear() { return endedYear; }


    //Den her skulle anvendes inden i en funktion til sæsoner der ville have en liste af lister.
    public File getEpisodeByIndex(int index) {
        return episodes.get(index);
    }
}
