package pipeline;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 22/03/2016.
 */
public interface Processor {

    public BufferedImage process(BufferedImage bufferedImage);

    public String getProcessName();

}
