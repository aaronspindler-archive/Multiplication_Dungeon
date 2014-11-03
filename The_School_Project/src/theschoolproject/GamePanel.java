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

public class GamePanel extends JPanel {

    Random r = new Random();
    FloorTile[][] ft = new FloorTile[17][15];
    boolean mainMenu = true;
    boolean gameScreen = true;
    Keyboard keys = new Keyboard();

    public GamePanel() {
        this.addKeyListener(keys);
        
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
        }

    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }
}
