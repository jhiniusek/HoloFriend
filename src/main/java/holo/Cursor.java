package holo;

import java.awt.*;

public class Cursor implements Runnable {
    private FrendStats stats;

    public Cursor(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            stats.setCursorX((int) MouseInfo.getPointerInfo().getLocation().getX());
            stats.setCursorY((int) MouseInfo.getPointerInfo().getLocation().getY());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
