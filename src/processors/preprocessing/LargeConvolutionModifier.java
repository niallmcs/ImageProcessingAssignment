package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class LargeConvolutionModifier implements Processor {

    private final float[] LOWPASS5X5 = {1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
            1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
            1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
            1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
            1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f};

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        return ImageOp.convolver(bufferedImage, LOWPASS5X5);
    }

    @Override
    public String getProcessName() {
        return "5x5 Convolution Noise Removal";
    }
}
