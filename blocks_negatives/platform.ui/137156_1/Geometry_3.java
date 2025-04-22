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
