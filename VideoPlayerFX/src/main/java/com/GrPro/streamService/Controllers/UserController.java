package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.Media;
import com.GrPro.streamService.Model.User;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserController {

    public static void create_user(String displayname, String username, String password) {

        //Myrder ikke det her objekt asap, det *kan* være et problem i fremtiden.
        User user = new User(displayname, username, password);

        try {
            IOController.save_User(user);
            IOController.save_UUID_Map(user.getUsername(), user.getId());
        } catch (IOException ioException) {
            System.out.println("IO exception");
        }

    }



}
