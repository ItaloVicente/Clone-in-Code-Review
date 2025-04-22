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
					return ImageDescriptor.getMissingImageDescriptor()
							.getImageData(100);
