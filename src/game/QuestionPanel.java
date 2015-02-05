package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

public class QuestionPanel implements Serializable {

    Random gen = new Random();
    static int QUESTION_RANGE = 15;
    transient BufferedImage BG = UsefulSnippets.loadImage("/resources/QuestionBG.png");
    transient BufferedImage Boxes = UsefulSnippets.loadImage("/resources/boxesGrey.png");
    transient BufferedImage Numbers = UsefulSnippets.loadImage("/resources/numbers.png");
    String equation;
    int product;
    int xLoc = 200;
    int yLoc = 100;
    GameEngine world;
    int mX = -1;
    int mY = -1;
    int delay = 0;
    int winDelay = 150;
    int xButtonsOffset = 209;
    String currentNumber = "";
    boolean answerRight = false;
    final int delaySet = 25;
    final int answerLength = 3;
    int timer = 0;
    int maxTimer = 2000;
    int clock;
    double addScore = 0.0;

    public QuestionPanel(GameEngine ge) {
        equation = makeEquation();
        world = ge;
    }

    public void tick() {
        mX = world.mouse.getX();
        mY = world.mouse.getY();
        //System.out.println(mX + ", " + mY);
        //209 351
        double d = ((double)timer / (double)maxTimer);
        clock = (int) (360 - (d * 360));
        if (((world.mouse.isMousePressed() && answerRight == false) || (world.keys.isKeyPressed() && answerRight == false)) && (delay == 0)) {
            if (currentNumber.length() < answerLength) {
                if ((buttonBounds(209, 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("1"))) {
                    currentNumber = currentNumber + "1";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + 43, 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("2"))) {
                    currentNumber = currentNumber + "2";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 2), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("3"))) {
                    currentNumber = currentNumber + "3";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 3), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("4"))) {
                    currentNumber = currentNumber + "4";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 4), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("5"))) {
                    currentNumber = currentNumber + "5";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 5), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("6"))) {
                    currentNumber = currentNumber + "6";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 6), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("7"))) {
                    currentNumber = currentNumber + "7";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 7), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("8"))) {
                    currentNumber = currentNumber + "8";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 8), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("9"))) {
                    currentNumber = currentNumber + "9";
                    delay = delaySet;
                }
                if ((buttonBounds(xButtonsOffset + (43 * 9), 351, 40, 40, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("0"))) {
                    currentNumber = currentNumber + "0";
                    delay = delaySet;
                }
            }
            if ((buttonBounds(xButtonsOffset + 306, 293, 50, 50, mX, mY) && world.mouse.isMousePressed()) || (world.keys.isKeyDown("Backspace"))) {
                if (currentNumber.length() > 1) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                } else {
                    currentNumber = "";
                }
                delay = delaySet;
            }
            world.mouse.unPress();
            world.keys.unPress();
        }
        if (currentNumber != "" && Integer.parseInt(currentNumber) == product) {
            answerRight = true;
            
            if (winDelay == 0) {
                if (addScore == 0){
                    addScore = ((double) ((double)clock/360)*1000);
                    world.pl.score = world.pl.score + (int) addScore;
//                    System.out.println(addScore + " - " + clock);
                }
            
                world.switchTo("game");
                world.rooms[world.currentRoomX][world.currentRoomY].en_arry.remove(world.en_index);
                addScore = 0.0;
                
            } else {
                winDelay--;
                if (winDelay < 0) {
                    winDelay = 0;
                }
            }
        }
        delay--;
        if (delay < 0) {
            delay = 0;
        }

        timer++;
        if (timer > maxTimer && !answerRight) {
            System.out.println(world.pl.lives);
            world.pl.loseLife();
            if (world.pl.lives <= 0) {
                world.endMillis = System.currentTimeMillis();
                world.switchTo("gameover");
            } else {
                world.switchTo("game");
                world.rooms[world.currentRoomX][world.currentRoomY].en_arry.remove(world.en_index);
                addScore = 0.0;
            }
        }
    }

    public boolean buttonBounds(int x, int y, int l, int h, int mx, int my) {
        if ((mx >= x && my >= y) && (mx < (x + l)) && my < y + h) {
            return true;
        }
        return false;
    }

    public void startNewEquation() {
        equation = "";
        equation = makeEquation();
    }

    public final String makeEquation() {
        timer = 0;
        String eq = "";
        answerRight = false;
        currentNumber = "";
        winDelay = 150;
        int multiplicand = gen.nextInt(QUESTION_RANGE) + 1;
        int multiplier = gen.nextInt(QUESTION_RANGE) + 1;
        this.product = multiplicand * multiplier;
        eq = (multiplicand + "x" + multiplier);
        return eq;
    }

    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        g.drawImage(BG, this.xLoc, this.yLoc, null);
        g.drawImage(Boxes, this.xLoc, this.yLoc, null);
        g.drawImage(Numbers, this.xLoc, this.yLoc, null);
        g.setColor(Color.black);
        g.drawString("Multiplication Question", this.xLoc + 10, this.yLoc + 30);
        g.drawString(equation, this.xLoc + 200, this.yLoc + 100);
        if (answerRight) {
            g.setColor(Color.GREEN);
            g.drawString("Correct!", this.xLoc + 100, this.yLoc + 200);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawString(currentNumber, this.xLoc + 220 - (currentNumber.length() * 3), this.yLoc + 150);
//        g.drawString("This now works try it!", this.xLoc + 100, this.yLoc + 200);
        g.setColor(new Color(0,0,0, 125));
        if(timer < maxTimer){
            g.fillArc(577, 112, 60, 60, 0, clock - 2);
        }
        if(clock < (360 / 3)){
            g.setColor(Color.red);
        }
        if(clock >= (360 / 3)){
            g.setColor(Color.yellow);
        }
        if(clock > (360 / 3) * 2){
            g.setColor(Color.green);
        }
        if(timer != 0 && timer < maxTimer){
           
            g.fillArc(575, 110, 60, 60, 0, clock);
            g.setColor(Color.BLACK);
            g.drawArc(575, 110, 60, 60, 0, clock);
        }
    }

    public void loadResources() {
        BG = UsefulSnippets.loadImage("/resources/QuestionBG.png");
        Boxes = UsefulSnippets.loadImage("/resources/boxesGrey.png");
        Numbers = UsefulSnippets.loadImage("/resources/numbers.png");
    }
}