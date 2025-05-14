package holo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Radio extends JFrame {
    private Point initialClick;
    private FrendStats stats;
    JLabel lcd = new JLabel(new ImageIcon(getClass().getResource("/sprites/RadioLCD_NoDisk.gif")));
    private Clip clip;
    private ArrayList<String> trackList;
    private int currentTrack = 0;

    public Radio(FrendStats stats) {
        this.stats = stats;
        updateTrackList();
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
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

        JButton previousTrack = new JButton();
        previousTrack.setBounds(24, 18, 13, 11);
        previousTrack.setContentAreaFilled(false);
        previousTrack.setBorderPainted(false);
        previousTrack.setFocusPainted(false);
        previousTrack.setOpaque(false);
        previousTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip!=null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                }
                if(stats.getState() == States.DANCE){
                    stats.setState(States.IDLE);
                }
                if(currentTrack == 0){
                    currentTrack = trackList.size() - 1;
                } else {
                    currentTrack -= 1;
                }
                updateLCD();
                stats.setCurrentTrack(trackList.get(currentTrack));
            }
        });
        add(previousTrack);

        JButton nextTrack = new JButton();
        nextTrack.setBounds(37, 18, 13, 11);
        nextTrack.setContentAreaFilled(false);
        nextTrack.setBorderPainted(false);
        nextTrack.setFocusPainted(false);
        nextTrack.setOpaque(false);
        nextTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip!=null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                }
                if(stats.getState() == States.DANCE){
                    stats.setState(States.IDLE);
                }
                if(currentTrack == trackList.size() - 1){
                    currentTrack = 0;
                } else {
                    currentTrack += 1;
                }
                updateLCD();
                stats.setCurrentTrack(trackList.get(currentTrack));
            }
        });
        add(nextTrack);

        JButton stop = new JButton();
        stop.setBounds(24, 29, 13, 11);
        stop.setContentAreaFilled(false);
        stop.setBorderPainted(false);
        stop.setFocusPainted(false);
        stop.setOpaque(false);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip!=null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                }
                if(stats.getState() == States.DANCE){
                    stats.setState(States.IDLE);
                }
            }
        });
        add(stop);

        JButton play = new JButton();
        play.setBounds(37, 29, 13, 11);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setOpaque(false);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (clip != null && clip.isRunning()) {
                        return;
                    }

                    URL soundURL = getClass().getResource("/sprites/" + getCurrentTrack() + ".wav");

                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                    if(stats.getState() != States.SLEEP || stats.getState() != States.WORK){
                        stats.setState(States.DANCE);
                    }
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
                }
            }
        });
        add(play);



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

    public String getCurrentTrack(){
        return trackList.get(currentTrack);
    }

    public void updateTrackList(){
        trackList = new ArrayList<>();
        trackList.add("NoDisk");
        if(stats.getChessSlowed() == 1){
            trackList.add("RatDance");
        }
    }

    private void updateLCD(){
        lcd.setIcon(new ImageIcon(getClass().getResource("/sprites/RadioLCD_" + getCurrentTrack() + ".gif")));
    }
}