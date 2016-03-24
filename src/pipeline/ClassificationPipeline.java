package pipeline;

import classification.*;
import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;
import visionsystem.HistogramException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class ClassificationPipeline {

    private PipelineProcessor pipelineProcessor;

    private List<ImageModel> trainingImages;

    private boolean isTrainingImagesProcessed = false;

    public ClassificationPipeline(PipelineProcessor pipelineProcessor, List<ImageModel> trainingImages) {
        this.pipelineProcessor = pipelineProcessor;
        this.trainingImages = trainingImages;
    }

    public void processTrainingSet() throws HistogramException {
        for (ImageModel imageModel : trainingImages) {
            imageModel = pipelineProcessor.process(imageModel);
        }

        trainingImages = FeatureExtractor.computePropertyModelsFromList(trainingImages);

        isTrainingImagesProcessed = true;
    }

    public String classify(ImageModel testImage, int k, boolean verbose, String... features) throws HistogramException {

        if (features.length > 2) {
            throw new RuntimeException("Currently do not support more than 2 features");
        }

        if (isTrainingImagesProcessed != true) {
            throw new RuntimeException("Please process the training images using #processTrainingSet()");
        }

        //do the test image
        testImage = pipelineProcessor.process(testImage);
        testImage = FeatureExtractor.computePropertiesForImageModel(testImage);


        Classifier classifier;
        List<FeatureClassificationItem> featureClassificationItems = new LinkedList<>();
        int[] featureValues;

        //choose implementation based on number of features
        if (features.length == 1) {
            classifier = new NearestNeighbour(k);

            for (ImageModel imageModel : trainingImages) {
                //create a feature item using the first feature item
                featureClassificationItems.add(new SingleFeatureClassificationItem(imageModel.getPropertyModel().getClassification(), imageModel.getPropertyModel().getFeature(features[0])));
            }

            featureValues = new int[1];
            featureValues[0] = testImage.getPropertyModel().getFeature(features[0]);
        } else {

            classifier = new NearestNeighbour2D(k);

            for (ImageModel imageModel : trainingImages) {
                featureClassificationItems.add(new DoubleFeatureClassificationItem(
                        imageModel.getPropertyModel().getClassification(),
                        imageModel.getPropertyModel().getFeature(features[0]),
                        imageModel.getPropertyModel().getFeature(features[1])
                ));
            }

            featureValues = new int[2];
            featureValues[0] = testImage.getPropertyModel().getFeature(features[0]);
            featureValues[1] = testImage.getPropertyModel().getFeature(features[1]);
        }

        //if you want to print verbose info, turn this flag on
        if (verbose) {
            System.out.print("It was "+ features.length +" feature(s): ");
            for(String feature : features){
                System.out.print(feature + ", ");
            }
            System.out.println();

            //csv time
            System.out.print("Class,");
            for(String feature : features){
                System.out.print(feature + ",");
            }
            System.out.println();

            for (FeatureClassificationItem item : featureClassificationItems) {
                System.out.println(item.toCsv());
            }
            System.out.print("item["+testImage.getPropertyModel().getClassification()+"],");
            for(int feature : featureValues){
                System.out.print(feature + ",");
            }
            System.out.println("\n");
        }

        return classifier.classify(featureClassificationItems, testImage, featureValues);
    }

    public double classifyImagesAndReturnSuccess(List<ImageModel> testImages, int k, boolean verbose, String... features) throws HistogramException {

        PipelineResultModel pipelineResultModel = new PipelineResultModel();

        for (ImageModel testImageModel : testImages) {
            String classification = classify(testImageModel, k, false, features);

            //increment the amount of attempts
            pipelineResultModel.incrementTotalAttempts();

            if (verbose) {
                System.out.println("Classification: " + classification + " Expected: " + testImageModel.getPropertyModel().getClassification());
            }

            //if the result was correct, increment the correct attempts
            if (classification.equals(testImageModel.getPropertyModel().getClassification())) {
                pipelineResultModel.incrementCorrectAttempts();
            }
        }

        return pipelineResultModel.computeResult();
    }


}
