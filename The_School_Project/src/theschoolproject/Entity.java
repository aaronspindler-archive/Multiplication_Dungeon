/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import theschoolproject.Input.Keyboard;
import java.awt.Rectangle;

/**
 *
 * @author root
 */
public class Entity {

    GamePanel world;

    double xLoc = 200;
    double yLoc = 200;
    double xLocLegs = this.xLoc + 32;
    double yLocLegs = this.yLoc + 64;

    boolean uBlock, lBlock, dBlock, rBlock = false;

    int tileLocX;
    int tileLocY;
    int orientation = 2; //0 - North, 1 - East, 2 - South, 3 - West
    int[] animSeq = {0, 1, 2, 1};
    double spd = 0;
    boolean isMoving = false;

    int rows = 4;
    int columns = 3;
    int height = 64;
    int width = 64;
    int animCycle = 1;
    BufferedImage spriteSheetB;
    BufferedImage[][] sprites;
    String[] spritePaths = {"/resources/pl_sprite.png", "/resources/en1_sprite.png"};
    Random rand = new Random();

    Keyboard keys;

    public Entity(GamePanel gp, String sp) {
        world = gp;
        sprites = new BufferedImage[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                spriteSheetB = UsefulSnippets.loadImage(sp);
                sprites[i][j] = spriteSheetB.getSubimage(j * width, i * height, width, height);
            }
        }
        this.xLoc = rand.nextInt(400) + 50;
        this.yLoc = rand.nextInt(300) + 50;
    }

    public void draw(Graphics g) {
        g.drawImage(sprites[orientation][animSeq[animCycle]], (int) xLoc, (int) yLoc, null);
    }

    public void quadTree() {
        System.out.println("" + uBlock + rBlock + dBlock + lBlock);
        if ((this.tileLocX != 0) && (this.tileLocY != 0)) {
            uBlock = false;
            rBlock = false;
            dBlock = false;
            lBlock = false;
            if (world.rooms[0].tileArry[(this.tileLocX + 1) * this.tileLocY].TILE_ID == 1) {
                this.rBlock = true;
            }
            if (world.rooms[0].tileArry[(this.tileLocX - 1) * this.tileLocY].TILE_ID == 1) {
                this.lBlock = true;
            }
            if (world.rooms[0].tileArry[this.tileLocX * (this.tileLocY + 1)].TILE_ID == 1) {
                this.dBlock = true;
            }
            if (world.rooms[0].tileArry[this.tileLocX * (this.tileLocY - 1)].TILE_ID == 1) {
                this.uBlock = true;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) this.xLoc, (int) this.yLoc, (int) width, (int) height);
    }

    public void tick() {

        quadTree();

        if (isMoving && spd < 3) {
            spd = spd + 0.5;
        }

        if (!isMoving && spd > 0) {
            spd = spd - 0.5;
            animCycle = 1;
        }

        if (animCycle > 2) {
            animCycle = 0;
        }

        switch (orientation) {
            case 0:
                if (!uBlock) {
                    setLocation(this.getX(), this.getY() - spd);
                }
                break;
            case 1:
                if (!rBlock) {
                    setLocation(this.getX() + spd, this.getY());
                }
                break;
            case 2:
                if (!dBlock) {
                    setLocation(this.getX(), this.getY() + spd);
                }
                break;
            case 3:
                if (!lBlock) {
                    setLocation(this.getX() - spd, this.getY());
                }
                tileLocX = (int) (xLocLegs) / 50;
                tileLocY = (int) (yLocLegs) / 50;
                break;
        }
    }

    public void setLocation(double x, double y) {
        this.xLoc = x;
        this.yLoc = y;
        xLocLegs = this.xLoc + 32;
        yLocLegs = this.yLoc + 64;
        tileLocX = (int) (xLocLegs) / 50;
        tileLocY = (int) (yLocLegs) / 50;
    }

    public double getX() {
        return xLoc;
    }

    public double getY() {
        return yLoc;
    }
}
