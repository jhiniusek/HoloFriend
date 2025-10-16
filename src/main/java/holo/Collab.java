package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Time;
import java.sql.Timestamp;

public class Collab extends JFrame {
    private FriendStats stats;
    public float positionX;
    public float positionY;
    private float msX;
    private float msY;
    private float destinationX;
    private float destinationY;
    private int id;
    private int chatOption = 1;
    private String name;
    private int level;
    private int experience;
    private long time;
    private States state = States.IDLE;
    private JLabel sprite;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
        stats.updateExp(id, experience);
    }

    public long getTime() {
        return time;
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

    public Collab(FriendStats stats, String name, int id){
        this.stats = stats;
        this.name = name;
        this.id = id;
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
        sprite = new JLabel(new ImageIcon(getClass().getResource(path + "/IdleL.gif")));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(60,129); //Different sizes for different names
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        shadow.setBounds(1,118,60,11);
        add(shadow);
        sprite.setBounds(0,0,64,126);
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
                if (state != States.PULL) {
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

    public void evaluateMs(){

    }

    @Override
    public String toString(){
        return "";
    }

}
