    /**
     * Performs the steps that are to be treated as a single logical workspace
     * change.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @param monitor the progress monitor to use to display progress and field
     *   user requests to cancel
     * @exception CoreException if the operation fails due to a CoreException
     * @exception InvocationTargetException if the operation fails due to an exception other than CoreException
     * @exception InterruptedException if the operation detects a request to cancel,
     *  using <code>IProgressMonitor.isCanceled()</code>, it should exit by throwing
     *  <code>InterruptedException</code>.  It is also possible to throw
     *  <code>OperationCanceledException</code>, which gets mapped to <code>InterruptedException</code>
     *  by the <code>run</code> method.
     */
    protected abstract void execute(IProgressMonitor monitor)
            throws CoreException, InvocationTargetException,
            InterruptedException;
