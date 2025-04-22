    /**
     * Performs arbitrary actions after the window has been restored,
     * but before it is opened.
     * <p>
     * This method is called after a previously-saved window has been
     * recreated. This method is not called when a new window is created from
     * scratch. This method is never called when a workbench is started for the
     * very first time, or when workbench state is not saved or restored.
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation does nothing. Subclasses may override.
     * It is okay to call <code>IWorkbench.close()</code> from this method.
     * </p>
     *
     * @exception WorkbenchException thrown if there are any errors to report
     *   from post-restoration of the window
     */
    public void postWindowRestore() throws WorkbenchException {
    }
