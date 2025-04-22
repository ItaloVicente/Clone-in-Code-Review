	@Override
	public ImageData getImageData(int zoom) {
		Image image = internalCreateImage(Display.getCurrent());
		ImageData result = getDataProvider(image).getImageData(zoom);
		image.dispose();
		return result;
	}
