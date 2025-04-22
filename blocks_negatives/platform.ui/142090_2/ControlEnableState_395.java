    /**
     * Creates a new object and saves in it the current enable/disable state of
     * the given control and its descendents; the controls that are saved are
     * also disabled.
     *
     * @param w
     *            the control
     */
    protected ControlEnableState(Control w) {
        this(w, null);
    }
