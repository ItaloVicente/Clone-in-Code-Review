	private static ImageDataProvider asImageDataProvider(ImageDescriptor descriptor) {
		return new ImageDataProvider() {
			ImageData cached;
			int cachedZoom;

			@Override
			public ImageData getImageData(int zoom) {
				if (zoom == cachedZoom) {
					return cached;
				}
				ImageData zoomed = descriptor.getImageData(zoom);
				if (zoomed != null) {
					cached = zoomed;
					cachedZoom = zoom;
					return zoomed;
				}
				if (zoom == 100) {
					return ImageDescriptor.getMissingImageDescriptor().getImageData(100);
				}

				ImageData data100 = descriptor.getImageData(100);
				if (data100 != null) {
					cached = data100;
					cachedZoom = 100;
					return null;
				}
				return null;
			}
		};
	}

