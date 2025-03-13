package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameWindow implements Runnable{
    private FrendStats stats;
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel hunger = new JLabel();

    public GameWindow(FrendStats stats, ArrayList<Food> foodList) {
        this.stats = stats;
        frame.setSize(250,300);
        hunger.setText("Hunger = " + String.valueOf(stats.getHunger()));
        hunger.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(100));
        panel.add(hunger);
        panel.add(Box.createVerticalStrut(100));
        JButton foodSpawner = new JButton();
        foodSpawner.setText("Make Food");
        foodSpawner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < foodList.size(); i++){
                    if(foodList.get(i) == null){
                        Food food = new Food(frame.getLocation().x + 45 + (i * 36), frame.getLocation().y + 200);
                        foodList.set(i, food);
                        break;
                    }
                }
            }
        });
        foodSpawner.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(foodSpawner);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void run(){
        while (true){
            hunger.setText("Hunger = " + String.valueOf(stats.getHunger()));
        }
    }
}
