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
    int baseForNumber = 209;
    String CurrentNumber = "";
    boolean answerRight = false;

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
            if ((numButton(209, 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "1";
                delay = 50;
            }
            if ((numButton(baseForNumber + 43, 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "2";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 2), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "3";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 3), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "4";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 4), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "5";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 5), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "6";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 6), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "7";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 7), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "8";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 8), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "9";
                delay = 50;
            }
            if ((numButton(baseForNumber + (43 * 9), 351, 40, 40, mX, mY) && delay == 0)) {
                CurrentNumber = CurrentNumber + "0";
                delay = 50;
            }
        }
        if(CurrentNumber != "" && Integer.parseInt(CurrentNumber) == product){
            answerRight = true;
            if(timer == 0){
                 world.switchTo("game");
            }else{
                timer--;
                if(timer < 0){
                    timer = 0;
                }
            }
        }
        delay--;
        if (delay < 0) {
            delay = 0;
        }
    }

    public boolean numButton(int x, int y, int l, int h, int mx, int my) {
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
        CurrentNumber = "";
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
        if(answerRight){
            g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.BLACK);
        }
        g.drawString(CurrentNumber, this.xLoc + 220 - (CurrentNumber.length() * 3), this.yLoc + 150);
        g.drawString("This now works try it!", this.xLoc + 100, this.yLoc + 200);
    }
}
