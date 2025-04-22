		return new Composite(parent, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return new Point(x, y);
			}
		};
