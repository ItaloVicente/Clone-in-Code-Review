    /**
     * Saves the contents of this part.
     * <p>
     * If the save is successful, the part should fire a property changed event
     * reflecting the new dirty state (<code>PROP_DIRTY</code> property).
     * </p>
     * <p>
     * If the save is cancelled through user action, or for any other reason, the
     * part should invoke <code>setCancelled</code> on the <code>IProgressMonitor</code>
     * to inform the caller.
     * </p>
     * <p>
     * This method is long-running; progress and cancellation are provided
     * by the given progress monitor.
     * </p>
     *
     * @param monitor the progress monitor
     */
    public void doSave(IProgressMonitor monitor);
