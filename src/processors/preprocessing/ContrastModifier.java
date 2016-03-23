package processors.preprocessing;

import visionsystem.Histogram;
import visionsystem.HistogramException;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

@Deprecated
public class ContrastModifier {

    public BufferedImage modifyWithPowerLaw(BufferedImage bufferedImage, double gamma) {
        short[] powerLawLookupTable = new short[256];

        for(int i = 0; i < powerLawLookupTable.length; i++)
        {
            powerLawLookupTable[i] = (short) (Math.pow(i, gamma) / Math.pow(255, gamma - 1));
        }
        return ImageOp.pixelop(bufferedImage, powerLawLookupTable);
    }
    
    
    public BufferedImage modifyWithLinearStretching(BufferedImage bufferedImage, double gradient, double amount){

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

    public BufferedImage modifyWithHistogramEqualisation(BufferedImage bufferedImage, Histogram histogram) throws HistogramException {
        short[] histogramEqualisationLookupTable = new short[256];

        for(int i = 0; i < histogramEqualisationLookupTable.length; i++)
        {
            histogramEqualisationLookupTable[i] = (short) Math.max(0, (256 * histogram.getCumulativeFrequency(i) / histogram.getNumSamples()) - 1);
        }

        return ImageOp.pixelop(bufferedImage, histogramEqualisationLookupTable);
    }

}