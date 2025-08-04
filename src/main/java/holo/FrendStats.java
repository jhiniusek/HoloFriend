package holo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FrendStats {
    private int hunger = 100;
    private int tiredness = 100;
    private int currency = 10;
    private boolean alive = true;
    private ArrayList<Screen> screens = new ArrayList<>();
    private ArrayList<Food> foodList;
    private float positionX = 500;
    private float positionY = 500;
    private float destinationX = 500;
    private float destinationY = 500;
    private float msX = 4;
    private float msY = 0;
    private boolean right = false;
    private int cursorX = 0;
    private int cursorY = 0;
    private States state = States.IDLE;
    private String chaseObject = "";
    private int clickCounter = 0;
    private boolean ableToWork = true;
    private Lake lake;
    private Bed bed;
    private Wardrobe wardrobe;
    private Radio radio;
    private boolean ableToSleep = true;
    String[] skins = new String[] {"basic/", "kurokami/"};
    private String currentTrack = "NoDisk";

    //EQUIPMENT
    private int goodRod = 0;
    private int superRod = 0;

    private int bedOwned = 0;
    public int bedPositionX = 0;
    public int bedPositionY = 0;

    private int wardrobeOwned = 0;
    public int wardrobePositionX = 0;
    public int wardrobePositionY = 0;
    private int kurokami = 0;
    private int skin = 0;

    private int radioOwned = 0;
    public int radioPositionX = 0;
    public int radioPositionY = 0;
    private int chessSlowed = 0;

    public FrendStats() throws InterruptedException {
        getScreens();
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int tiredness) {
        this.tiredness = tiredness;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getChaseObject() {
        return chaseObject;
    }

    public void setChaseObject(String chaseObject) {
        this.chaseObject = chaseObject;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public float getMsX() {
        return msX;
    }

    public void setMsX(float msX) {
        this.msX = msX;
    }

    public float getMsY() {
        return msY;
    }

    public void setMsY(float msY) {
        this.msY = msY;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean goRight) {
        this.right = goRight;
    }

    public boolean isAbleToWork() {
        return ableToWork;
    }

    public void setAbleToWork(boolean ableToWork) {
        this.ableToWork = ableToWork;
    }

    public void setLake(Lake lake) {
        this.lake = lake;
    }

    public Lake getLake() {
        return lake;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Wardrobe getWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(Wardrobe wardrobe) {
        this.wardrobe = wardrobe;
    }

    public Radio getRadio() {
        return radio;
    }

    public void setRadio(Radio radio) {
        this.radio = radio;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public boolean isAbleToSleep() {
        return ableToSleep;
    }

    public void setAbleToSleep(boolean ableToSleep) {
        this.ableToSleep = ableToSleep;
    }

    public void setSkin(int skin){
        this.skin = skin;
    }

    public String getSkin(){
        return skins[skin];
    }

    public void setCurrentTrack(String track){
        this.currentTrack = track;
    }

    public String getCurrentTrack(){
        return currentTrack;
    }

    public int getGoodRod() {
        return goodRod;
    }

    public void setGoodRod(int goodRod) {
        this.goodRod = goodRod;
    }

    public int getSuperRod() {
        return superRod;
    }

    public void setSuperRod(int superRod) {
        this.superRod = superRod;
    }

    public int getBedOwned() {
        return bedOwned;
    }

    public void setBedOwned(int bedOwned) {
        this.bedOwned = bedOwned;
    }

    public int getWardrobeOwned() {
        return wardrobeOwned;
    }

    public void setWardrobeOwned(int wardrobeOwned) {
        this.wardrobeOwned = wardrobeOwned;
    }

    public int getKurokami() {
        return kurokami;
    }

    public void setKurokami(int kurokami) {
        this.kurokami = kurokami;
    }

    public int getRadioOwned() {
        return radioOwned;
    }

    public void setRadioOwned(int radioOwned) {
        this.radioOwned = radioOwned;
    }

    public int getChessSlowed() {
        return chessSlowed;
    }

    public void setChessSlowed(int chessSlowed) {
        this.chessSlowed = chessSlowed;
    }

    private void getScreens() throws InterruptedException {
        GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        JFrame screenDetector = new JFrame();
        screenDetector.setUndecorated(true);
        screenDetector.setVisible(false);
        for (int i = 0; i < gs.length; i++) {
            gs[i].setFullScreenWindow(screenDetector);
            System.out.println(screenDetector.getLocation() + "  " + screenDetector.getSize());
            screens.addLast(new Screen(screenDetector));
        }
        screenDetector.dispose();
    }

    public void chooseDestination(){
        // Chase or Walk
        int foodProbabilty = 100 - hunger;
        int sleepProbability = 50 - tiredness;
        if (sleepProbability < 0) {sleepProbability = 0;}
        sleepProbability += foodProbabilty;
        int workProbability = 20 + tiredness + sleepProbability;
        int cursorProbability = 10 + workProbability;
        int walkProbability = 50 + cursorProbability;

        int target = (int)(Math.random() * walkProbability);
        System.out.println("TEST RANDOM DESTINY: " + target + "\n Food: " + foodProbabilty + "  Bed: " + sleepProbability + "  Lake: " + workProbability + "  Cursor: " + cursorProbability + "  Walk: " + walkProbability);
        if(target > cursorProbability){
            System.out.println("WALK RANDOMLY");
            chaseObject = "Random";
        } else if (target > workProbability) {
            System.out.println("CHASE CURSOR");
            chaseObject = "Cursor";
        } else if (target > sleepProbability) {
            System.out.println("GO TO WORK");
            chaseObject = "Lake";
        } else if (target > foodProbabilty && getBedOwned() == 1) {
            System.out.println("GO TO SLEEP");
            chaseObject = "Bed";
        } else {
            System.out.println("GO EAT");
            chaseObject = "Food";
        }

        if (hunger < 10){
            chaseObject = "Food";
        }

        Screen randomScreen = screens.get((int)(Math.random() * screens.size()));
        Point point = randomScreen.getRandomPoint();
        destinationX = point.x;
        destinationY = point.y;
        evaluateMs();
    }

    public void updateDestination(){
        switch(chaseObject) {
            case "Random":
                evaluateMs();
                break;
            case "Cursor":
                destinationX = cursorX - 84;
                destinationY = cursorY - 38;
                evaluateMs();
                break;
            case "Lake":
                destinationX = lake.getX() + 10;
                destinationY = lake.getY() + 50;
                evaluateMs();
                break;
            case "Bed":
                destinationX = bedPositionX + 40;
                destinationY = bedPositionY - 15;
                evaluateMs();
                break;
            case "Food":
                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i) != null) {
                        destinationX = foodList.get(i).getX() - 10;
                        destinationY = foodList.get(i).getY() - 10;
                        break;
                    }
                }
                evaluateMs();
                break;
            default:
                evaluateMs();
                break;
        }
    }

    public void evaluateMs(){
        if (state == States.WALK) {
            right = destinationX > positionX;
        }

        if(state == States.CHASE && right && destinationX < positionX){
            right = false;
            positionX = positionX - 60;
        }

        if(state == States.CHASE && !right && destinationX > positionX){
            right = true;
            positionX = positionX + 60;
        }

        float distanceX = Math.abs(destinationX - positionX);
        float distanceY = Math.abs(destinationY - positionY);

        if (distanceX > distanceY){
            if (chaseObject == "Cursor") {
                msX = 8;
                state = States.CHASE;
            } else if (state == States.PULL){
                msX = 2;
            } else {
                msX = 4;
            }
            float steps = distanceX / msX;
            msY = distanceY / steps;
        } else {
            if (chaseObject == "Cursor") {
                msY = 8;
                state = States.CHASE;
            } else  if (state == States.PULL){
                msY = 2;
            } else {
                msY = 4;
            }
            float steps = distanceY / msY;
            msX = distanceX / steps;
        }
    }

    public void changeSkin(){
        ArrayList<Integer> skinList =  new ArrayList<>();
        skinList.add(0);
        if(kurokami==1){
            skinList.add(1);
        }
        int index = skinList.indexOf(skin);
        try {
            skin = skinList.get(index+1);
        } catch (Exception e) {
            skin = 0;
        }
    }

    public void checkOutOfBounds(){
        Point object = new Point();
        object.setLocation(bedPositionX, bedPositionY);
        boolean bedVisible = false;
        for (int i = 0; i < screens.size() - 1; i++) {
            if(screens.get(i).checkIfVisible(object)){
               bedVisible = true;
            }
        }
        if(!bedVisible){
            bedPositionX = 0;
            bedPositionY = 0;
        }

        object.setLocation(wardrobePositionX, wardrobePositionY);
        boolean wardrobeVisible = false;
        for (int i = 0; i < screens.size() - 1; i++) {
            if(screens.get(i).checkIfVisible(object)){
                wardrobeVisible = true;
            }
        }
        if(!wardrobeVisible){
            wardrobePositionX = 0;
            wardrobePositionY = 0;
        }

        object.setLocation(radioPositionX, radioPositionY);
        boolean radioVisible = false;
        for (int i = 0; i < screens.size() - 1; i++) {
            if(screens.get(i).checkIfVisible(object)){
                radioVisible = true;
            }
        }
        if(!radioVisible){
            radioPositionX = 0;
            radioPositionY = 0;
        }
    }

    public void load(File save) {
        ArrayList<Integer> load = new ArrayList();
        try {
            Scanner scanner = new Scanner(save);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                load.add(Integer.parseInt(data));
            }
            scanner.close();
            hunger = load.get(0);
            tiredness = load.get(1);
            currency = load.get(2);
            goodRod = load.get(3);
            superRod = load.get(4);
            bedOwned = load.get(5);
            bedPositionX = load.get(6);
            bedPositionY = load.get(7);
            wardrobeOwned = load.get(8);
            wardrobePositionX = load.get(9);
            wardrobePositionY = load.get(10);
            kurokami = load.get(11);
            skin = load.get(12);
            radioOwned = load.get(13);
            radioPositionX = load.get(14);
            radioPositionY = load.get(15);
            chessSlowed = load.get(16);
        } catch (Exception e) {
            System.out.println("save corrupted"); // Soon Display options here, if restart or try to fix a save and restart the software
        }
        checkOutOfBounds();
    }

    @Override
    public String toString() {
        return hunger + "\n" + tiredness + "\n" + currency + "\n" + goodRod + "\n" + superRod + "\n" + bedOwned + "\n" +
                bedPositionX + "\n" + bedPositionY + "\n" + wardrobeOwned + "\n" + wardrobePositionX + "\n" +
                wardrobePositionY + "\n" + kurokami + "\n" + skin + "\n" + radioOwned + "\n" + radioPositionX + "\n" +
                radioPositionY + "\n" + chessSlowed;
    }

}