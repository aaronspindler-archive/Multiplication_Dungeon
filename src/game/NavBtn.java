/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.Serializable;

/**
 *
 * @author Arthur
 */
public class NavBtn implements Serializable{

    int xLoc;
    int yLoc;
    int width;
    int height;
    String label = "";
    boolean isMouseOver = false;

    public NavBtn(int x, int y, int w, int h, String s) {
        xLoc = x;
        yLoc = y;
        width = w;
        height = h;
        label = s;
    }
}
