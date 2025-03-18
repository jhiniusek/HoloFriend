package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class NewFrend extends JFrame{

    private Point initialClick;
    public JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/sprites/IdleL1.png")));
    private FrendStats stats;

    public NewFrend(FrendStats stats) throws HeadlessException {
        this.stats = stats;
        this.setSize(64,128);
        this.add(icon);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0f));
        this.setVisible(true);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                stats.setState(States.HOLD);
            }

            public void mouseReleased(MouseEvent e) {
                stats.setState(States.WALK);
                stats.evaluateMs();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
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
