	@Override
	public ImageData getImageData(int zoom) {
		Image image = internalCreateImage(Display.getCurrent());
		ImageData result = null;
		if (zoom == 100) {
			result = image.getImageData();
		}
		if (isAtCurrentZoom(image, zoom)) {
			result = image.getImageDataAtCurrentZoom();
		}
		image.dispose();
		return result;
	}

	private static boolean isAtCurrentZoom(Image image, int zoom) {
		Rectangle bounds= image.getBounds();
		Rectangle boundsInPixels= image.getBoundsInPixels();
		return bounds.width == scaleDown(boundsInPixels.width, zoom)
				|| bounds.height == scaleDown(boundsInPixels.height, zoom);
	}
