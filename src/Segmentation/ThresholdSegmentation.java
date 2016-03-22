package segmentation;

import visionsystem.ImageOp;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ThresholdSegmentation {

    private final int NUM_COLOURS = 256;

    public BufferedImage applyManualThresholdSegmentation(BufferedImage bufferedImage, int threshold) {

        short[] thresholdLookupTable = new short[NUM_COLOURS];

        for(int i = 0; i < thresholdLookupTable.length; i++ ) {
            if(i <= threshold) {
                thresholdLookupTable[i] = 255;
            } else {
                thresholdLookupTable[i] = 0;
            }
        }

        return ImageOp.pixelop(bufferedImage, thresholdLookupTable);
    }

    public BufferedImage applyAutomaticThresholdSegmentation(BufferedImage bufferedImage, double a) {

        double threshold = getMeanFromImage(bufferedImage) + a * getStandardDeviationOfImage(bufferedImage);

        short[] thresholdLookupTable = new short[NUM_COLOURS];

        for(int i = 0; i < thresholdLookupTable.length; i++ ) {
            if(i <= threshold) {
                thresholdLookupTable[i] = 255;
            } else {
                thresholdLookupTable[i] = 0;
            }
        }

        return ImageOp.pixelop(bufferedImage, thresholdLookupTable);
    }

    public int getMeanFromImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Raster raster = bufferedImage.getRaster();

        int total = 0;
        for(int i = 0; i < width; i++) {
            for( int j = 0; j < height; j++) {
                total += raster.getSample(j,i,0);
            }
        }

        return total / (width * height);
    }

    public int getStandardDeviationOfImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int mean = getMeanFromImage(bufferedImage);
        int total = 0;

        Raster raster = bufferedImage.getRaster();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                total += Math.pow(raster.getSample(j,i,0) - mean,2);
            }
        }

        int standardDeviation = (int) Math.sqrt(total / ((width * height) - 1));
        return standardDeviation;
    }

}
