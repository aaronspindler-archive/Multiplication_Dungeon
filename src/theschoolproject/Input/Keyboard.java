package theschoolproject.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import theschoolproject.GamePanel;

public class Keyboard implements KeyListener {

    GamePanel world;
    boolean isKeyPressed = false;

    public Keyboard(GamePanel gp) {
        world = gp;
    }
    boolean[] keys = new boolean[525];

    public boolean isKeyPressed() {
        return isKeyPressed;
    }

    public boolean isKeyDown(String s) {
        if (!world.transitioning) {
            isKeyPressed = true;
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
            if (s.equals("w")) {
                return keys[KeyEvent.VK_W];
            }
            if (s.equals("a")) {
                return keys[KeyEvent.VK_A];
            }
            if (s.equals("s")) {
                return keys[KeyEvent.VK_S];
            }
            if (s.equals("d")) {
                return keys[KeyEvent.VK_D];
            }
            if (s.equals("1")) {
                return keys[KeyEvent.VK_1];
            }
            if (s.equals("2")) {
                return keys[KeyEvent.VK_2];
            }
            if (s.equals("3")) {
                return keys[KeyEvent.VK_3];
            }
            if (s.equals("4")) {
                return keys[KeyEvent.VK_4];
            }
            if (s.equals("5")) {
                return keys[KeyEvent.VK_5];
            }
            if (s.equals("6")) {
                return keys[KeyEvent.VK_6];
            }
            if (s.equals("7")) {
                return keys[KeyEvent.VK_7];
            }
            if (s.equals("8")) {
                return keys[KeyEvent.VK_8];
            }
            if (s.equals("9")) {
                return keys[KeyEvent.VK_9];
            }
            if (s.equals("0")) {
                return keys[KeyEvent.VK_0];
            }
            if (s.equals("Backspaces")) {
                return keys[KeyEvent.VK_BACK_SPACE];
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

}
