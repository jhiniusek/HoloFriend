package holo;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Collab extends JFrame {
    private FriendStats stats;
    public float positionX;
    public float positionY;
    private float msX;
    private float msY;
    private float destinationX;
    private float destinationY;
    private boolean right = true;
    private boolean isActive = false;

    private String chaseObject = "";
    private GameWindow gameWindow;
    private WinDef.HWND window;
    private WinDef.RECT rect = new WinDef.RECT();
    WinUser.WINDOWPLACEMENT placement = new WinUser.WINDOWPLACEMENT();
    private int windowSide;
    private int windowCatchPoint;
    private boolean pullUp = false;
    private float topFix;
    private float botFix;
    private Point mousePoint = new Point (0,0);

    private int danceLenght = 0;
    private int danceTimer = 0;

    private int id;
    private int chatOption = 1;
    private String name;
    private int level = 1;
    private JLabel levelIcon;
    private int experience;
    private JProgressBar expBar;
    private ArrayList<Gift> listOfGifts;
    private boolean receivedGift = false;
    private long time = 0;
    private States state = States.IDLE;
    private int idleTimer;

    private JLabel sprite;
    private String newSprite;
    private String spritePath;
    private JLabel shadow = new JLabel(new ImageIcon(getClass().getResource("/sprites/Shadow.png")));

    Point initialClick;
    long clickTime;
    long releaseTime;
    int clickCounter;

    @Override
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public void setGameWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getChatOption() {
        return chatOption;
    }

    public void setChatOption(int chatOption) {
        this.chatOption = chatOption;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        stats.updateLevel(id, level);
    }

    public JLabel getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(JLabel levelIcon) {
        this.levelIcon = levelIcon;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
        stats.updateExp(id, experience);
    }

    public JProgressBar getExpBar() {
        return expBar;
    }

    public void setExpBar(JProgressBar expBar) {
        this.expBar = expBar;
    }

    public long getTime() {
        return time;
    }

    public boolean checkCooldown(){
        long now = System.currentTimeMillis();
                       //10 min
        if(now - time > 600000){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTimeUp(){
        long now = System.currentTimeMillis();
        //10 min
        switch(level){
            case 5:
                return true;
            case 4:             //25 min
                if(now - time > 1500000){
                    return true;
                } else {
                    return false;
                }
            case 3:             //10 min
                if(now - time > 600000){
                    return true;
                } else {
                    return false;
                }
            case 2:             //5 min
                if(now - time > 300000){
                    return true;
                } else {
                    return false;
                }
            case 1:             //2 min
                if(now - time > 120000){
                    return true;
                } else {
                    return false;
                }
        }


        if(now - time > 600000){
            return true;
        } else {
            return false;
        }
    }

    public void setTime(long time) {
        this.time = time;
        stats.updateTime(id, time);
    }

    public States getCollabState() {
        return state;
    }

    public void setCollabState(States state) {
        this.state = state;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public Collab(FriendStats stats, String name, int id, ArrayList<Gift> listOfGifts){
        this.stats = stats;
        this.name = name;
        this.id = id;
        this.listOfGifts = listOfGifts;
        try {
            setLevel(stats.getLevelById(id));
        } catch (Exception e) {
            System.out.println("Collab " + name + " could not load Level");
            setLevel(1);
        }
        try {
            setExperience(stats.getExpById(id));
        } catch (Exception e) {
            System.out.println("Collab " + name + " could not load EXP");
            setExperience(0);
        }
        try {
            setTime(stats.getTimeById(id));
        } catch (Exception e) {
            System.out.println("Collab " + name + " could not load Time");
        }

        stats.updateLevel(id, level);
        stats.updateExp(id, experience);
        stats.updateTime(id, time);

        String path = "/sprites/collabs/" + name;
        sprite = new JLabel(new ImageIcon(getClass().getResource(path + "/IdleR.gif")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(60,129); //Different sizes for different names
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        shadow.setBounds(1,118,60,11);
        add(shadow);
        sprite.setBounds(0,0,60,129);
        add(sprite);
        getContentPane().setComponentZOrder(sprite, 0);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        setVisible(false);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                clickTime = System.currentTimeMillis();

            }

            public void mouseReleased(MouseEvent e) {
                releaseTime = System.currentTimeMillis();
                if (releaseTime - clickTime < 100) {
                    System.out.println(name + " clicked");
                    clickCounter++;
                }
                try {
                    Thread.sleep(75);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                if(state == States.HOLD){
                    try {
                        Thread.sleep(0);        ///??? it bugs without this for some reason
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                        state = States.WALK;
                }
                evaluateMs();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (state != States.PULL && state != States.END) {
                    state = States.HOLD;

                    int thisX = getLocation().x;
                    int thisY = getLocation().y;

                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;

                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    setLocation(X, Y);
                    positionX = X;
                    positionY = Y;
                }
            }
        });
    }

    public void start(){
        isActive = true;
        receivedGift = false;
        state = States.WALK;
        positionX = -100;
        positionY = stats.getRandomY();
        setLocation((int)positionX, (int)positionY);
        destinationX = 100;
        destinationY = positionY;
        evaluateMs();
        idleTimer = 60;
    }

    public void end(){
        state = States.END;
        destinationX = -100;
        destinationY = stats.getRandomY();
        evaluateMs();
    }

    public void updateSprite(){
        setLocation((int)positionX, (int)positionY);

        if(state == States.IDLE){
            setSize(60,129);
            sprite.setBounds(0,0,60,129);
            shadow.setBounds(1,118,60,11);
            if(right){
                newSprite = "/sprites/collabs/"+name+"/IdleR.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }

            } else {
                newSprite = "/sprites/collabs/"+name+"/IdleL.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }

        } else if (state == States.WALK || state == States.END) {
            setSize(60,129);
            sprite.setBounds(0,0,60,129);
            shadow.setBounds(1,118,60,11);
            if(right){
                newSprite = "/sprites/collabs/"+name+"/WalkR.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            } else {
                newSprite = "/sprites/collabs/"+name+"/WalkL.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }

        } else if (state == States.CHASE) {
            setSize(163,127);
            sprite.setBounds(0,0,163,127);
            evaluateMs();
            if(right){
                shadow.setBounds(6,117,60,11);
                newSprite = "/sprites/collabs/"+name+"/ChaseR.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            } else {
                shadow.setBounds(100,117,60,11);
                newSprite = "/sprites/collabs/"+name+"/ChaseL.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }

        }else if (state == States.HOLD) {
            setSize(60,129);
            sprite.setBounds(0,0,60,129);
            shadow.setBounds(0,119,60,11);
            if(right){
                newSprite = "/sprites/collabs/"+name+"/HoldR.png";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            } else {
                newSprite = "/sprites/collabs/"+name+"/HoldL.png";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        } else if (state == States.PULL) {
            setSize(60,129);
            sprite.setBounds(0,0,60,129);
            shadow.setBounds(0,119,60,11);
            if(right){
                newSprite = "/sprites/collabs/"+name+"/PullR.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            } else {
                newSprite = "/sprites/collabs/"+name+"/PullL.gif";
                if (!newSprite.equals(spritePath)) {
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        } else if (state == States.PUSH) {
            setSize(72,129);
            sprite.setBounds(0,0,72,129);
            if(right){
                shadow.setBounds(0,119,60,11);
                newSprite = "/sprites/collabs/"+name+"/PushR.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            } else {
                shadow.setBounds(14,119,60,11);
                newSprite = "/sprites/collabs/"+name+"/PushL.gif";
                if (!newSprite.equals(spritePath)) {
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }
            }
        }
    }

    public void doStep(){
        updateDestination();
        if(!receivedGift){
            CheckGiftCollision();
        }
        if(checkTimeUp() && state != States.END){
            end();
        }
        if((positionX != destinationX || positionY != destinationY) && (state==States.WALK || state==States.CHASE || state==States.PULL || state==States.PUSH || state==States.END)) {

            if (positionX > destinationX){

                if ((positionX - destinationX) < msX) {
                    positionX = destinationX;
                } else {
                    positionX = (positionX - msX);
                }

            } else if (positionX < destinationX){

                if ((destinationX - positionX) < msX) {
                    positionX = destinationX;
                } else {
                    positionX = (positionX + msX);
                }

            }

            if (positionY > destinationY){

                if ((positionY - destinationY) < msY) {
                    positionY = destinationY;
                } else {
                    positionY = (positionY - msY);
                }

            } else if (positionY < destinationY){

                if ((destinationY - positionY) < msY) {
                    positionY = destinationY;
                } else {
                    positionY = (positionY + msY);
                }

            }
        }

        if(positionX == destinationX && positionY == destinationY && (state==States.PULL || state==States.PUSH)){
            window = null;
            rect.clear();
            state = States.IDLE;
            gameWindow.setAlwaysOnTop(false);
        }

        if(positionX == destinationX && positionY == destinationY && (state==States.WALK || state==States.CHASE)){
            if (chaseObject == "Cursor") {
                state=States.PULL;
                int pullDistanceX = (int)(Math.random() * 200 + 200);
                int pullDistanceY = (int)(Math.random() * 50 + 50);
                if(right){
                    positionX = (positionX + 20);
                    destinationX = (destinationX - pullDistanceX);
                } else {
                    positionX = (positionX + 80);
                    destinationX = (destinationX + pullDistanceX);
                }
                if(pullDistanceY % 2 == 0){
                    destinationY = (destinationY + pullDistanceY);
                } else {
                    destinationY = (destinationY - pullDistanceY);
                }
            } else if (chaseObject == "Window" || chaseObject == "GameWindow") {
                Random rand = new Random();
                int PushOrPull = rand.nextInt(2);
                int distanceX = (int)(Math.random() * 200 + 200);
                int distanceY = (int)(Math.random() * 50 + 50);
                topFix = rect.top;
                botFix = rect.bottom;
                if(windowSide == 0){
                    switch (PushOrPull){
                        case 0:
                            state = States.PULL;
                            destinationX = (destinationX - distanceX);
                            break;
                        case 1:
                            state = States.PUSH;
                            destinationX = (destinationX + distanceX);
                            break;
                    }
                } else {
                    switch (PushOrPull){
                        case 0:
                            state = States.PULL;
                            destinationX = (destinationX + distanceX);
                            break;
                        case 1:
                            state = States.PUSH;
                            destinationX = (destinationX - distanceX);
                            break;
                    }
                }
                if(distanceY % 2 == 0){
                    destinationY = (destinationY + distanceY);
                    pullUp = false;
                } else {
                    destinationY = (destinationY - distanceY);
                    pullUp = true;
                }
                evaluateMs();
                User32.INSTANCE.ShowWindow(window, WinUser.SW_RESTORE);
                User32.INSTANCE.BringWindowToTop(window);
                User32.INSTANCE.SetForegroundWindow(window);

            } else {
                state = States.IDLE;
            }
        }

        if(idleTimer != 0 && state == States.IDLE){
            idleTimer--;
        }

        if (positionX == destinationX && positionY == destinationY && state == States.END) {
            setTime(System.currentTimeMillis());
            chatOption = 1;
            setVisible(false);
            isActive=false;
        }

        if (positionX == destinationX && positionY == destinationY && idleTimer == 0) {
            idleTimer = (int)(Math.random() * 80 + 64);
            chooseDestination();
            state = States.WALK;
        }

        if (state == States.IDLE && idleTimer == 0 && (positionX != destinationX || positionY != destinationY)){
            evaluateMs();
            state = States.WALK;
        }

        // PULL CURSOR

        if(state == States.PULL && chaseObject == "Cursor"){
            if(right){
                mousePoint.x = (int) (positionX + 6);
                mousePoint.y = (int) (positionY + 43);
            } else {
                mousePoint.x = (int) (positionX + 53);
                mousePoint.y = (int) (positionY + 43);
            }
            moveMouse(mousePoint);
        }

        // MOVE WINDOW

        if((state == States.PULL || state == States.PUSH) && chaseObject == "Window") {
            if (!User32.INSTANCE.IsWindow(window)) {
                state = States.WALK;
                window = null;
                rect.clear();
            }

            if(right){
                rect.left += (int) msX;
                rect.right += (int) msX;
            } else {
                rect.left -= (int) msX;
                rect.right -= (int) msX;
            }
            if(pullUp){
                topFix -= msY;
                botFix -= msY;
            } else {
                if (rect.top != 0) {
                    topFix += msY;
                    botFix += msY;
                }
            }
            rect.top = (int)topFix;
            rect.bottom = (int)botFix;

            User32.INSTANCE.SetWindowPos(window, null, rect.left, rect.top, (rect.right - rect.left), (rect.bottom - rect.top), 0);
            User32.INSTANCE.ShowWindow(window, WinUser.SW_RESTORE);
        }

        if((state == States.PULL || state == States.PUSH) && chaseObject == "GameWindow"){
            if(right){
                rect.left += (int) msX;
            } else {
                rect.left -= (int) msX;
            }
            if(pullUp){
                topFix -= msY;
            } else {
                if (rect.top != 0) {
                    topFix += msY;
                }
            }
            rect.top = (int)topFix;
            gameWindow.setLocation(rect.left, rect.top);
            gameWindow.setAlwaysOnTop(true);
        }

        //DANCING

        if(state == States.DANCE){
            String currentDance = stats.getCurrentTrack();
            if(currentDance == "NoDisk"){
                danceLenght = 140;

                newSprite = "/sprites/collabs/"+name+"/IdleL.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }

                if(danceTimer == danceLenght){
                    state = States.IDLE;
                }
                danceTimer++;
            }
            if(currentDance == "RatDance"){
                setSize(60,126);
                sprite.setBounds(0,0,60,126);
                danceLenght = 2176;

                newSprite = "/sprites/collabs/"+name+"/RatDance.gif";
                if(!newSprite.equals(spritePath)){
                    sprite.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(newSprite))));
                    spritePath = newSprite;
                }

                if(danceTimer == danceLenght){
                    state = States.IDLE;
                }
                danceTimer++;
            }
        } else {
            danceTimer = 0;
        }
    }

    public void chooseDestination(){
        int cursorProbability = stats.getChaseCursorValue();
        int windowProbability = stats.getChaseWindowValue() + cursorProbability;
        int walkProbability = 50 + windowProbability;

        int target = (int)(Math.random() * walkProbability);
        System.out.println("TEST RANDOM DESTINY: " + target + "  Cursor: " + cursorProbability + "  Window: " + windowProbability + "  Walk: " + walkProbability);
        if(target > windowProbability){
            System.out.println("WALK RANDOMLY");
            chaseObject = "Random";
        } else if (target > cursorProbability) {
            System.out.println("MOVE WINDOW");
            chaseObject = "Window";
            window = chooseRandomWindow();
        } else {
            System.out.println("CHASE CURSOR");
            chaseObject = "Cursor";
        }

        Point point = stats.getRandomPoint();
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
                    destinationX = stats.getCursorX() - 80;
                    destinationY = stats.getCursorY() - 47;
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
            default:
                evaluateMs();
                break;
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

    public void evaluateMs(){
        if (state == States.WALK || state == States.PULL || state == States.PUSH || state == States.END) {
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
    }

    private void CheckGiftCollision(){
        for(int i = 0; i < listOfGifts.size(); i++){
            int x = -10;
            int y = -10;
            try {
                x = listOfGifts.get(i).getX();
                y = listOfGifts.get(i).getY();
                int min_x = (int)positionX;
                int max_x = min_x + 48;

                int min_y = (int)positionY - 16;
                int max_y = min_y + 128;

                if (x > min_x && x < max_x){
                    if (y > min_y && y < max_y){
                        if (listOfGifts.get(i).isHolding()) {
                            Random random = new Random();
                            switch(name){
                                case "mio":
                                    String gift = listOfGifts.get(i).getName();
                                    if(Objects.equals(gift, "Sukonbu") || Objects.equals(gift, "Miteru") || Objects.equals(gift, "Oruyanke")){
                                        gainExperience(random.nextInt(60, 80));
                                    }
                                    else if(Objects.equals(gift, "MioFamNeko") || Objects.equals(gift, "MioFamOokami")){
                                        gainExperience(random.nextInt(40, 60));
                                    } else {
                                        gainExperience(random.nextInt(50, 70));
                                    }
                                    break;
                                case "korone":
                                    break;
                            }
                            listOfGifts.get(i).dispose();
                            listOfGifts.remove(i);
                            receivedGift = true;
                            break;
                        }
                    }
                }
            } catch (Exception e){
            }
        }
    }

    private void gainExperience(int amount){
        // Level 1
        // Level 2  =  100exp
        // Level 3  =  250exp
        // Level 4  =  500exp
        // Level 5  =  1000exp
        // Total    =  1850exp

        setExperience(getExperience() + amount);

        if(getExperience() >= 100){
            if(getExperience() >= 350){
                if(getExperience() >= 850){
                    if(getExperience() >= 1850){
                        setLevel(5);
                        levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/level5.png")));
                        expBar.setValue(100);
                    } else {
                        setLevel(4);
                        levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/level4.png")));
                        expBar.setValue((int) ((((float)getExperience() - 850)/1000)*100));
                    }
                } else {
                    setLevel(3);
                    levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/level3.png")));
                    expBar.setValue((int) ((((float)getExperience() - 350)/500)*100));
                }
            } else {
                setLevel(2);
                levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/level2.png")));
                expBar.setValue((int) ((((float)getExperience() - 100)/250)*100));
            }
        } else {
            setLevel(1);
            levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/level1.png")));
            expBar.setValue(getExperience());
        }

    }

    @Override
    public String toString(){
        return "";
    }

}
