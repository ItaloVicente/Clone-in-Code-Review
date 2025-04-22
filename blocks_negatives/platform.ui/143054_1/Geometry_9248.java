    }

    /**
     * Returns true iff the given SWT side constant corresponds to a horizontal side
     * of a rectangle. That is, returns true for the top and bottom but false for the
     * left and right.
     *
     * @param swtSideConstant one of SWT.TOP, SWT.BOTTOM, SWT.LEFT, or SWT.RIGHT
     * @return true iff the given side is horizontal.
     * @since 3.0
     */
    public static boolean isHorizontal(int swtSideConstant) {
        return !(swtSideConstant == SWT.LEFT || swtSideConstant == SWT.RIGHT);
    }

    /**
     * Moves the given rectangle by the given delta.
     *
     * @param rect rectangle to move (will be modified)
     * @param delta direction vector to move the rectangle by
     * @since 3.0
     */
    public static void moveRectangle(Rectangle rect, Point delta) {
        rect.x += delta.x;
        rect.y += delta.y;
    }

    /**
     * Moves each edge of the given rectangle outward by the given amount. Negative values
     * cause the rectangle to contract. Does not allow the rectangle's width or height to be
     * reduced below zero.
     *
     * @param rect normalized rectangle to modify
     * @param differenceRect difference rectangle to be added to rect
     * @since 3.3
     */
    public static void expand(Rectangle rect, Rectangle differenceRect) {
    	rect.x += differenceRect.x;
    	rect.y += differenceRect.y;
    	rect.height = Math.max(0, rect.height + differenceRect.height);
    	rect.width = Math.max(0, rect.width + differenceRect.width);
    }

    /**
     * <p>Returns a rectangle which, when added to another rectangle, will expand each side
     * by the given number of units.</p>
     *
     * <p>This is commonly used to store margin sizes. For example:</p>
     *
     * <code><pre>
     *
     *     Rectangle margins = Geometry.createDifferenceRect(10,5,1,15);
     *     Rectangle bounds = someControl.getBounds();
     *     someControl.setBounds(Geometry.add(bounds, margins));
     * </pre></code>
     *
     * @param left distance to expand the left side (negative values move the edge inward)
     * @param right distance to expand the right side (negative values move the edge inward)
     * @param top distance to expand the top (negative values move the edge inward)
     * @param bottom distance to expand the bottom (negative values move the edge inward)
     *
     * @return a difference rectangle that, when added to another rectangle, will cause each
     * side to expand by the given number of units
     * @since 3.3
     */
    public static Rectangle createDiffRectangle(int left, int right, int top, int bottom) {
    	return new Rectangle(-left, -top, left + right, top + bottom);
    }

    /**
     * Moves each edge of the given rectangle outward by the given amount. Negative values
     * cause the rectangle to contract. Does not allow the rectangle's width or height to be
     * reduced below zero.
     *
     * @param rect normalized rectangle to modify
     * @param left distance to move the left edge outward (negative values move the edge inward)
     * @param right distance to move the right edge outward (negative values move the edge inward)
     * @param top distance to move the top edge outward (negative values move the edge inward)
     * @param bottom distance to move the bottom edge outward (negative values move the edge inward)
     * @since 3.1
     */
    public static void expand(Rectangle rect, int left, int right, int top, int bottom) {
        rect.x -= left;
        rect.width = Math.max(0, rect.width + left + right);
        rect.y -= top;
        rect.height = Math.max(0, rect.height + top + bottom);
    }

    /**
     * Normalizes the given rectangle. That is, any rectangle with
     * negative width or height becomes a rectangle with positive
     * width or height that extends to the upper-left of the original
     * rectangle.
     *
     * @param rect rectangle to modify
     * @since 3.0
     */
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

    /**
     * Converts the given rectangle from display coordinates to the local coordinate system
     * of the given object into display coordinates.
     *
     * @param coordinateSystem local coordinate system being converted to
     * @param toConvert rectangle to convert
     * @return a rectangle in control coordinates
     * @since 3.0
     */
    public static Rectangle toControl(Control coordinateSystem,
            Rectangle toConvert) {
    	return(coordinateSystem.getDisplay().map
    			(null,coordinateSystem,toConvert));
    }

    /**
     * Converts the given rectangle from the local coordinate system of the given object
     * into display coordinates.
     *
     * @param coordinateSystem local coordinate system being converted from
     * @param toConvert rectangle to convert
     * @return a rectangle in display coordinates
     * @since 3.0
     */
    public static Rectangle toDisplay(Control coordinateSystem,
            Rectangle toConvert) {
    	return(coordinateSystem.getDisplay().map
    			(coordinateSystem,null,toConvert));

    }

    /**
     * Determines where the given point lies with respect to the given rectangle.
     * Returns a combination of SWT.LEFT, SWT.RIGHT, SWT.TOP, and SWT.BOTTOM, combined
     * with bitwise or (for example, returns SWT.TOP | SWT.LEFT if the point is to the
     * upper-left of the rectangle). Returns 0 if the point lies within the rectangle.
     * Positions are in screen coordinates (ie: a point is to the upper-left of the
     * rectangle if its x and y coordinates are smaller than any point in the rectangle)
     *
     * @param boundary normalized boundary rectangle
     * @param toTest point whose relative position to the rectangle is being computed
     * @return one of SWT.LEFT | SWT.TOP, SWT.TOP, SWT.RIGHT | SWT.TOP, SWT.LEFT, 0,
     * SWT.RIGHT, SWT.LEFT | SWT.BOTTOM, SWT.BOTTOM, SWT.RIGHT | SWT.BOTTOM
     * @since 3.0
     */
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

    /**
     * Returns the distance from the point to the nearest edge of the given
     * rectangle. Returns negative values if the point lies outside the rectangle.
     *
     * @param boundary rectangle to test
     * @param toTest point to test
     * @return the distance between the given point and the nearest edge of the rectangle.
     * Returns positive values for points inside the rectangle and negative values for points
     * outside the rectangle.
     * @since 3.1
     */
    public static int getDistanceFrom(Rectangle boundary, Point toTest) {
        int side = getClosestSide(boundary, toTest);
        return getDistanceFromEdge(boundary, toTest, side);
    }

    /**
     * Returns the edge of the given rectangle is closest to the given
     * point.
     *
     * @param boundary rectangle to test
     * @param toTest point to compare
     * @return one of SWT.LEFT, SWT.RIGHT, SWT.TOP, or SWT.BOTTOM
     *
     * @since 3.0
     */
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

    /**
     * Returns a copy of the given rectangle
     *
     * @param toCopy rectangle to copy
     * @return a copy of the given rectangle
     * @since 3.0
     */
    public static Rectangle copy(Rectangle toCopy) {
        return new Rectangle(toCopy.x, toCopy.y, toCopy.width, toCopy.height);
    }

    /**
     * Returns the size of the rectangle, as a Point
     *
     * @param rectangle rectangle whose size is being computed
     * @return the size of the given rectangle
     * @since 3.0
     */
    public static Point getSize(Rectangle rectangle) {
        return new Point(rectangle.width, rectangle.height);
    }

    /**
     * Sets the size of the given rectangle to the given size
     *
     * @param rectangle rectangle to modify
     * @param newSize new size of the rectangle
     * @since 3.0
     */
    public static void setSize(Rectangle rectangle, Point newSize) {
        rectangle.width = newSize.x;
        rectangle.height = newSize.y;
    }

    /**
     * Sets the x,y position of the given rectangle. For a normalized
     * rectangle (a rectangle with positive width and height), this will
     * be the upper-left corner of the rectangle.
     *
     * @param rectangle rectangle to modify
     * @param newLocation new location of the rectangle
     *
     * @since 3.0
     */
    public static void setLocation(Rectangle rectangle, Point newLocation) {
        rectangle.x = newLocation.x;
        rectangle.y = newLocation.y;
    }

    /**
     * Returns the x,y position of the given rectangle. For normalized rectangles
     * (rectangles with positive width and height), this is the upper-left
     * corner of the rectangle.
     *
     * @param toQuery rectangle to query
     * @return a Point containing the x,y position of the rectangle
     *
     * @since 3.0
     */
    public static Point getLocation(Rectangle toQuery) {
        return new Point(toQuery.x, toQuery.y);
    }

    /**
     * Returns a new rectangle with the given position and dimensions, expressed
     * as points.
     *
     * @param position the (x,y) position of the rectangle
     * @param size the size of the new rectangle, where (x,y) -> (width, height)
     * @return a new Rectangle with the given position and size
     *
     * @since 3.0
     */
    public static Rectangle createRectangle(Point position, Point size) {
        return new Rectangle(position.x, position.y, size.x, size.y);
    }

    /**
