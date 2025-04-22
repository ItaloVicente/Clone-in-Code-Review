	public static Rectangle createDiffRectangle(int left, int right, int top, int bottom) {
		return new Rectangle(-left, -top, left + right, top + bottom);
	}

	public static void expand(Rectangle rect, int left, int right, int top, int bottom) {
		rect.x -= left;
		rect.width = Math.max(0, rect.width + left + right);
		rect.y -= top;
		rect.height = Math.max(0, rect.height + top + bottom);
	}

	public static void normalize(Rectangle rect) {
		if (rect.width < 0) {
			rect.width = -rect.width;
			rect.x -= rect.width;
		}

		if (rect.height < 0) {
			rect.height = -rect.height;
			rect.y -= rect.height;
		}
	}

	public static Rectangle toControl(Control coordinateSystem,
			Rectangle toConvert) {
		return(coordinateSystem.getDisplay().map
				(null,coordinateSystem,toConvert));
	}

	public static Rectangle toDisplay(Control coordinateSystem,
			Rectangle toConvert) {
		return(coordinateSystem.getDisplay().map
				(coordinateSystem,null,toConvert));

	}

	public static int getRelativePosition(Rectangle boundary, Point toTest) {
		int result = 0;

		if (toTest.x < boundary.x) {
			result |= SWT.LEFT;
		} else if (toTest.x >= boundary.x + boundary.width) {
			result |= SWT.RIGHT;
		}

		if (toTest.y < boundary.y) {
			result |= SWT.TOP;
		} else if (toTest.y >= boundary.y + boundary.height) {
			result |= SWT.BOTTOM;
		}

		return result;
	}

	public static int getDistanceFrom(Rectangle boundary, Point toTest) {
		int side = getClosestSide(boundary, toTest);
		return getDistanceFromEdge(boundary, toTest, side);
	}

	public static int getClosestSide(Rectangle boundary, Point toTest) {
		int[] sides = new int[] { SWT.LEFT, SWT.RIGHT, SWT.TOP, SWT.BOTTOM };

		int closestSide = SWT.LEFT;
		int closestDistance = Integer.MAX_VALUE;

		for (int side : sides) {
			int distance = getDistanceFromEdge(boundary, toTest, side);

			if (distance < closestDistance) {
				closestDistance = distance;
				closestSide = side;
			}
		}

		return closestSide;
	}

	public static Rectangle copy(Rectangle toCopy) {
		return new Rectangle(toCopy.x, toCopy.y, toCopy.width, toCopy.height);
	}

	public static Point getSize(Rectangle rectangle) {
		return new Point(rectangle.width, rectangle.height);
	}

	public static void setSize(Rectangle rectangle, Point newSize) {
		rectangle.width = newSize.x;
		rectangle.height = newSize.y;
	}

	public static void setLocation(Rectangle rectangle, Point newLocation) {
		rectangle.x = newLocation.x;
		rectangle.y = newLocation.y;
	}

	public static Point getLocation(Rectangle toQuery) {
		return new Point(toQuery.x, toQuery.y);
	}

