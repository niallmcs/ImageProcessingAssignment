package processors.segmentation;

import processors.Processor;
import util.ImageHelper;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

public class EdgeSegmentation implements Processor {


    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        short[] edgeLookupTable = new short[ImageHelper.NUM_COLOURS];

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        final float[] HIGHPASS1X2 =     {-10.0f, 10.0f,
                0.0f, 0.0f};

        final float[] HIGHPASS2X1 =     {-10.0f, 0.0f,
                10.0f, 0.0f};

        BufferedImage highPassHorizontal = ImageOp.convolver(bufferedImage, HIGHPASS1X2);
        BufferedImage highPassVertical = ImageOp.convolver(bufferedImage, HIGHPASS2X1);


        BufferedImage resultantImage = ImageOp.imagrad(highPassHorizontal, highPassVertical);

        return resultantImage;
    }

    @Override
    public String getProcessName() {
        return "Edge Segmentation";
    }


}
