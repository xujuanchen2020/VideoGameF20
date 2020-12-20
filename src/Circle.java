import java.awt.*;

public class Circle {
    double x, y, r, cosA, sinA;
    int A;

    public Circle(double x, double y, double r, int A){
        this.x = x;
        this.y = y;
        this.r = r;
        this.A = A;
        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];
    }

    public void draw(Graphics g){
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
        g.drawLine((int)x, (int)y, (int)(x+r*cosA), (int)(y+r*sinA));
    }

    public void moveBy(double dx, double dy){
        x += dx;
        y += dy;
    }

    public void goForward(double d){
        double dx = d * cosA;
        double dy = d * sinA;
        moveBy(dx, dy);
    }

    public void goBackward(double d){
        double dx = -d * cosA;
        double dy = -d * sinA;
        moveBy(dx, dy);
    }

    public void turnLeft(int dA){
        A -= dA;
        if(A < 0) A += 360;
        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];
    }

    public void turnRight(int dA){
        A += dA;
        if(A > 359) A -= 360;
        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];
    }

    public boolean overlaps(Circle c){
        double dx = x - c.x;
        double dy = y - c.y;
        double d2 = dx*dx + dy*dy;
        return d2 < (r+c.r)*(r+c.r);
    }
}
