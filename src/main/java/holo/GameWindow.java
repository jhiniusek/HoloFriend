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
    public JLabel menuImage = new JLabel(new ImageIcon(getClass().getResource("/sprites/MenuENG.png")));
    public JProgressBar hungerBar = new JProgressBar(0,100);
    public JProgressBar tiredBar = new JProgressBar(0,100);
    public JLabel currency = new JLabel();
    public JLabel hint = new JLabel();
    private JFrame options = new JFrame("Options");
    private JFrame byeFriend = new JFrame("Bye Friend");
    private JFrame help = new JFrame("Help");
    private Point initialClick;
    public String[] tips;
    final String[] tipsENG = {"Hi Friends!",
            "Click on a cog(earring) to open options",
            "Fubuki gets hungry over time",
            "Fubuki dies and loses gold when starved",
            "Make burgers to feed Fubuki",
            "Each burger costs 2 gold",
            "Hold LMB on an object to grab it and move",
            "Fubuki regenerates stamina over time",
            "Earn gold by fishing",
            "Fishing uses stamina",
            "Sleeping regenerates stamina",
            "You can spend gold in the shop",
            "Click on wardrobe to change clothes",
            "If you pet Fubuki too much, she gets angry"};

    JList<String> listOfTips = new JList<String>(tipsENG);

    final String[] tipsJP = {"Hi Friends!",
            "オプションを開くために歯車（イヤリング）をクリックして",
            "フブキは時間が経つとお腹が空いてく",
            "フブキは飢えると死にコインを失う",
            "フブキに食べさせるためにバーガーを作ろう",
            "バーガーはコイン２枚かかる",
            "オブジェクトを掴んで動かすにはLMBを長押しする",
            "フブキは時間が経つとスタミナを回復していく",
            "釣りをしてコインを稼ごう",
            "釣りはスタミナを消費する",
            "寝るとスタミナが回復する",
            "ショップでコインを使えるよ",
            "ワードローブをクリックして服を着替える",
            "TOTRANSLATE"};

    Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));

    public GameWindow(FriendStats stats, ArrayList<Food> foodList, Shop shop) throws IOException, FontFormatException {
        Map<Integer, Point> burgerMap = new HashMap<Integer, Point>();
        burgerMap.put(0, new Point(178,230));
        burgerMap.put(1, new Point(219,230));
        burgerMap.put(2, new Point(260,230));
        burgerMap.put(3, new Point(178,207));
        burgerMap.put(4, new Point(219,207));
        burgerMap.put(5, new Point(260,207));


        setSize(306,346);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setTitle("HoloFriend");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        menuImage.setBounds(0, 0, menuImage.getIcon().getIconWidth(), menuImage.getIcon().getIconHeight());
        add(menuImage);


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


        byeFriend.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        byeFriend.setSize(240, 180);
        byeFriend.setLayout(null);
        byeFriend.setLocationRelativeTo(null);
        byeFriend.setResizable(false);

        JLabel quitMessage = new JLabel("<html>Are you sure you want to quit?<br/>Progress will be saved.", SwingConstants.CENTER);
        quitMessage.setBounds(20, 10, 190, 70);
        byeFriend.add(quitMessage);

        JButton closeButton = new JButton("YES");
        closeButton.setBounds(40, 90, 60, 30);
        byeFriend.add(closeButton);

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
        JButton stayButton = new JButton("NO");
        stayButton.setBounds(125, 90, 60, 30);
        byeFriend.add(stayButton);

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byeFriend.dispose();
            }
        });

        JButton quitButton = new JButton();
        quitButton.setBounds(133, 20, 40, 40);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setFocusPainted(false);
        quitButton.setOpaque(false);
        quitButton.addActionListener(e -> {
            byeFriend.setVisible(true);
        });
        add(quitButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitButton.doClick();
            }
        });




        options.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        options.setSize(240, 360);
        options.setLayout(null);
        options.setLocationRelativeTo(null);
        options.setResizable(false);

        JLabel optionsMessage = new JLabel("Object lost? Respawn it:", SwingConstants.CENTER);
        optionsMessage.setBounds(20, 10, 190, 40);
        options.add(optionsMessage);

        JButton respawnLake = new JButton("Lake");
        respawnLake.setBounds(40, 50, 144, 30);
        options.add(respawnLake);

        respawnLake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stats.getLake().setLocation(stats.getCursorX(), stats.getCursorY());
                stats.getLake().toFront();
            }
        });

        JButton respawnBed = new JButton("Bed");
        respawnBed.setBounds(40, 90, 144, 30);
        options.add(respawnBed);

        respawnBed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stats.getBedOwned() == 1){
                    stats.getBed().setLocation(stats.getCursorX(), stats.getCursorY());
                    stats.getBed().toFront();
                }
            }
        });

        JButton respawnRadio = new JButton("Radio");
        respawnRadio.setBounds(40, 130, 144, 30);
        options.add(respawnRadio);

        respawnRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stats.getRadioOwned() == 1){
                    stats.getRadio().setLocation(stats.getCursorX(), stats.getCursorY());
                    stats.getRadio().toFront();
                }
            }
        });

        JButton respawnWardrobe = new JButton("Wardrobe");
        respawnWardrobe.setBounds(40, 170, 144, 30);
        options.add(respawnWardrobe);

        respawnWardrobe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stats.getWardrobeOwned() == 1){
                    stats.getWardrobe().setLocation(stats.getCursorX(), stats.getCursorY());
                    stats.getWardrobe().toFront();
                }
            }
        });

        JButton helpButton = new JButton("Help");
        helpButton.setBounds(40, 230, 144, 30);
        options.add(helpButton);

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help.setVisible(true);
            }
        });

        JButton englishButton = new JButton("English");
        englishButton.setBounds(20, 270, 90, 30);
        options.add(englishButton);

        englishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tips = tipsENG;
                listOfTips.setListData(tipsENG);
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));
                } catch (Exception ex) {
                    System.out.println("ERROR FONT NOT FOUND");
                }
                hint.setFont(font.deriveFont(16f));
                UpdateHint();
                menuImage.setIcon(new ImageIcon(getClass().getResource("/sprites/MenuENG.png")));
            }
        });

        JButton japaneseButton = new JButton("Japanese");
        japaneseButton.setBounds(120, 270, 90, 30);
        options.add(japaneseButton);

        japaneseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tips = tipsJP;
                listOfTips.setListData(tipsJP);
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/DotGothic16-Regular.ttf"));
                } catch (Exception ex) {
                    System.out.println("ERROR FONT NOT FOUND");
                }
                hint.setFont(font.deriveFont(8f));
                UpdateHint();
                menuImage.setIcon(new ImageIcon(getClass().getResource("/sprites/MenuJP.png")));
            }
        });

        tips = tipsENG;

        help.setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        help.setSize(370, 310);
        help.setLayout(null);
        help.setLocationRelativeTo(null);
        help.setResizable(false);
        listOfTips.setBounds(10,10,335,251);
        help.add(listOfTips);

        JButton optionsButton = new JButton();
        optionsButton.setBounds(250, 45, 35, 35);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setBorderPainted(false);
        optionsButton.setFocusPainted(false);
        optionsButton.setOpaque(false);
        optionsButton.addActionListener(e -> {
            options.setVisible(true);
        });
        add(optionsButton);
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


        currency.setText(String.valueOf(stats.getCurrency()));
        currency.setFont(font.deriveFont(35f));
        currency.setForeground(new Color(39,199,255,255));
        currency.setBounds(205, 270, 90, 35);
        add(currency);
        getContentPane().setComponentZOrder(currency, 0);

        hint.setText(tips[0]);
        hint.setFont(font.deriveFont(16f));
        hint.setForeground(new Color(39,199,255,255));
        hint.setBounds(48, 295, 250, 50);
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        add(hint);
        getContentPane().setComponentZOrder(hint, 0);



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

    public void UpdateHint(){
        hint.setText(tips[(int)(Math.random() * (tips.length - 1) + 1)]);
    }
}