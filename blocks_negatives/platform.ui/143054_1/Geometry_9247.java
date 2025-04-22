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
     * Extrudes the given edge inward by the given distance. That is, if one side of the rectangle
     * was sliced off with a given thickness, this returns the rectangle that forms the slice. Note
     * that the returned rectangle will be inside the given rectangle if size > 0.
     *
     * @param toExtrude the rectangle to extrude. The resulting rectangle will share three sides
     * with this rectangle.
     * @param size distance to extrude. A negative size will extrude outwards (that is, the resulting
     * rectangle will overlap the original iff this is positive).
     * @param orientation the side to extrude.  One of SWT.LEFT, SWT.RIGHT, SWT.TOP, or SWT.BOTTOM. The
     * resulting rectangle will always share this side with the original rectangle.
     * @return a rectangle formed by extruding the given side of the rectangle by the given distance.
     * @since 3.0
     */
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

    /**
     * Returns the opposite of the given direction. That is, returns SWT.LEFT if
     * given SWT.RIGHT and visa-versa.
     *
     * @param swtDirectionConstant one of SWT.LEFT, SWT.RIGHT, SWT.TOP, or SWT.BOTTOM
     * @return one of SWT.LEFT, SWT.RIGHT, SWT.TOP, or SWT.BOTTOM
     * @since 3.0
     */
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

    /**
     * Converts the given boolean into an SWT orientation constant.
     *
     * @param horizontal if true, returns SWT.HORIZONTAL. If false, returns SWT.VERTICAL
     * @return SWT.HORIZONTAL or SWT.VERTICAL.
     * @since 3.0
     */
    public static int getSwtHorizontalOrVerticalConstant(boolean horizontal) {
        if (horizontal) {
            return SWT.HORIZONTAL;
        }
