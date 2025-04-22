		this.baseImageDataProvider = asImageDataProvider(baseImageDescriptor);
		this.size = () -> {
			int zoomLevel = getZoomLevel();
			if (zoomLevel != 0) {
				ImageData data = baseImageDataProvider.getImageData(zoomLevel);
				return new Point(autoScaleDown(data.width), autoScaleDown(data.height));
