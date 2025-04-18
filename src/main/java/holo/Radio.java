package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Radio extends JFrame {
    private Point initialClick;
    JLabel lcd = new JLabel(new ImageIcon(getClass().getResource("/sprites/RadioLCD_NoDisk.gif")));

    public Radio(FrendStats stats) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(76,40);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        lcd.setBounds(0, 0, 76, 40);
        add(lcd);

        JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/Radio.png")));
        sprite.setBounds(0, 0, 76, 40);
        add(sprite);

        getContentPane().setComponentZOrder(lcd, 0);

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
                stats.radioPositionX = X;
                stats.radioPositionY = Y;
            }
        });
        setVisible(true);
        setVisible(stats.getRadio() == 1);
        setLocation(stats.radioPositionX, stats.radioPositionY);
    }
}