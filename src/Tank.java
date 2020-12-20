import java.awt.*;

public class Tank {
    private int x, y, A;
    double[] cos = Lookup.genCos();
    double[] sin = Lookup.genSin();
    private Rect AABB;

    public static int[][] x_structure = {
            {-40, -40,  40,  40},
            {-10, -10,  10,  10},
            { 35,  35,  10,  10},
            {-35, -35,  35,  35},
            {-35, -35,  35,  35},
            {4, 20, 30, 5},
            {4, 20, 30, 5},
            {-4, -20, -30, -5},
            {-4, -20, -30, -5},
    };
    public static int[][] y_structure = {
            {-25,  25,  25, -25},
            {-10,  10,  10, -10},
            { -3,   3,   3,  -3},
            {-35, -25, -25, -35},
            { 35,  25,  25,  35},
            {3, 3, 70, 50},
            {-3, -3, -70, -50},
            {3, 3, 70, 50},
            {-3, -3, -70, -50},
    };

    public Tank(int x, int y, int A){
        this.x = x;
        this.y = y;
        this.A = A;

        AABB = new Rect(x-40, y-40, 80, 80); // Rect( x, y,w, h)
    }

    public boolean overlaps(Tank tank){
        return AABB.overlaps(tank.AABB);
    }

    public void moveBy(int dx, int dy){
        x += dx;
        y += dy;

        AABB.moveBy(dx, dy);
    }

    public void moveForwardBy(int d){
        int dx = (int) (d*cos[A]);
        int dy = (int) (d*sin[A]);
        moveBy(dx, dy);
    }

    public void rotateBy(int dA){
        A += dA;
        if(A > 359) A -= 360;
        if(A < 0) A += 360;
    }

    public void draw(Graphics g){
        int _x;
        int _y;
        int[] x_points = new int[4];
        int[] y_points = new int[4];

        for(int polygon=0; polygon<x_structure.length; polygon++) {
            for (int vertex = 0; vertex < x_structure[polygon].length; vertex++) {
                _x = x_structure[polygon][vertex];
                _y = y_structure[polygon][vertex];
                x_points[vertex] = (int) (_x * cos[A] - _y * sin[A] + x);
                y_points[vertex] = (int) (_y * cos[A] + _x * sin[A] + y);
            }
            g.drawPolygon(x_points,y_points,x_structure[polygon].length);
        }

        AABB.draw(g);
    }
}
