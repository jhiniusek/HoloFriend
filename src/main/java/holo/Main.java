package holo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, FontFormatException {

        FrendStats stats = new FrendStats();

        File saveFile = new File("save.txt");
        if(saveFile.createNewFile()){
        } else {
            stats.load(saveFile);
        }

        ArrayList<Food> foodList= new ArrayList<>();
        for(int i = 0; i < 6; i++){
            foodList.addFirst(null);
        }

        Bed bed = new Bed(stats);
        Radio radio = new Radio(stats);
        Wardrobe wardrobe = new Wardrobe(stats);

        Shop shop = new Shop(stats, bed, radio, wardrobe);
        GameWindow window = new  GameWindow(stats, foodList, shop);
        shop.setMainWindow(window);

        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();

        Frend frend = new Frend(stats);
        Lake lake = new Lake(window.getLocation().x - 270,window.getLocation().y + 100);

        Thread clock = new Thread(new Clock(stats, frend, window, foodList, lake, bed));
        clock.start();





    }
}