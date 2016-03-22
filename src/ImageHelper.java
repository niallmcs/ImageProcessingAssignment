import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class ImageHelper {

    public static BufferedImage getSampleImage(){
        return ImageOp.readInImage("images/training/tomato/tomato1-066-153.png");
    }

}
