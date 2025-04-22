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
