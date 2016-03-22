import visionsystem.JVision;

import java.awt.image.BufferedImage;

public class VisionSystem
{
	public static void main(String[] args)
	{
		new VisionSystem();
	}
		
	//constructor for VisionSystem class
	public VisionSystem()
	{
		try
		{
			//create jvision window and sets size
			JVision jvis = new JVision();
			jvis.setSize(1400, 1000);
			
			//training sets
			BufferedImage[] trainingImages = Utilities.loadImageData();
			BufferedImage[] preprocessedImages = new BufferedImage[trainingImages.length];
			BufferedImage[] thresholdedImages = new BufferedImage[trainingImages.length];
			BufferedImage[] postprocessedImages = new BufferedImage[trainingImages.length];
			
			//images to test
			BufferedImage[] testImages = new BufferedImage[24];
			
			Utilities.displayFourImages(trainingImages,5,6,7,8,jvis, "test");
			
			//preprocess images
			for (int i=0;i<12;i++)
			{
			    preprocessedImages[i] = Preprocess.preprocessAnImage(trainingImages[i]);
			}
			
			//threshold images
			for (int i=0;i<12;i++)
			{
			    thresholdedImages[i] = Threshold.performAutomaticThresholding(preprocessedImages[i]);
			}
			
			//postprocess images
			for (int i=0;i<12;i++)
			{
			    postprocessedImages[i] = Postprocess.postprocessAnImage(thresholdedImages[i]);
			}
			
			//calculating area of an object
			int area = 0;
			area = FeatureExtraction.featureExtractFromAnImage(postprocessedImages[0]);
			System.out.println("Area is " + area);
		}
		catch(Exception e)
		{
			System.out.println("Error message");
			e.printStackTrace();
		}
    }
}