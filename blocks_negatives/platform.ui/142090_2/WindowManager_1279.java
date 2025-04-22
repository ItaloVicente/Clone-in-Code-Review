    /**
     * Adds the given window to the set of windows managed by
     * this window manager. Does nothing is this window is
     * already managed by this window manager.
     *
     * @param window the window
     */
    public void add(Window window) {
        if (!windows.contains(window)) {
            windows.add(window);
            window.setWindowManager(this);
        }
    }
