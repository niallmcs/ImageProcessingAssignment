import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.ClassificationPipeline;
import pipeline.PipelineProcessor;
import processors.postprocessing.ClosePostProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.HistogramEqualisationContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import processors.segmentation.EdgeSegmentation;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;

import java.util.List;

/**
 * Created by Niall McShane on 24/03/2016.
 */
public class DemoClassificationPipelineController {

    public static void main(String[] args) throws Exception {

        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new BrightnessModifier(75));
        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new MedianNoiseModifier(2));
        pipelineProcessor.addProcessor(new EdgeSegmentation());
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.8));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));

        ImageModel testImageModel = TestImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);

        List<ImageModel> imageModels = TrainingImageHelper.getStartingTrainingImages();
        List<ImageModel> testImages = TestImageHelper.getStartingTestingImages();

        ClassificationPipeline classificationPipeline = new ClassificationPipeline(pipelineProcessor, imageModels);
        classificationPipeline.processTrainingSet();


        int valueOfK = 3;
        String result = classificationPipeline.classify(testImageModel, valueOfK, true, FeatureExtractor.AREA, FeatureExtractor.PERIMETER);

        Double correctPercentage = classificationPipeline.classifyImagesAndReturnSuccess(testImages, valueOfK, true, FeatureExtractor.COMPACTNESS);
        System.out.println("Result for classification: " + String.valueOf(correctPercentage) + "%");

    }
}
