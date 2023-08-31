package entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GifEncoderTestMain {
	
	public static void main(String[] args) {
		GifEncoder enc = new GifEncoder();
		enc.start("test.gif");
		enc.setDelay(10);
		for(int i = 0; i < 241; i++) {
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new File("TEMP_IMG/cycle_" + i + ".png"));
			} catch (IOException e) {
				break;
			}
			enc.addFrame(img);
		}
	}
}
