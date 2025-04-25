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
    private Bed bed;
    private String spritePath = "/sprites/IdleL.gif";
    private String newSprite = null;

    //IDLE VARIABLES
    private int idleTimer = 60;
    private int counter = 1;

    //FISHING VARIABLES
    private int workCooldown = 0;
    private int castAnimation = 22;
    private int catchTimer = 64;
    private int fishValue = 0;
    private int tired = 0;
    private int rodSpeed = 160;

    //SLEEP VARIABLES
    private int sleepCooldown = 0;
    private int sleepTimer = 20;

    public Clock(FrendStats stats, Frend frend, GameWindow window, ArrayList<Food> foodList, Lake lake, Bed bed) {
        this.stats = stats;
        this.frend = frend;
        this.window = window;
        this.foodList = foodList;
        this.lake = lake;
        this.bed = bed;
    }

    @Override
    public void run() {
        while(stats.isAlive()){

            if (stats.getState() != States.SLEEP) {
                for(int i = 0; i < foodList.size(); i++){
                    if(foodList.get(i) != null){
                        CheckFoodCollision();
                        break;
                    }
                }
            }

            if(stats.getGoodRod() == 1){
                rodSpeed = 120;
            }
            if(stats.getSuperRod() == 1){
                rodSpeed = 80;
            }

            if(stats.getState() == States.IDLE){
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"IdleR.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }

                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"IdleL.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }

            } else if (stats.getState() == States.WALK) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"WalkR.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"WalkL.gif";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            } else if (stats.getState() == States.HOLD) {
                if(stats.isRight()){
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"Hold1R.png";
                    if(!newSprite.equals(spritePath)){
                        frend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    frend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());
                    newSprite = "/sprites/"+stats.getSkin()+"Hold1L.png";
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


            if(lake.forceStop){
                stats.setAbleToWork(false);
            }

            if(workCooldown>0){
                workCooldown--;
            }

            if(stats.getTiredness()<11){
                stats.setAbleToWork(false);
            }

            if(stats.getState() == States.WORK){
                stats.setPositionX(lake.getLocation().x+30);
                stats.setPositionY(lake.getLocation().y+50);

                if(castAnimation > 0){
                    castAnimation--;
                }
                if(castAnimation == 0){
                    newSprite = "/sprites/"+stats.getSkin()+"LakeIdle.gif";
                    if(!newSprite.equals(spritePath)){
                        lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                    castAnimation = -1;
                }

                if(catchTimer > 0){
                    catchTimer--;
                }
                if(castAnimation == 15){
                    stats.setCurrency(stats.getCurrency()+ fishValue);
                    window.currency.setText(String.valueOf(stats.getCurrency()));
                    stats.setTiredness(stats.getTiredness()-tired);
                    window.tiredBar.setValue(stats.getTiredness());
                    fishValue = 0;
                    tired = 0;
                }
                if(catchTimer == 0){
                    if(Math.random() < 0.05){
                        newSprite = "/sprites/"+stats.getSkin()+"CatchShiny.gif";
                        fishValue = 50;
                    } else {
                        newSprite = "/sprites/"+stats.getSkin()+"CatchNormal.gif";
                        fishValue = 5;
                    }
                    castAnimation = 32;
                    tired = 10;
                    if(!newSprite.equals(spritePath)){
                        lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                    catchTimer = ((int) (Math.random() * rodSpeed) + rodSpeed);
                }
                if(!stats.isAbleToWork() && castAnimation<10){
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
                    lake.forceStop = false;
                    lake.removeForcableStop();
                }

            } else if (stats.isAbleToWork() && workCooldown == 0 && stats.getState() != States.SLEEP){
                CheckLakeCollision();
            }



            if(sleepCooldown>0){
                sleepCooldown--;
            }

            stats.setAbleToSleep(true);

            if(stats.getTiredness() > 70 && stats.getState() != States.SLEEP){
                stats.setAbleToSleep(false);
            }

            if(stats.getTiredness() == 100){
                stats.setAbleToSleep(false);
            }

            if(bed.wakeup || stats.getHunger() < 5){
                stats.setAbleToSleep(false);
            }

            if(stats.getState()==States.SLEEP){
                if(sleepTimer > 0){
                    sleepTimer--;
                }
                if(sleepTimer == 0){
                    stats.setTiredness(stats.getTiredness()+1);
                    window.tiredBar.setValue(stats.getTiredness());
                    sleepTimer = 25;
                }
                if(!stats.isAbleToSleep()){
                    sleepCooldown = 320;
                    stats.setPositionX(bed.getLocation().x+95);
                    stats.setPositionY(bed.getLocation().y-50);
                    newSprite = "/sprites/Bed.png";
                    if(!newSprite.equals(spritePath)){
                        bed.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                    frend.setLocation((int)stats.getPositionX(),(int)stats.getPositionY());
                    frend.setVisible(true);
                    stats.setState(States.IDLE);
                    bed.wakeup = false;
                    bed.removeForcableStop();
                }

            } else if(stats.isAbleToSleep() && sleepCooldown == 0 && stats.getState() != States.WORK){
                CheckBedCollision();
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
                            stats.setHunger(stats.getHunger()+10);
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
                lake.makeForceableStop();
                frend.setVisible(false);
                newSprite = "/sprites/"+stats.getSkin()+"Cast.gif";
                if(!newSprite.equals(spritePath)){
                    lake.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        }
    }

    private void CheckBedCollision(){
        int x = (int)stats.getPositionX() + 16;
        int y = (int)stats.getPositionY() + 64;
        int min_x = bed.getX()+50;
        int max_x = min_x + 120;

        int min_y = bed.getY()+15;
        int max_y = min_y + 70;

        if(x > min_x && x < max_x){
            if (y > min_y && y < max_y){
                stats.setState(States.SLEEP);
                bed.makeForceableStop();
                frend.setVisible(false);
                //newSprite = "/sprites/"+stats.getSkin()+"Sleep.gif";
                newSprite = "/sprites/basic/Sleep.gif"; //REMOVE WHEN SLEEP KUROKAMI ADDED
                if(!newSprite.equals(spritePath)){
                    bed.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        }
    }
}
