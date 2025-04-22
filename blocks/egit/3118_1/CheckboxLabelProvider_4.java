	private static Image getImage(ResourceManager rm, Color bgColor, Image img) {
		ImageData imageData = img.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(bgColor
				.getRGB());
		Image image = rm.createImage(createFromImageData(imageData));
		img.dispose();
