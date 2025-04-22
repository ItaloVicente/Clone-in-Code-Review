    /**
     * Sets the spacing for layouts created with this factory. The spacing
     * is the distance between cells within the layout.
     *
     * @param hSpacing horizontal spacing (pixels)
     * @param vSpacing vertical spacing (pixels)
     * @return this
     * @see #margins(Point)
     * @see #margins(int, int)
     */
    public GridLayoutFactory spacing(int hSpacing, int vSpacing) {
        l.horizontalSpacing = hSpacing;
        l.verticalSpacing = vSpacing;
        return this;
    }
