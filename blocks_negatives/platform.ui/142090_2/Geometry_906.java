    }

    /**
     * Returns the x or y coordinates of the given point.
     *
     * @param toMeasure point being measured
     * @param width if true, returns x. Otherwise, returns y.
     * @return the x or y coordinate
     * @since 3.1
     */
    public static int getCoordinate(Point toMeasure, boolean width) {
    	return width ? toMeasure.x : toMeasure.y;
    }

    /**
     * Returns the x or y coordinates of the given rectangle.
     *
     * @param toMeasure rectangle being measured
     * @param width if true, returns x. Otherwise, returns y.
     * @return the x or y coordinate
     * @since 3.1
     */
    public static int getCoordinate(Rectangle toMeasure, boolean width) {
    	return width ? toMeasure.x : toMeasure.y;
    }

    /**
     * Sets one dimension of the given rectangle. Modifies the given rectangle.
     *
     * @param toSet rectangle to modify
     * @param width if true, the width is modified. If false, the height is modified.
     * @param newCoordinate new value of the width or height
     * @since 3.1
     */
    public static void setDimension(Rectangle toSet, boolean width, int newCoordinate) {
    	if (width) {
    		toSet.width = newCoordinate;
    	} else {
    		toSet.height = newCoordinate;
    	}
    }

    /**
     * Sets one coordinate of the given rectangle. Modifies the given rectangle.
     *
     * @param toSet rectangle to modify
     * @param width if true, the x coordinate is modified. If false, the y coordinate is modified.
     * @param newCoordinate new value of the x or y coordinates
     * @since 3.1
     */
    public static void setCoordinate(Rectangle toSet, boolean width, int newCoordinate) {
    	if (width) {
    		toSet.x = newCoordinate;
    	} else {
    		toSet.y = newCoordinate;
    	}
    }

    /**
     * Sets one coordinate of the given point. Modifies the given point.
     *
     * @param toSet point to modify
     * @param width if true, the x coordinate is modified. If false, the y coordinate is modified.
     * @param newCoordinate new value of the x or y coordinates
     * @since 3.1
     */
    public static void setCoordinate(Point toSet, boolean width, int newCoordinate) {
    	if (width) {
    		toSet.x = newCoordinate;
    	} else {
    		toSet.y = newCoordinate;
    	}
    }

    /**
     * Returns the distance of the given point from a particular side of the given rectangle.
     * Returns negative values for points outside the rectangle.
     *
     * @param rectangle a bounding rectangle
     * @param testPoint a point to test
     * @param edgeOfInterest side of the rectangle to test against
     * @return the distance of the given point from the given edge of the rectangle
     * @since 3.0
     */
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

    /**
