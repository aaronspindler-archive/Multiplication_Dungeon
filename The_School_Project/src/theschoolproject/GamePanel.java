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

public class GamePanel extends JPanel {

    Random r = new Random();

    public GamePanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(150, 75, 0));
        g.fillRect(0, 0, 910, 710);
    }

    public GamePanel(LayoutManager layout) {
        super(layout);
    }
}
