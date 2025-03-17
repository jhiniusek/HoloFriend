package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clock implements Runnable{
    private FrendStats stats;
    private int counter = 1;
    private int frame = 1;

    public Clock(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            stats.setAnimationFrame(frame);
            if(frame<8){
                frame++;
            }else{
                frame=1;
            }

            counter++;
            if (counter == 480) {       // 8 fps :   1 min = 480 frames
                stats.setHunger(stats.getHunger()-1);
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
                Thread.sleep(125); //8 fps
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
