import java.awt.*;

public class Line {
    double x1, y1, x2, y2, Nx, Ny;

    public Line(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        double vx = x2 - x1;
        double vy = y2 - y1;

        double mag = Math.sqrt(vx*vx + vy*vy);

        double ux = vx / mag;
        double uy = vy / mag;

        Nx = uy;
        Ny = -ux;
    }

    public double distanceTo(double x, double y){
        return Nx * (x1 - x) + Ny * (y1 - y);
    }

    public void draw(Graphics g){
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
}
