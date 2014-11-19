/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import theschoolproject.Input.Keyboard;
import theschoolproject.Input.Mouse;
import theschoolproject.Objects.GuiButton;
import flexjson.JSONSerializer;
import flexjson.JSONDeserializer;
import resources.SettingsProperties;

public class GamePanel extends JPanel {

    Random rand = new Random();
    ListenerThread lt = new ListenerThread();
    Thread th = new Thread(lt);
    FloorTile[][] ft = new FloorTile[17][16];
    JSONSerializer jsonSer = new JSONSerializer();
    JSONDeserializer jsonDes = new JSONDeserializer();
    SettingsProperties props = new SettingsProperties();

    //=========================
    //   Game State Variables
    //=========================
    public boolean mainMenu = true;
    public boolean gameScreen = false;
    public boolean battle = false;
    public boolean frozen = false;

    //=========================
    //      Input Variables
    //=========================
    Keyboard keys = new Keyboard();
    Mouse mouse = new Mouse(this);

    //=========================
    //    Player Variables
    //=========================
    Player pl;
    ArrayList<Entity> en_arry = new ArrayList();
    int numEnemies = 5;

    //=========================
    //    Question Variables
    //=========================
    QuestionTimer qt;

    //=========================
    //      Menu Variables
    //=========================
    BufferedImage menuScreen;
    BufferedImage menuTitle;
    BufferedImage play_NoGlow;
    BufferedImage play_Glow;
    int AnimationTimer = 0;
    int ImageScroll = 0;
    ArrayList<GuiButton> buttons = new ArrayList();

    //=========================
    //      Room Variables
    //=========================
    Room[] rooms = new Room[64];
    int CurrentRoom = 0;

    public GamePanel() {
        for (int l = 0; l < numEnemies; l++) {
            en_arry.add(new Enemy(this, "/resources/en1_sprite.png"));
        }
        this.addKeyListener(keys);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.qt = new QuestionTimer();
        pl = new Player(this, "/resources/pl_sprite.png", keys);
        th.start();
        for (int w = 0; w < 17; w++) {
            for (int h = 0; h < 15; h++) {
                if (w == 0 || w == 16 || h == 0 || h == 12) {
                    ft[w][h] = new FloorTile(1);
                } else {
                    ft[w][h] = new FloorTile(0);
                }
            }
        }
        this.setFocusable(true);
        menuScreen = UsefulSnippets.loadImage("/resources/JustBG.png");
        menuTitle = UsefulSnippets.loadImage("/resources/MenuTitle.png");
        play_NoGlow = UsefulSnippets.loadImage("/resources/Play_NoGlow.png");
        play_Glow = UsefulSnippets.loadImage("/resources/Play_WithGlow.png");
        rooms[0] = new Room(this, "/resources/Levels/Level_02.png");
        buttons.add(new GuiButton("/resources/Play_NoGlow.png", "/resources/Play_WithGlow.png", "game", 350, 335, 500, 390, this));
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        if (mainMenu) {
            g.drawImage(menuScreen, 0 - ImageScroll, 0, 850, 650, null);
            g.drawImage(menuScreen, 850 - ImageScroll, 0, 850, 650, null);
            g.drawImage(menuTitle, 0, 0, null);
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).draw(g1);
            }
        }
        if (gameScreen) {

            rooms[CurrentRoom].draw(g);

            for (int i = 0; i < mouse.Xcoords.size() - 1; i++) {
                int x = (int) mouse.Xcoords.get(i);
                int y = (int) mouse.Ycoords.get(i);
                int x1 = (int) mouse.Xcoords.get(i + 1);
                int y1 = (int) mouse.Ycoords.get(i + 1);
                g.drawLine(x, y, x1, y1);
            }

            g.setColor(Color.red);

            mouse.x1 = (int) pl.xLoc + 32;
            mouse.y1 = (int) pl.yLoc + 32;
            if (mouse.isMousePressed()) {
                g.setColor(Color.WHITE);

                int dx = mouse.x2 - mouse.x1;
                int dy = mouse.y2 - mouse.y1;
                g.drawLine(mouse.x1, mouse.y1, mouse.x2, mouse.y2);
                if (dx > 0) {    //R
                    if (dy > 0) {
                        if (abs(dx) < abs(dy)) {
                            if (props.getDebugMode() == true) {
                                g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                                g.drawString("quad_3_D", 50, 50);
                            }
                            pl.distToMove = abs(dy);
                            pl.orientation = 2;

                        } else {
                            if (props.getDebugMode() == true) {
                                g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                                g.drawString("quad_3_R", 50, 50);
                            }
                            pl.distToMove = abs(dx);
                            pl.orientation = 1;

                        }

                    } else {
                        if (abs(dx) < abs(dy)) {
                            if (props.getDebugMode() == true) {
                                g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                                g.drawString("quad_0_U", 50, 50);
                            }
                            pl.distToMove = abs(dy);
                            pl.orientation = 0;

                        } else {
                            if (props.getDebugMode() == true) {
                                g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                                g.drawString("quad_0_R", 50, 50);
                            }
                            pl.distToMove = abs(dx);
                            pl.orientation = 1;

                        }

                    }
                } else //L
                if (dy > 0) {
                    if (abs(dx) < abs(dy)) {
                        if (props.getDebugMode() == true) {
                            g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                            g.drawString("quad_2_D", 50, 50);
                        }
                        pl.distToMove = abs(dy);
                        pl.orientation = 2;

                    } else {
                        if (props.getDebugMode() == true) {
                            g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                            g.drawString("quad_2_L", 50, 50);
                        }
                        pl.distToMove = abs(dx);
                        pl.orientation = 3;

                    }

                } else {
                    if (abs(dx) < abs(dy)) {
                        if (props.getDebugMode() == true) {
                            g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                            g.drawString("quad_1_U", 50, 50);
                        }
                        pl.distToMove = abs(dy);
                        pl.orientation = 0;

                    } else {
                        if (props.getDebugMode() == true) {
                            g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                            g.drawString("quad_1_L", 50, 50);
                        }
                        pl.distToMove = abs(dx);
                        pl.orientation = 3;

                    }
                }
            }
            if (props.getDebugMode() == true) {
                g.drawString("pl_pos: " + pl.xLoc + ", " + pl.yLoc, 50, 60);
            }

            for (int i = 0; i < numEnemies; i++) {
                en_arry.get(i).draw(g);
                g.setColor(Color.GREEN);
                if (props.getDebugMode() == true) {
                    g.drawLine((int) en_arry.get(i).xLoc + 32, (int) en_arry.get(i).yLoc + 32,
                            (int) pl.xLoc + 32, (int) pl.yLoc + 32);
                }
            }
            pl.draw(g);
            for (int a = 0; a < en_arry.size(); a++) {
                if ((pl.getBounds().intersects(en_arry.get(a).getBounds())) && (pl.graceTimer < 1)) {
                    battle = true;
                    frozen = true;
                }
            }
        }
        if (battle) {
            qt.draw(g);
            pl.graceTimer = 1000;
        }
    }

    public void tick() {
        if (mainMenu) {
            if (AnimationTimer > 5) {
                ImageScroll++;
                if (ImageScroll >= 850) {
                    ImageScroll = 0;
                }
                AnimationTimer = 0;
            } else {
                AnimationTimer++;
            }

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).tick();
            }
        }
        if (gameScreen && !frozen) {
            pl.tick();
            for (int i = 0; i < en_arry.size(); i++) {
                en_arry.get(i).tick();
            }
        }

        if (battle) {
            qt.tick();
        }
    }

    /*
     Switchs the gamemode to the desired gamemode
     */
    public void switchTo(String mode) {
        if (mode.equals("menu")) {
            this.mainMenu = true;
            this.gameScreen = false;
            this.battle = false;
        }
        if (mode.equals("game")) {
            this.mainMenu = false;
            this.gameScreen = true;
            this.battle = false;
        }
        if (mode.equals("battle")) {
            this.mainMenu = false;
            this.gameScreen = false;
            this.battle = true;
        }
    }

    public void saveState() {
        jsonSer.serialize(this);
    }

    public void loadState() {

    }

    public Keyboard getKeyboard() {
        return keys;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }

    public class ListenerThread implements Runnable {

        boolean listening = true;   //listener is always listening

        @Override
        public void run() {
            while (listening) {
                tick();
                repaint();
                try {
                    sleep(5);   //This is to save resources on repaint
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
