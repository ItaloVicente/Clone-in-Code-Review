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
