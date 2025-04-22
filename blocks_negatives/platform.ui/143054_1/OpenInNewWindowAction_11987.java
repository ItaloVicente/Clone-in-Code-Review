    /**
     * Creates a new <code>OpenInNewWindowAction</code>. Sets
     * the new window page's input to be an application-specific
     * default.
     *
     * @param window the workbench window containing this action
     */
    public OpenInNewWindowAction(IWorkbenchWindow window) {
        this(window, ((Workbench) window.getWorkbench()).getDefaultPageInput());
