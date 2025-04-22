package org.eclipse.ui.testing.dumps;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AwtScreenshot {

	public static void main(String[] args) {
		try {
			System.setProperty("java.awt.headless", "false");
			Robot robot= new Robot();
			Rectangle rect= new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image= robot.createScreenCapture(rect);
			File file= new File(args[0]);
			ImageIO.write(image, "png", file);

			System.out.println("AWT screenshot saved to: " + file.getAbsolutePath());
		} catch (HeadlessException|AWTException|IOException e) {
			e.printStackTrace();
		}
	}

}
