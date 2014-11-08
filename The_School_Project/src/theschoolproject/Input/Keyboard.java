package theschoolproject.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    boolean[] keys = new boolean[525];

    public boolean isKeyDown(String s) {
        if (s.equals("up")) {
            return keys[KeyEvent.VK_UP];
        }
        if (s.equals("down")) {
            return keys[KeyEvent.VK_DOWN];
        }
        if (s.equals("left")) {
            return keys[KeyEvent.VK_LEFT];
        }
        if (s.equals("right")) {
            return keys[KeyEvent.VK_RIGHT];
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

}
