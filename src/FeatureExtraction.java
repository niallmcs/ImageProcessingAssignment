import visionsystem.ImageOp;

import java.awt.image.BufferedImage;
@Deprecated
public class FeatureExtraction 
{
	
	//gets the area of an image
	public static int featureExtractFromAnImage(BufferedImage source){
		return ImageOp.area(source);
	}
}
