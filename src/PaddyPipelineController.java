import imagewrappers.ImageModel;
import pipeline.PipelineProcessor;
import processors.preprocessing.BrightnessModifier;
import processors.preprocessing.LinearStretchingContrastModifier;
import util.DisplayHelper;
import util.ImageHelper;
import visionsystem.JVision;

/**
 * Created by patrick mcnicholl on 23/03/2016.
 */
public class PaddyPipelineController {
    public static void main(String[] args) throws Exception {

        ImageModel imageModel =  ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_TOMATO);

        //ImageHelper.getSampleImageFromClass(ImageHelper.TYPE_COW);

        DisplayHelper displayHelper = new DisplayHelper(new JVision());

        PipelineProcessor pipelineProcessor = new PipelineProcessor();
        pipelineProcessor.addProcessor(new BrightnessModifier(50));
        pipelineProcessor.addProcessor(new LinearStretchingContrastModifier(0.4,0.5));

        imageModel = pipelineProcessor.process(imageModel);

        displayHelper.addImage(imageModel);
        displayHelper.drawPipeline();
    }
}
