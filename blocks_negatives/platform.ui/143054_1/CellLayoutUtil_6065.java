    /**
     * Returns the minimum size for the given composite. That is,
     * this returns the smallest values that will have any effect
     * when passed into the composite's setSize method. Passing any
     * smaller value is equivalent to passing the minimum size.
     * <p>
     * This method is intended for use by layouts. The layout can
     * use this information when determining its preferred size.
     * Returning a preferred size smaller than the composite's
     * minimum size is pointless since the composite could never
     * be set to that size. The layout may choose a different preferred
     * size in this situation.
     * </p><p>
     * Note that this method is only concerned with restrictions imposed
     * by the composite; not it's layout. If the only restriction on the
     * composite's size is imposed by the layout, then this method returns (0,0).
     * </p><p>
     * Currently SWT does not expose this information through
     * API, so this method is developed using trial-and-error. Whenever
     * a composite is discovered that will not accept sizes below
     * a certain threshold on some platform, this method should be
     * updated to reflect that fact.
     * </p><p>
     * At this time, the only known composite that has a minimum size
     * are Shells.
     * </p>
     *
     * @param toCompute the composite whose minimum size is being computed
     * @return a size, in pixels, which is the smallest value that can be
     * passed into the composite's setSize(...) method.
     */
    static Point computeMinimumSize(Composite toCompute) {
        if (toCompute instanceof Shell) {
            if (minimumShellSize == null) {
                Shell testShell = new Shell((Shell) toCompute, SWT.DIALOG_TRIM
                        | SWT.RESIZE);
                testShell.setSize(0, 0);
                minimumShellSize = testShell.getSize();
                testShell.dispose();
            }
