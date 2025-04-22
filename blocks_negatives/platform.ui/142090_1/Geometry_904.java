    /**
     * Prevent this class from being instantiated.
     *
     * @since 3.0
     */
    private Geometry() {
    }

    /**
     * Returns the square of the distance between two points.
     * <p>This is preferred over the real distance when searching
     * for the closest point, since it avoids square roots.</p>
     *
     * @param p1 first endpoint
     * @param p2 second endpoint
     * @return the square of the distance between the two points
     *
     * @since 3.0
     */
    public static int distanceSquared(Point p1, Point p2) {
        int term1 = p1.x - p2.x;
        int term2 = p1.y - p2.y;
        return term1 * term1 + term2 * term2;
    }

    /**
     * Returns the magnitude of the given 2d vector (represented as a Point)
     *
     * @param p point representing the 2d vector whose magnitude is being computed
     * @return the magnitude of the given 2d vector
     * @since 3.0
     */
    public static double magnitude(Point p) {
        return Math.sqrt(magnitudeSquared(p));
    }

    /**
     * Returns the square of the magnitude of the given 2-space vector (represented
     * using a point)
     *
     * @param p the point whose magnitude is being computed
     * @return the square of the magnitude of the given vector
     * @since 3.0
     */
    public static int magnitudeSquared(Point p) {
        return p.x * p.x + p.y * p.y;
    }

    /**
     * Returns the dot product of the given vectors (expressed as Points)
     *
     * @param p1 the first vector
     * @param p2 the second vector
     * @return the dot product of the two vectors
     * @since 3.0
     */
    public static int dotProduct(Point p1, Point p2) {
        return p1.x * p2.x + p1.y * p2.y;
    }

    /**
     * Returns a new point whose coordinates are the minimum of the coordinates of the
     * given points
     *
     * @param p1 a Point
     * @param p2 a Point
     * @return a new point whose coordinates are the minimum of the coordinates of the
     * given points
     * @since 3.0
     */
    public static Point min(Point p1, Point p2) {
        return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
    }

    /**
     * Returns a new point whose coordinates are the maximum of the coordinates
     * of the given points
     * @param p1 a Point
     * @param p2 a Point
     * @return point a new point whose coordinates are the maximum of the coordinates
     * @since 3.0
     */
    public static Point max(Point p1, Point p2) {
        return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
    }

    /**
     * Returns a vector in the given direction with the given
     * magnitude. Directions are given using SWT direction constants, and
     * the resulting vector is in the screen's coordinate system. That is,
     * the vector (0, 1) is down and the vector (1, 0) is right.
     *
     * @param distance magnitude of the vector
     * @param direction one of SWT.TOP, SWT.BOTTOM, SWT.LEFT, or SWT.RIGHT
     * @return a point representing a vector in the given direction with the given magnitude
     * @since 3.0
     */
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

    /**
     * Returns the point in the center of the given rectangle.
     *
     * @param rect rectangle being computed
     * @return a Point at the center of the given rectangle.
     * @since 3.0
     */
    public static Point centerPoint(Rectangle rect) {
        return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
    }

    /**
     * Returns a copy of the given point
     *
     * @param toCopy point to copy
     * @return a copy of the given point
     */
    public static Point copy(Point toCopy) {
        return new Point(toCopy.x, toCopy.y);
    }

    /**
     * Sets result equal to toCopy
     *
     * @param result object that will be modified
     * @param toCopy object that will be copied
     * @since 3.1
     */
    public static void set(Point result, Point toCopy) {
    	result.x = toCopy.x;
    	result.y = toCopy.y;
    }

    /**
     * Sets result equal to toCopy
     *
     * @param result object that will be modified
     * @param toCopy object that will be copied
     * @since 3.1
     */
    public static void set(Rectangle result, Rectangle toCopy) {
    	result.x = toCopy.x;
    	result.y = toCopy.y;
    	result.width = toCopy.width;
    	result.height = toCopy.height;
    }

    /**
