package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import theschoolproject.Input.Keyboard;
import theschoolproject.Input.Mouse;
import theschoolproject.Objects.GuiButton;
import flexjson.JSONSerializer;
import flexjson.JSONDeserializer;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.Serializable;
import resources.SettingsProperties;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class GamePanel extends JPanel {

    public GameEngine ge = new GameEngine(this);

    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(ge.keys);
        this.addMouseListener(ge.mouse);
        this.addMouseMotionListener(ge.mouse);
        GamePanel.ListenerThread lt = new GamePanel.ListenerThread();
        Thread th = new Thread(lt);
        th.start();
    }

    @Override
    protected void paintComponent(Graphics g1) {
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

        ge.font = ge.font.deriveFont(26.0f);
        g.setFont(ge.font);

        if (ge.mainMenu) {
            g.drawImage(ge.menuScreen, 0 - ge.ImageScroll, 0, 850, 650, null);
            g.drawImage(ge.menuScreen, 850 - ge.ImageScroll, 0, 850, 650, null);
            g.drawImage(ge.menuTitle, 0, 0, null);
            for (int i = 0; i < ge.buttons.size(); i++) {
                ge.buttons.get(i).draw(g1);
            }
        }
        if (ge.gameScreen) {
            ge.rooms[ge.currentRoomX][ge.currentRoomY].draw(g);

//            for (int i = 0; i < mouse.Xcoords.size() - 1; i++) {
//                int x = (int) mouse.Xcoords.get(i);
//                int y = (int) mouse.Ycoords.get(i);
//                int x1 = (int) mouse.Xcoords.get(i + 1);
//                int y1 = (int) mouse.Ycoords.get(i + 1);
//                g.drawLine(x, y, x1, y1);
//            }
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
            if (SettingsProperties.debugModeG == true) {
//                g.drawString("pl_pos: " + pl.xLocFeet + ", " + pl.yLocFeet, 50, 60);
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

        if (ge.transitioning) {
            g.setColor(Color.BLACK);
            ge.transitionProg = ge.transitionProg + 10;
            ge.drawTransition(ge.transitionDir, g);
        }
    }

    public class ListenerThread implements Runnable {

        boolean listening = true;   //listener is always listening

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
