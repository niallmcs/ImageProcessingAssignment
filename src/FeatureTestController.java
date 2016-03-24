import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import util.DisplayManager;
import util.ImageHelper;
import visionsystem.JVision;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class FeatureTestController {

    public static void main(String[] args) throws Exception {
        ImageModel imageModel = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_TOMATO);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));

        imageModel = pipelineProcessor.process(imageModel);

        DisplayManager displayManager = new DisplayManager(new JVision());
        displayManager.drawPipeline(pipelineProcessor.generateImageModels(imageModel));

        FeatureExtractor featureExtractor = new FeatureExtractor();
        int area = featureExtractor.computeArea(imageModel.getBufferedImage());
        int perimeter = featureExtractor.computePerimeter(imageModel.getBufferedImage());
        System.out.println("Area: " + area);
        System.out.println("Perimeter: " + perimeter);
    }
}
