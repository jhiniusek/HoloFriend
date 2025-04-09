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
    private Lake lake;
    private String spritePath = "/sprites/IdleL.gif";
    private String newSprite = null;
    private int idleTimer = 60;
    private int counter = 1;
    private int workCooldown = 0;
    private int castAnimation = 22;
    private int catchTimer = 64;

    public Clock(FrendStats stats, Frend frend, GameWindow window, ArrayList<Food> foodList, Lake lake) {
        this.stats = stats;
        this.frend = frend;
        this.window = window;
        this.foodList = foodList;
        this.lake = lake;
    }

    @Override
    public void run() {
        while(stats.isAlive()){

            for(int i = 0; i < foodList.size(); i++){
                if(foodList.get(i) != null){
                    CheckFoodCollision();
                    break;
                }
            }

            if(stats.getState() == States.IDLE){
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/IdleR.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }

                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/IdleL.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }

            } else if (stats.getState() == States.WALK) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/WalkR.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/WalkL.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            } else if (stats.getState() == States.HOLD) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/Hold1R.png";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/Hold1L.png";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            }

            if((stats.getPositionX() != stats.getDestinationX() || stats.getPositionY() != stats.getDestinationY()) && stats.getState()==States.WALK) {

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

            if(stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY() && stats.getState() == States.WALK){
                stats.setState(States.IDLE);
            }

            if(idleTimer != 0 && stats.getState() == States.IDLE){
                idleTimer--;
            }

            if (stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY() && idleTimer == 0) {
                idleTimer = (int)(Math.random() * 80 + 64);
                stats.chooseDestination();
                stats.setState(States.WALK);
            }

            if (stats.getState() == States.IDLE && idleTimer == 0 && (stats.getPositionX() != stats.getDestinationX() || stats.getPositionY() != stats.getDestinationY())){
                stats.evaluateMs();
                stats.setState(States.WALK);
            }



            if(workCooldown>0){
                workCooldown--;
            }

            if(stats.getTiredness()<6){
                stats.setAbleToWork(false);
            }

            if(stats.getState() == States.WORK){
                stats.setPositionX(lake.getLocation().x+30);
                stats.setPositionY(lake.getLocation().y+50);

                if(castAnimation > 0){
                    castAnimation--;
                }
                if(castAnimation == 0){
                    newSprite = "/sprites/LakeIdle.gif";
                    if(!newSprite.equals(spritePath)){
                        lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                    castAnimation = -1;
                }

                if(catchTimer > 0){
                    catchTimer--;
                }
                if(catchTimer == 0){
                    if(Math.random() < 0.5){
                        castAnimation = 32;
                        newSprite = "/sprites/CatchShiny.gif";
                        if(!newSprite.equals(spritePath)){
                            lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                            spritePath = newSprite;
                        }
                        catchTimer = ((int) (Math.random() * 80) + 80);
                        stats.setTiredness(stats.getTiredness()-5);
                        //CURRENCY + 100 or smth
                    } else {
                        castAnimation = 32;
                        newSprite = "/sprites/CatchNormal.gif";
                        if(!newSprite.equals(spritePath)){
                            lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                            spritePath = newSprite;
                        }
                        catchTimer = ((int) (Math.random() * 80) + 80);
                        stats.setTiredness(stats.getTiredness()-5);
                        //CURRENCY + 10 or smth
                    }
                }
                if(!stats.isAbleToWork() && castAnimation==10){
                    workCooldown = 320;
                    stats.setPositionX(lake.getLocation().x-40);
                    stats.setPositionY(lake.getLocation().y+50);
                    newSprite = "/sprites/Lake.png";
                    if(!newSprite.equals(spritePath)){
                        lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                    frend.setLocation((int)stats.getPositionX(),(int)stats.getPositionY());
                    frend.setVisible(true);
                    stats.setState(States.IDLE);
                    castAnimation = 22;
                }

            } else if (stats.isAbleToWork() && workCooldown == 0){
                CheckLakeCollision();
            }

            counter++;
            if (counter == 96) {       // 16 ticks per second :   1 min = 960 tics
                stats.setHunger(stats.getHunger()-1);
                window.hungerBar.setValue(stats.getHunger());
                if(stats.getTiredness() < 100){
                    stats.setTiredness(stats.getTiredness()+1);
                    if(stats.getTiredness()>20){
                        stats.setAbleToWork(true);
                    }
                }
                window.tiredBar.setValue(stats.getTiredness());
                counter = 1;
            }


            // Death out of Hunger
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

    private void CheckLakeCollision(){
        int x = (int)stats.getPositionX() + 32;
        int y = (int)stats.getPositionY() + 100;
        int min_x = lake.getX()+30;
        int max_x = min_x + 220;

        int min_y = lake.getY()+100;
        int max_y = min_y + 128;

        if(x > min_x && x < max_x){
            if (y > min_y && y < max_y){
                stats.setState(States.WORK);
                frend.setVisible(false);
                newSprite = "/sprites/Cast.gif";
                if(!newSprite.equals(spritePath)){
                    lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        }
    }
}
