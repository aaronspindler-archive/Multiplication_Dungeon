package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class QuestionPanel {

    Random gen = new Random();
    static int QUESTION_RANGE = 10;
    BufferedImage BG = UsefulSnippets.loadImage("/resources/QuestionBG.png");
    BufferedImage Boxes = UsefulSnippets.loadImage("/resources/boxesGrey.png");
    BufferedImage Numbers = UsefulSnippets.loadImage("/resources/numbers.png");
    String equation;
    int product;
    int xLoc = 200;
    int yLoc = 100;
    GamePanel world;

    public QuestionPanel(GamePanel gp) {
        equation = makeEquation();
        world = gp;
    }

    public void tick() {

    }

    public void startNewEquation() {
        equation = makeEquation();
    }

    public final String makeEquation() {
        String eq = "";
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
    }
}
