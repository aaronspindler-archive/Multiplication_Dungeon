package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import java.awt.RenderingHints;
import resources.SettingsProperties;

public class GamePanel extends JPanel {

    public GameEngine ge = new GameEngine();
    public GamePanel.ListenerThread lt;
    public MultiplicationDungeonForm parent;
    Thread th;
    int coolDown = 0;

    public GamePanel() {
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(ge.keys);
        this.addMouseListener(ge.mouse);
        this.addMouseMotionListener(ge.mouse);
        newThread();
    }

    public void newThread() {
        if (th != null) {
            lt.listening = false;
        }
        lt = new GamePanel.ListenerThread();
        th = new Thread(lt);
        th.start();
    }

    public void reloadEngine() {
        this.setFocusable(true);
        this.addKeyListener(ge.keys);
        this.addMouseListener(ge.mouse);
        this.addMouseMotionListener(ge.mouse);
        newThread();
    }

    @Override
    protected void paintComponent(Graphics g1) {

        if (coolDown > 0) {
            coolDown--;
        }

        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        if (SettingsProperties.antiAlisaingGraphics) {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (SettingsProperties.antiAlisaingText) {
            g.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        ge.font[0] = ge.font[0].deriveFont(26.0f);
        g.setFont(ge.font[0]);

        if (ge.intro) {
            g.drawImage(ge.menuScreen, 0 - ge.ImageScroll, 0, 850, 650, null);
            g.drawImage(ge.menuScreen, 850 - ge.ImageScroll, 0, 850, 650, null);
            g.setColor(new Color(0, 0, 0, 125));

            g.fill3DRect(ge.loadingBarProg, 630, 210, 12, true);
            ge.loadingBarProg++;
            if (ge.loadingBarProg > (2 * (Math.PI / .01))) {
                ge.loadingBarProg = 0;
                ge.intro = false;
                ge.mainMenu = true;
            }
            if (ge.loadTimer <= 10) {
                g.setColor(Color.BLACK);
                g.drawString("Loading.", 365, 325);
                g.setColor(Color.WHITE);
                g.drawString("Loading.", 364, 324);
            }
            if (ge.loadTimer > 10) {
                g.setColor(Color.BLACK);
                g.drawString("Loading..", 365, 325);
                g.setColor(Color.WHITE);
                g.drawString("Loading..", 364, 324);
            }
            if (ge.loadTimer > 20) {
                g.setColor(Color.BLACK);
                g.drawString("Loading...", 365, 325);
                g.setColor(Color.WHITE);
                g.drawString("Loading...", 364, 324);
            }
        }

        if (ge.mainMenu) {
            g.drawImage(ge.menuScreen, 0 - ge.ImageScroll, 0, 850, 650, null);
            g.drawImage(ge.menuScreen, 850 - ge.ImageScroll, 0, 850, 650, null);
            if (ge.mainSettings) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(new Color(180, 180, 180, 190));
                g.fill3DRect(200, 200, (this.getWidth() - 400), (this.getHeight() - 300), true);

                g.setColor(Color.DARK_GRAY);
                g.drawString("Player model - " + ge.playerSp, this.getWidth() / 3, this.getHeight() / 3 + 50);
                g.drawString("Difficulty", this.getWidth() / 3, this.getHeight() / 3 + 100);
                g.drawString("Room dimension - < " + ge.rooms.length + " > x < " + ge.rooms[0].length + " >", this.getWidth() / 3, this.getHeight() / 3 + 150);
                g.drawString("Help", this.getWidth() / 3, this.getHeight() / 3 + 200);
                g.drawString("Back", this.getWidth() / 3, this.getHeight() / 3 + 250);

                g.setColor(Color.black);
                g.drawString("Player model - " + ge.playerSp, this.getWidth() / 3 + 2, this.getHeight() / 3 + 52);
                g.drawString("Difficulty", this.getWidth() / 3 + 2, this.getHeight() / 3 + 102);
                g.drawString("Room dimension - < " + ge.rooms.length + " > x < " + ge.rooms[0].length + " >", this.getWidth() / 3 + 2, this.getHeight() / 3 + 152);
                g.drawString("Help", this.getWidth() / 3 + 2, this.getHeight() / 3 + 202);
                g.drawString("Back", this.getWidth() / 3 + 2, this.getHeight() / 3 + 252);

                if (ge.mouse.getX() > 280 && ge.mouse.getX() < 350 && ge.mouse.getY() > 245 && ge.mouse.getY() < 270) {
                    if (ge.mouse.isMousePressed() && coolDown <= 0) {
                        if (ge.playerSp > 0) {
                            ge.playerSp = 0;
                        } else {
                            ge.playerSp++;
                        }
                        ge.pl = new Player(ge, ge.playerSpritePaths[ge.playerSp], ge.keys);
                        coolDown = 200;
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Player model - " + ge.playerSp, this.getWidth() / 3, this.getHeight() / 3 + 52);
                }
                if (ge.mouse.getX() > 280 && ge.mouse.getX() < 350 && ge.mouse.getY() > 295 && ge.mouse.getY() < 320) {
                    if (ge.mouse.isMousePressed()) {
                        ge.mainSettings = false;
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Difficulty", this.getWidth() / 3, this.getHeight() / 3 + 102);
                }
                if (ge.mouse.getX() > 280 && ge.mouse.getX() < 350 && ge.mouse.getY() > 345 && ge.mouse.getY() < 370) {
                    if (ge.mouse.isMousePressed()) {
                        ge.mainSettings = false;
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Room dimension - " + ge.rooms.length + " x " + ge.rooms[0].length, this.getWidth() / 3, this.getHeight() / 3 + 152);
                }
                if (ge.mouse.getX() > 280 && ge.mouse.getX() < 350 && ge.mouse.getY() > 395 && ge.mouse.getY() < 420) {
                    if (ge.mouse.isMousePressed() && coolDown <= 0) {
                        UsefulSnippets.openWebpage("https://github.com/xNovax/Multiplication_Dungeon");
                        coolDown = 200;
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Help", this.getWidth() / 3, this.getHeight() / 3 + 202);
                }
                if (ge.mouse.getX() > 280 && ge.mouse.getX() < 350 && ge.mouse.getY() > 445 && ge.mouse.getY() < 470) {
                    if (ge.mouse.isMousePressed()) {
                        ge.mainSettings = false;
                    }
                    g.setColor(Color.WHITE);
                    g.drawString("Back", this.getWidth() / 3, this.getHeight() / 3 + 252);
                }

            } else {
                g.drawImage(ge.menuTitle, 0, 0, null);
                for (int i = 0; i < ge.buttons.size(); i++) {
                    ge.buttons.get(i).draw(g1);
                }
            }

        }
        if (ge.gameScreen) {
            ge.rooms[ge.currentRoomX][ge.currentRoomY].draw(g);

            g.setColor(Color.red);

            ge.mouse.x1 = (int) ge.pl.xLoc + 32;
            ge.mouse.y1 = (int) ge.pl.yLoc + 32;

            if (ge.mouse.isMousePressed() && !ge.transitioning) {
                g.setColor(Color.WHITE);

                int dx = ge.mouse.x2 - ge.mouse.x1;
                int dy = ge.mouse.y2 - ge.mouse.y1;
                g.drawLine(ge.mouse.x1, ge.mouse.y1, ge.mouse.x2, ge.mouse.y2);

                if (dx > 0) {    //R
                    if (dy > 0) {
                        if (abs(dx) < abs(dy)) {
                            if (SettingsProperties.debugModeG == true) {
//                                g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                                g.drawString("quad_3_D", 50, 50);
                            }
                            ge.pl.distToMove = abs(dy);
                            ge.pl.orientation = 2;

                        } else {
                            if (SettingsProperties.debugModeG == true) {
//                                g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                                g.drawString("quad_3_R", 50, 50);
                            }
                            ge.pl.distToMove = abs(dx);
                            ge.pl.orientation = 1;

                        }

                    } else {
                        if (abs(dx) < abs(dy)) {
                            if (SettingsProperties.debugModeG == true) {
//                                g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                                g.drawString("quad_0_U", 50, 50);
                            }
                            ge.pl.distToMove = abs(dy);
                            ge.pl.orientation = 0;

                        } else {
                            if (SettingsProperties.debugModeG == true) {
//                                g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                                g.drawString("quad_0_R", 50, 50);
                            }
                            ge.pl.distToMove = abs(dx);
                            ge.pl.orientation = 1;

                        }

                    }
                } else //L
                if (dy > 0) {
                    if (abs(dx) < abs(dy)) {
                        if (SettingsProperties.debugModeG == true) {
//                            g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                            g.drawString("quad_2_D", 50, 50);
                        }
                        ge.pl.distToMove = abs(dy);
                        ge.pl.orientation = 2;

                    } else {
                        if (SettingsProperties.debugModeG == true) {
//                            g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                            g.drawString("quad_2_L", 50, 50);
                        }
                        ge.pl.distToMove = abs(dx);
                        ge.pl.orientation = 3;

                    }

                } else {
                    if (abs(dx) < abs(dy)) {
                        if (SettingsProperties.debugModeG == true) {
//                            g.drawLine(mouse.x1, mouse.y1, mouse.x1, mouse.y1 + dy);
                            g.drawString("quad_1_U", 50, 50);
                        }
                        ge.pl.distToMove = abs(dy);
                        ge.pl.orientation = 0;

                    } else {
                        if (SettingsProperties.debugModeG == true) {
//                            g.drawLine(mouse.x1, mouse.y1, mouse.x1 + dx, mouse.y1);
                            g.drawString("quad_1_L", 50, 50);
                        }
                        ge.pl.distToMove = abs(dx);
                        ge.pl.orientation = 3;

                    }
                }
            } else {
                ge.pl.distToMove = 0;
            }

            for (int i = 0; i < ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.size(); i++) {
                ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.get(i).draw(g);
                g.setColor(Color.GREEN);
                if (SettingsProperties.debugModeG == true) {
                    g.drawLine((int) ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.get(i).xLoc + 32, (int) ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.get(i).yLoc + 32,
                            (int) ge.pl.xLoc + 32, (int) ge.pl.yLoc + 32);
                }
            }
            ge.pl.draw(g);
            for (int a = 0; a < ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.size(); a++) {
                if ((ge.pl.getBounds().intersects(ge.rooms[ge.currentRoomX][ge.currentRoomY].en_arry.get(a).getBounds())) && (ge.pl.graceTimer < 1)) {
                    ge.en_index = a;
                    ge.switchTo("battle");
                    ge.frozen = true;
                }
            }
            ge.hud.draw(g);
        }
        if (ge.battle) {
            ge.qt.draw(g);
            ge.pl.graceTimer = 1000;
        }

        if (ge.paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(new Color(180, 180, 180, 190));
            g.fill3DRect(200, 200, (this.getWidth() - 400), (this.getHeight() - 400), true);

            g.setColor(Color.DARK_GRAY);
            g.drawString("Save state", this.getWidth() / 3 + 2, this.getHeight() / 3 + 52);
            g.drawString("Load state", this.getWidth() / 3 + 2, this.getHeight() / 3 + 102);
            g.drawString("Exit", this.getWidth() / 3 + 2, this.getHeight() / 3 + 152);

            g.setColor(Color.black);
            g.drawString("Save state", this.getWidth() / 3, this.getHeight() / 3 + 50);
            g.drawString("Load state", this.getWidth() / 3, this.getHeight() / 3 + 100);
            g.drawString("Exit", this.getWidth() / 3, this.getHeight() / 3 + 150);

            if (ge.mouse.getX() > 280 && ge.mouse.getX() < 425 && ge.mouse.getY() > 240 && ge.mouse.getY() < 270) {
                if (ge.mouse.isMousePressed()) {
                    this.parent.saveState();
                    ge.paused = false;
                }
                g.setColor(Color.WHITE);
                g.drawString("Save state", this.getWidth() / 3, this.getHeight() / 3 + 50);
            }
            if (ge.mouse.getX() > 280 && ge.mouse.getX() < 425 && ge.mouse.getY() > 290 && ge.mouse.getY() < 320) {
                if (ge.mouse.isMousePressed()) {
                    this.parent.loadState();
                    ge.paused = false;
                }
                g.setColor(Color.WHITE);
                g.drawString("Load state", this.getWidth() / 3, this.getHeight() / 3 + 100);
            }
            if (ge.mouse.getX() > 280 && ge.mouse.getX() < 425 && ge.mouse.getY() > 340 && ge.mouse.getY() < 370) {
                if (ge.mouse.isMousePressed()) {
                    ge = new GameEngine();
                    this.reloadEngine();
                }
                g.setColor(Color.WHITE);
                g.drawString("Exit", this.getWidth() / 3, this.getHeight() / 3 + 150);
            }
        }

        if (ge.transitioning) {
            if (ge.transitionDir == 4) {
                g.setColor(Color.BLACK);
                ge.transitionProg = ge.transitionProg + 4;
                ge.drawTransition(ge.transitionDir, g);
            } else {
                g.setColor(Color.BLACK);
                ge.transitionProg = ge.transitionProg + 10;
                ge.drawTransition(ge.transitionDir, g);
            }

        }

        if (ge.gameOver) {
            g.setColor(Color.darkGray);
            g.drawString("Game Over", this.getWidth() / 3, this.getHeight() / 3);
            g.drawString("You got " + ge.pl.score + " points!", this.getWidth() / 3, this.getHeight() / 3 + 75);
            g.drawString("Completed in " + (long) ((ge.endMillis - ge.startMillis) / 1000) / 60 + " minutes and " + (int) ((ge.endMillis - ge.startMillis) / 1000) % 60 + " seconds", this.getWidth() / 3, this.getHeight() / 3 + 100);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Exit", this.getWidth() / 3 + 2, this.getHeight() / 3 + 152);
            g.setColor(Color.black);
            g.drawString("Exit", this.getWidth() / 3, this.getHeight() / 3 + 150);
            if (ge.mouse.getX() > 280 && ge.mouse.getX() < 425 && ge.mouse.getY() > 340 && ge.mouse.getY() < 370) {
                if (ge.mouse.isMousePressed()) {
                    ge = new GameEngine();
                    this.reloadEngine();
                }
                g.setColor(Color.WHITE);
                g.drawString("Exit", this.getWidth() / 3, this.getHeight() / 3 + 150);
            }

        }
        g.setColor(Color.red);

//        g.drawString(ge.pl.getX() + " - " + ge.pl.getY(), 100, 100);
    }

    public void setParent(MultiplicationDungeonForm p) {
        parent = p;
    }

    public class ListenerThread implements Runnable {

        public boolean listening = true;   //listener is always listening

        @Override
        public void run() {
            while (listening) {
                ge.tick();
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
