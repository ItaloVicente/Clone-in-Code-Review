		return getImageData(100);
	}

	@Override
	public ImageData getImageData(int zoom) {
		if (!supportsZoomLevel(zoom)) {
			return null;
		}
