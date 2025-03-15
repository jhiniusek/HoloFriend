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
        Point point = new Point();
        point.setLocation((int)(Math.random() * (xMax - xMin + 1) + xMin), (int)(Math.random() * (yMax - yMin + 1) + yMin));
        System.out.println("NEW DESTINATION: " + point);
        return point;
    }
}
