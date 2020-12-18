import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public static boolean up_pressed = false;
    public static boolean dn_pressed = false;
    public static boolean lt_pressed = false;
    public static boolean rt_pressed = false;

    public static final int UP = KeyEvent.VK_UP;
    public static final int DN = KeyEvent.VK_DOWN;
    public static final int LT = KeyEvent.VK_LEFT;
    public static final int RT = KeyEvent.VK_RIGHT;

    public KeyManager() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == UP) up_pressed = true;
        if (key == DN) dn_pressed = true;
        if (key == LT) lt_pressed = true;
        if (key == RT) rt_pressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == UP) up_pressed = false;
        if (key == DN) dn_pressed = false;
        if (key == LT) lt_pressed = false;
        if (key == RT) rt_pressed = false;
    }
}