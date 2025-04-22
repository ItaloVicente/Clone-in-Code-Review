    /**
     * Create a new instance of this class.
     *
     * @param window the window
     * @param label the label
     */
    public ExportResourcesAction(IWorkbenchWindow window, String label) {
        super(label);
        if (window == null) {
            throw new IllegalArgumentException();
        }
