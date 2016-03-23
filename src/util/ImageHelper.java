package util;

import imagewrappers.ImageModel;
import visionsystem.HistogramException;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public class ImageHelper {

    public final static int NUM_COLOURS = 256;
    public final static String TYPE_TOMATO = "tomato";
    public final static String TYPE_PEAR = "pear";
    public final static String TYPE_COW = "cow";
    public final static String TYPE_DOG = "dog";

    public final static String[] STARTING_CLASSES = { "tomato","pear","cow","dog" };
    public final static String[] FINAL_CLASSES = {"horse", "apple", "car", "cup", "tomato","pear","cow","dog"};

    static List<String> trainingFileLocations = new ArrayList<>();
    static List<String> testingFileLocations = new ArrayList<>();

    static {
        try {
            Files.walk(Paths.get("images/training/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    trainingFileLocations.add(filePath.toString());
                }
            });
            Files.walk(Paths.get("images/test/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    testingFileLocations.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageModel getSampleImage() throws Exception {
        return new ImageModel(ImageOp.readInImage("images/training/tomato/tomato1-066-153.png"), "Tomato");
    }

    public static ImageModel getSampleImageFromClass(String classType) throws Exception {
        for (String path : testingFileLocations) {

            if(path.contains(classType)){
                ImageModel tempImageModel = new ImageModel(ImageOp.readInImage(path), classType);
                return tempImageModel;
            }
        }

        throw new Exception("Could not find a file with including: '" + classType + "'");
    }

    public static ImageModel getRandomTestImage() throws Exception {
        Random generator = new Random();
        int i = generator.nextInt(testingFileLocations.size());
        return new ImageModel(ImageOp.readInImage(testingFileLocations.get(i)), "Random");
    }

    public static  List <ImageModel> getStartingTrainingImages() throws HistogramException {
        List <ImageModel>  images = new ArrayList<>();
        ImageModel tempImageModel;

        String[] classes = {"tomato","pear","cow","dog"};

        for (String path : trainingFileLocations) {
            for(int i = 0; i<classes.length; i++){
                if(path.contains(classes[i])){

                    tempImageModel = new ImageModel(ImageOp.readInImage(path), classes[i]);
                    images.add(tempImageModel);
                }
            }
       }
        return images;
    }

    public static  List<ImageModel> getFinalTrainingImages() throws HistogramException {
        List<ImageModel> images = new ArrayList<>();
        ImageModel tempImageModel;

        for (String path : trainingFileLocations) {
            tempImageModel = new ImageModel(ImageOp.readInImage(path), "");
            images.add(tempImageModel);
        }
        return images;
    }


    public static int getMeanFromImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Raster raster = bufferedImage.getRaster();

        int total = 0;
        for(int i = 0; i < width; i++) {
            for( int j = 0; j < height; j++) {
                total += raster.getSample(j,i,0);
            }
        }

        return total / (width * height);
    }

    public static int getStandardDeviationOfImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int mean = getMeanFromImage(bufferedImage);
        int total = 0;

        Raster raster = bufferedImage.getRaster();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                total += Math.pow(raster.getSample(j,i,0) - mean,2);
            }
        }

        int standardDeviation = (int)Math.round(Math.sqrt(total / ((width * height) - 1)));
        return standardDeviation;
    }

    public static String computeClassFromPath(String path){
        for (String classification : FINAL_CLASSES) {
            if(path.contains(classification)){
                return classification;
            }
        }
        return "Couldn't find it :/";
    }
}
