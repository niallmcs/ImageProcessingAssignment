import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.ClassificationPipeline;
import pipeline.PipelineProcessor;
import processors.postprocessing.ClosePostProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.HistogramEqualisationContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import ui.JVisionScrollPanel;
import util.DisplayManager;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;

import java.util.List;

/**
 * Created by Niall McShane on 24/03/2016.
 */
public class NiallController {

    public static void main(String[] args) throws Exception {

PipelineProcessor pipelineProcessor = new PipelineProcessor();
//        pipelineProcessor.addProcessor(new BrightnessModifier(75));
//        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
//        pipelineProcessor.addProcessor(new MedianNoiseModifier(2));
//        pipelineProcessor.addProcessor(new EdgeSegmentation());
//        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.8));
//        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
//        pipelineProcessor.addProcessor(new ClosePostProcessor(2));


        List<ImageModel> imageModelList = TrainingImageHelper.getFinalTrainingImages();
        List<ImageModel> singleClassImageModelList = TrainingImageHelper.getAllStartingForClassification(ImageHelper.TYPE_COW);

        JVisionScrollPanel jVision = new JVisionScrollPanel(1800, 900);
        jVision.setVisible(false);
        DisplayManager displayManager = new DisplayManager(jVision);

        for (ImageModel imageModel: imageModelList) {
            imageModel = pipelineProcessor.process(imageModel);
        }
        displayManager.drawPipeline(imageModelList);

        displayManager.newLine();

        //add brightness modifier
//        pipelineProcessor.addProcessor(new BrightnessModifier(20)); //83.33333333333334% vs 83.33333333333334%
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
//        pipelineProcessor.addProcessor(new PowerLawContrastModifier(2));
//        pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(1.2, 1.5));
        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.8));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));

        for (ImageModel imageModel: imageModelList) {
            imageModel = pipelineProcessor.process(imageModel);
        }
        displayManager.drawPipeline(imageModelList);

//        displayManager.newLine();
//
//        for (ImageModel imageModel: singleClassImageModelList) {
//            imageModel = pipelineProcessor.process(imageModel);
//        }
//        displayManager.drawPipeline(singleClassImageModelList);
        List<ImageModel> imageModels;
        List<ImageModel> testImages;

        boolean isFinal = true;

        if(isFinal){
            imageModels = TrainingImageHelper.getFinalTrainingImages();
            testImages = TestImageHelper.getFinalTestingImages();
        }else{
            imageModels = TrainingImageHelper.getStartingTrainingImages();
            testImages = TestImageHelper.getStartingTestingImages();
        }

        ClassificationPipeline classificationPipeline = new ClassificationPipeline(pipelineProcessor, imageModels);
        classificationPipeline.processTrainingSet();

        int valueOfK = 3;

//        ImageModel testImageModel = TestImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);
        List<ImageModel> singleClassTestImages = TestImageHelper.getAllStartingForClassification(ImageHelper.TYPE_COW);

//        String result = classificationPipeline.classify(singleClassTestImages.get(0), valueOfK, true, FeatureExtractor.COMPACTNESS, FeatureExtractor.PERIMETER);

        Double correctPercentage = classificationPipeline.classifyImagesAndReturnSuccess(testImages, valueOfK, true, FeatureExtractor.COMPACTNESS, FeatureExtractor.PERIMETER);
        System.out.println("Recognition Rate: " + String.valueOf(correctPercentage) + "%");

    }

}
