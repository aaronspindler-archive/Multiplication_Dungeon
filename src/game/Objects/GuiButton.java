package game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import resources.SettingsProperties;
import game.GameEngine;
import game.Input.Mouse;
import game.UsefulSnippets;

public class GuiButton implements Serializable {

    transient BufferedImage unPressed;
    transient BufferedImage Pressed;
    int minX, maxX, minY, maxY;
    String mode;
    boolean hover = false;
    Mouse m;
    GameEngine g;
    boolean bounds;
    String btn_unp;
    String btn_pr;

    public GuiButton(String UP, String P, String modeSwitched, int x, int y, GameEngine ge) {
        bounds = false;
        unPressed = UsefulSnippets.loadImage(UP);
        Pressed = UsefulSnippets.loadImage(P);
        btn_unp = UP;
        btn_pr = P;
        minX = x;
        maxX = x + unPressed.getWidth();
        minY = y;
        maxY = y + unPressed.getHeight();
        mode = modeSwitched;
        g = ge;
        m = ge.getMouse();
    }

    public GuiButton(String UP, String P, String modeSwitched, int x, int y, int x1, int y1, GameEngine ge) {
        bounds = true;
        unPressed = UsefulSnippets.loadImage(UP);
        Pressed = UsefulSnippets.loadImage(P);
        minX = x;
        maxX = x1;
        minY = y;
        maxY = y1;
        mode = modeSwitched;
        g = ge;
        m = ge.getMouse();
    }

    public void tick() {
        if (SettingsProperties.debugModeG == true) {
            System.out.println(m.getX() + " || " + m.getY());
        }

        if ((m.getX() > minX && m.getX() < maxX) && (m.getY() > minY && m.getY() < maxY)) {
            hover = true;
            if (m.isMousePressed()) {
                g.switchTo(mode);
                g.startMillis = System.currentTimeMillis();
                m.unPress();
            }
        } else {
            hover = false;
        }
    }

    public void draw(Graphics g) {
        if (!bounds) {
            if (hover) {
                g.drawImage(Pressed, minX, minY, null);
            } else {
                g.drawImage(unPressed, minX, minY, null);
            }
        } else {
            if (hover) {
                g.drawImage(Pressed, 0, 0, null);
            } else {
                g.drawImage(unPressed, 0, 0, null);
            }
        }
    }

    public void loadResources() {
        unPressed = UsefulSnippets.loadImage(btn_unp);
        Pressed = UsefulSnippets.loadImage(btn_pr);
    }
}
