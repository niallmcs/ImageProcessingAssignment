package util;

import visionsystem.ImageOp;

import java.awt.image.BufferedImage;
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

    static List<String> fileLocations = new ArrayList<>();

    static {
        try {
            Files.walk(Paths.get("images/training/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    fileLocations.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getSampleImage(){
        return ImageOp.readInImage("images/training/tomato/tomato1-066-153.png");
    }

    public static BufferedImage getRandomSampleImage(){

        Random generator = new Random();
        int i = generator.nextInt(fileLocations.size());
        return ImageOp.readInImage(fileLocations.get(i));
    }

}
