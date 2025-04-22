    /**
     * Creates a new action with the given text. Register this
     * action with the workbench window for page lifecycle
     * events.
     *
     * @param text the string used as the text for the action,
     *   or <code>null</code> if there is no text
     * @param window the workbench window this action is
     *   registered with
     */
    protected PageEventAction(String text, IWorkbenchWindow window) {
        super(text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        this.activePage = window.getActivePage();
        this.workbenchWindow.addPageListener(this);
        this.workbenchWindow.getPartService().addPartListener(this);
    }
