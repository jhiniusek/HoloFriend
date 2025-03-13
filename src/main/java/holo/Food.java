package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Food extends JFrame{
    private Point initialClick;

    public Food(int x, int y) {
        setUndecorated(true); // Remove default title bar
        setSize(16, 16);
        setLocation(x, y);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/Burger.png")));
        add(sprite);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });

        setVisible(true);
    }
}
