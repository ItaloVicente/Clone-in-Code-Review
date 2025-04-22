    /**
     * Performs arbitrary actions after the window has been created (possibly
     * after being restored), but has not yet been opened.
     * <p>
     * This method is called after the window has been created from scratch,
     * or when it has been restored from a previously-saved window.  In the latter case,
     * this method is called after <code>postWindowRestore</code>.
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation does nothing. Subclasses may override.
     * </p>
     */
    public void postWindowCreate() {
    }
