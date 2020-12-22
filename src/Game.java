import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends GameApplet implements Runnable {
    private Thread thread;
    private Display display;

    private final String title;
    private final int width, height;

    private boolean running = false;

    Rect r1 = new Rect(10, 10, 200, 100);
    Rect r2 = new Rect(100, 200, 100, 100);

    Tank tank1 = new Tank(150,150, 90);
    Tank tank2 = new Tank(150, 350, 90);

    Circle[] c = new Circle[50];
    Line L = new Line(200, 0, 1000, 500);

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

        for(int i=0; i<c.length; i++){
            c[i] = new Circle(rnd.nextInt(300)+50, rnd.nextInt(300)+50, 15, 0);
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
        if(pressing[UP])  c[0].goForward(6);
        if(pressing[DN])  c[0].goBackward(3);
        if(pressing[LT])  c[0].turnLeft(3);
        if(pressing[RT])  c[0].turnRight(3);

        for(int i=1; i<c.length; i++){
            c[i].goForward(rnd.nextInt(2));
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

        for(int i=0; i<c.length-1; i++){
            for(int j=i+1; j<c.length; j++){
                if(c[i].overlaps(c[j])) c[i].pushes(c[j]);
            }
        }

        for(int i=0; i<c.length; i++){
            if(c[i].overlaps(L)) c[i].isPushedBackBy(L);
            c[i].draw(g);
        }
        L.draw(g);

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