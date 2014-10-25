/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    Random r = new Random();
    FloorTile[][] ft = new FloorTile[17][13];
    
    public GamePanel() {
          for (int w = 0; w < 17; w++) {
            for (int h = 0; h < 13; h++) {
                if(w == 0 || w == 16 || h == 0 || h == 12){
                    ft[w][h] = new FloorTile(1);
                }else{
                    ft[w][h] = new FloorTile(0);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);     
        for (int w = 0; w < 17; w++) {
            for (int h = 0; h < 13; h++) {
              g.setColor(ft[w][h].getColor());
              g.fillRect(w*50, h*50, 50, 50);
            }
        }
    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }
}
