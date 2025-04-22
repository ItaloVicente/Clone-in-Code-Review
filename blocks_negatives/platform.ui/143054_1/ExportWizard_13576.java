    /**
     * Initializes the wizard.
     *
     * @param aWorkbench the workbench
     * @param currentSelection the current selectio
     */
    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        this.theWorkbench = aWorkbench;
        this.selection = currentSelection;
