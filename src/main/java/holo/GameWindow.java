package holo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame{
    public JProgressBar hungerBar = new JProgressBar(0,100);
    public JProgressBar tiredBar = new JProgressBar(0,100);
    public JLabel currency = new JLabel();
    private Point initialClick;

    public GameWindow(FrendStats stats, ArrayList<Food> foodList, Shop shop) throws IOException, FontFormatException {
        Map<Integer, Point> burgerMap = new HashMap<Integer, Point>();
        burgerMap.put(0, new Point(178,230));
        burgerMap.put(1, new Point(219,230));
        burgerMap.put(2, new Point(260,230));
        burgerMap.put(3, new Point(178,207));
        burgerMap.put(4, new Point(219,207));
        burgerMap.put(5, new Point(260,207));


        setSize(306,346);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/sprites/Menu.png"));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel);


        hungerBar.setValue(stats.getHunger());
        hungerBar.setBounds(88,113,160,16);
        hungerBar.setForeground(new Color(39,199,225));
        add(hungerBar);
        getContentPane().setComponentZOrder(hungerBar, 0);


        tiredBar.setValue(stats.getTiredness());
        tiredBar.setBounds(88,161,160,16);
        tiredBar.setForeground(new Color(39,199,225));
        add(tiredBar);
        getContentPane().setComponentZOrder(tiredBar, 0);


        JButton quitButton = new JButton();
        quitButton.setBounds(133, 20, 40, 40);
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

                    File save = new File("save.txt");
                    FileWriter myWriter = null;
                    try {
                        myWriter = new FileWriter("save.txt");
                        myWriter.write(stats.toString());
                        myWriter.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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
        add(quitButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitButton.doClick();
            }
        });


        JButton foodSpawner = new JButton();
        foodSpawner.setBounds(20, 209, 143, 35);
        foodSpawner.setContentAreaFilled(false);
        foodSpawner.setBorderPainted(false);
        foodSpawner.setFocusPainted(false);
        foodSpawner.setOpaque(false);
        foodSpawner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(stats.getCurrency()>2){
                    for(int i = 0; i < foodList.size(); i++){
                        if(foodList.get(i) == null){
                            Food food = new Food((int) (getLocation().x + burgerMap.get(i).getX()), (int) (getLocation().y + burgerMap.get(i).getY()));
                            foodList.set(i, food);
                            stats.setCurrency(stats.getCurrency()-2);
                            currency.setText(String.valueOf(stats.getCurrency()));
                            break;
                        }
                    }
                }
            }
        });
        add(foodSpawner);



        JButton openShop = new JButton();
        openShop.setBounds(20, 270, 143, 35);
        openShop.setContentAreaFilled(false);
        openShop.setBorderPainted(false);
        openShop.setFocusPainted(false);
        openShop.setOpaque(false);
        openShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shop.setLocation(getLocation().x-170,getLocation().y+250);
                shop.setVisible(true);
            }
        });
        add(openShop);

        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));
        currency.setText(String.valueOf(stats.getCurrency()));
        currency.setFont(font.deriveFont(35f));
        currency.setForeground(new Color(39,199,255,255));
        currency.setBounds(205, 270, 90, 35);
        add(currency);
        getContentPane().setComponentZOrder(currency, 0);




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
        setVisible(true);

        stats.setPositionX(getX()+325);
        stats.setDestinationX(getX()+325);
        stats.setPositionY(getY()+210);
        stats.setDestinationY(getY()+210);

    }
}