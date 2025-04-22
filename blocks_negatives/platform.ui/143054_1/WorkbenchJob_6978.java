    /**
     * Create a new instance of the receiver with the
     * supplied display and name.
     * Normally this constructor would not be used as
     * it is best to let the job find the display from
     *  the workbench
     * @param jobDisplay Display. The display to run the
     * 		job with.
     * @param name String
     */
    public WorkbenchJob(Display jobDisplay, String name) {
        super(jobDisplay, name);
        addDefaultJobChangeListener();
    }
