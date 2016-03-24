import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import processors.postprocessing.ClosePostProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.LinearStretchingContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import util.DisplayManager;
import util.ImageHelper;
import util.TrainingImageHelper;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by patrick mcnicholl on 23/03/2016.
 */
public class PaddyPipelineController {
    public static void main(String[] args) throws Exception {

//        ImageModel imageModel =  ImageHelper.getRandomTestImage();
//
//        //ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);
//
//        DisplayManager displayManager = new DisplayManager(new JVision());
//
//        PipelineProcessor pipelineProcessor = new PipelineProcessor();
//        pipelineProcessor.addProcessor(new BrightnessModifier(50));
//
//        imageModel = pipelineProcessor.process(imageModel);
//
//        displayManager.addImage(imageModel);
//        displayManager.drawPipeline();

        ImageModel imageModel = TrainingImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);


        //set up a JVision for mac
        JVision jVision = new JVision();
        jVision.setSize(1300, 700);

        DisplayManager displayManager = new DisplayManager(jVision);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();

        //PREPROCESS
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
        pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(1,1));
        pipelineProcessor.addProcessor(new BrightnessModifier(5));

        //SEGMENT
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));

        //POSTPROCESS
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));

        List<ImageModel> imageModels = pipelineProcessor.generateImageModels(imageModel);

        displayManager.drawPipeline(imageModels);
    }
}
