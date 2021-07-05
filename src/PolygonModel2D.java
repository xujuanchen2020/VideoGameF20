import java.awt.*;

public class PolygonModel2D {
    double x, y, cosA, sinA;
    int A;
    int[][] x_structure;
    int[][] y_structure;

    public PolygonModel2D(double x, double y, int A, int[][] x_structure, int[][] y_structure){
        this.x = x;
        this.y = y;
        this.A = A;
        this.x_structure = x_structure;
        this.y_structure = y_structure;
        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];
    }

    public void moveBy(double dx, double dy){
        x += dx;
        y += dy;
    }

    public void goForward(double d){
        double dx = d*cosA;
        double dy = d*sinA;
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

    public void draw(Graphics g){
        int _x;
        int _y;
        int[] x_points = new int[100];
        int[] y_points = new int[100];

        for(int polygon = 0; polygon < x_structure.length; polygon++){
            for(int vertex = 0; vertex < x_structure[polygon].length; vertex++){
                _x = x_structure[polygon][vertex];
                _y = y_structure[polygon][vertex];
                x_points[vertex] = (int) (_x*cosA - _y*sinA + x);
                y_points[vertex] = (int) (_y*cosA + _x*sinA + y);
            }
            g.drawPolygon(x_points, y_points, x_structure[polygon].length);
        }
    }
}
