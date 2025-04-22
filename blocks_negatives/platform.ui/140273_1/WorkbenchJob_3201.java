    /**
     * Add a new instance of the receiver with the
     * supplied name.
     * @param name String
     */
    public WorkbenchJob(String name) {
        super(name);
        addDefaultJobChangeListener();
    }
