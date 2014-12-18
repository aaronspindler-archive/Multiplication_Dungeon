/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import game.Input.Keyboard;
import game.Input.Mouse;
import game.Objects.GuiButton;

public class GameEngine implements Serializable {

    GamePanel gp;

    Random rand = new Random();
    FloorTile[][] ft = new FloorTile[17][16];
    public Font font;

    //=========================
    //   Game State Variables
    //=========================
    public boolean mainMenu = true;
    public boolean gameScreen = false;
    public boolean battle = false;
    public boolean frozen = false;
    public int stratum = 1; //"depth" of rooms: 1 - normal, 2 - ice, 3 - lava, 4 - ???

    //=========================
    //      Input Variables
    //=========================
    public Keyboard keys = new Keyboard(this);
    public Mouse mouse = new Mouse(this);

    //=========================
    //    Player Variables
    //=========================
    String[] spritePaths = {"/resources/en1_sprite.png", "/resources/en2_sprite.png", "/resources/en3_sprite.png", "/resources/textures.png"};
    Player pl;
    HUD hud = new HUD(this); //heads up display
    int numEnemies = 5;
    int en_index; //the enemy that the player has collided with

    //=========================
    //    Question Variables
    //=========================
    public QuestionPanel qt;

    //=========================
    //      Menu Variables
    //=========================
    transient BufferedImage menuScreen;
    transient BufferedImage menuTitle;
    transient BufferedImage play_NoGlow;
    transient BufferedImage play_Glow;
    int AnimationTimer = 0;
    int ImageScroll = 0;
    ArrayList<GuiButton> buttons = new ArrayList();

    //=========================
    //      Room Variables
    //=========================
    public Room[][] rooms = new Room[5][5];
    int currentRoomX = 0;
    int currentRoomY = 0;
    int transitionProg = -1000;
    int transitionDir = -1;
    public boolean transitioning = false;

    transient BufferedImage spriteSheetTex;
    transient BufferedImage[][] spritesTex;
    int texRows = 20;
    int texCols = 20;
    int texD = 50;

    public GameEngine(GamePanel gp) {

        this.qt = new QuestionPanel(this);
        pl = new Player(this, "/resources/pl_sprite.png", keys);
        for (int w = 0; w < 17; w++) {
            for (int h = 0; h < 15; h++) {
                if (w == 0 || w == 16 || h == 0 || h == 12) {
                    ft[w][h] = new FloorTile(1);
                } else {
                    ft[w][h] = new FloorTile(0);
                }
            }
        }
        spritesTex = new BufferedImage[texCols][texRows];
        spriteSheetTex = UsefulSnippets.loadImage(spritePaths[spritePaths.length - 1]);
        for (int i = 0; i < texCols; i++) {
            for (int j = 0; j < texRows; j++) {
                spritesTex[i][j] = spriteSheetTex.getSubimage(i * texD, j * texD, texD, texD);
            }
        }
        menuScreen = UsefulSnippets.loadImage("/resources/JustBG.png");
        menuTitle = UsefulSnippets.loadImage("/resources/MenuTitle.png");
        play_NoGlow = UsefulSnippets.loadImage("/resources/Play_NoGlow.png");
        play_Glow = UsefulSnippets.loadImage("/resources/Play_WithGlow.png");
        buttons.add(new GuiButton("/resources/Play_NoGlow.png", "/resources/Play_WithGlow.png", "game", 350, 335, 500, 390, this));
        font = UsefulSnippets.loadFont("/resources/Deadhead Rough.ttf");
        loadRooms();
        UsefulSnippets.playMusic("/resources/game.mp3");
    }

    public void loadRooms() {
        DecimalFormat form = new DecimalFormat("00");
        for (int x = 0; x < rooms.length; x++) {
            for (int y = 0; y < rooms[0].length; y++) {
                String mapNum = form.format(rand.nextInt(11) + 1);
                rooms[x][y] = new Room(this,
                        "/resources/Levels/Level_" + mapNum + "_" + stratum + ".png",
                        "/resources/Levels/Spawn_Map_" + mapNum + ".png",
                        x,
                        y);
            }
        }
    }

    public void tick() {
        if (mainMenu) {
            if (AnimationTimer > 5) {
                ImageScroll++;
                if (ImageScroll >= 850) {
                    ImageScroll = 0;
                }
                AnimationTimer = 0;
            } else {
                AnimationTimer++;
            }

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).tick();
            }
        }
        if (gameScreen && !frozen) {
            pl.tick();
            for (int i = 0; i < rooms[currentRoomX][currentRoomY].en_arry.size(); i++) {
                rooms[currentRoomX][currentRoomY].en_arry.get(i).tick();
            }
        }

        if (battle) {
            qt.tick();
        }

        if (transitionProg > 800) {
            transitioning = false;
            transitionProg = -1000;
            pl.graceTimer = 100;
        }
    }

    public void switchTo(String mode) {
        if (mode.equals("menu")) {
            this.mainMenu = true;
            this.gameScreen = false;
            this.battle = false;
        }
        if (mode.equals("game")) {
            this.mainMenu = false;
            this.gameScreen = true;
            this.battle = false;
            this.frozen = false;
        }
        if (mode.equals("battle")) {
            this.mainMenu = false;
            this.gameScreen = true;
            this.battle = true;
            this.qt.startNewEquation();
        }
    }

    public Keyboard getKeyboard() {
        return keys;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void drawTransition(int i, Graphics g) {
        switch (i) {
            case 0:
                g.fillRect(0, -transitionProg, 1000, 800);
                break;
            case 1:
                g.fillRect(transitionProg, 0, 1000, 800);
                break;
            case 2:
                g.fillRect(0, transitionProg, 1000, 800);
                break;
            case 3:
                g.fillRect(-transitionProg, 0, 1000, 800);
                break;

        }
    }

    public void loadResources() {  //Called on loadstate, reloads all transient resources into gameEngine
        pl.loadResources("/resources/pl_sprite.png");
        font = UsefulSnippets.loadFont("/resources/Deadhead Rough.ttf");
        spritesTex = new BufferedImage[texCols][texRows];
        spriteSheetTex = UsefulSnippets.loadImage(spritePaths[spritePaths.length-1]);
        for (int i = 0; i < texCols; i++) {
            for (int j = 0; j < texRows; j++) {
                spritesTex[i][j] = spriteSheetTex.getSubimage(i * texD, j * texD, texD, texD);
            }
        }
        menuScreen = UsefulSnippets.loadImage("/resources/JustBG.png");
        menuTitle = UsefulSnippets.loadImage("/resources/MenuTitle.png");
        play_NoGlow = UsefulSnippets.loadImage("/resources/Play_NoGlow.png");
        play_Glow = UsefulSnippets.loadImage("/resources/Play_WithGlow.png");
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                rooms[i][j].loadResources();
                for (int k = 0; k < rooms[i][j].en_arry.size(); k++) {
                    rooms[i][j].en_arry.get(k).loadResources(rooms[i][j].en_arry.get(k).spritePath);
                }
            }
        }
        qt.loadResources();
        for (int l = 0; l < 0; l++) {
            buttons.get(l).loadResources();
        }
    }
}
