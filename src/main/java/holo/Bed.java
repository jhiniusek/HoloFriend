package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bed extends JFrame {
    private Point initialClick;
    public boolean wakeup = false;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/Bed.png")));
    JButton cancel = new JButton();

    public Bed(FrendStats stats) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(182,83);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        JLabel shadow = new JLabel(new ImageIcon(getClass().getResource("/sprites/BedShadow.png")));
        shadow.setBounds(0, 0, 182, 83);
        add(shadow);
        sprite.setBounds(0, 0, 182, 83);
        add(sprite);

        cancel.setBounds(135, 10, 35, 35);
        cancel.setContentAreaFilled(false);
        cancel.setBorderPainted(false);
        cancel.setFocusPainted(false);
        cancel.setOpaque(false);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("FORCED STOPPED");
                wakeup = true;
                remove(cancel);
            }
        });

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
                stats.bedPositionX = X;
                stats.bedPositionY = Y;
            }
        });
        setVisible(stats.getBed() == 1);
        setLocation(stats.bedPositionX, stats.bedPositionY);
    }

    public void makeForceableStop(){
        add(cancel);
    }

    public void removeForcableStop(){
        remove(cancel);
    }
}