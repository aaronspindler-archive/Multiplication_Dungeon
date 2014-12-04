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
    int drawCycle = 0;

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
                    tileArry[i + j * width].setTile(1); //Wall
                }
                if (tiles[i + j * width] == 0xFF000000) {
                    tileArry[i + j * width].setTile(2); //Floor
                }
                if (tiles[i + j * width] == 0xFF532F00) {
                    tileArry[i + j * width].setTile(3); //Door
                }
                if (tiles[i + j * width] == 0xFF0000ff) {
                    tileArry[i + j * width].setTile(9); //Water
                }
                if (tiles[i + j * width] == 0xFF03a5ff) {
                    tileArry[i + j * width].setTile(5); //Ice
                }
                if (tiles[i + j * width] == 0xFFfc1604) {
                    tileArry[i + j * width].setTile(6); //Lava
                }
                if (tiles[i + j * width] == 0xFF073a08) {
                    tileArry[i + j * width].setTile(7); //Moss
                }
                if (tiles[i + j * width] == 0xFF007f00) {
                    tileArry[i + j * width].setTile(8); //Grass
                }
                if (tiles[i + j * width] == 0xFF6b3d00) {
                    tileArry[i + j * width].setTile(9); //Rock
                }
            }
        }

        System.out.println(tileArry[5 + 5 * width].TILE_ID);
        for (int i = 1; i < lvl.getWidth() - 1; i++) {
            for (int j = 1; j < lvl.getHeight() - 1; j++) {
                if (tileArry[i + j * width].TILE_ID == 1) {
                    if (tileArry[i + (j + 1) * width].TILE_ID == 4) {
                        tileArry[i + j * width].setMetadata(1);
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[i + (j - 1) * width].TILE_ID == 4) {
                        tileArry[i + j * width].setMetadata(1);
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 4) {
                        tileArry[i + j * width].setMetadata(1);
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4) {
                        tileArry[i + j * width].setMetadata(1);
                        tileArry[i + j * width].metaDir = 3;
                    }
                }
            }
        }
    }

    public void draw(Graphics g) {

        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                g.setColor(tileArry[i + j * width].getColor());
                g.fill3DRect(i * 50, j * 50, 50, 50, true);
                switch (tileArry[i + j * width].TILE_ID) {
                    case 0:
                        break;
                    case 1:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][1], i * 50, j * 50, null);
                        break;
                    case 2:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][0], i * 50, j * 50, null);
                        break;
                    case 3:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][5], i * 50, j * 50, null);
                        break;
                    case 4:
                        drawCycle++;
                        if (drawCycle > 10) {
                            tileArry[i + j * width].metaData = UsefulSnippets.generateRandomNumber(3);
                            drawCycle = 0;
                            g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][2], i * 50, j * 50, null);
                        } else {
                            g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][2], i * 50, j * 50, null);
                        }
                        break;
                    case 5:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][3], i * 50, j * 50, null);
                        break;
                    case 6:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][6], i * 50, j * 50, null);
                        break;
                    case 7:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][6], i * 50, j * 50, null);
                        break;
                    case 8:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + j * width].metaData][6], i * 50, j * 50, null);
                        break;
                    case 9:
                        g.drawImage(mainPanel.spritesTex[tileArry[i + (j-1) * width].metaData][2], i * 50, j * 50, null);
                        g.drawImage(mainPanel.spritesTex[1][5], i * 50, j * 50, null);
                        break;

                }
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
