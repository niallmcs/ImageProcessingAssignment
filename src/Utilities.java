import visionsystem.GraphPlot;
import visionsystem.Histogram;
import visionsystem.ImageOp;
import visionsystem.JVision;

import java.awt.image.BufferedImage;

public class Utilities
{
	//loads all training images, only loads 4 classes for now
	public static BufferedImage [] loadImageData()
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
	public static BufferedImage readInImage(String filename)
	{
	    return ImageOp.readInImage(filename);
	}

	//displays image in JVision window provided with a position and title
	public static void displayAnImage(BufferedImage img, JVision display, int x, int y, String title)
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
	
	//displays selected images 
	public static void displayFourImages(BufferedImage[] img,int a,int b, int c, int d, JVision display,String title)
	{
		 display.imdisp(img[a], title, 0, 0);
		 display.imdisp(img[b], title, 300, 0);
		 display.imdisp(img[c], title, 600, 0);
		 display.imdisp(img[d], title, 900, 0);
	}
}
