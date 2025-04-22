			ImageData data = baseImageData.get();
			return new Point(data.width, data.height);
		};
		this.baseImageData = new Supplier<ImageData>() {
			private ImageData value;
			@Override
			public ImageData get() {
				if (value == null) {
					value = baseImageDescriptor.getImageData();
				}
				return value;
