    /**
     * Constructs a FastProgressReporter that wraps the given progress monitor
     *
     * @param monitor the monitor to wrap
     * @param totalProgress the total progress to be reported
     */
    public FastProgressReporter(IProgressMonitor monitor, int totalProgress) {
        this.monitor = monitor;
        canceled = monitor.isCanceled();
    }
