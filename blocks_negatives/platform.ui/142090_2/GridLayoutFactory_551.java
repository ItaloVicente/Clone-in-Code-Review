    /**
     * Sets the margins for layouts created with this factory. The margins
     * are the distance between the outer cells and the edge of the layout.
     *
     * @param margins margin size (pixels)
     * @return this
     * @see #spacing(Point)
     * @see #spacing(int, int)
     */
    public GridLayoutFactory margins(Point margins) {
        l.marginWidth = margins.x;
        l.marginHeight = margins.y;
        return this;
    }
