import visionsystem.ImageOp;

import java.awt.image.BufferedImage;

public class Postprocess 
{
	
	//clean up thresholded image
	public  static BufferedImage postprocessAnImage(BufferedImage source)
	{
		ImageOp temp = new ImageOp();
		source = ImageOp.open(source, 3);
		source = ImageOp.close(source, 1);
		
		return source;
	}
}
