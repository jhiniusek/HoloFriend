package holo;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PC extends JFrame {
    private Point initialClickPC;
    private Point initialClickDesktop;
    JLabel sprite = new JLabel(new ImageIcon(getClass().getResource("/sprites/PC.png")));
    private JFrame frame = new JFrame();
    JPanel youtubePanel;
    JPanel discordPanel;
    ImageIcon desktopSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/PC UI.png"));
    ImageIcon youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOffline.png"));
    Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/sprites/Micro5-Regular.ttf"));
    JLabel viewerCount = new JLabel();
    JLabel subscriberCount = new JLabel();
    public boolean stream = false;
    public int subscribers = 0;
    public int viewers = 0;
    private ArrayList<Collab> listOfCollabs;
    private String activeName;

    public PC(FriendStats stats, ArrayList<Collab> listOfCollabs) throws IOException, FontFormatException {
        subscribers = stats.getSubscribers();
        this.listOfCollabs = listOfCollabs;
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setType(Window.Type.UTILITY);
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

        setVisible(true);
        setLocation(100,100);




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(320, 180);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new CardLayout());

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

        // =========================
        // EKRAN PULPITU
        // =========================
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

        JButton closeBtn1 = new JButton();
        closeBtn1.setContentAreaFilled(false);
        closeBtn1.setBorderPainted(false);
        closeBtn1.setFocusPainted(false);
        closeBtn1.setOpaque(false);
        closeBtn1.setBounds(308, 5, 7, 7);
        desktopPanel.add(closeBtn1);

        // ========================
        // EKRAN YOUTUBE
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
        youtubePanel.add(viewerCount);

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
        // EKRAN DISCORD
        // =========================
        discordPanel = new JPanel(null) {
            private final Image background =
                    new ImageIcon(getClass().getResource("/sprites/Desktop/Discord.png")).getImage();

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

        // 🔹 Panel z ikonami
        JPanel iconsPanel = new JPanel();
        iconsPanel.setLayout(new BoxLayout(iconsPanel, BoxLayout.Y_AXIS));
        iconsPanel.setOpaque(false);
        iconsPanel.setDoubleBuffered(true);

        Dimension fixedSize = new Dimension(32, 32);
        for (int i = 0; i < listOfCollabs.size(); i++) {
            JButton btn = new JButton();
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(fixedSize);
            btn.setMinimumSize(fixedSize);
            btn.setMaximumSize(fixedSize);
            btn.setBorderPainted(false);
            int finalI = i;
            String path = listOfCollabs.get(finalI).getName() + "Icon";
            btn.setIcon(new ImageIcon(getClass().getResource("/sprites/Desktop/" + path + ".png")));

            btn.addActionListener(e -> {
                activeName = listOfCollabs.get(finalI).getName();
                updateDiscord();
            });
            iconsPanel.add(btn);
            if (i < 12) iconsPanel.add(Box.createVerticalStrut(10));
        }

        // 🔹 ScrollPane z przezroczystością
        JScrollPane scrollPane = new JScrollPane(iconsPanel);
        scrollPane.setBounds(38, 30, 42, 114);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scrollPane.setDoubleBuffered(true);

        // 🔹 Usunięcie widocznych pasków przewijania, ale zachowanie scrolla
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {}
            @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
        });
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // 🔹 Przycisk powrotu do pulpitu
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

        discordPanel.add(scrollPane);

        // =========================
        // PRZEŁĄCZANIE EKRANÓW
        // =========================
        frame.add(desktopPanel, "desktop");
        frame.add(youtubePanel, "youtube");
        frame.add(discordPanel, "discord");

        Container content = frame.getContentPane();
        content.setLayout(new CardLayout());
        content.add(desktopPanel, "desktop");
        content.add(youtubePanel, "youtube");
        content.add(discordPanel, "discord");

        CardLayout cl = (CardLayout) content.getLayout();

        openYoutube.addActionListener(e -> cl.show(frame.getContentPane(), "youtube"));
        streamButton.addActionListener(e -> {
            stream();
            updateStreamStats(stats);
        });
        openDiscord.addActionListener(e -> cl.show(frame.getContentPane(), "discord"));
        backBtn1.addActionListener(e -> cl.show(frame.getContentPane(), "desktop"));
        backBtn2.addActionListener(e -> cl.show(frame.getContentPane(), "desktop"));
        closeBtn1.addActionListener(e -> frame.setVisible(false));
        closeBtn2.addActionListener(e -> frame.setVisible(false));
        closeBtn3.addActionListener(e -> frame.setVisible(false));


        // 🔹 Zmniejsza liczbę niepotrzebnych repaintów (optymalizacja Swinga)
        System.setProperty("sun.java2d.opengl", "true");



        setLocation(stats.pcPositionX, stats.pcPositionY);
    }

    public void stream(){
        if(!stream){
            youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOnline.png"));
            int randomNum = ThreadLocalRandom.current().nextInt(60, 70 + 1);
            viewers = (subscribers * randomNum) / 100 + 1;
            stream = true;
            youtubePanel.repaint();
        } else {
            youtubeSprite = new ImageIcon(getClass().getResource("/sprites/Desktop/StreamOffline.png"));
            stream = false;
            youtubePanel.repaint();
        }
    }

    public void updateStreamStats(FriendStats stats){
        subscribers = stats.getSubscribers();
        subscriberCount.setText(String.valueOf(subscribers));
        viewerCount.setText(String.valueOf(viewers));
    }

    public void updateDiscord(){

        discordPanel.repaint();
    }
}