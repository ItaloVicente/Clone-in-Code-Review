    /**
     * Creates an instance of this action, for use in the given window.
     * @param window the window
     */
    public QuickStartAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.QuickStart_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setToolTipText(IDEWorkbenchMessages.QuickStart_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
