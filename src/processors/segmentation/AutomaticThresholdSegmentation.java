package processors.segmentation;

import processors.Processor;
import util.ImageHelper;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class AutomaticThresholdSegmentation implements Processor {

    private final double a;

    public AutomaticThresholdSegmentation(double a) {
        this.a = a;
    }

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        double threshold = ImageHelper.getMeanFromImage(bufferedImage) + a * ImageHelper.getStandardDeviationOfImage(bufferedImage);

        short[] thresholdLookupTable = new short[ImageHelper.NUM_COLOURS];

        for(int i = 0; i < thresholdLookupTable.length; i++ ) {
            if(i >= threshold) {
                thresholdLookupTable[i] = 255;
            } else {
                thresholdLookupTable[i] = 0;
            }
        }

        return ImageOp.pixelop(bufferedImage, thresholdLookupTable);
    }

    @Override
    public String getProcessName() {
        return "Automatic Threshold";
    }
}
