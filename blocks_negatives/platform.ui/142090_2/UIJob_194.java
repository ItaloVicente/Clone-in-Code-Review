    private Display cachedDisplay;

    /**
     * Create a new instance of the receiver with the supplied name. The display
     * used will be the one from the workbench if this is available. UIJobs with
     * this constructor will determine their display at runtime.
     *
     * @param name
     *            the job name
     *
     */
    public UIJob(String name) {
        super(name);
    }

    /**
     * Create a new instance of the receiver with the supplied Display.
     *
     * @param jobDisplay
     *            the display
     * @param name
     *            the job name
     * @see Job
     */
    public UIJob(Display jobDisplay, String name) {
        this(name);
        setDisplay(jobDisplay);
    }

    /**
     * Convenience method to return a status for an exception.
     *
     * @param exception
     * @return IStatus an error status built from the exception
     * @see Job
     */
    public static IStatus errorStatus(Throwable exception) {
        return getStatus(exception);
    }

    /**
     * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
     *      Note: this message is marked final. Implementors should use
     *      runInUIThread() instead.
     */
    @Override
