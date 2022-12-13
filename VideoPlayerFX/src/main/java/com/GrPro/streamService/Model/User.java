package com.GrPro.streamService.Model;

import javafx.scene.image.Image;

import java.io.File;
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


    //Midlertidigt lavet "rank" om til string.
    private String userRank;

    private final String Id;

    //Default userImg - Kan måske sættes til en kat senere?
    private String userImg = "src/main/resources/Assets/CustomMediaImages/placeholder.jpg";
    public String displayname;
    private String username;
    private String password;
    private final List<Media> favorites;


    public User(String displayname, String username, String password, String userRank) {
        Id = generateId();
        favorites = new ArrayList<>();
        this.displayname = displayname;
        this.username = username;
        this.password = password;
        this.userRank = userRank;
    }


    //UUID gen og getters
    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    public String getUserImg() {
        return new File(userImg).toURI().toString();
    }

    public String getDisplayName() {
        return displayname;
    }

    public String getUsername() {
        return username;
    }
    public String getId() {
        return Id;
    }

    public String getPermissions() {
        return userRank;
    }

    //Burde aldrig være behov for den her.
    private String getPassword() {
        return password;
    }



    //Setters
    public void setUserImg(String new_userImg) {
        userImg = new_userImg;
    }

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

    public void setUserRank(String new_userRank) {
        userRank = new_userRank;
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
}
