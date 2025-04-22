    /**
     * Set the cursor to busy and run the runnable in a non-UI Thread.
     * The calling thread will be blocked for the duration of the execution
     * of the supplied runnable.
     *
     * After the cursor has been running for
     * <code>getLongOperationTime()</code> replace it with
     * a ProgressMonitorDialog so that the user may cancel.
     * Do not open the ProgressMonitorDialog if there is already a modal
     * dialog open.
     *
     * @param runnable The runnable to execute and show the progress for.
     * @see IProgressService#getLongOperationTime
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    void busyCursorWhile(IRunnableWithProgress runnable)
            throws InvocationTargetException, InterruptedException;
