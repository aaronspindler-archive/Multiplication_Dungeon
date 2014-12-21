package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class HUD implements Serializable {

    GameEngine world;
    int lives;
    int score;

    public HUD(GameEngine ge) {
        world = ge;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("Score: " + world.pl.score, 52, 52);
        g.drawString("Lives: " + world.pl.lives, 52, 102);
        g.setColor(Color.WHITE);
        g.setFont(world.font);
        g.drawString("Score: " + world.pl.score, 50, 50);
        g.drawString("Lives: " + world.pl.lives, 50, 100);

        for (int i = 0; i < world.pl.lives; i++) {
            g.drawImage(null, null, i, i);
        }
    }
}
