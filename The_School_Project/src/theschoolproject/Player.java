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

    int rows = 3;
    int columns = 2;
    int height = 32;
    int width = 32;
    BufferedImage spriteSheet;
    BufferedImage[] sprites;

    public Player() {
        try {
            //ImageIcon img = new ImageIcon(this.getClass().getResource("/src/resources/playersprite.png"));
            this.spriteSheet = ImageIO.read(new File("src/resources/playersprite.png"));
            //this.spriteSheet = (BufferedImage)img.getImage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sprites = new BufferedImage[rows * columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sprites[(i * columns) + j] = spriteSheet.getSubimage(i * width, j * height, width, height);
            }
        }
        this.xLoc = 50;
        this.yLoc = 50;
    }
    
    public void draw(Graphics g){
          g.drawImage(sprites[orientation], xLoc, yLoc, null);
    }

    public void tick(){
    
    }
    
    public void setLocation(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
    }

    public int getX(){
        return xLoc;
    }
    
    public int getY(){
        return yLoc;
    }
}
