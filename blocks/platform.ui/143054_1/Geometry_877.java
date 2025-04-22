	}

	public static int getCoordinate(Point toMeasure, boolean width) {
		return width ? toMeasure.x : toMeasure.y;
	}

	public static int getCoordinate(Rectangle toMeasure, boolean width) {
		return width ? toMeasure.x : toMeasure.y;
	}

	public static void setDimension(Rectangle toSet, boolean width, int newCoordinate) {
		if (width) {
			toSet.width = newCoordinate;
		} else {
			toSet.height = newCoordinate;
		}
	}

	public static void setCoordinate(Rectangle toSet, boolean width, int newCoordinate) {
		if (width) {
			toSet.x = newCoordinate;
		} else {
			toSet.y = newCoordinate;
		}
	}

	public static void setCoordinate(Point toSet, boolean width, int newCoordinate) {
		if (width) {
			toSet.x = newCoordinate;
		} else {
			toSet.y = newCoordinate;
		}
	}

	public static int getDistanceFromEdge(Rectangle rectangle, Point testPoint,
			int edgeOfInterest) {
		switch (edgeOfInterest) {
		case SWT.TOP:
			return testPoint.y - rectangle.y;
		case SWT.BOTTOM:
			return rectangle.y + rectangle.height - testPoint.y;
		case SWT.LEFT:
			return testPoint.x - rectangle.x;
		case SWT.RIGHT:
			return rectangle.x + rectangle.width - testPoint.x;
		}

		return 0;
	}

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
