package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class PowerLawContrastModifier implements Processor {

    private final double gamma;

    public PowerLawContrastModifier(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        short[] powerLawLookupTable = new short[256];

        for(int i = 0; i < powerLawLookupTable.length; i++)
        {
            powerLawLookupTable[i] = (short) (Math.pow(i, gamma) / Math.pow(255, gamma - 1));
        }
        return ImageOp.pixelop(bufferedImage, powerLawLookupTable);
    }

    @Override
    public String getProcessName() {
        return "Power Law";
    }
}
