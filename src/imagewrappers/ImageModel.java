package imagewrappers;

import visionsystem.GraphPlot;
import visionsystem.Histogram;
import visionsystem.HistogramException;

import java.awt.image.BufferedImage;

/**
 * Created by Cathan O'Donnell on 22/03/2016.
 */
public class ImageModel {

    private BufferedImage bufferedImage;
    private final Histogram histogram;
    private final String title;
    private final PropertyModel propertyModel;

    public ImageModel(BufferedImage bufferedImage, Histogram histogram, String title, PropertyModel propertyModel) {
        this.bufferedImage = bufferedImage;
        this.histogram = histogram;
        this.title = title;
        this.propertyModel = propertyModel;
    }

    public ImageModel(BufferedImage bufferedImage, String title) throws HistogramException {
        this.bufferedImage = bufferedImage;
        this.title = title;

        if(bufferedImage.getType() != 12 && bufferedImage.getType() != 11) {
            this.histogram = new Histogram(bufferedImage);
        }else{
            this.histogram = new Histogram();
        }
        this.propertyModel = new PropertyModel();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Histogram getHistogram() {
        return histogram;
    }

    public String getTitle() {
        return title;
    }

    public PropertyModel getPropertyModel() { return propertyModel; }

    public GraphPlot toGraphPlot(){
        return new GraphPlot(histogram);
    }

}
