    }

    /**
     * Start watching the ticks. When the long operation time has
     * passed open the dialog.
     */
    public void watchTicks() {
        watchTime = System.currentTimeMillis();
    }

    /**
     * Create a monitor for the receiver that wrappers the superclasses monitor.
     *
     */
    public void createWrapperedMonitor() {
        wrapperedMonitor = new IProgressMonitorWithBlocking() {

            IProgressMonitor superMonitor = ProgressMonitorJobsDialog.super
                    .getProgressMonitor();

            @Override
