/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author root
 */
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
