/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    int rows = 4;
    int columns = 3;
    BufferedImage spritesheet;
    BufferedImage[] sprites = new BufferedImage[rows * columns];
        
    public Player(){
        try {
            this.spritesheet = ImageIO.read(new File("playersprite.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void setLocation(){
        
    }
    
}
