    /**
     * Creates a new instance of this class.
     *
     * @param window the workbench window this action applies to
     * @param showSeparator whether to add a separator in the menu
     */
    public SwitchToWindowMenu(IWorkbenchWindow window, String id,
            boolean showSeparator) {
        super(id);
        this.workbenchWindow = window;
        this.showSeparator = showSeparator;
    }
