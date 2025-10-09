package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Collab extends JFrame {
    public float positionX;
    public float positionY;
    private float msX;
    private float msY;
    private float destinationX;
    private float destinationY;
    private String name;
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

    public Collab(String name){
        this.name = name;
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

}
