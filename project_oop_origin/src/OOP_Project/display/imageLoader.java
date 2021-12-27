package OOP_Project.display;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class imageLoader 
{
	public static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(imageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
