package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shop extends JFrame {
    private Point initialClick;
    private GameWindow mainWindow;
    ImageIcon soldOut = new ImageIcon(getClass().getResource("/sprites/Shop_Sold.png"));

    public Shop(FrendStats stats, Bed bed, Radio radio, Wardrobe wardrobe) {
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
                    bed.setLocation(getLocation().x + 306, getLocation().y + 162);
                    SoldOut(172, 51);
                }

            }
        });
        if(stats.getBed() == 0){
            add(buyBed);
        } else {
            JLabel bedSold = new JLabel(soldOut);
            bedSold.setBounds(172, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(bedSold);
            getContentPane().setComponentZOrder(bedSold, 0);
        }


        JButton buyWardrobe = new JButton();
        buyWardrobe.setBounds(222, 57, 59, 80);
        buyWardrobe.setContentAreaFilled(false);
        buyWardrobe.setBorderPainted(false);
        buyWardrobe.setFocusPainted(false);
        buyWardrobe.setOpaque(false);
        buyWardrobe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=100) {
                    wardrobe.setVisible(true);
                    buyWardrobe.setEnabled(false);
                    stats.setWardrobe(1);
                    stats.setCurrency(stats.getCurrency() - 100);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    wardrobe.setLocation(getLocation().x + 310, getLocation().y + 95);
                    SoldOut(241, 51);
                }

            }
        });
        if(stats.getWardrobe() == 0){
            add(buyWardrobe);
        } else {
            JLabel wardrobeSold = new JLabel(soldOut);
            wardrobeSold.setBounds(241, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(wardrobeSold);
            getContentPane().setComponentZOrder(wardrobeSold, 0);
        }


        JButton buyRadio = new JButton();
        buyRadio.setBounds(84, 157, 59, 80);
        buyRadio.setContentAreaFilled(false);
        buyRadio.setBorderPainted(false);
        buyRadio.setFocusPainted(false);
        buyRadio.setOpaque(false);
        buyRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=200) {
                    radio.setVisible(true);
                    buyRadio.setEnabled(false);
                    stats.setRadio(1);
                    stats.setCurrency(stats.getCurrency() - 200);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    radio.setLocation(getLocation().x + 310, getLocation().y + 195);
                    SoldOut(103, 151);
                }

            }
        });
        if(stats.getRadio() == 0){
            add(buyRadio);
        } else {
            JLabel radioSold = new JLabel(soldOut);
            radioSold.setBounds(103, 151, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(radioSold);
            getContentPane().setComponentZOrder(radioSold, 0);
        }


        setVisible(false);
    }

    public void setMainWindow(GameWindow window){
        this.mainWindow = window;
    }

    public void SoldOut(int x, int y){
        JLabel sold = new JLabel(soldOut);
        sold.setBounds(x, y, soldOut.getIconWidth(), soldOut.getIconHeight());
        add(sold);
        getContentPane().setComponentZOrder(sold, 0);
        repaint();
    }
}