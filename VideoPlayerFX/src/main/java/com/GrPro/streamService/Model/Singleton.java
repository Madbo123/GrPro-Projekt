package com.GrPro.streamService.Model;

import java.util.ArrayList;

public class Singleton {
    private static Singleton singleton = null;
    private ArrayList<Media> listOfMedia;
    private Singleton() {

    }

    public static Singleton getInstance() {
        if (singleton == null) singleton = new Singleton();
        return singleton;
    }

    public void insertArray(ArrayList<Media> list) {
        listOfMedia = list;
    }

    public ArrayList getMedia() {
        return listOfMedia;
    }
}
