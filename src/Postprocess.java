import java.awt.image.BufferedImage;

public class Postprocess 
{
	
	//clean up thresholded image
	public  static BufferedImage postprocessAnImage(BufferedImage source)
	{
		source = ImageOp.open(source, 3);
		source = ImageOp.close(source, 1);
		
		return source;
	}
}
