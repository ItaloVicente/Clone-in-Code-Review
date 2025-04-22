    /**
     * A blockage has occured. Show the blockage when there is
     * no longer any modal shells in the UI and forward any actions
     * to blockingMonitor.
     *
     * <b>NOTE:</b> As no shell has been specified this method will
     * not open any blocked notification until all other modal shells
     * have been closed.
     *
     * @param blocking The monitor to forward to. This is most
     * important for calls to <code>cancel()</code>.
     * @param blockingStatus The status that describes the blockage
     * @param blockedName The name of the locked operation.
     */
    public void showBlocked(IProgressMonitor blocking, IStatus blockingStatus,
            String blockedName);
