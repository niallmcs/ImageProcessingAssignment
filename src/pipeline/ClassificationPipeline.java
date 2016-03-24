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

    public ClassificationPipeline(PipelineProcessor pipelineProcessor, List<ImageModel> trainingImages) {
        this.pipelineProcessor = pipelineProcessor;
        this.trainingImages = trainingImages;
    }

//    private void processTrainingSet() throws HistogramException {
//        for (ImageModel imageModel : trainingImages) {
//            imageModel = pipelineProcessor.process(imageModel);
//        }
//
//        trainingImages = FeatureExtractor.computePropertyModelsFromList(trainingImages);
//    }

    public String classify(ImageModel testImage, String... features) throws HistogramException {

        if(features.length > 2){
            throw new RuntimeException("Currently do not support more than 2 features");
        }

        //process all the stuff
        for (ImageModel imageModel : trainingImages) {
            imageModel = pipelineProcessor.process(imageModel);
        }
        trainingImages = FeatureExtractor.computePropertyModelsFromList(trainingImages);

        //do the test image
        testImage = pipelineProcessor.process(testImage);
        testImage = FeatureExtractor.computePropertiesForImageModel(testImage);


        Classifier classifier;
        List<FeatureClassificationItem> featureClassificationItems = new LinkedList<>();

        //choose implementation based on number of features
        if(features.length == 1){
            System.out.println("It was 1 feature: " + features[0]);

            classifier = new NearestNeighbour(3);

            for(ImageModel imageModel : trainingImages){
                //create a feature item using the first feature item
                featureClassificationItems.add(new SingleFeatureClassificationItem(imageModel.getPropertyModel().getClassification(), imageModel.getPropertyModel().getFeature(features[0])));
            }
        }else {
            System.out.println("It was 2 feature: " + features[0] + ", " +  features[1]);

            classifier = new NearestNeighbour2D(3);

            for(ImageModel imageModel : trainingImages){
                featureClassificationItems.add(new DoubleFeatureClassificationItem(
                            imageModel.getPropertyModel().getClassification(),
                            imageModel.getPropertyModel().getFeature(features[0]),
                            imageModel.getPropertyModel().getFeature(features[1])
                        ));
            }

        }

        for (FeatureClassificationItem item : featureClassificationItems) {
            System.out.println(item.toString());

        }

        return classifier.classify(featureClassificationItems, testImage, testImage.getPropertyModel().getFeature(features[0]), testImage.getPropertyModel().getFeature(features[1]));
    }


}
