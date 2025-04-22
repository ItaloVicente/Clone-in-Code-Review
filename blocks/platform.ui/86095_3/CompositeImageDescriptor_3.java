
	protected boolean supportsZoomLevel(int zoom) {
		return zoom > 0 && zoom % 100 == 0;
	}

	private ImageData getZoomedImageData(ImageDataProvider srcProvider) {
		ImageData src = srcProvider.getImageData(compositeZoom);
		if (src == null) {
			ImageData src100 = srcProvider.getImageData(100);
			src = src100.scaledTo(autoScaleUp(src100.width), autoScaleUp(src100.height));
		}
		return src;
	}

	protected int getZoomLevel() {
		return compositeZoom;
	}

	protected int autoScaleDown(int pixels) {
		if (compositeZoom == 100) {
			return pixels;
		}
		float scaleFactor = compositeZoom / 100f;
		return Math.round(pixels / scaleFactor);
	}

	protected int autoScaleUp(int points) {
		return scaleUp(points, compositeZoom);
	}

	protected CachedImageDataProvider createCachedImageDataProvider(Image image) {
		return new CachedImageImageDataProvider(image);
	}

	protected CachedImageDataProvider createCachedImageDataProvider(ImageDescriptor imageDescriptor) {
		return new CachedDescriptorImageDataProvider(imageDescriptor);
	}

	private static int scaleUp(int points, int zoom) {
		if (zoom == 100) {
			return points;
		}
		float scaleFactor = zoom / 100f;
		return Math.round(points * scaleFactor);
	}
