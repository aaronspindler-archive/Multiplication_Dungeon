package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class QuestionTimer {

    Random gen = new Random();
    static int QUESTION_RANGE = 10;

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
        g.fill3DRect(200, 100, 450, 300, true);
        g.setColor(Color.black);
        g.drawString("Multiplication Question", 370, 121);
        g.drawString("1x2 = ", 370, 201);
        SchoolProjectForm.checkButton.setVisible(true);
        SchoolProjectForm.productTextField.setVisible(true);
    }
}
