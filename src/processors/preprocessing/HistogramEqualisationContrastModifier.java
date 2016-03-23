package processors.preprocessing;

import processors.Processor;
import visionsystem.Histogram;
import visionsystem.HistogramException;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class HistogramEqualisationContrastModifier implements Processor {


    @Override
    public BufferedImage process(BufferedImage bufferedImage) {

        Histogram histogram = null;
        try {
            histogram = new Histogram(bufferedImage);
            short[] histogramEqualisationLookupTable = new short[256];

            for(int i = 0; i < histogramEqualisationLookupTable.length; i++)
            {
                histogramEqualisationLookupTable[i] = (short) Math.max(0, (256 * histogram.getCumulativeFrequency(i) / histogram.getNumSamples()) - 1);
            }

            return ImageOp.pixelop(bufferedImage, histogramEqualisationLookupTable);
        } catch (HistogramException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getProcessName() {
        return "Histogram Equalisation";
    }
}
