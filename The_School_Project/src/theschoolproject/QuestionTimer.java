package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class QuestionTimer {

    Random gen = new Random();
    static int QUESTION_RANGE = 10;
    BufferedImage BG = UsefulSnippets.loadImage("/resources/QuestionBG.png");
    
    public QuestionTimer() {
        
    }

    public void tick() {

    }

    public String makeEquation() {
        String equation = "";
        int multiplicand = gen.nextInt(QUESTION_RANGE) + 1;
        int multiplier = gen.nextInt(QUESTION_RANGE) + 1;
        int product = multiplicand * multiplier;
        equation = (multiplicand + "x" + multiplier);
        return equation;
    }

    public void draw(Graphics g) {
        String equation = makeEquation();
        g.setColor(Color.white);
        g.drawImage(BG, 200, 100, null);
        g.setColor(Color.black);
        g.drawString("Multiplication Question", 370, 121);
        g.drawString("1x2 = ", 370, 201);
        
        //Don't use ratchet text boxes and buttons --> They can't use keyboard
        //It also looks god awful
        //SchoolProjectForm.checkButton.setVisible(true);
        //SchoolProjectForm.productTextField.setVisible(true);
    }
}
