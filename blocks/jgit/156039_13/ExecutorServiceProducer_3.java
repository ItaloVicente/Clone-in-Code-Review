package org.eclipse.jgit.niofs.internal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IOUtil {

	protected final List<File> tempFiles = new ArrayList<>();
	protected static final Map<String

	public void cleanup() {
		for (final File tempFile : tempFiles) {
			try {
				FileUtils.delete(tempFile
			} catch (IOException e) {
			}
		}
	}

	public File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("temp"
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		tempFiles.add(temp);

		return temp;
	}

	public byte[] getImage1() {
		return images.computeIfAbsent("image1"
	}

	public byte[] getImage2() {
		return images.computeIfAbsent("image2"
	}

	public byte[] getImage3() {
		return images.computeIfAbsent("image3"
	}

	private byte[] drawImage(final Color c1
							 final String message) {
		int width = 250;
		int height = 250;

		final BufferedImage bufferedImage = new BufferedImage(width

		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.setColor(c1);
		g2d.fillRect(0

		g2d.setColor(c2);
		g2d.fillOval(0

		g2d.setColor(c3);
		g2d.drawString(message

		g2d.dispose();

		byte[] imageInByte;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage
			baos.flush();
			imageInByte = baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return imageInByte;
	}
}
