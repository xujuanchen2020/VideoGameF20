import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private Thread thread;
    private Display display;

    private final String title;
    private final int width, height;

    private boolean running = false;

    Rect r1 = new Rect(10, 10, 200, 100);
    Rect r2 = new Rect(100, 200, 100, 100);

    Tank tank1 = new Tank(150,150, 90);
    Tank tank2 = new Tank(150, 350, 90);

    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init(){
        display = new Display(title, width, height);
        MouseManager mouseManager = new MouseManager();
        KeyManager keyManager = new KeyManager();

        display.getjFrame().addKeyListener(keyManager);
        display.getjFrame().addMouseListener(mouseManager);
        display.getjFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

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

    private void tick() {
        if (KeyManager.up_pressed) r1.moveBy(0, -5);
        if (KeyManager.dn_pressed) r1.moveBy(0, 5);
        if (KeyManager.lt_pressed) r1.moveBy(-5, 0);
        if (KeyManager.rt_pressed) r1.moveBy(5, 0);

//        if (KeyManager.up_pressed) tank1.moveForwardBy(10);
//        if (KeyManager.dn_pressed) tank1.moveBy(0, 5);
//        if (KeyManager.lt_pressed) tank1.rotateBy(-5);
//        if (KeyManager.rt_pressed) tank1.rotateBy(5);

        tank2.moveBy(5,0);
        tank2.rotateBy(5);
    }

    private void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0, width, height);

//        if(r1.contains(MouseManager.mouseX, MouseManager.mouseY)) g.setColor(Color.RED);
//        else g.setColor(Color.BLACK);

        if(r1.overlaps(r2)) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);
        r1.draw(g);
        r2.draw(g);

        g.setColor(Color.BLACK);
        tank1.draw(g);
        tank2.draw(g);

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