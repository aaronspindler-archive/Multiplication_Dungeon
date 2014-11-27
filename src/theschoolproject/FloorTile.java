package theschoolproject;

import java.awt.Color;
import java.util.Random;

public class FloorTile {

    int TILE_ID = -1;
    Random rand = new Random();
    Color c;
    //Tile 0 = Test
    //Tile 1 = Wall
    //Tile 2 = Floor
    //Tile 3 = Door
    //Tile 4 = Water
    //Tile 5 = Lava

    public FloorTile(int id) {
        this.TILE_ID = id;
//        c = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        c = Color.GRAY;
    }

    public void setTile(int i) {
        TILE_ID = i;
    }

    public boolean isSolid() {
        switch (TILE_ID) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return true;
            case 5:
                return true;
            default:
                return false;
        }
    }

    public boolean isDoor() {
        switch (TILE_ID) {
            case 3:
                return true;
            default:
                return false;
        }
    }

    public Color getColor() {

        switch (TILE_ID) {
            case 0:
                c = Color.MAGENTA;
                break;
            case 1:
                c = new Color(65, 43, 3);
                break;
            case 2:
                c = new Color(130, 86, 6);
                break;
            case 3:
                c = new Color(32, 21, 1);
                break;
        }
        return c;
    }
}
