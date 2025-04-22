
	/**
	 * Returns an image data provider that is backed by the given image.
	 * <p>
	 * This is a workaround for the missing API Image#getImageData(int zoom) (bug 496409).
	 *
	 * @param image an image
	 * @return an image data provider
	 */
	static ImageDataProvider getDataProvider(Image image) {
		return zoom -> {
			if (zoom == 100) {
				return image.getImageData();
			}
			ImageData zoomedImageData = image.getImageDataAtCurrentZoom();
			Rectangle bounds = image.getBounds();
			if (bounds.width == scaleDown(zoomedImageData.width, zoom)
					&& bounds.height == scaleDown(zoomedImageData.height, zoom)) {
				return zoomedImageData;
			}
			return null;
		};
	}

	private static int scaleDown(int value, int zoom) {
		float scaleFactor = zoom / 100f;
		return Math.round(value / scaleFactor);
	}
