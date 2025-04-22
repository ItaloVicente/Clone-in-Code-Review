
			private /*static*/ boolean isAtCurrentZoom(Image image, int zoom) {
				Rectangle bounds= image.getBounds();
				Rectangle boundsInPixels= image.getBoundsInPixels();
				return bounds.width == scaleDown(boundsInPixels.width, zoom)
						|| bounds.height == scaleDown(boundsInPixels.height, zoom);
			}

			private /*static*/ int scaleDown(int value, int zoom) {
				float scaleFactor = zoom / 100f;
				return Math.round(value / scaleFactor);
