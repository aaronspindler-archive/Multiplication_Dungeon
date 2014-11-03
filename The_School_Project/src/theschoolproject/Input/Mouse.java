/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Mouse implements MouseListener,MouseMotionListener{

    int xLoc;
    int yLoc;
    boolean pressed = false;
    public ArrayList Xcoords = new ArrayList<>();
    public ArrayList Ycoords = new ArrayList<>();
    
    
    public boolean isMousePressed(){
        return pressed;
    }
    
    public int getX(){
        return xLoc;
    }
    
    public int getY(){
        return yLoc;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        pressed= true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        Xcoords.add(e.getX());
        Ycoords.add(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) { 
        xLoc = e.getX();
        yLoc = e.getY();
    }
    
}

