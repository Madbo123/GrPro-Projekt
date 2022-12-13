package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.User;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.GrPro.streamService.Controllers.IOController.currentUser;

public class UserController {

    //Opret brugeren som "User"
    public static void create_user(String displayname, String username, String password) {

        //Myrder ikke det her objekt asap, det *kan* være et problem i fremtiden.
        User user = new User(displayname, username, password, "User");

        try {
            IOController.save_User(user);
            IOController.save_UUID_Map(username, password, user.getId());
        } catch (IOException ioException) {
            System.out.println("Error accessing data folder or saving userdata.");
        }
    }


    //Kompenserer for spaghetti kode.
    public static void currentUserNullFailsafe() {
        if (currentUser == null) {
            currentUser = new User("GuestFailsafe", "Guest", "", "Guest");
            System.out.println("Failsafe Triggered. -- FIX THIS --");
        }
    }


    //Burde være en subklasse probs. Masse problemer med saving som skal tages højde for PT.
    public static void login_as_guest() {
        User guest = new User("Guest", "", "", "Guest");
        currentUser = guest;
        System.out.println("Logged in as guest");
    }


}
