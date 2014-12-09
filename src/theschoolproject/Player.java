package theschoolproject;

import theschoolproject.Input.Keyboard;

public class Player extends Entity {

    Keyboard keys;
    int lives;
    int score;
    int distToMove = 0;
    int graceTimer = 0;
    boolean autoMove = false;

    public Player(GamePanel gp, String sp, Keyboard k) {
        super(gp, sp);
        keys = k;

        this.xLoc = 100;
        this.yLoc = 100;
    }

    @Override
    public void checkCollision() {
        try {
//        System.out.println(" " + dTu + " " + dTd + " " + dTr + " " + dTl);
            if ((this.tileLocX != 0) && (this.tileLocY != 0)) {
                uBlock = false;
                rBlock = false;
                dBlock = false;
                lBlock = false;

                dTu = 99;
                dTr = 99;
                dTd = 99;
                dTl = 99;

                if (world.rooms[world.currentRoomX][world.currentRoomY].tileArry[(this.tileLocX + 1) + this.tileLocY * world.rooms[world.currentRoomX][world.currentRoomY].width].isSolid()) {
                    this.rBlock = true;
                    dTr = ((50 * (this.tileLocX + 1))) - (int) this.xLocFeet;
                }   //Right

                if (world.rooms[world.currentRoomX][world.currentRoomY].tileArry[(this.tileLocX - 1) + this.tileLocY * world.rooms[world.currentRoomX][world.currentRoomY].width].isSolid()) {
                    this.lBlock = true;
                    dTl = ((int) this.xLocFeet - (50 * (this.tileLocX)));
                }   //Left

                if (world.rooms[world.currentRoomX][world.currentRoomY].tileArry[this.tileLocX + (this.tileLocY + 1) * world.rooms[world.currentRoomX][world.currentRoomY].width].isSolid()) {
                    this.dBlock = true;
                    dTd = ((50 * (this.tileLocY + 1))) - (int) this.yLocFeet;
                }   //Up

                if (world.rooms[world.currentRoomX][world.currentRoomY].tileArry[this.tileLocX + (this.tileLocY - 1) * world.rooms[world.currentRoomX][world.currentRoomY].width].isSolid()) {
                    this.uBlock = true;
                    dTu = ((int) this.yLocFeet - (50 * (this.tileLocY)));
                }   //Down
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        isMoving = false;
        if (this.yLocFeet < 600 && this.yLocFeet > 40) {
            checkCollision();
        }

        if (this.yLocFeet < 40 && world.currentRoomY > 0) {
            nextRoom(0);
            if (world.transitionProg > 0) {
                this.xLoc = 400;
                this.yLoc = 550;
//                uBlock = false;
//                dBlock = false;
            }
        }
        if (this.yLocFeet > 625 && world.currentRoomY < world.rooms.length-1) {
            nextRoom(2);
            if (world.transitionProg > 0) {
                this.xLoc = 400;
                this.yLoc = 50;
            }
        }
        if (this.xLocFeet > 825 && world.currentRoomX < world.rooms[0].length-1) {
            nextRoom(1);
            if (world.transitionProg > 0) {
                this.xLoc = 50;
                this.yLoc = 300;
                rBlock = false;
            }
        }
        if (this.xLocFeet < 40 && world.currentRoomX > 0) {
            nextRoom(3);
            if (world.transitionProg > 0) {
                this.xLoc = 750;
                this.yLoc = 300;
                lBlock = false;
            }
        }

        if (distToMove > 3) {
            distToMove--;
            spd = 2;
            move(orientation);
        }
        if (graceTimer > 0) {
            graceTimer--;
        }

        if (keys.isKeyDown("up") || keys.isKeyDown("w")) {
            move(0);
        }
        if (keys.isKeyDown("right") || keys.isKeyDown("d")) {
            move(1);
        }
        if (keys.isKeyDown("down") || keys.isKeyDown("s")) {
            move(2);
        }
        if (keys.isKeyDown("left") || keys.isKeyDown("a")) {
            move(3);
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

    public void move(int dir) {
        orientation = dir;
        isMoving = true;
        animCycle++;
    }

    public void nextRoom(int i) {
        world.transitioning = true;
        switch (i) {
            case 0:
                if (world.currentRoomY > 0 && world.transitionProg > 0) {
                    world.currentRoomY--;
                }
                break;
            case 1:
                if (world.currentRoomX < world.rooms.length - 1 && world.transitionProg > 0) {
                    world.currentRoomX++;
                }
                break;
            case 2:
                if (world.currentRoomY < world.rooms[0].length - 1 && world.transitionProg > 0) {
                    world.currentRoomY++;
                }
                break;
            case 3:
                if (world.currentRoomX > 0 && world.transitionProg > 0) {
                    world.currentRoomX--;
                }
                break;

        }
        world.transitionDir = i;
    }
}
