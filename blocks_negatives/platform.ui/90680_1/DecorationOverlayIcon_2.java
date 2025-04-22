		this.size = new Supplier<Point>() {
			@Override
			public Point get() {
				ImageData data = baseImageData.get();
				return new Point(data.width, data.height);
			}
