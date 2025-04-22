		this.size = new Supplier<Point>() {
			@Override
			public Point get() {
				return sizeValue;
			}
		};
