import java.awt.image.*;

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
			JVision jvis = new JVision();
			jvis.setSize(1400, 3000);
			
			//training sets
			BufferedImage[] trainingImages = loadImageData();
			BufferedImage[] preprocessedImages = new BufferedImage[trainingImages.length];
			BufferedImage[] thresholdedImages = new BufferedImage[trainingImages.length];
			BufferedImage[] postprocessedImages = new BufferedImage[trainingImages.length];
			
			//images to test
			BufferedImage[] testImages = new BufferedImage[24];
			
			//preprocess images
			for (int i=0;i<12;i++)
			{
			    preprocessedImages[i] = preprocessAnImage(trainingImages[i]);
			}
			
			displayFiveImages(preprocessedImages, 0, 150, 0, jvis, "");
			
			//threshold images
			for (int i=0;i<12;i++)
			{
			    thresholdedImages[i] = thresholdAnImage(preprocessedImages[i]);
			}
			
			displayFiveImages(thresholdedImages, 0, 350, 0, jvis, "");
			
			//postprocess images
			for (int i=0;i<12;i++)
			{
			    postprocessedImages[i] = postprocessAnImage(thresholdedImages[i]);
			}
			
			displayFiveImages(postprocessedImages, 0, 600, 0, jvis, "");
			
			int area = 0;
			area = featureExtractFromAnImage(postprocessedImages[0]);
			System.out.println("Area is " + area);
		}
		catch(Exception e)
		{
			System.out.println("Error message");
			e.printStackTrace();
		}
    }
	
	public BufferedImage [] loadImageData()
	{
		BufferedImage [] trainingImages = new BufferedImage[20];
		
		//load tomato image data
		trainingImages[0] =  readInImage("images/training/tomato/tomato1-066-153.png");
		trainingImages[1] =  readInImage("images/training/tomato/tomato2-066-153.png");
		trainingImages[2] =  readInImage("images/training/tomato/tomato3-066-153.png");
		trainingImages[3] =  readInImage("images/training/tomato/tomato5-066-153.png");
		trainingImages[4] =  readInImage("images/training/tomato/tomato7-066-153.png");
		
		//load pear image data
		trainingImages[5] =  readInImage("images/training/pear/pear1-066-153.png");
		trainingImages[6] =  readInImage("images/training/pear/pear2-066-153.png");
		trainingImages[7] =  readInImage("images/training/pear/pear3-066-153.png");
		trainingImages[8] =  readInImage("images/training/pear/pear4-066-153.png");
		trainingImages[9] =  readInImage("images/training/pear/pear5-066-153.png");
		
		//load car image data
		trainingImages[10] =  readInImage("images/training/car/car1-066-153.png");
		trainingImages[11] =  readInImage("images/training/car/car2-066-153.png");
		trainingImages[12] =  readInImage("images/training/car/car3-066-153.png");
		trainingImages[13] =  readInImage("images/training/car/car5-066-153.png");
		trainingImages[14] =  readInImage("images/training/car/car7-066-153.png");
		
		//load cow image data
		trainingImages[15] =  readInImage("images/training/cow/cow1-066-153.png");
		trainingImages[16] =  readInImage("images/training/cow/cow2-066-153.png");
		trainingImages[17] =  readInImage("images/training/cow/cow3-066-153.png");
		trainingImages[18] =  readInImage("images/training/cow/cow4-066-153.png");
		trainingImages[19] =  readInImage("images/training/cow/cow8-066-153.png");
	
		/*Full Test Data
		//load apple image data
		BufferedImage [] trainingImages = new BufferedImage[40];
		trainingImages[0] =  readInImage("images/training/apple/apple1-066-153.png");
		trainingImages[1] =  readInImage("images/training/apple/apple4-066-153.png");
		trainingImages[2] =  readInImage("images/training/apple/apple5-066-153.png");
		trainingImages[3] =  readInImage("images/training/apple/apple7-066-153.png");
		trainingImages[4] =  readInImage("images/training/apple/apple9-066-153.png");
		
		//load car image data
		trainingImages[5] =  readInImage("images/training/car/car1-066-153.png");
		trainingImages[6] =  readInImage("images/training/car/car2-066-153.png");
		trainingImages[7] =  readInImage("images/training/car/car3-066-153.png");
		trainingImages[8] =  readInImage("images/training/car/car5-066-153.png");
		trainingImages[9] =  readInImage("images/training/car/car7-066-153.png");
		
		//load cow image data
		trainingImages[10] =  readInImage("images/training/cow/cow1-066-153.png");
		trainingImages[11] =  readInImage("images/training/cow/cow2-066-153.png");
		trainingImages[12] =  readInImage("images/training/cow/cow3-066-153.png");
		trainingImages[13] =  readInImage("images/training/cow/cow4-066-153.png");
		trainingImages[14] =  readInImage("images/training/cow/cow8-066-153.png");
		
		//load cup image data
		trainingImages[15] =  readInImage("images/training/cup/cup1-066-153.png");
		trainingImages[16] =  readInImage("images/training/cup/cup2-066-153.png");
		trainingImages[17] =  readInImage("images/training/cup/cup3-066-153.png");
		trainingImages[18] =  readInImage("images/training/cup/cup4-066-153.png");
		trainingImages[19] =  readInImage("images/training/cup/cup5-066-153.png");
		
		//load dog image data
		trainingImages[20] =  readInImage("images/training/dog/dog1-066-153.png");
		trainingImages[21] =  readInImage("images/training/dog/dog2-066-153.png");
		trainingImages[22] =  readInImage("images/training/dog/dog3-066-153.png");
		trainingImages[23] =  readInImage("images/training/dog/dog4-066-153.png");
		trainingImages[24] =  readInImage("images/training/dog/dog5-066-153.png");
		
		//load horse image data
		trainingImages[25] =  readInImage("images/training/horse/horse1-066-153.png");
		trainingImages[26] =  readInImage("images/training/horse/horse2-066-153.png");
		trainingImages[27] =  readInImage("images/training/horse/horse3-066-153.png");
		trainingImages[28] =  readInImage("images/training/horse/horse5-066-153.png");
		trainingImages[29] =  readInImage("images/training/horse/horse6-066-153.png");
		
		//load pear image data
		trainingImages[30] =  readInImage("images/training/pear/pear1-066-153.png");
		trainingImages[31] =  readInImage("images/training/pear/pear2-066-153.png");
		trainingImages[32] =  readInImage("images/training/pear/pear3-066-153.png");
		trainingImages[33] =  readInImage("images/training/pear/pear4-066-153.png");
		trainingImages[34] =  readInImage("images/training/pear/pear5-066-153.png");
		
		//load tomato image data
		trainingImages[35] =  readInImage("images/training/tomato/tomato1-066-153.png");
		trainingImages[36] =  readInImage("images/training/tomato/tomato2-066-153.png");
		trainingImages[37] =  readInImage("images/training/tomato/tomato3-066-153.png");
		trainingImages[38] =  readInImage("images/training/tomato/tomato5-066-153.png");
		trainingImages[39] =  readInImage("images/training/tomato/tomato7-066-153.png");
		*/
		
		return trainingImages;
	}
	
	//reads in image data and stores it in BufferedImage object
	public BufferedImage readInImage(String filename)
	{
	    return ImageOp.readInImage(filename);
	}

	//displays image in JVision window provided with a position and title
	public void displayAnImage(BufferedImage img, JVision display, int x, int y, String title)
	{
	    display.imdisp(img,title,x,y);
	}

	//takes image data and uses it to plot a histogram then display on JVision window
	public void createAndDisplayHistogram(BufferedImage img,JVision display,int x,int y,String title) throws Exception
	{
	    Histogram hist = new Histogram(img);
	    GraphPlot gp = new GraphPlot(hist);
	    display.imdisp(gp,title,x,y);
	}

	public BufferedImage preprocessAnImage(BufferedImage source)
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
	public BufferedImage performNoiseReduction(BufferedImage source)
	{
		return ImageOp.median(source, 2);
	}
	
	//creates lookup table based on int c
	public short [] brightnessLUT(int c)
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
	public BufferedImage enhanceBrightness(BufferedImage source)
	{
		short [] data = brightnessLUT(-5);
		source = ImageOp.pixelop(source, data);
		return source;
	}

	//create a linear stretching lookup table
	public short [] linearStretchLUT(double m, double c)
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
	public short [] powerLawLUT(double gamma)
	{
		short [] data = new short[256];
			
		for(int i = 0; i < data.length; i++)
		{
			data[i] = (short) (Math.pow(i, gamma) / Math.pow(255, gamma - 1));
		}
		return data;
	}
		
	//create a histogram equalisation LUT
	public short[] histogramEqualisationLUT (Histogram hist) throws HistogramException
	{
		short [] data = new short[256];
			
		for(int i = 0; i < data.length; i++)
		{
			data[i] = (short) Math.max(0, (256 * hist.getCumulativeFrequency(i) / hist.getNumSamples()) - 1);
		}
		return data;
	}
		
	//enhances the contrast of an image
	public BufferedImage enhanceContrast(BufferedImage source)
	{
		short [] data;
			
		try
		{
			//use linear stretching LUT
			//use linear stretching LUT
			//data = linearStretchLUT(1.66, -80.0);
		
			//use power law LUT
			//data = powerLawLUT(2);
		
			//use histogram equalisation LUT
			Histogram hist = new Histogram(source);
			data = histogramEqualisationLUT(hist);
					
			source = ImageOp.pixelop(source, data);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return source;
	}

	//calculate a lookup table using the threshold
	public short[] thresholdLUT(double t) 
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

	//create binary image using threshold LUT
	public BufferedImage thresholdAnImage(BufferedImage source)
	{
		short [] data = thresholdLUT(70);
		source = ImageOp.pixelop(source, data);
		return source;
	}

	//calculate mean of image
	public double mean(BufferedImage source)
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
	public double standardDeviation(BufferedImage source, double mean)
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

	//automatically perform thresholding
	public BufferedImage performAutomaticThresholding(BufferedImage source)
	{
		double average = mean(source);
		double sd = standardDeviation(source, average);
		double a = -0.5;
		double threshold = (average + (a * sd));
		
		short [] data = thresholdLUT(threshold);
		source = ImageOp.pixelop(source, data);
		return source;
	}

	//clean up thresholded image
	public BufferedImage postprocessAnImage(BufferedImage source)
	{
		source = ImageOp.open(source, 3);
		source = ImageOp.close(source, 1);
		
		return source;
	}

	//gets the area of an image
	public int featureExtractFromAnImage(BufferedImage source)
	{
		return ImageOp.area(source);
	}

	//displays all images 
	public void displayFiveImages(BufferedImage[] img,int a,int b, int c,JVision display,String title)
	{
		 display.imdisp(img[c], title, a, b);
		 display.imdisp(img[c + 1], title, 260, b);
		 display.imdisp(img[c + 2], title, 520, b);
		 display.imdisp(img[c + 3], title, 780, b);
		 display.imdisp(img[c + 4], title, 1040, b);
	}
}