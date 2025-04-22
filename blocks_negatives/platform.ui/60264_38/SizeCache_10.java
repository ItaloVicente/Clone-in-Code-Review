     * Compute the control's size, and ensure that non-default hints are returned verbatim
     * (this tries to compensate for SWT's hints, which aren't really the outer width of the
     * control).
     *
     * @param widthHint the horizontal hint
     * @param heightHint the vertical hint
     * @return the control's size
     */
    public Point computeAdjustedSize(int widthHint, int heightHint) {
