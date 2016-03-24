package util;

import imagewrappers.ImageModel;
import visionsystem.Histogram;
import visionsystem.HistogramException;
import visionsystem.JVision;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cathan O'Donnell on 22/03/2016.
 */
public class DisplayManager {

    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 300;
    private static final int START_X = 1;
    private static final int START_Y = 1;
    private final JVision jvision;
    private final int maxWidth;
    private final int maxHeight;

    private int currentXPosition = START_X;
    private int currentYPosition = START_Y;

    private List<ImageModel> imageModels = new ArrayList<>();
    
    public DisplayManager(JVision jvision){
        this.jvision = jvision;
        this.maxWidth = jvision.getWidth();
        this.maxHeight = jvision.getHeight();
    }

    public void drawPipeline() throws HistogramException {
        drawPipeline(this.imageModels);
    }

    public void drawPipeline(List<ImageModel> imageDisplayPipeline) throws HistogramException {

        Histogram histogram;

        for (ImageModel image : imageDisplayPipeline) {

            //display the image
            jvision.imdisp(image.getBufferedImage(), image.getTitle(), currentXPosition, currentYPosition);

            //generate the histogram dynamically...
            jvision.imdisp(image.toGraphPlot(), image.getTitle() + " Histogram", currentXPosition, currentYPosition + IMAGE_HEIGHT);

            //increment the counts
//            currentXPosition = currentXPosition > maxWidth ? START_X : currentXPosition + IMAGE_WIDTH ;
//            currentYPosition = currentYPosition > maxHeight ?  currentYPosition + IMAGE_HEIGHT : START_Y;

            if(currentXPosition >= maxWidth){
                currentXPosition = START_X;
                currentYPosition = currentYPosition + (IMAGE_HEIGHT * 2);
            }else{
                currentXPosition += IMAGE_WIDTH;
            }
        }
    }

    public void newLine(){
        currentXPosition = START_X;
        currentYPosition = currentYPosition + (IMAGE_HEIGHT * 2);
    }

    public void addImage(BufferedImage bufferedImage, String title) throws HistogramException {
        ImageModel imageModel = new ImageModel(bufferedImage, title);

        imageModels.add(imageModel);
    }

    public void addImage(ImageModel imageModel){
        imageModels.add(imageModel);
    }


}
