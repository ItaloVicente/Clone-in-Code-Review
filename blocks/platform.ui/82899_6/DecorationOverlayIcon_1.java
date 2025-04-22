	public DecorationOverlayIcon(ImageDescriptor baseImageDescriptor, ImageDescriptor overlayImage, int quadrant) {
		this.referenceImageOrDescriptor = baseImageDescriptor;
		this.overlays = createArrayFrom(overlayImage, quadrant);
		this.size = new Supplier<Point>() {
			@Override
			public Point get() {
				ImageData data = baseImageData.get();
				return new Point(data.width, data.height);
			}

		};
		this.baseImageData = new Supplier<ImageData>() {
			private ImageData value;

			@Override
			public ImageData get() {
				if (value == null) {
					value = baseImageDescriptor.getImageData();
				}
				return value;
			}
		};
	}

