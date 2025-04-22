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
