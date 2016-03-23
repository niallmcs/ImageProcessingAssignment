package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class SmallConvolutionModifier implements Processor {

    private final float[] LOWPASS3X3 = {1/9.f, 1/9.f, 1/9.f,
            1/9.f, 1/9.f, 1/9.f,
            1/9.f, 1/9.f, 1/9.f};

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        return ImageOp.convolver(bufferedImage, LOWPASS3X3);
    }

    @Override
    public String getProcessName() {
        return "3x3 Convolution Noise Removal";
    }
}
