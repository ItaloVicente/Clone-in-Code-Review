    /**
     * Constructor for use by ActionFactory.
     *
     * @param window the window
     */
    public HelpSearchAction(IWorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setActionDefinitionId(IWorkbenchCommandConstants.HELP_HELP_SEARCH);
