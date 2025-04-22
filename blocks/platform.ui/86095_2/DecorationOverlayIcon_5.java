			public ImageData getImageData(int zoom) {
				if (zoom == cachedZoom) {
					return cached;
				}
				if (isAtCurrentZoom(baseImage, zoom)) {
					cached = baseImage.getImageDataAtCurrentZoom();
				} else if (zoom == 100) {
					cached = baseImage.getImageData();
				} else {
					zoom = 0;
					cached = null;
