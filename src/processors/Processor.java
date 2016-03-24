package processors;

import visionsystem.HistogramException;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public interface Processor {

    public BufferedImage process(BufferedImage bufferedImage) throws HistogramException;

    public String getProcessName();

}
