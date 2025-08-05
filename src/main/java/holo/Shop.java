package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shop extends JFrame {
    private Point initialClick;
    private GameWindow mainWindow;
    ImageIcon soldOut = new ImageIcon(getClass().getResource("/sprites/Shop_Sold.png"));
    ImageIcon locked = new ImageIcon(getClass().getResource("/sprites/Shop_Locked.png"));

    public Shop(FriendStats stats, Bed bed, Radio radio, Wardrobe wardrobe) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
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



        JButton buySuperRod = new JButton();
        buySuperRod.setBounds(84, 57, 59, 80);
        buySuperRod.setContentAreaFilled(false);
        buySuperRod.setBorderPainted(false);
        buySuperRod.setFocusPainted(false);
        buySuperRod.setOpaque(false);
        buySuperRod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=500) {
                    buySuperRod.setEnabled(false);
                    stats.setSuperRod(1);
                    stats.setCurrency(stats.getCurrency() - 500);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    SoldOut(103, 51);
                }

            }
        });
        if(stats.getSuperRod() == 0 && stats.getGoodRod() == 1){
            add(buySuperRod);
        } else if (stats.getSuperRod() == 1){
            JLabel superRodSold = new JLabel(soldOut);
            superRodSold.setBounds(103, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(superRodSold);
            getContentPane().setComponentZOrder(superRodSold, 0);
        }



        JLabel superRodLocked = new JLabel(locked);
        superRodLocked.setBounds(87, 60, locked.getIconWidth(), locked.getIconHeight());

        JButton buyGoodRod = new JButton();
        buyGoodRod.setBounds(15, 57, 59, 80);
        buyGoodRod.setContentAreaFilled(false);
        buyGoodRod.setBorderPainted(false);
        buyGoodRod.setFocusPainted(false);
        buyGoodRod.setOpaque(false);
        buyGoodRod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=200) {
                    buyGoodRod.setEnabled(false);
                    stats.setGoodRod(1);
                    stats.setCurrency(stats.getCurrency() - 200);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    SoldOut(34, 51);
                    unlock(superRodLocked);
                    add(buySuperRod);
                }

            }
        });
        if(stats.getGoodRod() == 0){
            add(buyGoodRod);
            add(superRodLocked);
            getContentPane().setComponentZOrder(superRodLocked, 0);

        } else {
            JLabel goodRodSold = new JLabel(soldOut);
            goodRodSold.setBounds(34, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(goodRodSold);
            getContentPane().setComponentZOrder(goodRodSold, 0);
        }





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
                    stats.setBedOwned(1);
                    stats.setCurrency(stats.getCurrency() - 350);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    bed.setLocation(getLocation().x + 306, getLocation().y + 162);
                    stats.checkOutOfBounds();
                    bed.setLocation(stats.bedPositionX, stats.bedPositionY);
                    SoldOut(172, 51);
                }

            }
        });
        if(stats.getBedOwned() == 0){
            add(buyBed);
        } else {
            JLabel bedSold = new JLabel(soldOut);
            bedSold.setBounds(172, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(bedSold);
            getContentPane().setComponentZOrder(bedSold, 0);
        }




        JButton buyKurokami = new JButton();
        buyKurokami.setBounds(15, 157, 59, 80);
        buyKurokami.setContentAreaFilled(false);
        buyKurokami.setBorderPainted(false);
        buyKurokami.setFocusPainted(false);
        buyKurokami.setOpaque(false);
        buyKurokami.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=500) {
                    buyKurokami.setEnabled(false);
                    stats.setKurokami(1);
                    stats.setCurrency(stats.getCurrency() - 500);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    SoldOut(34, 151);
                }

            }
        });
        if(stats.getKurokami() == 0 && stats.getWardrobeOwned() == 1){
            add(buyKurokami);
        } else if (stats.getKurokami() == 1){
            JLabel kurokamiSold = new JLabel(soldOut);
            kurokamiSold.setBounds(34, 151, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(kurokamiSold);
            getContentPane().setComponentZOrder(kurokamiSold, 0);
        }

        JLabel kurokamiLocked = new JLabel(locked);
        kurokamiLocked.setBounds(18, 160, locked.getIconWidth(), locked.getIconHeight());


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
                    stats.setWardrobeOwned(1);
                    stats.setCurrency(stats.getCurrency() - 100);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    wardrobe.setLocation(getLocation().x + 310, getLocation().y + 95);
                    stats.checkOutOfBounds();
                    wardrobe.setLocation(stats.wardrobePositionX, stats.wardrobePositionY);
                    SoldOut(241, 51);
                    unlock(kurokamiLocked);
                    add(buyKurokami);
                }

            }
        });
        if(stats.getWardrobeOwned() == 0){
            add(buyWardrobe);
            add(kurokamiLocked);
            getContentPane().setComponentZOrder(kurokamiLocked, 0);
        } else {
            JLabel wardrobeSold = new JLabel(soldOut);
            wardrobeSold.setBounds(241, 51, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(wardrobeSold);
            getContentPane().setComponentZOrder(wardrobeSold, 0);
        }






        JButton buyChessTypeBeat = new JButton();
        buyChessTypeBeat.setBounds(153, 157, 59, 80);
        buyChessTypeBeat.setContentAreaFilled(false);
        buyChessTypeBeat.setBorderPainted(false);
        buyChessTypeBeat.setFocusPainted(false);
        buyChessTypeBeat.setOpaque(false);
        buyChessTypeBeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.getCurrency()>=200) {
                    buyChessTypeBeat.setEnabled(false);
                    stats.setChessSlowed(1);
                    stats.setCurrency(stats.getCurrency() - 200);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    SoldOut(172, 151);
                    radio.updateTrackList();
                }

            }
        });
        if(stats.getChessSlowed() == 0 && stats.getRadioOwned() == 1){
            add(buyChessTypeBeat);
        } else if (stats.getChessSlowed() == 1){
            JLabel chessTypeBeatSold = new JLabel(soldOut);
            chessTypeBeatSold.setBounds(172, 151, soldOut.getIconWidth(), soldOut.getIconHeight());
            add(chessTypeBeatSold);
            getContentPane().setComponentZOrder(chessTypeBeatSold, 0);
        }

        JLabel chessTypeBeatLocked = new JLabel(locked);
        chessTypeBeatLocked.setBounds(156, 160, locked.getIconWidth(), locked.getIconHeight());

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
                    stats.setRadioOwned(1);
                    stats.setCurrency(stats.getCurrency() - 200);
                    mainWindow.currency.setText(String.valueOf(stats.getCurrency()));
                    radio.setLocation(getLocation().x + 310, getLocation().y + 195);
                    stats.checkOutOfBounds();
                    radio.setLocation(stats.radioPositionX, stats.radioPositionY);
                    SoldOut(103, 151);
                    unlock(chessTypeBeatLocked);
                    add(buyChessTypeBeat);
                }

            }
        });
        if(stats.getRadioOwned() == 0){
            add(buyRadio);
            add(chessTypeBeatLocked);
            getContentPane().setComponentZOrder(chessTypeBeatLocked, 0);
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

    public void unlock(JLabel lock){
        lock.setVisible(false);
    }
}