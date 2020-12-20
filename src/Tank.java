import java.awt.*;

public class Tank extends PolygonModel2D{
    Rect AABB;

    public static int[][] tank_x_structure = {
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


    public static int[][] tank_y_structure = {
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

    public Tank(int x, int y, int A) {
        super(x, y, A, tank_x_structure, tank_y_structure);
        AABB = new Rect(x-40, y-40, 80, 80);
    }

    public void moveBy(int dx, int dy) {
        super.moveBy(dx, dy);
        AABB.moveBy(dx, dy);
    }

    public boolean overlaps(Tank tank) {
        return AABB.overlaps(tank.AABB);
    }

    public void draw(Graphics g) {
        super.draw(g);
        AABB.draw(g);
    }
}