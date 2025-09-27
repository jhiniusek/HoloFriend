package holo;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Clock implements Runnable{
    private FriendStats stats;
    private Friend friend;
    private GameWindow window;
    private ArrayList<Food> foodList;
    private Lake lake;
    private Bed bed;
    private PC pc;
    private String spritePath = "/sprites/IdleL.gif";
    private String newSprite = null;
    private Point mousePoint = new Point (0,0);
    private boolean pullUp = false;
    private float topFix;
    private float botFix;

    //IDLE VARIABLES
    private int idleTimer = 60;
    private int counter = 1;
    private int hintRefresh = 1;

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

    //DANCE VARIABLES
    private int danceLenght = 0;
    private int danceTimer = 0;

    public Clock(FriendStats stats, Friend friend, GameWindow window, ArrayList<Food> foodList, Lake lake, Bed bed, PC pc) {
        this.stats = stats;
        this.friend = friend;
        this.window = window;
        this.foodList = foodList;
        this.lake = lake;
        this.bed = bed;
        this.pc = pc;
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

            if(stats.getGoodRod()){
                rodSpeed = 120;
            }
            if(stats.getSuperRod()){
                rodSpeed = 80;
            }

            friend.setLocation((int)stats.getPositionX(), (int)stats.getPositionY());

            if(stats.getState() == States.IDLE){
                friend.setSize(64,128);
                friend.sprite.setBounds(0,0,64,128);
                friend.shadow.setBounds(0,116,60,11);
                if(stats.isRight()){
                    newSprite = "/sprites/"+stats.getSkin()+"IdleR.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }

                } else {
                    newSprite = "/sprites/"+stats.getSkin()+"IdleL.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }

            } else if (stats.getState() == States.WALK) {
                friend.setSize(64,128);
                friend.sprite.setBounds(0,0,64,128);
                friend.shadow.setBounds(0,116,60,11);
                if(stats.isRight()){
                    newSprite = "/sprites/"+stats.getSkin()+"WalkR.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    newSprite = "/sprites/"+stats.getSkin()+"WalkL.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }

            } else if (stats.getState() == States.CHASE) {
                friend.setSize(175,128);
                friend.sprite.setBounds(0,0,175,128);
                stats.evaluateMs();
                if(stats.isRight()){
                    friend.shadow.setBounds(0,116,60,11);
                    newSprite = "/sprites/"+stats.getSkin()+"ChaseR.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    friend.shadow.setBounds(80,116,60,11);
                    newSprite = "/sprites/"+stats.getSkin()+"ChaseL.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }

            }else if (stats.getState() == States.HOLD) {
                friend.setSize(64,128);
                friend.sprite.setBounds(0,0,64,128);
                friend.shadow.setBounds(0,116,60,11);
                if(stats.isRight()){
                    newSprite = "/sprites/"+stats.getSkin()+"Hold1R.png";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    newSprite = "/sprites/"+stats.getSkin()+"Hold1L.png";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            } else if (stats.getState() == States.PULL) {
                friend.setSize(64,128);
                friend.sprite.setBounds(0,0,64,128);
                friend.shadow.setBounds(0,116,60,11);
                if(stats.isRight()){
                    newSprite = "/sprites/"+stats.getSkin()+"PullR.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    newSprite = "/sprites/" + stats.getSkin() + "PullL.gif";
                    if (!newSprite.equals(spritePath)) {
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            } else if (stats.getState() == States.PUSH) {
                friend.setSize(64,128);
                friend.sprite.setBounds(3,0,64,128);
                if(stats.isRight()){
                    friend.shadow.setBounds(0,116,60,11);
                    newSprite = "/sprites/"+stats.getSkin()+"PushR.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                } else {
                    friend.shadow.setBounds(5,116,60,11);
                    newSprite = "/sprites/" + stats.getSkin() + "PushL.gif";
                    if (!newSprite.equals(spritePath)) {
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }
                }
            }

            //
            //       MOVEMENT LOGIC
            //

            if((stats.getPositionX() != stats.getDestinationX() || stats.getPositionY() != stats.getDestinationY()) && (stats.getState()==States.WALK || stats.getState()==States.CHASE || stats.getState()==States.PULL || stats.getState()==States.PUSH)) {

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

            if(stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY() && (stats.getState()==States.PULL || stats.getState()==States.PUSH)){
                stats.setWindow(null);
                stats.getRect().clear();
                stats.setState(States.IDLE);
                window.setAlwaysOnTop(false);
            }

            if(stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY() && (stats.getState()==States.WALK || stats.getState()==States.CHASE)){
                if (stats.getChaseObject() == "Cursor") {
                    stats.setState(States.PULL);
                    int pullDistanceX = (int)(Math.random() * 200 + 200);
                    int pullDistanceY = (int)(Math.random() * 50 + 50);
                    if(stats.isRight()){
                        stats.setPositionX(stats.getPositionX() + 20);
                        stats.setDestinationX(stats.getDestinationX() - pullDistanceX);
                    } else {
                        stats.setPositionX(stats.getPositionX() + 80);
                        stats.setDestinationX(stats.getDestinationX() + pullDistanceX);
                    }
                    if(pullDistanceY % 2 == 0){
                        stats.setDestinationY(stats.getDestinationY() + pullDistanceY);
                    } else {
                        stats.setDestinationY(stats.getDestinationY() - pullDistanceY);
                    }
                } else if (stats.getChaseObject() == "Window" || stats.getChaseObject() == "GameWindow") {
                    Random rand = new Random();
                    int PushOrPull = rand.nextInt(2);
                    int distanceX = (int)(Math.random() * 200 + 200);
                    int distanceY = (int)(Math.random() * 50 + 50);
                    topFix = stats.getRect().top;
                    botFix = stats.getRect().bottom;
                    if(stats.getWindowSide() == 0){
                        switch (PushOrPull){
                            case 0:
                                stats.setState(States.PULL);
                                stats.setDestinationX(stats.getDestinationX() - distanceX);
                                break;
                            case 1:
                                stats.setState(States.PUSH);
                                stats.setDestinationX(stats.getDestinationX() + distanceX);
                                break;
                        }
                    } else {
                        switch (PushOrPull){
                            case 0:
                                stats.setState(States.PULL);
                                stats.setDestinationX(stats.getDestinationX() + distanceX);
                                break;
                            case 1:
                                stats.setState(States.PUSH);
                                stats.setDestinationX(stats.getDestinationX() - distanceX);
                                break;
                        }
                    }
                    if(distanceY % 2 == 0){
                        stats.setDestinationY(stats.getDestinationY() + distanceY);
                        pullUp = false;
                    } else {
                        stats.setDestinationY(stats.getDestinationY() - distanceY);
                        pullUp = true;
                    }
                    stats.evaluateMs();
                    User32.INSTANCE.ShowWindow(stats.getWindow(), WinUser.SW_RESTORE);
                    User32.INSTANCE.BringWindowToTop(stats.getWindow());
                    User32.INSTANCE.SetForegroundWindow(stats.getWindow());

                } else {
                    stats.setState(States.IDLE);
                }
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


            //
            //                        PULL CURSOR
            //

            if(stats.getClickCounter() >= 3){
                stats.setClickCounter(0);
                stats.setChaseObject("Cursor");
                stats.setState(States.CHASE);
            }

            if(stats.getState() == States.PULL && stats.getChaseObject() == "Cursor"){
                if(stats.isRight()){
                    mousePoint.x = (int) (stats.getPositionX() + 6);
                    mousePoint.y = (int) (stats.getPositionY() + 43);
                } else {
                    mousePoint.x = (int) (stats.getPositionX() + 53);
                    mousePoint.y = (int) (stats.getPositionY() + 43);
                }
                moveMouse(mousePoint);
            }


            //
            //                PULL or PUSH WINDOW
            //

            if((stats.getState() == States.PULL || stats.getState() == States.PUSH) && stats.getChaseObject() == "Window") {
                if (!User32.INSTANCE.IsWindow(stats.getWindow())) {
                    stats.setState(States.WALK);
                    stats.setWindow(null);
                    stats.getRect().clear();
                }

                if(stats.isRight()){
                    stats.getRect().left += (int)stats.getMsX();
                    stats.getRect().right += (int)stats.getMsX();
                } else {
                    stats.getRect().left -= (int)stats.getMsX();
                    stats.getRect().right -= (int)stats.getMsX();
                }
                if(pullUp){
                    topFix -= stats.getMsY();
                    botFix -= stats.getMsY();
                } else {
                    if (stats.getRect().top != 0) {
                        topFix += stats.getMsY();
                        botFix += stats.getMsY();
                    }
                }
                stats.getRect().top = (int)topFix;
                stats.getRect().bottom = (int)botFix;

                User32.INSTANCE.SetWindowPos(stats.getWindow(), null, stats.getRect().left, stats.getRect().top, (stats.getRect().right - stats.getRect().left), (stats.getRect().bottom - stats.getRect().top), 0);
                User32.INSTANCE.ShowWindow(stats.getWindow(), WinUser.SW_RESTORE);
            }

            if((stats.getState() == States.PULL || stats.getState() == States.PUSH) && stats.getChaseObject() == "GameWindow"){
                if(stats.isRight()){
                    stats.getRect().left += (int)stats.getMsX();
                } else {
                    stats.getRect().left -= (int)stats.getMsX();
                }
                if(pullUp){
                    topFix -= stats.getMsY();
                } else {
                    if (stats.getRect().top != 0) {
                        topFix += stats.getMsY();
                    }
                }
                stats.getRect().top = (int)topFix;
                window.setLocation(stats.getRect().left, stats.getRect().top);
                window.setAlwaysOnTop(true);
                for (int i = 0; i <foodList.size() ; i++) {
                    try {
                        foodList.get(i).moved=true;
                    } catch (Exception e) {
                    }
                }
            }

            //
            //                        WORK
            //

            if(lake.forceStop){
                stats.setAbleToWork(false);
                workCooldown = 320;
            }

            if(workCooldown>0){
                workCooldown--;
            }
            stats.setAbleToWork(workCooldown == 0);

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
                    friend.setLocation((int)stats.getPositionX(),(int)stats.getPositionY());
                    friend.setVisible(true);
                    stats.setState(States.IDLE);
                    castAnimation = 22;
                    lake.forceStop = false;
                    lake.removeForcableStop();
                }

            } else if (stats.isAbleToWork() && workCooldown == 0 && (stats.getChaseObject() == "Lake" ||  stats.getState() == States.HOLD)){
                CheckLakeCollision();
            }

            //
            //            SLEEP
            //

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
                stats.setPositionX(bed.getLocation().x+95);
                stats.setPositionY(bed.getLocation().y-50);
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
                    friend.setLocation((int)stats.getPositionX(),(int)stats.getPositionY());
                    friend.setVisible(true);
                    stats.setState(States.IDLE);
                    bed.wakeup = false;
                    bed.removeForcableStop();
                }

            } else if(stats.getBedOwned() && stats.isAbleToSleep() && sleepCooldown == 0 && (stats.getChaseObject() == "Bed" ||  stats.getState() == States.HOLD)){
                CheckBedCollision();
            }

            //
            //           DANCING
            //

            if(stats.getState() == States.DANCE){
                String currentDance = stats.getCurrentTrack();
                if(currentDance == "NoDisk"){
                    danceLenght = 140;

                    newSprite = "/sprites/"+stats.getSkin()+"IdleL.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }

                    if(danceTimer == danceLenght){
                        stats.setState(States.IDLE);
                    }
                    danceTimer++;
                }
                if(currentDance == "RatDance"){
                    friend.setSize(78,128);
                    danceLenght = 2176;

                    newSprite = "/sprites/"+stats.getSkin()+"RatDance.gif";
                    if(!newSprite.equals(spritePath)){
                        friend.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                        spritePath = newSprite;
                    }

                    if(danceTimer == danceLenght){
                        stats.setState(States.IDLE);
                    }
                    danceTimer++;
                }

            } else {
                danceTimer = 0;
            }


            // CLOCK
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
                if(hintRefresh == 5){
                    window.UpdateHint();
                    hintRefresh = 1;
                } else {
                    hintRefresh++;
                }
            }

            try {
                stats.setCursorX((int) MouseInfo.getPointerInfo().getLocation().getX());
                stats.setCursorY((int) MouseInfo.getPointerInfo().getLocation().getY());
            } catch (Exception e) {

            }
            stats.updateDestination();


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
                        stats.setCurrency(0);
                        stats.setHunger(100);
                        stats.setTiredness(100);
                        stats.Save();
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
                        if (stats.getHunger() < 79){
                            stats.setHunger(stats.getHunger()+20);
                            window.hungerBar.setValue(stats.getHunger());
                        } else {
                            stats.setHunger(100);
                            window.hungerBar.setValue(stats.getHunger());
                        }
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
                friend.setVisible(false);
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
                friend.setVisible(false);
                newSprite = "/sprites/"+stats.getSkin()+"Sleep.gif";
                if(!newSprite.equals(spritePath)){
                    bed.sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        }
    }

    public void moveMouse(Point p) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (GraphicsDevice device: gs) {
            GraphicsConfiguration[] configurations = device.getConfigurations();
            for (GraphicsConfiguration config: configurations) {
                try {
                    Robot r = new Robot(device);
                    r.mouseMove(p.x, p.y);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        return;
    }
}
