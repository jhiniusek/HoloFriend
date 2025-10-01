package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class PC extends JFrame {
    private Point initialClickPC;
    private Point initialClickDesktop;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC.png")));
    private JFrame desktop = new JFrame("Desktop");
    JLabel desktopSprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC UI.png")));
    JButton closeDesktop = new JButton();
    JButton openYoutubeButton = new JButton();
    JButton streamButton = new JButton();
    JButton closeYoutube = new JButton();
    Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));
    JLabel viewerCount = new JLabel();
    JLabel subscriberCount = new JLabel();
    public boolean stream = false;
    public int subscribers = 0;
    public int viewers = 0;

    public PC(FriendStats stats) throws IOException, FontFormatException {
        subscribers = stats.getSubscribers();
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(85,79);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        sprite.setBounds(0, 0, 85, 79);
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
                stats.pcPositionX = X;
                stats.pcPositionY = Y;
            }
        });

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.setVisible(true);
                desktop.setLocation(PC.super.getX()+50, PC.super.getY()-80);
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
                desktop.add(subscriberCount);
                desktop.getContentPane().setComponentZOrder(subscriberCount, 0);
            }
        });
        desktop.add(openYoutubeButton);


        subscriberCount.setText(String.valueOf(stats.getSubscribers()));
        subscriberCount.setFont(font.deriveFont(20f));
        subscriberCount.setHorizontalAlignment(SwingConstants.RIGHT);
        subscriberCount.setBounds(200,34,58,11);

        viewerCount.setText(String.valueOf(viewers));
        viewerCount.setFont(font.deriveFont(20f));
        viewerCount.setHorizontalAlignment(SwingConstants.LEFT);
        viewerCount.setBounds(62,138,80,11);

        closeYoutube.setBounds(275, 22, 13, 13);
        closeYoutube.setContentAreaFilled(false);
        closeYoutube.setBorderPainted(false);
        closeYoutube.setFocusPainted(false);
        closeYoutube.setOpaque(false);
        closeYoutube.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/PC UI.png")));
                desktop.remove(subscriberCount);
                desktop.remove(closeYoutube);
                desktop.remove(streamButton);
                desktop.add(openYoutubeButton);
            }
        });

        streamButton.setBounds(205, 136, 73, 14);
        streamButton.setContentAreaFilled(false);
        streamButton.setBorderPainted(false);
        streamButton.setFocusPainted(false);
        streamButton.setOpaque(false);
        streamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stream();
                updateStreamStats(stats);
            }
        });

        setLocation(stats.pcPositionX, stats.pcPositionY);
    }

    public void OpenYoutube(){
        if(!stream){
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOffline.png")));
            desktop.remove(viewerCount);
        } else {
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOnline.png")));
            desktop.add(viewerCount);
            desktop.getContentPane().setComponentZOrder(viewerCount, 0);
        }
        desktop.remove(openYoutubeButton);
        desktop.add(streamButton);
        desktop.add(closeYoutube);
    }

    public void stream(){
        if(!stream){
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOnline.png")));
            desktop.add(viewerCount);
            int randomNum = ThreadLocalRandom.current().nextInt(60, 70 + 1);
            viewers = (subscribers * randomNum) / 100 + 1;
            desktop.getContentPane().setComponentZOrder(viewerCount, 0);
            stream = true;
        } else {
            desktopSprite.setIcon(new ImageIcon(getClass().getResource("/sprites/StreamOffline.png")));
            desktop.remove(viewerCount);
            stream = false;
        }
    }

    public void updateStreamStats(FriendStats stats){
        subscribers = stats.getSubscribers();
        subscriberCount.setText(String.valueOf(subscribers));
        viewerCount.setText(String.valueOf(viewers));
    }
}