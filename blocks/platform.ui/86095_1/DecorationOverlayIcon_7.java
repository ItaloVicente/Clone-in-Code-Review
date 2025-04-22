	private int computeInPoints(ImageDataProvider overlayImageProvider, ToIntFunction<ImageData> computer) {
		ImageData overlayData = overlayImageProvider.getImageData(getZoomLevel());
		if (overlayData != null) {
			int valueInPixels = computer.applyAsInt(overlayData);
			return autoScaleDown(valueInPixels);
		}
		overlayData = overlayImageProvider.getImageData(100);
		return computer.applyAsInt(overlayData);
	}

