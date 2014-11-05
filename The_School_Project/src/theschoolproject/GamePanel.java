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
import theschoolproject.Input.Keyboard;
import theschoolproject.Input.Mouse;

public class GamePanel extends JPanel {
   
    Random r = new Random();
    FloorTile[][] ft = new FloorTile[17][15];
    boolean mainMenu = true;
    boolean gameScreen = true;
    Keyboard keys = new Keyboard();
    Mouse mouse = new Mouse();

    public GamePanel() {
        this.addKeyListener(keys);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        for (int w = 0; w < 17; w++) {
            for (int h = 0; h < 15; h++) {
                if (w == 0 || w == 16 || h == 0 || h == 12) {
                    ft[w][h] = new FloorTile(1);
                } else {
                    ft[w][h] = new FloorTile(0);
                }
            }
        }
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mainMenu) {
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        if (gameScreen) {
            for (int w = 0; w < 17; w++) {
                for (int h = 0; h < 15; h++) {
                    g.setColor(ft[w][h].getColor());
                    g.fillRect(w * 50, h * 50, 50, 50);
                }
            }
            for(int i = 0; i < mouse.Xcoords.size()-1; i++){
                int x = (int)mouse.Xcoords.get(i);
                int y = (int)mouse.Ycoords.get(i);
                int x1 = (int)mouse.Xcoords.get(i+1);
                int y1 = (int)mouse.Ycoords.get(i+1);
                g.drawLine(x, y, x1, y1);
            }
            
        }

    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }
    
    public class ListenerThread implements Runnable{
        
        private boolean listening = true;   //listener is always listening
        
        @Override
        public void run() {
            while (listening){
                repaint();
            }
        }
        
    }
}
