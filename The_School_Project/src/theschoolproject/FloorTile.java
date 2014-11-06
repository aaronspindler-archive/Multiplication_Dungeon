package theschoolproject;

import java.awt.Color;

/**
 *
 * @author Arthur
 */
public class FloorTile {

    int TILE_ID = -1;

    public FloorTile(int id) {
        this.TILE_ID = id;
    }

    public boolean isSolid() {
        switch (TILE_ID) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return false;
        }
    }

    public Color getColor() {
        Color c = Color.GRAY;
        switch (TILE_ID) {
            case 1:
                c = new Color(65, 43, 3);
                break;
            case 2:
                c = new Color(130, 86, 6);
        }
        return c;
    }
}
