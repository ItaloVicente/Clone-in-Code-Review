    /**
     * The time at which an operation becomes considered a long
     * operation. Used to determine when the busy cursor will
     * be replaced with a progress monitor.
     * @return int
     * @see IProgressService#busyCursorWhile(IRunnableWithProgress)
     */
    int getLongOperationTime();
