package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;

public class QuestionTimer {

    public QuestionTimer() {

    }
    
    public void tick(){
    
    }
    
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.fill3DRect(200, 100, 450, 300, true);
        g.setColor(Color.black);
        g.drawString("QuestionMessage_1", 200, 300);
    }
}
