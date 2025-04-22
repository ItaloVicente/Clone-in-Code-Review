    /**
     * Create a new instance of this class.
     *
     * @param window the window
     */
    public ImportResourcesAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.ImportResourcesAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
