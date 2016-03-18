import java.awt.image.BufferedImage;

public class Preprocess {
	
	public static BufferedImage preprocessAnImage(BufferedImage source)
	{
		//reduce noise
		source = performNoiseReduction(source);
		
		//enhance brightness
		source = enhanceBrightness(source);
		
		//enhance contrast
		source = enhanceContrast(source);
		
		return source;
	}
	
	//removes noise from the image
	public static BufferedImage performNoiseReduction(BufferedImage source)
	{

		final float[] LOWPASS3X3 = {1/9.f, 1/9.f, 1/9.f,
									1/9.f, 1/9.f, 1/9.f,
									1/9.f, 1/9.f, 1/9.f};
		
		final float[] LOWPASS5X5 = {1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
									1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
									1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
									1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f,
									1/25.f, 1/25.f, 1/25.f, 1/25.f, 1/25.f};
		
		//return ImageOp.convolver(source, LOWPASS5X5);
		return ImageOp.median(source, 2);
	}
	
	//creates lookup table based on int c
	public static short [] brightnessLUT(int c)
	{
		short [] data = new short[256];
		int output = 0;
			
		for(int i = 0; i < data.length; i++)
		{
			if (i <  (c * -1))
			{
				output  = 0;
			}
			else if (i > (255 - c))
			{
				output = 255;
			}
			else
			{
				output =  i + c;
			}
			data[i] = (short) output;
		}
		return data;
	}

	//enhances the brightness of an image
	public static BufferedImage enhanceBrightness(BufferedImage source)
	{
		short [] data = brightnessLUT(-5);
		source = ImageOp.pixelop(source, data);
		return source;
	}

	//create a linear stretching lookup table
	public static short [] linearStretchLUT(double m, double c)
	{
		short [] data = new short[256];
		double output = 0;
			
		for(int i = 0; i < data.length; i++)
		{
			if (i < ((c * -1)/m))
			{
				output = 0;
			}
			else if (i > ((255 - c) / m))
			{
				output = 255;
			}
			else
			{
				output =  m * i + c;
			}
			data[i] = (short) output; 
		}
		return data;
	}
	
	//create a power law lookup table 
	public static short [] powerLawLUT(double gamma)
	{
		short [] data = new short[256];
			
		for(int i = 0; i < data.length; i++)
		{
			data[i] = (short) (Math.pow(i, gamma) / Math.pow(255, gamma - 1));
		}
		return data;
	}
		
	//create a histogram equalisation LUT
	public static short[] histogramEqualisationLUT (Histogram hist) throws HistogramException
	{
		short [] data = new short[256];
			
		for(int i = 0; i < data.length; i++)
		{
			data[i] = (short) Math.max(0, (256 * hist.getCumulativeFrequency(i) / hist.getNumSamples()) - 1);
		}
		return data;
	}
		
	//enhances the contrast of an image
	public static BufferedImage enhanceContrast(BufferedImage source)
	{
		short [] data;
			
		try
		{
			//use linear stretching LUT
			data = linearStretchLUT(1.66, -80.0);
		
			//use power law LUT
			//data = powerLawLUT(2);
		
			//use histogram equalisation LUT
			//Histogram hist = new Histogram(source);
			//data = histogramEqualisationLUT(hist);
					
			source = ImageOp.pixelop(source, data);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return source;
	}

}
