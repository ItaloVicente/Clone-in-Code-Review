    /**
     * Saves the current enable/disable state of the given control and its
     * descendents in the returned object; the controls are all disabled.
     *
     * @param w
     *            the control
     * @return an object capturing the enable/disable state
     */
    public static ControlEnableState disable(Control w) {
        return new ControlEnableState(w);
    }
