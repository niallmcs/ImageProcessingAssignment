package featureextraction;

import imagewrappers.ImageModel;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class FeatureExtractor {

    private static final int MAX_COLOURS = 255;

    public static List<ImageModel> computePropertyModelsFromList(List<ImageModel> imageModels) {
        List<ImageModel> computedImageModels = new ArrayList<ImageModel>();
        for(ImageModel imageModel : imageModels) {
            imageModel = computePropertiesForImageModel(imageModel);
            computedImageModels.add(imageModel);
        }
        return computedImageModels;
    }
    
    public static ImageModel computePropertiesForImageModel(ImageModel imageModel){
        imageModel.getPropertyModel().setArea(computeArea(imageModel.getBufferedImage()));
        imageModel.getPropertyModel().setPerimeter(computePerimeter(imageModel.getBufferedImage()));
        imageModel.getPropertyModel().setCompactness(computeCompactness(imageModel.getBufferedImage()));
        return imageModel;
    }

    public static int computeArea(BufferedImage bufferedImage){
        return ImageOp.area(bufferedImage);
    }

    public static int computePerimeter(BufferedImage bufferedImage){

        //get original area
        int originalArea = computeArea(bufferedImage);

        //erode the image
        BufferedImage erodedImage = ImageOp.erode(bufferedImage, 2);

        //get area of new
        int newArea = computeArea(erodedImage);

        //subtract new area from original
        return (originalArea - newArea);
    }

    public static int computeCompactness(BufferedImage bufferedImage){
        return (int)Math.round(Math.pow(computePerimeter(bufferedImage),2)/computeArea(bufferedImage));
    }

}
