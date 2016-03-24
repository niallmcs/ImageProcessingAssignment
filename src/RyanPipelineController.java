import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.ClassificationPipeline;
import pipeline.PipelineProcessor;
import processors.postprocessing.ClosePostProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.LinearStretchingContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by RyanMcCleave on 23/03/2016.
 */
public class RyanPipelineController {

    public static void main(String [] args) throws Exception {

        ImageModel imageModel = TrainingImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);


        //set up a JVision for mac
        JVision jVision = new JVision();
        jVision.setSize(1800, 1000);

////        DisplayManager displayManager = new DisplayManager(jVision);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();

        //Brightness Enhancement
        pipelineProcessor.addProcessor(new BrightnessModifier(-10));

        //PREPROCESS
        //Noise Reduction

        //pipelineProcessor.addProcessor(new SmallConvolutionModifier());
        //pipelineProcessor.addProcessor(new LargeConvolutionModifier());


        //Contrast Enhancement
        pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(2,1.5));
//        pipelineProcessor.addProcessor(new PowerLawContrastModifier(1.5));
//        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));

        pipelineProcessor.addProcessor(new BrightnessModifier(-10));



        //SEGMENT
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.6));
//        pipelineProcessor.addProcessor(new EdgeSegmentation());
//        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(1));
//        pipelineProcessor.addProcessor(new ManualThresholdSegmentation(3));

        //POSTPROCESS

        pipelineProcessor.addProcessor(new ClosePostProcessor(2));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));

        List<ImageModel> imageModels = pipelineProcessor.generateImageModels(imageModel);

//        displayManager.drawPipeline(imageModels);


        /*
        Classification
         */
        List<ImageModel> trainingImageModels = TrainingImageHelper.getStartingTrainingImages();
        List<ImageModel> testImageModels = TestImageHelper.getStartingTestingImages();

        ClassificationPipeline classificationPipeline = new ClassificationPipeline(pipelineProcessor, trainingImageModels);
        classificationPipeline.processTrainingSet();

        int k = 3;
        Double correctPercentage = classificationPipeline.classifyImagesAndReturnSuccess(testImageModels, k, true, FeatureExtractor.AREA, FeatureExtractor.PERIMETER);

        System.out.println("Result for classification: " + String.valueOf(correctPercentage) + "%");

    }
}
