package holo;

import javax.swing.*;
import java.awt.*;

public class Frend implements Runnable{
    private JFrame frame = new JFrame();
    private FrendStats stats;
    private JLabel icon;

    public Frend(FrendStats stats) {
        this.stats = stats;
        icon = new JLabel(new ImageIcon(getClass().getResource("/sprites/IdleL1.png")));
        frame.setSize(64,128);
        frame.add(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setBackground(new Color(1.0f,1.0f,1.0f,0f));
        frame.setVisible(true);
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            if(stats.getState() == States.IDLE){
                if(stats.isRight()){
                    frame.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleR" + stats.getAnimationFrame() + ".png";
                    icon.setIcon(new ImageIcon(getClass().getResource(path)));
                } else {
                    frame.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleL" + stats.getAnimationFrame() + ".png";
                    icon.setIcon(new ImageIcon(getClass().getResource(path)));
                }

            } else if (stats.getState() == States.WALK) {
                if(stats.isRight()){
                    frame.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkR" + stats.getAnimationFrame() + ".png";
                    icon.setIcon(new ImageIcon(getClass().getResource(path)));
                } else {
                    frame.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkL" + stats.getAnimationFrame() + ".png";
                    icon.setIcon(new ImageIcon(getClass().getResource(path)));
                }
            }
        }
    }
}
