import java.awt.*;

public class Sprite {
    double x;
    double y;
    int action;
    boolean moving = false;
    public static final int UP    = 0;
    public static final int DOWN  = 1;
    public static final int LEFT  = 2;
    public static final int RIGHT = 3;
    public static final int JUMP  = 4;

    Animation[] animation;

    public Sprite(double x, double y, int action, String[] name, int duration, int count, String ext) {
        this.x = x;
        this.y = y;
        this.action = action;
        animation = new Animation[name.length];

        for(int i = 0; i < name.length; i++)
            animation[i] = new Animation(name[i], duration, count, ext);

    }

    public void moveBy(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void moveUp(double dy) {
        y -= dy;
        action = UP;
        moving = true;
    }

    public void moveDown(double dy) {
        y += dy;
        action = DOWN;
        moving = true;
    }

    public void moveLeft(double dx) {
        x -= dx;
        action = LEFT;
        moving = true;
    }

    public void moveRight(double dx) {
        x += dx;
        action = RIGHT;
        moving = true;
    }


    public void draw(Graphics g) {
        if(moving)
            g.drawImage(animation[action].getCurrentImage(), (int)x, (int)y, null);
        else
            g.drawImage(animation[action].getStillImage(), (int)x, (int)y, null);
        moving = false;
    }

}