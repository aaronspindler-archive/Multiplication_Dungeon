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

/**
 *
 * @author root
 */
public class Player {

    int xLoc = 0;
    int yLoc = 0;
    int orientation; //0 - North, 1 - East, 2 - South, 3 - West
    int score = 0;
    int lives = 3;
    int spd = 0;
    boolean isMoving = false;

    int rows = 3;
    int columns = 2;
    int height = 64;
    int width = 64;
    BufferedImage spriteSheetB;
    BufferedImage[] sprites;
    ImageIcon spriteSheet;

    public Player() {
        try {
            spriteSheet = new ImageIcon(this.getClass().getResource("/resources/playersprite.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sprites = new BufferedImage[rows * columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                spriteSheetB = new BufferedImage(spriteSheet.getIconWidth(), spriteSheet.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics sG = spriteSheetB.getGraphics();
                sG.drawImage(spriteSheet.getImage(), 0, 0, null);
                sprites[(i * columns) + j] = spriteSheetB.getSubimage(i * width, j * height, width, height);
            }
        }
        this.xLoc = 50;
        this.yLoc = 50;
    }

    public void draw(Graphics g) {
        g.drawImage(sprites[orientation], xLoc, yLoc, null);
    }

    public void tick() {
        if (isMoving && spd < 3) {
            spd++;
        }

        if (!isMoving && spd > 0) {
            spd--;
        }
        
        switch (orientation){
            case 0: yLoc = yLoc - spd;
                break;
            case 1: xLoc = xLoc + spd;
                break;
            case 2: yLoc = yLoc + spd;
                break;
            case 3: xLoc = xLoc - spd;
                break;
        }
    }

    public void setLocation(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }
}
