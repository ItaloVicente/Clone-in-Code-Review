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
