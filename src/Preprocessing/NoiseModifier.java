package preprocessing;

import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

public class NoiseModifier {

    public BufferedImage removeNoiseUsingConvolution(BufferedImage bufferedImage, float[] mask){
        return ImageOp.convolver(bufferedImage, mask);
    }

    public BufferedImage removeNoiseUsing3X3Convolution(BufferedImage bufferedImage){
        final float[] LOWPASS3X3 = {1/9.f, 1/9.f, 1/9.f,
                                    1/9.f, 1/9.f, 1/9.f,
                                    1/9.f, 1/9.f, 1/9.f};

        return ImageOp.convolver(bufferedImage, LOWPASS3X3);
    }

    public BufferedImage removeNoiseUsing5X5Convolution(BufferedImage bufferedImage){
        final float[] LOWPASS5X5 = {1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
                1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
                1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
                1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
                1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f};

        return ImageOp.convolver(bufferedImage, LOWPASS5X5);
    }

    public BufferedImage removeUsingMedian(BufferedImage bufferedImage, int median){
        return ImageOp.median(bufferedImage, median);
    }

}
