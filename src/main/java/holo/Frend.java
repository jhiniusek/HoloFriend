package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frend extends JFrame{


    public JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/sprites/IdleL.gif")));
    public JLabel shadow = new JLabel(new ImageIcon(getClass().getResource("/sprites/Shadow.png")));
    private FrendStats stats;
    private Point initialClick;
    private Boolean holding;

    public Frend(FrendStats stats) throws HeadlessException {
        this.stats = stats;
        this.setSize(64,128);
        this.setLayout(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        shadow.setBounds(0,116,60,11);
        this.add(shadow);
        icon.setBounds(0,0,64,126);
        this.add(icon);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0f));
        this.setVisible(true);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                holding = false;

            }

            public void mouseReleased(MouseEvent e) {
                if (!holding) {
                    System.out.println("Frend clicked");
                } else {
                    stats.setState(States.WALK);
                    stats.evaluateMs();
                }
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                holding = true;
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
        });
    }
}
