package util;

import imagewrappers.ImageModel;
import visionsystem.HistogramException;
import visionsystem.ImageOp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class TestImageHelper {

    static List<String> testingFileLocations = new ArrayList<>();

    static {
        try {
            Files.walk(Paths.get("images/test/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    testingFileLocations.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ImageModel getSampleImageFromClass(String classType) throws Exception {
        for (String path : testingFileLocations) {

            if(path.contains(classType)){
                ImageModel tempImageModel = new ImageModel(ImageOp.readInImage(path), classType);
                tempImageModel.getPropertyModel().setClassification(classType);
                return tempImageModel;
            }
        }

        throw new Exception("Could not find a file with including: '" + classType + "'");
    }

    public static  List <ImageModel> getStartingTestingImages() throws HistogramException {
        List <ImageModel>  images = new ArrayList<>();
        ImageModel tempImageModel;

        String[] classes = ImageHelper.STARTING_CLASSES;

        for (String path : testingFileLocations) {
            for(int i = 0; i<classes.length; i++){
                if(path.contains(classes[i])){
                    tempImageModel = new ImageModel(ImageOp.readInImage(path), classes[i]);
                    tempImageModel.getPropertyModel().setClassification(classes[i]);
                    images.add(tempImageModel);
                }
            }
        }
        return images;
    }

    public static  List<ImageModel> getFinalTestingImages() throws HistogramException {
        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;

        String[] classes = ImageHelper.FINAL_CLASSES;

        for (String path : testingFileLocations) {
            for(int i = 0; i<classes.length; i++){
                if(path.contains(classes[i])){
                    tempImageModel = new ImageModel(ImageOp.readInImage(path), "");
                    tempImageModel.getPropertyModel().setClassification(classes[i]);
                    images.add(tempImageModel);
                }
            }
        }
        return images;
    }

    public static List<ImageModel> getAllStartingForClassification(String classification) throws HistogramException {
        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;
        String foundClassification;

        for (String path : testingFileLocations) {
            foundClassification = ImageHelper.computeClassFromPath(path);

            if(classification.equals(foundClassification)){
                tempImageModel = new ImageModel(ImageOp.readInImage(path), classification);
                tempImageModel.getPropertyModel().setClassification(classification);
                images.add(tempImageModel);
            }
        }
        return images;
    }

}
