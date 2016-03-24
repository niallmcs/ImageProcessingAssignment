package util;

import imagewrappers.ImageModel;
import visionsystem.HistogramException;
import visionsystem.ImageOp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class TrainingImageHelper {

    static List<String> trainingFileLocations = new ArrayList<>();

    static {
        try {
            Files.walk(Paths.get("images/training/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    trainingFileLocations.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageModel getSampleImageFromClass(String classType) throws Exception {
        for (String path : trainingFileLocations) {

            if(path.contains(classType)){
                ImageModel tempImageModel = new ImageModel(ImageOp.readInImage(path), classType);
                return tempImageModel;
            }
        }

        throw new Exception("Could not find a file with including: '" + classType + "'");
    }

    public static  List <ImageModel> getStartingTrainingImages() throws HistogramException {
        List <ImageModel>  images = new ArrayList<>();
        ImageModel tempImageModel;

        String[] classes = ImageHelper.STARTING_CLASSES;

        for (String path : trainingFileLocations) {
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

    public static  List<ImageModel> getFinalTrainingImages() throws HistogramException {
        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;

        String[] classes = ImageHelper.FINAL_CLASSES;

        for (String path : trainingFileLocations) {
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

    public static List<ImageModel> getSampleForEachClassFromStart() throws HistogramException {
        Map<String, Boolean> values = new HashMap<>();
        for (String classification : ImageHelper.STARTING_CLASSES) {
            values.put(classification, false);
        }

        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;
        String classification;

        for (String path : trainingFileLocations) {
            classification = ImageHelper.computeClassFromPath(path);

            if(values.containsKey(classification)){
                if(!values.get(classification)){
                    tempImageModel = new ImageModel(ImageOp.readInImage(path), classification);
                    tempImageModel.getPropertyModel().setClassification(classification);
                    images.add(tempImageModel);
                    values.put(classification, true);
                }
            }
        }
        return images;
    }

    public static List<ImageModel> getAllStartingForClassification(String classification) throws HistogramException {
        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;
        String foundClassification;

        for (String path : trainingFileLocations) {
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
