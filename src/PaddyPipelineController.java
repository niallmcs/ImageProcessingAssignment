import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.ClassificationPipeline;
import pipeline.PipelineProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.MedianNoiseModifier;
import ui.JVisionScrollPanel;
import util.DisplayManager;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;

import java.util.List;

/**
 * Created by patrick mcnicholl on 23/03/2016.
 */
public class PaddyPipelineController {
    public static void main(String[] args) throws Exception {
        PipelineProcessor pipelineProcessor = new PipelineProcessor();
//        pipelineProcessor.addProcessor(new BrightnessModifier(75));
//        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
//        pipelineProcessor.addProcessor(new MedianNoiseModifier(2));
//        pipelineProcessor.addProcessor(new EdgeSegmentation());
//        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.8));
//        pipelineProcessor.addProcessor(new ManualThresholdSegmentation(0.5));
//        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
//        pipelineProcessor.addProcessor(new ClosePostProcessor(2));


        List<ImageModel> imageModelList = TrainingImageHelper.getFinalTrainingImages();
        List<ImageModel> singleClassImageModelList = TrainingImageHelper.getAllStartingForClassification(ImageHelper.TYPE_PEAR);

        ui.JVisionScrollPanel jVision = new JVisionScrollPanel(1300, 900);
        DisplayManager displayManager = new DisplayManager(jVision);

        for (ImageModel imageModel: imageModelList) {
            imageModel = pipelineProcessor.process(imageModel);
        }
        displayManager.drawPipeline(imageModelList);

        displayManager.newLine();

        //add brightness modifier
        pipelineProcessor.addProcessor(new BrightnessModifier(20));
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
//        pipelineProcessor.addProcessor(new PowerLawContrastModifier(2));
//        pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(1.2, 1.5));
//        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
//          pipelineProcessor.addProcessor(new );
 //       pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(1.1));
//        pipelineProcessor.addProcessor(new ManualThresholdSegmentation(135));
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
        //pipelineProcessor.addProcessor(new ClosePostProcessor(3));

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

        boolean isFinal = false;

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

        String result = classificationPipeline.classify(singleClassTestImages.get(0), valueOfK, true, FeatureExtractor.PERIMETER, FeatureExtractor.COMPACTNESS);

        Double correctPercentage = classificationPipeline.classifyImagesAndReturnSuccess(testImages, valueOfK, true, FeatureExtractor.COMPACTNESS);
        System.out.println("Result for classification: " + String.valueOf(correctPercentage) + "%");

        System.out.println("Car : " + FeatureExtractor.computePropertiesForImageModel(imageModelList.get(5)).getPropertyModel().getFeature(FeatureExtractor.COMPACTNESS));
        System.out.println("Cow : " + FeatureExtractor.computePropertiesForImageModel(imageModelList.get(10)).getPropertyModel().getFeature(FeatureExtractor.COMPACTNESS));
        System.out.println("Pear : " + FeatureExtractor.computePropertiesForImageModel(imageModelList.get(36)).getPropertyModel().getFeature(FeatureExtractor.COMPACTNESS));
        System.out.println("Tomato : " + FeatureExtractor.computePropertiesForImageModel(imageModelList.get(40)).getPropertyModel().getFeature(FeatureExtractor.COMPACTNESS));


//if you want to get the area
        // go imageModel.getPropertyModel().getFeature(FeatureExtractor.AREA)
    }
}
