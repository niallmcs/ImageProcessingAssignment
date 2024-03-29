package util;

import imagewrappers.ImageModel;
import ui.JVisionScrollPanel;
import visionsystem.Histogram;
import visionsystem.HistogramException;

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
    private final JVisionScrollPanel jvision;
    private final int maxWidth;
    private final int maxHeight;

    private int currentXPosition = START_X;
    private int currentYPosition = START_Y;

    private List<ImageModel> imageModels = new ArrayList<>();
    
    public DisplayManager(JVisionScrollPanel jvision){
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
            jvision.imageDisplay(image.getBufferedImage(), image.toGraphPlot(), image.getTitle(), currentXPosition, currentYPosition, 300);

            //generate the histogram dynamically...

//            if(image.toGraphPlot() != null){
////                jvision.imdisp(image.toGraphPlot(), image.getTitle() + " Histogram", currentXPosition, currentYPosition + IMAGE_HEIGHT);
//                jvision.imageDisplay(image.getBufferedImage(), image.toGraphPlot(), image.getTitle(), currentXPosition, currentYPosition, 300);
//            }


            //increment the counts
//            currentXPosition = currentXPosition > maxWidth ? START_X : currentXPosition + IMAGE_WIDTH ;
//            currentYPosition = currentYPosition > maxHeight ?  currentYPosition + IMAGE_HEIGHT : START_Y;

//            if(currentXPosition >= maxWidth){
//                currentXPosition = START_X;
//                currentYPosition = currentYPosition + (IMAGE_HEIGHT * 2);
//            }else{
                currentXPosition += IMAGE_WIDTH;
//            }
        }
    }

    public void newLine(){
        currentXPosition = START_X;
        currentYPosition = currentYPosition + (IMAGE_HEIGHT * 2);
        jvision.insertNewLine();
    }

    public void addImage(BufferedImage bufferedImage, String title) throws HistogramException {
        ImageModel imageModel = new ImageModel(bufferedImage, title);

        imageModels.add(imageModel);
    }

    public void addImage(ImageModel imageModel){
        imageModels.add(imageModel);
    }


}
