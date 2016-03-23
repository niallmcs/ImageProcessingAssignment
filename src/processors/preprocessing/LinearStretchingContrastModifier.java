package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class LinearStretchingContrastModifier implements Processor {

    private final double gradient;
    private final double amount;

    public LinearStretchingContrastModifier(double gradient, double amount) {
        this.gradient = gradient;
        this.amount = amount;
    }

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        short[] linearStretchingLookupTable = new short[256];
        double output = 0;

        for(int i = 0; i < linearStretchingLookupTable.length; i++)
        {
            if (i < ((amount * -1)/gradient)){
                output = 0;
            }
            else if (i > ((255 - amount) / gradient)){
                output = 255;
            }
            else{
                output =  gradient * i + amount;
            }
            linearStretchingLookupTable[i] = (short) output;
        }
        return ImageOp.pixelop(bufferedImage, linearStretchingLookupTable);
    }

    @Override
    public String getProcessName() {
        return "Linear Stretching";
    }
}
