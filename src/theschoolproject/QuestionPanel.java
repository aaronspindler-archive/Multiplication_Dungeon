package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class QuestionPanel {

    Random gen = new Random();
    static int QUESTION_RANGE = 15;
    BufferedImage BG = UsefulSnippets.loadImage("/resources/QuestionBG.png");
    BufferedImage Boxes = UsefulSnippets.loadImage("/resources/boxesGrey.png");
    BufferedImage Numbers = UsefulSnippets.loadImage("/resources/numbers.png");
    String equation;
    int product;
    int xLoc = 200;
    int yLoc = 100;
    GamePanel world;
    int mX = -1;
    int mY = -1;
    int delay = 0;
    int timer = 150;
    int xButtonsOffset = 209;
    String currentNumber = "";
    boolean answerRight = false;
    final int delaySet = 50;
    final int answerLength = 3;

    public QuestionPanel(GamePanel gp) {
        equation = makeEquation();
        world = gp;
    }

    public void tick() {
        mX = world.mouse.getX();
        mY = world.mouse.getY();
        //System.out.println(mX + ", " + mY);
        //209 351
        if (world.mouse.isMousePressed() && answerRight == false) {
            if ((buttonBounds(209, 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "1";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + 43, 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "2";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 2), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "3";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 3), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "4";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 4), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "5";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 5), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "6";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 6), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "7";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 7), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "8";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 8), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "9";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + (43 * 9), 351, 40, 40, mX, mY) && delay == 0 && currentNumber.length() < answerLength)) {
                currentNumber = currentNumber + "0";
                delay = delaySet;
            }
            if ((buttonBounds(xButtonsOffset + 306, 293, 50, 50, mX, mY) && delay == 0)) {
                if (currentNumber.length() > 1) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                } else {
                    currentNumber = "";
                }
                delay = delaySet;
            }
        }
        if (currentNumber != "" && Integer.parseInt(currentNumber) == product) {
            answerRight = true;
            if (timer == 0) {
                world.switchTo("game");
                world.en_arry.remove(world.en_index);
                world.pl.score = world.pl.score + 200;
            } else {
                timer--;
                if (timer < 0) {
                    timer = 0;
                }
            }
        }
        delay--;
        if (delay < 0) {
            delay = 0;
        }
    }

    public boolean buttonBounds(int x, int y, int l, int h, int mx, int my) {
        if ((mx >= x && my >= y) && (mx < (x + l)) && my < y + h) {
            return true;
        }
        return false;
    }

    public void startNewEquation() {
        equation = makeEquation();
    }

    public final String makeEquation() {
        String eq = "";
        answerRight = false;
        currentNumber = "";
        timer = 150;
        int multiplicand = gen.nextInt(QUESTION_RANGE) + 1;
        int multiplier = gen.nextInt(QUESTION_RANGE) + 1;
        this.product = multiplicand * multiplier;
        eq = (multiplicand + "x" + multiplier);
        return eq;
    }

    public void draw(Graphics g) {
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
    }
}
