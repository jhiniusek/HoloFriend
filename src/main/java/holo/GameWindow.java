package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameWindow extends JFrame{
    private JPanel panel = new JPanel();
    public JLabel hunger = new JLabel();

    public GameWindow(FrendStats stats, ArrayList<Food> foodList) {
        this.setSize(250,300);
        hunger.setText("Hunger = " + String.valueOf(stats.getHunger()));
        hunger.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
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
                        Food food = new Food(getLocation().x + 45 + (i * 36), getLocation().y + 200);
                        foodList.set(i, food);
                        break;
                    }
                }
            }
        });
        foodSpawner.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(foodSpawner);
        this.add(panel);
        this.setVisible(true);
    }
}
