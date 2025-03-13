package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BGClock implements Runnable{
    private FrendStats stats;

    public BGClock(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while (stats.isAlive()) {
            try {
                Thread.sleep(20000); //3 minutes = 180000
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stats.setHunger(stats.getHunger()-1);
            //stats.setTiredness(stats.getTiredness()-1);

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
        }
    }
}
