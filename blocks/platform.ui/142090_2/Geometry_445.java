	public static Rectangle getExtrudedEdge(Rectangle toExtrude, int size,
			int orientation) {
		Rectangle bounds = new Rectangle(toExtrude.x, toExtrude.y,
				toExtrude.width, toExtrude.height);

		if (!isHorizontal(orientation)) {
			bounds.width = size;
		} else {
			bounds.height = size;
		}

		switch (orientation) {
		case SWT.RIGHT:
			bounds.x = toExtrude.x + toExtrude.width - bounds.width;
			break;
		case SWT.BOTTOM:
			bounds.y = toExtrude.y + toExtrude.height - bounds.height;
			break;
		}

		normalize(bounds);

		return bounds;
	}

	public static int getOppositeSide(int swtDirectionConstant) {
		switch (swtDirectionConstant) {
		case SWT.TOP:
			return SWT.BOTTOM;
		case SWT.BOTTOM:
			return SWT.TOP;
		case SWT.LEFT:
			return SWT.RIGHT;
		case SWT.RIGHT:
			return SWT.LEFT;
		}

		return swtDirectionConstant;
	}

	public static int getSwtHorizontalOrVerticalConstant(boolean horizontal) {
		if (horizontal) {
			return SWT.HORIZONTAL;
		}
