package processors.postprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

/**
 * Created by Niall McShane on 23/03/2016.
 */
public class ClosePostProcessor implements Processor {
    private final int amount;

    public ClosePostProcessor(int amount) {
        this.amount = amount;
    }

    @Override
    public BufferedImage process(BufferedImage bufferedImage) {
        return ImageOp.open(bufferedImage, amount);
    }

    @Override
    public String getProcessName() {
        return "Close Post Process";
    }
}
