package theschoolproject; //THis will be the dungeon room, 16x11 = 176 tiles

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Room {

    int width = 17;
    int height = 13;

    FloorTile[] tileArry = new FloorTile[17 * 13];

    BufferedImage lvl;

    public Room(String LevelImage) {
        for (int i = 0; i < tileArry.length; i++) {
            tileArry[i] = new FloorTile(1);
        }
        lvl = UsefulSnippets.loadImage(LevelImage);
        loadLevel();
    }

    public final void loadLevel() {
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                int color = lvl.getRGB(i, j);

                if (color == -16777216) {
                    tileArry[i + j * width].setTile(2);
                } 
                else if(color == -11325696){
                    tileArry[i + j * width].setTile(3);
                    
                }else if(color == -1){
                    tileArry[i + j * width].setTile(1);
                }else{
                    System.out.println(color);
                    tileArry[i + j * width].setTile(0);
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                g.setColor(tileArry[i + j * width].getColor());
                g.fill3DRect(i * 50, j * 50, 50, 50, true);
            }
        }
    }
}
