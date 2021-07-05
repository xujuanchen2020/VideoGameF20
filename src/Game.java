import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends GameApplet implements Runnable {
    private Thread thread;
    private Display display;
    private final String title;
    private final int width, height;
    private boolean running = false;

    BattleLord battlelord = new BattleLord(100,100,BattleLord.DOWN);
    Circle[] c = new Circle[5];
    Line[] L = new Line[3];
    Random rnd = new Random(System.currentTimeMillis());

    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init(){
        display = new Display(title, width, height);
        display.getjFrame().addKeyListener(this);
        display.getjFrame().addMouseListener(this);
        display.getjFrame().addMouseMotionListener(this);
        display.getCanvas().addMouseListener(this);
        display.getCanvas().addMouseMotionListener(this);

        double gravity = 0.7;
        for(int i=0; i<c.length; i++){
            c[i] = new Circle(rnd.nextInt(900)+50, rnd.nextInt(500)+50, 15, 0);
            c[i].setAcceleration(0, gravity);
        }

        double[][] v = {
                {1200, 680,    0, 680},
                { 1100,  0, 1100, 680},
                {  50, 680,   50,   0},
        };

        for(int i=0; i<v.length; i++){
            L[i] = new Line(v[i][0], v[i][1], v[i][2], v[i][3]);
        }
    }

    @Override
    public void run() {
        init();

        double fps = 60.0;  //ticks per second
        double timePerTick = 1000000000 / fps; // nanoseconds per tick
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime)/timePerTick; //0.00024++  ticks
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) { // if delta reached 1 tick
                tick();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    @Override
    public void tick() {
        for(int i=0; i<c.length; i++){
            if(pressing[UP])  c[i].jump(4);
            if(pressing[DN])  c[i].goBackward(3);
            if(pressing[LT])  c[i].turnLeft(3);
            if(pressing[RT])  c[i].toss(10, -20);
            c[i].move();
        }

        for(int i = 0; i < c.length-1; i++) {
            for(int j = i + 1; j < c.length; j++ ) {
                if(c[i].overlaps(c[j])) {
                    c[i].pushes(c[j]);
                    c[i].bounceOff(c[j]);
                }
            }
        }

        for(int i = 0; i < c.length; i++) {
            if(c[i].overlaps(L[0])) {
                c[i].isPushedBackBy(L[0]);
                if(Math.abs(c[i].vx)<0.01) c[i].vx = 0;
                c[i].vx = 0.99 * c[i].vx;
                c[i].bounceOff(L[0]);
            }

            if(c[i].overlaps(L[1])) {
                c[i].isPushedBackBy(L[1]);
                c[i].bounceOff(L[1]);
            }

            if(c[i].overlaps(L[2])) {
                c[i].isPushedBackBy(L[2]);
                c[i].bounceOff(L[2]);
            }
        }

        if(pressing[UP])  battlelord.moveUp(7);
        if(pressing[DN])  battlelord.moveDown(7);
        if(pressing[LT])  battlelord.moveLeft(7);
        if(pressing[RT])  battlelord.moveRight(7);

    }

    @Override
    public void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0, width, height);

        for(int i=0; i<c.length; i++){
            c[i].draw(g);
        }

        for(int i=0; i<L.length; i++){
            L[i].draw(g);
        }

        battlelord.draw(g);

        bs.show();
        g.dispose();
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void mousePressed(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();

        for(int i=0; i<L.length; i++){
            L[i].grabbedAt(mouseX, mouseY);
        }

        for(int i=0; i<c.length; i++){
            c[i].grabbedAt(mouseX, mouseY);
        }
    }

    public void mouseDragged(MouseEvent e){
        int nx = e.getX();
        int ny = e.getY();
        int dx = nx - mouseX;
        int dy = ny - mouseY;

        mouseX = nx;
        mouseY = ny;

        for(int i=0; i<L.length; i++){
            L[i].draggedBy(dx, dy);
        }

        for(int i=0; i<c.length; i++){
            if(c[i].held){
                c[i].moveBy(dx, dy);
                c[i].ay = 0;
            }
        }
    }

    public void mouseReleased(MouseEvent e){
        for(int i=0; i<L.length; i++){
            L[i].released();
        }
        for(int i=0; i<c.length; i++){
            c[i].released();
            c[i].ay = 0.7;
        }
    }
}