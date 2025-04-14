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

        GameWindow window = new  GameWindow(stats, foodList);

        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();

        Frend frend = new Frend(stats);
        Lake lake = new Lake(500,500);
        Bed bed = new Bed(0,0);
        Thread clock = new Thread(new Clock(stats, frend, window, foodList, lake, bed));
        clock.start();



    }
}