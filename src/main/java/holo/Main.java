package holo;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, FontFormatException {
        FriendStats stats = new FriendStats();
        stats.Load();
        //stats.LoadDebug();

        ArrayList<Food> foodList= new ArrayList<>();
        for(int i = 0; i < 6; i++){
            foodList.addFirst(null);
        }
        stats.setFoodList(foodList);

        Bed bed = new Bed(stats);
        stats.setBed(bed);
        Radio radio = new Radio(stats);
        stats.setRadio(radio);
        Wardrobe wardrobe = new Wardrobe(stats);
        stats.setWardrobe(wardrobe);
        PC pc = new PC(stats);

        Shop shop = new Shop(stats, bed, radio, wardrobe);
        GameWindow window = new  GameWindow(stats, foodList, shop);
        shop.setMainWindow(window);
        stats.setGameWindow(window);

        Friend friend = new Friend(stats);
        Lake lake = new Lake(window.getLocation().x - 270,window.getLocation().y + 100);
        stats.setLake(lake);

//        CollabNames[] collabNames = CollabNames.values();
//        ArrayList<Collab> listOfCollabs = new ArrayList<>();
//        for (int i = 0; i < collabNames.length; i++) {
//            listOfCollabs.add(new Collab(collabNames[i].name()));
//        }

        Thread clock = new Thread(new Clock(stats, friend, window, foodList, lake, bed));
        clock.start();
    }
}