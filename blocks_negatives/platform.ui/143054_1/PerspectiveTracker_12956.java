    /**
     * Performs some function based on the active perspective in the window.
     * <p>
     * The default implementation enables the action (if given) if there
     * is an active perspective, otherwise it disables it.
     * </p>
     * <p>
     * Subclasses may override or extend.
     * </p>
     *
     * @param persp the active perspective in the window, or <code>null</code> if none
     */
    protected void update(IPerspectiveDescriptor persp) {
        if (action != null) {
            action.setEnabled(persp != null);
        }
    }
