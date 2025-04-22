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
