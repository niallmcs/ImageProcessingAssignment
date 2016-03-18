import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Threshold {
	
	//create binary image using manual threshold LUT
	public static BufferedImage thresholdAnImage(BufferedImage source)
	{
		short [] data = thresholdLUT(70);
		source = ImageOp.pixelop(source, data);
		return source;
	}
		
	//calculate a lookup table using the threshold
	public static short[] thresholdLUT(double t) 
	{
		short [] data = new short[256];
		int output = 0;
			
		for(int i = 0; i < data.length; i++)
		{
			if (i <= t)
			{
				output = 255;
			}
			else
			{
				output = 0;
			}
			data[i] = (short) output;
		}
		return data;
	}
	
	//automatically perform thresholding
	public static BufferedImage performAutomaticThresholding(BufferedImage source)
	{
		double average = mean(source);
		double sd = standardDeviation(source, average);
		double a = -0.5;
		double threshold = (average + (a * sd));
			
		short [] data = thresholdLUT(threshold);
		source = ImageOp.pixelop(source, data);
		return source;
	}

	//calculate mean of image
	public static double mean(BufferedImage source)
	{
		double width = source.getWidth();
		double height = source.getHeight();
		double sum = 0;
		
		Raster raster = source.getRaster();
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				sum += raster.getSample(i,j,0);
			}
		}
		return sum / (width * height);
	}

	//calculate standard deviation of image
	public static double standardDeviation(BufferedImage source, double mean)
	{
		double width = source.getWidth();
		double height = source.getHeight();
		double sum = 0;
		
		Raster raster = source.getRaster();
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				sum += Math.pow((raster.getSample(i,j,0) - mean),2);
			}
		}
		return Math.sqrt(sum / ((width * height) - 1));
	}
}
