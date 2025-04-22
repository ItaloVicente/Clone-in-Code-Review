        INewWizard {

    /**
     * The workbench.
     */
    private IWorkbench workbench;

    /**
     * The current selection.
     */
    protected IStructuredSelection selection;

    /**
     * Creates an empty wizard for creating a new resource in the workspace.
     */
    protected BasicNewResourceWizard() {
        super();
    }

    /**
     * Returns the selection which was passed to <code>init</code>.
     *
     * @return the selection
     */
    public IStructuredSelection getSelection() {
        return selection;
    }

    /**
     * Returns the workbench which was passed to <code>init</code>.
     *
     * @return the workbench
     */
    public IWorkbench getWorkbench() {
        return workbench;
    }

    /**
     * The <code>BasicNewResourceWizard</code> implementation of this
     * <code>IWorkbenchWizard</code> method records the given workbench and
     * selection, and initializes the default banner image for the pages
     * by calling <code>initializeDefaultPageImageDescriptor</code>.
     * Subclasses may extend.
     */
    @Override
