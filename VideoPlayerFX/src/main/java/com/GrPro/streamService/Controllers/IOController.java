package com.GrPro.streamService.Controllers;

import com.GrPro.streamService.Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Reading og writing af brugere foregår via OOS og OIS
public class IOController {

    static final String userdataPath = "src/main/resources/Data/Users/";
    static User currentUser;



    //MEGA REDUNDANT ATM
    public static void create_Account(String displayname, String username, String password) {
        UserController.create_user(displayname, username, password);
    }


    public static User load_User(String IdKey) throws IOException, ClassNotFoundException {
        FileInputStream read_user = new FileInputStream(userdataPath + "User-" + IdKey + ".dat");
        ObjectInputStream in_user = new ObjectInputStream(read_user);
        currentUser = (User) in_user.readObject();
        in_user.close();
        read_user.close();
        return currentUser;
    }



    public static void save_User(User user) throws IOException {
        if (!currentUser.getUserRank().equals("Guest")) {
            FileOutputStream write_user = new FileOutputStream(userdataPath + "User-" + user.getId() + ".dat");
            ObjectOutputStream out_user = new ObjectOutputStream(write_user);
            out_user.writeObject(user);
            out_user.close();
            write_user.close();
        }
    }


    public static void save_UUID_Map(String username, String password, String UUID) {
        //Der mangler funktionalitet til at slette en bruger igen.
        if (!currentUser.getUserRank().equals("Guest")) {
            try {
                FileWriter write_UUID = new FileWriter(userdataPath + "UUID_MAP.dat", true);
                write_UUID.append(username + " " + password + " " + UUID + "\n");
                write_UUID.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    //Placeholder
    public static void saveMedia(Media media)  {

    }

    // media load metoder
    public static void loadMedia() throws FileNotFoundException {
        File file0 = new File("src/main/resources/Data/film.txt");
        Scanner s = new Scanner(file0,"UTF8");
        ArrayList<Media> med = new ArrayList<>();

        while(s.hasNextLine()){
            String[] line = s.nextLine().split(";");
            ArrayList<String> arr  = new ArrayList<>();
            for(String element :line[2].split(",")){
                arr.add(element.trim());

            }
            Movie movie = new Movie(line[0],arr,Double.valueOf(line[3].replace(",", ".")),Integer.valueOf(line[1].trim()));
            med.add(movie);
        }

        File file1 = new File("src/main/resources/Data/serier.txt");
        Scanner s1 = new Scanner(file1, "UTF8");
        //Den her bid læser ikke serier.txt
        while(s1.hasNextLine()) {
            String[] line = s1.nextLine().split(";");
            for (int i = 0; i < line.length ; i++) {
                line[i] = line[i].trim();
            }
            ArrayList<String> arr = new ArrayList<>();
            //if (!line[1].contains("-")) line[1] += "-";
            String[] years = line[1].split("-");
            for (String element : line[2].split(",")) {
                arr.add(element.trim());

            }

            ArrayList<Integer> seasons = new ArrayList<>();
            for (String element : line[4].split(",")) {
                element = element.trim();
                seasons.add(Integer.valueOf(element.split("-")[1]));
            }

            ArrayList<String> yearsList = new ArrayList<>(List.of(years));
            if ( yearsList.size() == 1 || yearsList.get(1).equals("")) yearsList.add(1, "-1");
            Serie serie = new Serie(line[0], arr, Double.valueOf(line[3].replace(",", ".")), Integer.valueOf(yearsList.get(0)), Integer.valueOf(yearsList.get(1)), seasons);
            med.add(serie);
        }
        Singleton.getInstance().insertArray(med);

    }

}
