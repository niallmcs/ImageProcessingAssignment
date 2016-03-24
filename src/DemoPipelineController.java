import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import util.ImageHelper;
import util.TrainingImageHelper;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class DemoPipelineController {

    public static void main(String[] args) throws Exception {

        ImageModel imageModel = TrainingImageHelper.getSampleImageFromClass(ImageHelper.TYPE_DOG);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();
//        pipelineProcessor.addProcessor(new BrightnessModifier(10));
//        pipelineProcessor.addProcessor(new BrightnessModifier(-50));
//        pipelineProcessor.addProcessor(new PowerLawContrastModifier(0.25));
//        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
       // pipelineProcessor.addProcessor(new AutomaticLinearStretchingContrastModifier());
//        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
//        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));

        List<ImageModel> imageModels = pipelineProcessor.generateImageModels(imageModel);


        JVision JVisionScrollPanel = new JVision();
        JVisionScrollPanel.setSize(1800, 1000);

//        DisplayManager displayManager = new DisplayManager(JVisionScrollPanel);
//        displayManager.drawPipeline(imageModels);
    }

}
