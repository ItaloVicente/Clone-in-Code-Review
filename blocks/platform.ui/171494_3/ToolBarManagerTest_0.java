	private static void assertImageEqualsDescriptor(ImageDescriptor descriptor, Image image) {
		Image createImage = descriptor.createImage();
		try {
			assertImageDataEquals(createImage.getImageData(), image.getImageData());
		} finally {
			createImage.dispose();
		}
	}

	private static void assertImageDataEquals(ImageData im1, ImageData im2) {
		assertNotNull("ImageData 1 is null", im1);
		assertNotNull("ImageData 2 is null", im2);
		assertEquals("ImageData width missmatch", im1.width, im2.width);
		assertEquals("ImageData height missmatch", im1.height, im2.height);
		for (int x = 0; x < im1.width; x++) {
			for (int y = 0; y < im1.height; y++) {
				assertEquals("pixel (" + x + "," + y + ") are not equal", im1.getPixel(x, y), im2.getPixel(x, y));
			}
		}
	}

