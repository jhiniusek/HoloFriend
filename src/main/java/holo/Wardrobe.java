package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Wardrobe extends JFrame {
    private Point initialClick;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/Wardrobe.png")));

    public Wardrobe(FrendStats stats) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(91,153);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        sprite.setBounds(0, 0, 91, 153);
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
                stats.wardrobePositionX = X;
                stats.wardrobePositionY = Y;
            }
        });

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                stats.changeSkin();
            }
        });

        setVisible(stats.getWardrobeOwned() == 1);
        setLocation(stats.wardrobePositionX, stats.wardrobePositionY);
    }
}