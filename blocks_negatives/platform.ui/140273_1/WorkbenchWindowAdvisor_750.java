    /**
     * Performs arbitrary actions after the window has been opened (possibly
     * after being restored).
     * <p>
     * This method is called after the window has been opened. This method is
     * called after the window has been created from scratch, or when
     * it has been restored from a previously-saved window.
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation does nothing. Subclasses may override.
     * </p>
     */
    public void postWindowOpen() {
    }
