import preprocessing.BrightnessModifier;
import preprocessing.ContrastModifier;
import preprocessing.NoiseModifier;
import segmentation.ThresholdSegmentation;
import util.ImageHelper;
import visionsystem.Histogram;
import visionsystem.HistogramException;
import visionsystem.JVision;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane & Cathan O'Donnell on 22/03/2016.
 *
 * @author Niall McShane
 * @author Cathan O'Donnell
 */
public class DityStart {
    
    public static void main(String[] args) throws HistogramException {

        //create jvision window
        JVision jVision = new JVision();
        jVision.setSize(1400, 1000);

        //create brightness modifier
        BrightnessModifier brightnessModifier = new BrightnessModifier(30);

        //display result
        BufferedImage bufferedImage = brightnessModifier.modifyBrightness(ImageHelper.getRandomSampleImage());

        //display brighter image
        jVision.imdisp(bufferedImage, "Brightness enhancement", 1, 1);

        //contrast enhancement
        ContrastModifier contrastModifier = new ContrastModifier();

        BufferedImage linearStretchedImage = contrastModifier.modifyWithLinearStretching(bufferedImage, 1, 80.0);
        BufferedImage powerLawedImage = contrastModifier.modifyWithPowerLaw(bufferedImage, 0.25);

        //Histogram
        Histogram histogram = new Histogram();
        histogram.computeHistogram(linearStretchedImage);
        BufferedImage histogramImage = contrastModifier.modifyWithHistogramEqualisation(linearStretchedImage, histogram);
//        histogramImage = contrastModifier.modifyWithLinearStretching(histogramImage, 1, 80.0);

        //display results
        jVision.imdisp(linearStretchedImage, "Linear Stretched enhancement", 1, 301);
        jVision.imdisp(powerLawedImage, "Power Lawed enhancement", 301, 301);
        jVision.imdisp(histogramImage, "Histogram enhancement", 602, 301);

        //noise reduction
        NoiseModifier noiseModifier = new NoiseModifier();
        BufferedImage noiseReducedHistogramImage3 = noiseModifier.removeNoiseUsing3X3Convolution(histogramImage);
        BufferedImage noiseReducedHistogramImage5 = noiseModifier.removeNoiseUsing5X5Convolution(histogramImage);
        BufferedImage noiseReducedHistogramImageMedianFirst = noiseModifier.removeUsingMedian(histogramImage, 3);
        BufferedImage noiseReducedHistogramImageMedianSecond = noiseModifier.removeUsingMedian(histogramImage, 5);

        //draw it
        jVision.imdisp(noiseReducedHistogramImage3, "Noise reducted Histo 3x3", 1, 601);
        jVision.imdisp(noiseReducedHistogramImage5, "Noise reducted Histo 5x5", 301, 601);
        jVision.imdisp(noiseReducedHistogramImageMedianFirst, "Noise reducted Histo Median 3x", 601, 601);
        jVision.imdisp(noiseReducedHistogramImageMedianSecond, "Noise reducted Histo Median 5x", 901, 601);

        //segmentation
        ThresholdSegmentation thresholdSegmentation = new ThresholdSegmentation();
        BufferedImage thresholdedNoiseReducedHisto = thresholdSegmentation.applyAutomaticThresholdSegmentation(noiseReducedHistogramImageMedianFirst, 0.9);

        jVision.imdisp(thresholdedNoiseReducedHisto, "Thresholded median Noise reduced histo", 901, 1);

        //display results

        //something to do with feature extraction
    }
}
