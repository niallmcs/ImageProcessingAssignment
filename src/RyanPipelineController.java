import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import processors.postprocessing.ClosePostProcessor;
import processors.preprocessing.*;
import processors.segmentation.AutomaticThresholdSegmentation;
import util.DisplayManager;
import util.ImageHelper;
import util.TrainingImageHelper;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by RyanMcCleave on 23/03/2016.
 */
public class RyanPipelineController {

    public static void main(String [] args) throws Exception {

        ImageModel imageModel = TrainingImageHelper.getSampleImageFromClass(ImageHelper.TYPE_CAR);


        //set up a JVision for mac
        JVision jVision = new JVision();
        jVision.setSize(1800, 1000);

        DisplayManager displayManager = new DisplayManager(jVision);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();

        //PREPROCESS
        //Noise Reduction
        //pipelineProcessor.addProcessor(new MedianNoiseModifier(2));
        //pipelineProcessor.addProcessor(new SmallConvolutionModifier());
        //pipelineProcessor.addProcessor(new LargeConvolutionModifier());


        //Contrast Enhancement
        //pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(2,1.5));
        pipelineProcessor.addProcessor(new PowerLawContrastModifier(1.5));
        //pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());

        //Brightness Enhancement
        pipelineProcessor.addProcessor(new BrightnessModifier(0));

        //SEGMENT
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));

        //POSTPROCESS
        pipelineProcessor.addProcessor(new ClosePostProcessor(2));

        List<ImageModel> imageModels = pipelineProcessor.generateImageModels(imageModel);

        displayManager.drawPipeline(imageModels);

    }
}
