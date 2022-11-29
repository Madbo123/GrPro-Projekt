package com.GrPro.streamService.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {

    //serialVersionUID garanterer at user-filer kan læses på trods af ændringer til klassen, eller skift af computer.
    //Kun ændr hvis der laves store ændringer til user klassen.
    @Serial
    private static final long serialVersionUID = 111L;
    private boolean isAdmin;
    private final String Id;
    public String displayname;
    private String username;
    private String password;
    private final List<Media> favorites;

    public User(String displayname, String username, String password) {
        isAdmin = false;
        Id = generateId();
        favorites = new ArrayList<>();
        this.displayname = displayname;
        this.username = username;
        this.password = password;
    }

    //UUID gen og getters
    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 12);
    }
    public String getDisplayName() {
        return displayname;
    }

    public String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }
    public String getId() {
        return Id;
    }


    //Favorites
    public List<Media> getFavorites() {
        return favorites;
    }

    public void addFavorite(Media media) {
        favorites.add(media);
    }

    public void removeFavorite(Media media) {
        favorites.remove(media);
    }


    //Setters
    public void setUsername(String new_username) {
        username = new_username;
        //save_credentials();
        System.out.println("New Username has been set.");
    }

    public void setPassword(String new_password) {
        password = new_password;
        System.out.println("New Password has been set.");
    }

    public void setDisplayname(String new_displayname) {
        displayname = new_displayname;
        System.out.println("New displayname has been set.");
    }
}
