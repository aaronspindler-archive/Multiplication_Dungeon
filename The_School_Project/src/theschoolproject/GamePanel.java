/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class GamePanel extends JPanel {

    Random r = new Random();
    
    public GamePanel() {
    } 

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.red);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        
        for(int i = 0; i < 17; i++){
            for(int h = 0; h < 13; h++){
                g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                g.fillRect(i * 50, h * 50, 50, 50);
            }
        }
    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }
}
