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
        Thread window = new Thread(new GameWindow(stats, foodList));
        window.start();
        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();

        NewFrend frend = new NewFrend(stats);
        Thread clock = new Thread(new Clock(stats, frend));
        clock.start();
        Thread logic = new Thread(new Logic(stats));
        logic.start();
        Thread coordinator = new Thread(new Coordinator(stats, foodList));
        coordinator.start();
    }
}