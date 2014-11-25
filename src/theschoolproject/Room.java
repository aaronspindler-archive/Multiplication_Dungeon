package theschoolproject; //THis will be the dungeon room, 16x11 = 176 tiles

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import resources.SettingsProperties;

public class Room {
    
    int width = 17;
    int height = 13;
    
    int[] tiles = new int[width * height];
    FloorTile[] tileArry = new FloorTile[width * height];
    GamePanel mainPanel;
    SettingsProperties props = new SettingsProperties();
    
    BufferedImage lvl;
    
    public Room(GamePanel gp, String LevelImage) {
        mainPanel = gp;
        for (int i = 0; i < tileArry.length; i++) {
            tileArry[i] = new FloorTile(1);
        }
        lvl = UsefulSnippets.loadImage(LevelImage);
        loadLevel();
    }
    
    public final void loadLevel() {
        lvl.getRGB(0, 0, width, height, tiles, 0, width);
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                if (tiles[i + j * width] == 0xFFFFFFFF) {
                    tileArry[i + j * width].setTile(1);
                }
                if (tiles[i + j * width] == 0xFF000000) {
                    tileArry[i + j * width].setTile(2);
                }
                if (tiles[i + j * width] == 0xFF532f00) {
                    tileArry[i + j * width].setTile(3);
                }
            }
        }
    }
    
    public void draw(Graphics g) {
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                g.setColor(tileArry[i + j * width].getColor());
                g.fill3DRect(i * 50, j * 50, 50, 50, true);
//                if (SettingsProperties.debugModeG == true) {
//                    g.setColor(Color.yellow);
//                    g.fill3DRect((mainPanel.pl.tileLocX) * 50, (mainPanel.pl.tileLocY) * 50, 50, 50, true);
//                    g.setColor(new Color(0, 255, 0, 7));
//                    g.fill3DRect((mainPanel.pl.tileLocX + 1) * 50, (mainPanel.pl.tileLocY) * 50, 50, 50, true);
//                    g.fill3DRect((mainPanel.pl.tileLocX) * 50, (mainPanel.pl.tileLocY + 1) * 50, 50, 50, true);
//                    g.fill3DRect((mainPanel.pl.tileLocX - 1) * 50, (mainPanel.pl.tileLocY) * 50, 50, 50, true);
//                    g.fill3DRect((mainPanel.pl.tileLocX) * 50, (mainPanel.pl.tileLocY - 1) * 50, 50, 50, true);
//                    
//                    g.setColor(new Color(255, 0, 0, 7));
//                    if (tileArry[(mainPanel.pl.tileLocX + 1) + (mainPanel.pl.tileLocY) * width].isSolid()) {
//                        g.fill3DRect((mainPanel.pl.tileLocX + 1) * 50, (mainPanel.pl.tileLocY) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(mainPanel.pl.tileLocX) + (mainPanel.pl.tileLocY + 1) * width].isSolid()) {
//                        g.fill3DRect((mainPanel.pl.tileLocX) * 50, (mainPanel.pl.tileLocY + 1) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(mainPanel.pl.tileLocX - 1) + (mainPanel.pl.tileLocY) * width].isSolid()) {
//                        g.fill3DRect((mainPanel.pl.tileLocX - 1) * 50, (mainPanel.pl.tileLocY) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(mainPanel.pl.tileLocX) + (mainPanel.pl.tileLocY - 1) * width].isSolid()) {
//                        g.fill3DRect((mainPanel.pl.tileLocX) * 50, (mainPanel.pl.tileLocY - 1) * 50, 50, 50, true);
//                    }
//                    g.setColor(Color.white);
//                }
            }
        }
    }
}