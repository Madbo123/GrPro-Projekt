package com.GrPro.streamService.Model;

import java.util.List;

public class Serie extends Media {
    private int _endedYear;
    private List<Integer> _season;

    public Serie(String title, List<String> categories, double rating, int releaseYear, int _endedYear, List<Integer> _season) {
        super(title, categories, rating, releaseYear);
        typeOfMedia = "Serie";
        this._endedYear = _endedYear;
        this._season  = _season;
    }

    public List<Integer> getSeasons() { return _season; }
    public int getEndedYear() { return _endedYear; }
}
