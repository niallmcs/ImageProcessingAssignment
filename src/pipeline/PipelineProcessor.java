package pipeline;

import imagewrappers.ImageModel;
import processors.Processor;
import visionsystem.HistogramException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class PipelineProcessor {

    private final List<Processor> processors;

    public PipelineProcessor() {
        processors = new LinkedList<>();
    }

    public PipelineProcessor(List<Processor> processors) {
        this.processors = processors;
    }

    public ImageModel process(ImageModel imageModel) {
        for(Processor processor : processors) {
            imageModel.setBufferedImage(processor.process(imageModel.getBufferedImage()));
        }

        //Returns final processed image
        return imageModel;
    }

    public List<ImageModel> generateImageModels(ImageModel imageModel) throws HistogramException {
        List<ImageModel> imageModels = new LinkedList<>();

        ImageModel tempImageModel = null;

        for(Processor processor : processors) {
            if(tempImageModel == null) {
                tempImageModel = new ImageModel(processor.process(imageModel.getBufferedImage()), processor.getProcessName());
            }else{
                tempImageModel = new ImageModel(processor.process(tempImageModel.getBufferedImage()), processor.getProcessName());
            }
            imageModels.add(tempImageModel);
        }

        return imageModels;
    }

    public void addProcessor(Processor processor){
        processors.add(processor);
    }
}
