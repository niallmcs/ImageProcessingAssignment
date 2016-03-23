import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.HistogramEqualisationContrastModifier;
import processors.preprocessing.MedianNoiseModifier;
import processors.preprocessing.PowerLawContrastModifier;
import processors.segmentation.AutomaticThresholdSegmentation;
import util.DisplayHelper;
import util.ImageHelper;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class DemoPipelineController {

    public static void main(String[] args) throws Exception {

        ImageModel imageModel = ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_DOG);

        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new BrightnessModifier(10));
        pipelineProcessor.addProcessor(new BrightnessModifier(-50));
        pipelineProcessor.addProcessor(new PowerLawContrastModifier(0.25));
        pipelineProcessor.addProcessor(new HistogramEqualisationContrastModifier());
        pipelineProcessor.addProcessor(new MedianNoiseModifier(5));
        pipelineProcessor.addProcessor(new AutomaticThresholdSegmentation(0.5));

        List<ImageModel> imageModels = pipelineProcessor.generateImageModels(imageModel);


        JVision jVision = new JVision();
        jVision.setSize(1800, 1000);

        DisplayHelper displayHelper = new DisplayHelper(jVision);
        displayHelper.drawPipeline(imageModels);
    }

}
