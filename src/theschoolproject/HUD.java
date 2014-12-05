package theschoolproject;

import java.awt.Color;
import java.awt.Graphics2D;

public class HUD {

    GamePanel world;
    int lives;
    int score;

    public HUD(GamePanel gp) {
        world = gp;
    }

    public void draw (Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(world.font);
        g.drawString("Score: "+world.pl.score, 50, 50);
    }
}
