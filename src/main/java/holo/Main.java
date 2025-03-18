package holo;

import javax.management.remote.NotificationResult;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        FrendStats stats = new FrendStats();
        ArrayList<Food> foodList= new ArrayList<>();
        for(int i = 0; i < 5; i++){
            foodList.addFirst(null);
        }



        GameWindow window = new  GameWindow(stats, foodList);
        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();

        NewFrend frend = new NewFrend(stats);
        Thread clock = new Thread(new Clock(stats, frend, window));
        clock.start();
        Thread logic = new Thread(new Logic(stats));
        logic.start();
        Thread coordinator = new Thread(new Coordinator(stats, foodList, window));
        coordinator.start();
    }
}