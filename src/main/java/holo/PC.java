package holo;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PC extends JFrame {
    private FriendStats stats;
    private GameWindow gameWindow;
    private Point initialClickPC;
    private Point initialClickDesktop;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC.png")));
    private JFrame frame = new JFrame();
    JPanel youtubePanel;
    JPanel discordPanel;
    JPanel amazonPanel;
    ImageIcon desktopSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/PC UI.png"));
    ImageIcon youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOffline.png"));
    ImageIcon discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1.png"));
    public ImageIcon amazonSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/Amazon.png"));
    Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));
    JLabel viewerCount = new JLabel();
    JLabel subscriberCount = new JLabel();
    public boolean stream = false;
    public int subscribers = 0;
    public int viewers = 0;
    private ArrayList<Collab> listOfCollabs;
    private String activeName = "mio";
    private JLabel chatterIcon = new JLabel();
    private ArrayList<JButton> amazonTiles = new ArrayList<>();
    private JLabel amazonTimer = new JLabel();
    public ArrayList<Gift> listOfGifts;
    private boolean shopRefreshed = false;

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public PC(FriendStats stats, ArrayList<Collab> listOfCollabs, ArrayList<Gift> listOfGifts) throws IOException, FontFormatException {
        this.stats = stats;
        subscribers = stats.getSubscribers();
        this.listOfCollabs = listOfCollabs;
        this.listOfGifts = listOfGifts;
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Type.UTILITY);
        setSize(85,79);
        setLayout(null);
        setIconImage(new ImageIcon(getClass().getResource("/sprites/Icon.png")).getImage());
        setBackground(new Color(1.0f,1.0f,1.0f,0f));
        sprite.setBounds(0, 0, 85, 79);
        add(sprite);

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClickPC = e.getPoint();
            }
        });

        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClickPC.x;
                int yMoved = e.getY() - initialClickPC.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
                stats.pcPositionX = X;
                stats.pcPositionY = Y;
            }
        });

        getContentPane().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(true);
                frame.setLocation(PC.super.getX()+50, PC.super.getY()-80);
            }
        });

        setVisible(stats.isPcOwned());
        setLocation(stats.pcPositionX, stats.pcPositionY);

        // =========================
        // DESKTOP
        // =========================

        frame.setUndecorated(true);
        frame.setSize(320, 180);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new CardLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setType(Window.Type.UTILITY);

        frame.getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClickDesktop = e.getPoint();
            }
        });

        frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved = e.getX() - initialClickDesktop.x;
                int yMoved = e.getY() - initialClickDesktop.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });

        JPanel desktopPanel = new JPanel() {
            private final Image background = desktopSprite.getImage();

            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };
        desktopPanel.setLayout(null);
        desktopPanel.setDoubleBuffered(true);

        JButton openYoutube = new JButton();
        openYoutube.setContentAreaFilled(false);
        openYoutube.setBorderPainted(false);
        openYoutube.setFocusPainted(false);
        openYoutube.setOpaque(false);
        openYoutube.setBounds(10, 12, 41, 31);
        desktopPanel.add(openYoutube);

        JButton openDiscord = new JButton();
        openDiscord.setContentAreaFilled(false);
        openDiscord.setBorderPainted(false);
        openDiscord.setFocusPainted(false);
        openDiscord.setOpaque(false);
        openDiscord.setBounds(10, 51, 41, 41);
        desktopPanel.add(openDiscord);

        JButton openAmazon = new JButton();
        openAmazon.setContentAreaFilled(false);
        openAmazon.setBorderPainted(false);
        openAmazon.setFocusPainted(false);
        openAmazon.setOpaque(false);
        openAmazon.setBounds(10, 100, 41, 35);
        desktopPanel.add(openAmazon);

        JButton closeBtn1 = new JButton();
        closeBtn1.setContentAreaFilled(false);
        closeBtn1.setBorderPainted(false);
        closeBtn1.setFocusPainted(false);
        closeBtn1.setOpaque(false);
        closeBtn1.setBounds(308, 5, 7, 7);
        desktopPanel.add(closeBtn1);

        // ========================
        // YOUTUBE
        // ========================
        youtubePanel = new JPanel(null) {

            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2.drawImage(youtubeSprite.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };

        JButton backBtn1 = new JButton();
        backBtn1.setContentAreaFilled(false);
        backBtn1.setBorderPainted(false);
        backBtn1.setFocusPainted(false);
        backBtn1.setOpaque(false);
        backBtn1.setBounds(277, 24, 7, 7);
        youtubePanel.add(backBtn1);

        subscriberCount.setText(String.valueOf(stats.getSubscribers()));
        subscriberCount.setFont(font.deriveFont(20f));
        subscriberCount.setHorizontalAlignment(SwingConstants.RIGHT);
        subscriberCount.setBounds(200,34,58,11);
        youtubePanel.add(subscriberCount);

        viewerCount.setText(String.valueOf(viewers));
        viewerCount.setFont(font.deriveFont(20f));
        viewerCount.setHorizontalAlignment(SwingConstants.LEFT);
        viewerCount.setBounds(62,138,80,11);

        JButton streamButton = new JButton();
        streamButton.setContentAreaFilled(false);
        streamButton.setBorderPainted(false);
        streamButton.setFocusPainted(false);
        streamButton.setOpaque(false);
        streamButton.setBounds(205, 136, 73, 14);
        youtubePanel.add(streamButton);

        JButton closeBtn2 = new JButton();
        closeBtn2.setContentAreaFilled(false);
        closeBtn2.setBorderPainted(false);
        closeBtn2.setFocusPainted(false);
        closeBtn2.setOpaque(false);
        closeBtn2.setBounds(308, 5, 7, 7);
        youtubePanel.add(closeBtn2);

        // =========================
        // DISCORD
        // =========================
        if(stats.getLocale() == MyLocale.JAPANESE){
            discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1JP.png"));
        }

        discordPanel = new JPanel(null) {

            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2.drawImage(discordSprite.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };

        JPanel iconsPanel = new JPanel();
        iconsPanel.setLayout(new BoxLayout(iconsPanel, BoxLayout.Y_AXIS));
        iconsPanel.setOpaque(false);
        iconsPanel.setDoubleBuffered(true);

        Dimension slotSize = new Dimension(40, 52);

        for (int i = 0; i < listOfCollabs.size(); i++) {

            JLayeredPane slot = new JLayeredPane();
            slot.setPreferredSize(slotSize);
            slot.setMinimumSize(slotSize);
            slot.setMaximumSize(slotSize);
            slot.setOpaque(false);


            JButton btn = new JButton();
            btn.setBounds(4, 0, 32, 32);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);

            int finalI = i;
            String path = listOfCollabs.get(finalI).getName() + "Icon";
            btn.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + path + ".png")));

            btn.addActionListener(e -> {
                activeName = listOfCollabs.get(finalI).getName();
                updateDiscord(listOfCollabs.get(finalI));
            });


            JProgressBar expBar = new JProgressBar(0, 100);
            listOfCollabs.get(i).setExpBar(expBar);
            expBar.setValue(listOfCollabs.get(i).getExperience());
            expBar.setBounds(7, 33, 27, 4);
            expBar.setBorderPainted(false);
            expBar.setOpaque(false);
            expBar.setForeground(new Color(80, 200, 255));


            JLabel levelIcon = new JLabel();
            listOfCollabs.get(i).setLevelIcon(levelIcon);
            levelIcon.setBounds(1, 27, 32, 10);
            levelIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/Level" + listOfCollabs.get(i).getLevel() + ".png")));


            slot.add(btn, Integer.valueOf(100));
            slot.add(expBar, Integer.valueOf(50));
            slot.add(levelIcon, Integer.valueOf(200));

            iconsPanel.add(slot);
            iconsPanel.add(Box.createVerticalStrut(-10));
        }


        JScrollPane scrollPane = new JScrollPane(iconsPanel);
        scrollPane.setBounds(38, 30, 42, 114);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scrollPane.setDoubleBuffered(true);


        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {}
            @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
        });
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
        chatterIcon.setBounds(89,58,22,22);
        chatterIcon.setVisible(false);
        discordPanel.add(chatterIcon);

        JButton backBtn2 = new JButton();
        backBtn2.setContentAreaFilled(false);
        backBtn2.setBorderPainted(false);
        backBtn2.setFocusPainted(false);
        backBtn2.setOpaque(false);
        backBtn2.setBounds(277, 24, 7, 7);
        discordPanel.add(backBtn2);

        JButton closeBtn3 = new JButton();
        closeBtn3.setContentAreaFilled(false);
        closeBtn3.setBorderPainted(false);
        closeBtn3.setFocusPainted(false);
        closeBtn3.setOpaque(false);
        closeBtn3.setBounds(308, 5, 7, 7);
        discordPanel.add(closeBtn3);

        JButton chatBtn = new JButton();
        chatBtn.setContentAreaFilled(false);
        chatBtn.setBorderPainted(false);
        chatBtn.setFocusPainted(false);
        chatBtn.setOpaque(false);
        chatBtn.setBounds(103, 128, 124, 12);
        discordPanel.add(chatBtn);

        discordPanel.add(scrollPane);


        // ========================
        // AMAZON
        // ========================
        if(stats.getLocale() == MyLocale.JAPANESE){
            amazonSprite= new ImageIcon(getClass().getResource("/sprites/Desktop/AmazonJP.png"));
        }

        amazonPanel = new JPanel(null) {

            {
                setDoubleBuffered(true);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2.drawImage(amazonSprite.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        };


        for (int i = 0; i < 8; i++) {
            JButton amazonTile = new JButton();
            amazonTile.setContentAreaFilled(false);
            amazonTile.setBorderPainted(false);
            amazonTile.setFocusPainted(false);
            amazonTile.setOpaque(false);
            amazonTile.setBounds(77 + (44 * (i % 4)), 68 + (43 * ((i % 8) / 4)), 34, 40);
            amazonPanel.add(amazonTile);
            amazonTiles.add(amazonTile);
        }

        amazonTimer.setText("09:59");
        amazonTimer.setFont(font.deriveFont(16f));
        amazonTimer.setForeground(Color.WHITE);
        amazonTimer.setBounds(184, 52, 30, 8);
        amazonPanel.add(amazonTimer);

        JButton backBtn3 = new JButton();
        backBtn3.setContentAreaFilled(false);
        backBtn3.setBorderPainted(false);
        backBtn3.setFocusPainted(false);
        backBtn3.setOpaque(false);
        backBtn3.setBounds(277, 24, 7, 7);
        amazonPanel.add(backBtn3);

        JButton closeBtn4 = new JButton();
        closeBtn4.setContentAreaFilled(false);
        closeBtn4.setBorderPainted(false);
        closeBtn4.setFocusPainted(false);
        closeBtn4.setOpaque(false);
        closeBtn4.setBounds(308, 5, 7, 7);
        amazonPanel.add(closeBtn4);

        // =========================
        // SWITCHING SCREENS
        // =========================
        frame.add(desktopPanel, "desktop");
        frame.add(youtubePanel, "youtube");
        frame.add(discordPanel, "discord");
        frame.add(amazonPanel, "amazon");

        Container content = frame.getContentPane();
        content.setLayout(new CardLayout());
        content.add(desktopPanel, "desktop");
        content.add(youtubePanel, "youtube");
        content.add(discordPanel, "discord");
        content.add(amazonPanel, "amazon");

        CardLayout cl = (CardLayout) content.getLayout();

        openYoutube.addActionListener(e -> cl.show(frame.getContentPane(), "youtube"));
        streamButton.addActionListener(e -> {
            stream();
            updateStreamStats(stats);
        });
        openDiscord.addActionListener(e -> cl.show(frame.getContentPane(), "discord"));
        chatBtn.addActionListener(e -> {
            Collab collab = null;
            for (int i = 0; i < listOfCollabs.size(); i++) {
                if(activeName == listOfCollabs.get(i).getName()){
                    collab = listOfCollabs.get(i);
                }
            }
            switch (collab.getChatOption()){
                case 1:
                    if(collab.checkCooldown()){
                        collab.setChatOption(2);
                        collab.setVisible(true);
                        collab.start();
                        collab.setTime(System.currentTimeMillis());
                        updateDiscord(collab);
                    } else {
                        collab.setChatOption(3);
                        updateDiscord(collab);
                    }
                    break;
                case 2:
                    collab.setChatOption(4);
                    collab.end();
                    updateDiscord(collab);
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        });
        openAmazon.addActionListener(e -> cl.show(frame.getContentPane(), "amazon"));

        backBtn1.addActionListener(e -> cl.show(frame.getContentPane(), "desktop"));
        backBtn2.addActionListener(e -> cl.show(frame.getContentPane(), "desktop"));
        backBtn3.addActionListener(e -> cl.show(frame.getContentPane(), "desktop"));
        closeBtn1.addActionListener(e -> frame.setVisible(false));
        closeBtn2.addActionListener(e -> frame.setVisible(false));
        closeBtn3.addActionListener(e -> frame.setVisible(false));
        closeBtn4.addActionListener(e -> frame.setVisible(false));



        System.setProperty("sun.java2d.opengl", "true");



        setLocation(stats.pcPositionX, stats.pcPositionY);

        loadAmazonShop();
    }

    public void stream(){
        if(!stream){
            youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOnline.png"));
            int randomNum = ThreadLocalRandom.current().nextInt(60, 70 + 1);
            viewers = (subscribers * randomNum) / 100 + 1;
            stream = true;
            youtubePanel.add(viewerCount);
            youtubePanel.repaint();
        } else {
            youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOffline.png"));
            stream = false;
            youtubePanel.remove(viewerCount);
            youtubePanel.repaint();
        }
    }

    public void updateStreamStats(FriendStats stats){
        subscribers = stats.getSubscribers();
        subscriberCount.setText(String.valueOf(subscribers));
        viewerCount.setText(String.valueOf(viewers));
    }

    public void updateDiscord(Collab collab){
        switch (collab.getChatOption()){
            case 1:
                chatterIcon.setVisible(false);
                chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                if(stats.getLocale() == MyLocale.JAPANESE){
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1JP.png"));
                } else if (stats.getLocale() == MyLocale.ENGLISH) {
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1.png"));
                }
                break;
            case 2:
                chatterIcon.setVisible(true);
                chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                if(stats.getLocale() == MyLocale.JAPANESE){
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat2JP.png"));
                } else if (stats.getLocale() == MyLocale.ENGLISH) {
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat2.png"));
                }
                break;
            case 3:
                chatterIcon.setVisible(true);
                chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                if(stats.getLocale() == MyLocale.JAPANESE){
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat3JP.png"));
                } else if (stats.getLocale() == MyLocale.ENGLISH) {
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat3.png"));
                }
                break;
            case 4:
                chatterIcon.setVisible(true);
                chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                if(stats.getLocale() == MyLocale.JAPANESE){
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat4JP.png"));
                } else if (stats.getLocale() == MyLocale.ENGLISH) {
                    discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat4.png"));
                }
                break;
        }
        discordPanel.repaint();
    }

    public void updateDiscord(){
        Collab collab = null;
        for (int i = 0; i < listOfCollabs.size(); i++) {
            Collab checkingCollab = listOfCollabs.get(i);
            if (checkingCollab.getName() == activeName) {
                collab = checkingCollab;
            }
        }
        if (collab != null) {
            switch (collab.getChatOption()){
                case 1:
                    chatterIcon.setVisible(false);
                    chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                    if(stats.getLocale() == MyLocale.JAPANESE){
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1JP.png"));
                    } else if (stats.getLocale() == MyLocale.ENGLISH) {
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat1.png"));
                    }
                    break;
                case 2:
                    chatterIcon.setVisible(true);
                    chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                    if(stats.getLocale() == MyLocale.JAPANESE){
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat2JP.png"));
                    } else if (stats.getLocale() == MyLocale.ENGLISH) {
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat2.png"));
                    }
                    break;
                case 3:
                    chatterIcon.setVisible(true);
                    chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                    if(stats.getLocale() == MyLocale.JAPANESE){
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat3JP.png"));
                    } else if (stats.getLocale() == MyLocale.ENGLISH) {
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat3.png"));
                    }
                    break;
                case 4:
                    chatterIcon.setVisible(true);
                    chatterIcon.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + activeName + "ChatIcon.png")));
                    if(stats.getLocale() == MyLocale.JAPANESE){
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat4JP.png"));
                    } else if (stats.getLocale() == MyLocale.ENGLISH) {
                        discordSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/DiscordChat4.png"));
                    }
                    break;
            }
        }
        discordPanel.repaint();
    }

    public void loadAmazonShop(){
        if(stats.getAmazonGiftNames().size() == 8 && stats.getAmazonGiftPrices().size() == 8 && stats.getAmazonGiftEnabled().size() == 8){
            for (int i = 0; i < 8; i++) {
                amazonTiles.get(i).setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
                amazonTiles.get(i).setIcon(new ImageIcon(getClass().getResource("/sprites/gifts/"+ stats.amazonGiftNames.get(i) +".png")));
                amazonTiles.get(i).setText("  " + stats.amazonGiftPrices.get(i));
                amazonTiles.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
                amazonTiles.get(i).setVerticalTextPosition(SwingConstants.BOTTOM);
                amazonTiles.get(i).setIconTextGap(1);
                amazonTiles.get(i).setFont(font.deriveFont(17f));
                int finalI = i;
                amazonTiles.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(stats.getCurrency() > Integer.parseInt(stats.amazonGiftPrices.get(finalI))) {
                            Gift gift = new Gift(stats.amazonGiftNames.get(finalI), stats.amazonGiftPrices.get(finalI));
                            gift.setLocation(stats.getCursorX(), stats.getCursorY());
                            stats.setCurrency(stats.getCurrency() - Integer.parseInt(stats.amazonGiftPrices.get(finalI)));
                            gameWindow.currency.setText(String.valueOf(stats.getCurrency()));
                            listOfGifts.add(gift);
                            amazonTiles.get(finalI).setEnabled(false);
                            stats.amazonGiftEnabled.set(finalI, false);
                        }
                    }
                });
                amazonTiles.get(i).setEnabled(stats.getAmazonGiftEnabled().get(i));
            }
        } else {
            generateAmazonShop();
        }
    }

    public void generateAmazonShop(){
        Random rand = new Random();
        GiftNames[] allnames = GiftNames.values();
        stats.getAmazonGiftNames().clear();
        stats.getAmazonGiftPrices().clear();
        stats.getAmazonGiftEnabled().clear();

        for (int i = 0; i < 8; i++) {
            int gift = rand.nextInt(allnames.length);
            String giftName = String.valueOf(allnames[gift]);
            amazonTiles.get(i).setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
            amazonTiles.get(i).setIcon(new ImageIcon(getClass().getResource("/sprites/gifts/"+ giftName +".png")));
            String price = String.valueOf(rand.nextInt(100,201));
            amazonTiles.get(i).setText("  " + price);
            amazonTiles.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
            amazonTiles.get(i).setVerticalTextPosition(SwingConstants.BOTTOM);
            amazonTiles.get(i).setIconTextGap(1);
            amazonTiles.get(i).setFont(font.deriveFont(17f));
            for (ActionListener al : amazonTiles.get(i).getActionListeners()) {
                amazonTiles.get(i).removeActionListener(al);
            }
            int finalI = i;
            amazonTiles.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(stats.getCurrency() > Integer.parseInt(price)){
                        Gift gift = new Gift(giftName, price);
                        gift.setLocation(stats.getCursorX(), stats.getCursorY());
                        stats.setCurrency(stats.getCurrency() - Integer.parseInt(price));
                        gameWindow.currency.setText(String.valueOf(stats.getCurrency()));
                        listOfGifts.add(gift);
                        amazonTiles.get(finalI).setEnabled(false);
                        stats.amazonGiftEnabled.set(finalI,false);
                    }
                }
            });
            amazonTiles.get(i).setEnabled(true);
            stats.getAmazonGiftNames().add(i, giftName);
            stats.getAmazonGiftPrices().add(i, price);
            stats.getAmazonGiftEnabled().add(i, true);
        }
    }

    public void updateAmazonTimer(){
        long currentTime = System.currentTimeMillis();
        long timeleft = 600 - (currentTime/1000) % 600;
        int minutes = (int) (timeleft / 60);
        int seconds = (int) (timeleft % 60);
        String text = String.format("%02d:%02d", minutes, seconds);
        amazonTimer.setText(text);

        if(timeleft == 600 && !shopRefreshed) {
            generateAmazonShop();
            shopRefreshed = true;
        }

        if(timeleft < 600) {
            shopRefreshed = false;
        }
    }
}