package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class MedianNoiseModifier implements Processor {

    private final int median;

    public MedianNoiseModifier(int median) {
        this.median = median;
    }

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        return ImageOp.median(bufferedImage, median);
    }

    @Override
    public String getProcessName() {
        return "Median Noise Removal";
    }
}
