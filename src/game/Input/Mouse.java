package game.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import game.GameEngine;

public class Mouse implements MouseListener, MouseMotionListener, Serializable {

    int xLoc;
    int yLoc;
    boolean pressed = false;
    GameEngine world;
//    public ArrayList Xcoords = new ArrayList<>();
//    public ArrayList Ycoords = new ArrayList<>();

    public int y2, y1, x2, x1; //starting and ending points of mousedrag action

    public Mouse(GameEngine ge) {
        world = ge;
    }

    public boolean isMousePressed() {
        return pressed;
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }

    public void unPress() {
        pressed = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
//        Xcoords = new ArrayList<>();
//        Ycoords = new ArrayList<>();
        //world.frozen = false;
        // world.battle = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
//        Xcoords = new ArrayList<>();
//        Ycoords = new ArrayList<>();
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        Xcoords = new ArrayList<>();
//        Ycoords = new ArrayList<>();
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        Xcoords.add(e.getX());
//        Ycoords.add(e.getY());
        x2 = e.getX();
        y2 = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xLoc = e.getX();
        yLoc = e.getY();
    }

}
