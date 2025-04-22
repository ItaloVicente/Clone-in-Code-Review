    /**
     * Add a new instance of the reciever with the
     * supplied name.
     * @param name String
     */
    public WorkbenchJob(String name) {
        super(name);
        addDefaultJobChangeListener();
    }
