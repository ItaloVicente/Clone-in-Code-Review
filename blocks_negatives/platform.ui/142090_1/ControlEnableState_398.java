    /**
     * Saves the current enable/disable state of the given control and its
     * descendents in the returned object except for the given list of exception
     * cases; the controls that are saved are also disabled.
     *
     * @param w
     *            the control
     * @param exceptions
     *            the list of controls to not disable (element type:
     *            <code>Control</code>)
     * @return an object capturing the enable/disable state
     */
    public static ControlEnableState disable(Control w, List<Control> exceptions) {
        return new ControlEnableState(w, exceptions);
    }
