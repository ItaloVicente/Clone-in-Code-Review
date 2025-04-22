
    public SizeCache() {
        this(null);
    }

    /**
     * Creates a cache for size computations on the given control
     *
     * @param control the control for which sizes will be calculated,
     * or null to always return (0,0)
     */
    public SizeCache(Control control) {
        setControl(control);
    }

    /**
     * Sets the control whose size is being cached. Does nothing (will not
     * even flush the cache) if this is the same control as last time or
     * it is already disposed.
     *
     * @param newControl the control whose size is being cached, or null to always return (0,0)
     */
    public void setControl(Control newControl) {
        if (newControl != control && !newControl.isDisposed()) {
            control = newControl;
            if (control == null) {
                independentDimensions = true;
                preferredWidthOrLargerIsMinimumHeight = false;
                widthAdjustment = 0;
                heightAdjustment = 0;
            } else {
                independentDimensions = independentLengthAndWidth(control);
                preferredWidthOrLargerIsMinimumHeight = isPreferredWidthMaximum(control);
                computeHintOffset(control);
