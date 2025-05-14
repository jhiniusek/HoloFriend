package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lake extends JFrame {
    private Point initialClick;
    public boolean forceStop = false;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/Lake.png")));
    JButton cancel = new JButton();

    public Lake(int x, int y) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(261,217);
        setLayout(null);
        setLocation(x, y);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        sprite.setBounds(0, 0, 261, 217);
        add(sprite);

        cancel.setBounds(30, 50, 50, 128);
        cancel.setContentAreaFilled(false);
        cancel.setBorderPainted(false);
        cancel.setFocusPainted(false);
        cancel.setOpaque(false);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forceStop = true;
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
            }
        });

        setVisible(true);
    }

    public void makeForceableStop(){
        add(cancel);
        setAlwaysOnTop(true);
    }

    public void removeForcableStop(){
        remove(cancel);
        setAlwaysOnTop(false);
    }
}