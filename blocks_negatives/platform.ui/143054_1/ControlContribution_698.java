    /**
     * Computes the width of the given control which is being added
     * to a tool bar.  This is needed to determine the width of the tool bar item
     * containing the given control.
     * <p>
     * The default implementation of this framework method returns
     * <code>control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x</code>.
     * Subclasses may override if required.
     * </p>
     *
     * @param control the control being added
     * @return the width of the control
     */
    protected int computeWidth(Control control) {
        return control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
    }
