import java.awt.*;
import java.awt.image.BufferStrategy;

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

    Circle c1 = new Circle(100, 100, 50, 0);
    Circle c2 = new Circle(200, 200, 50, 0);

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
        if(pressing[UP])  c1.goForward(6);
        if(pressing[DN])  c1.goBackward(3);
        if(pressing[LT])  c1.turnLeft(3);
        if(pressing[RT])  c1.turnRight(3);
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

        if(r1.contains(mouseX, mouseY)) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);
//        if(r1.overlaps(r2)) g.setColor(Color.RED);
//        else g.setColor(Color.BLACK);
        r1.draw(g);
        r2.draw(g);

//        if (tank1.overlaps(tank2)) g.setColor(Color.RED);
//        else g.setColor(Color.BLACK);
//        tank1.draw(g);
//        tank2.draw(g);

        if (c1.overlaps(c2)) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);
        c1.draw(g);
        c2.draw(g);

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