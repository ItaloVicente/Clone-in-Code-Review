    /**
     * Performs arbitrary actions before the window is opened.
     * <p>
     * This method is called before the window's controls have been created.
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation does nothing. Subclasses may override.
     * Typical clients will use the window configurer to tweak the
     * workbench window in an application-specific way; however, filling the
     * window's menu bar, tool bar, and status line must be done in
     * {@link ActionBarAdvisor#fillActionBars}, which is called immediately
     * after this method is called.
     * </p>
     */
    public void preWindowOpen() {
    }
