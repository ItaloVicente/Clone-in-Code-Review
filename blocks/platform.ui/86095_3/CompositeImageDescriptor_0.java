	protected abstract class CachedImageDataProvider implements ImageDataProvider {
		public int getWidth() {
			return computeInPoints(imageData -> imageData.width);
		}

		public int getHeight() {
			return computeInPoints(imageData -> imageData.height);
		}

		public int computeInPoints(ToIntFunction<ImageData> function) {
			ImageData overlayData = getImageData(getZoomLevel());
			if (overlayData != null) {
				int valueInPixels = function.applyAsInt(overlayData);
				return autoScaleDown(valueInPixels);
			}
			overlayData = getImageData(100);
			return function.applyAsInt(overlayData);
		}
	}

	private final class CachedImageImageDataProvider extends CachedImageDataProvider {
		final Image baseImage;
		ImageData cached;
		int cachedZoom;

		private CachedImageImageDataProvider(Image baseImage) {
			this.baseImage = Objects.requireNonNull(baseImage);
		}

		@Override
		public ImageData getImageData(int zoom) {
			if (zoom == cachedZoom) {
				return cached;
			}
			if (zoom == 100) {
				cached = baseImage.getImageData();
			} else if (isAtCurrentZoom(baseImage, zoom)) {
				cached = baseImage.getImageDataAtCurrentZoom();
			} else {
				zoom = 0;
				cached = null;
			}
			cachedZoom = zoom;
			return cached;
		}

		private /*static*/ boolean isAtCurrentZoom(Image image, int zoom) {
			Rectangle bounds= image.getBounds();
			Rectangle boundsInPixels= image.getBoundsInPixels();
			return bounds.width == scaleDown(boundsInPixels.width, zoom)
					|| bounds.height == scaleDown(boundsInPixels.height, zoom);
		}

		private /*static*/ int scaleDown(int value, int zoom) {
			float scaleFactor = zoom / 100f;
			return Math.round(value / scaleFactor);
		}
	}

	private final class CachedDescriptorImageDataProvider extends CachedImageDataProvider {
		final ImageDescriptor descriptor;
		ImageData cached;
		int cachedZoom;

		private CachedDescriptorImageDataProvider(ImageDescriptor descriptor) {
			this.descriptor = Objects.requireNonNull(descriptor);
		}

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
	}

