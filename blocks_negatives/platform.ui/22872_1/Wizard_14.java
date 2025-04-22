    public void setHelpAvailable(boolean b) {
        isHelpAvailable = b;
    }

    /**
     * Sets whether this wizard needs a progress monitor.
     * 
     * @param b
     *            <code>true</code> if a progress monitor is required, and
     *            <code>false</code> if none is needed
     * @see #needsProgressMonitor()
     */
    public void setNeedsProgressMonitor(boolean b) {
        needsProgressMonitor = b;
    }

    /**
     * Sets the title bar color for this wizard.
     * 
     * @param color
     *            the title bar color
     */
    public void setTitleBarColor(RGB color) {
        titleBarColor = color;
    }

    /**
     * Sets the window title for the container that hosts this page to the given
     * string.
     * 
     * @param newTitle
     *            the window title for the container
     */
    public void setWindowTitle(String newTitle) {
        windowTitle = newTitle;
        if (container != null) {
