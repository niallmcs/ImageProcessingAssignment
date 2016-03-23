package util;

import featureextraction.FeatureExtractor;
import imagewrappers.ImageModel;

import java.util.List;

/**
 * Created by Ryan McCleave1 on 22/03/2016.
 */
public class FeatureHelper {

    public static ImageModel getImageProperties(ImageModel imageModel)
    {
        FeatureExtractor featureExtractor = new FeatureExtractor();
        imageModel.getPropertyModel().setArea(featureExtractor.computeArea(imageModel.getBufferedImage()));
        imageModel.getPropertyModel().setPerimeter(featureExtractor.computePerimeter(imageModel.getBufferedImage()));
        imageModel.getPropertyModel().setCompactness(featureExtractor.computeCompactness(imageModel.getBufferedImage()));

        return imageModel;
    }

    public static List<ImageModel> getImageListProperties(List<ImageModel> imageList)
    {
        for(ImageModel imageModel : imageList)
        {
            imageModel = getImageProperties(imageModel);
        }
        return imageList;
    }
}
