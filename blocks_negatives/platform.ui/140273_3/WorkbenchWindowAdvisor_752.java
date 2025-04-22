    /**
     * Opens the introduction componenet.
     * <p>
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation opens the intro in the first window provided
     * if the preference IWorkbenchPreferences.SHOW_INTRO is <code>true</code>.  If
     * an intro is shown then this preference will be set to <code>false</code>.
     * Subsequently, and intro will be shown only if
     * <code>WorkbenchConfigurer.getSaveAndRestore()</code> returns
     * <code>true</code> and the introduction was visible on last shutdown.
     * Subclasses may override.
     * </p>
     */
    public void openIntro() {
