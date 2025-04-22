    /**
     * Removes the given window from the set of windows managed by
     * this window manager. Does nothing is this window is
     * not managed by this window manager.
     *
     * @param window the window
     */
    public final void remove(Window window) {
        if (windows.contains(window)) {
            windows.remove(window);
            window.setWindowManager(null);
        }
    }
