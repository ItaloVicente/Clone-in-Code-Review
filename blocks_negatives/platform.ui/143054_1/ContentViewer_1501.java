    /**
     * Adds event listener hooks to the given control.
     * <p>
     * All subclasses must call this method when their control is
     * first established.
     * </p>
     * <p>
     * The <code>ContentViewer</code> implementation of this method hooks
     * dispose events for the given control.
     * Subclasses may override if they need to add other control hooks;
     * however, <code>super.hookControl</code> must be invoked.
     * </p>
     *
     * @param control the control
     */
    protected void hookControl(Control control) {
