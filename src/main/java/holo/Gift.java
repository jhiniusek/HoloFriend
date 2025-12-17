package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Gift extends JFrame{
    private Point initialClick;
    private boolean isHolding = false;
    private String name;
    private String price;

    public boolean isHolding() {
        return isHolding;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Gift(String name, String price) {
        this.name = name;
        this.price = price;
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(20, 24);
        setAlwaysOnTop(true);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/gifts/"+ name +".png")));
        add(sprite);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                isHolding = true;
            }

            public void mouseReleased(MouseEvent e) {
                isHolding = false;
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
