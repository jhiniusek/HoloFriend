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
    private boolean ableToWork = true;
    private boolean ableToSleep = true;
    String[] skins = new String[] {"basic/", "kurokami/"};
    private String currentTrack = "NoDisk";

    //EQUIPMENT
    private int goodRod = 0;
    private int superRod = 0;

    private int bed = 0;
    public int bedPositionX = 0;
    public int bedPositionY = 0;

    private int wardrobe = 0;
    public int wardrobePositionX = 0;
    public int wardrobePositionY = 0;
    private int kurokami = 0;
    private int skin = 0;

    private int radio = 0;
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

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(int wardrobe) {
        this.wardrobe = wardrobe;
    }

    public int getKurokami() {
        return kurokami;
    }

    public void setKurokami(int kurokami) {
        this.kurokami = kurokami;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
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
        Screen randomScreen = screens.get((int)(Math.random() * screens.size()));
        Point point = randomScreen.getRandomPoint();
        destinationX = point.x;
        destinationY = point.y;
        evaluateMs();
    }

    public void evaluateMs(){
        right = destinationX > positionX;

        float distanceX = Math.abs(destinationX - positionX);
        float distanceY = Math.abs(destinationY - positionY);

        if (distanceX > distanceY){
            msX = 4;
            float steps = distanceX / msX;
            msY = distanceY / steps;
        } else {
            msY = 4;
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
            bed = load.get(5);
            bedPositionX = load.get(6);
            bedPositionY = load.get(7);
            wardrobe = load.get(8);
            wardrobePositionX = load.get(9);
            wardrobePositionY = load.get(10);
            kurokami = load.get(11);
            skin = load.get(12);
            radio = load.get(13);
            radioPositionX = load.get(14);
            radioPositionY = load.get(15);
            chessSlowed = load.get(16);
        } catch (Exception e) {
            System.out.println("save corrupted"); // Display options here, if restart or try to fix a save and restart the software
        }
    }

    @Override
    public String toString() {
        return hunger + "\n" + tiredness + "\n" + currency + "\n" + goodRod + "\n" + superRod + "\n" + bed + "\n" +
                bedPositionX + "\n" + bedPositionY + "\n" + wardrobe + "\n" + wardrobePositionX + "\n" +
                wardrobePositionY + "\n" + kurokami + "\n" + skin + "\n" + radio + "\n" + radioPositionX + "\n" +
                radioPositionY + "\n" + chessSlowed;
    }

}
