		final ImageData imageData = image.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(greenScreen
				.getRGB());
		final Image checkboxImage = resourceManager.createImage(
				ImageDescriptor.createFromImageData(imageData));
		image.dispose();
		return checkboxImage;
