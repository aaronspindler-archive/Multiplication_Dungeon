/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
//import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import theschoolproject.Input.Keyboard;

/**
 *
 * @author root
 */
public class Player {

    double xLoc = 0;
    double yLoc = 0;
    int orientation; //0 - North, 1 - East, 2 - South, 3 - West
    int[] animSeq = {0, 1, 2, 1};
    int score = 0;
    int lives = 3;
    double spd = 0;
    boolean isMoving = false;

    int rows = 4;
    int columns = 3;
    int height = 64;
    int width = 64;
    int animCycle = 0;
    BufferedImage spriteSheetB;
    BufferedImage[][] sprites;
    ImageIcon spriteSheet;

    Keyboard keys;

    public Player(Keyboard k) {
        keys = k;
        try {
            spriteSheet = new ImageIcon(this.getClass().getResource("/resources/pl_sprite.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sprites = new BufferedImage[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                spriteSheetB = new BufferedImage(spriteSheet.getIconWidth(), spriteSheet.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics sG = spriteSheetB.getGraphics();
                sG.drawImage(spriteSheet.getImage(), 0, 0, null);
                sprites[i][j] = spriteSheetB.getSubimage(j * width, i * height, width, height);
            }
        }
        this.xLoc = 50;
        this.yLoc = 50;
    }

    public void draw(Graphics g) {
        g.drawImage(sprites[orientation][animSeq[animCycle]], (int) xLoc, (int) yLoc, null);
    }

    public void tick() {
        isMoving = false;
        if (keys.isKeyDown("up") || keys.isKeyDown("w")) {
            orientation = 0;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("left") || keys.isKeyDown("a")) {
            orientation = 3;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("down") || keys.isKeyDown("s")) {
            orientation = 2;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("right") || keys.isKeyDown("d")) {
            orientation = 1;
            isMoving = true;
            animCycle++;
        }

        if (isMoving && spd < 3) {
            spd = spd + 0.5;
        }

        if (!isMoving && spd > 0) {
            spd = spd - 0.5;
        }
        
        if (animCycle > 2){
            animCycle = 0;
        }
        
        switch (orientation) {
            case 0:
                if (this.yLoc > 50) {
                    setLocation(this.getX(), this.getY() - spd);
                }
                break;
            case 1:
                if (this.xLoc < 750) {
                    setLocation(this.getX() + spd, this.getY());
                }
                break;
            case 2:
                if (this.yLoc < 550) {
                    setLocation(this.getX(), this.getY() + spd);
                }
                break;
            case 3:
                if (this.xLoc > 50) {
                    setLocation(this.getX() - spd, this.getY());
                }
                break;
        }
    }

    public void setLocation(double x, double y) {
        this.xLoc = x;
        this.yLoc = y;
    }

    public double getX() {
        return xLoc;
    }

    public double getY() {
        return yLoc;
    }
}
