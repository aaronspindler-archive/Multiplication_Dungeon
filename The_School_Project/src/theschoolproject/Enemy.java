/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theschoolproject;

/**
 *
 * @author root
 */
public class Enemy extends Entity {

    public Enemy(String sp) {
        super(sp);
    }
    
    @Override
    public void tick(){
        this.setLocation(this.xLoc + rand.nextDouble()-.5, this.yLoc + rand.nextDouble()-.5);
    }

}
