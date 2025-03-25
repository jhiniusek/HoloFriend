package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Clock implements Runnable{
    private FrendStats stats;
    private Frend frend;
    private GameWindow window;
    private ArrayList<Food> foodList;
    private int counter = 1;

    public Clock(FrendStats stats, Frend frend, GameWindow window, ArrayList<Food> foodList) {
        this.stats = stats;
        this.frend = frend;
        this.window = window;
        this.foodList = foodList;
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            if(stats.getState() == States.IDLE){
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleR.gif";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/IdleL.gif";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                }

            } else if (stats.getState() == States.WALK) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkR.gif";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/WalkL.gif";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                }
            } else if (stats.getState() == States.HOLD) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/Hold1R.png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    String path = "/sprites/Hold1L.png";
                    frend.icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
                }
            }

            for(int i = 0; i < foodList.size(); i++){
                if(foodList.get(i) != null){
                    CheckFoodCollision();
                    break;
                }
            }
            if((stats.getPositionX() != stats.getDestinationX() || stats.getPositionY() != stats.getDestinationY()) && stats.getState()!=States.HOLD) {

                if (stats.getPositionX() > stats.getDestinationX()){

                    if ((stats.getPositionX() - stats.getDestinationX()) < stats.getMsX()) {
                        stats.setPositionX(stats.getDestinationX());
                    } else {
                        stats.setPositionX(stats.getPositionX() - stats.getMsX());
                    }

                } else if (stats.getPositionX() < stats.getDestinationX()){

                    if ((stats.getDestinationX() - stats.getPositionX()) < stats.getMsX()) {
                        stats.setPositionX(stats.getDestinationX());
                    } else {
                        stats.setPositionX(stats.getPositionX() + stats.getMsX());
                    }

                }

                if (stats.getPositionY() > stats.getDestinationY()){

                    if ((stats.getPositionY() - stats.getDestinationY()) < stats.getMsY()) {
                        stats.setPositionY(stats.getDestinationY());
                    } else {
                        stats.setPositionY(stats.getPositionY() - stats.getMsY());
                    }

                } else if (stats.getPositionY() < stats.getDestinationY()){

                    if ((stats.getDestinationY() - stats.getPositionY()) < stats.getMsY()) {
                        stats.setPositionY(stats.getDestinationY());
                    } else {
                        stats.setPositionY(stats.getPositionY() + stats.getMsY());
                    }

                }
            }

            if(stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY()){
                stats.setState(States.IDLE);
            }

            counter++;
            if (counter == 96) {       // 16 ticks per second :   1 min = 960 tics
                stats.setHunger(stats.getHunger()-1);
                window.hungerBar.setValue(stats.getHunger());
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

    private void CheckFoodCollision(){
        for(int i = 0; i < foodList.size(); i++){
            int x = -10;
            int y = -10;
            try {
                x = foodList.get(i).getX();
                y = foodList.get(i).getY();
                int min_x = (int)stats.getPositionX();
                int max_x = min_x + 48;

                int min_y = (int)stats.getPositionY() - 16;
                int max_y = min_y + 128;

                if (x > min_x && x < max_x){
                    if (y > min_y && y < max_y){
                        foodList.get(i).dispose();
                        foodList.set(i, null);
                        if (stats.getHunger() < 70){
                            stats.setHunger(stats.getHunger()+30);
                            window.hungerBar.setValue(stats.getHunger());
                        } else {
                            stats.setHunger(100);
                            window.hungerBar.setValue(stats.getHunger());
                        }
                        System.out.println("HAMBURGER EATEN");  //debug
                        break;
                    }
                }
            } catch (Exception e){
            }
        }
    }
}
