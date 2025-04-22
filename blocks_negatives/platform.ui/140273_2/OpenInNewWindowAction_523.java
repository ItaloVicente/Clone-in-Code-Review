    /**
     * Creates a new <code>OpenInNewWindowAction</code>.
     *
     * @param window the workbench window containing this action
     * @param input the input for the new window's page
     */
    public OpenInNewWindowAction(IWorkbenchWindow window, IAdaptable input) {
        super(WorkbenchMessages.OpenInNewWindowAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
        pageInput = input;
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
    }
