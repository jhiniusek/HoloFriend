package holo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        FrendStats stats = new FrendStats();
        ArrayList<Food> foodList= new ArrayList<>();
        for(int i = 0; i < 6; i++){
            foodList.addFirst(null);
        }

        GameWindow window = new  GameWindow(stats, foodList);

        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();

        Frend frend = new Frend(stats);
        Thread clock = new Thread(new Clock(stats, frend, window, foodList));
        clock.start();
        Thread logic = new Thread(new Logic(stats));
        logic.start();
    }
}