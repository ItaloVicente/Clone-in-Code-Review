		drawImage(getUnzoomedImageDataProvider(src), ox, oy);
	}

	private static ImageDataProvider getUnzoomedImageDataProvider(ImageData imageData) {
		return zoom -> zoom == 100 ? imageData : null;
	}

	final protected void drawImage(ImageDataProvider srcProvider, int ox, int oy) {
