package com.GrPro.streamService.Model;

import java.util.ArrayList;

public class Singleton {
    private static Singleton singleton = null;
    private ArrayList<Media> listOfMedia;
    private Singleton(ArrayList<Media> list) {
        listOfMedia = list;
    }

    public static Singleton getInstance() {
        if (singleton == null) singleton = new Singleton(load);
        return singleton;
    }
}
