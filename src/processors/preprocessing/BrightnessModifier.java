package processors.preprocessing;

import processors.Processor;
import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

public class BrightnessModifier implements Processor {

    private final int NUM_COLOURS = 256;

    private final int amount;

    public BrightnessModifier(int amount) {
        this.amount = amount;
    }

    public BufferedImage process(BufferedImage image) {
        short[] increaseLookupTable = new short[NUM_COLOURS];
        int output = 0;

        //Straight line equation
        for(int i = 0; i < increaseLookupTable.length; i++) {
            if (i <  (amount * -1)) {
                output  = 0;
            } else if (i > (255 - amount)) { //Cap at 255 maximum allowable value
                output = 255;
            } else {
                output =  i + amount;
            }
            increaseLookupTable[i] = (short) output;
        }

        image = ImageOp.pixelop(image, increaseLookupTable);

        return image;
    }

    @Override
    public String getProcessName() {
        return "Brightness Modification";
    }

    //TODO: Not implemented yet
    public BufferedImage decreaseBrightness(BufferedImage image) {
        short[] decreaseLookupTable = new short[NUM_COLOURS];
        return null;
    }

}
