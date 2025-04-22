    /**
     * Constructs a new perspective action for the given window.
     *
     * @param window the window
     */
    protected PerspectiveAction(IWorkbenchWindow window) {
        Assert.isNotNull(window);
        this.workbenchWindow = window;
        tracker = new PerspectiveTracker(window, this);
    }
