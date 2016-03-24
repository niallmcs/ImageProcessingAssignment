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
import ui.JVisionScrollPanel;
import util.DisplayManager;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;

import java.util.List;

/**
 * Created by Niall McShane on 24/03/2016.
 */
public class ExampleRunner {

    public static void main(String[] args) throws Exception {

        //Create a pipeline processor

        /*
            A pipeline processor encapsulates a collection of modifications you will do to an image.

            Images are pushed along a pipeline. Each image effect is modified in the

            Below I will create a Pipeline Processor, and add a few affects.
         */
        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new BrightnessModifier(75));
        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new MedianNoiseModifier(2));
        pipelineProcessor.addProcessor(new EdgeSegmentation());
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.8));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));

        // I will now create our custom display manager and a scrollable JPanel
        JVisionScrollPanel jVision = new JVisionScrollPanel(1800, 900);
        jVision.setVisible(false);
        DisplayManager displayManager = new DisplayManager(jVision);


        /*
            Lets get some data from the test and training set
         */

        //get a sample image
        ImageModel testImageModel = TestImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);

        //lets process it, and get back an image for each stage of the processor - nice for debugging!
        List<ImageModel> testImageProcessedStages = pipelineProcessor.generateImageModels(testImageModel);

        //lets draw out the results
        displayManager.drawPipeline(testImageProcessedStages);

        //now, we have demoed how to show the effect of our modifications to the image

        /*
                Classification demo
         */

        List<ImageModel> trainingImageModels;
        List<ImageModel> testImageModels;

        //define if you want use the final set or not
        boolean isFinal = true;

        if(isFinal){
            trainingImageModels = TrainingImageHelper.getFinalTrainingImages();
            testImageModels = TestImageHelper.getFinalTestingImages();
        }else{
            trainingImageModels = TrainingImageHelper.getStartingTrainingImages();
            testImageModels = TestImageHelper.getStartingTestingImages();
        }


        //create a classificaiton pipeline, pass the effects pipeline, and the training Image Models
        ClassificationPipeline classificationPipeline = new ClassificationPipeline(pipelineProcessor, trainingImageModels);

        //process the training set
        classificationPipeline.processTrainingSet();

        //set a value of 3
        int valueOfK = 3;

        /*
            Lets classify a test set

            the system will automatically choose an classification implementation based on the input features.

            E.g. if I add one feature: "FeatureExtractor.COMPACTNESS", it will choose the 1d solution

                 if I add 2 parameters: "FeatureExtractor.COMPACTNESS, FeatureExtractor.PERIMETER", it will choose 2D

            This function also has a parameter to show verbose logging, e.g. the results of each classification.
         */

        Double correctPercentage = classificationPipeline.classifyImagesAndReturnSuccess(testImageModels, valueOfK, true, FeatureExtractor.COMPACTNESS, FeatureExtractor.PERIMETER);
        System.out.println("Recognition Rate: " + String.valueOf(correctPercentage) + "%");
    }
}
