import java.awt.*;

public class Rect {
    private int x, y, w, h;

    public Rect(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean overlaps(Rect r){
        return (r.x + r.w >= x) &&
                (x + w >= r.x) &&
                (r.y + r.h >= y) &&
                (y + h >= r.y);
    }

    public boolean contains(int mx, int my) {
        return  (mx >= x  ) &&
                (mx <= x+w) &&
                (my >= y  ) &&
                (my <= y+h);
    }

    public void moveBy(int dx, int dy){
        x += dx;
        y += dy;
    }

    public void draw(Graphics g){
        g.drawRect(x, y, w, h);
    }
}
