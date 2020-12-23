import java.awt.*;

public class Line {
    double x1, y1, x2, y2, Nx, Ny, c;
    int held_at = 0;

    public Line(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        computeNormal();
        c = x1 * Nx + y1 * Ny;
    }

    private void computeNormal() {
        double vx = x2 - x1;
        double vy = y2 - y1;

        double mag = Math.sqrt(vx*vx + vy*vy);

        double ux = vx / mag;
        double uy = vy / mag;

        Nx = -uy;
        Ny = ux;
    }

    public double distanceTo(double x, double y){
        return Nx * (x - x1) + Ny * (y - y1);
//        return x * Nx + y * Ny - c;
    }

    public void draw(Graphics g){
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    public void grabbedAt(int mx, int my){
        double dx;
        double dy;
        dx = x1 - mx;
        dy = y1 - my;
        if(dx * dx + dy * dy < 49) held_at = 1;

        dx = x2 - mx;
        dy = y2 - my;
        if(dx * dx + dy * dy < 49) held_at = 2;
    }

    public void draggedBy(int dx, int dy){
        if(held_at == 1){
            x1 += dx;
            y1 += dy;
        }
        if(held_at == 2){
            x2 += dx;
            y2 += dy;
        }
    }

    public void released(){
        held_at = 0;
    }

    public boolean isHeld(){
        return held_at != 0;
    }
}
