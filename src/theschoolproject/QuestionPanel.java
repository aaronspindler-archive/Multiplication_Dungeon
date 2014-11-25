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
    
    public QuestionPanel() {
        equation = makeEquation();
    }

    public void tick() {

    }

    public void startNewEquation(){
        equation = makeEquation();
    }
    
    public final String makeEquation() {
        String eq = "";
        int multiplicand = gen.nextInt(QUESTION_RANGE) + 1;
        int multiplier = gen.nextInt(QUESTION_RANGE) + 1;
        int product = multiplicand * multiplier;
        eq = (multiplicand + "x" + multiplier);
        return eq;
    }

    public void draw(Graphics g) {       
        g.drawImage(BG, 200, 100, null);
        g.drawImage(Boxes, 200, 100, null);
        g.drawImage(Numbers, 200, 100, null);
        g.setColor(Color.black);
        g.drawString("Multiplication Question", 370, 121);
        g.drawString(equation, 370, 201);
        
        //Don't use ratchet text boxes and buttons --> They can't use keyboard
        //It also looks god awful
        //SchoolProjectForm.checkButton.setVisible(true);
        //SchoolProjectForm.productTextField.setVisible(true);
    }
}
