	private Geometry() {
	}

	public static int distanceSquared(Point p1, Point p2) {
		int term1 = p1.x - p2.x;
		int term2 = p1.y - p2.y;
		return term1 * term1 + term2 * term2;
	}

	public static double magnitude(Point p) {
		return Math.sqrt(magnitudeSquared(p));
	}

	public static int magnitudeSquared(Point p) {
		return p.x * p.x + p.y * p.y;
	}

	public static int dotProduct(Point p1, Point p2) {
		return p1.x * p2.x + p1.y * p2.y;
	}

	public static Point min(Point p1, Point p2) {
		return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
	}

	public static Point max(Point p1, Point p2) {
		return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
	}

	public static Point getDirectionVector(int distance, int direction) {
		switch (direction) {
		case SWT.TOP:
			return new Point(0, -distance);
		case SWT.BOTTOM:
			return new Point(0, distance);
		case SWT.LEFT:
			return new Point(-distance, 0);
		case SWT.RIGHT:
			return new Point(distance, 0);
		}

		return new Point(0, 0);
	}

	public static Point centerPoint(Rectangle rect) {
		return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
	}

	public static Point copy(Point toCopy) {
		return new Point(toCopy.x, toCopy.y);
	}

	public static void set(Point result, Point toCopy) {
		result.x = toCopy.x;
		result.y = toCopy.y;
	}

	public static void set(Rectangle result, Rectangle toCopy) {
		result.x = toCopy.x;
		result.y = toCopy.y;
		result.width = toCopy.width;
		result.height = toCopy.height;
	}

	public static Rectangle subtract(Rectangle rect1, Rectangle rect2) {
		return new Rectangle(rect1.x - rect2.x, rect1.y - rect2.y, rect1.width - rect2.width, rect1.height - rect2.height);
	}

	public static Rectangle add(Rectangle rect1, Rectangle rect2) {
		return new Rectangle(rect1.x + rect2.x, rect1.y + rect2.y,
				rect1.width + rect2.width, rect1.height + rect2.height);
	}

	public static Point add(Point point1, Point point2) {
		return new Point(point1.x + point2.x, point1.y + point2.y);
	}

	public static Point divide(Point toDivide, int scalar) {
		return new Point(toDivide.x / scalar, toDivide.y / scalar);
	}


	public static Point subtract(Point point1, Point point2) {
		return new Point(point1.x - point2.x, point1.y - point2.y);
	}

	public static void flipXY(Point toFlip) {
		int temp = toFlip.x;
		toFlip.x = toFlip.y;
		toFlip.y = temp;
	}

	public static void flipXY(Rectangle toFlip) {
		int temp = toFlip.x;
		toFlip.x = toFlip.y;
		toFlip.y = temp;

		temp = toFlip.width;
		toFlip.width = toFlip.height;
		toFlip.height = temp;
	}

	public static int getDimension(Rectangle toMeasure, boolean width) {
		if (width) {
			return toMeasure.width;
		}
