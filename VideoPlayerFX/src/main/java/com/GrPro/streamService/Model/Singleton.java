package com.GrPro.streamService.Model;

import java.util.ArrayList;

public class Singleton {
    private static Singleton singleton = null;
    private ArrayList<Media> listOfMedia;
    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) singleton = new Singleton();
        return singleton;
    }
    public void insertArray(ArrayList<Media> list) {
        listOfMedia = list;
    }


    //Ændret til mediaList da getMedia() kan være uklart.
    public ArrayList getMediaList() {
        return listOfMedia;
    }

}
