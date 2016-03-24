import imagewrappers.ImageModel;
import util.DisplayManager;
import util.ImageHelper;
import visionsystem.HistogramException;
import visionsystem.JVision;

import java.util.List;

/**
 * Created by Ryan McCleave1 on 22/03/2016.
 */
public class MultiImageController {

    public static void main(String[]args) throws HistogramException {

        List<ImageModel> imageModelList = ImageHelper.getStartingTrainingImages();

        JVision jvis = new JVision();
        DisplayManager dh = new DisplayManager(jvis);

        dh.drawPipeline(imageModelList);



        //imageModelList = FeatureHelper.getImageListProperties(imageModelList);

        System.out.print("");
    }
}
