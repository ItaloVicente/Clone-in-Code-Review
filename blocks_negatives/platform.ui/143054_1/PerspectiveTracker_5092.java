    /**
     * Creates a perspective tracker for the given window which will
     * enable the given action only when there is an active perspective.
     *
     * @param window the window to track
     * @param action the action to enable or disable
     */
    public PerspectiveTracker(IWorkbenchWindow window, IAction action) {
        this(window);
        this.action = action;
        update();
    }
