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
import java.awt.Color;
import resources.SettingsProperties;

public class GameEngine implements Serializable {

    GamePanel gp;

    Random rand = new Random();
    FloorTile[][] ft = new FloorTile[17][16];
    public Font[] font = new Font[2];
    public Mp3Player mu;
    public MusicThread mt;
    Thread td;

    //=========================
    //   Game State Variables
    //=========================
    public boolean intro = true;
    public boolean mainMenu = false;
    public boolean gameScreen = false;
    public boolean battle = false;
    public boolean frozen = false;
    public boolean paused = false;
    public boolean gameover = false;
    public int stratum = 1; //"depth" of rooms: 1 - normal, 2 - ice, 3 - lava, 4 - ???
    public int introTime = 100;
    public boolean incStratumTime = false;
    public int transitionCoolDown = 1000;

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
    //    Quest ion Variables
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
    int loadingBarX = 0;
    int loadingBarW = 0;
    int loadingBarProg = 0;
    ArrayList<GuiButton> buttons = new ArrayList();

    //=========================
    //      Room Variables
    //=========================
    public Room[][] rooms = new Room[5][5];
    int currentRoomX = 4;
    int currentRoomY = 4;
    int transitionProg = -1000;
    int transitionDir = -1;
    public boolean transitioning = false;

    transient BufferedImage spriteSheetTex;
    transient BufferedImage[][] spritesTex;
    int texRows = 20;
    int texCols = 20;
    int texD = 50;

    //=============================
    //=============================
    //==========CONSTRUCTOR========
    //=============================
    public GameEngine() {
        mt = new MusicThread();
        td = new Thread(mt);
        this.qt = new QuestionPanel(this);
        pl = new Player(this, "/resources/pl_fem.png", keys);
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
        font[0] = UsefulSnippets.loadFont("/resources/Deadhead Rough.ttf");
        font[1] = UsefulSnippets.loadFont("/resources/RODUSreg300.otf");
        loadRooms();
//        UsefulSnippets.playMusic("/resources/game.mp3");
        mu = new Mp3Player("src/resources/game_opening.mp3");
        td.start();
    }

    //=============================
    public void loadRooms() {
        DecimalFormat form = new DecimalFormat("00");
        for (int x = 0; x < rooms.length; x++) {
            for (int y = 0; y < rooms[0].length; y++) {
                String mapNum = form.format(rand.nextInt(11) + 1);
//                String mapNum = form.format(9);
                rooms[x][y] = new Room(this,
                        "/resources/Levels/Level_" + mapNum + "_" + stratum + ".png",
                        "/resources/Levels/Spawn_Map_" + mapNum + ".png",
                        x,
                        y);
            }
        }
    }

    public void tick() {
        if (intro) {

        }
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
        
        if (keys.isKeyDown("Escape") && gameScreen) {
            if (!paused) {
                paused = true;
            } else {
                paused = false;
            }
        }
        
        if (gameScreen && !frozen) {
            pl.tick();
            for (int i = 0; i < rooms[currentRoomX][currentRoomY].en_arry.size(); i++) {
                rooms[currentRoomX][currentRoomY].en_arry.get(i).tick();
            }

            if (currentRoomX == rooms.length - 1 && currentRoomY == rooms[0].length - 1) {
                if ((pl.xLocFeet > rooms[currentRoomX][currentRoomY].trapDoorX * 50) && (pl.xLocFeet < rooms[currentRoomX][currentRoomY].trapDoorX * 50 + 50) && (pl.yLocFeet > rooms[currentRoomX][currentRoomY].trapDoorY * 50) && (pl.yLocFeet < rooms[currentRoomX][currentRoomY].trapDoorY * 50 + 50)) {
                    frozen = true;
                    transitionDir = 4;
                    transitioning = true;
                }
            }
        }

        if (battle) {
            qt.tick();
        }

        if (transitionProg == 0 && transitionDir == 4 && transitionCoolDown == 0) {
            currentRoomX = 0;
            currentRoomY = 0;
            stratum++;
            transitionCoolDown = 1000;
            loadRooms();
        } else {
            if (transitionCoolDown > 0) {
                transitionCoolDown--;
            }
        }

        if (paused) {
            frozen = true;
        }

        if (transitionProg > 1000) {
            transitioning = false;
            transitionProg = -1000;
            pl.graceTimer = 100;
        }
        
        if(gameover){
        
        }
    }

    public void switchTo(String mode) {
        if (mode.equals("menu")) {
            this.mainMenu = true;
            this.gameScreen = false;
            this.battle = false;
            this.gameover = false;
        }
        if (mode.equals("game")) {
            this.mainMenu = false;
            this.gameScreen = true;
            this.battle = false;
            this.frozen = false;
            this.gameover = false;
        }
        if (mode.equals("battle")) {
            this.mainMenu = false;
            this.gameScreen = true;
            this.battle = true;
            this.qt.startNewEquation();
            this.gameover = false;
        }
        
        if (mode.equals("gameover")) {
            this.mainMenu = false;
            this.gameScreen = false;
            this.battle = false;
            this.gameover = true;
        }
        
    }

    public Keyboard getKeyboard() {
        return keys;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void drawTransition(int i, Graphics g) {
        frozen = true;
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
            case 4:
                int alpha = Math.min(255, Math.max(0, (int) (256 * (((-0.002 * (Math.pow((transitionProg + 1000) - 1000, 2))) / 2000) + 1))));
                g.setColor(new Color(0, 0, 0, alpha));
                g.fillRect(0, 0, 1000, 1000);
                g.setFont(font[0]);
                g.setColor(new Color(50, 50, 50, alpha));
                g.drawString("Stratum " + stratum, 105, 105);
                g.setColor(new Color(255, 255 - (70 * stratum), 255 - (70 * stratum), alpha));
                g.drawString("Stratum " + stratum, 100, 100);

        }
        frozen = false;
    }

    public void loadResources() {  //Called on loadstate, reloads all transient resources into gameEngine
        pl.loadResources("/resources/pl_fem.png");
        font[0] = UsefulSnippets.loadFont("/resources/Deadhead Rough.ttf");
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

    public class MusicThread implements Runnable {

        public boolean listening = true;   //listener is always listening

        @Override
        public void run() {
            if(SettingsProperties.programSound){
                mu.play();
            }
        }

    }

}
