import classification.*;
import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import pipeline.PipelineResultModel;
import processors.postprocessing.ClosePostProcessor;
import processors.postprocessing.OpenPostProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.HistogramEqualisationContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import processors.segmentation.EdgeSegmentation;
import util.DisplayHelper;
import util.ImageHelper;
import util.TestImageHelper;
import util.TrainingImageHelper;
import visionsystem.HistogramException;
import visionsystem.JVision;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class DemoClassificationController {

    private static DisplayHelper displayHelper;

    public static void main(String[] args) throws Exception {

//        BufferedImage dogImage = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_DOG);
//        BufferedImage cowImage = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);
//        BufferedImage pearImage = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_PEAR);
//        BufferedImage tomatoImage = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_TOMATO);

        JVision jVision = new JVision();
        jVision.setSize(1800, 1000);

        displayHelper = new DisplayHelper(jVision);

        ImageModel testImageModel = TestImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);

        List<ImageModel> startingTrainingImages = TrainingImageHelper.getFinalTrainingImages();
        List<ImageModel> startingTestingImages = TestImageHelper.getFinalTestingImages();

        //Pipeline processing
        /*
         *  Preprocessing modifiers
         */
        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new BrightnessModifier(75));
//        pipelineProcessor.addProcessor(new BrightnessModifier(-50));
//        pipelineProcessor.addProcessor(new PowerLawContrastModifier(0.25));
        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
        /*
         *  Segmentation modifiers
         */
        pipelineProcessor.addProcessor(new EdgeSegmentation());
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));
        /*
         *  Postprocessing modifiers
         */
        pipelineProcessor.addProcessor(new OpenPostProcessor(2));
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));


        //process the training images
        for (ImageModel imageModel : startingTrainingImages) {
            imageModel = pipelineProcessor.process(imageModel);
        }
        //process the testing images
        for (ImageModel imageModel : startingTestingImages) {
            imageModel = pipelineProcessor.process(imageModel);
        }

        //process the test image
        testImageModel = pipelineProcessor.process(testImageModel);
        displayHelper.addImage(testImageModel);

        displayHelper.drawPipeline();

        //extract our features for training images
        FeatureExtractor featureExtractor = new FeatureExtractor();

        //populate the training images with properties
        startingTrainingImages = featureExtractor.computePropertyModelsFromList(startingTrainingImages);

        //populate for test images
        startingTestingImages = featureExtractor.computePropertyModelsFromList(startingTestingImages);

//        List<FeatureClassificationItem> trainingList = new LinkedList<>();
//        for (ImageModel imageModel : startingTrainingImages) {
//            trainingList.add(new DoubleFeatureClassificationItem(imageModel.getTitle(), featureExtractor.computeArea(imageModel.getBufferedImage()), featureExtractor.computePerimeter(imageModel.getBufferedImage())));
//        }
//

        //compare the string returned from the classifier with the test image class

        Double singleResult = classifiyByNearest1D(startingTrainingImages, startingTestingImages);
        System.out.println("Result for 1d: " + String.valueOf(singleResult) + "%");

        Double doubleResult = classifiyByNearest2D(startingTrainingImages, startingTestingImages);
        System.out.println("Result for 2d: " + String.valueOf(doubleResult) + "%");

    }

    private static double classifiyByNearest1D(List<ImageModel> trainingImages, List<ImageModel> testImages) throws HistogramException {
        List<FeatureClassificationItem> trainingList = new LinkedList<>();
        for (ImageModel imageModel : trainingImages) {
            trainingList.add(new SingleFeatureClassificationItem(imageModel.getPropertyModel().getClassification(), imageModel.getPropertyModel().getArea()));
        }

        Classifier nearestNeighbour = new NearestNeighbour(3);

        String classification;
        PipelineResultModel pipelineResultModel = new PipelineResultModel();

        for (ImageModel testImage : testImages) {

            pipelineResultModel.incrementTotalAttempts();
            classification = nearestNeighbour.classify(trainingList, testImage, testImage.getPropertyModel().getArea());

            if(classification.equals(testImage.getPropertyModel().getClassification())){
                System.out.println("Correct - " + classification + " " + testImage.getPropertyModel().getClassification());
                pipelineResultModel.incrementCorrectAttempts();
            }else{
                System.out.println("FAiled - " + classification + " " + testImage.getPropertyModel().getClassification());
            }
        }


        //return the correct percentage
        return pipelineResultModel.computeResult();
    }

    private static double classifiyByNearest2D(List<ImageModel> trainingImages, List<ImageModel> testImages){
        List<FeatureClassificationItem> trainingList = new LinkedList<>();
        for (ImageModel imageModel : trainingImages) {
            trainingList.add(new DoubleFeatureClassificationItem(imageModel.getPropertyModel().getClassification(), imageModel.getPropertyModel().getArea(), imageModel.getPropertyModel().getPerimeter()));
        }

        String classification;
        PipelineResultModel pipelineResultModel = new PipelineResultModel();
        Classifier nearestNeighbour = new NearestNeighbour2D(3);
        for (ImageModel testImage : testImages) {
            pipelineResultModel.incrementTotalAttempts();
            classification = nearestNeighbour.classify(trainingList, testImage, testImage.getPropertyModel().getArea(), testImage.getPropertyModel().getPerimeter());

            if(classification.equals(testImage.getPropertyModel().getClassification())){
                pipelineResultModel.incrementCorrectAttempts();
            }
        }

        //return the result percentage
        return pipelineResultModel.computeResult();
    }
}
