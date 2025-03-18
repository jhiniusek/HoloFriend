package holo;

import javax.swing.*;
import java.awt.*;

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
}
