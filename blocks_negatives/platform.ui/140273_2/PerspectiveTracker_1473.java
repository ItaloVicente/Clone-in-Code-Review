    /**
     * Creates a perspective tracker for the given window.
     * Subclasses should override <code>update(IPerspectiveDescriptor)</code>
     * to get notified of perspective changes.
     *
     * @param window the window to track
     */
    protected PerspectiveTracker(IWorkbenchWindow window) {
        Assert.isNotNull(window);
        this.window = window;
        window.addPageListener(this);
        window.addPerspectiveListener(this);
    }
