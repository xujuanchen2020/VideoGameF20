import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {

    public static BufferedImage LoadImage(String name){
        try {
            return ImageIO.read(ImageLoader.class.getResource(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
