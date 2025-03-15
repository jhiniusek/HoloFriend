package holo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        FrendStats stats = new FrendStats();
        ArrayList<Food> foodList= new ArrayList<>();
        for(int i = 0; i < 5; i++){
            foodList.addFirst(null);
        }
        Thread fubuki = new Thread(new Frend(stats));
        fubuki.start();
        Thread window = new Thread(new GameWindow(stats, foodList));
        window.start();
        //Thread cursor = new Thread(new Cursor(stats));
        //cursor.start();
        Thread animator = new Thread(new Animator(stats));
        animator.start();
        Thread clock = new Thread(new BGClock(stats));
        clock.start();
        Thread logic = new Thread(new Logic(stats));
        logic.start();
        Thread coordinator = new Thread(new Coordinator(stats, foodList));
        coordinator.start();
    }
}