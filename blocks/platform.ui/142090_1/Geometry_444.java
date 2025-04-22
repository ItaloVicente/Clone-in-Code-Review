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

