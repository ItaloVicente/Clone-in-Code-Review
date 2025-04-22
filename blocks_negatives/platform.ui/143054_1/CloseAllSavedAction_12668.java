    /**
     * Create an instance of this class.
     * @param window the window
     */
    public CloseAllSavedAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.CloseAllSavedAction_text, window);
        setToolTipText(WorkbenchMessages.CloseAllSavedAction_toolTip);
        updateState();
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.CLOSE_ALL_SAVED_ACTION);
    }
