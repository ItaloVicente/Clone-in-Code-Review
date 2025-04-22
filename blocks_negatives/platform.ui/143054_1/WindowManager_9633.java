    /**
     * Adds the given window manager to the list of
     * window managers that have this one as a parent.
     * </p>
     * @param wm the child window manager
     */
    private void addWindowManager(WindowManager wm) {
        if (subManagers == null) {
