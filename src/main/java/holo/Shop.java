package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shop extends JFrame {
    private Point initialClick;
    private GameWindow mainWindow;

    public Shop(FrendStats stats, Bed bed) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(306,262);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/sprites/Shop.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel);


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


        JButton closeShop = new JButton();
        closeShop.setBounds(251, 5, 40, 40);
        closeShop.setContentAreaFilled(false);
        closeShop.setBorderPainted(false);
        closeShop.setFocusPainted(false);
        closeShop.setOpaque(false);
        closeShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(closeShop);


        JButton buyBed = new JButton();
        buyBed.setBounds(153, 57, 59, 80);
        buyBed.setContentAreaFilled(false);
        buyBed.setBorderPainted(false);
        buyBed.setFocusPainted(false);
        buyBed.setOpaque(false);
        buyBed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=350) {
                    bed.setVisible(true);
                    buyBed.setEnabled(false);
                    stats.setBed(1);
                    stats.setCurrency(stats.getCurrency() - 350);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    bed.setLocation(getLocation().x + 306, getLocation().y + 262);
                }

            }
        });
        add(buyBed);

        setVisible(false);
    }

    public void setMainWindow(GameWindow window){
        this.mainWindow = window;
    }
}