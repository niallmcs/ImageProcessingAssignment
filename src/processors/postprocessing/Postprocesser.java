package processors.postprocessing;

import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Cathan O'Donnell on 22/03/2016.
 */
public class Postprocesser {

    public Postprocesser() {

    }

    public BufferedImage open(BufferedImage bufferedImage,int amount) {
        return ImageOp.open(bufferedImage, amount);
    }

    public BufferedImage close(BufferedImage bufferedImage,int amount) {
        return ImageOp.close(bufferedImage, amount);
    }

}
