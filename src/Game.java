import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends GameApplet implements Runnable {
    private Thread thread;
    private Display display;
    private final String title;
    private final int width, height;
    private boolean running = false;

    Circle[] c = new Circle[10];

    Line L = new Line(1350, 600, 10, 600);
    Line L2 = new Line(1300, 0, 1300, 800);
    Line L3 = new Line(50, 800, 50, 0);

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
            c[i] = new Circle(rnd.nextInt(1000)+50, rnd.nextInt(500)+50, 15, 0);
            c[i].setAcceleration(0, gravity);
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
        for(int i=1; i<c.length; i++){
            if(pressing[UP])  c[0].goForward(6);
            if(pressing[DN])  c[0].goBackward(3);
            if(pressing[LT])  c[0].turnLeft(3);
            if(pressing[RT])  c[0].turnRight(3);
            c[i].move();
        }

        for(int i = 0; i < c.length; i++) {
            if(c[i].overlaps(L)) {
                c[i].isPushedBackBy(L);
                c[i].vy = -0.9* c[i].vy;
            }

            if(c[i].overlaps(L2)) {
                c[i].isPushedBackBy(L2);
                c[i].vx = -0.5* c[i].vx;
            }

            if(c[i].overlaps(L3)) {
                c[i].isPushedBackBy(L3);
                c[i].vx = -0.5* c[i].vx;
            }
        }
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
        L.draw(g);
        L2.draw(g);
        L3.draw(g);

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
}