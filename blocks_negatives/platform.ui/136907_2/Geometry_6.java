     * <p>Returns a new difference Rectangle whose x, y, width, and height are equal to the difference of the corresponding
     * attributes from the given rectangles</p>
     *
     * <p></p>
     * <b>Example: Compute the margins for a given Composite, and apply those same margins to a new GridLayout</b>
     *
     * <code><pre>
     *      Rectangle clientArea = Display.getCurrent().map(inputComposite,
     *      	inputComposite.getParent(), inputComposite.getClientArea());
     *
     *      Rectangle margins = Geometry.subtract(inputComposite.getBounds(), clientArea);
     *
     *      GridLayout layout = GridLayoutFactory.fillDefaults().margins(margins).create();
     * </pre></code>
     *
     * @param rect1 first rectangle
     * @param rect2 rectangle to subtract
     * @return the difference between the two rectangles (computed as rect1 - rect2)
     * @since 3.3
     */
