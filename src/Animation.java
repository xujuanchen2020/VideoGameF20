
import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] image;
    private int current = 0;
    private int delay;
    private int duration;

    public Animation(String name, int duration, int count, String ext) {
        image = new BufferedImage[count];
        for(int i = 0; i < count; i++)
            image[i] = ImageLoader.LoadImage(name+i+ext);
        this.duration = duration;
        delay = duration;
    }

    public BufferedImage getCurrentImage() {
        if(delay == 0) {
            current++;
            if(current == image.length)   current = 1;
            delay = duration;
        }
        delay--;
        return image[current];
    }

    public BufferedImage getStillImage() {
        return image[0];
    }
}