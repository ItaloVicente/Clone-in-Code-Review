    /**
     * Sets the spacing for layouts created with this factory. The spacing
     * is the distance between cells within the layout.
     *
     * @param spacing space between controls in the layout (pixels)
     * @return this
     * @see #margins(Point)
     * @see #margins(int, int)
     */
    public GridLayoutFactory spacing(Point spacing) {
        l.horizontalSpacing = spacing.x;
        l.verticalSpacing = spacing.y;
        return this;
    }
