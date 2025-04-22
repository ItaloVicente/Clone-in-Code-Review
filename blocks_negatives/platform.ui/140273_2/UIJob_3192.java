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
