package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PC extends JFrame {
    private Point initialClickPC;
    private Point initialClickDesktop;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC.png")));
    private JFrame desktop = new JFrame("Desktop");
    JLabel desktopSprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC UI.png")));
    JButton closeDesktop = new JButton();
    JButton openYoutubeButton = new JButton();
    JButton startStreamButton = new JButton();
    JButton stopStreamButton = new JButton();
    JButton closeYoutube = new JButton();
    public boolean stream = false;
    public int subscribers = 0;
    public int viewers = 0;

    public PC(FriendStats stats) {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(73,74);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        sprite.setBounds(0, 0, 73, 74);
        add(sprite);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClickPC = e.getPoint();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClickPC.x;
                int yMoved = e.getY() - initialClickPC.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
//                stats.wardrobePositionX = X;
//                stats.wardrobePositionY = Y;
            }
        });

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.setVisible(true);
            }
        });

        setVisible(true);
        setLocation(100,100);

        desktop.setUndecorated(true);
        desktop.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        desktop.setType(Window.Type.UTILITY);
        desktop.setLayout(null);
        desktop.setSize(320,180);
        desktop.setBackground(new Color(1.0f,1.0f,1.0f,0f));
        desktopSprite.setBounds(0, 0, 320, 180);
        desktop.add(desktopSprite);

        desktop.getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClickDesktop = e.getPoint();
            }
        });

        desktop.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = desktop.getLocation().x;
                int thisY = desktop.getLocation().y;

                int xMoved = e.getX() - initialClickDesktop.x;
                int yMoved = e.getY() - initialClickDesktop.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                desktop.setLocation(X, Y);
//                stats.wardrobePositionX = X;
//                stats.wardrobePositionY = Y;
            }
        });

        closeDesktop.setBounds(306, 2, 13, 13);
        closeDesktop.setContentAreaFilled(false);
        closeDesktop.setBorderPainted(false);
        closeDesktop.setFocusPainted(false);
        closeDesktop.setOpaque(false);
        closeDesktop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktop.setVisible(false);
            }
        });
        desktop.add(closeDesktop);


        openYoutubeButton.setBounds(10, 12, 42, 32);
        openYoutubeButton.setContentAreaFilled(false);
        openYoutubeButton.setBorderPainted(false);
        openYoutubeButton.setFocusPainted(false);
        openYoutubeButton.setOpaque(false);
        openYoutubeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenYoutube();
            }
        });
        desktop.add(openYoutubeButton);

        closeYoutube.setBounds(275, 22, 13, 13);
        closeYoutube.setContentAreaFilled(false);
        closeYoutube.setBorderPainted(false);
        closeYoutube.setFocusPainted(false);
        closeYoutube.setOpaque(false);
        closeYoutube.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/PC UI.png")));
                desktop.remove(closeYoutube);
                desktop.add(openYoutubeButton);
            }
        });
    }

    public void OpenYoutube(){
        if(!stream){
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOffline.png")));

        } else {
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOnline.png")));

        }
        desktop.remove(openYoutubeButton);
        desktop.add(closeYoutube);
    }
}