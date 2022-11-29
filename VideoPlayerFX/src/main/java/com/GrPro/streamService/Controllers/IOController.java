package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.User;

import java.io.*;

//Reading og writing af brugere foregår via OOS og OIS
public class IOController {
    //Denne path tager dig direkte til "Users" inde i data folderen. Navngiv filer ved "path" + "/NAVN"

    private static final String dataPath = "src/main/java/com/GrPro/streamService/Data/Users/";

    //MEGA REDUNDANT ATM
    public static void create_Account(String displayname, String username, String password) {
        UserController.create_user(displayname, username, password);
    }

    public static void save_User(User user) throws IOException {
        FileOutputStream write_user = new FileOutputStream(dataPath + "User-" + user.getId() + ".dat");
        ObjectOutputStream out_user = new ObjectOutputStream(write_user);
        out_user.writeObject(user);
        out_user.close();
        write_user.close();
    }


    private static User load_User(String IdKey) throws IOException, ClassNotFoundException {
        FileInputStream read_user = new FileInputStream(dataPath + "User-" + IdKey + ".dat");
        ObjectInputStream in_user = new ObjectInputStream(read_user);
        User currentUser = (User) in_user.readObject();
        in_user.close();
        read_user.close();
        return currentUser;
    }




    public static void save_UUID_Map(String username, String UUID) {
        String appendMap = username + " " + UUID + "\n";
        try {
            FileWriter write_UUID = new FileWriter(dataPath + "UUID_MAP.dat", true);
            write_UUID.append(appendMap);
            write_UUID.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
