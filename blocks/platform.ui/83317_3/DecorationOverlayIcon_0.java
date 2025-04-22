		this.baseImageData = new Supplier<ImageData>() {
			private ImageData value;
			@Override
			public ImageData get() {
				if (value == null) {
					value = baseImage.getImageData();
				}
				return value;
			}
		};
		this.size = new Supplier<Point>() {
			@Override
			public Point get() {
				return sizeValue;
			}
		};
