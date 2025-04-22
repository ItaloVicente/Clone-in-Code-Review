    /**
     * A blockage has occured. Show the blockage and
     * forward any actions to blockingMonitor.
     * <b>NOTE:</b> This will open any blocked notification immediately
     * even if there is a modal shell open.
     *
     * @param parentShell The shell this is being sent from. If the parent
     * shell is <code>null</code> the behavior will be the same as
     * IDialogBlockedHandler#showBlocked(IProgressMonitor, IStatus, String)
     *
     * @param blocking The monitor to forward to. This is most
     * important for calls to <code>cancel()</code>.
     * @param blockingStatus The status that describes the blockage
     * @param blockedName The name of the locked operation.
     * @see IDialogBlockedHandler#showBlocked(IProgressMonitor, IStatus, String)
     */
    public void showBlocked(Shell parentShell, IProgressMonitor blocking,
            IStatus blockingStatus, String blockedName);
