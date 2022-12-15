package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.Singleton;
import com.GrPro.streamService.Model.User;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserController {

    //Opret brugeren som "User"
    public static void create_user(String displayname, String username, String password) {

        //Myrder ikke det her objekt asap, det *kan* være et problem i fremtiden.
        setUser(new User(displayname, username, password, "User"));

        try {
            IOController.save_User(getCurrentUser());
            IOController.save_UUID_Map(username, password, getCurrentUser().getId());
        } catch (IOException ioException) {
            System.out.println("Error accessing data folder or saving userdata.");
        }
    }


    //Kompenserer for spaghetti kode.
    public static void currentUserNullFailsafe() {
        if (getCurrentUser() == null) {
            setUser(new User("Guest - Failsafe", "Guest", "Guest", "Guest"));
            System.out.println("Failsafe Triggered. -- FIX THIS --");
        }
    }


    //Burde være en subklasse probs. Masse problemer med saving som skal tages højde for PT.
    public static void login_as_guest() {
        setUser(new User("Guest", "Guest", "Guest", "Guest"));
        System.out.println("Logged in as guest");
    }

    //Getter og setter, forkorter bare opskrivningen en smule.
    public static User getCurrentUser() {
        return Singleton.getInstance().getUser();
    }


    public static void setUser(User user) {
        Singleton.getInstance().setUser(user);
    }
}
