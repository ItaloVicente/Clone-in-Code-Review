    /**
     * Create an instance of this class.
     *
     * @param window the window
     */
    public TipsAndTricksAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.TipsAndTricks_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setToolTipText(IDEWorkbenchMessages.TipsAndTricks_toolTip);
        window.getWorkbench().getHelpSystem().setHelp(this,
