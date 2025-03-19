package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Clock implements Runnable{
    private FrendStats stats;
    private Frend frend;
    private GameWindow window;
    private int counter = 1;
    private int frame = 1;

    public Clock(FrendStats stats, Frend frend, GameWindow window) {
        this.stats = stats;
        this.frend = frend;
        this.window = window;
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            stats.setAnimationFrame(frame);
            if(counter%2 == 0){
                if(frame<8){
                    frame++;
                }else{
                    frame=1;
                }
            }

            if(stats.getState() == States.IDLE){
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleR" + stats.getAnimationFrame() + ".png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleL" + stats.getAnimationFrame() + ".png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                }

            } else if (stats.getState() == States.WALK) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkR" + stats.getAnimationFrame() + ".png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkL" + stats.getAnimationFrame() + ".png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                }
            }


            counter++;
            if (counter == 960) {       // 16 ticks per second :   1 min = 960 tics
                stats.setHunger(stats.getHunger()-1);
                window.hunger.setText("Hunger = " + String.valueOf(stats.getHunger()));
                //stats.setTiredness(stats.getTiredness()-1);
                counter = 1;
            }

            if (stats.getHunger() == 0){
                stats.setAlive(false);

                JFrame frame = new JFrame("Starved");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 150);
                frame.setLayout(new BorderLayout());
                frame.setLocationRelativeTo(null);

                JLabel label = new JLabel("I am die, thank you forever!", SwingConstants.CENTER);
                frame.add(label, BorderLayout.CENTER);

                JButton closeButton = new JButton("RIP");
                frame.add(closeButton, BorderLayout.SOUTH);

                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                frame.setVisible(true);
            }

            try {
                Thread.sleep(63); // movement 16 ticks per second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
