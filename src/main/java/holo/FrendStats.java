package holo;

import java.awt.*;

public class FrendStats {
    private int hunger = 10;
    private int tiredness = 10;
    private boolean alive = true;
    private int screenWidth;
    private int screenHeight;
    private float positionX = 500;
    private float positionY = 500;
    private float destinationX = 500;
    private float destinationY = 500;
    private float msX = 4;
    private float msY = 0;
    private boolean right = false;
    private int cursorX = 0;
    private int cursorY = 0;
    private int animationFrame = 1;
    private States state = States.IDLE;

    public FrendStats() {
        getMonitorSizes();
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

    public int getAnimationFrame() {
        return animationFrame;
    }

    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
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

    private void getMonitorSizes() {
        int width = 0;
        int height = 0;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[]    gs = ge.getScreenDevices();
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();
            width += dm.getWidth();
            height += dm.getHeight();
        }
        screenWidth = width;
        screenHeight = (height/gs.length);
    }

    public void chooseDestination(){
        int max_x = screenWidth - 64;
        int max_y = screenHeight - 128;
        destinationX = (int)(Math.random() * max_x + 1);
        destinationY = (int)(Math.random() * max_y + 1);
        this.right = destinationX > positionX;

        float distance_x = Math.abs(destinationX - positionX);
        float distance_y = Math.abs(destinationY - positionY);

        if (distance_x > distance_y){
            msX = 4;
            float steps = distance_x / msX;
            msY = distance_y / steps;
        } else {
            msY = 4;
            float steps = distance_y / msY;
            msX = distance_x / steps;
        }


    }
}
