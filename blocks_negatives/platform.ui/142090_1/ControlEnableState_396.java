    /**
     * Creates a new object and saves in it the current enable/disable state of
     * the given control and its descendents except for the given list of
     * exception cases; the controls that are saved are also disabled.
     *
     * @param w
     *            the control
     * @param exceptions
     *            the list of controls to not disable (element type:
     *            <code>Control</code>), or <code>null</code> if none
     */
    protected ControlEnableState(Control w, List<Control> exceptions) {
        super();
        states = new ArrayList<>();
        this.exceptions = exceptions;
        readStateForAndDisable(w);
    }
