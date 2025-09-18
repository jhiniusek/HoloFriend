package holo;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FriendStats {
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
    private GameWindow gameWindow;
    private WinDef.HWND window;
    private WinDef.RECT rect = new WinDef.RECT();
    WinUser.WINDOWPLACEMENT placement = new WinUser.WINDOWPLACEMENT();
    private int windowSide;
    private int windowCatchPoint;
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

    public FriendStats() throws InterruptedException {
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

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public WinDef.HWND getWindow() {
        return window;
    }

    public void setWindow(WinDef.HWND window) {
        this.window = window;
    }

    public WinDef.RECT getRect() {
        return rect;
    }

    public void setRect(WinDef.RECT rect) {
        this.rect = rect;
    }

    public int getWindowCatchPoint() {
        return windowCatchPoint;
    }

    public int getWindowSide() {
        return windowSide;
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
        int foodProbability = 100 - hunger;
        int sleepProbability = 50 - tiredness;
        if (sleepProbability < 0) {sleepProbability = 0;}
        sleepProbability += foodProbability;
        int workProbability = sleepProbability;
        if(ableToWork){
            workProbability = 20 + tiredness + sleepProbability;
        }
        int cursorProbability = 10 + workProbability;
        int windowProbability = 10 + cursorProbability;
        int walkProbability = 50 + windowProbability;

        int target = (int)(Math.random() * walkProbability);
        System.out.println("TEST RANDOM DESTINY: " + target + "\n Food: " + foodProbability + "  Bed: " + sleepProbability + "  Lake: " + workProbability + "  Cursor: " + cursorProbability + "  Window: " + windowProbability + "  Walk: " + walkProbability);
        if(target > windowProbability){
            System.out.println("WALK RANDOMLY");
            chaseObject = "Random";
        } else if (target > cursorProbability) {
            System.out.println("MOVE WINDOW");
            chaseObject = "Window";
            window = chooseRandomWindow();
        } else if (target > workProbability) {
            System.out.println("CHASE CURSOR");
            chaseObject = "Cursor";
        } else if (target > sleepProbability) {
            System.out.println("GO TO WORK");
            chaseObject = "Lake";
        } else if (target > foodProbability && getBedOwned() == 1) {
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
                if (state == States.CHASE) {
                    destinationX = cursorX - 84;
                    destinationY = cursorY - 38;
                }
                evaluateMs();
                break;
            case "Window":
                if(window == null){
                    chaseObject = "GameWindow";
                    break;
                }
                if (state == States.WALK) {
                    updateWindowPosition();
                    if(windowSide == 0){
                        destinationX = rect.left - 53;
                        destinationY = rect.top + windowCatchPoint - 53;
                    } else {
                        destinationX = rect.right - 15;
                        destinationY = rect.top + windowCatchPoint - 53;
                    }
                }
                evaluateMs();
                break;
            case "GameWindow":
                if (state == States.WALK) {
                    updateWindowPosition();
                    if(windowSide == 0){
                        destinationX = gameWindow.getX() - 53;
                        destinationY = gameWindow.getY() + windowCatchPoint - 53;
                    } else {
                        destinationX = gameWindow.getX() + 300 - 15;
                        destinationY = gameWindow.getY() + windowCatchPoint - 53;
                    }
                }
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
                for (Food food : foodList) {
                    if (food != null) {
                        destinationX = food.getX() - 10;
                        destinationY = food.getY() - 10;
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
        if (state == States.WALK || state == States.PULL || state == States.PUSH) {
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
            if (chaseObject == "Cursor" && (state == States.WALK || state == States.CHASE)) {
                msX = 8;
                state = States.CHASE;
            } else if (state == States.PULL || state == States.PUSH){
                msX = 2;
            } else {
                msX = 4;
            }
            float steps = distanceX / msX;
            msY = distanceY / steps;
        } else {
            if (chaseObject == "Cursor" && (state == States.WALK || state == States.CHASE)) {
                msY = 8;
                state = States.CHASE;
            } else  if (state == States.PULL || state == States.PUSH){
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

    private WinDef.HWND chooseRandomWindow(){
        List<WinDef.HWND> windows = new ArrayList<>();
        int WS_EX_TOOLWINDOW = 0x00000080;

        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            @Override
            public boolean callback(WinDef.HWND hWnd, Pointer data) {
                if (User32.INSTANCE.IsWindowVisible(hWnd) && User32.INSTANCE.IsWindowEnabled(hWnd)) {
                    char[] buffer = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, buffer, 512);
                    String windowTitle = Native.toString(buffer);

                    if (!windowTitle.isBlank() && User32.INSTANCE.IsWindowVisible(hWnd)) {
                        WinDef.HWND root = User32.INSTANCE.GetAncestor(hWnd, WinUser.GA_ROOT);
                        if (!hWnd.equals(root)) {
                            return true;
                        }

                        char[] className = new char[512];
                        User32.INSTANCE.GetClassName(hWnd, className, 512);
                        String wndClass = Native.toString(className);

                        if (wndClass.equals("ApplicationFrameWindow")) {
                            WinDef.HWND child = User32.INSTANCE.FindWindowEx(hWnd, null, "Windows.UI.Core.CoreWindow", null);
                            if (child == null || !User32.INSTANCE.IsWindowVisible(child)) {
                                return true;
                            }
                        }

                        int style = User32.INSTANCE.GetWindowLong(hWnd, WinUser.GWL_STYLE);
                        int exStyle = User32.INSTANCE.GetWindowLong(hWnd, WinUser.GWL_EXSTYLE);

                        boolean hasTitleBar = (style & WinUser.WS_CAPTION) != 0;
                        boolean hasSysMenu  = (style & WinUser.WS_SYSMENU) != 0;
                        boolean maximized = (style & WinUser.WS_MAXIMIZE) != 0;
                        boolean minimized = (style & WinUser.WS_MINIMIZE) != 0;
                        boolean isToolWindow = (exStyle & WS_EX_TOOLWINDOW) != 0;

                        if (hasTitleBar && hasSysMenu && !isToolWindow && !maximized && !minimized) {
                            System.out.println("Found: " + windowTitle);
                            windows.add(hWnd);
                        }
                    }
                }
                return true;
            }
        }, null);

        WinDef.HWND randomWindow = null;
        Random rand = new Random();

        if (!windows.isEmpty()) {
            randomWindow = windows.get(rand.nextInt(windows.size()));
            User32.INSTANCE.GetWindowRect(randomWindow, rect);
            if(rect.left == -32000){
                User32.INSTANCE.ShowWindow(randomWindow, WinUser.SW_RESTORE);
                User32.INSTANCE.GetWindowRect(randomWindow, rect);
                User32.INSTANCE.ShowWindow(randomWindow, WinUser.SW_MINIMIZE);
            }
            int height = rect.bottom - rect.top;
            windowSide = rand.nextInt(2); // 0 -> left   1 -> right
            windowCatchPoint = rand.nextInt(height);
        } else {
            windowSide = rand.nextInt(2); // 0 -> left   1 -> right
            windowCatchPoint = rand.nextInt(gameWindow.getHeight());
        }
        return randomWindow;
    }

    public void updateWindowPosition(){
        if (chaseObject == "Window") {
            if (!User32.INSTANCE.IsWindow(window)) {
                state = States.WALK;
                window = null;
                rect.clear();
            }

            User32.INSTANCE.GetWindowPlacement(window, placement);

            boolean minimized = (placement.showCmd == WinUser.SW_SHOWMINIMIZED);

            if(!minimized){
                User32.INSTANCE.GetWindowRect(window, rect);
                int height = rect.bottom - rect.top;
                if(windowCatchPoint > height){
                    Random rand = new Random();
                    windowCatchPoint = rand.nextInt(height);
                }
            }
        } else if (chaseObject == "GameWindow") {
            if(windowCatchPoint >  gameWindow.getHeight()){
                Random rand = new Random();
                windowCatchPoint = rand.nextInt(gameWindow.getHeight());
            }
            rect.left = gameWindow.getX();
            rect.top = gameWindow.getY();
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