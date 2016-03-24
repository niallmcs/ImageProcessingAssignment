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

    public static final String AREA = "area";
    public static final String PERIMETER = "perimeter";
    public static final String COMPACTNESS = "compactness";

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

        int area = computeArea(imageModel.getBufferedImage());
        int perimeter = computePerimeter(imageModel.getBufferedImage());
        int compactness = computeCompactness(imageModel.getBufferedImage());

        imageModel.getPropertyModel().setArea(area);
        imageModel.getPropertyModel().setPerimeter(perimeter);
        imageModel.getPropertyModel().setCompactness(compactness);

        imageModel.getPropertyModel().setFeature(AREA, area);
        imageModel.getPropertyModel().setFeature(PERIMETER, perimeter);
        imageModel.getPropertyModel().setFeature(COMPACTNESS, compactness);
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
