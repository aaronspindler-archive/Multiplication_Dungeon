/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Graphics;
import theschoolproject.Input.Keyboard;

/**
 *
 * @author root
 */
public class Player extends Entity {

    Keyboard keys;
    int distToMove = 0;
    int graceTimer = 0;

    public Player(GamePanel gp, String sp, Keyboard k) {
        super(gp, sp);
        keys = k;

        this.xLoc = 393;
        this.yLoc = 281;
    }

    public void tick() {
        checkCollision();
        if (distToMove > 0) {
            distToMove--;
        }
        if (graceTimer > 0) {
            graceTimer--;
        }

        isMoving = false;
        if (keys.isKeyDown("up") || keys.isKeyDown("w")) {
            orientation = 0;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("left") || keys.isKeyDown("a")) {
            orientation = 3;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("down") || keys.isKeyDown("s")) {
            orientation = 2;
            isMoving = true;
            animCycle++;
        }
        if (keys.isKeyDown("right") || keys.isKeyDown("d")) {
            orientation = 1;
            isMoving = true;
            animCycle++;
        }

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
                if (!uBlock || dTu > collMinDist) {
                    setLocation(this.getX(), this.getY() - spd);
                }
                break;
            case 1:
                if (!rBlock || dTr > collMinDist) {
                    setLocation(this.getX() + spd, this.getY());
                }
                break;
            case 2:
                if (!dBlock || dTd > collMinDist) {
                    setLocation(this.getX(), this.getY() + spd);
                }
                break;
            case 3:
                if (!lBlock || dTl > collMinDist) {
                    setLocation(this.getX() - spd, this.getY());
                }
                break;
        }
    }
}
