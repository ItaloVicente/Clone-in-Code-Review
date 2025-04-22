	private Point getAdjustedExpected(Point calcSize, int whint, int hhint) {
		Point adjusted = computeHintOffset();
		int expectedHeight = hhint == SWT.DEFAULT ? calcSize.y : hhint + adjusted.y;
		int expectedWidth = whint == SWT.DEFAULT ? calcSize.x : whint + adjusted.x;
		return new Point(expectedWidth, expectedHeight);
	}

