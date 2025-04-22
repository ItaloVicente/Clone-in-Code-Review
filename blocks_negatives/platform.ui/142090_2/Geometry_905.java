    public static Rectangle subtract(Rectangle rect1, Rectangle rect2) {
    	return new Rectangle(rect1.x - rect2.x, rect1.y - rect2.y, rect1.width - rect2.width, rect1.height - rect2.height);
    }

    /**
     * <p>Returns a new Rectangle whose x, y, width, and height is the sum of the x, y, width, and height values of
     * both rectangles respectively.</p>
     *
     * @param rect1 first rectangle to add
     * @param rect2 second rectangle to add
     * @return a new rectangle whose x, y, height, and width attributes are the sum of the corresponding attributes from
     *         the arguments.
     * @since 3.3
     */
    public static Rectangle add(Rectangle rect1, Rectangle rect2) {
    	return new Rectangle(rect1.x + rect2.x, rect1.y + rect2.y,
    			rect1.width + rect2.width, rect1.height + rect2.height);
    }

    /**
     * Adds two points as 2d vectors. Returns a new point whose coordinates are
     * the sum of the original two points.
     *
     * @param point1 the first point (not null)
     * @param point2 the second point (not null)
     * @return a new point whose coordinates are the sum of the given points
     * @since 3.0
     */
    public static Point add(Point point1, Point point2) {
        return new Point(point1.x + point2.x, point1.y + point2.y);
    }

    /**
     * Divides both coordinates of the given point by the given scalar.
     *
     * @since 3.1
     *
     * @param toDivide point to divide
     * @param scalar denominator
     * @return a new Point whose coordinates are equal to the original point divided by the scalar
     */
    public static Point divide(Point toDivide, int scalar) {
        return new Point(toDivide.x / scalar, toDivide.y / scalar);
    }


    /**
     * Performs vector subtraction on two points. Returns a new point equal to
     * (point1 - point2).
     *
     * @param point1 initial point
     * @param point2 vector to subtract
     * @return the difference (point1 - point2)
     * @since 3.0
     */
    public static Point subtract(Point point1, Point point2) {
        return new Point(point1.x - point2.x, point1.y - point2.y);
    }

    /**
     * Swaps the X and Y coordinates of the given point.
     *
     * @param toFlip modifies this point
     * @since 3.1
     */
    public static void flipXY(Point toFlip) {
    	int temp = toFlip.x;
    	toFlip.x = toFlip.y;
    	toFlip.y = temp;
    }

    /**
     * Swaps the X and Y coordinates of the given rectangle, along with the height and width.
     *
     * @param toFlip modifies this rectangle
     * @since 3.1
     */
    public static void flipXY(Rectangle toFlip) {
    	int temp = toFlip.x;
    	toFlip.x = toFlip.y;
    	toFlip.y = temp;

    	temp = toFlip.width;
    	toFlip.width = toFlip.height;
    	toFlip.height = temp;
    }

    /**
     * Returns the height or width of the given rectangle.
     *
     * @param toMeasure rectangle to measure
     * @param width returns the width if true, and the height if false
     * @return the width or height of the given rectangle
     * @since 3.0
     */
    public static int getDimension(Rectangle toMeasure, boolean width) {
        if (width) {
            return toMeasure.width;
        }
