package holo;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Screen {
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    public Screen(JFrame frame) {
        xMin = frame.getLocation().x;
        xMax = xMin + frame.getSize().width;
        yMin = frame.getLocation().y;
        yMax = yMin + frame.getSize().height;
    }

    Point getRandomPoint(){
        int x = (int)(Math.random() * (xMax - xMin + 1) + xMin);
        int y = (int)(Math.random() * (yMax - yMin + 1) + yMin);
        if(x > 64){
            x = x - 64;
        }
        if(y > 128){
            y = y - 128;
        }
        Point point = new Point();
        point.setLocation(x, y);

        System.out.println("NEW DESTINATION: " + point);
        return point;
    }

    int getRandomY(){
        int randomY = ThreadLocalRandom.current().nextInt(yMin, yMax + 1);
        if(randomY > 128){
            randomY = randomY - 128;
        }
        return randomY;
    }

    public boolean checkIfVisible(Point point){
        return(xMin <= point.x && point.x <= xMax -1 && yMin <= point.y && point.y <= yMax);
    }
}
