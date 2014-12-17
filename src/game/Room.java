package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import resources.SettingsProperties;

public class Room implements Serializable {

    int width = 17;
    int height = 13;
    int xNum;
    int yNum;

    public int numEnemies = UsefulSnippets.generateRandomNumber(3) + 2;
    public ArrayList<Entity> en_arry = new ArrayList();

    int[] tiles = new int[width * height];
    int[] spawnTiles = new int[width * height];
    FloorTile[] tileArry = new FloorTile[width * height];
    GameEngine world;
    SettingsProperties props = new SettingsProperties();

    transient BufferedImage lvl;
    transient BufferedImage spawnMap;
    String lvlPath;
    String spawnMapPath;
    int drawCycle = 0;

    Random gen = new Random();

    public Room(GameEngine ge, String levelImage, String spawnImage, int x, int y) {
        world = ge;
        for (int i = 0; i < tileArry.length; i++) {
            tileArry[i] = new FloorTile(1);
        }
        for (int l = 0; l < numEnemies; l++) {
            int spr = UsefulSnippets.generateRandomNumber(3);
            en_arry.add(new Enemy(world, world.spritePaths[spr]));
        }
        lvl = UsefulSnippets.loadImage(levelImage);
        spawnMap = UsefulSnippets.loadImage(spawnImage);
        lvlPath = levelImage;
        spawnMapPath = spawnImage;
        this.xNum = x;
        this.yNum = y;
        this.numEnemies = gen.nextInt(3) + 2;
        loadLevel();
    }

    public final void loadLevel() {
        lvl.getRGB(0, 0, width, height, tiles, 0, width);
        spawnMap.getRGB(0, 0, width, height, spawnTiles, 0, width);
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {

                if (tiles[i + j * width] == 0xFFFFFFFF) {
                    tileArry[i + j * width].setTile(1); //Wall
                }
                if (tiles[i + j * width] == 0xFF000000) {
                    tileArry[i + j * width].setTile(2); //Floor
                    tileArry[i + j * width].metaElement = UsefulSnippets.generateRandomNumber(3);
                }
                if (tiles[i + j * width] == 0xFF532F00) {
                    tileArry[i + j * width].setTile(3); //Door
                }
                if (tiles[i + j * width] == 0xFF0000ff) {
                    tileArry[i + j * width].setTile(4); //Water
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
                if (spawnTiles[i + j * width] == 0xFFff0096) {
                    tileArry[i + j * width].isSpawn = true; //Spawn
                }
            }
        }
//
//        System.out.println(tileArry[5 + 5 * width].TILE_ID);
        for (int i = 1; i < lvl.getWidth() - 1; i++) {
            for (int j = 1; j < lvl.getHeight() - 1; j++) {
                if (tileArry[i + j * width].TILE_ID == 2) {
                    
                    //Water
                    //Sides
                    if (tileArry[i + (j - 1) * width].TILE_ID == 4) {  //up
                        tileArry[i + j * width].metaElement = 1;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 4) {  //right
                        tileArry[i + j * width].metaElement = 1;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[i + (j + 1) * width].TILE_ID == 4) {  //down
                        tileArry[i + j * width].metaElement = 1;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4) {  //left
                        tileArry[i + j * width].metaElement = 1;
                        tileArry[i + j * width].metaDir = 3;
                    }
                    //Corners
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4 && tileArry[i + (j - 1) * width].TILE_ID == 4) {  //up + left
                        tileArry[i + j * width].metaElement = 2;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 4 && tileArry[i + (j - 1) * width].TILE_ID == 4) {  //up + right
                        tileArry[i + j * width].metaElement = 2;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 4 && tileArry[i + (j + 1) * width].TILE_ID == 4) {  //down + right
                        tileArry[i + j * width].metaElement = 2;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4 && tileArry[i + (j + 1) * width].TILE_ID == 4) {  //down + left
                        tileArry[i + j * width].metaElement = 2;
                        tileArry[i + j * width].metaDir = 3;
                    }

                    //Opposites
                    if (tileArry[i + (j - 1) * width].TILE_ID == 4 && tileArry[i + (j + 1) * width].TILE_ID == 4) {  //up/down
                        tileArry[i + j * width].metaElement = 3;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4 && tileArry[(i + 1) + j * width].TILE_ID == 4) {  //left/right
                        tileArry[i + j * width].metaElement = 3;
                        tileArry[i + j * width].metaDir = 1;
                    }

                    //3 Sides (dead end)
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4 && tileArry[i + (j - 1) * width].TILE_ID == 4 && tileArry[(i + 1) + j * width].TILE_ID == 4) {  //n
                        tileArry[i + j * width].metaElement = 4;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(j - 1) + j * width].TILE_ID == 4 && tileArry[i + (j + 1) * width].TILE_ID == 4 && tileArry[(i + 1) + j * width].TILE_ID == 4) {  //backwards "C"
                        tileArry[i + j * width].metaElement = 4;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 4 && tileArry[i + (j - 1) * width].TILE_ID == 4 && tileArry[(i + 1) + j * width].TILE_ID == 4) {  //U
                        tileArry[i + j * width].metaElement = 4;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[i + (j - 1) * width].TILE_ID == 4 && tileArry[i + (j + 1) * width].TILE_ID == 4 && tileArry[(i - 1) + j * width].TILE_ID == 4) {  //C
                        tileArry[i + j * width].metaElement = 4;
                        tileArry[i + j * width].metaDir = 3;
                    }

                    //Lava 
                    //Sides
                    if (tileArry[i + (j - 1) * width].TILE_ID == 6) {  //up
                        tileArry[i + j * width].metaElement = 5;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 6) {  //right
                        tileArry[i + j * width].metaElement = 5;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[i + (j + 1) * width].TILE_ID == 6) {  //down
                        tileArry[i + j * width].metaElement = 5;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6) {  //left
                        tileArry[i + j * width].metaElement = 5;
                        tileArry[i + j * width].metaDir = 3;
                    }
                    //Corners
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6 && tileArry[i + (j - 1) * width].TILE_ID == 6) {  //up + left
                        tileArry[i + j * width].metaElement = 6;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 6 && tileArry[i + (j - 1) * width].TILE_ID == 6) {  //up + right
                        tileArry[i + j * width].metaElement = 6;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i + 1) + j * width].TILE_ID == 6 && tileArry[i + (j + 1) * width].TILE_ID == 6) {  //down + right
                        tileArry[i + j * width].metaElement = 6;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6 && tileArry[i + (j + 1) * width].TILE_ID == 6) {  //down + left
                        tileArry[i + j * width].metaElement = 6;
                        tileArry[i + j * width].metaDir = 3;
                    }

                    //Opposites
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6 && tileArry[(i + 1) + j * width].TILE_ID == 6) {  //left/right
                        tileArry[i + j * width].metaElement = 7;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[i + (j - 1) * width].TILE_ID == 6 && tileArry[i + (j + 1) * width].TILE_ID == 6) {  //up/down
                        tileArry[i + j * width].metaElement = 7;
                        tileArry[i + j * width].metaDir = 1;
                    }

                    //3 Sides (dead end)
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6 && tileArry[i + (j - 1) * width].TILE_ID == 6 && tileArry[(i + 1) + j * width].TILE_ID == 6) {  //n
                        tileArry[i + j * width].metaElement = 8;
                        tileArry[i + j * width].metaDir = 0;
                    }
                    if (tileArry[(j - 1) + j * width].TILE_ID == 6 && tileArry[i + (j + 1) * width].TILE_ID == 6 && tileArry[(i + 1) + j * width].TILE_ID == 6) {  //backwards "C"
                        tileArry[i + j * width].metaElement = 8;
                        tileArry[i + j * width].metaDir = 1;
                    }
                    if (tileArry[(i - 1) + j * width].TILE_ID == 6 && tileArry[i + (j - 1) * width].TILE_ID == 6 && tileArry[(i + 1) + j * width].TILE_ID == 6) {  //U
                        tileArry[i + j * width].metaElement = 8;
                        tileArry[i + j * width].metaDir = 2;
                    }
                    if (tileArry[i + (j - 1) * width].TILE_ID == 6 && tileArry[i + (j + 1) * width].TILE_ID == 6 && tileArry[(i - 1) + j * width].TILE_ID == 6) {  //C
                        tileArry[i + j * width].metaElement = 8;
                        tileArry[i + j * width].metaDir = 3;
                    }
                }

            }
        }

        if (this.yNum == 0) {
            tileArry[7].TILE_ID = 1;
            tileArry[8].TILE_ID = 1;
            tileArry[9].TILE_ID = 1;
        }
        if (this.xNum == 0) {
            tileArry[85].TILE_ID = 1;
            tileArry[102].TILE_ID = 1;
            tileArry[119].TILE_ID = 1;
        }
        if (this.xNum == world.rooms[0].length - 1) {
            tileArry[101].TILE_ID = 1;
            tileArry[118].TILE_ID = 1;
            tileArry[135].TILE_ID = 1;
        }
        if (this.yNum == world.rooms.length - 1) {
            tileArry[211].TILE_ID = 1;
            tileArry[212].TILE_ID = 1;
            tileArry[213].TILE_ID = 1;
        }

    }

    public void draw(Graphics g) {
        //You are entering switch hell
        for (int i = 0; i < lvl.getWidth(); i++) {
            for (int j = 0; j < lvl.getHeight(); j++) {
                g.setColor(tileArry[i + j * width].getColor());
                g.fill3DRect(i * 50, j * 50, 50, 50, true);
                switch (tileArry[i + j * width].TILE_ID) {
                    case 0:
                        g.drawImage(world.spritesTex[0][0], i * 50, j * 50, null); //Test tile
                        break;
                    case 1:
                        g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][1], i * 50, j * 50, null);  //Wall tile
                        break;
                    case 2:
                        if (tileArry[i + j * width].metaDir == -1) {
                            g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][2], i * 50, j * 50, null);
                        } else {
                            switch (tileArry[i + j * width].metaElement) {
                                case 1:
                                    switch (tileArry[i + j * width].metaDir) {
                                        case 0:
                                            g.drawImage(world.spritesTex[6][4], i * 50, j * 50, null);
                                            break;
                                        case 1:
                                            g.drawImage(world.spritesTex[8][4], i * 50, j * 50, null);
                                            break;
                                        case 2:
                                            g.drawImage(world.spritesTex[9][4], i * 50, j * 50, null);
                                            break;
                                        case 3:
                                            g.drawImage(world.spritesTex[7][4], i * 50, j * 50, null);
                                            break;
                                    }
                                    break;
                                case 2:
                                    switch (tileArry[i + j * width].metaDir) {
                                        case 0:
                                            g.drawImage(world.spritesTex[10][4], i * 50, j * 50, null);
                                            break;
                                        case 1:
                                            g.drawImage(world.spritesTex[11][4], i * 50, j * 50, null);
                                            break;
                                        case 2:
                                            g.drawImage(world.spritesTex[12][4], i * 50, j * 50, null);
                                            break;
                                        case 3:
                                            g.drawImage(world.spritesTex[3][4], i * 50, j * 50, null);
                                            break;
                                    }
                                    break;
                                case 3:
                                    g.drawImage(world.spritesTex[4 + tileArry[i + j * width].metaDir][4], i * 50, j * 50, null);
                                    break;
                                case 4:
                                    g.drawImage(world.spritesTex[13 + tileArry[i + j * width].metaDir][4], i * 50, j * 50, null);
                                    break;
                            }
                            break;
                        }
                        break;
                    case 3:
                        g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][3], i * 50, j * 50, null);  //Door tile
                        break;
                    case 4:
                        drawCycle++;
                        if (drawCycle > 10) {
                            tileArry[i + j * width].metaElement = UsefulSnippets.generateRandomNumber(3);
                            drawCycle = 0;
                            g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][4], i * 50, j * 50, null);
                        } else {
                            g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][4], i * 50, j * 50, null);
                        }
                        break;
                    case 5:
                        g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][5], i * 50, j * 50, null);
                        break;
                    case 6:
                        drawCycle++;
                        if (drawCycle > 10) {
                            tileArry[i + j * width].metaElement = UsefulSnippets.generateRandomNumber(3);
                            drawCycle = 0;
                            g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][6], i * 50, j * 50, null);
                        } else {
                            switch (tileArry[i + j * width].metaElement) {
                                case 5:
                                    switch (tileArry[i + j * width].metaDir) {
                                        case 0:
                                            g.drawImage(world.spritesTex[6][6], i * 50, j * 50, null);
                                            break;
                                        case 1:
                                            g.drawImage(world.spritesTex[8][6], i * 50, j * 50, null);
                                            break;
                                        case 2:
                                            g.drawImage(world.spritesTex[9][6], i * 50, j * 50, null);
                                            break;
                                        case 3:
                                            g.drawImage(world.spritesTex[7][6], i * 50, j * 50, null);
                                            break;
                                    }
                                    break;
                                case 6:
                                    switch (tileArry[i + j * width].metaDir) {
                                        case 0:
                                            g.drawImage(world.spritesTex[10][6], i * 50, j * 50, null);
                                            break;
                                        case 1:
                                            g.drawImage(world.spritesTex[11][6], i * 50, j * 50, null);
                                            break;
                                        case 2:
                                            g.drawImage(world.spritesTex[12][6], i * 50, j * 50, null);
                                            break;
                                        case 3:
                                            g.drawImage(world.spritesTex[3][6], i * 50, j * 50, null);
                                            break;
                                    }
                                case 7:
                                    g.drawImage(world.spritesTex[4 + tileArry[i + j * width].metaDir][6], i * 50, j * 50, null);
                                    break;
                                case 8:
                                    g.drawImage(world.spritesTex[13 + tileArry[i + j * width].metaDir][6], i * 50, j * 50, null);
                            }
                        }
                        break;
                    case 7:
                        g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][7], i * 50, j * 50, null);
                        break;
                    case 8:
                        g.drawImage(world.spritesTex[tileArry[i + j * width].metaElement][7], i * 50, j * 50, null);
                        break;
                    case 9:
                        g.drawImage(world.spritesTex[tileArry[(i + j * width) + 1].metaElement][tileArry[(i + j * width) + 1].TILE_ID], i * 50, j * 50, null);
                        g.drawImage(world.spritesTex[0][9], i * 50, j * 50, null);
                        break;
                    case 10:
                        g.drawImage(world.spritesTex[tileArry[(i + j * width) + 1].metaElement][tileArry[(i + j * width) + 1].TILE_ID], i * 50, j * 50, null);
                        g.drawImage(world.spritesTex[0][10], i * 50, j * 50, null);
                        break;

                }
                if (tileArry[i + j * width].isSpawn) {
                    g.drawImage(world.spritesTex[0][10], i * 50, j * 50, null);
                }
//                if (SettingsProperties.debugModeG == true) {
//                    g.setColor(Color.yellow);
//                    g.fill3DRect((world.pl.tileLocX) * 50, (world.pl.tileLocY) * 50, 50, 50, true);
//                    g.setColor(new Color(0, 255, 0, 7));
//                    g.fill3DRect((world.pl.tileLocX + 1) * 50, (world.pl.tileLocY) * 50, 50, 50, true);
//                    g.fill3DRect((world.pl.tileLocX) * 50, (world.pl.tileLocY + 1) * 50, 50, 50, true);
//                    g.fill3DRect((world.pl.tileLocX - 1) * 50, (world.pl.tileLocY) * 50, 50, 50, true);
//                    g.fill3DRect((world.pl.tileLocX) * 50, (world.pl.tileLocY - 1) * 50, 50, 50, true);
//                    
//                    g.setColor(new Color(255, 0, 0, 7));
//                    if (tileArry[(world.pl.tileLocX + 1) + (world.pl.tileLocY) * width].isSolid()) {
//                        g.fill3DRect((world.pl.tileLocX + 1) * 50, (world.pl.tileLocY) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(world.pl.tileLocX) + (world.pl.tileLocY + 1) * width].isSolid()) {
//                        g.fill3DRect((world.pl.tileLocX) * 50, (world.pl.tileLocY + 1) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(world.pl.tileLocX - 1) + (world.pl.tileLocY) * width].isSolid()) {
//                        g.fill3DRect((world.pl.tileLocX - 1) * 50, (world.pl.tileLocY) * 50, 50, 50, true);
//                    }
//                    if (tileArry[(world.pl.tileLocX) + (world.pl.tileLocY - 1) * width].isSolid()) {
//                        g.fill3DRect((world.pl.tileLocX) * 50, (world.pl.tileLocY - 1) * 50, 50, 50, true);
//                    }
//                    g.setColor(Color.white);
//                }
            }
        }
    }

    public void loadResources() {
        lvl = UsefulSnippets.loadImage(lvlPath);
        spawnMap = UsefulSnippets.loadImage(spawnMapPath);
    }
}
