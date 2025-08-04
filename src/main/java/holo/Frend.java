package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frend extends JFrame{


    public JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/basic/IdleL.gif")));
    public JLabel shadow = new JLabel(new ImageIcon(getClass().getResource("/sprites/Shadow.png")));
    private Point initialClick;
    private FrendStats stats;
    long clickTime;
    long releaseTime;

    public Frend(FrendStats stats) throws HeadlessException {
        this.stats = stats;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
        setSize(64,128);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        shadow.setBounds(0,116,60,11);
        add(shadow);
        sprite.setBounds(0,0,64,126);
        add(sprite);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        setVisible(true);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                clickTime = System.currentTimeMillis();

            }

            public void mouseReleased(MouseEvent e) {
                releaseTime = System.currentTimeMillis();
                if (releaseTime - clickTime < 100) {
                    System.out.println("Frend clicked");
                    stats.setClickCounter(stats.getClickCounter() + 1);
                }
                try {
                    Thread.sleep(75);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                if(stats.getState() == States.HOLD){
                    try {
                        Thread.sleep(0);        ///??? it bugs without this for some reason
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(stats.getState() != States.SLEEP || stats.getState() != States.WORK){
                        stats.setState(States.WALK);
                    }
                }
                stats.evaluateMs();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (stats.getState() != States.PULL) {
                    stats.setState(States.HOLD);

                    int thisX = getLocation().x;
                    int thisY = getLocation().y;

                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;

                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    setLocation(X, Y);
                    stats.setPositionX(X);
                    stats.setPositionY(Y);
                }
            }
        });
    }
}
