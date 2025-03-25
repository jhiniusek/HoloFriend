package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame{
    public JProgressBar hungerBar = new JProgressBar(0,100);
    public JProgressBar tiredBar = new JProgressBar(0,100);
    private Point initialClick;

    public GameWindow(FrendStats stats, ArrayList<Food> foodList) {
        Map<Integer, Point> burgerMap = new HashMap<Integer, Point>();
        burgerMap.put(0, new Point(80,210));
        burgerMap.put(1, new Point(128,210));
        burgerMap.put(2, new Point(176,210));
        burgerMap.put(3, new Point(104,186));
        burgerMap.put(4, new Point(152,186));
        burgerMap.put(5, new Point(128,162));


        this.setSize(500,350);
        this.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0f));
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/sprites/Menu.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        this.add(imageLabel);

        hungerBar.setValue(stats.getHunger());
        hungerBar.setBounds(224,104,160,16);
        hungerBar.setForeground(new Color(39,199,225));
        this.add(hungerBar);
        this.getContentPane().setComponentZOrder(hungerBar, 0);


        tiredBar.setValue(stats.getTiredness());
        tiredBar.setBounds(224,152,160,16);
        tiredBar.setForeground(new Color(39,199,225));
        this.add(tiredBar);
        this.getContentPane().setComponentZOrder(tiredBar, 0);


        JButton quitButton = new JButton();
        quitButton.setBounds(235, 35, 40, 40);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setFocusPainted(false);
        quitButton.setOpaque(false);
        quitButton.addActionListener(e -> {
            JFrame byeFrend = new JFrame("Bye Frend");
            byeFrend.setSize(240, 150);
            byeFrend.setLayout(null);
            byeFrend.setLocationRelativeTo(null);

            JLabel label = new JLabel("Are you sure you want to quit?", SwingConstants.CENTER);
            label.setBounds(20, 10, 190, 40);
            byeFrend.add(label);

            JButton closeButton = new JButton("YES");
            closeButton.setBounds(40, 60, 60, 30);
            byeFrend.add(closeButton);

            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            byeFrend.setVisible(true);
            JButton stayButton = new JButton("NO");
            stayButton.setBounds(125, 60, 60, 30);
            byeFrend.add(stayButton);

            stayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    byeFrend.dispose();
                }
            });
            byeFrend.setVisible(true);
        });
        this.add(quitButton);


        JButton foodSpawner = new JButton();
        foodSpawner.setBounds(64, 241, 144, 48);
        foodSpawner.setContentAreaFilled(false);
        foodSpawner.setBorderPainted(false);
        foodSpawner.setFocusPainted(false);
        foodSpawner.setOpaque(false);
        foodSpawner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < foodList.size(); i++){
                    if(foodList.get(i) == null){
                        Food food = new Food((int) (getLocation().x + burgerMap.get(i).getX()), (int) (getLocation().y + burgerMap.get(i).getY()));
                        foodList.set(i, food);
                        break;
                    }
                }
            }
        });
        this.add(foodSpawner);


        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                for (int i = 0; i < foodList.size(); i++) {
                    try {
                        if(!foodList.get(i).moved){
                            int thisX = foodList.get(i).getLocation().x;
                            int thisY = foodList.get(i).getLocation().y;

                            int xMoved = e.getX() - initialClick.x;
                            int yMoved = e.getY() - initialClick.y;

                            int X = thisX + xMoved;
                            int Y = thisY + yMoved;
                            foodList.get(i).setLocation(X, Y);
                        }
                    } catch (Exception ex) {
                    }
                }
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
        this.setVisible(true);


        stats.setPositionX(this.getX()+226);
        stats.setDestinationX(this.getX()+226);
        stats.setPositionY(this.getY()+209);
        stats.setDestinationY(this.getY()+209);

    }
}
