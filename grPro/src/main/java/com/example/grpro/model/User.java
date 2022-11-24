package com.example.grpro.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

    String _username;
    String _password;
    List<Media> _favorites;
    public User(String username, String password, List<Media> favorites) {
        this._username = username;
        this._password = password;
        this._favorites = favorites;
    }
}
